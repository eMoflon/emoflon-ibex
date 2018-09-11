package org.emoflon.ibex.tgg.operational.repair;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

import runtime.TGGRuleApplication;

public abstract class AbstractRepairStrategy {

	abstract protected boolean isCandidate(TGGRuleApplication ra, IMatch iMatch);
	
	abstract protected IMatch repair(TGGRuleApplication ra, IMatch iMatch);

	abstract protected boolean checkIfRepairWasSucessful(TGGRuleApplication ra, IMatch oldMatch, IMatch newMatch);

	abstract protected boolean revokeRepair(TGGRuleApplication ra);
	
}
