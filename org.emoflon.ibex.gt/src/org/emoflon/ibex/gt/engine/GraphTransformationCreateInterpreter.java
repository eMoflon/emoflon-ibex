package org.emoflon.ibex.gt.engine;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.ICreatePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.utils.EMFManipulationUtils;

import IBeXLanguage.IBeXCreatePattern;

/**
 * Interpreter applying creation of elements for graph transformation.
 */
public class GraphTransformationCreateInterpreter implements ICreatePatternInterpreter {
	private Resource defaultResource;

	/**
	 * Creates a new GraphTransformationCreateInterpreter.
	 * 
	 * @param defaultResource
	 *            the default resource
	 */
	public GraphTransformationCreateInterpreter(final Resource defaultResource) {
		this.defaultResource = defaultResource;
	}

	@Override
	public Optional<IMatch> apply(final IBeXCreatePattern createPattern, final IMatch match) {
		createPattern.getCreatedNodes().forEach(node -> {
			EObject newNode = EcoreUtil.create(node.getType());
			this.defaultResource.getContents().add(newNode);
			match.put(node.getName(), newNode);
		});
		createPattern.getCreatedEdges().forEach(edge -> {
			EObject src = (EObject) match.get(edge.getSourceNode().getName());
			EObject trg = (EObject) match.get(edge.getTargetNode().getName());
			EMFManipulationUtils.createEdge(src, trg, edge.getType());
		});
		return Optional.of(match);
	}
}
