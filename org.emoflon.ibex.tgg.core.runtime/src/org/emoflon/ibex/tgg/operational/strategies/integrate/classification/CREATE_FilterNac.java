package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.BindingType;
import language.DomainType;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class CREATE_FilterNac extends MatchClassificationComponent {

	CREATE_FilterNac() {
	}

	@Override
	public Mismatch classify(AnalysedMatch analysedMatch) {
		Mismatch mismatch = new Mismatch(analysedMatch.getMatch(), this);
		
		// Classify green elements
		List<TGGRuleElement> greenElements = new ArrayList<>();
		greenElements.addAll(analysedMatch.getGroupedElements().get(DomainType.SRC).get(BindingType.CREATE));
		greenElements.addAll(analysedMatch.getGroupedElements().get(DomainType.TRG).get(BindingType.CREATE));

		greenElements.stream() //
				.filter(e -> e instanceof TGGRuleNode) //
				.map(e -> (EObject) analysedMatch.getMatch().get(e.getName())) //
				.forEach(n -> mismatch.addElement(n, EltClassifier.TO_BE_TRANSLATED));

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return !analysedMatch.getFilterNacViolations().isEmpty();
	}

}
