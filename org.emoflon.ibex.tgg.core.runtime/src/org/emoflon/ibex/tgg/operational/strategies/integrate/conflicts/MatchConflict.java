package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public interface MatchConflict extends GeneralConflict {

	ITGGMatch getMatch();
	
}
