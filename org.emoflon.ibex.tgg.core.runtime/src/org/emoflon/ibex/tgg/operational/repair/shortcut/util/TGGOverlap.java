package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import java.util.Collection;
import java.util.Map;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import language.TGGRule;
import language.TGGRuleElement;

/**
 * Container class that contains the overlap information between a original and
 * a replacing rule. It contains the successfully mapped elements of both sides
 * as well as those elements that are to be created and deleted. Additionally,
 * it also contains context elements which were not mapped.
 * 
 * @author lfritsche
 *
 */
public class TGGOverlap {
	public TGGRule originalRule;
	public TGGRule replacingRule;
	public Map<TGGRuleElement, TGGRuleElement> mappings;
	public Map<TGGRuleElement, TGGRuleElement> revertMappings;

	public Map<String, String> nameMappings;

	public Collection<TGGRuleElement> unboundOriginalContext;
	public Collection<TGGRuleElement> unboundReplacingContext;
	public Collection<TGGRuleElement> deletions;
	public Collection<TGGRuleElement> creations;

	public TGGOverlap(TGGRule originalRule, TGGRule replacingRule, Map<TGGRuleElement, TGGRuleElement> mappings,
			Collection<TGGRuleElement> originalContext, Collection<TGGRuleElement> replacingContext,
			Collection<TGGRuleElement> deletions, Collection<TGGRuleElement> creations) {
		this(originalRule, replacingRule);
		this.mappings = mappings;
		mappings.keySet().stream().forEach(k -> nameMappings.put(k.getName(), mappings.get(k).getName()));
		mappings.keySet().stream().forEach(k -> revertMappings.put(mappings.get(k), k));
		this.unboundOriginalContext = originalContext;
		this.unboundReplacingContext = replacingContext;
		this.deletions = deletions;
		this.creations = creations;
	}

	public TGGOverlap(TGGRule sourceRule, TGGRule targetRule) {
		this.originalRule = sourceRule;
		this.replacingRule = targetRule;

		this.mappings = cfactory.createObjectToObjectHashMap();

		this.revertMappings = cfactory.createObjectToObjectHashMap();
		this.nameMappings = cfactory.createObjectToObjectHashMap();
		this.unboundOriginalContext = cfactory.createObjectSet();
		this.unboundReplacingContext = cfactory.createObjectSet();
		this.deletions = cfactory.createObjectSet();
		this.creations = cfactory.createObjectSet();
	}
}