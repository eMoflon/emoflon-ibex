package org.emoflon.ibex.common.collections.fastutil;

import org.emoflon.ibex.common.collections.ObjectToIntMap;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class FastUtilObjectToIntMap<T> extends ObjectToIntMap<T> {
	private Object2IntOpenHashMap<T> internal = new Object2IntOpenHashMap<T>();

	@Override
	public boolean containsKey(T k) {
		return internal.containsKey(k);
	}

	@Override
	public void put(T k, int v) {
		internal.put(k,v);
	}

	@Override
	public int getInt(T k) {
		return internal.getInt(k);
	}

}
