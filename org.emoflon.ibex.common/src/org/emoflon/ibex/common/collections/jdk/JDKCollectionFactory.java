package org.emoflon.ibex.common.collections.jdk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntCollection;
import org.emoflon.ibex.common.collections.IntList;
import org.emoflon.ibex.common.collections.IntSet;
import org.emoflon.ibex.common.collections.IntToDoubleMap;
import org.emoflon.ibex.common.collections.IntToIntMap;
import org.emoflon.ibex.common.collections.IntToObjectMap;
import org.emoflon.ibex.common.collections.ObjectToIntMap;
import org.emoflon.ibex.common.emf.EMFEdge;

public class JDKCollectionFactory extends CollectionFactory {
	@Override
	public <K, V> Map<K, V> createObjectToObjectLinkedHashMap() {
		return new LinkedHashMap<>();
	}

	@Override
	public <K, V> Map<K, V> createObjectToObjectHashMap() {
		return new HashMap<>();
	}

	@Override
	public Set<EMFEdge> createEMFEdgeHashSet() {
		return new HashSet<>();
	}

	@Override
	public <T> Set<T> createObjectSet() {
		return new LinkedHashSet<>();
	}

	@Override
	public <T> IntToObjectMap<T> createIntToObjectHashMap() {
		return new JDKIntToObjectMap<>();
	}

	@Override
	public <T> Map<EMFEdge, T> createEMFEdgeHashMap() {
		return new HashMap<>();
	}

	@Override
	public IntSet createIntSet() {
		return new JDKIntSet();
	}

	@Override
	public <T> ObjectToIntMap<T> createObjectToIntHashMap() {
		return new JDKObjectToIntMap<T>();
	}

	@Override
	public IntToDoubleMap createIntToDoubleMap() {
		return new JDKIntToDoubleMap();
	}

	@Override
	public <T> Set<T> createLinkedObjectSet() {
		return new LinkedHashSet<T>();
	}

	@Override
	public IntSet createLinkedIntSet() {
		return new JDKLinkedIntSet();
	}

	@Override
	public IntSet createIntSet(final int[] is) {
		return new JDKIntSet(is);
	}

	@Override
	public IntList createIntArrayList() {
		return new JDKIntArrayList();
	}

	@Override
	public IntList createIntArrayList(final IntCollection integers) {
		return new JDKIntArrayList(integers);
	}

	@Override
	public IntToIntMap createIntToIntMap() {
		return new JDKIntToIntMap();
	}

	@Override
	public IntToIntMap createIntToIntLinkedMap() {
		return new JDKIntToIntLinkedMap();
	}
}