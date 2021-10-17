package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirectionHolder.PropagationDirection;

import language.TGGAttributeConstraint;
import language.TGGParamValue;
import runtime.TGGRuleApplication;

public class AttributeRepairStrategy implements RepairStrategy {

	private PropagatingOperationalStrategy opStrat;

	public AttributeRepairStrategy(PropagatingOperationalStrategy opStrat) {
		this.opStrat = opStrat;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate, PatternType type) {
		PropagationDirection propDir = PropagationDirection.byPatternType(type);
		if (propDir == null)
			return null;

		return repair(repairCandidate, determineCSP(propDir, getFactory(repairCandidate), repairCandidate));
	}

	public ITGGMatch repair(List<TGGAttributeConstraint> constraints, ITGGMatch repairCandidate, PatternType type) {
		PropagationDirection propDir = PropagationDirection.byPatternType(type);
		if (propDir == null)
			return null;

		return repair(repairCandidate, determineCSP(propDir, constraints, repairCandidate));
	}

	protected ITGGMatch repair(ITGGMatch repairCandidate, IRuntimeTGGAttrConstrContainer csp) {
		if (!csp.solve())
			return null;

		csp.applyCSPValues(repairCandidate);

		LoggerConfig.log(LoggerConfig.log_repair(), () -> //
		"Repaired Attributes: " + repairCandidate.getPatternName() + " (" + repairCandidate.hashCode() + ")");

		return repairCandidate;
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

	protected IGreenPatternFactory getFactory(ITGGMatch match) {
		return opStrat.getGreenFactory(PatternSuffixes.removeSuffix(match.getPatternName()));
	}

	protected IRuntimeTGGAttrConstrContainer determineCSP(PropagationDirection propDir, IGreenPatternFactory greenFactory, ITGGMatch match) {
		ITGGMatch matchCopy = match.copy();

		IGreenPattern greenPattern = greenFactory.create(propDir.getPatternType());

		matchCopy.getParameterNames().removeAll( //
				propDir.getNodesInOutputDomain(greenPattern).stream() //
						.map(n -> n.getName()) //
						.collect(Collectors.toList()) //
		);

		return greenPattern.getAttributeConstraintContainer(matchCopy);
	}

	protected IRuntimeTGGAttrConstrContainer determineCSP(PropagationDirection propDir, List<TGGAttributeConstraint> constraints, ITGGMatch match) {
		ITGGMatch matchCopy = match.copy();

		IGreenPattern greenPattern = getFactory(matchCopy).create(propDir.getPatternType());

		matchCopy.getParameterNames().removeAll( //
				propDir.getNodesInOutputDomain(greenPattern).stream() //
						.map(n -> n.getName()) //
						.collect(Collectors.toList()) //
		);

		Set<TGGParamValue> params = new HashSet<>();
		for (TGGAttributeConstraint constraint : constraints)
			params.addAll(new HashSet<>(constraint.getParameters()));

		IRuntimeTGGAttrConstrContainer csp = new RuntimeTGGAttributeConstraintContainer(new LinkedList<>(params), //
				constraints, matchCopy, opStrat.getOptions().csp.constraintProvider());

		return csp;
	}

}
