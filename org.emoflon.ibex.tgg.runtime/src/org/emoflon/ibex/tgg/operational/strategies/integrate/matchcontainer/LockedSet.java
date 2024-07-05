package org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockedSet<E> extends HashSet<E> {

	private static final long serialVersionUID = 8024757431383098909L;
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public static <E> Set<E> create() {
		return new LockedSet<E>();
	}

	
	public void lock_for_read() {
		lock.readLock().lock();
	}
	
	public void unlock_for_read() {
		lock.readLock().unlock();
	}

	@Override
	public boolean add(E e) {
		lock.writeLock().lock();
		boolean b = super.add(e);
		lock.writeLock().unlock();
		return b;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		lock.writeLock().lock();
		boolean b = super.addAll(c);
		lock.writeLock().unlock();
		return b;
	}
	
	@Override
	public void clear() {
		lock.writeLock().lock();
		super.clear();
		lock.writeLock().unlock();
	}
	
	@Override
	public boolean remove(Object o) {
		lock.writeLock().lock();
		boolean b = super.remove(o);
		lock.writeLock().unlock();
		return b;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		lock.writeLock().lock();
		boolean b = super.removeAll(c);
		lock.writeLock().unlock();
		return b;
	}
}
