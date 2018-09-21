package org.emoflon.ibex.common.collections.fastutil;

import java.util.PrimitiveIterator.OfInt;

import org.emoflon.ibex.common.collections.IntSet;

import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;

class FastUtilLinkedIntSet extends IntSet {

	FastUtilLinkedIntSet() {
		this.setInternalSet(new IntLinkedOpenHashSet());
	}

	@Override
	protected IntLinkedOpenHashSet getInternalSet() {
		return (IntLinkedOpenHashSet) super.getInternalSet();
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
		return this.getInternalSet().remove(i);
	}

	@Override
	public OfInt iterator() {
		return this.getInternalSet().iterator();
	}
}
