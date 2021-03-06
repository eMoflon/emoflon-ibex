package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AttributeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.Conflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.CorrPreservationConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DelPreserveAttrConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DelPreserveEdgeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePreserveConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.InconsDomainChangesConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.InconsistentChangesConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.OperationalMultiplicityConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection.MultiplicityCounter.OutgoingEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;

import com.google.common.collect.Sets;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraintDefinition;
import language.TGGAttributeExpression;
import language.TGGRuleNode;

public class ConflictDetector {

	protected final static Logger logger = Logger.getLogger(ConflictDetector.class);

	private INTEGRATE integrate;

	private Set<ITGGMatch> cachedAddedFwdBwdMatches;

	private Map<ITGGMatch, ConflictContainer> match2conflictContainer;
	private Map<ITGGMatch, List<ITGGMatch>> match2sortedRollBackCauses;

	public ConflictDetector(INTEGRATE integrate) {
		this.integrate = integrate;
		cachedAddedFwdBwdMatches = new HashSet<>();
	}

	public Map<ITGGMatch, ConflictContainer> detectConflicts() {
		match2conflictContainer = Collections.synchronizedMap(new HashMap<>());
		match2sortedRollBackCauses = Collections.synchronizedMap(new HashMap<>());

		// delete-preserve conflicts must be detected last, since some of them may be irrelevant, in case
		// they depend on other conflicts which repair the deletions while being resolved (see method
		// isDeletionRepairableConflictedMatch)
		detectBrokenMatchBasedConflicts();
		detectDeletePreserveConflicts();

		// sort out empty conflict containers
		for (Iterator<ConflictContainer> iterator = match2conflictContainer.values().iterator(); iterator.hasNext();) {
			ConflictContainer container = (ConflictContainer) iterator.next();
			if (container.getConflicts().isEmpty())
				iterator.remove();
		}

		return match2conflictContainer;
	}

	private void detectDeletePreserveConflicts() {
		// we only iterate over src/trg matches which are not part of a consistency match (already filtered
		// in precedence graph)
		integrate.getPrecedenceGraph().getSourceNodes().parallelStream() //
				.filter(srcNode -> !integrate.getPrecedenceGraph().hasAnyConsistencyOverlap(srcNode)) //
				.forEach(srcNode -> detectDeletePreserveEdgeConflict(srcNode, null));

		integrate.getPrecedenceGraph().getTargetNodes().parallelStream() //
				.filter(trgNode -> !integrate.getPrecedenceGraph().hasAnyConsistencyOverlap(trgNode)) //
				.forEach(trgNode -> detectDeletePreserveEdgeConflict(trgNode, null));

		detectDeletePreserveAttrConflicts();
	}

	/**
	 * Detects one or multiple delete-preserve conflicts for a given source/target match.
	 * 
	 * @param srcTrgNode        node which contains the source/target match
	 * @param matchToBeRepaired optional consistency match which has to be repaired after resolving this
	 *                          conflict (may be <code>null</code>)
	 */
	private boolean detectDeletePreserveEdgeConflict(PrecedenceNode srcTrgNode, ITGGMatch matchToBeRepaired) {
		boolean anyConflictDetected = false;
		DomainType domainToBePreserved = srcTrgNode.getMatch().getType() == PatternType.SRC ? DomainType.SRC : DomainType.TRG;

		Set<PrecedenceNode> directRollBackCauses = new HashSet<>();
		srcTrgNode.forAllToBeRolledBackBy((act, pre) -> {
			// TODO adrianm: improve performance?
			// we only want to traverse those src/trg matches that only matches non-translated green elements
			if (act.getMatch().getType() != PatternType.CONSISTENCY && integrate.getPrecedenceGraph().hasAnyConsistencyOverlap(act))
				return false;
			if (act.isBroken()) {
				directRollBackCauses.add(act);
				return false;
			}
			return true;
		});

		for (PrecedenceNode dirRollBackCause : directRollBackCauses) {
			ConflictContainer container = match2conflictContainer.computeIfAbsent(dirRollBackCause.getMatch(), //
					key -> new ConflictContainer(integrate, dirRollBackCause.getMatch()));

			boolean skipCheckDomainSpecificViolations = skipCheckDomainSpecificViolations(container, domainToBePreserved);

			if (skipCheckDomainSpecificViolations || checkDomainSpecificViolations(dirRollBackCause, oppositeOf(domainToBePreserved))) {
				if (matchToBeRepaired == null)
					createDelPresEdgeConflict(container, srcTrgNode.getMatch(), domainToBePreserved, dirRollBackCause.getMatch());
				else
					createDelPresEdgeConflict(container, srcTrgNode.getMatch(), domainToBePreserved, dirRollBackCause.getMatch(), matchToBeRepaired);
				anyConflictDetected = true;
			}
		}

		return anyConflictDetected;
	}

