package org.emoflon.ibex.common.collections.jdk;

import java.util.LinkedHashMap;
import java.util.Set;

import org.emoflon.ibex.common.collections.IntToIntMap;

class JDKIntToIntLinkedMap extends IntToIntMap {

	JDKIntToIntLinkedMap() {
		this.setInternalMap(new LinkedHashMap<Integer, Integer>());
	}

	@Override
	protected LinkedHashMap<Integer, Integer> getInternalMap() {
		return (LinkedHashMap<Integer, Integer>) super.getInternalMap();
	}

	@Override
	public Set<Integer> keySet() {
		return this.getInternalMap().keySet();
	}

	@Override
	public int put(final int k, final int v) {
		Integer oldValue = this.getInternalMap().put(k, v);
		return oldValue != null ? oldValue : 0;
	}

	@Override
	public int get(final int k) {
		Integer value = this.getInternalMap().get(k);
		return value != null ? value : 0;
	}

	@Override
	public int addTo(final int k, final int v) {
		int oldValue = this.get(k);
		this.put(k, oldValue + v);
		return oldValue;
	}

}
