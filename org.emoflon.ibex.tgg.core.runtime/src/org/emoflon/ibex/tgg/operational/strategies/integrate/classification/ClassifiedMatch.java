package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchAnalyzer.ConstrainedAttributeChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil;

import language.DomainType;

public class ClassifiedMatch {

	private final INTEGRATE integrate;

	private final ITGGMatch match;
	private final TGGMatchUtil util;

	private final MatchStatus matchStatus;
	private final DeletionPattern deletionPattern;

	private DeletionType deletionType;
	private final Map<ITGGMatch, DomainType> filterNacViolations;
	private final Set<ConstrainedAttributeChanges> constrainedAttrChanges;
	// TODO adrianm: add violated in-place attribute expressions
	
	public enum MatchStatus {
		INTACT, BROKEN, IMPLICIT_BROKEN;
	}

	public ClassifiedMatch(INTEGRATE integrate, PrecedenceNode node) {
		this.integrate = integrate;
		this.match = node.getMatch();
		this.matchStatus = calcMatchStatus(node);
		this.util = integrate.matchUtils().get(match);
		this.deletionPattern = util.analyzer().createDelPattern();
		this.filterNacViolations = util.analyzer().analyzeFilterNACViolations();
		this.constrainedAttrChanges = util.analyzer().analyzeAttributeChanges();
		fillDeletionTypes();
	}
	
	public ClassifiedMatch(INTEGRATE integrate, ITGGMatch match) {
		this(integrate, integrate.precedenceGraph().getNode(match));
	}

	private MatchStatus calcMatchStatus(PrecedenceNode node) {
		if (node.isBroken())
			return MatchStatus.BROKEN;
		if (integrate.precedenceGraph().getImplicitBrokenNodes().contains(node))
			return MatchStatus.IMPLICIT_BROKEN;
		return MatchStatus.INTACT;
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

	public boolean isImplicitBroken() {
		return matchStatus == MatchStatus.IMPLICIT_BROKEN;
	}

	public TGGMatchUtil util() {
		return util;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("ClassifiedMatch | ");
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
		b.append("Status [ ");
		b.append(matchStatus);
		b.append(" ]\n");
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
