package org.emoflon.ibex.common.collections;

import java.util.PrimitiveIterator.OfInt;

public abstract class IntSet extends SetWrapper<Integer> implements IntCollection {

	public abstract boolean add(int i);

	public abstract boolean contains(int i);

	public abstract boolean remove(int i);

	@Override
	public abstract OfInt iterator();

}
