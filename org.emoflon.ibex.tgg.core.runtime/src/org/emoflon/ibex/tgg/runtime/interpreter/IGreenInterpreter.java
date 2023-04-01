package org.emoflon.ibex.tgg.runtime.interpreter;

import java.util.Optional;

import org.emoflon.ibex.tgg.runtime.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

public interface IGreenInterpreter {

	/**
	 * Rule application can fail due to invalid attribute values (the provided match is not as expected).
	 */
	Optional<ITGGMatch> apply(TGGOperationalRule operationalRule, ITGGMatch match);
	
	void registerOperationalRule(TGGOperationalRule operationalRule);
	
	IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(ITGGMatch match);
	
	int getNumOfCreatedNodes();
	
	int getNumOfCreatedCorrNodes();

	void createMarkers(TGGOperationalRule operationalRule, ITGGMatch match);
}
