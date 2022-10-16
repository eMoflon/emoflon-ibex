package org.emoflon.ibex.gt.engine;

public abstract class IBeXGTRuleFactory {
	final IBeXGTEngine<?> gtEngine;

	public IBeXGTRuleFactory(final IBeXGTEngine<?> gtEngine) {
		this.gtEngine = gtEngine;
	}

	protected void registerTypedRule(IBeXGTRule<?, ?> rule) {
		gtEngine.registerTypedRule(rule);
	}
}
