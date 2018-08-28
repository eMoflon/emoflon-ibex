package org.emoflon.ibex.common.collections;

public abstract class IntToDoubleMap extends MapWrapper<Integer, Double> {

	public abstract double get(int k);

	public abstract void put(int k, double v);

	public abstract double addTo(int k, double v);
}
