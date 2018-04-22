package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorParameter;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorPatternType;
import org.emoflon.ibex.gt.editor.utils.GTFlattener;

import GTLanguage.GTLanguageFactory;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;

/**
 * Transformation from the editor model to an representation of the GT API.
 */
public class EditorToGTModelTransformation extends AbstractModelTransformation<EditorGTFile, GTRuleSet> {
	/**
	 * The rules transformed into the GT API model.
	 */
	private List<GTRule> gtRules = new ArrayList<GTRule>();

	@Override
	public GTRuleSet transform(final EditorGTFile editorModel) {
		Objects.requireNonNull(editorModel, "The editor model must not be null!");

		editorModel.getPatterns().forEach(editorRule -> transformRule(editorRule));

		GTRuleSet gtRuleSet = GTLanguageFactory.eINSTANCE.createGTRuleSet();
		gtRuleSet.getRules().addAll(gtRules.stream() //
				.sorted((p1, p2) -> p1.getName().compareTo(p2.getName())) //
				.collect(Collectors.toList()));
		return gtRuleSet;
	}

	/**
	 * Transforms an editor rule into a GTRule of the GT API model.
	 * 
	 * @param unflattenedEditorPattern
	 *            the editor rule, must not be <code>null</code>
	 */
	private void transformRule(final EditorPattern unflattenedEditorPattern) {
		Objects.requireNonNull(unflattenedEditorPattern, "The pattern must not be null!");

		if (unflattenedEditorPattern.isAbstract()) {
			return;
		}

		EditorPattern editorPattern = unflattenedEditorPattern;
		if (!unflattenedEditorPattern.getSuperPatterns().isEmpty()) {
			GTFlattener flattener = new GTFlattener(unflattenedEditorPattern);
			editorPattern = flattener.getFlattenedPattern();

			if (flattener.hasErrors()) {
				flattener.getErrors().forEach(e -> this.logError(e));
				return;
			}
		}

		GTRule gtRule = GTLanguageFactory.eINSTANCE.createGTRule();
		gtRule.setName(editorPattern.getName());
		gtRule.setExecutable(editorPattern.getType() == EditorPatternType.RULE);
		transformNodes(editorPattern, gtRule);
		transformParameters(editorPattern, gtRule);

		this.gtRules.add(gtRule);
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
		for (EditorNode editorNode : editorPattern.getNodes()) {
			if (!EditorModelUtils.isLocal(editorNode)) {
				GTNode gtNode = this.transformNode(editorNode);
				gtRule.getNodes().add(gtNode);

				// Only context and deleted nodes can be bound on the rule.
				if (editorNode.getOperator() != EditorOperator.CREATE) {
					gtRule.getRuleNodes().add(gtNode);
				}
			}
		}
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
			gtRule.getParameters().add(this.transformParameter(editorParameter));
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
}
