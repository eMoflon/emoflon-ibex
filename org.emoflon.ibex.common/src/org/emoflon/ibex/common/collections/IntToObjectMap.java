package org.emoflon.ibex.common.collections;

public abstract class IntToObjectMap<T> extends MapWrapper<Integer, T> {
	public abstract T get(int k);

	public abstract T put(int k, T v);

	public abstract boolean containsKey(int k);

	public abstract T remove(int k);
}
