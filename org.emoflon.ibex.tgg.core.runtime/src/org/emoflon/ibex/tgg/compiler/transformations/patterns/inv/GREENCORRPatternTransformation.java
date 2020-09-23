package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGGRule;

public class GREENCORRPatternTransformation extends SplitUpPatternTransformation {
	
	public GREENCORRPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	protected String getPatternName() {
		return TGGPatternUtil.generateGREENCORRBlackPatternName(rule.getName());
	}
	
	public void createGREENCORRPattern() {
		createPatternByBindingAndDomain(BindingType.CREATE, DomainType.CORR, getPatternName());
	}

}
