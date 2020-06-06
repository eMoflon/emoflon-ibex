package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import static org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification.COMPL_DEL;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification.PART_DEL;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification.UNCHANGED;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification.UNSPECIFIED;
import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil.EltFilter;

import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class MatchClassifier {

	abstract public BrokenMatch classify(BrokenMatch brokenMatch);

	abstract public boolean isApplicable(BrokenMatch brokenMatch);

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

	protected void classifyElts(BrokenMatch brokenMatch, Set<TGGRuleElement> elements, ElementClassifier classifier) {
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
		public BrokenMatch classify(BrokenMatch brokenMatch) {
			setPropDirection(brokenMatch);

			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef), ElementClassifier.USE);

			return brokenMatch;
		}

		private void setPropDirection(BrokenMatch brokenMatch) {
			PropagationDirection propDir = PropagationDirection.UNDEFINED;
			Collection<DomainType> domains = brokenMatch.getFilterNacViolations().values();
			if (domains.contains(DomainType.SRC))
				propDir = PropagationDirection.FORWARD;
			if (domains.contains(DomainType.TRG) && propDir != PropagationDirection.FORWARD)
				propDir = PropagationDirection.BACKWARD;
			else
				propDir = PropagationDirection.UNDEFINED;
			brokenMatch.setPropagationDirection(propDir);
		}

		@Override
		public boolean isApplicable(BrokenMatch brokenMatch) {
			return !brokenMatch.getFilterNacViolations().isEmpty();
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
		public BrokenMatch classify(BrokenMatch brokenMatch) {
			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef), ElementClassifier.NO_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(BrokenMatch brokenMatch) {
			return pattern.matches(brokenMatch.getModPattern());
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
		public BrokenMatch classify(BrokenMatch brokenMatch) {
			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef), ElementClassifier.USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(BrokenMatch brokenMatch) {
			return pattern.matches(brokenMatch.getModPattern());
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
		public BrokenMatch classify(BrokenMatch brokenMatch) {
			DomainType delSide;
			PropagationDirection propDir;
			if (fwdPattern.matches(brokenMatch.getModPattern())) {
				delSide = DomainType.SRC;
				propDir = PropagationDirection.FORWARD;
			} else if (bwdPattern.matches(brokenMatch.getModPattern())) {
				delSide = DomainType.TRG;
				propDir = PropagationDirection.BACKWARD;
			} else
				return null;

			brokenMatch.setPropagationDirection(propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.domains(oppositeOf(delSide))),
					ElementClassifier.PENAL_USE);
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.domains(delSide)), ElementClassifier.NO_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(BrokenMatch brokenMatch) {
			return fwdPattern.matches(brokenMatch.getModPattern()) || bwdPattern.matches(brokenMatch.getModPattern());
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
		public BrokenMatch classify(BrokenMatch brokenMatch) {
			DomainType partlySide;
			PropagationDirection propDir;
			if (fwdPattern.matches(brokenMatch.getModPattern())) {
				partlySide = DomainType.TRG;
				propDir = PropagationDirection.FORWARD;
			} else if (bwdPattern.matches(brokenMatch.getModPattern())) {
				partlySide = DomainType.SRC;
				propDir = PropagationDirection.BACKWARD;
			} else
				return null;

			brokenMatch.setPropagationDirection(propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.domains(oppositeOf(partlySide))),
					ElementClassifier.PENAL_USE);
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.domains(partlySide).deleted()),
					ElementClassifier.PENAL_USE);
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.notDeleted()), ElementClassifier.REWARDLESS_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(BrokenMatch brokenMatch) {
			return fwdPattern.matches(brokenMatch.getModPattern()) || bwdPattern.matches(brokenMatch.getModPattern());
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
		public BrokenMatch classify(BrokenMatch brokenMatch) {
			EltFilter ef = new EltFilter().srcAndTrg().create();
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.deleted()), ElementClassifier.REWARDLESS_USE);
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.notDeleted()), ElementClassifier.POTENTIAL_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(BrokenMatch brokenMatch) {
			return pattern.matches(brokenMatch.getModPattern());
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
		public BrokenMatch classify(BrokenMatch brokenMatch) {
			DomainType delSide;
			PropagationDirection propDir;
			if (fwdPattern.matches(brokenMatch.getModPattern())) {
				delSide = DomainType.SRC;
				propDir = PropagationDirection.FORWARD;
			} else if (bwdPattern.matches(brokenMatch.getModPattern())) {
				delSide = DomainType.TRG;
				propDir = PropagationDirection.BACKWARD;
			} else
				return null;

			brokenMatch.setPropagationDirection(propDir);

			EltFilter ef = new EltFilter().create();
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.domains(oppositeOf(delSide))),
					ElementClassifier.POTENTIAL_USE);
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.domains(delSide).deleted()),
					ElementClassifier.REWARDLESS_USE);
			classifyElts(brokenMatch, brokenMatch.util().getElts(ef.notDeleted()), ElementClassifier.POTENTIAL_USE);

			return brokenMatch;
		}

		@Override
		public boolean isApplicable(BrokenMatch brokenMatch) {
			return fwdPattern.matches(brokenMatch.getModPattern()) || bwdPattern.matches(brokenMatch.getModPattern());
		}

	}

}
