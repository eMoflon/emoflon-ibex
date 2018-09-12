package org.emoflon.ibex.tgg.operational.repair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import runtime.TGGRuleApplication;

public class RepairStrategyController {

	protected final static Logger logger = Logger.getLogger(RepairStrategyController.class);
	
	private OperationalStrategy oStrategy;

	// a collection of matches which have been broken
	private Map<TGGRuleApplication, IMatch> pendingRepairs;
	
	// a collection of matches that may have been repaired
	private Map<TGGRuleApplication, AbstractRepairStrategy> repairCandidateToStrategy;
	
	// a collection of repair strategies to repair broken matches into valid ones
	private Collection<AbstractRepairStrategy> strategies;

	private Map<TGGRuleApplication, IMatch> repairCandidates;
	private Map<TGGRuleApplication, IMatch> recentConsistencyMatches;
	private Map<TGGRuleApplication, IMatch> brokenRuleApplications;
	
	public RepairStrategyController(OperationalStrategy operationalStrategy, 
			Map<TGGRuleApplication, IMatch> repairCandidates, 
			Map<TGGRuleApplication, IMatch> recentConsistencyMatches, 
			Map<TGGRuleApplication, IMatch> brokenRuleApplications) {
		this.repairCandidates = repairCandidates;
		this.recentConsistencyMatches = recentConsistencyMatches;
		this.brokenRuleApplications = brokenRuleApplications;
		oStrategy = operationalStrategy;
		pendingRepairs = new Object2ObjectOpenHashMap<TGGRuleApplication, IMatch>();
		repairCandidateToStrategy = new Object2ObjectOpenHashMap<TGGRuleApplication, AbstractRepairStrategy>();
		strategies = new ArrayList<>();
	}

	public void registerStrategy(AbstractRepairStrategy strategy) {
		strategies.add(strategy);
	}
	
	public void clearStrategies() {
		strategies.clear();
	}
	
//	public Map<TGGRuleApplication, IMatch> getBrokenRuleApplications() {
//		return repairCandidates;
//	}
	
	/**
	 * This methods registers brokenRuleApplications which are processed by the registered repair strategies.
	 * After calling this method, the repairsSuccessfull method has to be called with newly registered matches
	 * to check whether the repairings were successful or not
	 * @param brokenRuleApplications
	 */
	public void repairMatches() {
		if(isSyncRunning())
			invokeStrategies();
	}
	
	/**
	 *  check if the repair was successful by searching for a new match for a TGGRuleApplication which was repaired by a strategy.
	 *  if no match can be found for a pending TGGRuleApplication, the previous repair step is assumed invalid and thus has to be revoked.
	 *  These elements can be accessed via getBrokenRuleApplications()
	 * @param recentConsistencyMatches
	 */
	public void repairsSuccessful() {
		Iterator<TGGRuleApplication> it = pendingRepairs.keySet().iterator();
		while(it.hasNext()) {
			TGGRuleApplication ra = it.next();
			IMatch match = pendingRepairs.get(ra);
			// check if a new match can be found. if so add it back to broken matches
			if(!recentConsistencyMatches.containsKey(ra)) {
				brokenRuleApplications.put(ra, match);
				logger.info(match.getPatternName() + " is considered broken (" + match.hashCode() + ")");
			}
			else {
				oStrategy.addOperationalRuleMatch(PatternSuffixes.removeSuffix(match.getPatternName()), match);
				logger.info(match.getPatternName() + " was found (" + match.hashCode() + ")");
			}
			// remove the candidate from both repair maps. Whether the repair was successful or not, no further repairs will take place here.
			repairCandidates.remove(ra);
		}
		repairCandidateToStrategy.clear();
		pendingRepairs.clear();
	}
	
	public boolean repairCandidatesPending() {
		return !strategies.isEmpty() && !pendingRepairs.isEmpty();
	}
	
	private void invokeStrategies() {
		for(TGGRuleApplication ra : repairCandidates.keySet()) {
			Iterator<AbstractRepairStrategy> it = strategies.iterator();
			while(it.hasNext()) {
				IMatch repairCandidate = repairCandidates.get(ra);
				AbstractRepairStrategy strategy = it.next();
				if(strategy.isCandidate(ra, repairCandidate)) {
					IMatch repairedMatch = strategy.repair(ra, repairCandidate);
					if(repairedMatch == null)
						continue;
					
					pendingRepairs.put(ra, repairedMatch);
					repairCandidateToStrategy.put(ra, strategy);
					
					if(repairCandidate.equals(repairedMatch))
						logger.info("Repaired: " + repairCandidate.getPatternName() + " (" + repairCandidate.hashCode() + ")");
					else
						logger.info("Repaired: " + repairCandidate.getPatternName() + "->" + repairedMatch.getPatternName() + " (" + repairCandidate.hashCode() + "->" + repairedMatch.hashCode() + ")");

					break;
				}
				if(!it.hasNext()) {
					brokenRuleApplications.put(ra, repairCandidate);
					repairCandidates.remove(ra);
				}
			}
		}
	}
	
	// TODO lfritsche: duplicate from OperationalStrategy
	public TGGRuleApplication getRuleApplicationNode(IMatch match) {
		return (TGGRuleApplication) match
				.get(ConsistencyPattern.getProtocolNodeName(PatternSuffixes.removeSuffix(match.getPatternName())));
	}
	
	private boolean isSyncRunning() {
		return oStrategy instanceof SYNC;
	}
}
