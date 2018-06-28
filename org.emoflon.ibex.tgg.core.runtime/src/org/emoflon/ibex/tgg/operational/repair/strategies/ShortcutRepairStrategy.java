package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Collection;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.OverlapUtil;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import runtime.TGGRuleApplication;

public class ShortcutRepairStrategy extends AbstractRepairStrategy{

	private Map<String, IGreenPatternFactory> factories;
	private OperationalStrategy operationalStrategy;

	public ShortcutRepairStrategy(OperationalStrategy operationalStrategy, Map<String, IGreenPatternFactory> factories) {
		this.factories = factories;
		this.operationalStrategy = operationalStrategy;
		
		initialize();
	}

	private void initialize() {
		OverlapUtil util = new OverlapUtil(operationalStrategy.getOptions());
		Collection<ShortcutRule> shortcutRules = util.calculateShortcutRules(operationalStrategy.getTGG());
		
	}
	
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
