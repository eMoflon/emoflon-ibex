package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.FWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import language.BindingType;
import language.DomainType;
import language.TGGRule;
import runtime.TGGRuleApplication;

public class AttributeRepairStrategy extends AbstractRepairStrategy {

	private Map<String, IGreenPatternFactory> factories;
	private OperationalStrategy operationalStrategy;
	private Map<String, Integer> tggElements;
	
	public AttributeRepairStrategy(OperationalStrategy operationalStrategy, Map<String, IGreenPatternFactory> factories) {
		initialize(operationalStrategy, factories);
	}

	private void initialize(OperationalStrategy operationalStrategy, Map<String, IGreenPatternFactory> factories) {
		this.factories = factories;
		this.operationalStrategy = operationalStrategy;
		this.tggElements = new Object2IntOpenHashMap<>();
		for(TGGRule rule : operationalStrategy.getTGG().getRules()) {
			tggElements.put(rule.getName(), rule.getNodes().stream().filter(n -> !(n.getBindingType().equals(BindingType.CONTEXT) && n.getDomainType().equals(DomainType.CORR))).collect(Collectors.toList()).size());
		}
	}

	@Override
	protected boolean repair(TGGRuleApplication ra, IMatch iMatch) {
		IMatch copy = iMatch.copy();
		
		IGreenPatternFactory factory = factories.get(PatternSuffixes.removeSuffix(iMatch.getPatternName()));
		IGreenPattern greenPattern;
		boolean isForwardSync = ((SYNC) operationalStrategy).getStrategy() instanceof FWD_Strategy ? true : false;

		if(isForwardSync) {
			greenPattern = factory.create(PatternSuffixes.removeSuffix(iMatch.getPatternName()) + PatternSuffixes.FWD);
			copy.getParameterNames().removeAll(greenPattern.getTrgNodes().stream().map(n -> n.getName()).collect(Collectors.toList()));
		}
		else {
			greenPattern = factory.create(PatternSuffixes.removeSuffix(iMatch.getPatternName()) + PatternSuffixes.BWD);
			copy.getParameterNames().removeAll(greenPattern.getSrcNodes().stream().map(n -> n.getName()).collect(Collectors.toList()));
		}
		
		IRuntimeTGGAttrConstrContainer cspContainer = greenPattern.getAttributeConstraintContainer(copy);
		if (!cspContainer.solve())
			return false;
		
		try {
			cspContainer.applyCSPValues(iMatch);
		} catch (Exception e) {
		}
		return true;
	}

	@Override
	// if a new match was found for the old TGGRuleApplication then we can assume that everything was correct
	protected boolean checkIfRepairWasSucessful(TGGRuleApplication ra, IMatch oldMatch, IMatch newMatch) {
		return true;
	}

	@Override
	// does not need to be revoked since we only change attributes which can simply be removed by the clean algorithm
	protected boolean revokeRepair(TGGRuleApplication ra) {
		return true;
	}

	@Override
	protected boolean isCandidate(TGGRuleApplication ra, IMatch iMatch) {
		return ra.getNodeMappings().keySet().size() == ra.getNodeMappings().values().size();
	}

}
