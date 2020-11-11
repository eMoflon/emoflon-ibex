package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.List;
import java.util.Set;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.util.TGGEdgeUtil;

import language.BindingType;
import language.DomainType;
import language.TGGRuleEdge;

public class DelPreserveEdgeConflict extends DeletePreserveConflict {

	private final ITGGMatch srcTrgMatch;

	public DelPreserveEdgeConflict(ConflictContainer container, ITGGMatch srcTrgMatch, DomainType domainToBePreserved,
			List<ITGGMatch> causingMatches) {
		super(container, domainToBePreserved, causingMatches);
		this.srcTrgMatch = srcTrgMatch;
	}
	
	public DelPreserveEdgeConflict(ConflictContainer container, ITGGMatch srcTrgMatch, DomainType domainToBePreserved,
			List<ITGGMatch> causingMatches, ITGGMatch matchToBeRepaired) {
		super(container, domainToBePreserved, causingMatches, matchToBeRepaired);
		this.srcTrgMatch = srcTrgMatch;
	}

	@Override
	protected Set<ITGGMatch> initConflictMatches() {
		Set<ITGGMatch> matches = super.initConflictMatches();
		matches.add(srcTrgMatch);
		return matches;
	}

	//// CRS ////

	@Override
	public void crs_revokeAddition() {
		for (TGGRuleEdge e : integrate().getMatchUtil().getEdges(srcTrgMatch, new EltFilter().create())) {
			if (e.getSrcNode().getBindingType() != BindingType.CONTEXT && e.getTrgNode().getBindingType() != BindingType.CONTEXT)
				return;

			EMFEdge emfEdge = TGGEdgeUtil.getRuntimeEdge(srcTrgMatch, e);
			if (emfEdge.getType().isContainment())
				ModelChangeUtil.deleteElement(emfEdge.getTarget(), true);
			else
				ModelChangeUtil.deleteEdge(emfEdge);
		}
		
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by REVOKE_ADDITION");
		resolved = true;
	}

}
