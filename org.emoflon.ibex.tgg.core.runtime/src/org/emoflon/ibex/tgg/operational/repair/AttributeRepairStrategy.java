package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import runtime.TGGRuleApplication;

public class AttributeRepairStrategy implements AbstractRepairStrategy {

	protected final static Logger logger = Logger.getLogger(AbstractRepairStrategy.class);

	private PropagatingOperationalStrategy operationalStrategy;
	
	public AttributeRepairStrategy(PropagatingOperationalStrategy operationalStrategy) {
		this.operationalStrategy = operationalStrategy;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate) {
		IGreenPatternFactory factory = operationalStrategy.getGreenFactory(PatternSuffixes.removeSuffix(repairCandidate.getPatternName()));		
		IRuntimeTGGAttrConstrContainer csp = operationalStrategy.determineCSP(factory, repairCandidate);
		if (csp.solve()) {
			csp.applyCSPValues(repairCandidate);
			logger.info("Repaired: " + repairCandidate.getPatternName() + " (" + repairCandidate.hashCode() + ")");
			return repairCandidate;
		} else
			return null;
	}

	@Override
	public Collection<ITGGMatch> chooseMatches(Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications) {
		return brokenRuleApplications.keySet()//
				.stream()//
				.filter(this::noMissingNodes)//
				.map(brokenRuleApplications::get)//
				.collect(Collectors.toList());
	}
	
	private boolean noMissingNodes(TGGRuleApplication ra) {
		return TGGPatternUtil.getAllNodes(ra).stream().noneMatch(n -> n == null);
	}
}
