package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import static org.emoflon.ibex.tgg.util.MAUtil.setFusedName;

import java.util.ArrayList;
import java.util.List;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.patterns.GreenFusedPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import IBeXLanguage.IBeXContextPattern;
import language.DomainType;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public abstract class FusedPatternTransformation extends OperationalPatternTransformation {
	protected TGGComplementRule crule;
	private DomainType inputDomain;
	private OperationalPatternTransformation kernelTransformer;
	private GreenFusedPatternFactory fusedFactory;

	public FusedPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule,
			DomainType inputDomain, OperationalStrategy strategy) {
		super(parent, options, rule);
		this.inputDomain = inputDomain;
		crule = (TGGComplementRule) rule;
		fusedFactory = new GreenFusedPatternFactory(getPatternName(), options, strategy);		
		kernelTransformer = getKernelTransformer();
	}

	protected abstract OperationalPatternTransformation getKernelTransformer();

	@Override
	protected String getPatternName() {
		return setFusedName(crule.getName(), crule.getKernel().getName()) + getSuffix();
	}

	protected abstract String getSuffix();

	@Override
	protected void handleComplementRules(IBeXContextPattern ibexPattern) {
		// Nothing
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> contextNodesInFusedPattern = determineContextNodes();

		for (final TGGRuleNode node : contextNodesInFusedPattern) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform in-node attributes.
		for (final TGGRuleNode node : contextNodesInFusedPattern) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}

	}

	private List<TGGRuleNode> determineContextNodes() {
		List<TGGRuleNode> contextNodesInFusedPattern = new ArrayList<>();
		contextNodesInFusedPattern.addAll(fusedFactory.getBlackCorrNodesInRule());
		contextNodesInFusedPattern.addAll(fusedFactory.getBlackSrcNodesInRule());
		contextNodesInFusedPattern.addAll(fusedFactory.getBlackTrgNodesInRule());

		switch (inputDomain) {
		case SRC:
			contextNodesInFusedPattern.addAll(fusedFactory.getGreenSrcNodesInRule());
			break;
		case TRG:
			contextNodesInFusedPattern.addAll(fusedFactory.getGreenTrgNodesInRule());
			break;
		default:
			break;
		}

		return contextNodesInFusedPattern;
	}

	private List<TGGRuleEdge> determineContextEdges() {
		List<TGGRuleEdge> contextEdgesInFusedPattern = new ArrayList<>();
		contextEdgesInFusedPattern.addAll(fusedFactory.getBlackCorrEdgesInRule());
		contextEdgesInFusedPattern.addAll(fusedFactory.getBlackSrcEdgesInRule());
		contextEdgesInFusedPattern.addAll(fusedFactory.getBlackTrgEdgesInRule());

		switch (inputDomain) {
		case SRC:
			contextEdgesInFusedPattern.addAll(fusedFactory.getGreenSrcEdgesInRule());
			break;
		case TRG:
			contextEdgesInFusedPattern.addAll(fusedFactory.getGreenTrgEdgesInRule());
			break;
		default:
			break;
		}

		return contextEdgesInFusedPattern;

	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> contextEdgesInFusedPattern = determineContextEdges();

		for (TGGRuleEdge edge : contextEdgesInFusedPattern)
			parent.transformEdge(contextEdgesInFusedPattern, edge, ibexPattern);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		kernelTransformer.transformNACs(ibexPattern);
	}
}
