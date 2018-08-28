package org.emoflon.ibex.common.collections.jdk;

import java.util.HashMap;

import org.emoflon.ibex.common.collections.IntToObjectMap;

class JDKIntToObjectMap<T> extends IntToObjectMap<T> {

	JDKIntToObjectMap() {
		this.setInternalMap(new HashMap<Integer, T>());
	}

	@Override
	protected HashMap<Integer, T> getInternalMap() {
		return (HashMap<Integer, T>) super.getInternalMap();
	}

	@Override
	public T get(final int i) {
		return this.getInternalMap().get(i);
	}

	@Override
	public T put(final int i, final T o) {
		return this.getInternalMap().put(i, o);
	}

	@Override
	public boolean containsKey(final int i) {
		return this.getInternalMap().containsKey(i);
	}

	@Override
	public T remove(final int k) {
		return this.getInternalMap().remove(k);
	}
}
