package org.emoflon.ibex.gt.engine;

import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;

public abstract class IBeXGTRule<P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>>
		extends IBeXGTPattern<P, M> {

	public final String ruleName;
	protected final GTRule rule;

	public IBeXGTRule(IBeXGtAPI<? extends IBeXGTPatternMatcher<?, ?>, ?, ?> api, GTRule rule) {
		super(api, (GTPattern) rule.getPrecondition());
		this.ruleName = rule.getName();
		this.rule = rule;
		gtEngine.registerTypedRule(this);
	}

}
