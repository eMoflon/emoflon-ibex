package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.BindingType;
import language.DomainType;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DEL_OneSided extends MatchClassificationComponent {

	DEL_OneSided() {
	}

	private final MCPattern fwd = MCPattern.DEL_ONESIDED_FWD;
	private final MCPattern bwd = MCPattern.DEL_ONESIDED_BWD;

	@Override
	public Mismatch classify(AnalysedMatch analysedMatch) {
		DomainType notDelDomain;
		if (fwd.matches(analysedMatch.getModPattern())) {
			notDelDomain = DomainType.TRG;
		} else if (bwd.matches(analysedMatch.getModPattern())) {
			notDelDomain = DomainType.SRC;
		} else
			return null;
		
		Mismatch mismatch = new Mismatch(analysedMatch.getMatch(), this);

		// Classify not deleted green elements
		List<TGGRuleElement> toBeDeleted = analysedMatch.getGroupedElements().get(notDelDomain)
				.get(BindingType.CREATE);
		toBeDeleted.stream() //
				.filter(e -> e instanceof TGGRuleNode) //
				.map(n -> (EObject) analysedMatch.getMatch().get(n.getName())) //
				.forEach(o -> mismatch.addElement(o, EltClassifier.TO_BE_DELETED));

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return fwd.matches(analysedMatch.getModPattern()) || bwd.matches(analysedMatch.getModPattern());
	}

}
