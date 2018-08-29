package org.emoflon.ibex.common.collections;

import java.util.Collection;
import java.util.Set;

public abstract class IntToObjectMap<T> {
	public abstract T get(int k);
	public abstract void put(int k, T v);
	public abstract Set<Integer> keySet();
	public abstract int size();
	public abstract Collection<T> values();
	public abstract boolean containsKey(int k);
	public abstract boolean containsValue(T v);
}
