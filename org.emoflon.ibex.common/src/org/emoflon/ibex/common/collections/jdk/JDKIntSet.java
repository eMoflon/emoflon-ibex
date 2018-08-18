package org.emoflon.ibex.common.collections.jdk;

import java.util.HashSet;
import java.util.stream.Stream;

import org.emoflon.ibex.common.collections.IntSet;

public class JDKIntSet extends IntSet {
	private HashSet<Integer> internal = new HashSet<>();

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
}
