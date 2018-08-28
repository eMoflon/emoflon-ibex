package org.emoflon.ibex.common.collections.fastutil;

import org.emoflon.ibex.common.collections.IntToIntMap;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;

class FastUtilLinkedIntToIntMap extends IntToIntMap {

	FastUtilLinkedIntToIntMap() {
		this.setInternalMap(new Int2IntLinkedOpenHashMap());
	}

	@Override
	protected Int2IntLinkedOpenHashMap getInternalMap() {
		return (Int2IntLinkedOpenHashMap) super.getInternalMap();
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
		return this.getInternalMap().addTo(k, v);
	}

}
