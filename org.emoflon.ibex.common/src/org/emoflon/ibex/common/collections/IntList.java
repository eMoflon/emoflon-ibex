package org.emoflon.ibex.common.collections;

public abstract class IntList extends ListWrapper<Integer> implements IntCollection {

	public abstract boolean add(int i);

	public abstract boolean contains(int i);

	public abstract int removeInt(int i);

}
