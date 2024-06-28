package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;

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

	public Set<TGGRuleElement> unboundOriginalContext;
	public Set<TGGRuleElement> unboundReplacingContext;
	public Set<TGGRuleElement> deletions;
	public Set<TGGRuleElement> creations;
	
	public OverlapCategory category;
	public Set<PatternType> nonOperationalizablePatterns = new HashSet<>();

	public TGGOverlap(TGGRule originalRule, TGGRule replacingRule, Map<TGGRuleElement, TGGRuleElement> mappings,
			Set<TGGRuleElement> originalContext, Set<TGGRuleElement> replacingContext,
			Set<TGGRuleElement> deletions, Set<TGGRuleElement> creations) {
		this(originalRule, replacingRule);
		this.mappings = mappings;
		mappings.keySet().stream().forEach(k -> revertMappings.put(mappings.get(k), k));
		this.unboundOriginalContext = originalContext;
		this.unboundReplacingContext = replacingContext;
		this.deletions = deletions;
		this.creations = creations;
	}

	public TGGOverlap(TGGRule originalRule, TGGRule replacingRule) {
		this.originalRule = originalRule;
		this.replacingRule = replacingRule;

		this.mappings = cfactory.createObjectToObjectHashMap();

		this.revertMappings = cfactory.createObjectToObjectHashMap();
		this.unboundOriginalContext = cfactory.createObjectSet();
		this.unboundReplacingContext = cfactory.createObjectSet();
		this.deletions = cfactory.createObjectSet();
		this.creations = cfactory.createObjectSet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((originalRule == null) ? 0 : originalRule.hashCode());
		result = prime * result + ((replacingRule == null) ? 0 : replacingRule.hashCode());
		result = prime * result + ((mappings == null) ? 0 : mappings.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TGGOverlap other = (TGGOverlap) obj;
		
		if (originalRule == null) {
			if (other.originalRule != null)
				return false;
		} else if (!originalRule.equals(other.originalRule))
			return false;
		
		if (replacingRule == null) {
			if (other.replacingRule != null)
				return false;
		} else if (!replacingRule.equals(other.replacingRule))
			return false;

		if (mappings == null) {
			if (other.mappings != null)
				return false;
		} else if (!mappings.equals(other.mappings))
			return false;
		
		return true;
	}
}