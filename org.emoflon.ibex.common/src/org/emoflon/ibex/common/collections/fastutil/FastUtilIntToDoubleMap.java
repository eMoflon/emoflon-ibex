package org.emoflon.ibex.common.collections.fastutil;

import java.util.Set;

import org.emoflon.ibex.common.collections.IntToDoubleMap;

import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;

public class FastUtilIntToDoubleMap extends IntToDoubleMap {
	private Int2DoubleOpenHashMap internal = new Int2DoubleOpenHashMap();
	
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
