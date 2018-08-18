package org.emoflon.ibex.common.collections;

import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.collections.fastutil.FastUtilCollectionFactory;
import org.emoflon.ibex.common.emf.EMFEdge;

public abstract class CollectionFactory {
	public static final CollectionFactory INSTANCE = new FastUtilCollectionFactory();
	
	public abstract  <K,V> Map<K, V> createObjectToObjectLinkedHashMap();
	public abstract <K,V> Map<K, V> createObjectToObjectHashMap();
	public abstract Set<EMFEdge> createEMFEdgeHashSet();
	public abstract <T> Map<EMFEdge, T> createEMFEdgeHashMap();
	public abstract <T> Set<T> createObjectSet();
	public abstract <T> IntToObjectMap<T> createIntToObjectHashMap();
	public abstract IntSet createIntSet();
}

