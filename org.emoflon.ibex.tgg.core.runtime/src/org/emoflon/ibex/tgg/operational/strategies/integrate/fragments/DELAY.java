package org.emoflon.ibex.tgg.operational.strategies.integrate.fragments;

import java.util.ArrayList;
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

public class DELAY extends MatchIntegrationFragment {

	private final IFPattern pattern = IFPattern.DELAY;

	@Override
	public boolean apply(AnalysedMatch analysedMatch, INTEGRATE integrate) {
		if (!pattern.matches(analysedMatch.getModPattern()))
			return false;

		IbexRedInterpreter interpreter = getIbexRedInterpreter(integrate);

		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		delGreenCorr(analysedMatch, interpreter, nodesToRevoke, edgesToRevoke);

		// Classify not deleted green elements
		List<TGGRuleElement> elementsPartlyDel = new ArrayList<>();
		elementsPartlyDel.addAll(analysedMatch.getGroupedElements().get(DomainType.SRC).get(BindingType.CREATE));
		elementsPartlyDel.addAll(analysedMatch.getGroupedElements().get(DomainType.TRG).get(BindingType.CREATE));
		
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
		return pattern.matches(analysedMatch.getModPattern());
	}

}
