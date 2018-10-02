package org.emoflon.ibex.common.collections.fastutil;

import java.util.PrimitiveIterator.OfInt;

import org.emoflon.ibex.common.collections.IntSet;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

class FastUtilIntSet extends IntSet {

	FastUtilIntSet() {
		this.setInternalSet(new IntOpenHashSet());
	}

	FastUtilIntSet(final int[] is) {
		this.setInternalSet(new IntOpenHashSet(is));
	}

	@Override
	protected IntOpenHashSet getInternalSet() {
		return (IntOpenHashSet) super.getInternalSet();
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
		return this.getInternalSet().iterator();
	}

	@Override
	public boolean remove(final int i) {
		return this.getInternalSet().remove(i);
	}
}
