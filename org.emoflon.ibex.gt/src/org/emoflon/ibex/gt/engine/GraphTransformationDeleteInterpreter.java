package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IDeletePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.common.utils.EMFEdge;
import org.emoflon.ibex.common.utils.EMFManipulationUtils;

import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXNode;

/**
 * Interpreter applying deletion of elements for graph transformation.
 */
public class GraphTransformationDeleteInterpreter implements IDeletePatternInterpreter {
	/**
	 * The trash resource.
	 */
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
	public Optional<IMatch> apply(final IBeXDeletePattern deletePattern, final IMatch match, final PushoutApproach po) {
		// Check applicability with DPO semantics.
		if (po == PushoutApproach.DPO && !isApplicableDPO(deletePattern, match)) {
			return Optional.empty();
		}

		delete(deletePattern, match);
		return Optional.of(match);
	}

	/**
	 * Executes the deletion.
	 * 
	 * @param deletePattern
	 *            the pattern defining which elements are deleted
	 * @param match
	 *            the match
	 */
	private void delete(final IBeXDeletePattern deletePattern, final IMatch match) {
		Set<EObject> nodesToDelete = new HashSet<EObject>();
		Set<EMFEdge> edgesToDelete = new HashSet<EMFEdge>();
		deletePattern.getDeletedNodes().forEach(node -> {
			nodesToDelete.add((EObject) match.get(node.getName()));
		});
		deletePattern.getDeletedEdges().forEach(edge -> {
			EObject src = (EObject) match.get(edge.getSourceNode().getName());
			EObject trg = (EObject) match.get(edge.getTargetNode().getName());
			edgesToDelete.add(new EMFEdge(src, trg, edge.getType()));
		});
		EMFManipulationUtils.delete(nodesToDelete, edgesToDelete,
				node -> trashResource.getContents().add(EcoreUtil.getRootContainer(node)));
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
			// All existing references of the deleted node must be deleted by the pattern,
			// otherwise rule application is not allowed according to DPO.
			EObject eObject = (EObject) match.get(deletedNode.getName());

			// Check outgoing containment edges.
			for (EObject target : eObject.eContents()) {
				EReference reference = target.eContainmentFeature();
				if (!patternDeletesEdge(deletePattern, match, eObject, target, reference)) {
					return false;
				}
			}

			// Check other outgoing edges.
			for (EContentsEList.FeatureIterator featureIterator = (EContentsEList.FeatureIterator) eObject
					.eCrossReferences().iterator(); featureIterator.hasNext();) {
				EObject target = (EObject) featureIterator.next();
				EReference reference = (EReference) featureIterator.feature();
				if (!patternDeletesEdge(deletePattern, match, eObject, target, reference)) {
					return false;
				}
			}

			// Check incoming containment edge.
			if (eObject.eContainer() != null) {
				if (!patternDeletesEdge(deletePattern, match, eObject.eContainer(), eObject,
						eObject.eContainmentFeature())) {
					return false;
				}
			}

			// Check other incoming edges.
			Collection<Setting> crossReferences = EcoreUtil.UsageCrossReferencer.find(eObject, eObject.eResource());
			for (Setting crossReference : crossReferences) {
				EReference reference = (EReference) crossReference.getEStructuralFeature();
				if (!patternDeletesEdge(deletePattern, match, eObject, crossReference.getEObject(), reference)) {
					return false;
				}
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
