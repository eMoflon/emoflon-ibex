package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.TGGRule;

public abstract class GENPatternTransformation extends SplitUpPatternTransformation{
	
	String patternName = TGGPatternUtil.generateGENBlackPatternName(rule.getName());
	
	public GENPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}
	
	protected String getPatternName() {
		return patternName;
	}
	
	public void createGENPattern() {
		createPatternByBinding(BindingType.CONTEXT, rule.getName(), PatternSuffixes.GEN);
	}
	
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
}
