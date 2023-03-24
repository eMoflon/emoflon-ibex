package org.emoflon.ibex.tgg.runtime.repair.strategies;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.PropagationDirectionHolder.PropagationDirection;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

import TGGRuntimeModel.TGGRuleApplication;

public class AttributeRepairStrategy implements RepairStrategy {

	protected final IbexOptions options;
	protected final IGreenInterpreter greenInterpreter;
	protected final RuleHandler ruleHandler;

	public AttributeRepairStrategy(IbexOptions options, IGreenInterpreter greenInterpreter) {
		this.options = options;
		this.greenInterpreter = greenInterpreter;
		this.ruleHandler = options.tgg.ruleHandler();
	}

	@Override
	public Collection<ITGGMatch> repair(RepairApplicationPoint applPoint) {
		PropagationDirection propDir = PropagationDirection.byPatternType(applPoint.getRepairType());
		if (propDir == null)
			return null;

		ITGGMatch applMatch = applPoint.getApplicationMatch();
		return Collections.singletonList(repair(applMatch, determineCSP(propDir, applMatch)));
	}

	public Collection<ITGGMatch> repair(List<TGGAttributeConstraint> constraints, RepairApplicationPoint applPoint) {
		PropagationDirection propDir = PropagationDirection.byPatternType(applPoint.getRepairType());
		if (propDir == null)
			return null;

		ITGGMatch applMatch = applPoint.getApplicationMatch();
		return Collections.singletonList(repair(applMatch, determineCSP(propDir, constraints, applMatch)));
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
	public boolean noMissingNodes(TGGRuleApplication ra) {
		return TGGPatternUtil.getAllNodes(ra).stream().noneMatch(n -> n == null);
	}

	protected IRuntimeTGGAttrConstrContainer determineCSP(PropagationDirection propDir, ITGGMatch match) {
		ITGGMatch matchCopy = match.copy();

		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), propDir.getOperationalisationMode());

		matchCopy.getParameterNames().removeAll( //
				propDir.getNodesInOutputDomain(operationalRule).stream() //
						.map(n -> n.getName()) //
						.collect(Collectors.toList()) //
		);

		return greenInterpreter.getAttributeConstraintContainer(matchCopy);
	}

	protected IRuntimeTGGAttrConstrContainer determineCSP(PropagationDirection propDir, List<TGGAttributeConstraint> constraints, ITGGMatch match) {
		ITGGMatch matchCopy = match.copy();

		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), propDir.getOperationalisationMode());

		matchCopy.getParameterNames().removeAll( //
				propDir.getNodesInOutputDomain(operationalRule).stream() //
						.map(n -> n.getName()) //
						.collect(Collectors.toList()) //
		);

		Set<TGGAttributeConstraintParameterValue> params = new HashSet<>();
		for (TGGAttributeConstraint constraint : constraints)
			params.addAll(new HashSet<>(constraint.getParameters()));

		IRuntimeTGGAttrConstrContainer csp = new RuntimeTGGAttributeConstraintContainer(new LinkedList<>(params), //
				constraints, matchCopy, options.csp.constraintProvider());

		return csp;
	}

}
