package org.emoflon.ibex.tgg.operational;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public interface IRedInterpreter {

	void revokeOperationalRule(ITGGMatch match);

	int getNumOfDeletedElements();
}
