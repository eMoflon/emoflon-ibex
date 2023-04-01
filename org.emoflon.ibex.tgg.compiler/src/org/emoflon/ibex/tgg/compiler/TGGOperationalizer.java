package org.emoflon.ibex.tgg.compiler;

import static org.emoflon.ibex.tgg.compiler.TGGRuleDerivedFieldsTool.fillDerivedTGGOperationalRuleFields;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

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
import org.emoflon.ibex.common.slimgt.slimGT.NodeAttributeExpression;
import org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil;
import org.emoflon.ibex.tgg.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;

public class TGGOperationalizer {

	private TGGModel model;
	private IBeXCoreModelFactory superFactory = IBeXCoreModelFactory.eINSTANCE;
	private IBeXTGGModelFactory factory = IBeXTGGModelFactory.eINSTANCE;
	private IBeXCoreArithmeticFactory arithmeticFactory = IBeXCoreArithmeticFactory.eINSTANCE;
	private ProtocolInformation protocolInformation;
	
	public TGGModel operationalizeTGGRules(TGGModel model, ProtocolInformation protocolInformation) {
		this.model = model;
		this.protocolInformation = protocolInformation;
		for(var rule : model.getRuleSet().getRules()) {
			operationalizeRule(rule);
		}
		return model;
	}

	private void operationalizeRule(TGGRule rule) {
		constructSource(rule);
		constructTarget(rule);
		constructModelGen(rule);
		constructForward(rule);
		constructBackward(rule);
		constructConsistencyCheck(rule);
		constructCheckOnly(rule);
		constructConsistency(rule);
	}

