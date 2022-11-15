package org.emoflon.ibex.tgg.runtime.strategies.integrate.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.tgg.runtime.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.runtime.benchmark.Timer;
import org.emoflon.ibex.tgg.runtime.benchmark.Times;
import org.emoflon.ibex.tgg.runtime.debug.LoggerConfig;
import org.emoflon.ibex.tgg.runtime.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.PrecedenceMatchContainer;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.ClassifiedMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.ModelChanges;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.ModelChangeProtocol.ChangeKey;
import org.emoflon.ibex.tgg.util.ConsoleUtil;

import runtime.TGGRuleApplication;

public class Revoker implements TimeMeasurable {

	private final INTEGRATE opStrat;

	private final Times times;

	public Revoker(INTEGRATE opStrat) {
		this.opStrat = opStrat;

		this.times = new Times();
	}

	public void rollBack() {
		Timer.start();

		do {
			opStrat.getOptions().matchDistributor().updateMatches();
			revokeCurrentlyBrokenMatches();
		} while (!opStrat.getMatchHandler().noBrokenRuleApplications());

		times.addTo("rollBack", Timer.stop());
	}

	private void revokeCurrentlyBrokenMatches() {
		opStrat.matchClassifier().clearAndClassifyAll(opStrat.getMatchHandler().getBrokenMatches());

		for (ClassifiedMatch classifiedBrokenMatch : opStrat.matchClassifier().getAllClassifiedMatches())
			revokeBrokenMatch(classifiedBrokenMatch);
		((PrecedenceMatchContainer) opStrat.getMatchContainer()).clearPendingElements();

		if (!opStrat.getMatchHandler().noBrokenRuleApplications())
			LoggerConfig.log(LoggerConfig.log_ruleApplication(), () -> "");
	}

	private void revokeBrokenMatch(ClassifiedMatch classifiedMatch) {
		ITGGMatch brokenMatch = classifiedMatch.getMatch();

		deleteGreenCorrs(brokenMatch);

		Set<EObject> nodesToBeDeleted = new HashSet<>();
		Set<EMFEdge> edgesToBeDeleted = new HashSet<>();

		EltFilter filter = new EltFilter().create().notDeleted();
		if (DeletionType.propFWDCandidates.contains(classifiedMatch.getDeletionType()))
			filter.trg();
		else if (DeletionType.propBWDCandidates.contains(classifiedMatch.getDeletionType()))
			filter.src();
		else
			filter.srcAndTrg();

		TGGMatchUtil matchUtil = opStrat.matchUtils().get(brokenMatch);
		matchUtil.getEObjects(filter).forEach((o) -> nodesToBeDeleted.add(o));
		matchUtil.getEMFEdges(filter).forEach((e) -> edgesToBeDeleted.add(e));
		opStrat.getRedInterpreter().revoke(nodesToBeDeleted, edgesToBeDeleted);

		removeBrokenMatch(brokenMatch);

		LoggerConfig.log(LoggerConfig.log_ruleApplication(),
				() -> "Rule application: rolled back " + brokenMatch.getPatternName() + "(" + brokenMatch.hashCode() + ")\n" //
						+ ConsoleUtil.indent(ConsoleUtil.printMatchParameter(brokenMatch), 18, true));
	}

	public void removeBrokenMatch(ITGGMatch brokenMatch) {
		TGGRuleApplication ra = brokenMatch.getRuleApplicationNode();

		for (EReference ref : ra.eClass().getEAllReferences())
			ra.eUnset(ref);

		opStrat.getOptions().matchDistributor().updateMatches();

		if (opStrat.getMatchHandler().removeBrokenRuleApplication(ra) == null)
			throw new RuntimeException("Match is still valid and therefore cannot be removed!");
		if (ra.eResource() != null)
			ra.eResource().getContents().remove(ra);
		opStrat.precedenceGraph().removeMatch(brokenMatch);
		opStrat.multiplicityCounter().removeMatch(brokenMatch);
	}

