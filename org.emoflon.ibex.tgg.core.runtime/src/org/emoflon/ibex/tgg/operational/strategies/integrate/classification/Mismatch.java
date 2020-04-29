package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

public class Mismatch {

	private final ITGGMatch match;
	private final MatchClassifier matchClassifier;
	private final PropagationDirection propDirection;

	private final Map<EObject, ElementClassifier> classifiedNodes;
	private final Map<EMFEdge, ElementClassifier> classifiedEdges;

	public Mismatch(ITGGMatch match, MatchClassifier matchClassifier, PropagationDirection propDirection) {
		this.match = match;
		this.matchClassifier = matchClassifier;
		this.propDirection = propDirection;

		classifiedNodes = new HashMap<>();
		classifiedEdges = new HashMap<>();
	}

	public ITGGMatch getMatch() {
		return match;
	}

	public MatchClassifier getMatchClassifier() {
		return matchClassifier;
	}

	public PropagationDirection getPropagationDirection() {
		return propDirection;
	}

	public Map<EObject, ElementClassifier> getClassifiedNodes() {
		return classifiedNodes;
	}

	public Map<EMFEdge, ElementClassifier> getClassifiedEdges() {
		return classifiedEdges;
	}

	public void addClassification(EObject node, ElementClassifier classifier) {
		classifiedNodes.put(node, classifier);
	}

	public void addClassification(EMFEdge edge, ElementClassifier classifier) {
		classifiedEdges.put(edge, classifier);
	}

	public void resolveMismatch(INTEGRATE integrate) {
		integrate.deleteGreenCorrs(match);

		MatchAnalysis analysis = integrate.getMatchAnalyser().getAnalysis(match);
		Set<EObject> nodesToBeDeleted = new HashSet<>();
		Set<EMFEdge> edgesToBeDeleted = new HashSet<>();
		classifiedNodes.forEach((n, cl) -> {
			if (isDeleteClassifier(cl) && !analysis.isElementDeleted(analysis.getNode(n)))
				nodesToBeDeleted.add(n);
		});
		classifiedEdges.forEach((e, cl) -> {
			if (isDeleteClassifier(cl) && !analysis.isElementDeleted(analysis.getEdge(e)))
				edgesToBeDeleted.add(e);
		});
		integrate.getRedInterpreter().revoke(nodesToBeDeleted, edgesToBeDeleted);

		integrate.removeBrokenMatch(match);
	}

	private static boolean isDeleteClassifier(ElementClassifier classifier) {
		switch (classifier) {
		case NO_USE:
		case PENAL_USE:
			return true;
		case REWARDLESS_USE:
		case POTENTIAL_USE:
		case USE:
			return false;
		default:
			return false;
		}
	}
}
