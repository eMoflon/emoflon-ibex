package org.emoflon.ibex.gt.transformation;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.gt.transformation.InternalGTToIBeX;
import org.junit.Test;

import GTLanguage.GTEdge;
import GTLanguage.GTLanguageFactory;
import GTLanguage.GTNode;
import GTLanguage.GTRule;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXGraph;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPattern;

/**
 * JUnit tests for the transformation from the internal GT model to IBeXPatterns.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class InternalGTToIBeXTest {
	private static EcorePackage ep = EcorePackage.eINSTANCE;

	@Test
	public void testTransformationForSimpleConstraintsA() {
		GTRule gtRule = createGTRule("A");
		GTNode node1 = addGTNode(gtRule, "operation", ep.getEOperation());
		GTNode node2 = addGTNode(gtRule, "typeParam", ep.getETypeParameter());
		GTNode node3 = addGTNode(gtRule, "type", ep.getEGenericType());
		addGTEdge(gtRule, ep.getEOperation_ETypeParameters(), node1, node2);
		addGTEdge(gtRule, ep.getETypeParameter_EBounds(), node2, node3);

		GTNode node4 = addGTNode(gtRule, "operation2", ep.getEOperation());
		GTNode node5 = addGTNode(gtRule, "typeParam2", ep.getETypeParameter());
		GTNode node6 = addGTNode(gtRule, "type2", ep.getEGenericType());
		addGTEdge(gtRule, ep.getEOperation_ETypeParameters(), node4, node5);
		addGTEdge(gtRule, ep.getETypeParameter_EBounds(), node5, node6);

		transformAndCheck(gtRule);
	}

	@Test
	public void testTransformationForSimpleConstraintsB() {
		GTRule gtRule = createGTRule("B");
		GTNode node1 = addGTNode(gtRule, "object1", ep.getEObject());
		GTNode node2 = addGTNode(gtRule, "object2", ep.getEObject());
		GTNode node3 = addGTNode(gtRule, "annotation", ep.getEAnnotation());
		addGTEdge(gtRule, ep.getEAnnotation_References(), node3, node1);
		addGTEdge(gtRule, ep.getEAnnotation_References(), node3, node2);

		transformAndCheck(gtRule);
	}

	private static GTEdge addGTEdge(final GTRule gtRule, final EReference edgeType, final GTNode sourceNode,
			final GTNode targetNode) {
		GTEdge gtEdge = GTLanguageFactory.eINSTANCE.createGTEdge();
		gtEdge.setSourceNode(sourceNode);
		gtEdge.setTargetNode(targetNode);
		gtEdge.setType(edgeType);
		gtRule.getGraph().getEdges().add(gtEdge);
		return gtEdge;
	}

	private static GTNode addGTNode(final GTRule gtRule, final String nodeName, final EClass nodeType) {
		GTNode gtNode = GTLanguageFactory.eINSTANCE.createGTNode();
		gtNode.setName(nodeName);
		gtNode.setType(nodeType);
		gtRule.getGraph().getNodes().add(gtNode);
		return gtNode;
	}

	private static GTRule createGTRule(final String ruleName) {
		GTRule gtRule = GTLanguageFactory.eINSTANCE.createGTRule();
		gtRule.setName(ruleName);
		gtRule.setGraph(GTLanguageFactory.eINSTANCE.createGTGraph());
		return gtRule;
	}

	private static void transformAndCheck(final GTRule gtRule) {
		assertEquivalence(gtRule, InternalGTToIBeX.transformRuleToIBeX(gtRule));
	}

	private static void assertEquivalence(final GTRule gtRule, final IBeXPattern ibexPattern) {
		assertEquals(gtRule.getName(), ibexPattern.getName());

		// Assert nodes.
		assertEquals(gtRule.getGraph().getNodes().size(), ibexPattern.getNodes().size());
		gtRule.getGraph().getNodes().forEach(gtNode -> assertNode(gtNode, ibexPattern.getNodes()));

		// Assert edges.
		assertEquals(gtRule.getGraph().getEdges().size(), ibexPattern.getEdges().size());
		gtRule.getGraph().getEdges().forEach(gtEdge -> assertEdge(gtEdge, ibexPattern.getEdges()));

		// Assert graph with all nodes.
		Optional<IBeXGraph> allNodesGraph = ibexPattern.getGraphs().stream()
				.filter(g -> g.getName().equals("all-nodes")).findAny();
		assertTrue(allNodesGraph.isPresent());
		assertEquals(gtRule.getGraph().getNodes().size(), allNodesGraph.get().getNodes().size());
		gtRule.getGraph().getNodes().forEach(gtNode -> assertNode(gtNode, allNodesGraph.get().getNodes()));

		// Per edge type, assert graphs with all references a graph with all edges and
		// the nodes involved.
		gtRule.getGraph().getEdges().stream().map(e -> e.getType()).forEach(edgeType -> {
			Optional<IBeXGraph> ibexGraph = ibexPattern.getGraphs().stream()
					.filter(g -> g.getName().equals("edges-" + edgeType.getName())).findAny();
			assertTrue(ibexGraph.isPresent());

			List<GTEdge> gtEdges = gtRule.getGraph().getEdges().stream().filter(e -> e.getType().equals(edgeType))
					.collect(Collectors.toList());
			assertEquals(gtEdges.size(), ibexGraph.get().getEdges().size());
			gtEdges.forEach(gtEdge -> assertEdge(gtEdge, ibexGraph.get().getEdges()));

			gtRule.getGraph().getNodes().stream().forEach(gtNode -> {
				boolean isSourceNode = gtNode.getOutgoingEdges().stream().anyMatch(e -> e.getType().equals(edgeType));
				boolean isTargetNode = gtNode.getIncomingEdges().stream().anyMatch(e -> e.getType().equals(edgeType));
				assertNode(gtNode, ibexGraph.get().getNodes(), isSourceNode || isTargetNode);
			});
		});
	}

	private static void assertNode(final GTNode gtNode, final EList<IBeXNode> ibexNodes) {
		assertNode(gtNode, ibexNodes, true);
	}

	private static void assertNode(final GTNode gtNode, final EList<IBeXNode> ibexNodes, boolean isPresent) {
		Optional<IBeXNode> ibexNode = ibexNodes.stream().filter(n -> gtNode.getName().equals(n.getName())).findAny();
		assertEquals(isPresent, ibexNode.isPresent());
		if (isPresent) {
			assertEquals(gtNode.getType(), ibexNode.get().getType());
		}
	}

	private static void assertEdge(final GTEdge gtEdge, final EList<IBeXEdge> ibexEdges) {
		Optional<IBeXEdge> ibexEdge = ibexEdges.stream() //
				.filter(e -> e.getType().equals(gtEdge.getType())) // correct type
				.filter(e -> e.getSourceNode().getName().equals(gtEdge.getSourceNode().getName())) // correct source
				.filter(e -> e.getTargetNode().getName().equals(gtEdge.getTargetNode().getName())) // correct target
				.findAny();
		assertTrue(ibexEdge.isPresent());
	}
}
