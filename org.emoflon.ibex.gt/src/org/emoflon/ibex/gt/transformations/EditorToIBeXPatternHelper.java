package org.emoflon.ibex.gt.transformations;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EDataType;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.ArithmeticCalculationExpression;
import org.emoflon.ibex.gt.editor.gT.EditorApplicationCondition;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeAssignment;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeConstraint;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorEnumExpression;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.gT.StochasticFunctionExpression;
import org.emoflon.ibex.gt.editor.utils.GTConditionHelper;
import org.emoflon.ibex.gt.editor.utils.GTEditorAttributeUtils;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue;
import org.moflon.core.utilities.EcoreUtils;


/**
 * Utility methods to transform editor patterns to IBeX Patterns.
 */
final public class EditorToIBeXPatternHelper {
	
	public static Collection<IBeXNode> getAllRuleNodes(IBeXRule rule) {
		Collection<IBeXNode> nodes = new LinkedList<>();
		IBeXContextPattern lhs = null;
		if(rule.getLhs() instanceof IBeXContextPattern) {
			lhs = (IBeXContextPattern) rule.getLhs();
		}else {
			lhs = ((IBeXContextAlternatives) rule.getLhs()).getContext();
		}
		nodes.addAll(lhs.getSignatureNodes());
		
		if(rule.getCreate() != null) {
			nodes.addAll(rule.getCreate().getCreatedNodes());
		}
		
		return nodes;
	}
	
	public static Collection<IBeXNode> getRuleContextNodes(IBeXRule rule) {
		Collection<IBeXNode> nodes = new LinkedList<>();
		IBeXContextPattern lhs = null;
		if(rule.getLhs() instanceof IBeXContextPattern) {
			lhs = (IBeXContextPattern) rule.getLhs();
		}else {
			lhs = ((IBeXContextAlternatives) rule.getLhs()).getContext();
		}
		
		nodes.addAll(lhs.getSignatureNodes());
		return nodes;
	}
	
	public static Collection<IBeXNode> getPatternNodes(IBeXContext pattern) {
		Collection<IBeXNode> nodes = new LinkedList<>();
		IBeXContextPattern context = null;
		if(pattern instanceof IBeXContextPattern) {
			context = (IBeXContextPattern) pattern;
		} else {
			context = ((IBeXContextAlternatives) pattern).getContext();
		}
		
		nodes.addAll(context.getSignatureNodes());
		return nodes;
	}
	
	public static Collection<IBeXArithmeticConstraint> getArithmeticConstraints(IBeXContext pattern) {
		Collection<IBeXArithmeticConstraint> constraints = new LinkedList<>();
		IBeXContextPattern context = null;
		if(pattern instanceof IBeXContextPattern) {
			context = (IBeXContextPattern) pattern;
		} else {
			context = ((IBeXContextAlternatives) pattern).getContext();		
		}
		constraints.addAll(context.getArithmeticConstraints());
		return constraints;
	}
	
	/**
	 * A comparator for editor attributes.
	 */
	public static final Comparator<EditorAttributeConstraint> sortAttributeConstraint = (a, b) -> {
		int compareAttributes = a.getRelation().getName().compareTo(b.getRelation().getName());
		if (compareAttributes != 0) {
			return compareAttributes;
		}

		return a.getRelation().compareTo(b.getRelation());
	};
	
	/**
	 * A comparator for editor attributes.
	 */
	public static final Comparator<EditorAttributeAssignment> sortAttributeAssignment = (a, b) -> {
		int compareAttributes = a.getAttribute().getName().compareTo(b.getAttribute().getName());
		return compareAttributes;
	};
	
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
		if(node1.equals(node2)) {
			throw new RuntimeException("Injectivity constraints can only be created for unequal pattern nodes!");
		}

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
	
	/**
	 * Returns the attribute assignments of the given node.
	 * 
	 * @param editorNode
	 *            the editor node
	 * @param filterCondition
	 *            the condition for filtering attributes
	 * @return the attribute assignments
	 */
	public static List<EditorAttributeAssignment> filterAttributes(final EditorNode editorNode,
			final Predicate<EditorAttributeAssignment> filterCondition) {
		return editorNode.getAttributes().stream() //
				.filter(filterCondition) //
				.sorted(sortAttributeAssignment) //
				.collect(Collectors.toList());
	}
	
