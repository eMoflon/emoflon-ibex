package org.emoflon.ibex.gt.transformations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.utils.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorAndCondition;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorConditionExpression;
import org.emoflon.ibex.gt.editor.gT.EditorConditionReference;
import org.emoflon.ibex.gt.editor.gT.EditorEnforce;
import org.emoflon.ibex.gt.editor.gT.EditorForbid;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;

import IBeXLanguage.IBeXContext;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternInvocation;

/**
 * Helper to transform conditions from the editor to the IBeX model.
 */
public class EditorToIBeXConditionHelper {
	/**
	 * The transformation.
	 */
	private final EditorToIBeXPatternTransformation transformation;

	/**
	 * The context pattern.
	 */
	private final IBeXContextPattern ibexPattern;

	/**
	 * Creates a new EditorToIBeXConditionHelper.
	 * 
	 * @param transformation
	 *            the transformation used to log errors and get patterns
	 * @param ibexPattern
	 *            the pattern where the pattern invocations for the conditions shall
	 *            be added
	 */
	public EditorToIBeXConditionHelper(final EditorToIBeXPatternTransformation transformation,
			final IBeXContextPattern ibexPattern) {
		this.transformation = transformation;
		this.ibexPattern = ibexPattern;
	}

	/**
	 * Transforms the condition of the editor pattern.
	 */
	public void transformCondition(final EditorCondition condition) {
		Objects.requireNonNull(condition, "The condition must not be null!");

		if (condition.getExpression() == null) {
			transformation.logError("One condition for pattern %s could not be found.", ibexPattern.getName());
			return;
		}
		transformCondition(condition.getExpression());
	}

	/**
	 * Transforms a single condition.
	 * 
	 * @param expression
	 *            the editor condition to transform
	 */
	private void transformCondition(final EditorConditionExpression expression) {
		Objects.requireNonNull(expression, "The expression of the condition must not be null!");

		if (expression instanceof EditorEnforce) {
			transformEnforcePattern((EditorEnforce) expression);
		} else if (expression instanceof EditorForbid) {
			transformForbidPattern((EditorForbid) expression);
		} else if (expression instanceof EditorConditionReference) {
			transformCondition(((EditorConditionReference) expression).getCondition().getExpression());
		} else if (expression instanceof EditorAndCondition) {
			transformCondition(((EditorAndCondition) expression).getLeft());
			transformCondition(((EditorAndCondition) expression).getRight());
		} else {
			throw new IllegalArgumentException("Invalid condition expression " + expression);
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
	 * Creates a pattern invocation for the given editor pattern mapping nodes of
	 * the same name.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param invocationType
	 *            <code>true</code> for positive invocation, <code>false</code> for
	 *            negative invocation
	 */
	private void transformPattern(final EditorPattern editorPattern, final boolean invocationType) {
		IBeXContext contextPattern = transformation.getContextPattern(editorPattern);
		if (!(contextPattern instanceof IBeXContextPattern)) {
			transformation.logError("%s not allowed in condition.", editorPattern.getName());
			return;
		}
		IBeXContextPattern invokedPattern = (IBeXContextPattern) contextPattern;

		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(invocationType);
		ibexPattern.getInvocations().add(invocation);

		Map<IBeXNode, IBeXNode> nodeMap = determineNodeMapping(invokedPattern);
		if (nodeMap.size() == invokedPattern.getSignatureNodes().size()) {
			invocation.setInvokedPattern(invokedPattern);
			EditorToIBeXPatternHelper.addNodeMapping(invocation, nodeMap);
		} else { // not all signature nodes are mapped.
			transformContextPatternForSignature(editorPattern, nodeMap).ifPresent(p -> {
				invocation.setInvokedPattern(p);
				EditorToIBeXPatternHelper.addNodeMapping(invocation, determineNodeMapping(p));
			});
		}
	}

	/**
	 * Check which nodes from the context pattern can be mapped to the invoked
	 * pattern based on the equal name convention.
	 * 
	 * @param invokedPattern
	 *            the pattern invoked by the context pattern
	 * @return the mapping between nodes of the context pattern to nodes of the
	 *         invoked pattern
	 */
	private Map<IBeXNode, IBeXNode> determineNodeMapping(final IBeXContextPattern invokedPattern) {
		Map<IBeXNode, IBeXNode> nodeMap = new HashMap<IBeXNode, IBeXNode>();
		for (final IBeXNode nodeInPattern : IBeXPatternUtils.getAllNodes(ibexPattern)) {
			IBeXPatternUtils.findIBeXNodeWithName(invokedPattern, nodeInPattern.getName())
					.ifPresent(nodeInInvokedPattern -> nodeMap.put(nodeInPattern, nodeInInvokedPattern));
		}
		return nodeMap;
	}

	/**
	 * Creates a context pattern for the given editor pattern which has the
	 * signature nodes of the given map. All other nodes will become local nodes.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param nodeMap
	 *            the node mapping
	 * @return the IBeXContextPattern if it exists
	 */
	private Optional<IBeXContextPattern> transformContextPatternForSignature(final EditorPattern editorPattern,
			final Map<IBeXNode, IBeXNode> nodeMap) {
		List<String> signatureNodeNames = nodeMap.values().stream() //
				.map(n -> n.getName()) //
				.collect(Collectors.toList());
		String patternName = editorPattern.getName() + "-CONDITION-"
				+ signatureNodeNames.stream().collect(Collectors.joining(","));

		return transformation.getFlattenedPattern(editorPattern) //
				.map(p -> transformation.transformToContextPattern(p, patternName,
						editorNode -> !signatureNodeNames.contains(editorNode.getName())));
	}
}
