package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.TGGRule;

public class GENPatternTransformation extends SplitUpPatternTransformation{
	
	String patternName = TGGPatternUtil.generateGENBlackPatternName(rule.getName());
	
	public GENPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}
	
	protected String getPatternName() {
		return patternName;
	}
	
	public void createGENPattern() {
		createPatternFromBinding(BindingType.CONTEXT, rule.getName(), PatternSuffixes.GEN);
	}
	
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
}
