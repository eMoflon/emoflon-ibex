package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.ModelChangeProtocol;

import language.BindingType;
import language.TGGRule;

public abstract class ConflictResolutionStrategy {

	public ConflictResolutionStrategy(ConflResStratToken token) {
	}

	protected List<Notification> restoreMatch(INTEGRATE integrate, IMatch match) {
		List<Notification> undos = new ArrayList<>();

		ModelChangeProtocol mcp = integrate.getModelChangeProtocol();
		AnalysedMatch analysedMatch = integrate.getAnalysedMatches().get(match);
		TGGRule rule = integrate.getMatchAnalyser().getRule(match.getRuleName());
		rule.getNodes().forEach(n -> {
			if (analysedMatch.isRuleEltDeleted(n) && n.getBindingType().equals(BindingType.CREATE)) {
				EObject element = (EObject) match.get(n.getName());
				undos.addAll(mcp.util.undoDeleteNode(element));
			}
		});
		AnalysedMatch reanalysedMatch = integrate.getMatchAnalyser().analyse(match);
		rule.getEdges().forEach(e -> {
			if (reanalysedMatch.isRuleEltDeleted(e) && e.getBindingType().equals(BindingType.CREATE)) {
				EMFEdge element = integrate.getRuntimeEdge(match, e);
				undos.addAll(mcp.util.undoDeleteEdge(element));
			}
		});

		return undos;
	}

	/**
	 * Applies this conflict resolution strategy to the models.
	 * 
	 * @param integrate INTEGRATE
	 * @return List of <code>Notification</code>s representing user deltas which
	 *         were reverted as part of strategy application
	 */
	public abstract List<Notification> apply(INTEGRATE integrate);

}
