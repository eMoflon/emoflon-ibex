package org.emoflon.ibex.tgg.runtime;

import java.util.Optional;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

public interface IGreenInterpreter {

	/**
	 * Rule application can fail due to invalid attribute values (the provided match is not as expected).
	 */
	Optional<ITGGMatch> apply(TGGOperationalRule operationalRule, String ruleName, ITGGMatch match);
	
	int getNumOfCreatedNodes();
	
	int getNumOfCreatedCorrNodes();

	void createMarkers(String ruleName, ITGGMatch match);
}
