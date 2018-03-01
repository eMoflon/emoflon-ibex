package org.emoflon.ibex.gt.engine;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IDeletePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.utils.EMFManipulationUtils;

import IBeXLanguage.IBeXDeletePattern;

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
	public Optional<IMatch> apply(final IBeXDeletePattern deletePattern, final IMatch match) {
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
}
