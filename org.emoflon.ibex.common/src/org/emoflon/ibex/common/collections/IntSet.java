package org.emoflon.ibex.common.collections;

import java.util.stream.Stream;

public abstract class IntSet {
	public abstract int size();
	public abstract Stream<Integer> stream();
	public abstract boolean isEmpty();
	public abstract void add(int i);
}
