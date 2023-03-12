package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts;

import java.util.List;
import java.util.Set;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.util.TGGEdgeUtil;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public class DelPreserveEdgeConflict extends DeletePreserveConflict {

	protected final ITGGMatch srcTrgMatch;
	protected final ITGGMatch matchToBeRepaired;

	public DelPreserveEdgeConflict(ConflictContainer container, ITGGMatch srcTrgMatch, DomainType domainToBePreserved,
			List<ITGGMatch> causingMatches) {
		super(container, domainToBePreserved, causingMatches);
		this.srcTrgMatch = srcTrgMatch;
		this.matchToBeRepaired = null;
	}

	public DelPreserveEdgeConflict(ConflictContainer container, ITGGMatch srcTrgMatch, DomainType domainToBePreserved, List<ITGGMatch> causingMatches,
			ITGGMatch matchToBeRepaired) {
		super(container, domainToBePreserved, causingMatches);
		this.srcTrgMatch = srcTrgMatch;
		this.matchToBeRepaired = matchToBeRepaired;
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
		super.crs_revokeAddition();

		if (matchToBeRepaired != null)
			autoRepair();
	}

	@Override
	public void crs_mergeAndPreserve() {
		super.crs_mergeAndPreserve();

		if (matchToBeRepaired != null)
			autoRepair();
	}

	@Override
	public void crs_revokeDeletion() {
		super.crs_revokeDeletion();

		if (matchToBeRepaired != null)
			autoRepair();
	}

	@Override
	public void crs_preferSource() {
		super.crs_preferSource();

		if (matchToBeRepaired != null)
			autoRepair();
	}

	@Override
	public void crs_preferTarget() {
		super.crs_preferTarget();

		if (matchToBeRepaired != null)
			autoRepair();
	}

	@Override
	protected void revokeAddition() {
		for (TGGEdge e : integrate().matchUtils().get(srcTrgMatch).getEdges(new EltFilter().create())) {
			if (((TGGNode) e.getSource()).getBindingType() != BindingType.CONTEXT && ((TGGNode) e.getTarget()).getBindingType() != BindingType.CONTEXT)
				return;

			EMFEdge emfEdge = TGGEdgeUtil.getRuntimeEdge(srcTrgMatch, e);
			if (emfEdge.getType().isContainment())
				ModelChangeUtil.deleteElement(emfEdge.getTarget(), true);
			else
				ModelChangeUtil.deleteEdge(emfEdge);
		}
	}

	protected void autoRepair() {
		integrate().getOptions().matchDistributor().updateMatches();
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Auto-repair after resolution of " + printConflictIdentification() + ":");
		PatternType repairDirection = domainToBePreserved == DomainType.SOURCE ? PatternType.FWD : PatternType.BWD;
		integrate().repair().shortcutRepairOneMatch(matchToBeRepaired, repairDirection);
	}

}
