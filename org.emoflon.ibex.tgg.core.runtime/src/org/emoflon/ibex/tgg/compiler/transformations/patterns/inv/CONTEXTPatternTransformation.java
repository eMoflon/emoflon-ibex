package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperator;

import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CONTEXTPatternTransformation extends SplitUpPatternTransformation{
	
	String patternName = TGGPatternUtil.generateCONTEXTBlackPatternName(rule.getName());
	
	public CONTEXTPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}
	
	protected String getPatternName() {
		return patternName;
	}
	
	public void createCONTEXTPattern() {
		createPatternFromBinding(BindingType.CONTEXT, getPatternName());
	}
	
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
}
