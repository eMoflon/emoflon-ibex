package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_MergeAndPreserve;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_RevokeAddition;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_RevokeDeletion;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.DeletionChain;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.IntegrateMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil.EltFilter;

import language.TGGRuleNode;

public abstract class DeletePropConflict extends Conflict
		implements CRS_MergeAndPreserve, CRS_RevokeDeletion, CRS_RevokeAddition {

	protected final DeletionChain deletionChain;

	public DeletePropConflict(ConflictContainer container) {
		super(container);
		this.deletionChain = new DeletionChain(integrate(), getMatch());
	}

	public DeletionChain getDeletionChain() {
		return deletionChain;
	}
	
	//// CRS ////

	@Override
	public void crs_mergeAndPreserve() {
		MatchAnalysis analysis = integrate().getMatchUtil().getAnalysis(getMatch());

		deletionChain.foreachReverse(m -> restoreMatch(m));

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
		deletionChain.foreachReverse(match -> {
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

}
