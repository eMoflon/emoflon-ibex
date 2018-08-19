package org.emoflon.ibex.common.collections.jdk;

import java.util.HashMap;
import java.util.Set;

import org.emoflon.ibex.common.collections.IntToDoubleMap;

public class JDKIntToDoubleMap extends IntToDoubleMap {
	private HashMap<Integer, Double> internal = new HashMap<>();
	
	@Override
	public Set<Integer> keySet() {
		return internal.keySet();
	}

	@Override
	public double get(int k) {
		return internal.get(k);
	}

	@Override
	public void put(int k, double v) {
		internal.put(k, v);
	}

}
