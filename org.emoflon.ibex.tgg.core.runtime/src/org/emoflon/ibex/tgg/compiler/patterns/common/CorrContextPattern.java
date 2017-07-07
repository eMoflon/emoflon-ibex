package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.stream.Stream;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.BindingType;
import language.DomainType;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class CorrContextPattern extends RulePartPattern {

	public CorrContextPattern(PatternFactory factory) {
		super(factory.getRule());
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {

		if (e.getBindingType() == BindingType.CREATE)
			return false;
		if (e instanceof TGGRuleCorr)
			return true;
		if (e instanceof TGGRuleNode) {
			TGGRuleNode n = (TGGRuleNode) e;
			return Stream.concat(n.getIncomingCorrsSource().stream(), n.getIncomingCorrsTarget().stream())
					.anyMatch(c -> c.getBindingType() == BindingType.CONTEXT);
		}
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.CORR_CONTEXT;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return isRelevantForSignature(n) && n.getDomainType() == DomainType.CORR;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return !(node1.getDomainType() == DomainType.CORR && node2.getDomainType() == DomainType.CORR);
	}

}
