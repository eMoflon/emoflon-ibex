package org.emoflon.ibex.common.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

abstract class MapWrapper<K, V> implements Map<K, V> {

	private Map<K, V> internalMap;

	protected Map<K, V> getInternalMap() {
		return this.internalMap;
	}

	protected void setInternalMap(final Map<K, V> internalMap) {
		this.internalMap = internalMap;
	}

	@Override
	public void clear() {
		this.internalMap.clear();
	}

	@Override
	public boolean containsKey(final Object arg0) {
		return this.internalMap.containsKey(arg0);
	}

	@Override
	public boolean containsValue(final Object arg0) {
		return this.internalMap.containsValue(this.internalMap);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return this.internalMap.entrySet();
	}

	@Override
	public V get(final Object arg0) {
		return this.internalMap.get(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.internalMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return this.internalMap.keySet();
	}

	@Override
	public V put(final K arg0, final V arg1) {
		return this.internalMap.put(arg0, arg1);
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> arg0) {
		this.internalMap.putAll(arg0);
	}

	@Override
	public V remove(final Object arg0) {
		return this.internalMap.remove(arg0);
	}

	@Override
	public int size() {
		return this.internalMap.size();
	}

	@Override
	public Collection<V> values() {
		return this.internalMap.values();
	}

}
