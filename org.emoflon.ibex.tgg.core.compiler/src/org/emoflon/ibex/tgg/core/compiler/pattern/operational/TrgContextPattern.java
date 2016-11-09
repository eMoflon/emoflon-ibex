package org.emoflon.ibex.tgg.core.compiler.pattern.operational;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class TrgContextPattern extends OperationalPattern {

	public TrgContextPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == DomainType.TRG && e.getBindingType() == BindingType.CONTEXT;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return isRelevantForSignature(e);
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_TRG_CONTEXT";
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return false;
	}

}
