package org.emoflon.ibex.common.collections.jdk;

import java.util.HashMap;
import java.util.Set;

import org.emoflon.ibex.common.collections.IntToDoubleMap;

class JDKIntToDoubleMap extends IntToDoubleMap {

	JDKIntToDoubleMap() {
		this.setInternalMap(new HashMap<Integer, Double>());
	}

	@Override
	protected HashMap<Integer, Double> getInternalMap() {
		return (HashMap<Integer, Double>) super.getInternalMap();
	}

	@Override
	public Set<Integer> keySet() {
		return this.getInternalMap().keySet();
	}

	@Override
	public double get(final int k) {
		return this.getInternalMap().get(k);
	}

	@Override
	public void put(final int k, final double v) {
		this.getInternalMap().put(k, v);
	}

	@Override
	public double addTo(final int k, final double v) {
		double oldValue = this.get(k);
		this.put(k, oldValue + v);
		return oldValue;
	}

}
