package org.emoflon.ibex.tgg.util;


import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;
import org.emoflon.ibex.common.slimgt.slimGT.NodeAttributeExpression;
import org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;


public class TGGModelUtils {

	public static List<TGGNode> getNodesByOperator(TGGRule rule, BindingType type) {
		Objects.requireNonNull(rule, "The rule must not be null!");
		return rule.getNodes().stream() //
				.filter(n -> type.equals(n.getBindingType())).sorted((a, b) -> a.getName().compareTo(b.getName())) //
				.collect(Collectors.toList());
	}

	public static List<TGGEdge> getEdgesByOperator(TGGRule rule, BindingType type) {
		Objects.requireNonNull(rule, "The rule must not be null!");
		return rule.getEdges().stream() //
				.filter(n -> type.equals(n.getBindingType())) //
				.sorted((a, b) -> a.getName().compareTo(b.getName())) //
				.collect(Collectors.toList());
	}

	public static List<TGGEdge> getEdgesByDomain(TGGRule rule, DomainType type) {
		Objects.requireNonNull(rule, "The rule must not be null!");
		return rule.getEdges().stream() //
				.filter(n -> type.equals(n.getDomainType())).sorted((a, b) -> a.getName().compareTo(b.getName())) //
				.collect(Collectors.toList());
	}

	public static List<TGGNode> getNodesByDomain(TGGRule rule, DomainType type) {
		Objects.requireNonNull(rule, "The rule must not be null!");
		return rule.getNodes().stream() //
				.filter(n -> type.equals(n.getDomainType())).sorted((a, b) -> a.getName().compareTo(b.getName())) //
				.collect(Collectors.toList());
	}

	/**
	 * Checks whether the rule is an axiom
	 * 
	 * @param rule The rule to check
	 * @return true if the rule only contains green nodes
	 */
	public static boolean ruleIsAxiom(TGGRule rule) {
		return rule.getNodes().stream().allMatch(n -> n.getBindingType() == BindingType.CREATE);
	}

	public static Collection<TGGNode> getNodesByOperatorAndDomain(TGGRule rule, BindingType type, DomainType domain) {
		return getNodesByOperator(rule, type)//
				.stream()//
				.filter(n -> n.getDomainType().equals(domain))//
				.collect(Collectors.toList());
	}

	public static String getMarkerTypeName(String ruleName) {
		return "ProtocolNode_" + ruleName ;
	}

	public static String getMarkerRefName(BindingType type, DomainType domain, String markedOVName) {
		return getMarkerRefNamePrefix(type, domain) + markedOVName;
	}

	public static String getMarkerRefNamePrefix(BindingType type, DomainType domain) {
		return type.getLiteral() + "__" + domain.getLiteral() + "__";
	}

	public static Collection<? extends TGGEdge> getEdgesByOperatorAndDomain(TGGRule rule, BindingType type, DomainType domain) {
		return getEdgesByOperator(rule, type)//
				.stream()//
				.filter(e -> e.getDomainType().equals(domain))//
				.collect(Collectors.toList());
	}

//	public static boolean isOfDomain(NAC nac, DomainType domain) {
//		return nac.getNodes().stream().anyMatch(n -> n.getDomainType().equals(domain));
//	}

	public static DomainType oppositeOf(DomainType type) {
		switch (type) {
		case SOURCE:
			return DomainType.TARGET;
		case TARGET:
			return DomainType.SOURCE;
		case CORRESPONDENCE:
			return DomainType.CORRESPONDENCE;
		default:
			return null;
		}
	}

	public static BooleanExpression transformAttributeAssignmentToAttributeCondition(IBeXAttributeAssignment attributeAssignment) {
		var attributeExpression = IBeXCoreArithmeticFactory.eINSTANCE.createRelationalExpression();

		IBeXAttributeValue lhsValue = IBeXCoreModelFactory.eINSTANCE.createIBeXAttributeValue();
		lhsValue.setNode(attributeAssignment.getNode());
		lhsValue.setAttribute(attributeAssignment.getAttribute());

		ValueExpression rhsValue = EcoreUtil.copy(attributeAssignment.getValue());

		attributeExpression.setLhs(lhsValue);
		attributeExpression.setOperator(RelationalOperator.EQUAL);
		attributeExpression.setRhs(rhsValue);
		
		((TGGNode) attributeAssignment.getNode()).getReferencedByConditions().add(attributeExpression);
		if (rhsValue instanceof IBeXAttributeValue attributeValue)
			((TGGNode) attributeValue.getNode()).getReferencedByConditions().add(attributeExpression);

		return attributeExpression;
	}
	
	public static IBeXAttributeValue getOperandWithAttributeValue(BooleanExpression expression, TGGNode referencedNode) {
		if (!(expression instanceof RelationalExpression relationalExpression))
			return null;
		if (relationalExpression.getLhs() instanceof IBeXAttributeValue leftAttrValue && leftAttrValue.getNode().equals(referencedNode))
			return leftAttrValue;
		if (relationalExpression.getRhs() instanceof IBeXAttributeValue rightAttrValue && rightAttrValue.getNode().equals(referencedNode))
			return rightAttrValue;
		return null;
	}

