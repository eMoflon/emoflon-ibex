package org.emoflon.ibex.tgg.operational.strategies.sync.repair.strategies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.emoflon.ibex.tgg.operational.strategies.sync.repair.AbstractRepairStrategy;

import runtime.TGGRuleApplication;

public class AttributeRepairStrategy implements AbstractRepairStrategy {

	private SYNC sync;
	
	private Collection<TGGRuleApplication> attemptedToRepair;
	
	public AttributeRepairStrategy(SYNC sync) {
		this.sync = sync;
		attemptedToRepair = new HashSet<>();
	}

	@Override
	public boolean repair(IMatch m) {
		IGreenPatternFactory factory = sync.getGreenFactory(PatternSuffixes.removeSuffix(m.getPatternName()));		
		IRuntimeTGGAttrConstrContainer csp = sync.determineCSP(factory, m);
		if (csp.solve()) {
			csp.applyCSPValues(m);
			return true;
		} else
			return false;
	}

	@Override
	public Optional<IMatch> chooseOneMatch(Map<TGGRuleApplication, IMatch> brokenRuleApplications) {
		return brokenRuleApplications.keySet()//
				.stream()//
				.filter(ra -> !attemptedToRepair.contains(ra))//
				.filter(this::noMissingNodes)//
				.findAny()//
				.map(ra -> {
					attemptedToRepair.add(ra);
					return brokenRuleApplications.get(ra);
				});
	}
	
	private boolean noMissingNodes(TGGRuleApplication ra) {
		return !ra.getNodeMappings().values().contains(null);
	}
}
