package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getConsistencyPatternName;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.PACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ConsistencyPatternTransformation extends OperationalPatternTransformation {

	public ConsistencyPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return getConsistencyPatternName(rule.getName());
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		rule.getNodes().forEach(n -> {
			parent.transformNode(ibexPattern, n);
		});

		// Transform attributes
		for (final TGGRuleNode node : rule.getNodes()) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		if(options.invocation.useGreenCorrPattern() && options.invocation.usePatternInvocation()) {
			parent.createInvocation(ibexPattern, parent.getPattern(TGGPatternUtil.generateBWDBlackPatternName(rule.getName())));
			parent.createInvocation(ibexPattern, parent.getPattern(TGGPatternUtil.generateFWD_GREENCORRBlackPatternName(rule.getName())));
		}
		else {
			for (TGGRuleEdge edge : rule.getEdges()) 
				parent.transformEdge(rule.getEdges(), edge, ibexPattern);
			if(options.invocation.usePatternInvocation()) {
				parent.createInvocation(ibexPattern, parent.getPattern(TGGPatternUtil.generateBWDBlackPatternName(rule.getName())));
				parent.createInvocation(ibexPattern, parent.getPattern(TGGPatternUtil.generateFWDBlackPatternName(rule.getName())));
			}	
		}
		// Create protocol node and connections to nodes in pattern
		parent.createAndConnectProtocolNode(rule, ibexPattern);
//		}
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		if(options.propagate.optimizeSyncPattern())
			return;
		if(options.patterns.lookAheadStrategy().equals(FilterNACStrategy.PACS)) {
			for (PACCandidate candidate : ((PACAnalysis) filterNACAnalysis).computePACCandidates(rule,  DomainType.SRC)) {
				parent.addContextPattern(createPAC(ibexPattern,  DomainType.SRC, candidate));
			}
			for (PACCandidate candidate : ((PACAnalysis) filterNACAnalysis).computePACCandidates(rule,  DomainType.TRG)) {
				parent.addContextPattern(createPAC(ibexPattern,  DomainType.SRC, candidate));
			}
		}
		else {
			for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates(rule, DomainType.SRC)) {
				parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
			}

			for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates(rule, DomainType.TRG)) {
				parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
			}
		}
	}
}