	public static void setOperationType(IBeXNode node, BindingType binding) {
		switch (binding) {
			case CREATE -> node.setOperationType(IBeXOperationType.CREATION);
			case DELETE -> node.setOperationType(IBeXOperationType.DELETION);
			case CONTEXT, RELAXED, NEGATIVE -> node.setOperationType(IBeXOperationType.CONTEXT);
			default -> {}
		}
	}

	public static void setOperationType(IBeXEdge edge, BindingType binding) {
		switch (binding) {
			case CREATE -> edge.setOperationType(IBeXOperationType.CREATION);
			case DELETE -> edge.setOperationType(IBeXOperationType.DELETION);
			case CONTEXT, RELAXED, NEGATIVE -> edge.setOperationType(IBeXOperationType.CONTEXT);
			default -> {}
		}
	}

	public static void removeAttributeConditions(Collection<BooleanExpression> conditions, DomainType domainType) {
		var deletedConditions = new LinkedList<EObject>();
		for(var condition : conditions) {
			var nodeExpressions = SlimGTModelUtil.getElements(condition, NodeAttributeExpression.class);	
			for(var nodeAttrExpr : nodeExpressions) {
				var ibexNode = nodeAttrExpr.getNodeExpression();
				if(ibexNode == null) {
					deletedConditions.add(condition);
					break;
				}
				else 
					if(ibexNode instanceof TGGNode tggNode) 
						if(tggNode.getDomainType() == domainType) {
							deletedConditions.add(condition);
							break;
						}
			}
			
			var attributeExpressions = SlimGTModelUtil.getElements(condition, IBeXAttributeValue.class);
			for(var attributeExpression : attributeExpressions) {
				var ibexNode = attributeExpression.getNode();
				if(ibexNode == null) {
					deletedConditions.add(condition);
					break;
				}
				else 
					if(ibexNode instanceof TGGNode tggNode) 
						if(tggNode.getDomainType() == domainType) {
							deletedConditions.add(condition);
							break;
						}
			} 
		}
		EcoreUtil.deleteAll(deletedConditions, true); 
	}

	public static void removeBrokenAttributeConditions(Collection<BooleanExpression> conditions) {
		var deletedConditions = new LinkedList<>();
		for(var condition : conditions) {
			var nodeExpressions = SlimGTModelUtil.getElements(condition, NodeAttributeExpression.class);	
			for(var nodeAttrExpr : nodeExpressions) {
				var ibexNode = nodeAttrExpr.getNodeExpression();
				if(ibexNode == null) {
					deletedConditions.add(condition);
					break;
				}
			}
		}
		for (var delCondition : deletedConditions) {
			if (!(delCondition instanceof RelationalExpression relationalExpression))
				continue;
			if (relationalExpression.getLhs() instanceof IBeXAttributeValue attributeValue)
				((TGGNode) attributeValue.getNode()).getReferencedByConditions().remove(delCondition);
			if (relationalExpression.getRhs() instanceof IBeXAttributeValue attributeValue)
				((TGGNode) attributeValue.getNode()).getReferencedByConditions().remove(delCondition);
		}
		conditions.removeAll(deletedConditions);
	}

	public static void removeAttributeConstraints(TGGAttributeConstraintSet tggAttributeConstraintSet, DomainType domainType) {
		var constraints = tggAttributeConstraintSet.getTggAttributeConstraints();
		
		var deletedConstraints = new HashSet<>();
		var remainingParameters = new HashSet<>();
		for(var constraint : constraints) {
			for(var param : constraint.getParameters()) {
				if(param.getExpression() instanceof IBeXAttributeValue nodeAttrExpr) {
					var ibexNode = nodeAttrExpr.getNode();
					if(ibexNode == null) {
						deletedConstraints.add(constraint);
						break;
					}
					else 
						if(ibexNode instanceof TGGNode tggNode) 
							if(tggNode.getDomainType() == domainType) {
								deletedConstraints.add(constraint);
								break;
							}
				}
			}
			if(!deletedConstraints.contains(constraint))
				remainingParameters.addAll(constraint.getParameters());
		}
		constraints.removeAll(deletedConstraints);
		var deletedParameters = tggAttributeConstraintSet.getParameters().stream().filter(p -> !remainingParameters.contains(p)).toList();
		deletedParameters.forEach(p -> EcoreUtil.delete(p));
	}

	public static void removeAttributeAssignments(Collection<IBeXAttributeAssignment> assignments, DomainType domainType) {
		List<EObject> deletedAssignments = new LinkedList<>();
		for(var assignment : assignments) {
			var ibexNode = assignment.getNode();
			if(ibexNode == null)
				deletedAssignments.add(assignment);
			else 
				if(ibexNode instanceof TGGNode tggNode) 
					if(tggNode.getDomainType() == domainType)
						deletedAssignments.add(assignment);
		}
		
		EcoreUtil.removeAll(deletedAssignments);
	}

	public static void removeInvocations(Collection<IBeXPatternInvocation> invocations, DomainType domainType) {
		var deletedInvocations = new LinkedList<>();
		for(var invocation : invocations) {
			for(var mapping : invocation.getMapping()) {
				var ibexNode = mapping.getKey();
				if(ibexNode == null) {
					deletedInvocations.add(invocation);
				}
				else
					if(ibexNode instanceof TGGNode tggNode) {
						if(tggNode.getDomainType() == domainType) {
							deletedInvocations.add(invocation);
						}
					}
			}
		}
		
		invocations.removeAll(deletedInvocations);
	}

}
