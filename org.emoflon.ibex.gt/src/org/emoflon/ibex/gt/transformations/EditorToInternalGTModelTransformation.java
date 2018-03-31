package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorEnumExpression;
import org.emoflon.ibex.gt.editor.gT.EditorExpression;
import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.gT.Node;
import org.emoflon.ibex.gt.editor.gT.Parameter;
import org.emoflon.ibex.gt.editor.gT.Rule;
import org.emoflon.ibex.gt.editor.utils.GTEditorAttributeUtils;
import org.emoflon.ibex.gt.editor.utils.GTFlattener;

import GTLanguage.GTAttribute;
import GTLanguage.GTAttributeAssignment;
import GTLanguage.GTAttributeCondition;
import GTLanguage.GTEdge;
import GTLanguage.GTGraph;
import GTLanguage.GTLanguageFactory;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;
import IBeXLanguage.IBeXAttributeExpression;
import IBeXLanguage.IBeXAttributeParameter;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXEnumLiteral;
import IBeXLanguage.IBeXLanguageFactory;

/**
 * Transformation from the editor file (which conforms to the GT.ecore
 * meta-model generated from the Xtext specification) to the internal GT model
 * (which conforms to the GTLanguage.ecore meta-model).
 */
public class EditorToInternalGTModelTransformation extends AbstractModelTransformation<EditorGTFile, GTRuleSet> {
	/**
	 * The rules transformed into the internal model.
	 */
	private List<GTRule> gtRules = new ArrayList<GTRule>();

