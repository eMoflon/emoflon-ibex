package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

public class Mismatch {

	private final ITGGMatch match;
	private final MatchClassificationComponent matchClassComp;
	private final PropagationDirection propDirection;

	private final Map<EObject, EltClassifier> classifiedNodes;
	private final Map<EMFEdge, EltClassifier> classifiedEdges;

	public Mismatch(ITGGMatch match, MatchClassificationComponent matchClassComp, PropagationDirection propDirection) {
		this.match = match;
		this.matchClassComp = matchClassComp;
		this.propDirection = propDirection;

		classifiedNodes = new HashMap<>();
		classifiedEdges = new HashMap<>();
	}

	public ITGGMatch getMatch() {
		return match;
	}

	public MatchClassificationComponent getMCC() {
		return matchClassComp;
	}

	public PropagationDirection getPropagationDirection() {
		return propDirection;
	}

	public Map<EObject, EltClassifier> getClassifiedNodes() {
		return classifiedNodes;
	}

	public Map<EMFEdge, EltClassifier> getClassifiedEdges() {
		return classifiedEdges;
	}

	public void addClassification(EObject node, EltClassifier classifier) {
		classifiedNodes.put(node, classifier);
	}

	public void addClassification(EMFEdge edge, EltClassifier classifier) {
		classifiedEdges.put(edge, classifier);
	}

	public void resolveMismatch(INTEGRATE integrate) {
		ModelChangeUtil util = integrate.getModelChangeProtocol().util;
		
		integrate.deleteGreenCorrs(match);
		classifiedEdges.forEach((e, cl) -> {
			if(isDeleteClassifier(cl))
				util.deleteEdge(e);
		});
		classifiedNodes.forEach((n, cl) -> {
			if(isDeleteClassifier(cl))
				util.deleteElement(n, true);
		});
		integrate.removeBrokenMatch(match);
	}

	private static boolean isDeleteClassifier(EltClassifier classifier) {
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
