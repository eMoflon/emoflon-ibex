package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;

import language.DomainType;

public class BrokenMatch {

	private final INTEGRATE integrate;

	private final ITGGMatch match;
	private final MatchAnalysis util;

	private final boolean broken;
	private final DeletionPattern deletionPattern;
	
	private DeletionType deletionType;
	private final Map<ITGGMatch, DomainType> filterNacViolations;
	private final Set<ConstrainedAttributeChanges> constrainedAttrChanges;
	// TODO adrianm: add violated in-place attribute expressions

	public BrokenMatch(INTEGRATE integrate, ITGGMatch match, boolean broken) {
		this.integrate = integrate;
		this.match = match;
		this.broken = broken;
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
	
	public boolean isBroken() {
		return broken;
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
		StringBuilder b = new StringBuilder();
		b.append("BrokenMatch | ");
		b.append(match.getPatternName());
		b.append("(");
		b.append(match.hashCode());
		b.append(") [");
		b.append("\n  " + print().replace("\n", "\n  "));
		b.append("\n]");
		return b.toString();
	}

	private String print() {
		StringBuilder b = new StringBuilder();
		b.append("DeletionType [ ");
		b.append(deletionType);
		b.append(" ]\n");
		b.append(deletionPattern.toString());
		b.append("\n");
		b.append("FilterNACViolations [");
		b.append("\n  " + printFilterNacViolations().replace("\n", "\n  "));
		b.append("\n]");
		b.append("\n");
		b.append("ConstrainedAttributeChanges [");
		b.append("\n  " + printConstrAttrChanges().replace("\n", "\n  "));
		b.append("\n]");
		return b.toString();
	}

	private String printFilterNacViolations() {
		StringBuilder b = new StringBuilder();
		for (ITGGMatch fnm : filterNacViolations.keySet()) {
			b.append(fnm.getRuleName());
			b.append("\n");
		}
		return b.length() == 0 ? b.toString() : b.substring(0, b.length() - 1);
	}
	
	private String printConstrAttrChanges() {
		StringBuilder b = new StringBuilder();
		for (ConstrainedAttributeChanges c : constrainedAttrChanges) {
			b.append(c.toString());
			b.append("\n");
		}
		return b.length() == 0 ? b.toString() : b.substring(0, b.length() - 1);
	}

}
