package org.emoflon.ibex.tgg.compiler;

import static org.emoflon.ibex.tgg.compiler.TGGRuleDerivedFieldsTool.fillDerivedTGGOperationalRuleFields;
import static org.emoflon.ibex.tgg.compiler.TGGRuleDerivedFieldsTool.fillDerivedTGGRuleFields;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator;
import org.emoflon.ibex.tgg.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.analysis.ACPatternTransformation;
import org.emoflon.ibex.tgg.analysis.ACStrategy;
import org.emoflon.ibex.tgg.analysis.FilterNACCandidate;
import org.emoflon.ibex.tgg.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.patterns.TGGPatternUtil;
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
import org.emoflon.ibex.tgg.util.TGGModelUtils;

public class TGGOperationalizer {

	private TGGModel model;
	private IBeXCoreModelFactory superFactory = IBeXCoreModelFactory.eINSTANCE;
	private IBeXTGGModelFactory factory = IBeXTGGModelFactory.eINSTANCE;
	private IBeXCoreArithmeticFactory arithmeticFactory = IBeXCoreArithmeticFactory.eINSTANCE;
	private ProtocolInformation protocolInformation;
	
	private ACAnalysis acAnalysis;
	private ACPatternTransformation acPatternTransformation;
	
	private Map<TGGRule, Collection<FilterNACCandidate>> rule2nacCandidates = new HashMap<>();
	
	public TGGModel operationalizeTGGRules(TGGModel model, ProtocolInformation protocolInformation) {
		this.model = model;
		
		acAnalysis = new ACAnalysis(model, ACStrategy.FILTER_NACS);
		acPatternTransformation = new ACPatternTransformation(model);
		
		this.protocolInformation = protocolInformation;
		for(var rule : model.getRuleSet().getRules()) {
			operationalizeRule(rule);
		}
		return model;
	}

	private void operationalizeRule(TGGRule rule) {
		initializeACAnalysis(rule);
		
		constructSource(rule);
		constructTarget(rule);
		constructModelGen(rule);
		constructForward(rule);
		constructBackward(rule);
		constructConsistencyCheck(rule);
		constructCheckOnly(rule);
		constructConsistency(rule);
		
		addInjectivityConstraints(rule);
		optimizeInjectivityConstraints(rule);
	}

