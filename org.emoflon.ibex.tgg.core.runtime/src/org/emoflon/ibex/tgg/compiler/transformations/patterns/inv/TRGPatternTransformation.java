package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.PACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGGRule;

public class TRGPatternTransformation extends DomainTypePatternTransformation{
	
	public TRGPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule){
		super(parent, options, rule, DomainType.TRG);
	}
	
	protected String getPatternName() {
		return TGGPatternUtil.generateTRGBlackPatternName(rule.getName());
	}
	
	public void createTRGPattern() {
		thisPattern = createPatternFromBindingAndDomain(BindingType.CREATE, DomainType.TRG, getPatternName());
	}
	
	public IBeXContextPattern getPattern() {
		return thisPattern;
	}
}
