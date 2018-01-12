package org.emoflon.ibex.gt.transformation;

import static org.junit.Assert.*;

import java.util.Optional;

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
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXPatternInvocation;

/**
 * JUnit tests for the transformation from the internal GT model to IBeX
 * Patterns.
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

		checkSimpleRuleToPattern(gtRule);
		checkRuleToPattern(gtRule);
	}

	@Test
	public void testTransformationForSimpleConstraintsB() {
		GTRule gtRule = createGTRule("B");
		GTNode node1 = addGTNode(gtRule, "object1", ep.getEObject());
		GTNode node2 = addGTNode(gtRule, "object2", ep.getEObject());
		GTNode node3 = addGTNode(gtRule, "annotation", ep.getEAnnotation());
		addGTEdge(gtRule, ep.getEAnnotation_References(), node3, node1);
		addGTEdge(gtRule, ep.getEAnnotation_References(), node3, node2);

		checkSimpleRuleToPattern(gtRule);
		checkRuleToPattern(gtRule);
	}

	// Utility methods to create examples

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

	// Utility methods for assertions

	private static void checkSimpleRuleToPattern(final GTRule gtRule) {
		IBeXPattern ibexPattern = InternalGTToIBeX.transformRule(gtRule, false);
		assertEquals(gtRule.getName(), ibexPattern.getName());

		assertNodes(gtRule.getGraph().getNodes(), ibexPattern.getLocalNodes());
		assertEquals(0, ibexPattern.getSignatureNodes().size());
		assertEdges(gtRule.getGraph().getEdges(), ibexPattern.getLocalEdges());

		assertEquals(0, ibexPattern.getNegativeInvocations().size());
		assertEquals(0, ibexPattern.getPositiveInvocations().size());
	}

	private static void checkRuleToPattern(final GTRule gtRule) {
		IBeXPattern ibexPattern = InternalGTToIBeX.transformRule(gtRule);
		assertEquals(gtRule.getName(), ibexPattern.getName());

		assertNodes(gtRule.getGraph().getNodes(), ibexPattern.getLocalNodes());
		assertEquals(0, ibexPattern.getSignatureNodes().size());
		assertEquals(0, ibexPattern.getLocalEdges().size());

		assertEquals(0, ibexPattern.getNegativeInvocations().size());
		assertInvocationsForEdges(gtRule.getGraph().getEdges(), ibexPattern);
	}

	private static void assertNodes(final EList<GTNode> gtNodes, final EList<IBeXNode> ibexNodes) {
		assertEquals(gtNodes.size(), ibexNodes.size());
		gtNodes.forEach(gtNode -> assertNode(gtNode, ibexNodes));
	}

	private static void assertNode(final GTNode gtNode, final EList<IBeXNode> ibexNodes) {
		Optional<IBeXNode> ibexNode = ibexNodes.stream().filter(n -> gtNode.getName().equals(n.getName())).findAny();
		assertTrue(ibexNode.isPresent());
		assertEquals(gtNode.getType(), ibexNode.get().getType());
	}

	private static void assertEdges(final EList<GTEdge> gtEdges, final EList<IBeXEdge> ibexEdges) {
		assertEquals(gtEdges.size(), ibexEdges.size());
		gtEdges.forEach(gtEdge -> assertEdge(gtEdge, ibexEdges));
	}

	private static void assertEdge(final GTEdge gtEdge, final EList<IBeXEdge> ibexEdges) {
		Optional<IBeXEdge> ibexEdge = ibexEdges.stream() //
				.filter(e -> e.getType().equals(gtEdge.getType())) // correct type
				.filter(e -> e.getSourceNode().getName().equals(gtEdge.getSourceNode().getName())) // correct source
				.filter(e -> e.getTargetNode().getName().equals(gtEdge.getTargetNode().getName())) // correct target
				.findAny();
		assertTrue(ibexEdge.isPresent());
	}

	private static void assertInvocationsForEdges(final EList<GTEdge> gtEdges, final IBeXPattern ibexPattern) {
		assertEquals(gtEdges.size(), ibexPattern.getPositiveInvocations().size());
		gtEdges.forEach(gtEdge -> {
			String patternName = "edge-" + gtEdge.getSourceNode().getType().getName() + "-" + gtEdge.getType().getName()
					+ "-" + gtEdge.getTargetNode().getType().getName();
			Optional<IBeXNode> sourceNodeOfEdge = InternalGTToIBeX.findIBeXNodeWithName(ibexPattern,
					gtEdge.getSourceNode().getName());
			Optional<IBeXNode> targetNodeOfEdge = InternalGTToIBeX.findIBeXNodeWithName(ibexPattern,
					gtEdge.getTargetNode().getName());

			assertTrue(sourceNodeOfEdge.isPresent());
			assertTrue(targetNodeOfEdge.isPresent());

			Optional<IBeXPatternInvocation> ibexPatternInvocation = ibexPattern.getPositiveInvocations().stream() //
					.filter(invocation -> invocation.getInvoking().getName().equals(patternName)) // correct pattern
					.filter(invocation -> invocation.getMapping().containsKey(sourceNodeOfEdge.get())) // source node
					.filter(invocation -> invocation.getMapping().containsKey(targetNodeOfEdge.get())) // target node
					.findAny();
			assertTrue(ibexPatternInvocation.isPresent());

			assertEquals(0, ibexPatternInvocation.get().getInvoking().getLocalNodes().size());
			assertEquals(2, ibexPatternInvocation.get().getInvoking().getSignatureNodes().size());
			assertEquals(1, ibexPatternInvocation.get().getInvoking().getLocalEdges().size());

			assertEquals(ibexPattern, ibexPatternInvocation.get().getInvokedBy());

			assertEquals("src", ibexPatternInvocation.get().getMapping().get(sourceNodeOfEdge.get()).getName());
			assertEquals("trg", ibexPatternInvocation.get().getMapping().get(targetNodeOfEdge.get()).getName());
		});
	}
}
