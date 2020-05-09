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

	abstract public BrokenMatch classify(INTEGRATE integrate, MatchAnalysis analysis);

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

	protected void classifyElts(INTEGRATE integrate, BrokenMatch brokenMatch, Set<TGGRuleElement> elements,
			ElementClassifier classifier) {
		elements.forEach(elt -> {
			if (elt instanceof TGGRuleNode) {
				EObject node = (EObject) brokenMatch.getMatch().get(elt.getName());
				brokenMatch.addClassification(node, classifier);
			} else if (elt instanceof TGGRuleEdge) {
				EMFEdge edge = getRuntimeEdge(brokenMatch.getMatch(), (TGGRuleEdge) elt);
				brokenMatch.addClassification(edge, classifier);
			}
		});
	}

	/**
	 * Classifies a broken <code>Match</code> that has <code>FilterNAC</code>
	 * violations in one or both domains.
	 *
	 */
	public static class CREATE_FilterNac extends MatchClassifier {

		@Override
		public BrokenMatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			BrokenMatch brokenMatch = new BrokenMatch(analysis.getMatch(), this, getPropDirection(analysis));

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, brokenMatch, analysis.getElts(ef), ElementClassifier.USE);

			return brokenMatch;
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

	/**
	 * Classifies a broken <code>Match</code> whose created elements in source
	 * <i>and</i> target domain are completely deleted.
	 *
	 */
	public static class DEL_Complete extends MatchClassifier {

		private final MatchModification pattern = new MatchModification( //
				COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED);

		@Override
		public BrokenMatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			BrokenMatch brokenMatch = new BrokenMatch(analysis.getMatch(), this, PropagationDirection.UNDEFINED);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, brokenMatch, analysis.getElts(ef), ElementClassifier.NO_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	/**
	 * Classifies a broken <code>Match</code> whose context correspondence elements
	 * are completely deleted.
	 *
	 */
	public static class DEL_Corr extends MatchClassifier {

		private final MatchModification pattern = new MatchModification( //
				UNCHANGED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNCHANGED, UNSPECIFIED);

		@Override
		public BrokenMatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			BrokenMatch brokenMatch = new BrokenMatch(analysis.getMatch(), this, PropagationDirection.UNDEFINED);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, brokenMatch, analysis.getElts(ef), ElementClassifier.USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	/**
	 * Classifies a broken <code>Match</code> whose created elements are completely
	 * deleted in only one domain (source <i>or</i> target).
	 * 
	 */
	public static class DEL_OneSided extends MatchClassifier {

		private final MatchModification fwdPattern = new MatchModification( //
				COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED);
		private final MatchModification bwdPattern = new MatchModification( //
				UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED);

		@Override
		public BrokenMatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
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

			BrokenMatch brokenMatch = new BrokenMatch(analysis.getMatch(), this, propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.domains(oppositeOf(delSide))),
					ElementClassifier.PENAL_USE);
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.domains(delSide)), ElementClassifier.NO_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return fwdPattern.matches(analysis.getModPattern()) || bwdPattern.matches(analysis.getModPattern());
		}

	}

	/**
	 * Classifies a broken <code>Match</code> whose created elements are completely
	 * deleted in one domain but incompletely deleted in the other domain.
	 *
	 */
	public static class DEL_OneSideIncompl extends MatchClassifier {

		private final MatchModification fwdPattern = new MatchModification( //
				COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED);
		private final MatchModification bwdPattern = new MatchModification( //
				PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED);

		@Override
		public BrokenMatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
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

			BrokenMatch brokenMatch = new BrokenMatch(analysis.getMatch(), this, propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.domains(oppositeOf(partlySide))),
					ElementClassifier.PENAL_USE);
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.domains(partlySide).deleted()),
					ElementClassifier.PENAL_USE);
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.notDeleted()), ElementClassifier.REWARDLESS_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return fwdPattern.matches(analysis.getModPattern()) || bwdPattern.matches(analysis.getModPattern());
		}

	}

	/**
	 * Classifies a broken <code>Match</code> whose created elements are deleted
	 * incompletely in <i>both</i> domains (source and target).
	 *
	 */
	public static class DEL_Partly extends MatchClassifier {

		private final MatchModification pattern = new MatchModification( //
				PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED);

		@Override
		public BrokenMatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
			BrokenMatch brokenMatch = new BrokenMatch(analysis.getMatch(), this, PropagationDirection.UNDEFINED);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.deleted()), ElementClassifier.REWARDLESS_USE);
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.notDeleted()), ElementClassifier.POTENTIAL_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return pattern.matches(analysis.getModPattern());
		}

	}

	/**
	 * Classifies a broken <code>Match</code> whose created elements are not deleted
	 * in one domain but partly deleted in the other domain.
	 * 
	 */
	public static class DEL_PartlyOneSided extends MatchClassifier {

		private final MatchModification fwdPattern = new MatchModification( //
				PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED);
		private final MatchModification bwdPattern = new MatchModification( //
				UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED);

		@Override
		public BrokenMatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
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

			BrokenMatch brokenMatch = new BrokenMatch(analysis.getMatch(), this, propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.domains(oppositeOf(delSide))),
					ElementClassifier.POTENTIAL_USE);
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.domains(delSide).deleted()),
					ElementClassifier.REWARDLESS_USE);
			classifyElts(integrate, brokenMatch, analysis.getElts(ef.notDeleted()), ElementClassifier.POTENTIAL_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(MatchAnalysis analysis) {
			return fwdPattern.matches(analysis.getModPattern()) || bwdPattern.matches(analysis.getModPattern());
		}

	}

}