	private void constructModelGen(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.GENERATE);
		op.setOperationalisationMode(OperationalisationMode.GENERATE);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);
		
		rule.getOperationalisations().add(op);
		fillDerivedTGGOperationalRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
	}

	private void constructForward(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.FORWARD);
		op.setOperationalisationMode(OperationalisationMode.FORWARD);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);
		
		rule.getOperationalisations().add(op);
		fillDerivedTGGOperationalRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
	}

	private void constructBackward(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.BACKWARD);
		op.setOperationalisationMode(OperationalisationMode.BACKWARD);
		
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);

		rule.getOperationalisations().add(op);
		fillDerivedTGGOperationalRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
	}

	private void constructConsistencyCheck(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.CONSISTENCY_CHECK);
		op.setOperationalisationMode(OperationalisationMode.CONSISTENCY_CHECK);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		fixPrecondition(op);

		createProtocolNode(op, BindingType.CREATE);
		rule.getOperationalisations().add(op);
		fillDerivedTGGOperationalRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
	}

	private void constructCheckOnly(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.CHECK_ONLY);
		op.setOperationalisationMode(OperationalisationMode.CHECK_ONLY);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op,  DomainType.CORRESPONDENCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);
		
		rule.getOperationalisations().add(op);
		fillDerivedTGGOperationalRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
	}
	
	private void constructConsistency(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.CONSISTENCY);
		op.setOperationalisationMode(OperationalisationMode.CONSISTENCY);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op,  DomainType.CORRESPONDENCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CONTEXT);
		fixPrecondition(op);
		
		rule.getOperationalisations().add(op);
		fillDerivedTGGOperationalRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
	}

	private void constructSource(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.SOURCE);
		op.setOperationalisationMode(OperationalisationMode.SOURCE);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		removeDomainInformation(op, DomainType.CORRESPONDENCE);
		removeDomainInformation(op, DomainType.TARGET);
		fixPrecondition(op);
		
		fillDerivedTGGOperationalRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
	}
	
	
	private void constructTarget(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.TARGET);
		op.setOperationalisationMode(OperationalisationMode.TARGET);
		
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		removeDomainInformation(op, DomainType.CORRESPONDENCE);
		removeDomainInformation(op, DomainType.SOURCE);
		fixPrecondition(op);

		fillDerivedTGGOperationalRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
	}
	
	private void removeDomainInformation(TGGOperationalRule op, DomainType type) {
		
		removeElements(op, type);
		removeElements(op, DomainType.CORRESPONDENCE);

		removeInvocations(op.getPrecondition().getInvocations(), type);

		removeAttributeAssignments(op.getAttributeAssignments(), type);
		removeAttributeConditions(op.getPrecondition().getConditions(), type);
		
		removeAttributeConstraints(op.getAttributeConstraints(), type);
		removeAttributeConstraints(((TGGPattern) op.getPrecondition()).getAttributeConstraints(), type);
	}
	
	private void removeElements(TGGOperationalRule op, DomainType domainType) {
		var deletedNodes = new LinkedList<EObject>();
		// remove all unnecessary nodes
		for(var node : op.getNodes()) {
			if(node.getDomainType() == domainType) {
				deletedNodes.add(node);
			}
		}
		deletedNodes.forEach(EcoreUtil::delete);
		
		var deletedEdges = new LinkedList<EObject>();
		// remove all unnecessary edges
		for(var edge : op.getEdges()) {
			if(edge.getDomainType() == domainType) {
				deletedEdges.add(edge);
			}
		}
		deletedEdges.forEach(EcoreUtil::delete);
	}

	private void removeAttributeConditions(Collection<BooleanExpression> conditions, DomainType domainType) {
		var deletedConditions = new LinkedList<>();
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
	
	private void removeBrokenAttributeConditions(Collection<BooleanExpression> conditions) {
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
	
	private void removeAttributeConstraints(TGGAttributeConstraintSet tggAttributeConstraintSet, DomainType domainType) {
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
	
	private void removeAttributeAssignments(Collection<IBeXAttributeAssignment> assignments, DomainType domainType) {
		var deletedAssignments = new LinkedList<>();
		for(var assignment : assignments) {
			var ibexNode = assignment.getNode();
			if(ibexNode == null)
				deletedAssignments.add(assignment);
			else 
				if(ibexNode instanceof TGGNode tggNode) 
					if(tggNode.getDomainType() == domainType)
						deletedAssignments.add(assignment);
				
		}
	}
	
	private void removeInvocations(Collection<IBeXPatternInvocation> invocations, DomainType domainType) {
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

	private void setRuleName(TGGRule rule, OperationalisationMode mode) {
		rule.setName(PatternSuffixes.getPatternName(rule.getName(), mode));
		rule.getPrecondition().setName(PatternSuffixes.getPatternName(rule.getPrecondition().getName(), mode));
	}
	
	private void transformBindings(TGGOperationalRule op, DomainType domain, BindingType formerBinding, BindingType newBinding) {
		Collection<TGGRuleElement> elements = new HashSet<>();
		elements.addAll(op.getNodes());
		elements.addAll(op.getEdges());
		
		for(var tggElement : elements) {
			if(tggElement.getDomainType() == domain) {
				if(tggElement.getBindingType() == formerBinding) {
					tggElement.setBindingType(newBinding);
					if(tggElement instanceof IBeXNode node)
						switch(newBinding) {
						case CONTEXT: 
						case RELAXED:
						case NEGATIVE:
							node.setOperationType(IBeXOperationType.CONTEXT);
							break;
						case CREATE:
							node.setOperationType(IBeXOperationType.CREATION);
							break;
						case DELETE:
							node.setOperationType(IBeXOperationType.DELETION);
							break;
						}
					
					if(tggElement instanceof IBeXEdge edge)
						switch(newBinding) {
						case CONTEXT: 
						case RELAXED:
						case NEGATIVE:
							edge.setOperationType(IBeXOperationType.CONTEXT);
							break;
						case CREATE:
							edge.setOperationType(IBeXOperationType.CREATION);
							break;
						case DELETE:
							edge.setOperationType(IBeXOperationType.DELETION);
							break;
						}
				}
			}
		}
	}

	private void transformAssignments(TGGOperationalRule op) {
		var transformedAssignments = new LinkedList<IBeXAttributeAssignment>();
		for(var assignment : op.getAttributeAssignments()) {
			if(assignment.getNode() instanceof TGGNode tggNode) {
				var condition = arithmeticFactory.createRelationalExpression();
				condition.setOperator(RelationalOperator.EQUAL);
				
				var lhs = superFactory.createIBeXAttributeValue();
				lhs.setNode(tggNode);
				lhs.setAttribute(assignment.getAttribute());
				lhs.setType(assignment.getAttribute().getEType());
				condition.setLhs(lhs);
				condition.setRhs(assignment.getValue());
				op.getPrecondition().getConditions().add(condition);
				op.getPostcondition().getConditions().add(EcoreUtil.copy(condition));
				transformedAssignments.add(assignment);
			}
			else {
				throw new RuntimeException("Patterns should not contain any assignments");
			}
		}
		op.getAttributeAssignments().removeAll(transformedAssignments);
	}

	private TGGOperationalRule createOperationalizedTGGRule(TGGRule rule) {
		var op = factory.createTGGOperationalRule();
		op.setName(rule.getName());
		
		rule.getGenericContents().add(rule.getPrecondition());
		var ruleCopy = EcoreUtil.copy(rule);
		model.getPatternSet().getPatterns().add(rule.getPrecondition());
		
		op.setPrecondition(ruleCopy.getPrecondition());
		model.getPatternSet().getPatterns().add(op.getPrecondition());
		
		op.getNodes().addAll(ruleCopy.getNodes());
		op.getAllNodes().addAll(ruleCopy.getNodes());
		
		op.getEdges().addAll(ruleCopy.getEdges());
		op.getAllEdges().addAll(ruleCopy.getEdges());
		
		op.getCorrespondenceNodes().addAll(ruleCopy.getCorrespondenceNodes());
		op.getAllNodes().addAll(ruleCopy.getCorrespondenceNodes());
		
		op.getAttributeAssignments().addAll(ruleCopy.getAttributeAssignments());
		op.setAttributeConstraints(ruleCopy.getAttributeConstraints());
			
		rule.getOperationalisations().add(op);
		
		return op;
	}
	
	private TGGNode createProtocolNode(TGGOperationalRule rule, BindingType binding) {
		ProtocolNodeInformation protocolNodeInformation = protocolInformation.ruleToInformation().get((TGGRule) rule.eContainer());

		var protocolNode = factory.createTGGNode();
		
		protocolNode.setName(ProtocolGenerator.PROTOCOL_NODE_PREFIX);
		protocolNode.setType(protocolNodeInformation.type());
		
		protocolNode.setDomainType(DomainType.PROTOCOL);
		protocolNode.setBindingType(binding);
		
		for(var node : rule.getNodes()) {
			var reference = protocolNodeInformation.nodeToReference().get(node.getName());
			var edge = factory.createTGGEdge();
			
			edge.setSource(protocolNode);
			edge.setTarget(node);
			edge.setType(reference);
			
			edge.setName("check_" + node.getName());
			
			edge.setDomainType(DomainType.PROTOCOL);
			edge.setBindingType(binding);
			switch(binding) {
			case CONTEXT: 
			case RELAXED:
			case NEGATIVE:
				edge.setOperationType(IBeXOperationType.CONTEXT);
				break;
			case CREATE:
				edge.setOperationType(IBeXOperationType.CREATION);
				break;
			case DELETE:
				edge.setOperationType(IBeXOperationType.DELETION);
				break;
			}
			
			rule.getAllEdges().add(edge);
			rule.getEdges().add(edge);
			
//			if(binding == BindingType.CREATE) 
//				rule.getCreation().getEdges().add(edge);
		}
		
		rule.getAllNodes().add(protocolNode);
		rule.getNodes().add(protocolNode);
//		if(binding == BindingType.CREATE)
//			rule.getCreation().getNodes().add(protocolNode);
		
		return protocolNode;
	}

	private void removeInvalidAttributeConstraintsFromPrecondition(TGGOperationalRule op) {
		var precondition = op.getPrecondition();
		var tggAttributeConstraintSet = ((TGGPattern) precondition).getAttributeConstraints();
		
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
							if(tggNode.getBindingType() != BindingType.CONTEXT) {
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

	private void fixPrecondition(TGGOperationalRule op) {
		var precondition = op.getPrecondition();
		
		precondition.getLocalNodes().clear();
		precondition.getSignatureNodes().clear();
		precondition.getEdges().clear();

		for(var node : op.getNodes()) {
			if(node.getBindingType() == BindingType.CONTEXT)
				precondition.getSignatureNodes().add(node);
		}
		
		for(var edge : op.getEdges()) {
			if(edge.getOperationType() == IBeXOperationType.CONTEXT)
				precondition.getEdges().add(edge);
		}
		
		removeBrokenAttributeConditions(precondition.getConditions());
	}
	
}
