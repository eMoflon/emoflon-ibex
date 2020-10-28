package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getConsistencyPatternName;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.ACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.PACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ConsistencyPatternTransformation extends OperationalPatternTransformation {

	public ConsistencyPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
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
		for (TGGRuleEdge edge : rule.getEdges()) 
			if(options.patterns.optimizePattern()) {
				if(edge.getSrcNode().getBindingType() == BindingType.CREATE && edge.getSrcNode().getDomainType() == DomainType.CORR)
					parent.transformEdge(rule.getEdges(), edge, ibexPattern);
			}	
			else
				parent.transformEdge(rule.getEdges(), edge, ibexPattern);
		// Create protocol node and connections to nodes in pattern
		parent.createAndConnectProtocolNode(rule, ibexPattern);
	}
	
	@Override
	protected boolean patternIsEmpty() {
		return false;
	}
}
