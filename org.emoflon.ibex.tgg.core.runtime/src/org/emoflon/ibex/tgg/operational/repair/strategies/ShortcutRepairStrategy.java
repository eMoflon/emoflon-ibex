package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Collection;

import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.ShortcutPatternTool;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.SyncDirection;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.OverlapUtil;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.FWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import runtime.TGGRuleApplication;

public class ShortcutRepairStrategy extends AbstractRepairStrategy{

	private OperationalStrategy operationalStrategy;
	private ShortcutPatternTool scTool;
	private SyncDirection syncDirection;

	public ShortcutRepairStrategy(OperationalStrategy operationalStrategy) {
		this.operationalStrategy = operationalStrategy;
		operationalStrategy.getResourceSet().eAdapters().add(new ECrossReferenceAdapter());		
		initialize();
	}

	private void initialize() {
		OverlapUtil util = new OverlapUtil(operationalStrategy.getOptions());
		Collection<ShortcutRule> shortcutRules = util.calculateShortcutRules(operationalStrategy.getTGG());
		scTool = new ShortcutPatternTool(operationalStrategy, shortcutRules);
		updateDirection();
	}
	
	@Override
	protected boolean isCandidate(TGGRuleApplication ra, IMatch iMatch) {
		return ra.getNodeMappings().keySet().size() == ra.getNodeMappings().values().size();
	}

	@Override
	protected IMatch repair(TGGRuleApplication ra, IMatch iMatch) {
		updateDirection();
		return scTool.processBrokenMatch(syncDirection, iMatch);
	}

	@Override
	protected boolean checkIfRepairWasSucessful(TGGRuleApplication ra, IMatch oldMatch, IMatch newMatch) {
		return true;
	}

	@Override
	protected boolean revokeRepair(TGGRuleApplication ra) {
		return true;
	}

	private void updateDirection() {
		if(operationalStrategy instanceof SYNC) 
			syncDirection = ((SYNC) operationalStrategy).getStrategy() instanceof FWD_Strategy ? SyncDirection.FORWARD : SyncDirection.BACKWARD;
		else
			syncDirection = SyncDirection.UNDEFINED;
	}
}
