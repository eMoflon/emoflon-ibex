package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil.EltFilter;

import language.DomainType;

public class BrokenMatch {

	private final INTEGRATE integrate;

	private final ITGGMatch match;
	private final MatchAnalysis util;

	private final DeletionPattern deletionPattern;
	private DeletionType deletionType;
	private final Map<ITGGMatch, DomainType> filterNacViolations;
	private final Set<ConstrainedAttributeChanges> constrainedAttrChanges;

	public BrokenMatch(INTEGRATE integrate, ITGGMatch match) {
		this.integrate = integrate;
		this.match = match;
		this.util = integrate.getMatchUtil().getAnalysis(match);
		this.deletionPattern = util.createDelPattern();
		this.filterNacViolations = util.analyzeFilterNACViolations();
		this.constrainedAttrChanges = util.analyzeAttributeChanges();
		fillDeletionTypes();
	}

	private void fillDeletionTypes() {
		for (DeletionType type : integrate.getOptions().integration.pattern().getDeletionTypes()) {
			if (type.isType(this))
				this.deletionType = type;
		}
	}

	public ITGGMatch getMatch() {
		return match;
	}

	public DeletionPattern getDeletionPattern() {
		return deletionPattern;
	}

	public DeletionType getDeletionType() {
		return deletionType;
	}

	public Map<ITGGMatch, DomainType> getFilterNacViolations() {
		return filterNacViolations;
	}

	public Set<ConstrainedAttributeChanges> getConstrainedAttrChanges() {
		return constrainedAttrChanges;
	}

	public MatchAnalysis util() {
		return util;
	}

	public void rollbackBrokenMatch() {
		integrate.deleteGreenCorrs(match);

		Set<EObject> nodesToBeDeleted = new HashSet<>();
		Set<EMFEdge> edgesToBeDeleted = new HashSet<>();
		
		EltFilter filter = new EltFilter().create().notDeleted();
		if(DeletionType.getPropFWDCandidates().contains(deletionType))
			filter.trg();
		else if (DeletionType.getPropBWDCandidates().contains(deletionType))
			filter.src();
		else
			filter.srcAndTrg();
		
		integrate.getMatchUtil().getObjects(match, filter)
				.forEach((o) -> nodesToBeDeleted.add(o));
		integrate.getMatchUtil().getEMFEdges(match, filter)
				.forEach((e) -> edgesToBeDeleted.add(e));
		integrate.getRedInterpreter().revoke(nodesToBeDeleted, edgesToBeDeleted);

		integrate.removeBrokenMatch(match);
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
		builder.append(deletionPattern.toString());
		builder.append("\nDeletion Type [");
		builder.append("\n  " + deletionType);
		builder.append("\n]");
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
