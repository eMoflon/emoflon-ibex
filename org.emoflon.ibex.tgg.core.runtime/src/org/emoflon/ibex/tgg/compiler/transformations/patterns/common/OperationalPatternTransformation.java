package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import static org.emoflon.ibex.common.patterns.IBeXPatternUtils.findIBeXNodeWithName;
import static org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper.addInjectivityConstraintIfNecessary;

import java.util.List;
import java.util.Optional;

import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;
import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.IBeXPatternOptimiser;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGG;
import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;
import language.TGGEnumExpression;
import language.TGGLiteralExpression;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleNode;

public abstract class OperationalPatternTransformation {
	protected ContextPatternTransformation parent;
	protected IbexOptions options;
	protected TGGRule rule;

	protected ACAnalysis filterNACAnalysis;

	public OperationalPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
		this.parent = parent;
		this.options = options;
		this.rule = rule;
		this.filterNACAnalysis = filterNACAnalysis;
	}

	protected abstract String getPatternName();

	protected abstract void transformNodes(IBeXContextPattern ibexPattern);

	protected abstract void transformEdges(IBeXContextPattern ibexPattern);

	protected abstract boolean patternIsEmpty();
	
	protected abstract boolean includeDerivedCSPParams();

	public IBeXContextPattern transform() {
		String patternName = getPatternName();

		if (parent.isTransformed(patternName))
			return parent.getPattern(patternName);

		if (patternIsEmpty())
			return null;
		
		// Root pattern
		IBeXContextPattern ibexPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);
		parent.addContextPattern(ibexPattern, rule);

		// Transform nodes.
		transformNodes(ibexPattern);

		// Ensure that all nodes must be disjoint even if they have the same type.
		List<TGGRuleNode> allNodes = rule.getNodes();
		for (int i = 0; i < allNodes.size(); i++) {
			for (int j = i + 1; j < allNodes.size(); j++) {
				TGGRuleNode ruleNode_i = allNodes.get(i);
				TGGRuleNode ruleNode_j = allNodes.get(j);
				if(ruleNode_i.getBindingType() != ruleNode_j.getBindingType())
					continue;
				
				if(options.patterns.ignoreInjectivity().test(ruleNode_i, ruleNode_j))
					continue;
				
				if (IBeXPatternOptimiser.unequalConstraintNecessary(ruleNode_i, ruleNode_j)) {
					findIBeXNodeWithName(ibexPattern, ruleNode_i.getName())//
							.ifPresent(ni -> findIBeXNodeWithName(ibexPattern, ruleNode_j.getName())//
									.ifPresent(nj -> addInjectivityConstraintIfNecessary(ibexPattern, ni, nj)));
				}
			}
		}

		// Transform edges.
		transformEdges(ibexPattern);
		
		// Transform CEPs
		transformCSPs(ibexPattern);

		return ibexPattern;
	}

	private void transformCSPs(IBeXContextPattern ibexPattern) {
		for (TGGAttributeConstraint csp : rule.getAttributeConditionLibrary().getTggAttributeConstraints()) {

			if (tryTransformingCEPToRelationalConstraint(ibexPattern, csp))
				continue;

			IBeXCSP iCSP = IBeXPatternModelFactory.eINSTANCE.createIBeXCSP();
			iCSP.setName(csp.getDefinition().getName());
			if (csp.getDefinition().isUserDefined()) {
				iCSP.setName("UserDefined_" + iCSP.getName());
				iCSP.setPackage("org.emoflon.ibex.tgg.operational.csp.constraints.custom." + ((TGG) rule.eContainer()).getName().toLowerCase());
			} else {
				iCSP.setPackage("org.emoflon.ibex.tgg.operational.csp.constraints");
			}

			for (TGGParamValue param : csp.getParameters()) {
				if (param instanceof TGGAttributeExpression tggAttrExpr) {
					if (tggAttrExpr.isDerived() && !this.includeDerivedCSPParams())
						break;
					IBeXAttributeExpression attrExpr = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeExpression();
					Optional<IBeXNode> iBeXNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, tggAttrExpr.getObjectVar().getName());
					if (iBeXNode.isPresent())
						attrExpr.setNode(iBeXNode.get());
					else
						break;
					attrExpr.setAttribute(tggAttrExpr.getAttribute());
					iCSP.getValues().add(attrExpr);
				}
				else if (param instanceof TGGEnumExpression tggEnumExpr) {
					IBeXEnumLiteral literal = IBeXPatternModelFactory.eINSTANCE.createIBeXEnumLiteral();
					iCSP.getValues().add(literal);
					literal.setLiteral(tggEnumExpr.getLiteral());
				}
				else if (param instanceof TGGLiteralExpression tggLiteralExpr) {
					IBeXConstant constant = IBeXPatternModelFactory.eINSTANCE.createIBeXConstant();
					iCSP.getValues().add(constant);
					constant.setValue(tggLiteralExpr.getValue());
					constant.setStringValue(tggLiteralExpr.getValue());
				}
			}

			if (iCSP.getValues().size() == csp.getParameters().size())
				ibexPattern.getCsps().add(iCSP);
		}
	}

	
	
	private boolean tryTransformingCEPToRelationalConstraint(IBeXContextPattern ibexPattern, TGGAttributeConstraint csp) {
		if (!options.patterns.optimizeCSPs())
			return false;

		if (csp.getDefinition().isUserDefined())
			return false;

		if (csp.getParameters().size() != 2)
			return false;

		if (!csp.getDefinition().getName().startsWith("eq_"))
			return false;

		IBeXAttributeConstraint ac = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeConstraint();
		ac.setRelation(IBeXRelation.EQUAL);

		for (TGGParamValue param : csp.getParameters()) {
			if (param instanceof TGGAttributeExpression tggAttrExpr) {
				if (tggAttrExpr.isDerived() && !this.includeDerivedCSPParams())
					return false;
				IBeXAttributeExpression attrExpr = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeExpression();
				Optional<IBeXNode> iBeXNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, tggAttrExpr.getObjectVar().getName());
				if (iBeXNode.isPresent())
					attrExpr.setNode(iBeXNode.get());
				else
					return false;
				attrExpr.setAttribute(tggAttrExpr.getAttribute());

				if (ac.getLhs() == null)
					ac.setLhs(attrExpr);
				else
					ac.setRhs(attrExpr);
			} 
			else if (param instanceof TGGEnumExpression tggEnumExpr) {
				IBeXEnumLiteral literal = IBeXPatternModelFactory.eINSTANCE.createIBeXEnumLiteral();
				literal.setLiteral(tggEnumExpr.getLiteral());

				if (ac.getLhs() == null)
					ac.setLhs(literal);
				else
					ac.setRhs(literal);
			} 
			else if (param instanceof TGGLiteralExpression tggLiteralExpr) {
				IBeXConstant constant = IBeXPatternModelFactory.eINSTANCE.createIBeXConstant();
				constant.setValue(tggLiteralExpr.getValue());
				constant.setStringValue(tggLiteralExpr.getValue());

				if (ac.getLhs() == null)
					ac.setLhs(constant);
				else
					ac.setRhs(constant);
			}
		}
		ibexPattern.getAttributeConstraint().add(ac);

		return true;
	}

	protected void createInvocation(IBeXContextPattern invoker, IBeXContextPattern invokee, boolean isPositive) {
		IBeXPatternInvocation invocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(isPositive);

		// Creating mapping for invocation: missing signature nodes of the invoked
		// pattern are added as local nodes to the invoking pattern
		for (IBeXNode node : invokee.getSignatureNodes()) {
			Optional<IBeXNode> src = IBeXPatternUtils.findIBeXNodeWithName(invoker, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else {
				IBeXNode newLocalNode = IBeXPatternModelFactory.eINSTANCE.createIBeXNode();
				newLocalNode.setName(node.getName());
				newLocalNode.setType(node.getType());
				invoker.getLocalNodes().add(newLocalNode);

				invocation.getMapping().put(newLocalNode, node);
			}
		}
		
		invocation.setInvokedPattern(invokee);
		invoker.getInvocations().add(invocation);
	}
}
