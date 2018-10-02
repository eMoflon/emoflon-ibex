package org.emoflon.ibex.common.collections.jdk;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.collections.IntSet;

class JDKIntSet extends IntSet {
	JDKIntSet() {
		this.setInternalSet(new HashSet<Integer>());
	}

	public JDKIntSet(final int[] is) {
		this.setInternalSet(new HashSet<Integer>(Arrays.stream(is).boxed().collect(Collectors.toList())));
	}

	@Override
	protected HashSet<Integer> getInternalSet() {
		return (HashSet<Integer>) super.getInternalSet();
	}

	@Override
	public boolean add(final int i) {
		return this.getInternalSet().add(i);
	}

	@Override
	public boolean contains(final int i) {
		return this.getInternalSet().contains(i);
	}

	@Override
	public OfInt iterator() {
		return new OfInt() {

			private final Iterator<Integer> it = JDKIntSet.this.getInternalSet().iterator();

			@Override
			public boolean hasNext() {
				return this.it.hasNext();
			}

			@Override
			public int nextInt() {
				return this.it.next();
			}

		};
	}

	@Override
	public boolean remove(final int i) {
		return this.getInternalSet().contains(i);
	}
}
