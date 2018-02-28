package org.emoflon.ibex.gt.engine;

import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.ICreatePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;

import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXNode;

/**
 * Interpreter applying creation of elements for graph transformation.
 */
public class GraphTransformationCreateInterpreter implements ICreatePatternInterpreter {
	private Resource resource;

	public GraphTransformationCreateInterpreter(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public Optional<IMatch> apply(final IBeXCreatePattern createPattern, final IMatch match) {
		createPattern.getCreatedNodes().forEach(node -> this.createNode(node, match));
		//createPattern.getCreatedEdges().forEach(edge -> this.createEdge(edge, match));
		return Optional.of(match);
	}

	private void createNode(IBeXNode node, final IMatch match) {
		EObject newNode = EcoreUtil.create(node.getType());
		this.resource.getContents().add(newNode);
		match.put(node.getName(), newNode);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createEdge(IBeXEdge edge, final IMatch match) {
		System.out.println(edge.getSourceNode().getName() + " : " + match.get(edge.getSourceNode().getName()));
		EObject src = (EObject) match.get(edge.getSourceNode().getName());
		EObject trg = (EObject) match.get(edge.getSourceNode().getName());

		System.out.println("Create edge " + edge.getType().getName() + " - " + src + " - " + trg);

		EReference reference = edge.getType();
		if (reference.isMany()) {
			((EList) src.eGet(reference)).add(trg);
		} else {
			src.eSet(reference, trg);
		}
	}
}
