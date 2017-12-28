package org.emoflon.ibex.gt;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.gt.editor.gT.BindingType;
import org.emoflon.ibex.gt.editor.gT.GTFactory;
import org.emoflon.ibex.gt.editor.gT.Model;
import org.emoflon.ibex.gt.editor.gT.Node;
import org.emoflon.ibex.gt.editor.gT.Reference;
import org.emoflon.ibex.gt.editor.gT.Rule;
import org.emoflon.ibex.gt.transformation.EditorToInternalGT;
import org.junit.Test;

import GTLanguage.GTEdge;
import GTLanguage.GTGraph;
import GTLanguage.GTNode;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;

/**
 * JUnit tests for the transformation from the editor to the internal
 * meta-model.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class EditorToInternalTransformationTest {
	private static EcorePackage ep = EcorePackage.eINSTANCE;

	@Test
	public void testTransformationForSimpleConstraints() {
		Node node1 = createEditorNode(BindingType.CONTEXT, "operation", ep.getEOperation());
		Node node2 = createEditorNode(BindingType.CONTEXT, "typeParam", ep.getETypeParameter());
		Node node3 = createEditorNode(BindingType.CONTEXT, "type", ep.getEGenericType());
		createReferenceForEditorNode(node1, BindingType.CONTEXT, ep.getEOperation_ETypeParameters(), "typeParam");
		createReferenceForEditorNode(node2, BindingType.CONTEXT, ep.getETypeParameter_EBounds(), "type");
		Rule ruleA = createEditorRule("A", node1, node2, node3);

		Rule ruleB = createEditorRule("B");

		Model editorModel = createEditorModel(ruleA, ruleB);
		transformAndCheck(editorModel);
	}

	private static Model createEditorModel(final Rule... rules) {
		Model editorModel = GTFactory.eINSTANCE.createModel();
		Arrays.stream(rules).forEach(r -> editorModel.getRules().add(r));
		return editorModel;
	}

	private static Rule createEditorRule(final String name, final Node... nodes) {
		Rule editorRule = GTFactory.eINSTANCE.createRule();
		editorRule.setName(name);
		Arrays.stream(nodes).forEach(n -> editorRule.getNodes().add(n));
		return editorRule;
	}

	private static Node createEditorNode(final BindingType bindingType, final String name, final EClass type) {
		Node editorNode = GTFactory.eINSTANCE.createNode();
		editorNode.setBindingType(bindingType);
		editorNode.setName(name);
		editorNode.setType(type);
		return editorNode;
	}

	private static Reference createReferenceForEditorNode(final Node editorNode, final BindingType bindingType,
			final EReference type, final String value) {
		Reference reference = GTFactory.eINSTANCE.createReference();
		reference.setBindingType(bindingType);
		reference.setType(type);
		reference.setValue(value);
		editorNode.getConstraints().add(reference);
		return reference;
	}

	private static void transformAndCheck(final Model editorModel) {
		GTRuleSet gtRuleSet = EditorToInternalGT.transformRuleSet(editorModel);
		checkRules(editorModel, gtRuleSet);
	}

	private static void checkRules(final Model editorModel, final GTRuleSet gtRuleSet) {
		// Correct number of rules.
		assertEquals(editorModel.getRules().size(), gtRuleSet.getRules().size());

		// Correct rules.
		editorModel.getRules().forEach(editorRule -> checkRule(editorRule, gtRuleSet.getRules()));
	}

	private static void checkRule(final Rule editorRule, final EList<GTRule> gtRules) {
		Optional<GTRule> gtRuleOptional = gtRules.stream().filter(r -> editorRule.getName().equals(r.getName()))
				.findAny();
		assertTrue(gtRuleOptional.isPresent());
		GTRule gtRule = gtRuleOptional.get();
		checkNodes(editorRule.getNodes(), gtRule.getGraph().getNodes());
		checkEdges(editorRule.getNodes(), gtRule.getGraph());
	}

	private static void checkNodes(final EList<Node> editorNodes, final EList<GTNode> gtNodes) {
		// Correct number of nodes.
		assertEquals(editorNodes.size(), gtNodes.size());

		// Correct nodes.
		editorNodes.forEach(editorNode -> checkNode(editorNode, gtNodes));
	}

	private static void checkNode(final Node editorNode, final EList<GTNode> gtNodes) {
		Optional<GTNode> gtNodeOptional = EditorToInternalGT.findGTNodeWithName(gtNodes, editorNode.getName());
		assertTrue(gtNodeOptional.isPresent());
		assertEquals(editorNode.getType(), gtNodeOptional.get().getType());
	}

	private static void checkEdges(final EList<Node> editorNodes, final GTGraph gtGraph) {
		List<Reference> references = editorNodes.stream().map(n -> n.getConstraints()).flatMap(c -> c.stream())
				.filter(c -> c instanceof Reference).map(r -> (Reference) r).collect(Collectors.toList());
		// Correct number of edges.
		assertEquals(references.size(), gtGraph.getEdges().size());

		// Correct edges.
		editorNodes.forEach(editorNode -> {
			editorNode.getConstraints().stream().filter(c -> c instanceof Reference).map(r -> (Reference) r)
					.forEach(reference -> checkEdge(editorNode, reference, gtGraph));
		});
	}

	private static void checkEdge(final Node editorNode, final Reference reference, final GTGraph gtGraph) {
		Optional<GTEdge> gtEdgeOptional = gtGraph.getEdges().stream()
				.filter(e -> e.getType().equals(reference.getType())) // correct type
				.filter(e -> editorNode.getName().equals(e.getSourceNode().getName())) // correct source
				.filter(e -> reference.getValue().equals(e.getTargetNode().getName())) // correct target
				.findAny();
		assertTrue(gtEdgeOptional.isPresent());
	}
}
