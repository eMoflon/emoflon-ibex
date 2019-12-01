package org.emoflon.ibex.tgg.operational.strategies.sync.repair;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import runtime.TGGRuleApplication;

public class AttributeRepairStrategy implements AbstractRepairStrategy {

	protected final static Logger logger = Logger.getLogger(AbstractRepairStrategy.class);

	private SYNC sync;
	
	public AttributeRepairStrategy(SYNC sync) {
		this.sync = sync;
	}

	@Override
	public IMatch repair(IMatch repairCandidate) {
		IGreenPatternFactory factory = sync.getGreenFactory(PatternSuffixes.removeSuffix(repairCandidate.getPatternName()));		
		IRuntimeTGGAttrConstrContainer csp = sync.determineCSP(factory, repairCandidate);
		if (csp.solve()) {
			csp.applyCSPValues(repairCandidate);
			logger.info("Repaired: " + repairCandidate.getPatternName() + " (" + repairCandidate.hashCode() + ")");
			return repairCandidate;
		} else
			return null;
	}

	@Override
	public Collection<IMatch> chooseMatches(Map<TGGRuleApplication, IMatch> brokenRuleApplications) {
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
