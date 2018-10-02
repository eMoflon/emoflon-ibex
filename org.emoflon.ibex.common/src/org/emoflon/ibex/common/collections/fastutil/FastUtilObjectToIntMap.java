package org.emoflon.ibex.common.collections.fastutil;

import org.emoflon.ibex.common.collections.ObjectToIntMap;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

class FastUtilObjectToIntMap<T> extends ObjectToIntMap<T> {

	FastUtilObjectToIntMap() {
		this.setInternalMap(new Object2IntOpenHashMap<T>());
	}

	@Override
	protected Object2IntOpenHashMap<T> getInternalMap() {
		return (Object2IntOpenHashMap<T>) super.getInternalMap();
	}

	@Override
	public int put(final T k, final int v) {
		return this.getInternalMap().put(k, v);
	}

	@Override
	public int getInt(final T k) {
		return this.getInternalMap().getInt(k);
	}

	@Override
	public boolean containsValue(final int v) {
		return this.getInternalMap().containsValue(v);
	}

}
