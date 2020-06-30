package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGGRule;

public class TRGPatternTransformation extends SplitUpPatternTransformation{
	
	public TRGPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}
	
	protected String getPatternName() {
		return TGGPatternUtil.generateTRGBlackPatternName(rule.getName());
	}
	
	public void createTRGPattern() {
		createPatternFromBindingAndDomain(BindingType.CREATE, DomainType.TRG, getPatternName());
	}
}
