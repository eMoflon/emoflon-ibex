package org.emoflon.ibex.common.collections;

import java.util.Collection;

public abstract class IntToObjectMap<T> {
	public abstract T get(int i);
	public abstract void put(int i, T o);
	public abstract Collection<Integer> keySet();
	public abstract int size();
	public abstract Collection<T> values();
	public abstract boolean containsKey(int i);
	public abstract boolean containsValue(T o);
}
