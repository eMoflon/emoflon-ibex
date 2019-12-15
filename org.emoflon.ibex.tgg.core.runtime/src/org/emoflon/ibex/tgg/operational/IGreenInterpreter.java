package org.emoflon.ibex.tgg.operational;

import java.util.Optional;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;

public interface IGreenInterpreter {

	/**
	 * Rule application can fail due to invalid attribute values (the provided match is not as expected).
	 */
	Optional<ITGGMatch> apply(IGreenPattern greenPattern, String ruleName, ITGGMatch match);
	
	int getNumOfCreatedElements();
}
