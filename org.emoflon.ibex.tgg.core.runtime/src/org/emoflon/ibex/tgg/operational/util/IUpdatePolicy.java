package org.emoflon.ibex.tgg.operational.util;

import java.util.HashMap;
import java.util.Set;

public interface IUpdatePolicy {
	
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer);
	
	public HashMap<String, Integer> getNumberOfApplications(Set<String> complementRules);

}
