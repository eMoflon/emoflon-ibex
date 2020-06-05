package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.List;

public class HierarchicalConflict implements GeneralConflict {
	
	private final List<MatchConflict> conflictDependency;

	public HierarchicalConflict(List<MatchConflict> conflictDependency) {
		this.conflictDependency = conflictDependency;
	}

	public List<MatchConflict> getConflictDependency() {
		return conflictDependency;
	}

}
