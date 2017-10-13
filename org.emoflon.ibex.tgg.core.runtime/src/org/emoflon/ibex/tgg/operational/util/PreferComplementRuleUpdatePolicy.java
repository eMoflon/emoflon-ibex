package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.Optional;

public class PreferComplementRuleUpdatePolicy implements UpdatePolicy {

	Collection<String> complementRulesNames;
	
	public PreferComplementRuleUpdatePolicy(Collection<String> complementRulesNames) {
		this.complementRulesNames = complementRulesNames;
	}
	
	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return complementRuleMatch(matchContainer).orElse(
				matchContainer.getNextRandom());
	}
	
	private Optional<IMatch> complementRuleMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getMatches().stream()
								 //.filter(m -> complementRulesNames.contains(m.patternName()))
								 .filter(m -> m.patternName().contains("Daughter2Female") || m.patternName().contains("Son2Male"))
								 .findAny();
	}

}