package org.emoflon.ibex.tgg.operational.repair.util;

import java.util.Collection;
import java.util.Map;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import language.TGGRule;
import language.TGGRuleElement;

/**
 * Container class that contains the overlap information between a source and a target rule.
 * It contains the successfully mapped elements of both sides as well as those elements that
 * are to be created and deleted.
 * Additionally, it also contains context elements which were not mapped.
 * 
 * @author lfritsche
 *
 */
public class TGGOverlap {
	public TGGRule sourceRule;
	public TGGRule targetRule;
	public Map<TGGRuleElement, TGGRuleElement> mappings;
	public Map<TGGRuleElement, TGGRuleElement> revertMappings;
	
	public Map<String, String> nameMappings;
	
	public Collection<TGGRuleElement> unboundSrcContext; 
	public Collection<TGGRuleElement> unboundTrgContext; 
	public Collection<TGGRuleElement> deletions;
	public Collection<TGGRuleElement> creations;

	public TGGOverlap(TGGRule sourceRule, TGGRule targetRule, Map<TGGRuleElement, TGGRuleElement> mappings, Collection<TGGRuleElement> srcContext, Collection<TGGRuleElement> trgContext, Collection<TGGRuleElement> deletions, Collection<TGGRuleElement> creations) {
		this(sourceRule, targetRule);
		this.mappings = mappings;
		mappings.keySet().stream().forEach(k -> nameMappings.put(k.getName(), mappings.get(k).getName()));
		mappings.keySet().stream().forEach(k -> revertMappings.put(mappings.get(k), k));
		this.unboundSrcContext = srcContext;
		this.unboundTrgContext = trgContext;
		this.deletions = deletions;
		this.creations = creations;
	}
	
	public TGGOverlap(TGGRule sourceRule, TGGRule targetRule) {
		this.sourceRule = sourceRule;
		this.targetRule = targetRule;
		
		this.mappings = cfactory.createObjectToObjectHashMap();
		
		this.revertMappings = cfactory.createObjectToObjectHashMap();
		this.nameMappings = cfactory.createObjectToObjectHashMap();
		this.unboundSrcContext = cfactory.createObjectSet();
		this.unboundTrgContext = cfactory.createObjectSet();
		this.deletions = cfactory.createObjectSet();
		this.creations = cfactory.createObjectSet();
	}
}