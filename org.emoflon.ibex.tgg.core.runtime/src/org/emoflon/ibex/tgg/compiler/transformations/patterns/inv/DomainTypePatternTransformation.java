package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGGRule;

public class DomainTypePatternTransformation extends SplitUpPatternTransformation{
	
	IBeXContextPattern thisPattern;
	DomainType domain;
	
	public DomainTypePatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, DomainType domain) {
		super(parent, options, rule);
		this.domain = domain;
	}
	
	protected String getPatternName() {
		if(domain.equals(DomainType.SRC))
			return TGGPatternUtil.generateSRCBlackPatternName(rule.getName());
		else return TGGPatternUtil.generateTRGBlackPatternName(rule.getName());
	}
	
	public void createDomainTypePattern() {
		if(!parent.isTransformed(getPatternName()))
			thisPattern = createPatternFromBindingAndDomain(BindingType.CREATE, domain, getPatternName());
//		transformPACs();
	}
	
	public IBeXContextPattern getPattern() {
		return thisPattern;
	}
}
