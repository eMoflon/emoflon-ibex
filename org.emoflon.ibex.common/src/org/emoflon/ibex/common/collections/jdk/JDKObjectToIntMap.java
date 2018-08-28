package org.emoflon.ibex.common.collections.jdk;

import java.util.HashMap;

import org.emoflon.ibex.common.collections.ObjectToIntMap;

class JDKObjectToIntMap<T> extends ObjectToIntMap<T> {

	JDKObjectToIntMap() {
		this.setInternalMap(new HashMap<T, Integer>());
	}

	@Override
	protected HashMap<T, Integer> getInternalMap() {
		return (HashMap<T, Integer>) super.getInternalMap();
	}

	@Override
	public int put(final T key, final int value) {
		return this.getInternalMap().put(key, value);
	}

	@Override
	public int getInt(final T key) {
		return this.getInternalMap().get(key);
	}

	@Override
	public boolean containsValue(final int v) {
		return this.getInternalMap().containsValue(v);
	}

}
