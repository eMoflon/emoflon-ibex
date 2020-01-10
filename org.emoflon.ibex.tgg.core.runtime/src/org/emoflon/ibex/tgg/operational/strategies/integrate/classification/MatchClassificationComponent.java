package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class MatchClassificationComponent {

	abstract public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis);

	abstract public boolean isApplicable(MatchAnalysis analysis);

	protected DomainType oppositeOf(DomainType type) {
		switch (type) {
		case SRC:
			return DomainType.TRG;
		case TRG:
			return DomainType.SRC;
		case CORR:
			return DomainType.CORR;
		default:
			return null;
		}
	}

	protected void classifyElts(INTEGRATE integrate, Mismatch mismatch, Set<TGGRuleElement> elements,
			EltClassifier classifier) {
		elements.forEach(elt -> {
			if (elt instanceof TGGRuleNode) {
				EObject node = (EObject) mismatch.getMatch().get(elt.getName());
				mismatch.addClassification(node, classifier);
			} else if (elt instanceof TGGRuleEdge) {
				EMFEdge edge = getRuntimeEdge(mismatch.getMatch(), (TGGRuleEdge) elt);
				mismatch.addClassification(edge, classifier);
			}
		});
	}

	public static class CREATE_FilterNac extends MatchClassificationComponent {

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef), EltClassifier.USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return !analysis.getFilterNacViolations().isEmpty();
		}

	}

	public static class DEL_Complete extends MatchClassificationComponent {

		private final MCPattern pattern = MCPattern.DEL_COMPLETE;

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef), EltClassifier.NO_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	public static class DEL_Corr extends MatchClassificationComponent {

		private final MCPattern pattern = MCPattern.DEL_CORR;

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef), EltClassifier.USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	public static class DEL_OneSided extends MatchClassificationComponent {

		private final MCPattern fwd = MCPattern.DEL_ONESIDED_FWD;
		private final MCPattern bwd = MCPattern.DEL_ONESIDED_BWD;

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			DomainType delSide;
			if (fwd.matches(analysis.getModPattern())) {
				delSide = DomainType.SRC;
			} else if (bwd.matches(analysis.getModPattern())) {
				delSide = DomainType.TRG;
			} else
				return null;

			Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

			EltFilter ef = new EltFilter().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(oppositeOf(delSide))),
					EltClassifier.PENAL_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(delSide)), EltClassifier.NO_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return fwd.matches(analysis.getModPattern()) || bwd.matches(analysis.getModPattern());
		}

	}

	public static class DEL_OneSideIncompl extends MatchClassificationComponent {

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

	public static class DEL_Partly extends MatchClassificationComponent {

		private final MCPattern pattern = MCPattern.DEL_PARTLY;

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef.deleted()), EltClassifier.REWARDLESS_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.notDeleted()), EltClassifier.POTENTIAL_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	public static class DEL_PartlyOneSided extends MatchClassificationComponent {

		private final MCPattern fwd = MCPattern.DEL_PARTLYONESIDED_FWD;
		private final MCPattern bwd = MCPattern.DEL_PARTLYONESIDED_FWD;

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			DomainType delSide;
			if (fwd.matches(analysis.getModPattern())) {
				delSide = DomainType.SRC;
			} else if (bwd.matches(analysis.getModPattern())) {
				delSide = DomainType.TRG;
			} else
				return null;

			Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

			EltFilter ef = new EltFilter().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(oppositeOf(delSide))),
					EltClassifier.POTENTIAL_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(delSide).deleted()),
					EltClassifier.REWARDLESS_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.notDeleted()), EltClassifier.POTENTIAL_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return fwd.matches(analysis.getModPattern()) || bwd.matches(analysis.getModPattern());
		}

	}

}
