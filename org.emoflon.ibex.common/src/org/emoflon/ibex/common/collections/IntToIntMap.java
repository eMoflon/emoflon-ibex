package org.emoflon.ibex.common.collections;

public abstract class IntToIntMap extends MapWrapper<Integer, Integer> {

	public abstract int get(int k);

	public abstract void put(int k, int v);

	public abstract int addTo(int k, int v);
}
