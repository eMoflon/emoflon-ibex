package org.emoflon.ibex.tgg.operational.strategies;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.opt.OPT;
import org.emoflon.ibex.tgg.operational.strategies.sync.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.sync.repair.AbstractRepairStrategy;

import language.TGGRuleEdge;
import runtime.TGGRuleApplication;

public abstract class ExtOperationalStrategy extends OperationalStrategy {

	// Repair
	protected Collection<AbstractRepairStrategy> repairStrategies = new ArrayList<>();
	protected Map<TGGRuleApplication, IMatch> brokenRuleApplications = CollectionFactory.cfactory
			.createObjectToObjectHashMap();
	protected IRedInterpreter redInterpreter;

	/***** Constructors *****/

	public ExtOperationalStrategy(IbexOptions options) throws IOException {
		super(options);
		redInterpreter = new IbexRedInterpreter(this);
	}

	public void registerRedInterpeter(IRedInterpreter redInterpreter) {
		this.redInterpreter = redInterpreter;
	}

	/***** Sync algorithm *****/

	protected void repair() {
		initializeRepairStrategy(options);

		// TODO loop this together with roll back
		translate();
		repairBrokenMatches();
	}

	protected abstract void initializeRepairStrategy(IbexOptions options);

	protected boolean repairBrokenMatches() {
		Collection<IMatch> alreadyProcessed = cfactory.createObjectSet();
		for (AbstractRepairStrategy rStrategy : repairStrategies) {
			for (IMatch repairCandidate : rStrategy.chooseMatches(brokenRuleApplications)) {
				if (alreadyProcessed.contains(repairCandidate))
					continue;

				IMatch repairedMatch = rStrategy.repair(repairCandidate);
				if (repairedMatch != null) {
					alreadyProcessed.add(repairCandidate);

					TGGRuleApplication oldRa = getRuleApplicationNode(repairCandidate);
					brokenRuleApplications.remove(oldRa);

					TGGRuleApplication newRa = getRuleApplicationNode(repairedMatch);
					brokenRuleApplications.put(newRa, repairedMatch);
					alreadyProcessed.add(repairedMatch);

					options.getBenchmarkLogger().addToNumOfMatchesRepaired(1);
				}
			}
		}
		return !alreadyProcessed.isEmpty();
	}

	protected void translate() {
		do
			blackInterpreter.updateMatches();
		while (processOneOperationalRuleMatch());
	}

	protected void rollBack() {
		do
			blackInterpreter.updateMatches();
		while (revokeBrokenMatches());
	}

	protected boolean revokeBrokenMatches() {
		// clear pending elements since every element that has not been repaired until
		// now has to be revoked
		if (operationalMatchContainer instanceof PrecedenceGraph)
			((PrecedenceGraph) operationalMatchContainer).clearPendingElements();

		if (brokenRuleApplications.isEmpty())
			return false;

		revokeAllMatches();

		return true;
	}

	protected void revokeAllMatches() {
		while (!brokenRuleApplications.isEmpty()) {
			Set<TGGRuleApplication> revoked = cfactory.createObjectSet();

			for (TGGRuleApplication ra : brokenRuleApplications.keySet()) {
				redInterpreter.revokeOperationalRule(brokenRuleApplications.get(ra));
				revoked.add(ra);

			}
			for (TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);
		}
	}	

	/***** Marker Handling *******/

	/**
	 * Override in subclass if markers for protocol are not required (this can speed
	 * up the translation process).
	 */
	@Override
	protected void handleSuccessfulRuleApplication(IMatch cm, String ruleName, IGreenPattern greenPattern) {
		createMarkers(greenPattern, cm, ruleName);
	}

	/***** Match and pattern management *****/
	
	@Override
	protected IMatchContainer createMatchContainer() {
		return new PrecedenceGraph(this);
	}

	public EMFEdge getRuntimeEdge(IMatch match, TGGRuleEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSrcNode().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTrgNode().getName());
		EReference ref = specificationEdge.getType();
		return new EMFEdge(src, trg, ref);
	}

	@Override
	protected void addConsistencyMatch(IMatch match) {
		super.addConsistencyMatch(match);

		TGGRuleApplication ruleAppNode = getRuleApplicationNode(match);
		if (brokenRuleApplications.containsKey(ruleAppNode)) {
			logger.info(match.getPatternName() + " (" + match.hashCode() + ") appears to be fixed.");
			brokenRuleApplications.remove(ruleAppNode);
		}

		operationalMatchContainer.matchApplied(match);
	}

	@Override
	public void removeMatch(org.emoflon.ibex.common.operational.IMatch match) {
		super.removeMatch(match);

		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY))
			addConsistencyBrokenMatch((IMatch) match);
	}

	protected void addConsistencyBrokenMatch(IMatch match) {
		TGGRuleApplication ra = getRuleApplicationNode(match);
		brokenRuleApplications.put(ra, match);

		consistencyMatches.remove(ra);
		operationalMatchContainer.removeMatch(match);
	}

	@Override
	protected Optional<IMatch> processOperationalRuleMatch(String ruleName, IMatch match) {
		Optional<IMatch> comatch = super.processOperationalRuleMatch(ruleName, match);
		return comatch;
	}

	@Override
	protected void collectDataToBeLogged() {
		super.collectDataToBeLogged();

		options.getBenchmarkLogger().setNumOfElementsCreated(greenInterpreter.getNumOfCreatedElements());
		options.getBenchmarkLogger().setNumOfElementsDeleted(redInterpreter.getNumOfDeletedElements());
	}
}
