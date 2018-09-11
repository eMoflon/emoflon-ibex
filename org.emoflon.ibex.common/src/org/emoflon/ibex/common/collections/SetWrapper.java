package org.emoflon.ibex.common.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

abstract class SetWrapper<T> implements Set<T> {

	private Set<T> internalSet;

	protected Set<T> getInternalSet() {
		return this.internalSet;
	}

	protected void setInternalSet(final Set<T> internalSet) {
		this.internalSet = internalSet;
	}

	@Override
	public boolean add(final T arg0) {
		return this.internalSet.add(arg0);
	}

	@Override
	public boolean addAll(final Collection<? extends T> arg0) {
		return this.internalSet.addAll(arg0);
	}

	@Override
	public void clear() {
		this.internalSet.clear();
	}

	@Override
	public boolean contains(final Object arg0) {
		return this.internalSet.contains(arg0);
	}

	@Override
	public boolean containsAll(final Collection<?> arg0) {
		return this.internalSet.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.internalSet.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return this.internalSet.iterator();
	}

	@Override
	public boolean remove(final Object arg0) {
		return this.internalSet.remove(arg0);
	}

	@Override
	public boolean removeAll(final Collection<?> arg0) {
		return this.internalSet.removeAll(arg0);
	}

	@Override
	public boolean retainAll(final Collection<?> arg0) {
		return this.internalSet.retainAll(arg0);
	}

	@Override
	public int size() {
		return this.internalSet.size();
	}

	@Override
	public Object[] toArray() {
		return this.internalSet.toArray();
	}

	@Override
	public <T2> T2[] toArray(final T2[] arg0) {
		return this.internalSet.toArray(arg0);
	}
}
