package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AttributeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.CorrPreservationConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DelPreserveAttrConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DelPreserveEdgeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePreserveConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.InconsDomainChangesConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.OperationalMultiplicityConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection.MultiplicityCounter.OutgoingEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;

import com.google.common.collect.Sets;

import language.DomainType;
import language.TGGAttributeConstraintDefinition;
import language.TGGAttributeExpression;
import language.TGGRuleNode;

public class ConflictDetector {

	protected final static Logger logger = Logger.getLogger(ConflictDetector.class);

	private INTEGRATE integrate;

	private MultiplicityCounter multiplicityCounter;
	private Set<PrecedenceNode> cachedAddedConsNodes;
	private Set<ITGGMatch> cachedAddedFwdBwdMatches;

	private Map<ITGGMatch, ConflictContainer> match2conflictContainer;
	private Map<ITGGMatch, List<ITGGMatch>> match2sortedRollBackCauses;

	public ConflictDetector(INTEGRATE integrate) {
		this.integrate = integrate;
		this.multiplicityCounter = new MultiplicityCounter(integrate.getOptions().tgg.getFlattenedConcreteTGGRules());
		cachedAddedConsNodes = new HashSet<>();
		cachedAddedFwdBwdMatches = new HashSet<>();
	}

	public Set<ConflictContainer> detectConflicts() {
		match2conflictContainer = Collections.synchronizedMap(new HashMap<>());
		match2sortedRollBackCauses = Collections.synchronizedMap(new HashMap<>());

		integrate.getClassifiedBrokenMatches().values().parallelStream() //
				.forEach(brokenMatch -> detectMatchConflicts(brokenMatch));
		detectDeletePreserveConflicts();
		detectOpMultiplicityConflicts();

		// sort out empty conflict containers
		for (Iterator<ConflictContainer> iterator = match2conflictContainer.values().iterator(); iterator.hasNext();) {
			ConflictContainer container = (ConflictContainer) iterator.next();
			if (container.getConflicts().isEmpty())
				iterator.remove();
		}

		return new HashSet<>(match2conflictContainer.values());
	}

	private void detectDeletePreserveConflicts() {
		// we only iterate over src/trg matches which are not part of a consistency match (already
		// filtered in precedence graph)

		// TODO adrianm: parallel stream causes concurrent modification exception
		integrate.getPrecedenceGraph().getSourceNodes().stream() //
				.filter(srcNode -> !integrate.getPrecedenceGraph().hasAnyConsistencyOverlap(srcNode)) //
				.forEach(srcNode -> detectDeletePreserveEdgeConflict(srcNode, DomainType.SRC));

		integrate.getPrecedenceGraph().getTargetNodes().stream() //
				.filter(trgNode -> !integrate.getPrecedenceGraph().hasAnyConsistencyOverlap(trgNode)) //
				.forEach(trgNode -> detectDeletePreserveEdgeConflict(trgNode, DomainType.TRG));

		detectDeletePreserveAttrConflicts();
	}

