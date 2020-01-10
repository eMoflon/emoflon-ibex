package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutPatternTool;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapUtil;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SyncDirection;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.FWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import runtime.TGGRuleApplication;

/**
 * This class attempts to repair broken matches by using operationalized
 * shortcut rules (OSR). These OSRs detect specific situations (like certain
 * deltas) and try to repair the broken match to be either a valid match of its
 * rule again or by transforming the match to that of another rule.
 * 
 * @author lfritsche
 *
 */
public class ShortcutRepairStrategy implements AbstractRepairStrategy {

	protected final static Logger logger = Logger.getLogger(AbstractRepairStrategy.class);
	
	private PropagatingOperationalStrategy operationalStrategy;
	private ShortcutPatternTool scTool;
	private SyncDirection syncDirection;
	
	public ShortcutRepairStrategy(PropagatingOperationalStrategy operationalStrategy) {
		this.operationalStrategy = operationalStrategy;
		
		// enable backward navigation for emf edges
		operationalStrategy.getOptions().getResourceHandler().getResourceSet().eAdapters().add(new ECrossReferenceAdapter());		
		initialize();
	}

	private void initialize() {
		OverlapUtil util = new OverlapUtil(operationalStrategy.getOptions());
		Collection<ShortcutRule> shortcutRules = util.calculateShortcutRules(operationalStrategy.getOptions().flattenedTGG());
		scTool = new ShortcutPatternTool(operationalStrategy, shortcutRules);
		updateDirection();
	}
	
	@Override
	public Collection<ITGGMatch> chooseMatches(Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications) {
		return brokenRuleApplications.keySet()//
				.stream()//
				.filter(this::noMissingNodes)//
				.map(brokenRuleApplications::get)//
				.collect(Collectors.toList());
	}
	
	private boolean noMissingNodes(TGGRuleApplication ra) {
//		return TGGPatternUtil.getAllNodes(ra).stream().noneMatch(n -> n == null);
		return true;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate) {
		updateDirection();
		ITGGMatch repairedMatch = scTool.processBrokenMatch(syncDirection, repairCandidate);
		if(repairedMatch != null)
			logger.info("Repaired: " + repairCandidate.getPatternName() + "->" + repairedMatch.getPatternName() + " (" + repairCandidate.hashCode() + "->" + repairedMatch.hashCode() + ")");
		return repairedMatch;
	}
	
	public ITGGMatch repair(ITGGMatch repairCandidate, SyncDirection direction) {
		ITGGMatch repairedMatch = scTool.processBrokenMatch(direction, repairCandidate);
		if(repairedMatch != null)
			logger.info("Repaired: " + repairCandidate.getPatternName() + "->" + repairedMatch.getPatternName() + " (" + repairCandidate.hashCode() + "->" + repairedMatch.hashCode() + ")");
		return repairedMatch;
	}

	private void updateDirection() {
		if(operationalStrategy instanceof SYNC) 
			syncDirection = operationalStrategy.getSyncStrategy() instanceof FWD_Strategy ? SyncDirection.FORWARD : SyncDirection.BACKWARD;
		else
			syncDirection = SyncDirection.UNDEFINED;
	}
	
	public int countDeletedElements() {
		return scTool.countDeletedElements();
	}
}
