package org.emoflon.ibex.common.collections.fastutil;

import org.emoflon.ibex.common.collections.IntToObjectMap;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

class FastUtilIntToObjectMap<T> extends IntToObjectMap<T> {

	FastUtilIntToObjectMap() {
		this.setInternalMap(new Int2ObjectOpenHashMap<T>());
	}

	@Override
	protected Int2ObjectOpenHashMap<T> getInternalMap() {
		return (Int2ObjectOpenHashMap<T>) super.getInternalMap();
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
