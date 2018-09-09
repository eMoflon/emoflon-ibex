package org.emoflon.ibex.common.collections.fastutil;

import org.emoflon.ibex.common.collections.IntToIntMap;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

class FastUtilIntToIntMap extends IntToIntMap {

	FastUtilIntToIntMap() {
		this.setInternalMap(new Int2IntOpenHashMap());
	}

	@Override
	protected Int2IntOpenHashMap getInternalMap() {
		return (Int2IntOpenHashMap) super.getInternalMap();
	}

	@Override
	public int get(final int k) {
		return this.getInternalMap().get(k);
	}

	@Override
	public int put(final int k, final int v) {
		return this.getInternalMap().put(k, v);
	}

	@Override
	public int addTo(final int k, final int v) {
		return this.getInternalMap().addTo(k, v);
	}

}
