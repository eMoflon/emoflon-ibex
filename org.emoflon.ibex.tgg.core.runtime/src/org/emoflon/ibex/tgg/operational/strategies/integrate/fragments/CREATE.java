package org.emoflon.ibex.tgg.operational.strategies.integrate.fragments;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.BindingType;
import language.DomainType;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class CREATE extends MatchIntegrationFragment {

	@Override
	public boolean softApply(AnalysedMatch analysedMatch, INTEGRATE integrate) {
		if (!isApplicable(analysedMatch))
			return false;
		
		delGreenCorr(analysedMatch, getIbexRedInterpreter(integrate));
		
		// Classify green elements
				List<TGGRuleElement> greenElements = new ArrayList<>();
				greenElements.addAll(analysedMatch.getGroupedElements().get(DomainType.SRC).get(BindingType.CREATE));
				greenElements.addAll(analysedMatch.getGroupedElements().get(DomainType.TRG).get(BindingType.CREATE));

				greenElements.stream() //
						.filter(e -> e instanceof TGGRuleNode) //
						.map(e -> (EObject) analysedMatch.getMatch().get(e.getName())) //
						.forEach(n -> integrate.getToBeTranslatedElements().add(n));

				return true;
	}

	@Override
	public boolean hardApply(AnalysedMatch analysedMatch, INTEGRATE integrate) {
		return softApply(analysedMatch, integrate);
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return !analysedMatch.getFilterNacViolations().isEmpty();
	}

}