	private void detectDeletePreserveAttrConflicts() {
		for (AttributeChange change : integrate.getGeneralModelChanges().getAttributeChanges()) {
			for (PrecedenceNode node : integrate.getPrecedenceGraph().getNodesTranslating(change.getElement())) {
				if (node.getMatch().getType() != PatternType.CONSISTENCY)
					continue;

				MatchAnalysis analysis = integrate.getMatchUtil().getAnalysis(node.getMatch());
				TGGRuleNode ruleNode = analysis.getNode(change.getElement());

				ConflictContainer container = match2conflictContainer.computeIfAbsent(node.getMatch(), //
						key -> new ConflictContainer(integrate, node.getMatch()));

				boolean skipCheckDomainSpecificViolations = skipCheckDomainSpecificViolations(container, ruleNode.getDomainType());

				if (skipCheckDomainSpecificViolations || checkDomainSpecificViolations(node, oppositeOf(ruleNode.getDomainType())))
					createDelPresAttrConflict(container, change, ruleNode.getDomainType(), node.getMatch());
			}
		}
	}

	private synchronized boolean skipCheckDomainSpecificViolations(ConflictContainer container, DomainType domain) {
		for (Conflict conflict : container.getConflicts()) {
			if (!(conflict instanceof DeletePreserveConflict))
				continue;
			if (((DeletePreserveConflict) conflict).getDomainToBePreserved() == domain)
				return true;
		}
		return false;
	}

	/**
	 * Checks for all matches which will roll back the considered rule hierarchy if they have domain
	 * specific violations. If during this process a conflicted match was found whose deletions can be
	 * repaired, the check is canceled. If until here a domain specific violation was found, this method
	 * returns true.
	 * 
	 * @param directRollBackCause the match which directly causes the roll back
	 * @param criticalDomain      the domain for which violations are critical
	 * @return true if there are domain specific violations causing a conflict
	 */
	private boolean checkDomainSpecificViolations(PrecedenceNode directRollBackCause, DomainType criticalDomain) {
		if (isDeletionRepairableConflictedMatch(directRollBackCause.getMatch()))
			return false;

		List<ITGGMatch> rollBackCauses = new LinkedList<>();
		rollBackCauses.add(directRollBackCause.getMatch());

		AtomicBoolean domainSpecificViolation = new AtomicBoolean(false);
		if (hasDomainSpecificViolations(directRollBackCause.getMatch(), criticalDomain))
			domainSpecificViolation.set(true);

		directRollBackCause.forAllToBeRolledBackBy((act, pre) -> {
			if (act.isBroken()) {
				if (!domainSpecificViolation.get()) {
					if (isDeletionRepairableConflictedMatch(act.getMatch()))
						return false;
					if (hasDomainSpecificViolations(act.getMatch(), criticalDomain))
						domainSpecificViolation.set(true);
				}
				rollBackCauses.add(act.getMatch());
			}
			return true;
		});

		if (domainSpecificViolation.get()) {
			// cache the roll back causing matches, since they might be needed by conflicts for their resolution
			match2sortedRollBackCauses.put(directRollBackCause.getMatch(), rollBackCauses);
			return true;
		}
		return false;
	}

	/**
	 * This method aims to check if a delete-preserve conflict above the specified match may be ignored.
	 * This is the case if a later conflict resolution is going to repair the deletions, that caused the
	 * delete-preserve conflict, anyway.
	 * <p>
	 * So we check, if the specified match is part of an inconsistent changes conflict, since resolution
	 * of these conflicts repairs the deleted structure in all cases.
	 * 
	 * @param match
	 * @return true if delete-preserve conflicts above may be ignored
	 */
	private synchronized boolean isDeletionRepairableConflictedMatch(ITGGMatch match) {
		ConflictContainer container = match2conflictContainer.get(match);
		if (container == null)
			return false;

		for (Conflict conflict : container.getConflicts()) {
			if (conflict instanceof InconsistentChangesConflict)
				return true;
		}
		return false;
	}

