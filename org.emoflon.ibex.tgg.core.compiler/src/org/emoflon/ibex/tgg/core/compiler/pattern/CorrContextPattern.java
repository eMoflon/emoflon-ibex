package org.emoflon.ibex.tgg.core.compiler.pattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class CorrContextPattern extends Pattern {

	public CorrContextPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return e instanceof TGGRuleNode && e.getBindingType() == BindingType.CONTEXT;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_CORR_CONTEXT";
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return n.getDomainType() == DomainType.CORR;
	}

}
