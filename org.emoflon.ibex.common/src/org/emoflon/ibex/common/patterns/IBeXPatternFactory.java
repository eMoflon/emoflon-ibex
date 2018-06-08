package org.emoflon.ibex.common.patterns;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;

/**
 * Some utility methods to create objects of the IBeX model.
 */
public class IBeXPatternFactory {

	/**
	 * Creates an IBeXNode with the given name and the given type.
	 * 
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @return the IBeXNode
	 */
	public static IBeXNode createNode(final String name, final EClass type) {
		IBeXNode node = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		node.setName(name);
		node.setType(type);
		return node;
	}

	/**
	 * Creates an IBeXEdge of the given type between the two nodes.
	 * 
	 * @param sourceNode
	 *            the source node
	 * @param targetNode
	 *            the target node
	 * @param type
	 *            the type
	 * @return the IBeXEdge
	 */
	public static IBeXEdge createEdge(final IBeXNode sourceNode, final IBeXNode targetNode, final EReference type) {
		IBeXEdge edge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		edge.setSourceNode(sourceNode);
		edge.setTargetNode(targetNode);
		edge.setType(type);
		return edge;
	}
}
