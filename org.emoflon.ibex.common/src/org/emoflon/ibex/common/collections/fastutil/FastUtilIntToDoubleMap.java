package org.emoflon.ibex.common.collections.fastutil;

import org.emoflon.ibex.common.collections.IntToDoubleMap;

import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;

class FastUtilIntToDoubleMap extends IntToDoubleMap {

	FastUtilIntToDoubleMap() {
		this.setInternalMap(new Int2DoubleOpenHashMap());
	}

	@Override
	protected Int2DoubleOpenHashMap getInternalMap() {
		return (Int2DoubleOpenHashMap) super.getInternalMap();
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
		return this.getInternalMap().addTo(k, v);
	}

}
