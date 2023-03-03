package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

public class InjectivityConstraintHandler {

	private IBeXCoreModelFactory factory = IBeXCoreModelFactory.eINSTANCE;
	private IBeXCoreArithmeticFactory arithFactory = IBeXCoreArithmeticFactory.eINSTANCE;
	
	private Collection<EObject> handledObjects = new HashSet<>();
	
	public void addInjectivityChecks(TGGModel tgg) {
		handledObjects.clear();
		
		for(var rule : tgg.getRuleSet().getRules()) {
			addInjectivityChecks(rule);
		}
		
		for(var pattern : tgg.getPatternSet().getPatterns()) {
			addInjectivityChecks(pattern);
		}
	}

	private void addInjectivityChecks(TGGRule rule) {
		addInjectivityChecks(rule.getPrecondition());
		addInjectivityChecks(rule.getPostcondition());
	}

	private void addInjectivityChecks(IBeXPattern pattern) {
		if(handledObjects.contains(pattern))
			return;
		
		handledObjects.add(pattern);

		for(var invocation : pattern.getInvocations()) {
			addInjectivityChecks(invocation.getInvocation());
		}
		
		var nodes = new LinkedList<IBeXNode>();
		nodes.addAll(pattern.getSignatureNodes());
		nodes.addAll(pattern.getLocalNodes());
		for(int i=0; i < nodes.size(); i++) {
			for(int j=i+1; j < nodes.size(); j++) {
				var leftNode = nodes.get(i);
				var rightNode = nodes.get(j);
				
				if(!isInjectivityConstraintNecessary(leftNode, rightNode))
					return;
				
				var nodeConstraint = arithFactory.createRelationalExpression();
				nodeConstraint.setOperator(RelationalOperator.UNEQUAL);
				pattern.getConditions().add(nodeConstraint);
				
				var leftExpression = factory.createIBeXNodeValue();
				leftExpression.setNode(leftNode);
				leftExpression.setType(leftNode.getType());
				nodeConstraint.setLhs(leftExpression);
				
				var rightExpression = factory.createIBeXNodeValue();
				rightExpression.setNode(rightNode);
				rightExpression.setType(rightNode.getType());
				nodeConstraint.setLhs(rightExpression);
			}
		}
	}
	
	private boolean isInjectivityConstraintNecessary(IBeXNode leftNode, IBeXNode rightNode) {
		var leftType = leftNode.getType();
		var rightType = rightNode.getType();
		
		if(leftType.isSuperTypeOf(rightType))
			return true;
		
		if(rightType.isSuperTypeOf(leftType))
			return true;
		return false;
	}

	
	public void optimizeInjectivityConstraints(TGGModel tgg) {
		handledObjects.clear();

		for(var rule : tgg.getRuleSet().getRules()) {
			optimizeInjectivityConstraints(rule);
		}
		
		for(var pattern : tgg.getPatternSet().getPatterns()) {
			optimizeInjectivityConstraints(pattern);
		}
	}

	private void optimizeInjectivityConstraints(TGGRule rule) {
		optimizeInjectivityConstraints(rule.getPrecondition());
		optimizeInjectivityConstraints(rule.getPostcondition());
	}

	private void optimizeInjectivityConstraints(IBeXPattern pattern) {
		if(handledObjects.contains(pattern))
			return;
		
		handledObjects.add(pattern);

		for(var invocation : pattern.getInvocations()) {
			optimizeInjectivityConstraints(invocation.getInvocation());
		}
		
		Collection<BooleanExpression> removedConditions = new LinkedList<>();
		Collection<InjectivityPair> nodePairs = new HashSet<>();
		for(var condition : pattern.getConditions()) {
			if(condition instanceof RelationalExpression relationalConstraint) {
				if(relationalConstraint.getLhs() instanceof IBeXNodeValue leftValue && relationalConstraint.getRhs() instanceof IBeXNodeValue rightValue) {
					var leftNode = leftValue.getNode();
					var rightNode = rightValue.getNode();
					
					// we have to do this twice to account for symmetric conditions
					boolean pairAlreadyPresent = !nodePairs.add(new InjectivityPair(leftNode, rightNode));
					pairAlreadyPresent |= !nodePairs.add(new InjectivityPair(rightNode, leftNode));
					
					if(pairAlreadyPresent) {
						removedConditions.add(condition);
					}
				}
			}
		}
		pattern.getConditions().removeAll(removedConditions);
	}
}

record InjectivityPair(IBeXNode leftNode, IBeXNode rightNode) {}