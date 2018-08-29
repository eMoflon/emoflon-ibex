package org.emoflon.ibex.common.collections.fastutil;

import java.util.PrimitiveIterator.OfInt;
import java.util.stream.Stream;

import org.emoflon.ibex.common.collections.IntSet;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

public class FastUtilIntSet extends IntSet {
	private IntOpenHashSet internal = new IntOpenHashSet();

	@Override
	public int size() {
		return internal.size();
	}

	@Override
	public Stream<Integer> stream() {
		return internal.stream();
	}

	@Override
	public boolean isEmpty() {
		return internal.isEmpty();
	}

	@Override
	public void add(int i) {
		internal.add(i);
	}

	@Override
	public OfInt iterator() {
		return internal.iterator();
	}
}
