package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.BWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.FWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import runtime.TGGRuleApplication;

public class AttributeRepairStrategy implements AbstractRepairStrategy {

	protected final static Logger logger = Logger.getLogger(AbstractRepairStrategy.class);

	private PropagatingOperationalStrategy opStrat;

	public AttributeRepairStrategy(PropagatingOperationalStrategy opStrat) {
		this.opStrat = opStrat;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate) {
		IGreenPatternFactory factory = opStrat.getGreenFactory(PatternSuffixes.removeSuffix(repairCandidate.getPatternName()));
		if (opStrat instanceof SYNC) {
			IRuntimeTGGAttrConstrContainer csp = ((SYNC) opStrat).determineCSP(factory, repairCandidate);
			return repair(repairCandidate, csp);
		}
		return null;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate, PatternType type) {
		IGreenPatternFactory factory = opStrat.getGreenFactory(PatternSuffixes.removeSuffix(repairCandidate.getPatternName()));
		IRuntimeTGGAttrConstrContainer csp;
		switch (type) {
		case FWD:
			csp = new FWD_Strategy().determineCSP(factory, repairCandidate);
			break;
		case BWD:
			csp = new BWD_Strategy().determineCSP(factory, repairCandidate);
			break;
		default:
			return null;
		}
		return repair(repairCandidate, csp);
	}

	private ITGGMatch repair(ITGGMatch repairCandidate, IRuntimeTGGAttrConstrContainer csp) {
		if (csp.solve()) {
			csp.applyCSPValues(repairCandidate);
			LoggerConfig.log(LoggerConfig.log_repair(), () -> //
					"Repaired Attributes: " + repairCandidate.getPatternName() + " (" + repairCandidate.hashCode() + ")");
			return repairCandidate;
		}
		return null;
	}

	@Override
	public Collection<ITGGMatch> chooseMatches(Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications) {
		return brokenRuleApplications.keySet() //
				.stream() //
				.filter(this::noMissingNodes) //
				.map(brokenRuleApplications::get) //
				.collect(Collectors.toList());
	}

	private boolean noMissingNodes(TGGRuleApplication ra) {
		return TGGPatternUtil.getAllNodes(ra).stream().noneMatch(n -> n == null);
	}
}
