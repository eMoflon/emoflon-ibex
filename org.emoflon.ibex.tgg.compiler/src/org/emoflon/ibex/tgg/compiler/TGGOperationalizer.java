package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

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
		constructModelGen(rule);
		constructForward(rule);
	}

	private void constructModelGen(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		op.setOperationalisationMode(OperationalisationMode.GENERATE);
	}
	
	private void constructForward(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		op.setOperationalisationMode(OperationalisationMode.FORWARD);
		
		transformBindings(op, DomainType.SOURCE, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
	}

	private void constructBackward(TGGRule rule) {
		var op = createOperationalizedTGGRule(rule);
		op.setOperationalisationMode(OperationalisationMode.FORWARD);
		
		transformBindings(op, DomainType.TARGET, BindingType.CREATE, BindingType.CONTEXT);
		transformAssignments(op);
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
		op.getNodes().addAll(ruleCopy.getNodes());
		op.getAllNodes().addAll(ruleCopy.getNodes());
		
		op.getEdges().addAll(ruleCopy.getEdges());
		op.getAllEdges().addAll(ruleCopy.getEdges());
		
		op.getCorrespondenceNodes().addAll(ruleCopy.getCorrespondenceNodes());
		op.getAllNodes().addAll(ruleCopy.getCorrespondenceNodes());
		
		op.getAttributeAssignments().addAll(ruleCopy.getAttributeAssignments());
		op.getAttributeConstraints().getTggAttributeConstraints().addAll(rule.getAttributeConstraints().getTggAttributeConstraints());
	
		rule.getOperationalisations().add(op);
		
		return op;
	}
	
	
}
