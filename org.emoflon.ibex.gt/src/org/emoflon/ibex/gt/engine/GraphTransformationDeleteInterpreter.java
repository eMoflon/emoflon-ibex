package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IDeletePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.PushoutSemantics;
import org.emoflon.ibex.common.utils.EMFManipulationUtils;

import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXNode;

/**
 * Interpreter applying deletion of elements for graph transformation.
 */
public class GraphTransformationDeleteInterpreter implements IDeletePatternInterpreter {
	private Resource trashResource;

	/**
	 * Creates a new GraphTransformationDeleteInterpreter.
	 * 
	 * @param trashResource
	 *            the resource containing trash objects
	 */
	public GraphTransformationDeleteInterpreter(final Resource trashResource) {
		this.trashResource = trashResource;
	}

	@Override
	public Optional<IMatch> apply(final IBeXDeletePattern deletePattern, final IMatch match,
			final PushoutSemantics po) {
		// Check applicability with DPO semantics.
		if (po == PushoutSemantics.DPO && !this.isApplicableDPO(deletePattern, match)) {
			return Optional.empty();
		}

		deletePattern.getDeletedNodes().forEach(node -> {
			EObject eObject = (EObject) match.get(node.getName());
			if (EMFManipulationUtils.isDanglingNode(eObject)) {
				// Move to trash resource.
				trashResource.getContents().add(EcoreUtil.getRootContainer(eObject));
			}
			EcoreUtil.delete(eObject);
		});
		deletePattern.getDeletedEdges().forEach(edge -> {
			EObject src = (EObject) match.get(edge.getSourceNode().getName());
			EObject trg = (EObject) match.get(edge.getTargetNode().getName());
			EMFManipulationUtils.deleteEdge(src, trg, edge.getType());
		});
		return Optional.of(match);
	}

	/**
	 * Checks whether the delete pattern can be applied to the given match according
	 * to DPO semantics.
	 * 
	 * @param deletePattern
	 *            the delete pattern
	 * @param match
	 *            the match
	 * @return true if and only if all dangling edges must be explicitly deleted by
	 *         the pattern.
	 */
	@SuppressWarnings("rawtypes")
	private boolean isApplicableDPO(final IBeXDeletePattern deletePattern, final IMatch match) {
		for (IBeXNode deletedNode : deletePattern.getDeletedNodes()) {
			EObject eObject = (EObject) match.get(deletedNode.getName());

			// If a containment reference exists, the delete pattern must delete it.
			if (eObject.eContainer() != null) {
				if (!patternDeletesEdge(deletePattern, match, eObject, eObject.eContainer(),
						eObject.eContainmentFeature())) {
					return false;
				}
			}

			// All existing references must be deleted by the pattern.
			for (EContentsEList.FeatureIterator featureIterator = (EContentsEList.FeatureIterator) eObject
					.eCrossReferences().iterator(); featureIterator.hasNext();) {
				EObject eObjectTarget = (EObject) featureIterator.next();
				EReference eReference = (EReference) featureIterator.feature();
				if (!patternDeletesEdge(deletePattern, match, eObject, eObjectTarget, eReference)) {
					return false;
				}
			}

			// Find references with target eObject.
			Collection<Setting> crossReferences = EcoreUtil.UsageCrossReferencer.find(eObject,
					this.trashResource.getResourceSet());
			for (Setting crossReference : crossReferences) {
				System.out.println(
						"cross reference" + crossReference.getEObject() + "" + crossReference.getEStructuralFeature());
			}
		}
		return true;
	}

	/**
	 * Checks whether the edge between the given source and target object of the
	 * given type is deleted by the delete pattern on the given match
	 * 
	 * @param deletePattern
	 *            the delete pattern
	 * @param match
	 *            the match
	 * @param source
	 *            the source object of the edge
	 * @param target
	 *            the target object of the edge
	 * @param type
	 *            the type of the edge
	 * @return <code>true</code> if and only if the pattern specifies to delete the
	 *         given edge
	 */
	private static boolean patternDeletesEdge(final IBeXDeletePattern deletePattern, final IMatch match,
			final EObject source, final EObject target, final EReference type) {
		for (IBeXEdge deletedEdge : deletePattern.getDeletedEdges()) {
			if (deletedEdge.getType().getName().equals(type.getName())) {
				EObject deletedEdgeSource = (EObject) match.get(deletedEdge.getSourceNode().getName());
				EObject deletedEdgeTarget = (EObject) match.get(deletedEdge.getTargetNode().getName());
				if ((deletedEdgeSource.equals(source) && deletedEdgeTarget.equals(target))
						|| (deletedEdgeSource.equals(target) && deletedEdgeTarget.equals(source))) {
					return true;
				}
			}
		}
		return false;
	}
}
