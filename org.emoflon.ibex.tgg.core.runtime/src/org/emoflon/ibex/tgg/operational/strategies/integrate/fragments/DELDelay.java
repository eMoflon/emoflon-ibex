package org.emoflon.ibex.tgg.operational.strategies.integrate.fragments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.IFPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DELDelay extends MatchIntegrationFragment {

	private final IFPattern fwd = IFPattern.DEL_DELAY_FWD;
	private final IFPattern bwd = IFPattern.DEL_DELAY_BWD;

	@Override
	public boolean apply(AnalysedMatch analysedMatch, INTEGRATE integrate) {
		DomainType domainPartlyDel;
		if (fwd.matches(analysedMatch.getModPattern())) {
			domainPartlyDel = DomainType.TRG;
		} else if (bwd.matches(analysedMatch.getModPattern())) {
			domainPartlyDel = DomainType.SRC;
		} else
			return false;

		IbexRedInterpreter interpreter = getIbexRedInterpreter(integrate);

		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		delGreenCorr(analysedMatch, interpreter, nodesToRevoke, edgesToRevoke);

		// Classify not deleted green elements
		List<TGGRuleElement> elementsPartlyDel = analysedMatch.getGroupedElements().get(domainPartlyDel)
				.get(BindingType.CREATE);
		elementsPartlyDel.stream() //
				.filter(e -> !analysedMatch.getAreElemsDel().get(e)) //
				.filter(e -> e instanceof TGGRuleNode) //
				.map(e -> (EObject) analysedMatch.getMatch().get(e.getName())) //
				.forEach(n -> integrate.getUndeterminedElements().add(n));
		
		interpreter.revoke(nodesToRevoke, edgesToRevoke);

		return true;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return fwd.matches(analysedMatch.getModPattern()) || bwd.matches(analysedMatch.getModPattern());
	}

}
