package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.emoflon.ibex.gt.editor.gT.AttributeConstraint;
import org.emoflon.ibex.gt.editor.gT.BooleanConstant;
import org.emoflon.ibex.gt.editor.gT.DecimalConstant;
import org.emoflon.ibex.gt.editor.gT.EnumValue;
import org.emoflon.ibex.gt.editor.gT.Expression;
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;
import org.emoflon.ibex.gt.editor.gT.IntegerConstant;
import org.emoflon.ibex.gt.editor.gT.Node;
import org.emoflon.ibex.gt.editor.gT.Parameter;
import org.emoflon.ibex.gt.editor.gT.ParameterValue;
import org.emoflon.ibex.gt.editor.gT.Relation;
import org.emoflon.ibex.gt.editor.gT.Rule;
import org.emoflon.ibex.gt.editor.gT.StringConstant;

import GTLanguage.GTAttributeAssignment;
import GTLanguage.GTAttributeCondition;
import GTLanguage.GTAttributeConstraint;
import GTLanguage.GTBoolean;
import GTLanguage.GTDouble;
import GTLanguage.GTEdge;
import GTLanguage.GTEnumLiteral;
import GTLanguage.GTGraph;
import GTLanguage.GTInteger;
import GTLanguage.GTLanguageFactory;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;
import GTLanguage.GTParameterReference;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;
import GTLanguage.GTString;

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
	private List<GTRule> gtRules = new ArrayList<GTRule>();

	@Override
	public GTRuleSet transform(final GraphTransformationFile editorModel) {
		Objects.requireNonNull(editorModel, "The editor model must not be null!");
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
		gtRule.setExecutable(EditorToInternalModelUtils.hasOperatorNodeOrReference(editorRule));

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

		// Transform the attribute constraints.
		editorNode.getAttributes().forEach(editorAttr -> {
			if (editorAttr.getRelation() == Relation.ASSIGNMENT) {
				GTAttributeAssignment gtAttr = GTLanguageFactory.eINSTANCE.createGTAttributeAssignment();
				gtAttr.setType(editorAttr.getAttribute());
				this.setAttributeConstraintValue(gtAttr, editorAttr, gtParameters);
				gtNode.getAttributeAssignments().add(gtAttr);
			} else {
				GTAttributeCondition gtAttr = GTLanguageFactory.eINSTANCE.createGTAttributeCondition();
				gtAttr.setType(editorAttr.getAttribute());
				gtAttr.setRelation(EditorToInternalModelUtils.convertRelation(editorAttr.getRelation()));
				this.setAttributeConstraintValue(gtAttr, editorAttr, gtParameters);
				gtNode.getAttributeConditions().add(gtAttr);
			}
		});
		return gtNode;
	}

	/**
	 * Sets the attribute value for the given attribute constraint.
	 * 
	 * @param gtAttributeConstraint
	 *            the attribute constraint whose value shall be set
	 * @param editorAttributeConstraint
	 *            the attribute constraint of the editor model
	 * @param gtParameters
	 *            the parameters of rule (internal model)
	 */
	private void setAttributeConstraintValue(final GTAttributeConstraint gtAttributeConstraint,
			final AttributeConstraint editorAttributeConstraint, final List<GTParameter> gtParameters) {
		Expression editorValue = editorAttributeConstraint.getValue();
		if (editorValue instanceof BooleanConstant) {
			GTBoolean gtBoolean = GTLanguageFactory.eINSTANCE.createGTBoolean();
			String bValue = ((BooleanConstant) editorValue).getValue();
			switch (bValue) {
			case "false":
				gtBoolean.setValue(false);
				break;
			case "true":
				gtBoolean.setValue(true);
				break;
			default:
				this.logError("Invalid Boolean value: " + bValue);
				break;
			}
			gtAttributeConstraint.setValue(gtBoolean);
		} else if (editorValue instanceof DecimalConstant) {
			String dValue = ((DecimalConstant) editorValue).getValue();
			try {
				double d = Double.parseDouble(dValue);
				GTDouble gtDouble = GTLanguageFactory.eINSTANCE.createGTDouble();
				gtDouble.setValue(d);
				gtAttributeConstraint.setValue(gtDouble);
			} catch (NumberFormatException e) {
				this.logError("Invalid double value: " + dValue);
			}
		} else if (editorValue instanceof IntegerConstant) {
			String iValue = ((IntegerConstant) editorValue).getValue();
			try {
				int i = Integer.parseInt(iValue);
				GTInteger gtInteger = GTLanguageFactory.eINSTANCE.createGTInteger();
				gtInteger.setValue(i);
				gtAttributeConstraint.setValue(gtInteger);
			} catch (NumberFormatException e) {
				this.logError("Invalid integer value: " + iValue);
			}
		} else if (editorValue instanceof StringConstant) {
			String sValue = ((StringConstant) editorValue).getValue();
			GTString gtString = GTLanguageFactory.eINSTANCE.createGTString();
			gtString.setValue(sValue);
			gtAttributeConstraint.setValue(gtString);
		} else if (editorValue instanceof EnumValue) {
			EEnumLiteral literal = ((EnumValue) editorValue).getLiteral();
			GTEnumLiteral gtEnumLiteral = GTLanguageFactory.eINSTANCE.createGTEnumLiteral();
			gtEnumLiteral.setLiteral(literal);
			gtAttributeConstraint.setValue(gtEnumLiteral);
		} else if (editorValue instanceof ParameterValue) {
			String parameterName = ((ParameterValue) editorValue).getParameter().getName();
			Optional<GTParameter> gtParameter = EditorToInternalModelUtils.findParameterWithName(gtParameters,
					parameterName);
			if (!gtParameter.isPresent()) {
				this.logError("Could not find parameter " + parameterName + "!");
			}
			gtParameter.ifPresent(p -> {
				GTParameterReference paramReference = GTLanguageFactory.eINSTANCE.createGTParameterReference();
				paramReference.setParameter(p);
				gtAttributeConstraint.setValue(paramReference);
			});
		} else {
			this.logError("Invalid attribute value: " + editorValue);
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
