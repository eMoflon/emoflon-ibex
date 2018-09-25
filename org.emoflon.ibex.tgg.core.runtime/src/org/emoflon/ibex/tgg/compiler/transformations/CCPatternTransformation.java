package org.emoflon.ibex.tgg.compiler.transformations;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.*;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.TGGComplementRule;
import language.TGGRule;

public class CCPatternTransformation extends OperationalPatternTransformation {

	public CCPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		super(parent, options);
	}
	
	@Override
	protected void handleComplementRules(TGGRule rule, IBeXContextPattern ibexPattern) {
		//if (rule instanceof TGGComplementRule)
		//	handleComplementRuleForGEN((TGGComplementRule) rule, ibexPattern);
	}
	
	@Override
	protected String getPatternName(TGGRule rule) {
		return getCCBlackPatternName(rule.getName());
	}
}