	private boolean hasDomainSpecificViolations(ITGGMatch match, DomainType domain) {
		BrokenMatch brokenMatch = integrate.getClassifiedBrokenMatches().get(match);
		if (brokenMatch == null)
			brokenMatch = new BrokenMatch(integrate, match, false);

		switch (domain) {
		case SRC:
			if (DeletionType.getSrcDelCandidates().contains(brokenMatch.getDeletionType()))
				return true;
			break;
		case TRG:
			if (DeletionType.getTrgDelCandidates().contains(brokenMatch.getDeletionType()))
				return true;
			break;
		default:
			break;
		}

		if (brokenMatch.getFilterNacViolations().containsValue(domain))
			return true;

		// TODO adrianm: check attributes

		return false;
	}

	private DomainType oppositeOf(DomainType type) {
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

	private void detectBrokenMatchBasedConflicts() {
		integrate.getClassifiedBrokenMatches().values().parallelStream() //
				.forEach(brokenMatch -> detectBrokenMatchBasedConflicts(brokenMatch));
	}

	private void detectBrokenMatchBasedConflicts(BrokenMatch brokenMatch) {
		ConflictContainer container = match2conflictContainer.computeIfAbsent(brokenMatch.getMatch(), //
				key -> new ConflictContainer(integrate, brokenMatch.getMatch()));

		detectConflictsCausedByContradictoryChanges(container, brokenMatch);
		detectAttributeConflicts(container, brokenMatch);
	}

	private void detectAttributeConflicts(ConflictContainer container, BrokenMatch brokenMatch) {
		for (ConstrainedAttributeChanges constrAttrChanges : brokenMatch.getConstrainedAttrChanges()) {
			TGGAttributeConstraintDefinition def = constrAttrChanges.constraint.getDefinition();
			if (def.isUserDefined() || !def.getName().startsWith("eq_")) {
				logger.error("Conflicted AttributeConstraints that are not equality constraints are currently not supported!");
				continue;
			}
			if (constrAttrChanges.constraint.getParameters().size() > 2) {
				logger.error("Conflicted AttributeConstraints with more than 2 parameters are currently not supported!");
				continue;
			}

			if (constrAttrChanges.affectedParams.size() > 1) {
				AttributeChange srcChange = null;
				AttributeChange trgChange = null;

				for (TGGAttributeExpression param : constrAttrChanges.affectedParams.keySet()) {
					switch (param.getObjectVar().getDomainType()) {
					case SRC:
						srcChange = constrAttrChanges.affectedParams.get(param);
						break;
					case TRG:
						trgChange = constrAttrChanges.affectedParams.get(param);
					default:
						break;
					}
				}

				if (srcChange == null || trgChange == null)
					logger.error("User attribute edit was not domain conform!");

				createAttrConflict(container, constrAttrChanges, srcChange, trgChange);
			}
		}
	}

	private boolean detectConflictsCausedByContradictoryChanges(ConflictContainer container, BrokenMatch brokenMatch) {
		DomainModification domainModSrc = brokenMatch.getDeletionPattern().getModType(DomainType.SRC, BindingType.CREATE);
		DomainModification domainModTrg = brokenMatch.getDeletionPattern().getModType(DomainType.TRG, BindingType.CREATE);

		boolean nacAtSrc = brokenMatch.getFilterNacViolations().containsValue(DomainType.SRC);
		boolean nacAtTrg = brokenMatch.getFilterNacViolations().containsValue(DomainType.TRG);

		boolean partlyModSrc = domainModSrc == DomainModification.PART_DEL || nacAtSrc;
		boolean partlyModTrg = domainModTrg == DomainModification.PART_DEL || nacAtTrg;

		if (partlyModSrc && partlyModTrg) {
			createCorrPresConflict(container);
			return true;
		}

		if (!partlyModSrc && !partlyModTrg)
			return false;

		boolean anyConflictsDetected = false;
		if (partlyModSrc) {
			Set<ITGGMatch> srcMatches = computeSrcMatches(container.getMatch());

			// Detect delete-preserve conflicts whose conflicting creations are involved in a reparable
			// delta (source domain partly deleted plus related creations). Thus, after conflict resolution the
			// reparable match has to be explicitly repaired.
			{
				// In case the conflicting match i.e. the match which has deletions is not the same as the
				// reparable match, we additionally have to detect possible conflicts starting from all qualified
				// source matches.
				for (ITGGMatch srcMatch : srcMatches)
					if (detectDeletePreserveEdgeConflict(integrate.getPrecedenceGraph().getNode(srcMatch), brokenMatch.getMatch()))
						anyConflictsDetected = true;

				// In case the conflicting match is also the reparable match, we simply can create the conflict.
				if (domainModTrg == DomainModification.COMPL_DEL) {
					for (ITGGMatch srcMatch : srcMatches) {
						createDelPresEdgeConflict(container, srcMatch, DomainType.SRC, brokenMatch.getMatch(), brokenMatch.getMatch());
						anyConflictsDetected = true;
					}
				}
			}

			// If there are changes at source domain which are inconsistent w.r.t. the specified TGG rules,
			// an appropriate conflict is created.
			if (!anyConflictsDetected) {
				createInconsDomainChangesConflict(container, DomainType.SRC);
				return true;
			}
		} else {
			Set<ITGGMatch> trgMatches = computeTrgMatches(container.getMatch());

			// Detect delete-preserve conflicts whose conflicting creations are involved in a reparable
			// delta (target domain partly deleted plus related creations). Thus, after conflict resolution the
			// reparable match has to be explicitly repaired.
			{
				// In case the conflicting match i.e. the match which has deletions is not the same as the
				// reparable match, we additionally have to detect possible conflicts starting from all qualified
				// target matches.
				for (ITGGMatch trgMatch : trgMatches)
					if (detectDeletePreserveEdgeConflict(integrate.getPrecedenceGraph().getNode(trgMatch), brokenMatch.getMatch()))
						anyConflictsDetected = true;

				// In case the conflicting match is also the reparable match, we simply can create the conflict.
				if (domainModSrc == DomainModification.COMPL_DEL) {
					for (ITGGMatch trgMatch : trgMatches) {
						createDelPresEdgeConflict(container, trgMatch, DomainType.TRG, brokenMatch.getMatch(), brokenMatch.getMatch());
						anyConflictsDetected = true;
					}
				}
			}

			// If there are changes at target domain which are inconsistent w.r.t. the specified TGG rules,
			// an appropriate conflict is created.
			if (!anyConflictsDetected) {
				createInconsDomainChangesConflict(container, DomainType.TRG);
				return true;
			}
		}

		return true;
	}

	/**
	 * Finds all valid source matches whose green nodes (partly) overlap with the green nodes from the
	 * specified match.
	 * 
	 * @param match
	 * @return a set of all source matches
	 */
	private Set<ITGGMatch> computeSrcMatches(ITGGMatch match) {
		Set<ITGGMatch> srcMatches = new HashSet<>();

		IGreenPatternFactory gFactory = integrate.getGreenFactory(match.getRuleName());
		for (TGGRuleNode ruleNode : gFactory.getGreenSrcNodesInRule()) {
			for (PrecedenceNode node : integrate.getPrecedenceGraph().getNodesTranslating(match.get(ruleNode.getName()))) {
				if (node.getMatch().getType() == PatternType.CONSISTENCY)
					continue;

				if (integrate.getPrecedenceGraph().getSourceNodes().contains(node))
					srcMatches.add(node.getMatch());
			}
		}

		return srcMatches;
	}

	/**
	 * Finds all valid target matches whose green nodes (partly) overlap with the green nodes from the
	 * specified match.
	 * 
	 * @param match
	 * @return a set of all target matches
	 */
	private Set<ITGGMatch> computeTrgMatches(ITGGMatch match) {
		Set<ITGGMatch> trgMatches = new HashSet<>();

		IGreenPatternFactory gFactory = integrate.getGreenFactory(match.getRuleName());
		for (TGGRuleNode ruleNode : gFactory.getGreenTrgNodesInRule()) {
			for (PrecedenceNode node : integrate.getPrecedenceGraph().getNodesTranslating(match.get(ruleNode.getName()))) {
				if (node.getMatch().getType() == PatternType.CONSISTENCY)
					continue;

				if (integrate.getPrecedenceGraph().getTargetNodes().contains(node))
					trgMatches.add(node.getMatch());
			}
		}

		return trgMatches;
	}

	private List<ITGGMatch> computeSortedRollBackCausesIfAbsent(ITGGMatch directCausingMatch) {
		return match2sortedRollBackCauses.computeIfAbsent(directCausingMatch, //
				k -> integrate.getPrecedenceGraph().getNode(directCausingMatch).computeSortedRollBackCauses().stream() //
						.map(n -> n.getMatch()) //
						.collect(Collectors.toList()));
	}

	public Map<ITGGMatch, ConflictContainer> detectOpMultiplicityConflicts() {
		Map<ITGGMatch, ConflictContainer> match2conflictContainer = new HashMap<>();

		// fill multiplicity counter with applicable fwd & bwd matches (consistency matches are caught
		// directly at INTEGRATE)
		Set<ITGGMatch> actFwdBwdMatches = integrate.getMatchContainer().getMatches();
		for (ITGGMatch fwdBwdMatch : Sets.difference(actFwdBwdMatches, cachedAddedFwdBwdMatches))
			integrate.getMultiplicityCounter().notifyAddedMatch(fwdBwdMatch);
		for (ITGGMatch fwdBwdMatch : Sets.difference(cachedAddedFwdBwdMatches, actFwdBwdMatches))
			integrate.getMultiplicityCounter().notifyRemovedMatch(fwdBwdMatch);
		cachedAddedFwdBwdMatches = new HashSet<>(actFwdBwdMatches);

		// detect conflicts
		integrate.getMultiplicityCounter().getSubject2reference2numOfEdges().forEach((subj, ref2numOfEdges) -> {
			ref2numOfEdges.forEach((ref, numOfEdges) -> {
				int violationCounter = integrate.getMultiplicityCounter().violatesMultiplicity(ref, numOfEdges);
				if (violationCounter != 0) {
					ITGGMatch underlyingMatch = null;
					for (PrecedenceNode node : integrate.getPrecedenceGraph().getNodesTranslating(subj)) {
						if (node.getMatch().getType() == PatternType.CONSISTENCY) {
							underlyingMatch = node.getMatch();
							break;
						}
					}

					if (underlyingMatch == null)
						throw new RuntimeException("There must exist an underlying consistency match!");

					final ITGGMatch tmpUnderlyingMatch = underlyingMatch;
					ConflictContainer container = match2conflictContainer.computeIfAbsent(underlyingMatch, //
							key -> new ConflictContainer(integrate, tmpUnderlyingMatch));

					OutgoingEdge outgoingEdge = new OutgoingEdge(subj, ref);
					Map<ITGGMatch, Integer> addedMatches = integrate.getMultiplicityCounter().getOutgoingEdge2addedMatches2numOfEdges() //
							.getOrDefault(outgoingEdge, Collections.emptyMap());
					Map<ITGGMatch, Integer> removedMatches = integrate.getMultiplicityCounter().getOutgoingEdge2removedMatches2numOfEdges() //
							.getOrDefault(outgoingEdge, Collections.emptyMap());

					new OperationalMultiplicityConflict(container, subj, ref, violationCounter, addedMatches, removedMatches);
				}
			});
		});

		return match2conflictContainer;
	}

	//// CONFLICT FACTORIES ////

	private synchronized void createDelPresEdgeConflict(ConflictContainer container, ITGGMatch srcTrgMatch, DomainType domainToBePreserved,
			ITGGMatch directCausingMatch) {
		new DelPreserveEdgeConflict(container, srcTrgMatch, domainToBePreserved, computeSortedRollBackCausesIfAbsent(directCausingMatch));
	}

	private synchronized void createDelPresEdgeConflict(ConflictContainer container, ITGGMatch srcTrgMatch, DomainType domainToBePreserved,
			ITGGMatch directCausingMatch, ITGGMatch matchToBeRepaired) {
		new DelPreserveEdgeConflict(container, srcTrgMatch, domainToBePreserved, computeSortedRollBackCausesIfAbsent(directCausingMatch),
				matchToBeRepaired);
	}

	private synchronized void createDelPresAttrConflict(ConflictContainer container, AttributeChange attrChange, DomainType domainToBePreserved,
			ITGGMatch directCausingMatch) {
		new DelPreserveAttrConflict(container, attrChange, domainToBePreserved, computeSortedRollBackCausesIfAbsent(directCausingMatch));
	}

	private synchronized void createAttrConflict(ConflictContainer container, ConstrainedAttributeChanges conflictedConstraint,
			AttributeChange srcChange, AttributeChange trgChange) {
		new AttributeConflict(container, conflictedConstraint, srcChange, trgChange);
	}

	private synchronized void createCorrPresConflict(ConflictContainer container) {
		new CorrPreservationConflict(container);
	}

	private synchronized void createInconsDomainChangesConflict(ConflictContainer container, DomainType changedDomain) {
		new InconsDomainChangesConflict(container, changedDomain);
	}

}
