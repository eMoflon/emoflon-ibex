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
		Integer oldValue = this.getInternalMap().put(key, value);
		return oldValue != null ? oldValue : 0;
	}

	@Override
	public int getInt(final T key) {
		Integer value = this.getInternalMap().get(key);
		return value != null ? value : 0;
	}

	@Override
	public boolean containsValue(final int v) {
		return this.getInternalMap().containsValue(v);
	}

}
