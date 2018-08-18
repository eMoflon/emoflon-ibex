package org.emoflon.ibex.common.collections.jdk;

import java.util.Collection;
import java.util.HashMap;

import org.emoflon.ibex.common.collections.IntToObjectMap;

public class JDKIntToObjectMap<T> extends IntToObjectMap<T> {
	private HashMap<Integer, T> internal = new HashMap<>();

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

	@Override
	public boolean containsKey(int i) {
		return internal.containsKey(i);
	}

	@Override
	public boolean containsValue(T o) {
		return internal.containsValue(o);
	}
}
