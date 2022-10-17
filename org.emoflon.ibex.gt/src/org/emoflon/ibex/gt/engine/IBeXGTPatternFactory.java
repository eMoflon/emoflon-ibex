package org.emoflon.ibex.gt.engine;

import org.emoflon.ibex.gt.api.IBeXGtAPI;

public abstract class IBeXGTPatternFactory {
	protected final IBeXGtAPI<?, ?, ?> api;

	public IBeXGTPatternFactory(final IBeXGtAPI<?, ?, ?> api) {
		this.api = api;
	}
}
