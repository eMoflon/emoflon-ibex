package org.emoflon.ibex.common.collections.fastutil;

import java.util.Collection;

import org.emoflon.ibex.common.collections.IntToObjectMap;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class FastUtilIntToObjectMap<T> extends IntToObjectMap<T> {
	private Int2ObjectOpenHashMap<T> internal = new Int2ObjectOpenHashMap<>();

	@Override
	public T get(int i) {
		return internal.get(i);
	}

	@Override
	public void put(int i, T o) {
		internal.put(i, o);
	}

	@Override
	public Collection<Integer> keySet() {
		return internal.keySet();
	}

	@Override
	public int size() {
		return internal.size();
	}

	@Override
	public Collection<T> values() {
		return internal.values();
	}
}
