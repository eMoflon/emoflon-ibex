package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.SGTPatternModel.GTAttribute;
import org.emoflon.ibex.gt.SGTPatternModel.GTRelation;
import org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelFactory;
import org.emoflon.ibex.gt.editor.gT.ArithmeticCalculationExpression;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorParameter;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorPatternType;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.utils.GTCommentExtractor;

import GTLanguage.GTArithmeticConstraint;
import GTLanguage.GTLanguageFactory;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;

/**
 * Transformation from the editor model to an representation of the GT API.
 */
public class EditorToGTModelTransformation extends AbstractEditorModelTransformation<GTRuleSet> {
	/**
	 * The rules transformed into the GT API model.
	 */
	private List<GTRule> gtRules = new ArrayList<GTRule>();

	@Override
	public GTRuleSet transform(final EditorGTFile editorModel) {
		Objects.requireNonNull(editorModel, "The editor model must not be null!");

		editorModel.getPatterns().forEach(editorRule -> transformPattern(editorRule));

		GTRuleSet gtRuleSet = GTLanguageFactory.eINSTANCE.createGTRuleSet();
		gtRuleSet.getRules().addAll(gtRules.stream() //
				.sorted((p1, p2) -> p1.getName().compareTo(p2.getName())) //
				.collect(Collectors.toList()));
		return gtRuleSet;
	}

	/**
	 * Transforms an editor rule into a GTRule of the GT API model.
	 * 
	 * @param editorPattern
	 *            the editor rule, must not be <code>null</code>
	 */
	private void transformPattern(final EditorPattern editorPattern) {
		Objects.requireNonNull(editorPattern, "The pattern must not be null!");

		if (editorPattern.isAbstract()) {
			return;
		}
		calcFlattenedPattern(editorPattern, this::logError);
		addRuleForFlattenedPattern(getFlattenedPattern(editorPattern, this::logError), GTCommentExtractor.getComment(editorPattern));
	}

	/**
	 * Adds a GTRule for the given flattened pattern.
	 * 
	 * @param flattenedPattern
	 *            the flattened editor pattern
	 * @param documentation
	 *            the documentation
	 */
	private void addRuleForFlattenedPattern(final EditorPattern flattenedPattern, final String documentation) {
		GTRule gtRule = GTLanguageFactory.eINSTANCE.createGTRule();
		gtRule.setName(flattenedPattern.getName());
		gtRule.setDocumentation(documentation);
		gtRule.setExecutable(flattenedPattern.getType() == EditorPatternType.RULE);
		gtRule.setProbability(EditorToStochasticExtensionHelper
				.transformToGTProbability(flattenedPattern));
		transformNodes(flattenedPattern, gtRule);
		transformParameters(flattenedPattern, gtRule);
		transformConstraints(flattenedPattern, gtRule);

		gtRules.add(gtRule);
	}

	/**
	 * Transforms all nodes of the editor pattern to GTNodes and adds them to the
	 * given rule.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param gtRule
	 *            the GTRule
	 */
	private void transformNodes(final EditorPattern editorPattern, final GTRule gtRule) {
		editorPattern.getNodes().stream().filter(n -> !EditorModelUtils.isLocal(n)).forEach(editorNode -> {
			GTNode gtNode = transformNode(editorNode);
			gtRule.getNodes().add(gtNode);

			// Only context and deleted nodes can be bound on the rule.
			if (editorNode.getOperator() != EditorOperator.CREATE) {
				gtRule.getRuleNodes().add(gtNode);
			}
		});
	}

	/**
	 * Transforms an editor node to a GTNode.
	 * 
	 * @param editorNode
	 *            the editor node, must not be <code>null</code>
	 * @return the GTNode
	 */
	private GTNode transformNode(final EditorNode editorNode) {
		Objects.requireNonNull(editorNode, "The node must not be null!");

		GTNode gtNode = GTLanguageFactory.eINSTANCE.createGTNode();
		gtNode.setName(editorNode.getName());
		gtNode.setType(editorNode.getType());
		return gtNode;
	}

	/**
	 * Transforms all parameters of the editor pattern to GTParameters and adds them
	 * to the given rule.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param gtRule
	 *            the GTRule
	 */
	private void transformParameters(final EditorPattern editorPattern, final GTRule gtRule) {
		for (EditorParameter editorParameter : editorPattern.getParameters()) {
			gtRule.getParameters().add(transformParameter(editorParameter));
		}
	}

	/**
	 * Transforms an editor parameter to a GTParameter.
	 * 
	 * @param editorParameter
	 *            the editor parameter, must not be <code>null</code>
	 * @return the GTParameter
	 */
	private GTParameter transformParameter(final EditorParameter editorParameter) {
		Objects.requireNonNull(editorParameter, "The parameter must not be null!");

		GTParameter gtParameter = GTLanguageFactory.eINSTANCE.createGTParameter();
		gtParameter.setName(editorParameter.getName());
		gtParameter.setType(editorParameter.getType());
		return gtParameter;
	}	
	
	/**
	 * Transforms the arithmetic expressions to rule constraints; only used when context patterns
	 * @param editorPattern
	 * @param gtRule
	 */
	private void transformConstraints(final EditorPattern editorPattern, final GTRule gtRule) {
		for(final EditorNode node: editorPattern.getNodes()) {
			for(final EditorAttribute attribute: node.getAttributes()) {
				if(attribute.getValue() instanceof ArithmeticCalculationExpression && attribute.getRelation() != EditorRelation.ASSIGNMENT) {
					GTAttribute gtAttribute = SGTPatternModelFactory.eINSTANCE.createGTAttribute();
					gtAttribute.setName(node.getName());
					gtAttribute.setAttribute(attribute.getAttribute());
					gtAttribute.setType(node.getType());
					GTArithmeticConstraint constraint = GTLanguageFactory.eINSTANCE.createGTArithmeticConstraint();
					constraint.setParameter(gtAttribute);
					constraint.setRelation(convertRelation(attribute.getRelation()));
					constraint.setExpression(EditorToArithmeticExtensionHelper
							.transformToGTArithmetics(((ArithmeticCalculationExpression) attribute.getValue()).getExpression()));
					gtRule.getConstraints().add(constraint);
				}
			}
		}
	}
	/**
	 * private method for the arithmeticConstraints
	 */
	private static GTRelation convertRelation(final EditorRelation relation) {
		switch (relation) {
		case GREATER:
			return GTRelation.GREATER;
		case GREATER_OR_EQUAL:
			return GTRelation.GREATER_OR_EQUAL;
		case EQUAL:
			return GTRelation.EQUAL;
		case UNEQUAL:
			return GTRelation.UNEQUAL;
		case SMALLER:
			return GTRelation.SMALLER;
		case SMALLER_OR_EQUAL:
			return GTRelation.SMALLER_OR_EQUAL;
		default:
			throw new IllegalArgumentException("Cannot convert relation: " + relation);
		}
	}
}
