package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.BindingType;
import language.DomainType;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DEL_PartlyOneSided extends MatchClassificationComponent {

	DEL_PartlyOneSided() {
	}

	private final MCPattern fwd = MCPattern.DEL_PARTLYONESIDED_FWD;
	private final MCPattern bwd = MCPattern.DEL_PARTLYONESIDED_FWD;

	@Override
	public Mismatch classify(AnalysedMatch analysedMatch) {
		DomainType partlyDelDomain;
		DomainType notDelDomain;
		if (fwd.matches(analysedMatch.getModPattern())) {
			partlyDelDomain = DomainType.SRC;
			notDelDomain = DomainType.TRG;
		} else if (bwd.matches(analysedMatch.getModPattern())) {
			partlyDelDomain = DomainType.TRG;
			notDelDomain = DomainType.SRC;
		} else
			return null;

		Mismatch mismatch = new Mismatch(analysedMatch.getMatch(), this);

		// Classify not deleted green elements
		List<TGGRuleElement> eltsPartlyDel = analysedMatch.getGroupedElements().get(partlyDelDomain)
				.get(BindingType.CREATE);
		List<TGGRuleElement> eltsNotDel = analysedMatch.getGroupedElements().get(notDelDomain).get(BindingType.CREATE);
		eltsPartlyDel.stream() //
				.filter(e -> !analysedMatch.isRuleEltDeleted(e)) //
				.filter(e -> e instanceof TGGRuleNode) //
				.map(e -> (EObject) analysedMatch.getMatch().get(e.getName())) //
				.forEach(n -> mismatch.addElement(n, EltClassifier.UNDETERMINED));
		eltsNotDel.stream() //
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
