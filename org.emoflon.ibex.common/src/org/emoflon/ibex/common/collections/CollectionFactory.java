package org.emoflon.ibex.common.collections;

import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.collections.jdk.JDKCollectionFactory;
import org.emoflon.ibex.common.emf.EMFEdge;

public abstract class CollectionFactory {
	public static final CollectionFactory cfactory = new JDKCollectionFactory();
		//	= new FastUtilCollectionFactory();

	public abstract <K, V> Map<K, V> createObjectToObjectLinkedHashMap();

	public abstract <K, V> Map<K, V> createObjectToObjectHashMap();

	public abstract Set<EMFEdge> createEMFEdgeHashSet();

	public abstract <T> Map<EMFEdge, T> createEMFEdgeHashMap();

	public abstract <T> Set<T> createObjectSet();

	public abstract <T> Set<T> createLinkedObjectSet();

	public abstract IntSet createLinkedIntSet();

	public abstract <T> IntToObjectMap<T> createIntToObjectHashMap();

	public abstract <T> ObjectToIntMap<T> createObjectToIntHashMap();

	public abstract IntSet createIntSet();

	public abstract IntList createIntArrayList();

	public abstract IntList createIntArrayList(IntCollection integers);

	public abstract IntSet createIntSet(int[] is);

	public abstract IntToDoubleMap createIntToDoubleMap();

	public abstract IntToIntMap createIntToIntMap();

	public abstract IntToIntMap createIntToIntLinkedMap();

}
