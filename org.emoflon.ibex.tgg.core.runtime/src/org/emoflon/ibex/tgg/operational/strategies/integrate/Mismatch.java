package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.EltClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;

public class Mismatch {

	private final ITGGMatch match;
	private final MatchClassificationComponent integrationFragment;
	private final Map<EObject, EltClassifier> classifiedNodes;
	private final Map<EMFEdge, EltClassifier> classifiedEdges;

	public Mismatch(ITGGMatch match, MatchClassificationComponent integrationFragment) {
		this.match = match;
		this.integrationFragment = integrationFragment;
		classifiedNodes = new HashMap<>();
		classifiedEdges = new HashMap<>();
	}

	public ITGGMatch getMatch() {
		return match;
	}

	public MatchClassificationComponent getMCC() {
		return integrationFragment;
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

}
