package org.emoflon.ibex.common.collections.fastutil;

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
import org.emoflon.ibex.common.emf.EMFEdgeHashingStrategy;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class FastUtilCollectionFactory extends CollectionFactory {
	@Override
	public <K, V> Map<K, V> createObjectToObjectLinkedHashMap() {
		return new Object2ObjectOpenHashMap<>();
	}

	@Override
	public <K, V> Map<K, V> createObjectToObjectHashMap() {
		return new Object2ObjectOpenHashMap<>();
	}

	@Override
	public Set<EMFEdge> createEMFEdgeHashSet() {
		return new ObjectOpenCustomHashSet<>(new EMFEdgeHashingStrategy());
	}

	@Override
	public <T> Set<T> createObjectSet() {
		return new ObjectOpenHashSet<>();
	}

	@Override
	public <T> Set<T> createLinkedObjectSet() {
		return new ObjectLinkedOpenHashSet<>();
	}

	@Override
	public <T> IntToObjectMap<T> createIntToObjectHashMap() {
		return new FastUtilIntToObjectMap<>();
	}

	@Override
	public <T> Map<EMFEdge, T> createEMFEdgeHashMap() {
		return new Object2ObjectOpenCustomHashMap<>(new EMFEdgeHashingStrategy());
	}

	@Override
	public IntSet createIntSet() {
		return new FastUtilIntSet();
	}

	@Override
	public <T> ObjectToIntMap<T> createObjectToIntHashMap() {
		return new FastUtilObjectToIntMap<T>();
	}

	@Override
	public IntToDoubleMap createIntToDoubleMap() {
		return new FastUtilIntToDoubleMap();
	}

	@Override
	public IntSet createLinkedIntSet() {
		return new FastUtilLinkedIntSet();
	}

	@Override
	public IntSet createIntSet(final int[] is) {
		return new FastUtilIntSet(is);
	}

	@Override
	public IntList createIntArrayList() {
		return new FastUtilIntArrayList();
	}

	@Override
	public IntList createIntArrayList(final IntCollection integers) {
		return new FastUtilIntArrayList(integers);
	}

	@Override
	public IntToIntMap createIntToIntMap() {
		return new FastUtilIntToIntMap();
	}

	@Override
	public IntToIntMap createIntToIntLinkedMap() {
		return new FastUtilLinkedIntToIntMap();
	}
}