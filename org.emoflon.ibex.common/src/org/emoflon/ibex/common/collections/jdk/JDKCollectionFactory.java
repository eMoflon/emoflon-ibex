package org.emoflon.ibex.common.collections.jdk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntSet;
import org.emoflon.ibex.common.collections.IntToObjectMap;
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
		return new HashSet<>();
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
}