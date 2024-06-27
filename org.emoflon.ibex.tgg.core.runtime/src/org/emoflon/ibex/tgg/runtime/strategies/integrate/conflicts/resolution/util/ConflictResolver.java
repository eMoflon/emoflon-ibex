package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.util;

import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.ConflictContainer;

public interface ConflictResolver {
	
	void resolveConflict(ConflictContainer conflict);
	
}
