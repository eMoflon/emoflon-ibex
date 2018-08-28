package org.emoflon.ibex.common.collections.fastutil;

import org.emoflon.ibex.common.collections.IntCollection;
import org.emoflon.ibex.common.collections.IntList;

import it.unimi.dsi.fastutil.ints.IntArrayList;

class FastUtilIntArrayList extends IntList {

	public FastUtilIntArrayList() {
		this.setInternalList(new IntArrayList());
	}

	public FastUtilIntArrayList(final IntCollection integers) {
		this.setInternalList(new IntArrayList(integers));
	}

	@Override
	protected IntArrayList getInternalList() {
		return (IntArrayList) super.getInternalList();
	}

	@Override
	public boolean add(final int i) {
		return this.getInternalList().add(i);
	}

	@Override
	public boolean contains(final int i) {
		return this.getInternalList().contains(i);
	}

	@Override
	public int removeInt(final int i) {
		return this.getInternalList().removeInt(i);
	}

}