	/**
	 * Converts the relation.
	 * 
	 * @param relation
	 *            the relation from the editor model
	 * @return the IBeXRelation
	 */
	public static IBeXRelation convertRelation(final EditorRelation relation) {
		return switch (relation) {
			case GREATER -> IBeXRelation.GREATER;
			case GREATER_OR_EQUAL -> IBeXRelation.GREATER_OR_EQUAL;
			case EQUAL -> IBeXRelation.EQUAL;
			case UNEQUAL -> IBeXRelation.UNEQUAL;
			case SMALLER -> IBeXRelation.SMALLER;
			case SMALLER_OR_EQUAL -> IBeXRelation.SMALLER_OR_EQUAL;
			default -> throw new IllegalArgumentException("Cannot convert relation: " + relation);
		};
	}
	
	/**
	 * Sets the attribute value for the given attribute to the enum expression.
	 * 
	 * @param editorExpression
	 *            the enum expression
	 * @return the IBeXEnumLiteral
	 */
	public static IBeXEnumLiteral convertAttributeValue(final EditorEnumExpression editorExpression) {
		IBeXEnumLiteral ibexEnumLiteral = IBeXPatternModelFactory.eINSTANCE.createIBeXEnumLiteral();
		ibexEnumLiteral.setLiteral(editorExpression.getLiteral());
		return ibexEnumLiteral;
	}
	
	/**
	 * Sets the attribute value for the given attribute to the literal expression.
	 * 
	 * @param editorAttribute
	 *            the attribute constraint of the editor model
	 * @return the IBeXConstant
	 */
	public static IBeXConstant convertAttributeValue(final EditorLiteralExpression editorExpression) {
		String s = editorExpression.getValue();
		Optional<Object> object = GTEditorAttributeUtils.convertEDataTypeStringToObject(editorExpression.getValue());
		if (object.isPresent()) {
			IBeXConstant ibexConstant = IBeXPatternModelFactory.eINSTANCE.createIBeXConstant();
			ibexConstant.setValue(object.get());
			ibexConstant.setStringValue(object.get().toString());
			return ibexConstant;
		} else {
			throw new IllegalArgumentException("Invalid attribute value: " + s);
		}
	}

	/**
	 * Sets the attribute value for the given attribute to the literal expression.
	 * 
	 * @param editorAttribute
	 *            the attribute constraint of the editor model
	 * @return the IBeXConstant
	 */
	public static IBeXConstant convertAttributeValue(final EditorLiteralExpression editorExpression,
			final EDataType type) {
		String s = editorExpression.getValue();
		Optional<Object> object = GTEditorAttributeUtils.convertEDataTypeStringToObject(type,
				editorExpression.getValue());
		if (object.isPresent()) {
			IBeXConstant ibexConstant = IBeXPatternModelFactory.eINSTANCE.createIBeXConstant();
			ibexConstant.setValue(object.get());
			ibexConstant.setStringValue(object.get().toString());
			return ibexConstant;
		} else {
			throw new IllegalArgumentException("Invalid attribute value: " + s);
		}
	}

	/**
	 * Sets the attribute value for the given attribute to the parameter expression.
	 * 
	 * @param editorExpression
	 *            the parameter expression
	 * @return the IBeXAttributeParameter
	 */
	public static IBeXAttributeParameter convertAttributeValue(final EditorParameterExpression editorExpression) {
		IBeXAttributeParameter parameter = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeParameter();
		parameter.setName(editorExpression.getParameter().getName());
		return parameter;
	}
	
	/**
	 * Sets the stochastic attribute which later generates a numeric value
	 * 
	 * @param stochasticExpression
	 * 			the stochastic expression
	 * @return the IBeXStochasticAttributeValue
	 */
	public static IBeXStochasticAttributeValue convertAttributeValue(final TransformationData data, final IBeXPattern ibexPattern, final StochasticFunctionExpression stochasticExpression) {
		IBeXStochasticAttributeValue value = IBeXPatternModelFactory.eINSTANCE.createIBeXStochasticAttributeValue();
		value.setFunction(EditorToStochasticExtensionHelper
				.transformStochasticFunction(data, ibexPattern, stochasticExpression));
		// the value of the enums from GTStochasticRange and OperatorRange need to be the same
		value.setRange(IBeXDistributionRange.get(stochasticExpression.getOperatorRange().getValue()));
		return value;
	}
	/**
	 * partly calculates and transforms the arithmetic expression
	 * 
	 * @param expression the arithmetic expression
	 * @return the IBeXArithmeticValue
	 */
	public static IBeXArithmeticValue convertAttributeValue(final TransformationData data, final IBeXPattern ibexPattern, final ArithmeticCalculationExpression expression) {
		IBeXArithmeticValue value= IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValue();		;
		value.setExpression(EditorToArithmeticExtensionHelper.transformToIBeXArithmeticExpression(data, ibexPattern, expression.getExpression()));
		return value;
	}
}
