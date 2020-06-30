package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGGRule;

public class GREENCORRPatternTransformation extends SplitUpPatternTransformation {
	
	public GREENCORRPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	protected String getPatternName() {
		return TGGPatternUtil.generateGREENCORRBlackPatternName(rule.getName());
	}
	
	public void createGREENCORRPattern() {
		createPatternFromBindingAndDomain(BindingType.CREATE, DomainType.CORR, getPatternName());
	}

}
