package org.emoflon.ibex.common.collections;

import java.util.Set;

public abstract class IntToDoubleMap {
	public abstract Set<Integer> keySet();
	public abstract double get(int k);
	public abstract void put(int k, double v);
}
