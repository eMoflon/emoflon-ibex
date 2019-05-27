package org.emoflon.ibex.tgg.operational.strategies.integrate.fragments;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.BindingType;
import language.DomainType;
import language.TGGRuleCorr;

public abstract class MatchIntegrationFragment {

	abstract public boolean apply(AnalysedMatch analysedMatch, INTEGRATE integrate);

	abstract public boolean isApplicable(AnalysedMatch analysedMatch);

	protected IbexRedInterpreter getIbexRedInterpreter(INTEGRATE integrate) {
		try {
			return (IbexRedInterpreter) integrate.getRedInterpreter();
		} catch (Exception e) {
			throw new RuntimeException("IbexRedInterpreter implementation is needed", e);
		}
	}

	protected void delGreenCorr(AnalysedMatch analysedMatch, IbexRedInterpreter interpreter, Set<EObject> nodesToRevoke,
			Set<EMFEdge> edgesToRevoke) {
		analysedMatch.getGroupedElements().get(DomainType.CORR).get(BindingType.CREATE).stream() //
				.filter(e -> e instanceof TGGRuleCorr) //
				.map(c -> (EObject) analysedMatch.getMatch().get(c.getName())) //
				.forEach(c -> interpreter.revokeCorr(c, nodesToRevoke, edgesToRevoke));
	}

}
