package org.emoflon.ibex.tgg.operational.repair.strategies;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;

import runtime.TGGRuleApplication;

public class ShortcutRepairStrategy extends AbstractRepairStrategy{

	@Override
	protected boolean isCandidate(TGGRuleApplication ra, IMatch iMatch) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean repair(TGGRuleApplication ra, IMatch iMatch) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean checkIfRepairWasSucessful(TGGRuleApplication ra, IMatch oldMatch, IMatch newMatch) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean revokeRepair(TGGRuleApplication ra) {
		// TODO Auto-generated method stub
		return false;
	}

}
