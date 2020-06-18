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
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.IntegrateMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil.EltFilter;

import language.DomainType;
import language.TGGRuleNode;
import precedencegraph.PrecedenceNode;

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
		IntegrateMatchContainer matchContainer = integrate().getIntegrMatchContainer();
		PrecedenceNode node = matchContainer.getNode(getBrokenMatch().getMatch());

		return new LinkedList<>(node.getRollbackCauses()).stream() //
				.sorted((n1, n2) -> n1.getRollbackCauses().size() - n2.getRollbackCauses().size()) //
				.map(n -> matchContainer.getMatch(n)) //
				.collect(Collectors.toList());
	}

	//// CRS ////

	@Override
	public void crs_mergeAndPreserve() {
		MatchAnalysis analysis = integrate().getMatchUtil().getAnalysis(getBrokenMatch().getMatch());

		causingMatches.forEach(m -> restoreMatch(m));

		analysis.getElts(new EltFilter().srcAndTrg().create().deleted()).forEach(elt -> {
			if (elt instanceof TGGRuleNode) {
				analysis.getObject((TGGRuleNode) elt).eContents().forEach(child -> {
					if (!analysis.getObjects().contains(child))
						ModelChangeUtil.deleteElement(child, true);
				});
			}
		});
	}

	private Set<ITGGMatch> restored;

	@Override
	public void crs_revokeDeletion() {
		restored = new HashSet<>();
		causingMatches.forEach(match -> {
			if (!restored.contains(match)) {
				restoreMatch(match);
				restored.add(match);
				restoreMatchesBasedOn(match);
			}
		});
	}

	protected void restoreMatchesBasedOn(ITGGMatch match) {
		IntegrateMatchContainer matchContainer = integrate().getIntegrMatchContainer();
		matchContainer.getNode(match).getRequiredBy().forEach(n -> {
			if (n.isBroken()) {
				ITGGMatch m = matchContainer.getMatch(n);
				if (!restored.contains(m)) {
					restoreMatch(m);
					restored.add(m);
					restoreMatchesBasedOn(m);
				}
			}
		});
	}

	@Override
	public void crs_preferSource() {
		// TODO Auto-generated method stub

	}

	@Override
	public void crs_preferTarget() {
		// TODO Auto-generated method stub

	}

}