	@Override
	public GTRuleSet transform(final EditorGTFile editorModel) {
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
	 * @param editorRuleUnflattened
	 *            the editor rule, must not be <code>null</code>
	 */
	private void transformRule(final Rule editorRuleUnflattened) {
		Objects.requireNonNull(editorRuleUnflattened, "rule must not be null!");

		Rule editorRule = editorRuleUnflattened;
		if (!editorRuleUnflattened.getSuperRules().isEmpty()) {
			GTFlattener flattener = new GTFlattener(editorRuleUnflattened);
			editorRule = flattener.getFlattenedRule();

			if (flattener.hasErrors()) {
				flattener.getErrors().forEach(e -> this.logError(e));
				return;
			}
		}

		GTRule gtRule = GTLanguageFactory.eINSTANCE.createGTRule();
		gtRule.setName(editorRule.getName());
		gtRule.setAbstract(editorRule.isAbstract());
		boolean isExecutable = EditorToInternalGTModelUtils.hasCreatedOrDeletedNode(editorRule)
				|| EditorToInternalGTModelUtils.hasCreatedOrDeletedReference(editorRule)
				|| EditorToInternalGTModelUtils.hasAttributeAssignment(editorRule);
		gtRule.setExecutable(isExecutable);

		editorRule.getParameters().forEach(editorParameter -> {
			gtRule.getParameters().add(this.transformParameter(editorParameter));
		});

		// Transform nodes and edges.
		GTGraph gtGraph = GTLanguageFactory.eINSTANCE.createGTGraph();
		editorRule.getNodes().forEach(editorNode -> {
			gtGraph.getNodes().add(this.transformNode(editorNode));
		});
		editorRule.getNodes().forEach(editorNode -> {
			gtGraph.getEdges().addAll(this.transformReferencesToEdges(editorNode, gtGraph.getNodes()));
		});
		gtRule.setGraph(gtGraph);

		// Transform attribute assignments and constraints.
		editorRule.getNodes().forEach(editorNode -> {
			Optional<GTNode> gtNodeOptional = InternalGTModelUtils.findGTNodeWithName(gtGraph.getNodes(),
					editorNode.getName());
			GTNode gtNode = gtNodeOptional.get();
			editorNode.getAttributes().forEach(editorAttr -> {
				if (editorAttr.getRelation() == EditorRelation.ASSIGNMENT) {
					GTAttributeAssignment gtAttr = GTLanguageFactory.eINSTANCE.createGTAttributeAssignment();
					gtAttr.setType(editorAttr.getAttribute());
					this.setAttributeValue(gtAttr, editorAttr, gtRule.getParameters());
					gtNode.getAttributeAssignments().add(gtAttr);
				} else {
					GTAttributeCondition gtAttr = GTLanguageFactory.eINSTANCE.createGTAttributeCondition();
					gtAttr.setType(editorAttr.getAttribute());
					gtAttr.setRelation(EditorToInternalGTModelUtils.convertRelation(editorAttr.getRelation()));
					this.setAttributeValue(gtAttr, editorAttr, gtRule.getParameters());
					gtNode.getAttributeConditions().add(gtAttr);
				}
			});
		});

		this.gtRules.add(gtRule);
	}

	/**
	 * Transforms an editor node into a GTNode.
	 * 
	 * @param editorNode
	 *            the editor node, must not be <code>null</code>
	 * @return the GTNode
	 */
	private GTNode transformNode(final Node editorNode) {
		Objects.requireNonNull(editorNode, "node must not be null!");
		GTNode gtNode = GTLanguageFactory.eINSTANCE.createGTNode();
		gtNode.setName(editorNode.getName());
		gtNode.setType(editorNode.getType());
		gtNode.setLocal(editorNode.getName().startsWith("_"));
		gtNode.setBindingType(EditorToInternalGTModelUtils.convertOperatorToBindingType(editorNode.getOperator()));
		return gtNode;
	}

	/**
	 * Sets the attribute value for the given attribute constraint.
	 * 
	 * @param gtAttribute
	 *            the attribute constraint whose value shall be set
	 * @param editorAttribute
	 *            the attribute constraint of the editor model
	 * @param gtParameters
	 *            the parameters of rule (internal model)
	 */
	private void setAttributeValue(final GTAttribute gtAttribute, final EditorAttribute editorAttribute,
			final List<GTParameter> gtParameters) {
		EditorExpression editorValue = editorAttribute.getValue();
		if (editorValue instanceof EditorAttributeExpression) {
			EditorAttributeExpression editorAttributeExpression = (EditorAttributeExpression) editorValue;
			IBeXAttributeExpression gtAttributeExpression = IBeXLanguageFactory.eINSTANCE
					.createIBeXAttributeExpression();
			gtAttributeExpression.setNodeName(editorAttributeExpression.getNode().getName());
			gtAttributeExpression.setAttribute(editorAttributeExpression.getAttribute());
			gtAttribute.setValue(gtAttributeExpression);
		} else if (editorValue instanceof EditorLiteralExpression) {
			String s = ((EditorLiteralExpression) editorValue).getValue();
			Optional<Object> object = GTEditorAttributeUtils
					.convertEDataTypeStringToObject(gtAttribute.getType().getEAttributeType(), s);
			object.ifPresent(o -> {
				IBeXConstant gtConstant = IBeXLanguageFactory.eINSTANCE.createIBeXConstant();
				gtConstant.setValue(o);
				gtConstant.setStringValue(o.toString());
				gtAttribute.setValue(gtConstant);
			});
			if (!object.isPresent()) {
				this.logError("Invalid attribute value: " + s);
			}
		} else if (editorValue instanceof EditorEnumExpression) {
			EEnumLiteral literal = ((EditorEnumExpression) editorValue).getLiteral();
			IBeXEnumLiteral gtEnumLiteral = IBeXLanguageFactory.eINSTANCE.createIBeXEnumLiteral();
			gtEnumLiteral.setLiteral(literal);
			gtAttribute.setValue(gtEnumLiteral);
		} else if (editorValue instanceof EditorParameterExpression) {
			String parameterName = ((EditorParameterExpression) editorValue).getParameter().getName();
			Optional<GTParameter> gtParameter = InternalGTModelUtils.findParameterWithName(gtParameters, parameterName);
			if (!gtParameter.isPresent()) {
				this.logError("Could not find parameter " + parameterName + "!");
			}
			gtParameter.ifPresent(p -> {
				IBeXAttributeParameter parameter = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeParameter();
				parameter.setName(p.getName());
				gtAttribute.setValue(parameter);
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

			Optional<GTNode> gtSourceNode = InternalGTModelUtils.findGTNodeWithName(gtNodes, sourceNodeName);
			Optional<GTNode> gtTargetNode = InternalGTModelUtils.findGTNodeWithName(gtNodes, targetNodeName);
			if (!gtSourceNode.isPresent()) {
				this.logError("Could not find source node " + sourceNodeName + "!");
			}
			if (!gtTargetNode.isPresent()) {
				this.logError("Could not find target node " + targetNodeName + "!");
			}

			if (gtSourceNode.isPresent() && gtTargetNode.isPresent()) {
				GTEdge gtEdge = GTLanguageFactory.eINSTANCE.createGTEdge();
				gtEdge.setType(reference.getType());
				gtEdge.setName(sourceNodeName + "-" + gtEdge.getType().getName() + "-" + targetNodeName);
				gtEdge.setBindingType(
						EditorToInternalGTModelUtils.convertOperatorToBindingType(reference.getOperator()));
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
		Objects.requireNonNull(editorParameter, "The parameter must not be null!");

		GTParameter gtParameter = GTLanguageFactory.eINSTANCE.createGTParameter();
		gtParameter.setName(editorParameter.getName());
		gtParameter.setType(editorParameter.getType());
		return gtParameter;
	}
}
