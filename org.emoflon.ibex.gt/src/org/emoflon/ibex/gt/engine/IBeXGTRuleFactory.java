package org.emoflon.ibex.gt.engine;

import org.emoflon.ibex.gt.api.IBeXGtAPI;

public abstract class IBeXGTRuleFactory {
	protected final IBeXGtAPI<?, ?, ?> api;

	public IBeXGTRuleFactory(IBeXGtAPI<?, ?, ?> api) {
		this.api = api;
	}
}
