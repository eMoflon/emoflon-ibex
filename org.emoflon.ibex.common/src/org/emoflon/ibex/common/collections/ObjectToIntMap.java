package org.emoflon.ibex.common.collections;

public abstract class ObjectToIntMap<T> {
	public abstract boolean containsKey(T k);
	public abstract void put(T k, int v);
	public abstract int getInt(T k);
}
