package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.BWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.FWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC_Strategy;

import language.TGGAttributeConstraint;
import language.TGGParamValue;
import runtime.TGGRuleApplication;

public class AttributeRepairStrategy implements AbstractRepairStrategy {

	protected final static Logger logger = Logger.getLogger(AbstractRepairStrategy.class);

	private PropagatingOperationalStrategy opStrat;

	public AttributeRepairStrategy(PropagatingOperationalStrategy opStrat) {
		this.opStrat = opStrat;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate) {
		if (opStrat instanceof SYNC) {
			IRuntimeTGGAttrConstrContainer csp = ((SYNC) opStrat).determineCSP(getFactory(repairCandidate), repairCandidate);
			return repair(repairCandidate, csp);
		}
		return null;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate, PatternType type) {
		IRuntimeTGGAttrConstrContainer csp;
		switch (type) {
		case FWD:
			csp = new FWD_Strategy().determineCSP(getFactory(repairCandidate), repairCandidate);
			break;
		case BWD:
			csp = new BWD_Strategy().determineCSP(getFactory(repairCandidate), repairCandidate);
			break;
		default:
			return null;
		}
		return repair(repairCandidate, csp);
	}
	
	public ITGGMatch repair(List<TGGAttributeConstraint> constraints, ITGGMatch repairCandidate, PatternType type) {
		ITGGMatch candidateCopy = repairCandidate.copy();
		SYNC_Strategy strat;
		switch (type) {
		case FWD:
			strat = new FWD_Strategy();
			break;
		case BWD:
			strat = new BWD_Strategy();
			break;
		default:
			return null;
		}

		IGreenPattern greenPattern = getFactory(candidateCopy).create(strat.getType());
		candidateCopy.getParameterNames().removeAll( //
				strat.getNodesInOutputDomain(greenPattern).stream() //
						.map(n -> n.getName()) //
						.collect(Collectors.toList()));

		Set<TGGParamValue> params = new HashSet<>();
		for (TGGAttributeConstraint constraint : constraints)
			params.addAll(new HashSet<>(constraint.getParameters()));

		IRuntimeTGGAttrConstrContainer csp = new RuntimeTGGAttributeConstraintContainer(new LinkedList<>(params), //
				constraints, candidateCopy, opStrat.getOptions().csp.constraintProvider());

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
	
	private IGreenPatternFactory getFactory(ITGGMatch match) {
		return opStrat.getGreenFactory(PatternSuffixes.removeSuffix(match.getPatternName()));
	}
}
