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
		Double value = this.getInternalMap().get(k);
		return value != null ? value : 0;
	}

	@Override
	public double put(final int k, final double v) {
		Double oldValue = this.getInternalMap().put(k, v);
		return oldValue != null ? oldValue : 0;
	}

	@Override
	public double addTo(final int k, final double v) {
		double oldValue = this.get(k);
		this.put(k, oldValue + v);
		return oldValue;
	}

}
