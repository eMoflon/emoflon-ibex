package org.emoflon.ibex.gt.transformation;

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
 * JUnit tests for the transformation from the editor to the internal GT model.
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
		createReferenceForEditorNode(node1, BindingType.CONTEXT, ep.getEOperation_ETypeParameters(), node2);
		createReferenceForEditorNode(node2, BindingType.CONTEXT, ep.getETypeParameter_EBounds(), node3);
		Rule ruleA = createEditorRule("A", node1, node2, node3);

		Node node4 = createEditorNode(BindingType.CONTEXT, "object1", ep.getEObject());
		Node node5 = createEditorNode(BindingType.CONTEXT, "object2", ep.getEObject());
		Node node6 = createEditorNode(BindingType.CONTEXT, "annotation", ep.getEAnnotation());
		createReferenceForEditorNode(node6, BindingType.CONTEXT, ep.getEAnnotation_References(), node4);
		createReferenceForEditorNode(node6, BindingType.CONTEXT, ep.getEAnnotation_References(), node5);
		Rule ruleB = createEditorRule("B", node4, node5, node6);

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
			final EReference type, final Node target) {
		Reference reference = GTFactory.eINSTANCE.createReference();
		reference.setBindingType(bindingType);
		reference.setType(type);
		reference.setTarget(target);
		editorNode.getConstraints().add(reference);
		return reference;
	}

	private static void transformAndCheck(final Model editorModel) {
		GTRuleSet gtRuleSet = EditorToInternalGT.transformRuleSet(editorModel);
		checkRules(editorModel, gtRuleSet);
	}

	private static void checkRules(final Model editorModel, final GTRuleSet gtRuleSet) {
		assertEquals("number of rules", editorModel.getRules().size(), gtRuleSet.getRules().size());
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
		assertEquals("number of nodes", editorNodes.size(), gtNodes.size());
		editorNodes.forEach(editorNode -> checkNode(editorNode, gtNodes));
	}

	private static void checkNode(final Node editorNode, final EList<GTNode> gtNodes) {
		Optional<GTNode> gtNode = EditorToInternalGT.findGTNodeWithName(gtNodes, editorNode.getName());
		assertTrue(gtNode.isPresent());
		assertEquals(editorNode.getType(), gtNode.get().getType());
	}

	private static void checkEdges(final EList<Node> editorNodes, final GTGraph gtGraph) {
		List<Reference> references = editorNodes.stream().map(n -> n.getConstraints()).flatMap(c -> c.stream())
				.filter(c -> c instanceof Reference).map(r -> (Reference) r).collect(Collectors.toList());
		assertEquals("number of edges", references.size(), gtGraph.getEdges().size());
		editorNodes.forEach(editorNode -> editorNode.getConstraints().stream() //
				.filter(c -> c instanceof Reference).map(r -> (Reference) r) // only References
				.forEach(reference -> checkEdge(editorNode, reference, gtGraph)));
	}

	private static void checkEdge(final Node editorNode, final Reference reference, final GTGraph gtGraph) {
		Node targetNode = reference.getTarget();
		Optional<GTEdge> gtEdge = gtGraph.getEdges().stream() //
				.filter(edge -> edge.getType().equals(reference.getType())) // correct type
				.filter(e -> editorNode.getName().equals(getNodeName(e.getSourceNode()))) // correct source
				.filter(e -> targetNode.getName().equals(getNodeName(e.getTargetNode()))) // correct target
				.findAny();
		assertTrue(gtEdge.isPresent());
	}

	private static String getNodeName(final GTNode gtNode) {
		if (null == gtNode) {
			return "";
		}
		return gtNode.getName();
	}
}
