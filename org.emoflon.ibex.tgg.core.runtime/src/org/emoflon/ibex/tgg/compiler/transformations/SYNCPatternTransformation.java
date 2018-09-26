package org.emoflon.ibex.tgg.compiler.transformations;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getFWDBlackPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getBWDBlackPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getFilterNACPatternName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.tgg.compiler.patterns.EdgeDirection;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternInvocation;
import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class SYNCPatternTransformation {
	private static final String NODE_NAME = "FILTER_NAC_NODE";
	private ContextPatternTransformation parent;
	private IbexOptions options;

	public SYNCPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		this.parent = parent;
		this.options = options;
	}

	public void transform(TGGRule rule) {
		createFWDPattern(rule);
		createBWDPattern(rule);
		createConsistencyPattern(rule);
	}

	private void createBWDPattern(TGGRule rule) {
		String patternName = getBWDBlackPatternName(rule.getName());

		createSyncPattern(rule, patternName, DomainType.TRG, DomainType.SRC);
	}

	private void createConsistencyPattern(TGGRule rule) {
		IBeXContextPattern consistencyPattern = new ConsistencyPatternTransformation(parent, options)
				.createConsistencyPattern(rule);
		parent.addContextPattern(consistencyPattern, rule);

		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(DomainType.SRC, rule);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(consistencyPattern, candidate, rule));
		}

		filterNACAnalysis = new FilterNACAnalysis(DomainType.TRG, rule);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(consistencyPattern, candidate, rule));
		}
	}

	private void createFWDPattern(TGGRule rule) {
		String patternName = getFWDBlackPatternName(rule.getName());

		createSyncPattern(rule, patternName, DomainType.SRC, DomainType.TRG);
	}

	private void createSyncPattern(TGGRule rule, String patternName, DomainType inputDomain, DomainType outputDomain) {
		if (parent.isTransformed(patternName))
			return;

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);

		// Transform nodes.
		List<TGGRuleNode> contextNodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		contextNodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, inputDomain));
		for (final TGGRuleNode node : contextNodes)
			parent.transformNode(ibexPattern, node);

		// Transform attributes.
		for (final TGGRuleNode node : contextNodes)
			parent.transformInNodeAttributeConditions(ibexPattern, node);

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges.
		List<TGGRuleEdge> edges = TGGModelUtils.getReferencesByOperator(rule, BindingType.CONTEXT);
		edges.addAll(TGGModelUtils.getReferencesByOperatorAndDomain(rule, BindingType.CREATE, inputDomain));
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);

		parent.addContextPattern(ibexPattern, rule);

		// Output Domain User NACs
		for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, outputDomain))
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
		}

		// Filter NACs
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(inputDomain, rule);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate, rule));
		}

		// Complement rule
		// TODO: Multi-Amalgamation
	}

	private IBeXContextPattern createFilterNAC(IBeXContextPattern ibexPattern, FilterNACCandidate candidate,
			TGGRule rule) {
		// Root pattern
		IBeXContextPattern nacPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();

		// Transform nodes
		TGGRuleNode firstNode = candidate.getNodeInRule();
		IBeXNode firstIBeXNode = parent.transformNode(nacPattern, firstNode);

		addNodesOfSameTypeFromInvoker(rule, nacPattern, candidate);

		IBeXNode secondIBeXNode = IBeXPatternFactory.createNode(NODE_NAME, getOtherNodeType(candidate));
		nacPattern.getSignatureNodes().add(secondIBeXNode);

		// Transform attributes
		parent.transformInNodeAttributeConditions(nacPattern, firstNode);

		// Transform edges
		if (candidate.getEDirection() == EdgeDirection.OUTGOING)
			parent.transformEdge(candidate.getEdgeType(), firstIBeXNode, secondIBeXNode, nacPattern);
		else
			parent.transformEdge(candidate.getEdgeType(), secondIBeXNode, firstIBeXNode, nacPattern);

		nacPattern.setName(getFilterNACPatternName(candidate, rule));

		// Invoke NAC from parent: nodes with/without pre-image are signature/local
		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(false);
		ArrayList<IBeXNode> localNodes = new ArrayList<>();

		for (IBeXNode node : nacPattern.getSignatureNodes()) {
			Optional<IBeXNode> src = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else
				localNodes.add(node);
		}

		nacPattern.getLocalNodes().addAll(localNodes);

		invocation.setInvokedPattern(nacPattern);
		ibexPattern.getInvocations().add(invocation);

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(nacPattern);
		
		return nacPattern;
	}

	private void addNodesOfSameTypeFromInvoker(TGGRule rule, IBeXContextPattern nacPattern,
			FilterNACCandidate candidate) {
		TGGRuleNode nodeInRule = candidate.getNodeInRule();
		switch (candidate.getEDirection()) {
		case INCOMING:
			nodeInRule.getIncomingEdges().forEach(e -> parent.transformNode(nacPattern, e.getSrcNode()));
			break;

		case OUTGOING:
			nodeInRule.getOutgoingEdges().forEach(e -> parent.transformNode(nacPattern, e.getTrgNode()));
			break;

		default:
			break;
		}
	}

	private EClass getOtherNodeType(FilterNACCandidate candidate) {
		return candidate.getEDirection() == EdgeDirection.OUTGOING ? (EClass) candidate.getEdgeType().getEType()
				: (EClass) candidate.getEdgeType().eContainer();
	}
}
