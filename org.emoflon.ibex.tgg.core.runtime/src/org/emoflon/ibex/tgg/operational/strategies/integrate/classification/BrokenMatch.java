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
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;

import language.DomainType;

public class BrokenMatch {

	private final INTEGRATE integrate;

	private final ITGGMatch match;
	private final MatchAnalysis util;

	private final MatchModification modificationPattern;
	private final Map<ITGGMatch, DomainType> filterNacViolations;
	private final Set<ConstrainedAttributeChanges> constrainedAttrChanges;

	private MatchClassifier matchClassifier = null;
	// TODO adrianm: move this into MatchClassifier
	private PropagationDirection propDirection;

	private final Map<EObject, ElementClassifier> classifiedNodes;
	private final Map<EMFEdge, ElementClassifier> classifiedEdges;

	public BrokenMatch(INTEGRATE integrate, ITGGMatch match) {
		this.integrate = integrate;
		this.match = match;
		this.util = integrate.getMatchUtil().getAnalysis(match);
		this.modificationPattern = util.createModPattern();
		this.filterNacViolations = util.analyzeFilterNACViolations();
		this.constrainedAttrChanges = util.analyzeAttributeChanges();
		this.propDirection = PropagationDirection.UNDEFINED;

		classifiedNodes = new HashMap<>();
		classifiedEdges = new HashMap<>();

		classify();
	}

	private void classify() {
		for (MatchClassifier matchClassifier : integrate.getOptions().integration.pattern().getMatchClassifier()) {
			if (matchClassifier.isApplicable(this)) {
				matchClassifier.classify(this);
				this.matchClassifier = matchClassifier;
				break;
			}
		}
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

	public void setPropagationDirection(PropagationDirection propDirection) {
		this.propDirection = propDirection;
	}

	public MatchModification getModPattern() {
		return modificationPattern;
	}

	public Map<ITGGMatch, DomainType> getFilterNacViolations() {
		return filterNacViolations;
	}

	public Set<ConstrainedAttributeChanges> getConstrainedAttrChanges() {
		return constrainedAttrChanges;
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

	public MatchAnalysis util() {
		return util;
	}

	public void resolveBrokenMatch() {
		integrate.deleteGreenCorrs(match);

		Set<EObject> nodesToBeDeleted = new HashSet<>();
		Set<EMFEdge> edgesToBeDeleted = new HashSet<>();
		classifiedNodes.forEach((n, cl) -> {
			if (isDeleteClassifier(cl) && !integrate.getGeneralModelChanges().isDeleted(n))
				nodesToBeDeleted.add(n);
		});
		classifiedEdges.forEach((e, cl) -> {
			if (isDeleteClassifier(cl) && !integrate.getGeneralModelChanges().isDeleted(e))
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BrokenMatch [");
		builder.append("\n  " + print().replace("\n", "\n  "));
		builder.append("\n]");
		return builder.toString();
	}

	private String print() {
		StringBuilder builder = new StringBuilder();
		builder.append("Match [");
		builder.append("\n  " + match.getPatternName());
		builder.append("\n]\n");
		builder.append(modificationPattern.toString());
		builder.append("\nFilterNAC Violations [");
		builder.append("\n  " + printFilterNacViolations().replace("\n", "\n  "));
		builder.append("\n]");
		return builder.toString();
	}

	private String printFilterNacViolations() {
		StringBuilder builder = new StringBuilder();
		for (ITGGMatch fnm : filterNacViolations.keySet()) {
			builder.append(fnm.getRuleName() + "\n");
		}
		return builder.length() == 0 ? builder.toString() : builder.substring(0, builder.length() - 1);
	}

}
