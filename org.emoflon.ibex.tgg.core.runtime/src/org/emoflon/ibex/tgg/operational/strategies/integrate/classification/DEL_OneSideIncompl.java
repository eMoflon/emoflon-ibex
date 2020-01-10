package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

import language.DomainType;

public class DEL_OneSideIncompl extends MatchClassificationComponent {

	DEL_OneSideIncompl() {
	}

	private final MCPattern fwd = MCPattern.DEL_ONESIDEINCOMPL_FWD;
	private final MCPattern bwd = MCPattern.DEL_ONESIDEINCOMPL_BWD;

	@Override
	public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
		DomainType partlySide;
		if (fwd.matches(analysis.getModPattern())) {
			partlySide = DomainType.TRG;
		} else if (bwd.matches(analysis.getModPattern())) {
			partlySide = DomainType.SRC;
		} else
			return null;

		Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

		EltFilter ef = new EltFilter().create();
		classifyElts(integrate, mismatch, analysis.getElts(ef.domains(oppositeOf(partlySide))),
				EltClassifier.PENAL_USE);
		classifyElts(integrate, mismatch, analysis.getElts(ef.domains(partlySide).deleted()),
				EltClassifier.PENAL_USE);
		classifyElts(integrate, mismatch, analysis.getElts(ef.notDeleted()), EltClassifier.REWARDLESS_USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(MatchAnalysis analysis) {
		return fwd.matches(analysis.getModPattern()) || bwd.matches(analysis.getModPattern());
	}

}
