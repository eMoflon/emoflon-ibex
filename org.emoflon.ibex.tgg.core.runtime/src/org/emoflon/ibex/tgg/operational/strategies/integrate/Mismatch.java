package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.EltClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

public class Mismatch {

	private final AnalysedMatch analysedMatch;
	private final MatchClassificationComponent integrationFragment;
	private final Map<EObject, EltClassifier> classifiedNodes;
	private final Map<EMFEdge, EltClassifier> classifiedEdges;

	public Mismatch(AnalysedMatch analysedMatch, MatchClassificationComponent integrationFragment) {
		this.analysedMatch = analysedMatch;
		this.integrationFragment = integrationFragment;
		classifiedNodes = new HashMap<>();
		classifiedEdges = new HashMap<>();
	}

	public AnalysedMatch getAnalysedMatch() {
		return analysedMatch;
	}

	public ITGGMatch getMatch() {
		return analysedMatch.getMatch();
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
