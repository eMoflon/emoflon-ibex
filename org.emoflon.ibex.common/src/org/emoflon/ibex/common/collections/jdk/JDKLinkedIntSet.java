package org.emoflon.ibex.common.collections.jdk;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.PrimitiveIterator.OfInt;

import org.emoflon.ibex.common.collections.IntSet;

class JDKLinkedIntSet extends IntSet {
	JDKLinkedIntSet() {
		this.setInternalSet(new LinkedHashSet<Integer>());
	}

	@Override
	protected LinkedHashSet<Integer> getInternalSet() {
		return (LinkedHashSet<Integer>) super.getInternalSet();
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
	public boolean remove(final int i) {
		return this.getInternalSet().contains(i);
	}

	@Override
	public OfInt iterator() {
		return new OfInt() {

			private final Iterator<Integer> it = JDKLinkedIntSet.this.getInternalSet().iterator();

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
}
