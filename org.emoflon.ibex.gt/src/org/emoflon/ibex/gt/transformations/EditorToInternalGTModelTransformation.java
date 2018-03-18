package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.AttributeConstraint;
import org.emoflon.ibex.gt.editor.gT.Expression;
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;
import org.emoflon.ibex.gt.editor.gT.Node;
import org.emoflon.ibex.gt.editor.gT.Parameter;
import org.emoflon.ibex.gt.editor.gT.ParameterValue;
import org.emoflon.ibex.gt.editor.gT.Relation;
import org.emoflon.ibex.gt.editor.gT.Rule;

import GTLanguage.GTAttributeAssignment;
import GTLanguage.GTAttributeCondition;
import GTLanguage.GTAttributeConstraint;
import GTLanguage.GTEdge;
import GTLanguage.GTGraph;
import GTLanguage.GTLanguageFactory;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;

/**
 * Transformation from the editor file (which conforms to the GT.ecore
 * meta-model generated from the Xtext specification) to the internal GT model
 * (which conforms to the GTLanguage.ecore meta-model).
 */
public class EditorToInternalGTModelTransformation
		extends AbstractModelTransformation<GraphTransformationFile, GTRuleSet> {
	/**
	 * The rules transformed into the internal model.
	 */
	private List<GTRule> gtRules;

	@Override
	public GTRuleSet transform(final GraphTransformationFile editorModel) {
		Objects.requireNonNull(editorModel, "The editor model must not be null!");
		this.gtRules = new ArrayList<GTRule>();
		editorModel.getRules().forEach(editorRule -> this.transformRule(editorRule));

		GTRuleSet gtRuleSet = GTLanguageFactory.eINSTANCE.createGTRuleSet();
		gtRuleSet.getRules().addAll(gtRules.stream() //
				.sorted((p1, p2) -> p1.getName().compareTo(p2.getName())) //
				.collect(Collectors.toList()));
		return gtRuleSet;
	}

	/**
	 * Transforms an editor rule into a GTRule of the internal GT model.
	 * 
	 * @param editorRule
	 *            the editor rule, must not be <code>null</code>
	 */
	private void transformRule(final Rule editorRule) {
		Objects.requireNonNull(editorRule, "rule must not be null!");

		GTRule gtRule = GTLanguageFactory.eINSTANCE.createGTRule();
		gtRule.setName(editorRule.getName());
		gtRule.setAbstract(editorRule.isAbstract());
		gtRule.setExecutable(EditorToInternalModelUtils.hasOperatorOrReference(editorRule));

		editorRule.getParameters().forEach(editorParameter -> {
			gtRule.getParameters().add(this.transformParameter(editorParameter));
		});

		// Transform nodes, edges, attribute constraints.
		GTGraph gtGraph = GTLanguageFactory.eINSTANCE.createGTGraph();
		editorRule.getNodes().forEach(editorNode -> {
			gtGraph.getNodes().add(this.transformNode(editorNode, gtRule.getParameters()));
		});
		editorRule.getNodes().forEach(editorNode -> {
			gtGraph.getEdges().addAll(this.transformReferencesToEdges(editorNode, gtGraph.getNodes()));
		});
		gtRule.setGraph(gtGraph);

		this.gtRules.add(gtRule);
	}

	/**
	 * Transforms an editor node into a GTNode.
	 * 
	 * @param editorNode
	 *            the editor node, must not be <code>null</code>
	 * @param gtParameters
	 *            the rule parameters
	 * @return the GTNode
	 */
	private GTNode transformNode(final Node editorNode, final List<GTParameter> gtParameters) {
		Objects.requireNonNull(editorNode, "node must not be null!");
		GTNode gtNode = GTLanguageFactory.eINSTANCE.createGTNode();
		gtNode.setName(editorNode.getName());
		gtNode.setType(editorNode.getType());
		gtNode.setLocal(editorNode.getName().startsWith("_"));
		gtNode.setBindingType(EditorToInternalModelUtils.convertOperatorToBindingType(editorNode.getOperator()));

		editorNode.getAttributes().forEach(editorAttributeConstraint -> {
			if (editorAttributeConstraint.getRelation() == Relation.ASSIGNMENT) {
				gtNode.getAttributeAssignments()
						.add(this.transformAttributeAssignment(editorAttributeConstraint, gtParameters));
			} else {
				gtNode.getAttributeConditions()
						.add(this.transformAttributeCondition(editorAttributeConstraint, gtParameters));
			}
		});
		return gtNode;
	}

	private GTAttributeAssignment transformAttributeAssignment(final AttributeConstraint editorAttributeConstraint,
			final List<GTParameter> gtParameters) {
		GTAttributeAssignment gtAttributeAssignment = GTLanguageFactory.eINSTANCE.createGTAttributeAssignment();
		gtAttributeAssignment.setType(editorAttributeConstraint.getAttribute());
		this.setAttributeConstraintValue(gtAttributeAssignment, editorAttributeConstraint, gtParameters);
		return gtAttributeAssignment;
	}

	private GTAttributeCondition transformAttributeCondition(final AttributeConstraint editorAttributeConstraint,
			final List<GTParameter> gtParameters) {
		GTAttributeCondition gtAttributeCondition = GTLanguageFactory.eINSTANCE.createGTAttributeCondition();
		gtAttributeCondition.setType(editorAttributeConstraint.getAttribute());
		gtAttributeCondition
				.setRelation(EditorToInternalModelUtils.convertRelation(editorAttributeConstraint.getRelation()));
		this.setAttributeConstraintValue(gtAttributeCondition, editorAttributeConstraint, gtParameters);
		return gtAttributeCondition;
	}

	private void setAttributeConstraintValue(final GTAttributeConstraint gtAttributeConstraint,
			final AttributeConstraint editorAttributeConstraint, final List<GTParameter> gtParameters) {
		Expression editorValue = editorAttributeConstraint.getValue();
		if (editorValue instanceof ParameterValue) {
			String parameterName = ((ParameterValue) editorValue).getParameter().getName();
			Optional<GTParameter> gtParameter = EditorToInternalModelUtils.findParameterWithName(gtParameters,
					parameterName);
			if (!gtParameter.isPresent()) {
				this.logError("Could not find parameter " + parameterName + "!");
			}
			gtParameter.ifPresent(p -> gtAttributeConstraint.setValue(p));
		}
	}

	/**
	 * Transforms the edges of an editor node into GTEdges.
	 * 
	 * @param editorNode
	 *            the editor node to transform, must not be <code>null</code>
	 * @param gtNodes
	 *            the list of GTNodes, which maybe source and target nodes of the
	 *            edges to be created
	 * @return the list of GTEdges
	 */
	private List<GTEdge> transformReferencesToEdges(final Node editorNode, final List<GTNode> gtNodes) {
		Objects.requireNonNull(editorNode, "editor node must not be null!");
		Objects.requireNonNull(gtNodes, "node list must not be null!");
		List<GTEdge> gtEdges = new ArrayList<GTEdge>();
		editorNode.getReferences().forEach(reference -> {
			String sourceNodeName = editorNode.getName();
			String targetNodeName = reference.getTarget().getName();

			Optional<GTNode> gtSourceNode = EditorToInternalModelUtils.findGTNodeWithName(gtNodes, sourceNodeName);
			Optional<GTNode> gtTargetNode = EditorToInternalModelUtils.findGTNodeWithName(gtNodes, targetNodeName);
			if (!gtSourceNode.isPresent()) {
				this.logError("Could not find node " + sourceNodeName + "!");
			}
			if (!gtTargetNode.isPresent()) {
				this.logError("Could not find node " + targetNodeName + "!");
			}

			if (gtSourceNode.isPresent() && gtSourceNode.isPresent()) {
				GTEdge gtEdge = GTLanguageFactory.eINSTANCE.createGTEdge();
				gtEdge.setType(reference.getType());
				gtEdge.setName(sourceNodeName + "-" + gtEdge.getType().getName() + "-" + targetNodeName);
				gtEdge.setBindingType(EditorToInternalModelUtils.convertOperatorToBindingType(reference.getOperator()));
				gtSourceNode.ifPresent(node -> gtEdge.setSourceNode(node));
				gtTargetNode.ifPresent(node -> gtEdge.setTargetNode(node));
				gtEdges.add(gtEdge);
			}
		});
		return gtEdges;
	}

	/**
	 * Transforms an editor parameter into a GTParameter.
	 * 
	 * @param editorParameter
	 *            the editor parameter, must not be <code>null</code>
	 * @return the GTParameter
	 */
	private GTParameter transformParameter(final Parameter editorParameter) {
		Objects.requireNonNull(editorParameter, "name must not be null!");
		GTParameter gtParameter = GTLanguageFactory.eINSTANCE.createGTParameter();
		gtParameter.setName(editorParameter.getName());
		gtParameter.setType(editorParameter.getType());
		return gtParameter;
	}
}
