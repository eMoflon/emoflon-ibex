package org.emoflon.ibex.common.collections.jdk;

import java.util.HashMap;
import java.util.Set;

import org.emoflon.ibex.common.collections.IntToIntMap;

class JDKIntToIntMap extends IntToIntMap {

	JDKIntToIntMap() {
		this.setInternalMap(new HashMap<Integer, Integer>());
	}

	@Override
	protected HashMap<Integer, Integer> getInternalMap() {
		return (HashMap<Integer, Integer>) super.getInternalMap();
	}

	@Override
	public Set<Integer> keySet() {
		return this.getInternalMap().keySet();
	}

	@Override
	public int get(final int k) {
		return this.getInternalMap().get(k);
	}

	@Override
	public void put(final int k, final int v) {
		this.getInternalMap().put(k, v);
	}

	@Override
	public int addTo(final int k, final int v) {
		int oldValue = this.get(k);
		this.put(k, oldValue + v);
		return oldValue;
	}

}
