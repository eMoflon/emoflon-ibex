package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification.COMPL_DEL;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification.PART_DEL;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification.UNCHANGED;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification.UNSPECIFIED;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class MatchClassifier {

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
			ElementClassifier classifier) {
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

	public static class CREATE_FilterNac extends MatchClassifier {

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			Mismatch mismatch = new Mismatch(analysis.getMatch(), this, getPropDirection(analysis));

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef), ElementClassifier.USE);

			return mismatch;
		}

		private PropagationDirection getPropDirection(MatchAnalysis analysis) {
			PropagationDirection propDir = PropagationDirection.UNDEFINED;
			Collection<DomainType> domains = analysis.getFilterNacViolations().values();
			if (domains.contains(DomainType.SRC))
				propDir = PropagationDirection.FORWARD;
			if (domains.contains(DomainType.TRG) && propDir != PropagationDirection.FORWARD)
				propDir = PropagationDirection.BACKWARD;
			else
				propDir = PropagationDirection.UNDEFINED;
			return propDir;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return !analysis.getFilterNacViolations().isEmpty();
		}

	}

	public static class DEL_Complete extends MatchClassifier {

		private final MatchModification pattern = new MatchModification( //
				COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED);

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			Mismatch mismatch = new Mismatch(analysis.getMatch(), this, PropagationDirection.UNDEFINED);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef), ElementClassifier.NO_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	public static class DEL_Corr extends MatchClassifier {

		private final MatchModification pattern = new MatchModification( //
				UNCHANGED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNCHANGED, UNSPECIFIED);

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			Mismatch mismatch = new Mismatch(analysis.getMatch(), this, PropagationDirection.UNDEFINED);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef), ElementClassifier.USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	public static class DEL_OneSided extends MatchClassifier {

		private final MatchModification fwdPattern = new MatchModification( //
				COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED);
		private final MatchModification bwdPattern = new MatchModification( //
				UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED);

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			DomainType delSide;
			PropagationDirection propDir;
			if (fwdPattern.matches(analysis.getModPattern())) {
				delSide = DomainType.SRC;
				propDir = PropagationDirection.FORWARD;
			} else if (bwdPattern.matches(analysis.getModPattern())) {
				delSide = DomainType.TRG;
				propDir = PropagationDirection.BACKWARD;
			} else
				return null;

			Mismatch mismatch = new Mismatch(analysis.getMatch(), this, propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(oppositeOf(delSide))),
					ElementClassifier.PENAL_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(delSide)), ElementClassifier.NO_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return fwdPattern.matches(analysis.getModPattern()) || bwdPattern.matches(analysis.getModPattern());
		}

	}

	public static class DEL_OneSideIncompl extends MatchClassifier {

		private final MatchModification fwdPattern = new MatchModification( //
				COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED);
		private final MatchModification bwdPattern = new MatchModification( //
				PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED);

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			DomainType partlySide;
			PropagationDirection propDir;
			if (fwdPattern.matches(analysis.getModPattern())) {
				partlySide = DomainType.TRG;
				propDir = PropagationDirection.FORWARD;
			} else if (bwdPattern.matches(analysis.getModPattern())) {
				partlySide = DomainType.SRC;
				propDir = PropagationDirection.BACKWARD;
			} else
				return null;

			Mismatch mismatch = new Mismatch(analysis.getMatch(), this, propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(oppositeOf(partlySide))),
					ElementClassifier.PENAL_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(partlySide).deleted()),
					ElementClassifier.PENAL_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.notDeleted()), ElementClassifier.REWARDLESS_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return fwdPattern.matches(analysis.getModPattern()) || bwdPattern.matches(analysis.getModPattern());
		}

	}

	public static class DEL_Partly extends MatchClassifier {

		private final MatchModification pattern = new MatchModification( //
				PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED);

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			Mismatch mismatch = new Mismatch(analysis.getMatch(), this, PropagationDirection.UNDEFINED);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef.deleted()), ElementClassifier.REWARDLESS_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.notDeleted()), ElementClassifier.POTENTIAL_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	public static class DEL_PartlyOneSided extends MatchClassifier {

		private final MatchModification fwdPattern = new MatchModification( //
				PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED);
		private final MatchModification bwdPattern = new MatchModification( //
				UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED);

		@Override
		public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			DomainType delSide;
			PropagationDirection propDir;
			if (fwdPattern.matches(analysis.getModPattern())) {
				delSide = DomainType.SRC;
				propDir = PropagationDirection.FORWARD;
			} else if (bwdPattern.matches(analysis.getModPattern())) {
				delSide = DomainType.TRG;
				propDir = PropagationDirection.BACKWARD;
			} else
				return null;

			Mismatch mismatch = new Mismatch(analysis.getMatch(), this, propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(oppositeOf(delSide))),
					ElementClassifier.POTENTIAL_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.domains(delSide).deleted()),
					ElementClassifier.REWARDLESS_USE);
			classifyElts(integrate, mismatch, analysis.getElts(ef.notDeleted()), ElementClassifier.POTENTIAL_USE);

			return mismatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return fwdPattern.matches(analysis.getModPattern()) || bwdPattern.matches(analysis.getModPattern());
		}

	}

}
