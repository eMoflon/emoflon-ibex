package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorApplicationCondition;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
import org.emoflon.ibex.gt.editor.utils.GTConditionHelper;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.moflon.core.utilities.EcoreUtils;


/**
 * Utility methods to transform editor patterns to IBeX Patterns.
 */
final public class EditorToIBeXPatternHelper {

	/**
	 * Adds injectivity constraints to the pattern such that all nodes in the
	 * pattern must be disjoint.
	 * 
	 * @param ibexPattern
	 *            the context pattern
	 */
	public static void addInjectivityConstraints(final IBeXContextPattern ibexPattern) {
		List<IBeXNode> allNodes = IBeXPatternUtils.getAllNodes(ibexPattern);
		for (int i = 0; i < allNodes.size(); i++) {
			for (int j = i + 1; j < allNodes.size(); j++) {
				addInjectivityConstraintIfNecessary(ibexPattern, allNodes.get(i), allNodes.get(j));
			}
		}
	}

	/**
	 * Adds an injectivity constraint for the nodes if their types are compatible.
	 * 
	 * @param ibexPattern
	 *            the context pattern
	 * @param node1
	 *            the first node
	 * @param node2
	 *            the second node
	 */
	public static void addInjectivityConstraintIfNecessary(final IBeXContextPattern ibexPattern, final IBeXNode node1,
			final IBeXNode node2) {
		if (EcoreUtils.areTypesCompatible(node1.getType(), node2.getType())) {
			IBeXInjectivityConstraint nodePair = IBeXPatternModelFactory.eINSTANCE.createIBeXInjectivityConstraint();
			nodePair.getValues().add(node1);
			nodePair.getValues().add(node2);
			ibexPattern.getInjectivityConstraints().add(nodePair);
		}
	}

	/**
	 * Adds the nodes of the given map to the mappings of the invocation.
	 * 
	 * @param invocation
	 *            the invocation
	 * @param nodeMap
	 *            the mapping of nodes
	 */
	public static void addNodeMapping(final IBeXPatternInvocation invocation, final Map<IBeXNode, IBeXNode> nodeMap) {
		// sorting necessary for deterministic output
		nodeMap.keySet().stream().sorted(IBeXPatternUtils.sortByName).forEach(k -> {
			invocation.getMapping().put(k, nodeMap.get(k));
		});
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
	public static Map<IBeXNode, IBeXNode> determineNodeMapping(final IBeXContextPattern ibexPattern, final IBeXContextPattern invokedPattern) {
		Map<IBeXNode, IBeXNode> nodeMap = new HashMap<IBeXNode, IBeXNode>();
		for (final IBeXNode nodeInPattern : IBeXPatternUtils.getAllNodes(ibexPattern)) {
			IBeXPatternUtils.findIBeXNodeWithName(invokedPattern, nodeInPattern.getName())
					.ifPresent(nodeInInvokedPattern -> nodeMap.put(nodeInPattern, nodeInInvokedPattern));
		}
		return nodeMap;
	}
	
	/**
	 * Returns the application conditions referenced by the given condition ordered
	 * by the name of the pattern.
	 * 
	 * @param condition
	 *            the condition
	 * @return the application condition
	 */
	public static List<EditorApplicationCondition> getApplicationConditions(final EditorCondition condition) {
		return new GTConditionHelper(condition).getApplicationConditions().stream()
				.sorted((a, b) -> a.getPattern().getName().compareTo(b.getPattern().getName()))
				.collect(Collectors.toList());
	}
}
