package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_MergeAndPreserve;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_RevokeAddition;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_RevokeDeletion;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

import language.DomainType;
import language.TGGRuleNode;

public abstract class DeletePreserveConflict extends Conflict
		implements CRS_MergeAndPreserve, CRS_RevokeDeletion, CRS_RevokeAddition, CRS_PreferSource, CRS_PreferTarget {

	protected final List<ITGGMatch> causingMatches;
	protected final DomainType domainToBePreserved;

	public DeletePreserveConflict(ConflictContainer container, DomainType domainToBePreserved) {
		super(container);
		this.causingMatches = getAndSortCausingMatches();
		this.domainToBePreserved = domainToBePreserved;
	}

	private List<ITGGMatch> getAndSortCausingMatches() {
		PrecedenceGraph pg = integrate().getPrecedenceGraph();
		PrecedenceNode node = pg.getNode(getBrokenMatch().getMatch());

		return new LinkedList<>(node.getRollbackCauses()).stream() //
				.sorted((n1, n2) -> n1.getRollbackCauses().size() - n2.getRollbackCauses().size()) //
				.map(n -> n.getMatch()) //
				.collect(Collectors.toList());
	}

	//// CRS ////

	@Override
	public void crs_mergeAndPreserve() {
		MatchAnalysis analysis = integrate().getMatchUtil().getAnalysis(getBrokenMatch().getMatch());

		causingMatches.forEach(m -> restoreMatch(integrate().getClassifiedBrokenMatches().get(m)));

		analysis.getElts(new EltFilter().srcAndTrg().create().deleted()).forEach(elt -> {
			if (elt instanceof TGGRuleNode) {
				analysis.getObject((TGGRuleNode) elt).eContents().forEach(child -> {
					if (!analysis.getObjects().contains(child))
						ModelChangeUtil.deleteElement(child, true);
				});
			}
		});
		resolved = true;
	}

	private Set<ITGGMatch> tmp_restored;

	@Override
	public void crs_revokeDeletion() {
		tmp_restored = new HashSet<>();
		causingMatches.forEach(match -> {
			if (!tmp_restored.contains(match)) {
				restoreMatch(integrate().getClassifiedBrokenMatches().get(match));
				tmp_restored.add(match);
				restoreMatchesBasedOn(match);
			}
		});
		resolved = true;
	}

	protected void restoreMatchesBasedOn(ITGGMatch match) {
		PrecedenceGraph pg = integrate().getPrecedenceGraph();
		pg.getNode(match).getRequiredBy().forEach(n -> {
			if (n.isBroken()) {
				ITGGMatch m = n.getMatch();
				if (!tmp_restored.contains(m)) {
					restoreMatch(integrate().getClassifiedBrokenMatches().get(m));
					tmp_restored.add(m);
					restoreMatchesBasedOn(m);
				}
			}
		});
	}

	@Override
	public void crs_preferSource() {
		switch (domainToBePreserved) {
		case SRC:
			crs_revokeDeletion();
			break;
		case TRG:
			crs_revokeAddition();
		default:
			break;
		}
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		switch (domainToBePreserved) {
		case SRC:
			crs_revokeAddition();
			break;
		case TRG:
			crs_revokeDeletion();
		default:
			break;
		}
		resolved = true;
	}

	public DomainType getDomainToBePreserved() {
		return domainToBePreserved;
	}
}
