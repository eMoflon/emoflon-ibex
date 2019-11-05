package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.BindingType;
import language.DomainType;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DEL_OneSideIncompl extends MatchClassificationComponent {

	DEL_OneSideIncompl() {
	}

	private final MCPattern fwd = MCPattern.DEL_ONESIDEINCOMPL_FWD;
	private final MCPattern bwd = MCPattern.DEL_ONESIDEINCOMPL_BWD;

	@Override
	public Mismatch classify(AnalysedMatch analysedMatch) {
		DomainType domainPartlyDel;
		if (fwd.matches(analysedMatch.getModPattern())) {
			domainPartlyDel = DomainType.TRG;
		} else if (bwd.matches(analysedMatch.getModPattern())) {
			domainPartlyDel = DomainType.SRC;
		} else
			return null;
		
		Mismatch mismatch = new Mismatch(analysedMatch.getMatch(), this);

		// Classify not deleted green elements
		List<TGGRuleElement> elementsPartlyDel = analysedMatch.getGroupedElements().get(domainPartlyDel)
				.get(BindingType.CREATE);
		elementsPartlyDel.stream() //
				.filter(e -> !analysedMatch.isRuleEltDeleted(e)) //
				.filter(e -> e instanceof TGGRuleNode) //
				.map(e -> (EObject) analysedMatch.getMatch().get(e.getName())) //
				.forEach(n -> mismatch.addElement(n, EltClassifier.UNDETERMINED));

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return fwd.matches(analysedMatch.getModPattern()) || bwd.matches(analysedMatch.getModPattern());
	}

}