	private void constructModelGen(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.GENERATE);
		op.setNoGeneratedInjectivityConstraints(rule.isNoGeneratedInjectivityConstraints());
		op.setOperationalisationMode(OperationalisationMode.GENERATE);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);
		
		rule.getOperationalisations().add(op);
		fillDerivedTGGRuleFields(op);
		fillDerivedTGGOperationalRuleFields(op);
		invalidateAttributeConstraintsWithDerivedParameters(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
		addInjectivityConstraints(op);
		optimizeInjectivityConstraints(op);
	}

	private void constructForward(TGGRule rule) {
		if(rule.getAllNodes().isEmpty())
			return;
		
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.FORWARD);
		op.setNoGeneratedInjectivityConstraints(rule.isNoGeneratedInjectivityConstraints());
		op.setOperationalisationMode(OperationalisationMode.FORWARD);

		// we have to fill marked fields before transforming the rule or else information about formerly created elements is lost
		fillDerivedTGGOperationalRuleFields(op);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);
		
		rule.getOperationalisations().add(op);
		fillDerivedTGGRuleFields(op);
		invalidateAttributeConstraintsWithDerivedParameters(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
		addInjectivityConstraints(op);
		optimizeInjectivityConstraints(op);
		
		applyACAnalysis(op, DomainType.SOURCE);
	}
	
	private void constructBackward(TGGRule rule) {
		if(rule.getNodes().isEmpty())
			return;
		
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.BACKWARD);
		op.setNoGeneratedInjectivityConstraints(rule.isNoGeneratedInjectivityConstraints());
		op.setOperationalisationMode(OperationalisationMode.BACKWARD);
		
		// we have to fill marked fields before transforming the rule or else information about formerly created elements is lost
		fillDerivedTGGOperationalRuleFields(op);
		
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);

		rule.getOperationalisations().add(op);
		fillDerivedTGGRuleFields(op);
		invalidateAttributeConstraintsWithDerivedParameters(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
		addInjectivityConstraints(op);
		optimizeInjectivityConstraints(op);
		
		applyACAnalysis(op, DomainType.TARGET);
	}

	private void constructConsistencyCheck(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.CONSISTENCY_CHECK);
		op.setNoGeneratedInjectivityConstraints(rule.isNoGeneratedInjectivityConstraints());
		op.setOperationalisationMode(OperationalisationMode.CONSISTENCY_CHECK);
		
		// we have to fill marked fields before transforming the rule or else information about formerly created elements is lost
		fillDerivedTGGOperationalRuleFields(op);

		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);

		rule.getOperationalisations().add(op);
		fillDerivedTGGRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
		addInjectivityConstraints(op);
		optimizeInjectivityConstraints(op);
		
		applyACAnalysis(op, DomainType.SOURCE, DomainType.TARGET);
	}

	private void constructCheckOnly(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.CHECK_ONLY);
		op.setNoGeneratedInjectivityConstraints(rule.isNoGeneratedInjectivityConstraints());
		op.setOperationalisationMode(OperationalisationMode.CHECK_ONLY);
		
		// we have to fill marked fields before transforming the rule or else information about formerly created elements is lost
		fillDerivedTGGOperationalRuleFields(op);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op,  DomainType.CORRESPONDENCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CREATE);
		fixPrecondition(op);
		
		rule.getOperationalisations().add(op);
		fillDerivedTGGRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
		addInjectivityConstraints(op);
		optimizeInjectivityConstraints(op);
	}
	
	private void constructConsistency(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.CONSISTENCY);
		op.setNoGeneratedInjectivityConstraints(rule.isNoGeneratedInjectivityConstraints());
		op.setOperationalisationMode(OperationalisationMode.CONSISTENCY);
		
		// we have to fill marked fields before transforming the rule or else information about formerly created elements is lost
		fillDerivedTGGOperationalRuleFields(op);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op,  DomainType.CORRESPONDENCE, BindingType.CREATE, BindingType.CONTEXT);
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
		createProtocolNode(op, BindingType.CONTEXT);
		fixPrecondition(op);
		
		rule.getOperationalisations().add(op);
		fillDerivedTGGRuleFields(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
		addInjectivityConstraints(op);
		optimizeInjectivityConstraints(op);
		
		applyACAnalysis(op, DomainType.SOURCE, DomainType.TARGET);
	}

	private void constructSource(TGGRule rule) {
		if(rule.getSource().getNodes().isEmpty())
			return;
		
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.SOURCE);
		op.setNoGeneratedInjectivityConstraints(rule.isNoGeneratedInjectivityConstraints());
		op.setOperationalisationMode(OperationalisationMode.SOURCE);
		
		// we have to fill marked fields before transforming the rule or else information about formerly created elements is lost
		fillDerivedTGGOperationalRuleFields(op);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		removeDomainInformation(op, DomainType.CORRESPONDENCE);
		removeDomainInformation(op, DomainType.TARGET);
		fixPrecondition(op);
		
		fillDerivedTGGRuleFields(op);
		invalidateAttributeConstraintsWithDerivedParameters(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
		addInjectivityConstraints(op);
		optimizeInjectivityConstraints(op);
		
		applyACAnalysis(op, DomainType.SOURCE);
	}
	
	
	private void constructTarget(TGGRule rule) {
		if(rule.getTarget().getNodes().isEmpty())
			return;
		
		var op = createOperationalizedTGGRule(rule);
		setRuleName(op, OperationalisationMode.TARGET);
		op.setNoGeneratedInjectivityConstraints(rule.isNoGeneratedInjectivityConstraints());
		op.setOperationalisationMode(OperationalisationMode.TARGET);
		
		// we have to fill marked fields before transforming the rule or else information about formerly created elements is lost
		fillDerivedTGGOperationalRuleFields(op);
		
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		removeDomainInformation(op, DomainType.CORRESPONDENCE);
		removeDomainInformation(op, DomainType.SOURCE);
		fixPrecondition(op);

		fillDerivedTGGRuleFields(op);
		invalidateAttributeConstraintsWithDerivedParameters(op);
		removeInvalidAttributeConstraintsFromPrecondition(op);
		addInjectivityConstraints(op);
		optimizeInjectivityConstraints(op);
		
		applyACAnalysis(op, DomainType.TARGET);
	}
	
	private void initializeACAnalysis(TGGRule rule) {
		var candidates = new LinkedList<FilterNACCandidate>();
		candidates.addAll(acAnalysis.computeFilterNACCandidates(rule, DomainType.SOURCE));
		candidates.addAll(acAnalysis.computeFilterNACCandidates(rule, DomainType.TARGET));
		rule2nacCandidates.put(rule, candidates);
	}

	private void applyACAnalysis(TGGOperationalRule operationalRule, DomainType...domainTypes) {
		var tggRule = operationalRule.getTggRule();
		
		for(var nacCandidate : rule2nacCandidates.get(tggRule)) {
			for(var domainType : domainTypes) {
				if(nacCandidate.getNodeInRule().getDomainType() == domainType)
					acPatternTransformation.addFilterNAC(operationalRule, nacCandidate);
			}
		}
	}

	private void removeDomainInformation(TGGOperationalRule op, DomainType type) {
		removeElements(op, type);
		removeElements(op, DomainType.CORRESPONDENCE);

		TGGModelUtils.removeInvocations(op.getPrecondition().getInvocations(), type);

		TGGModelUtils.removeAttributeAssignments(op.getAttributeAssignments(), type);
		TGGModelUtils.removeAttributeConditions(op.getPrecondition().getConditions(), type);
		
		TGGModelUtils.removeAttributeConstraints(op.getAttributeConstraints(), type);
		TGGModelUtils.removeAttributeConstraints(((TGGPattern) op.getPrecondition()).getAttributeConstraints(), type);
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
				if(tggNode.getBindingType() == BindingType.CONTEXT) {
					var condition = arithmeticFactory.createRelationalExpression();
					condition.setOperator(RelationalOperator.EQUAL);
					
					var lhs = superFactory.createIBeXAttributeValue();
					lhs.setNode(tggNode);
					lhs.setAttribute(assignment.getAttribute());
					lhs.setType(assignment.getAttribute().getEType());
					condition.setLhs(lhs);
					condition.setRhs(assignment.getValue());
					op.getPrecondition().getConditions().add(condition);
					transformedAssignments.add(assignment);
				}
			}
			else {
				throw new RuntimeException("Patterns should not contain any assignments");
			}
		}
		EcoreUtil.deleteAll(transformedAssignments, true);
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
		
		protocolNode.setName(TGGPatternUtil.getProtocolNodeName());
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

	private void invalidateAttributeConstraintsWithDerivedParameters(TGGOperationalRule op) {
		var precondition = op.getPrecondition();
		var tggAttributeConstraintSet = ((TGGPattern) precondition).getAttributeConstraints();
		var constraints = tggAttributeConstraintSet.getTggAttributeConstraints();
		for(var constraint : constraints) {
			for(var param : constraint.getParameters()) {
				if(param.isDerived()) {
					var expression = (IBeXAttributeValue) param.getExpression();
					expression.setNode(null);
					break;
				}
			}
		}
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
		
		TGGModelUtils.removeBrokenAttributeConditions(precondition.getConditions());
	}
	
	private void addInjectivityConstraints(TGGRule internalRule) {
		if(internalRule.isNoGeneratedInjectivityConstraints())
			return;
			
		var nodes = internalRule.getContext().getNodes();
		outer: for(int i=0; i < nodes.size(); i++) {
			for(int j=i+1; j < nodes.size(); j++) {
				var node = (TGGNode) nodes.get(i);
				var otherNode = (TGGNode) nodes.get(j);
				
				if(node.getDomainType() == DomainType.CORRESPONDENCE)
					continue outer;
				if(otherNode.getDomainType() == DomainType.CORRESPONDENCE)
					continue;
				
				if(node.getType().isSuperTypeOf(otherNode.getType()) || otherNode.getType().isSuperTypeOf(node.getType())) {
					var lhs = superFactory.createIBeXNodeValue();
					lhs.setNode(node);
					lhs.setType(node.getType());
					var rhs = superFactory.createIBeXNodeValue();
					rhs.setNode(otherNode);
					rhs.setType(otherNode.getType());
					var condition = arithmeticFactory.createRelationalExpression();
					condition.setLhs(lhs);
					condition.setRhs(rhs);
					condition.setOperator(RelationalOperator.OBJECT_NOT_EQUALS);
					internalRule.getPrecondition().getConditions().add(condition);
				}
			}
		}
	}

	private void optimizeInjectivityConstraints(TGGRule internalRule) {
		var nodePairs = new HashSet<NodePair>();
		var deletedConditions = new LinkedList<EObject>();
		for(var condition : internalRule.getPrecondition().getConditions()) {
			if(condition instanceof RelationalExpression relationConstraint) {
				if(relationConstraint.getOperator() == RelationalOperator.OBJECT_NOT_EQUALS) {
					if(relationConstraint.getLhs() == null || relationConstraint.getRhs() == null) {
						deletedConditions.add(condition);
						continue;
					}
					
					if(relationConstraint.getLhs() instanceof IBeXNodeValue nodeValue &&
							relationConstraint.getRhs() instanceof IBeXNodeValue otherNodeValue) {
						IBeXNode node = (IBeXNode) nodeValue.getNode();
						IBeXNode otherNode = (IBeXNode) otherNodeValue.getNode();
						
						NodePair nodePair = null;
						if(node.getName().compareTo(otherNode.getName()) > 0) {
							nodePair = new NodePair(node, otherNode);
						}
						else {
							nodePair = new NodePair(otherNode, node);
						}
						
						if(!nodePairs.add(nodePair)) {
							deletedConditions.add(condition);
						}
					}
				}
			}
		}
		EcoreUtil.deleteAll(deletedConditions, true);
	}
}
