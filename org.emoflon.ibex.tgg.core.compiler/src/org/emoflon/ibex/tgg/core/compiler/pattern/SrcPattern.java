package org.emoflon.ibex.tgg.core.compiler.pattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class SrcPattern extends Pattern {

	public SrcPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == DomainType.SRC;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return isRelevantForSignature(e) && e.getBindingType() == BindingType.CREATE;
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_SRC";
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return n.getBindingType() == BindingType.CREATE;
	}

}
