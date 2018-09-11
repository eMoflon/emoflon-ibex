package org.emoflon.ibex.common.collections.jdk;

import java.util.ArrayList;

import org.emoflon.ibex.common.collections.IntCollection;
import org.emoflon.ibex.common.collections.IntList;

class JDKIntArrayList extends IntList {

	public JDKIntArrayList() {
		this.setInternalList(new ArrayList<>());
	}

	public JDKIntArrayList(final IntCollection integers) {
		this.setInternalList(new ArrayList<Integer>(integers));
	}

	@Override
	protected ArrayList<Integer> getInternalList() {
		return (ArrayList<Integer>) super.getInternalList();
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
		return this.getInternalList().remove(i);
	}

}
