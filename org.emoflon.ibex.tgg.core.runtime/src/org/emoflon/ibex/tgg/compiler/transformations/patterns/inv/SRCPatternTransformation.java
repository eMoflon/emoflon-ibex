package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.PACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGGRule;

public class SRCPatternTransformation extends DomainTypePatternTransformation {
	
	
	public SRCPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule, DomainType.SRC);
	}
	
	protected String getPatternName() {
		return TGGPatternUtil.generateSRCBlackPatternName(rule.getName());
	}
	
	public void createSRCPattern() {
		thisPattern = createPatternFromBindingAndDomain(BindingType.CREATE, DomainType.SRC, getPatternName());
	}
	
	public IBeXContextPattern getPattern() {
		return thisPattern;
	}
	
}
