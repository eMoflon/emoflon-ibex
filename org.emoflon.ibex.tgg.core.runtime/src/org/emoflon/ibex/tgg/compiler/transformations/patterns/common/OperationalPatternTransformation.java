package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import static org.emoflon.ibex.common.patterns.IBeXPatternUtils.findIBeXNodeWithName;
import static org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper.addInjectivityConstraintIfNecessary;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getFilterNACPatternName;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.tgg.compiler.patterns.ConclusionRule;
import org.emoflon.ibex.tgg.compiler.patterns.EdgeDirection;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.IBeXPatternOptimiser;
import org.emoflon.ibex.tgg.compiler.patterns.PACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PACCandidate;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.inv.DomainTypePatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.DomainType;
import language.TGG;
import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;
import language.TGGEnumExpression;
import language.TGGLiteralExpression;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleNode;

public abstract class OperationalPatternTransformation {
	protected static final String NODE_NAME = "FILTER_NAC_NODE";

	protected ContextPatternTransformation parent;
	protected IbexOptions options;
	protected TGGRule rule;

	protected FilterNACAnalysis filterNACAnalysis;

	public OperationalPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
		this.parent = parent;
		this.options = options;
		this.rule = rule;
		this.filterNACAnalysis = filterNACAnalysis;
	}

	protected abstract String getPatternName();

	protected abstract void transformNodes(IBeXContextPattern ibexPattern);

	protected abstract void transformEdges(IBeXContextPattern ibexPattern);

	protected abstract void transformNACs(IBeXContextPattern ibexPattern);
	
	protected boolean patternIsEmpty() {
		return rule.getNodes().isEmpty();
	}

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
		// Transform NACs
		transformNACs(ibexPattern);
		
		// Transform CEPs
		transformCEPs(ibexPattern);

		return ibexPattern;
	}

	private void transformCEPs(IBeXContextPattern ibexPattern) {
		for(TGGAttributeConstraint csp : rule.getAttributeConditionLibrary().getTggAttributeConstraints()) {
			IBeXCSP iCSP = IBeXPatternModelFactory.eINSTANCE.createIBeXCSP();
			iCSP.setName(csp.getDefinition().getName());
			if(csp.getDefinition().isUserDefined()) {
				iCSP.setName("UserDefined_" + iCSP.getName());
				iCSP.setPackage("org.emoflon.ibex.tgg.operational.csp.constraints.custom." + ((TGG) rule.eContainer()).getName().toLowerCase());
			}
			else {
				iCSP.setPackage("org.emoflon.ibex.tgg.operational.csp.constraints");
			}
			
			for(TGGParamValue param : csp.getParameters()) {
				if(param instanceof TGGAttributeExpression) {
					TGGAttributeExpression tggAttrExpr = (TGGAttributeExpression) param;
					IBeXAttributeExpression attrExpr = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeExpression();
					Optional<IBeXNode> iBeXNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, tggAttrExpr.getObjectVar().getName());
					if(iBeXNode.isPresent())
						attrExpr.setNode(iBeXNode.get());
					else
						break;
					attrExpr.setAttribute(tggAttrExpr.getAttribute());
					iCSP.getValues().add(attrExpr);
				}
				else if(param instanceof TGGEnumExpression) {
					TGGEnumExpression tggEnumExpr = (TGGEnumExpression) param;
					IBeXConstant constant = IBeXPatternModelFactory.eINSTANCE.createIBeXConstant();
					iCSP.getValues().add(constant);
					constant.setValue(tggEnumExpr.getLiteral());
					constant.setStringValue(tggEnumExpr.getEenum().getEPackage().getNsPrefix() + "." + tggEnumExpr.getEenum().getName() + "." + tggEnumExpr.getLiteral().getName());
				}
				else if(param instanceof TGGLiteralExpression) {
					TGGLiteralExpression tggLiteralExpr = (TGGLiteralExpression) param;
					IBeXConstant constant = IBeXPatternModelFactory.eINSTANCE.createIBeXConstant();
					iCSP.getValues().add(constant);
					constant.setValue(tggLiteralExpr.getValue());
					constant.setStringValue(tggLiteralExpr.getValue());
				}
			}
			
			if(iCSP.getValues().size() == csp.getParameters().size())
				ibexPattern.getCsps().add(iCSP);
		}
	}

	protected IBeXContextPattern createFilterNAC(IBeXContextPattern ibexPattern, FilterNACCandidate candidate) {
		
		if(parent.isTransformed(getFilterNACPatternName(candidate, rule))) {
			IBeXContextPattern nacPattern =  parent.getPattern(getFilterNACPatternName(candidate, rule));
			createNegativeInvocation(ibexPattern, nacPattern);
			return nacPattern;
		}
		
		// Root pattern
		IBeXContextPattern nacPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();

		// Transform nodes
		TGGRuleNode firstNode = candidate.getNodeInRule();
		IBeXNode firstIBeXNode = parent.transformNode(nacPattern, firstNode);

		addNodesOfSameTypeFromInvoker(nacPattern, candidate, firstIBeXNode);

		IBeXNode secondIBeXNode = IBeXPatternFactory.createNode(NODE_NAME, getOtherNodeType(candidate));
		nacPattern.getSignatureNodes().add(secondIBeXNode);

		// Transform edges
		if (candidate.getEDirection() == EdgeDirection.OUTGOING)
			parent.transformEdge(candidate.getEdgeType(), firstIBeXNode, secondIBeXNode, nacPattern, false);
		else
			parent.transformEdge(candidate.getEdgeType(), secondIBeXNode, firstIBeXNode, nacPattern, false);

		nacPattern.setName(getFilterNACPatternName(candidate, rule));

		// Invoke NAC from parent: nodes with/without pre-image are signature/local
		createNegativeInvocation(ibexPattern, nacPattern);

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(nacPattern);

		return nacPattern;
	}

	private void createNegativeInvocation(IBeXContextPattern invoker, IBeXContextPattern invokee) {
		IBeXPatternInvocation invocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(false);
		ArrayList<IBeXNode> localNodes = new ArrayList<>();

		for (IBeXNode node : invokee.getSignatureNodes()) {
			Optional<IBeXNode> src = findIBeXNodeWithName(invoker, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else
				localNodes.add(node);
		}

		invokee.getLocalNodes().addAll(localNodes);

		invocation.setInvokedPattern(invokee);
		invoker.getInvocations().add(invocation);
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

	private void addNodesOfSameTypeFromInvoker(IBeXContextPattern nacPattern,
			FilterNACCandidate candidate, IBeXNode inRuleNode) {
		TGGRuleNode nodeInRule = candidate.getNodeInRule();
		switch (candidate.getEDirection()) {
		case INCOMING:
			nodeInRule.getIncomingEdges().stream()//
					.filter(e -> e.getType().equals(candidate.getEdgeType()))//
					.forEach(e -> {
						IBeXNode node = parent.transformNode(nacPattern, e.getSrcNode());
						parent.transformEdge(candidate.getEdgeType(), node, inRuleNode, nacPattern, false);
					});
			break;

		case OUTGOING:
			nodeInRule.getOutgoingEdges().stream()//
					.filter(e -> e.getType().equals(candidate.getEdgeType()))//
					.forEach(e -> {
						IBeXNode node = parent.transformNode(nacPattern, e.getTrgNode());
						parent.transformEdge(candidate.getEdgeType(), inRuleNode, node, nacPattern, false);
					});
			break;

		default:
			break;
		}
	}

	private EClass getOtherNodeType(FilterNACCandidate candidate) {
		return candidate.getEDirection() == EdgeDirection.OUTGOING ? (EClass) candidate.getEdgeType().getEType()
				: (EClass) candidate.getEdgeType().eContainer();
	}
	
	protected IBeXContextPattern createPAC(IBeXContextPattern ibexPattern, DomainType domain, PACCandidate pacCandidate) {
			IBeXContextPattern premisePattern = createFilterNAC(ibexPattern, pacCandidate.getPremise());
//			premisePattern.setName(rule.getName() + "_" + pacCandidate.getPremise() + "__PAC");
			//if there is only one conclusion there is no need for a extra PAC-Pattern, a single negative pattern invocation is enough
			if(pacCandidate.getConclusionRules().size() == 1) {
				ConclusionRule conclusion = pacCandidate.getConclusionRules().get(0);
				IBeXContextPattern conclusionPattern = parent.getPattern(conclusion.getConclusionRule().getName() + "__" + domain);
				if(conclusionPattern == null) {
					return premisePattern;
				}
				else {
					createPACPatternInvocation(conclusionPattern, premisePattern, pacCandidate, conclusion);
				}
			}
			else if(pacCandidate.getConclusionRules().size() > 1) {
				IBeXContextPattern pacPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
				pacPattern.setName(rule.getName() + "_" + pacCandidate.getPremise() + "__PAC");
				IBeXPatternInvocation pacInv = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
				pacInv.setPositive(true);
				pacInv.setInvokedPattern(pacPattern);
				pacInv.setInvokedBy(premisePattern);
				
				for(ConclusionRule conclusionRule : pacCandidate.getConclusionRules()) {
					if(!parent.isTransformed(conclusionRule.getConclusionRule().getName() + "__" + domain)) {
						DomainTypePatternTransformation transformer = new DomainTypePatternTransformation(parent, options, conclusionRule.getConclusionRule(), domain);
						transformer.createDomainTypePattern();
					}
					IBeXContextPattern conclusionPattern = parent.getPattern(conclusionRule.getConclusionRule().getName() + "__" + domain);
					IBeXNode pacNode = IBeXPatternModelFactory.eINSTANCE.createIBeXNode();
					pacNode.setName(pacCandidate.getPremise().getNodeInRule().getName());
					pacNode.setType(pacCandidate.getPremise().getNodeInRule().getType());
					pacPattern.getSignatureNodes().add(pacNode);
					Optional<IBeXNode> premiseNode = IBeXPatternUtils.findIBeXNodeWithName(pacPattern, pacCandidate.getPremise().getNodeInRule().getName());
					if(premiseNode.isPresent())
						pacInv.getMapping().put(premiseNode.get(), pacNode);
					premisePattern.getInvocations().add(pacInv);
					createPACPatternInvocation(conclusionPattern, pacPattern, pacCandidate, conclusionRule);
				}
				parent.addContextPattern(pacPattern);
			}
			return premisePattern;		
	}
	/*
	 * creates a negative Pattern Invocation from the Pac used Pattern with the conclusion and connects the necessary node
	 */
	private void createPACPatternInvocation(IBeXContextPattern conclusionPattern, IBeXContextPattern pacPattern, PACCandidate pacCandidate, ConclusionRule conclusionRule) {
		IBeXPatternInvocation inv = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		inv.setPositive(false);
		inv.setInvokedPattern(conclusionPattern);
		inv.setInvokedBy(pacPattern);
		Optional<IBeXNode> pacNode = IBeXPatternUtils.findIBeXNodeWithName(pacPattern, pacCandidate.getPremise().getNodeInRule().getName());
		Optional<IBeXNode> conclusionNode = IBeXPatternUtils.findIBeXNodeWithName(conclusionPattern, conclusionRule.getPremiseConclusionNode().getName());
		if(pacNode.isPresent() && conclusionNode.isPresent())
			inv.getMapping().put(pacNode.get(), conclusionNode.get());
		pacPattern.getInvocations().add(inv);
	}
}
