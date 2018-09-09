package org.emoflon.ibex.common.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

abstract class ListWrapper<E> implements List<E> {

	private List<E> internalList;

	protected List<E> getInternalList() {
		return this.internalList;
	}

	protected void setInternalList(final List<E> internalSet) {
		this.internalList = internalSet;
	}

	@Override
	public boolean add(final E arg0) {
		return this.internalList.add(arg0);
	}

	@Override
	public void add(final int arg0, final E arg1) {
		this.internalList.add(arg0, arg1);
	}

	@Override
	public boolean addAll(final Collection<? extends E> arg0) {
		return this.internalList.addAll(arg0);
	}

	@Override
	public boolean addAll(final int arg0, final Collection<? extends E> arg1) {
		return this.internalList.addAll(arg0, arg1);
	}

	@Override
	public void clear() {
		this.internalList.clear();
	}

	@Override
	public boolean contains(final Object arg0) {
		return this.internalList.contains(arg0);
	}

	@Override
	public boolean containsAll(final Collection<?> arg0) {
		return this.internalList.containsAll(arg0);
	}

	@Override
	public E get(final int arg0) {
		return this.internalList.get(arg0);
	}

	@Override
	public int indexOf(final Object arg0) {
		return this.internalList.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.internalList.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return this.internalList.iterator();
	}

	@Override
	public int lastIndexOf(final Object arg0) {
		return this.internalList.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.internalList.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(final int arg0) {
		return this.internalList.listIterator(arg0);
	}

	@Override
	public boolean remove(final Object arg0) {
		return this.internalList.remove(arg0);
	}

	@Override
	public E remove(final int arg0) {
		return this.internalList.remove(arg0);
	}

	@Override
	public boolean removeAll(final Collection<?> arg0) {
		return this.internalList.removeAll(arg0);
	}

	@Override
	public boolean retainAll(final Collection<?> arg0) {
		return this.internalList.retainAll(arg0);
	}

	@Override
	public E set(final int arg0, final E arg1) {
		return this.internalList.set(arg0, arg1);
	}

	@Override
	public int size() {
		return this.internalList.size();
	}

	@Override
	public List<E> subList(final int arg0, final int arg1) {
		return this.internalList.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return this.internalList.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] arg0) {
		return this.internalList.toArray(arg0);
	}

}
