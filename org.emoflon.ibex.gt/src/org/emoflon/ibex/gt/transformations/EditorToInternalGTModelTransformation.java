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
 * Transformation from the editor model to an representation of the Graph
 * Transformation API.
 */
public class EditorToInternalGTModelTransformation extends AbstractModelTransformation<EditorGTFile, GTRuleSet> {
	/**
	 * The rules transformed into the internal model.
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
	 * Transforms an editor rule into a GTRule of the internal GT model.
	 * 
	 * @param unflattenedEditorPattern
	 *            the editor rule, must not be <code>null</code>
	 */
	private void transformRule(final EditorPattern unflattenedEditorPattern) {
		Objects.requireNonNull(unflattenedEditorPattern, "pattern must not be null!");

		if (unflattenedEditorPattern.isAbstract()) {
			return;
		}

		EditorPattern editorRule = unflattenedEditorPattern;
		if (!unflattenedEditorPattern.getSuperPatterns().isEmpty()) {
			GTFlattener flattener = new GTFlattener(unflattenedEditorPattern);
			editorRule = flattener.getFlattenedPattern();

			if (flattener.hasErrors()) {
				flattener.getErrors().forEach(e -> this.logError(e));
				return;
			}
		}

		GTRule gtRule = GTLanguageFactory.eINSTANCE.createGTRule();
		gtRule.setName(editorRule.getName());
		gtRule.setExecutable(editorRule.getType() == EditorPatternType.RULE);

		for (EditorNode editorNode : editorRule.getNodes()) {
			GTNode gtNode = this.transformNode(editorNode);
			gtRule.getNodes().add(gtNode);
			if (!EditorModelUtils.isLocal(editorNode)) {
				// Only context and deleted nodes can be bound on the rule.
				if (editorNode.getOperator() != EditorOperator.CREATE) {
					gtRule.getRuleNodes().add(gtNode);
				}
			}
		}

		for (EditorParameter editorParameter : editorRule.getParameters()) {
			gtRule.getParameters().add(this.transformParameter(editorParameter));
		}

		this.gtRules.add(gtRule);
	}

	/**
	 * Transforms an editor node into a GTNode.
	 * 
	 * @param editorNode
	 *            the editor node, must not be <code>null</code>
	 * @return the GTNode
	 */
	private GTNode transformNode(final EditorNode editorNode) {
		Objects.requireNonNull(editorNode, "node must not be null!");
		GTNode gtNode = GTLanguageFactory.eINSTANCE.createGTNode();
		gtNode.setName(editorNode.getName());
		gtNode.setType(editorNode.getType());
		return gtNode;
	}

	/**
	 * Transforms an editor parameter into a GTParameter.
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
