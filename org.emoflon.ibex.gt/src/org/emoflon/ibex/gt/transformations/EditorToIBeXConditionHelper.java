package org.emoflon.ibex.gt.transformations;

import java.util.Optional;

import org.emoflon.ibex.common.utils.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorAndCondition;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorConditionExpression;
import org.emoflon.ibex.gt.editor.gT.EditorConditionReference;
import org.emoflon.ibex.gt.editor.gT.EditorEnforce;
import org.emoflon.ibex.gt.editor.gT.EditorForbid;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternInvocation;

/**
 * Helper to transform conditions from the editor model to the IBeXPatterns.
 */
public class EditorToIBeXConditionHelper {
	/**
	 * The transformation
	 */
	private final EditorToIBeXPatternTransformation transformation;

	/**
	 * The editor pattern.
	 */
	private final EditorPattern editorPattern;

	/**
	 * The context pattern.
	 */
	private final IBeXContextPattern ibexPattern;

	/**
	 * Creates a new EditorToIBeXConditionHelper.
	 * 
	 * @param transformation
	 *            the transformation used to log errors and get patterns
	 * @param editorPattern
	 *            the editor pattern whose conditions to transform
	 * @param ibexPattern
	 *            the pattern where the pattern invocations shall be added
	 */
	public EditorToIBeXConditionHelper(final EditorToIBeXPatternTransformation transformation,
			final EditorPattern editorPattern, final IBeXContextPattern ibexPattern) {
		this.transformation = transformation;
		this.editorPattern = editorPattern;
		this.ibexPattern = ibexPattern;
	}

	/**
	 * Transforms the conditions of the editor pattern.
	 */
	public void transformConditions() {
		for (EditorCondition editorCondition : editorPattern.getConditions()) {
			transformCondition(editorCondition.getExpression());
		}
	}

	/**
	 * Transforms a single condition.
	 * 
	 * @param condition
	 *            the editor condition to transform
	 */
	private void transformCondition(final EditorConditionExpression condition) {
		if (condition instanceof EditorEnforce) {
			transformEnforcePattern((EditorEnforce) condition);
		} else if (condition instanceof EditorForbid) {
			transformForbidPattern((EditorForbid) condition);
		} else if (condition instanceof EditorConditionReference) {
			transformCondition(((EditorConditionReference) condition).getCondition().getExpression());
		} else if (condition instanceof EditorAndCondition) {
			transformCondition(((EditorAndCondition) condition).getLeft());
			transformCondition(((EditorAndCondition) condition).getRight());
		} else {
			System.out.println(condition);
		}
	}

	/**
	 * Transforms the enforce condition (PAC) to a positive pattern invocation.
	 * 
	 * @param enforce
	 *            the enforce condition
	 */
	private void transformEnforcePattern(final EditorEnforce enforce) {
		transformPattern(enforce.getPattern(), true);
	}

	/**
	 * Transforms the forbid condition (NAC) to a negative pattern invocation.
	 * 
	 * @param forbid
	 *            the forbid condition
	 */
	private void transformForbidPattern(final EditorForbid forbid) {
		transformPattern(forbid.getPattern(), false);
	}

	/**
	 * Creates a pattern invocation for the given editor pattern.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param invocationType
	 *            <code>true</code> for positive invocation, <code>false</code> for
	 *            negative invocation
	 */
	private void transformPattern(final EditorPattern editorPattern, final boolean invocationType) {
		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(invocationType);
		invocation.setInvokedBy(ibexPattern);

		IBeXContextPattern invokedPattern = transformation.getContextPattern(editorPattern);
		invocation.setInvokedPattern(invokedPattern);

		// Map nodes of the same name.
		for (IBeXNode nodeInPattern : IBeXPatternUtils.getAllNodes(ibexPattern)) {
			Optional<IBeXNode> nodeInInvokedPattern = IBeXPatternUtils.findIBeXNodeWithName(invokedPattern,
					nodeInPattern.getName());
			nodeInInvokedPattern.ifPresent(n -> invocation.getMapping().put(nodeInPattern, n));
		}

		ibexPattern.getInvocations().add(invocation);
	}
}
