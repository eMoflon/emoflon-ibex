package org.emoflon.ibex.tgg.compiler;

import static org.emoflon.ibex.tgg.compiler.TGGRuleDerivedFieldsTool.fillDerivedTGGOperationalRuleFields;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator;
import org.emoflon.ibex.common.slimgt.slimGT.NodeAttributeExpression;
import org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil;
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

public class TGGOperationalizer {

	private IBeXCoreModelFactory superFactory = IBeXCoreModelFactory.eINSTANCE;
	private IBeXTGGModelFactory factory = IBeXTGGModelFactory.eINSTANCE;
	private IBeXCoreArithmeticFactory arithmeticFactory = IBeXCoreArithmeticFactory.eINSTANCE;
	private ProtocolInformation protocolInformation;
	
	public TGGModel operationalizeTGGRules(TGGModel model, ProtocolInformation protocolInformation) {
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
	}

	private void constructSource(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		op.setOperationalisationMode(OperationalisationMode.SOURCE);
		
		removeDomainInformation(op, DomainType.TARGET);
		fillDerivedTGGOperationalRuleFields(op);
	}
	
	
	private void constructTarget(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		op.setOperationalisationMode(OperationalisationMode.TARGET);
		
		removeDomainInformation(op, DomainType.SOURCE);
	}
	
	private void removeDomainInformation(TGGOperationalRule op, DomainType type) {
		
		removeElements(op, type);
		removeElements(op, DomainType.CORRESPONDENCE);

		removeInvocations(op.getPrecondition().getInvocations(), type);
//		removeInvocations(op.getPostcondition().getInvocations(), type);

		removeAttributeAssignments(op.getAttributeAssignments(), type);
		removeAttributeConstraints(((TGGPattern) op.getPrecondition()).getAttributeConstraints().getTggAttributeConstraints(), type);
		
		removeAttributeConditions(op.getPrecondition().getConditions(), type);
//		removeAttributeConditions(op.getPostcondition().getConditions(), type);
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
		conditions.removeAll(deletedConditions);
	}
	
	private void removeAttributeConstraints(Collection<TGGAttributeConstraint> constraints, DomainType domainType) {
		var deletedConstraints = new LinkedList<>();
		for(var constraint : constraints) {
			for(var param : constraint.getParameters()) {
				if(param instanceof NodeAttributeExpression nodeAttrExpr) {
					var ibexNode = nodeAttrExpr.getNodeExpression();
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
		}
		constraints.removeAll(deletedConstraints);
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

	private void constructModelGen(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.GENERATE);
		op.setOperationalisationMode(OperationalisationMode.GENERATE);
		
		createProtocolNode(op, BindingType.CREATE);
		rule.getOperationalisations().add(op);
	}

	private void constructForward(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.FORWARD);
		op.setOperationalisationMode(OperationalisationMode.FORWARD);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		
		createProtocolNode(op, BindingType.CREATE);
		rule.getOperationalisations().add(op);
	}

	private void constructBackward(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.BACKWARD);
		op.setOperationalisationMode(OperationalisationMode.BACKWARD);
		
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		
		createProtocolNode(op, BindingType.CREATE);
		rule.getOperationalisations().add(op);
	}
	
	private void constructConsistencyCheck(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.CONSISTENCY_CHECK);
		op.setOperationalisationMode(OperationalisationMode.CONSISTENCY_CHECK);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		
		createProtocolNode(op, BindingType.CREATE);
		rule.getOperationalisations().add(op);
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
		rule.getOperationalisations().add(op);
	}
	
	private void setRuleName(TGGRule rule, OperationalisationMode mode) {
		rule.setName(rule.getName() + "_" + mode.getName());
		rule.getPrecondition().setName(rule.getPrecondition().getName() + "_" + mode.getName());
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
		
		var ruleCopy = EcoreUtil.copy(rule);
		var preconditionCopy = EcoreUtil.copy(rule.getPrecondition());
		op.getNodes().addAll(ruleCopy.getNodes());
		op.getAllNodes().addAll(ruleCopy.getNodes());
		
		op.setPrecondition(preconditionCopy);
		
		op.getEdges().addAll(ruleCopy.getEdges());
		op.getAllEdges().addAll(ruleCopy.getEdges());
		
		op.getCorrespondenceNodes().addAll(ruleCopy.getCorrespondenceNodes());
		op.getAllNodes().addAll(ruleCopy.getCorrespondenceNodes());
		
		op.getAttributeAssignments().addAll(ruleCopy.getAttributeAssignments());
//		var tggAttributeConstraints = ((TGGPattern) ruleCopy.getPrecondition()).getAttributeConstraints().getTggAttributeConstraints();
//		((TGGPattern) op.getPrecondition()).getAttributeConstraints().getTggAttributeConstraints().addAll(tggAttributeConstraints);

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
			var reference = protocolNodeInformation.nodeToReference().get(node);
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
	
}