	private void detectDeletePreserveEdgeConflict(PrecedenceNode node, DomainType domainToBePreserved) {
		Set<PrecedenceNode> directRollBackCauses = new HashSet<>();
		node.forAllToBeRolledBackBy((act, pre) -> {
			// TODO adrianm: improve performance
			if (integrate.getPrecedenceGraph().hasAnyConsistencyOverlap(act))
				return false;
			if (act.isBroken()) {
				directRollBackCauses.add(act);
				return false;
			}
			return true;
		});

		for (PrecedenceNode rollBackCause : directRollBackCauses) {
			ConflictContainer container = match2conflictContainer.computeIfAbsent(rollBackCause.getMatch(), //
					key -> new ConflictContainer(integrate, rollBackCause.getMatch()));

			boolean skipCheckDomainSpecificViolations = skipCheckDomainSpecificViolations(container, domainToBePreserved);

			if (skipCheckDomainSpecificViolations || checkDomainSpecificViolations(rollBackCause, oppositeOf(domainToBePreserved)))
				new DelPreserveEdgeConflict(container, node.getMatch(), domainToBePreserved, match2sortedRollBackCauses.get(rollBackCause.getMatch()));
		}
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
					new DelPreserveAttrConflict(container, change, ruleNode.getDomainType(), match2sortedRollBackCauses.get(node.getMatch()));
			}
		}
	}

	private boolean skipCheckDomainSpecificViolations(ConflictContainer container, DomainType domain) {
		return container.getConflicts().stream() //
				.filter(conflict -> conflict instanceof DeletePreserveConflict) //
				.map(conflict -> (DeletePreserveConflict) conflict) //
				.anyMatch(conflict -> conflict.getDomainToBePreserved() == domain);
	}

	private boolean checkDomainSpecificViolations(PrecedenceNode rollBackCause, DomainType criticalDomain) {
		List<ITGGMatch> rollBackCauses = match2sortedRollBackCauses.computeIfAbsent(rollBackCause.getMatch(), match -> {
			return rollBackCause.computeSortedRollBackCauses().stream() //
					.map(node -> node.getMatch()) //
					.collect(Collectors.toList());
		});

		for (ITGGMatch match : rollBackCauses) {
			BrokenMatch brokenMatch = integrate.getClassifiedBrokenMatches().get(match);
			if (brokenMatch == null)
				continue;
			if (hasDomainSpecificViolations(brokenMatch, criticalDomain))
				return true;
		}
		return false;
	}

	private boolean hasDomainSpecificViolations(BrokenMatch cMatch, DomainType domain) {
		switch (domain) {
		case SRC:
			if (DeletionType.getSrcDelCandidates().contains(cMatch.getDeletionType()))
				return true;
			break;
		case TRG:
			if (DeletionType.getTrgDelCandidates().contains(cMatch.getDeletionType()))
				return true;
			break;
		default:
			break;
		}

		if (cMatch.getFilterNacViolations().containsValue(domain))
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

	private void detectMatchConflicts(BrokenMatch brokenMatch) {
		ConflictContainer container = match2conflictContainer.computeIfAbsent(brokenMatch.getMatch(), //
				key -> new ConflictContainer(integrate, brokenMatch.getMatch()));

		detectInconsistentChangesConflict(container, brokenMatch);
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

				new AttributeConflict(container, constrAttrChanges, srcChange, trgChange);
			}
		}
	}

	private boolean detectInconsistentChangesConflict(ConflictContainer container, BrokenMatch brokenMatch) {
		if (DeletionType.getInconsDelsCandidates().contains(brokenMatch.getDeletionType())) {
			if (brokenMatch.getDeletionType() == DeletionType.SRC_PARTLY_TRG_NOT) {
				new InconsDomainChangesConflict(container, DomainType.SRC);
				return true;
			}
			if (brokenMatch.getDeletionType() == DeletionType.SRC_NOT_TRG_PARTLY) {
				new InconsDomainChangesConflict(container, DomainType.TRG);
				return true;
			}
			new CorrPreservationConflict(container);
			return true;
		}

		if (brokenMatch.getFilterNacViolations().isEmpty())
			return false;

		boolean nacAtSrc = brokenMatch.getFilterNacViolations().containsValue(DomainType.SRC);
		boolean nacAtTrg = brokenMatch.getFilterNacViolations().containsValue(DomainType.TRG);
		if (nacAtSrc && nacAtTrg) {
			new CorrPreservationConflict(container);
			return true;
		}
		if (nacAtSrc) {
			if (DeletionType.getPropBWDCandidates().contains(brokenMatch.getDeletionType())) {
				new CorrPreservationConflict(container);
				return true;
			}
			new InconsDomainChangesConflict(container, DomainType.SRC);
			return true;
		}
		if (nacAtTrg) {
			if (DeletionType.getPropFWDCandidates().contains(brokenMatch.getDeletionType())) {
				new CorrPreservationConflict(container);
				return true;
			}
			new InconsDomainChangesConflict(container, DomainType.TRG);
			return true;
		}

		return false;
	}

	private void detectOpMultiplicityConflicts() {
		// fill multiplicity counter
		Set<PrecedenceNode> actConsNodes = integrate.getPrecedenceGraph().getConsistencyNodes();
		for (PrecedenceNode consNode : Sets.difference(actConsNodes, cachedAddedConsNodes))
			multiplicityCounter.addMatch(consNode.getMatch());
		for (PrecedenceNode consNode : Sets.difference(cachedAddedConsNodes, actConsNodes))
			multiplicityCounter.removeMatch(consNode.getMatch());
		cachedAddedConsNodes = new HashSet<>(actConsNodes);

		Set<ITGGMatch> actFwdBwdMatches = integrate.getMatchContainer().getMatches();
		for (ITGGMatch fwdBwdMatch : Sets.difference(actFwdBwdMatches, cachedAddedFwdBwdMatches))
			multiplicityCounter.addMatch(fwdBwdMatch);
		for (ITGGMatch fwdBwdMatch : Sets.difference(cachedAddedFwdBwdMatches, actFwdBwdMatches))
			multiplicityCounter.removeMatch(fwdBwdMatch);

		// detect conflicts
		multiplicityCounter.getSubject2reference2numOfEdges().forEach((subj, ref2numOfEdges) -> {
			ref2numOfEdges.forEach((ref, numOfEdges) -> {
				if (multiplicityCounter.violatesMultiplicity(ref, numOfEdges)) {
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

					Set<ITGGMatch> violatingMatches = multiplicityCounter.getOutgoingEdge2matches() //
							.getOrDefault(new OutgoingEdge(subj, ref), Collections.emptySet());

					new OperationalMultiplicityConflict(container, subj, ref, violatingMatches);
				}
			});
		});
	}

}