	public void removeCorrs(Set<ITGGMatch> matches) {
		for (ITGGMatch match : matches) {
			deleteGreenCorrs(match);
			removeBrokenMatch(match);
		}
	}

	/**
	 * Deletes all green correspondence elements of the given match.
	 * 
	 * @param match Match
	 * 
	 */
	public void deleteGreenCorrs(ITGGMatch match) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();
		prepareGreenCorrDeletion(match, nodesToRevoke, edgesToRevoke);
		opStrat.getRedInterpreter().revoke(nodesToRevoke, edgesToRevoke);
	}

	/**
	 * <p>
	 * Determines green correspondence elements and adds them to the passed sets (nodesToRevoke &
	 * edgesToRevoke).
	 * </p>
	 * <p>
	 * To delete this elements call {@link IbexRedInterpreter#revoke(nodesToRevoke, edgesToRevoke)}.
	 * </p>
	 * 
	 * @param match
	 * @param nodesToRevoke
	 * @param edgesToRevoke
	 */
	private void prepareGreenCorrDeletion(ITGGMatch match, Set<EObject> nodesToRevoke, Set<EMFEdge> edgesToRevoke) {
		opStrat.matchUtils().get(match).getEObjects(new EltFilter().corr().create()) //
				.forEach(obj -> opStrat.ibexRedInterpreter().revokeCorr(obj, nodesToRevoke, edgesToRevoke));
	}

	public void revokeUntranslatedElements() {
		Set<EObject> untranslated = new HashSet<>();
		opStrat.getResourceHandler().getSourceResource().getAllContents().forEachRemaining(n -> untranslated.add(n));
		opStrat.getResourceHandler().getTargetResource().getAllContents().forEachRemaining(n -> untranslated.add(n));
		opStrat.getResourceHandler().getProtocolResource().getContents()
				.forEach(ra -> ra.eCrossReferences().forEach(obj -> untranslated.remove(obj)));

		opStrat.getRedInterpreter().revoke(untranslated, Collections.emptySet());
	}

	public ChangeKey revokeBrokenCorrsAndRuleApplNodes() {
		ChangeKey key = new ChangeKey();
		opStrat.modelChangeProtocol().registerKey(key);

		Map<TGGRuleApplication, ITGGMatch> processed = new HashMap<>();
		do {
			opStrat.getOptions().matchDistributor().updateMatches();
		} while (deleteCorrsAndRAs(processed));
		opStrat.getMatchHandler().getBrokenRA2ConsMatches().putAll(processed);

		opStrat.modelChangeProtocol().deregisterKey(key);
		return key;
	}

	private boolean deleteCorrsAndRAs(Map<TGGRuleApplication, ITGGMatch> processed) {
		if (opStrat.getMatchHandler().noBrokenRuleApplications())
			return false;
		opStrat.getMatchHandler().getBrokenRA2ConsMatches().forEach((ra, m) -> {
			this.deleteGreenCorrs(m);
			EMFManipulationUtils.delete(ra);
		});
		processed.putAll(opStrat.getMatchHandler().getBrokenRA2ConsMatches());
		opStrat.getMatchHandler().clearBrokenRuleApplications();
		return true;
	}

	public void restoreBrokenCorrsAndRuleApplNodes(ChangeKey key) {
		opStrat.getOptions().matchDistributor().updateMatches();

		ModelChanges changes = opStrat.modelChangeProtocol().getModelChanges(key);
		opStrat.getMatchHandler().getBrokenRA2ConsMatches().forEach(
				(ra, m) -> opStrat.matchUtils().get(m).getEObjects(new EltFilter().corr().create()).forEach(obj -> restoreNode(changes, obj)));
		opStrat.getMatchHandler().getBrokenRA2ConsMatches().forEach((ra, m) -> restoreNode(changes, ra));
	}

	private void restoreNode(ModelChanges changes, EObject node) {
		Resource resource = changes.containedInResource(node);
		if (resource != null) {
			resource.getContents().add(node);
			changes.getDeletedEdges(node).forEach(e -> {
				if (node.equals(e.getSource()))
					ModelChangeUtil.createEdge(e);
			});
		}
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
