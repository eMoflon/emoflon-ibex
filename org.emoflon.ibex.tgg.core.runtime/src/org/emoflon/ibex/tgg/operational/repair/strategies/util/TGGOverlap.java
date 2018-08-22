package org.emoflon.ibex.tgg.operational.repair.strategies.util;

import java.util.Collection;
import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.TGGRule;
import language.TGGRuleElement;

public class TGGOverlap {
	public TGGRule sourceRule;
	public TGGRule targetRule;
	public Map<TGGRuleElement, TGGRuleElement> mappings;
	public Map<String, String> nameMappings;
	public Collection<TGGRuleElement> unboundContext; 
	public Collection<TGGRuleElement> deletions;
	public Collection<TGGRuleElement> creations;

	public TGGOverlap(TGGRule sourceRule, TGGRule targetRule, Map<TGGRuleElement, TGGRuleElement> mappings, Collection<TGGRuleElement> context, Collection<TGGRuleElement> deletions, Collection<TGGRuleElement> creations) {
		this(sourceRule, targetRule);
		this.mappings = mappings;
		mappings.keySet().stream().forEach(k -> nameMappings.put(k.getName(), mappings.get(k).getName()));
		this.unboundContext = context;
		this.deletions = deletions;
		this.creations = creations;
	}
	
	public TGGOverlap(TGGRule sourceRule, TGGRule targetRule) {
		this.sourceRule = sourceRule;
		this.targetRule = targetRule;
		this.mappings = new Object2ObjectOpenHashMap<>();
		this.nameMappings = new Object2ObjectOpenHashMap<>();
		this.unboundContext = new ObjectOpenHashSet<>();
		this.deletions = new ObjectOpenHashSet<>();
		this.creations = new ObjectOpenHashSet<>();
	}
}