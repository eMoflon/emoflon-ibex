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

public class DEL_Partly extends MatchClassificationComponent {

	DEL_Partly() {
	}

	private final MCPattern pattern = MCPattern.DEL_PARTLY;

	@Override
	public Mismatch classify(AnalysedMatch analysedMatch) {
		Mismatch mismatch = new Mismatch(analysedMatch.getMatch(), this);

		// Classify not deleted green elements
		List<TGGRuleElement> elementsPartlyDel = new ArrayList<>();
		elementsPartlyDel.addAll(analysedMatch.getGroupedElements().get(DomainType.SRC).get(BindingType.CREATE));
		elementsPartlyDel.addAll(analysedMatch.getGroupedElements().get(DomainType.TRG).get(BindingType.CREATE));
		
		elementsPartlyDel.stream() //
				.filter(e -> !analysedMatch.isRuleEltDeleted(e)) //
				.filter(e -> e instanceof TGGRuleNode) //
				.map(e -> (EObject) analysedMatch.getMatch().get(e.getName())) //
				.forEach(n -> mismatch.addElement(n, EltClassifier.UNDETERMINED));

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return pattern.matches(analysedMatch.getModPattern());
	}

}
