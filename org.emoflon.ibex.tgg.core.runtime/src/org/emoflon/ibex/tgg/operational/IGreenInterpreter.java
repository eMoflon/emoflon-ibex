package org.emoflon.ibex.tgg.operational;

import java.util.Optional;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;

public interface IGreenInterpreter {

	/**
	 * Rule application can fail due to invalid attribute values (the provided match is not as expected).
	 */
	Optional<IMatch> apply(IGreenPattern greenPattern, String ruleName, IMatch match);
}
