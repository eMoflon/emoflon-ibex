package org.emoflon.ibex.common.collections.jdk;

import java.util.HashMap;

import org.emoflon.ibex.common.collections.ObjectToIntMap;

public class JDKObjectToIntMap<T> extends ObjectToIntMap<T> {
	private HashMap<T, Integer> internal = new HashMap<>();
	
	@Override
	public boolean containsKey(T key) {
		return internal.containsKey(key);
	}

	@Override
	public void put(T key, int value) {
		internal.put(key, value);
	}

	@Override
	public int getInt(T key) {
		return internal.get(key);
	}

}
