package org.emoflon.ibex.common.collections;

public abstract class ObjectToIntMap<T> extends MapWrapper<T, Integer> {
	public abstract int put(T k, int v);

	public abstract int getInt(T k);

	public abstract boolean containsValue(int v);
}
