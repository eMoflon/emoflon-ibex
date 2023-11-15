package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRuleFactory.MatchRelatedRuleElement;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRuleFactory.MatchRelatedRuleElementMap;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Comparator;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import com.google.common.collect.Sets;

import language.BindingType;
import language.DomainType;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ILPHigherOrderRuleMappingSolver {

	private final TGGMatchUtilProvider mup;
	private final SupportedILPSolver solver;

	private final Collection<ITGGMatch> srcTrgMatches;
	private final MatchRelatedRuleElementMap propDomainMapping;
	private final MatchRelatedRuleElementMap oppositeDomainMapping;
	private final MatchRelatedRuleElementMap corrDomainMappings;
	private final MatchRelatedRuleElementMap consMappings;
	private final Map<EObject, MatchRelatedRuleElement> consEObject2elements;

	private int elementIDCounter = 0;
	private int matchIDCounter = 0;
	private int consIDCounter = 0;
	private int constraintNameCounter = 0;
	private int helperVarCounter = 0;
	private int noConcatVarCounter = 0;

	private Map<MatchRelatedRuleElement, Set<Integer>> propDomainElement2Candidates = cfactory.createObjectToObjectHashMap();
	private Map<MatchRelatedRuleElement, Set<Integer>> oppositeDomainElement2Candidates = cfactory.createObjectToObjectHashMap();
	private Map<MatchRelatedRuleElement, Set<Integer>> corrDomainElement2Candidates = cfactory.createObjectToObjectHashMap();
	private Map<MatchRelatedRuleElement, Set<Integer>> element2ConsCandidates = cfactory.createObjectToObjectHashMap();
	private Map<MatchRelatedRuleElement, Set<Integer>> consElement2ConsCandidates = cfactory.createObjectToObjectHashMap();

	private Map<ElementCandidate, Integer> candidate2ID = cfactory.createObjectToIntHashMap();
	private Map<Integer, ElementCandidate> id2Candidate = cfactory.createIntToObjectHashMap();

	private Set<Set<ITGGMatch>> overlappingMatches = cfactory.createObjectSet();
	private Map<ITGGMatch, Integer> match2ID = cfactory.createObjectToIntHashMap();
	private Map<Integer, ITGGMatch> id2match = cfactory.createIntToObjectHashMap();

	private Map<ElementCandidate, Integer> consCandidate2ID = cfactory.createObjectToIntHashMap();
	private Map<Integer, ElementCandidate> id2ConsCandidate = cfactory.createIntToObjectHashMap();

	private static final double EDGE_COEFF = 0.0000001;
	private static final double SIDE_OBJECTIVE = 0.0000000001;
	private static final double CONTEXT_TARGET_COEFF = 0.0000000000001;
	private Set<Integer> candidatesWithContextTarget = cfactory.createIntSet();

	private Map<MatchRelatedRuleElement, MatchRelatedRuleElement> mappingResult;

	public ILPHigherOrderRuleMappingSolver( //
			TGGMatchUtilProvider mup, //
			SupportedILPSolver solver, //
			Collection<ITGGMatch> srcTrgMatches, //
			MatchRelatedRuleElementMap propDomainMapping, //
			MatchRelatedRuleElementMap oppositeDomainMapping, //
			MatchRelatedRuleElementMap corrDomainMappings, //
			MatchRelatedRuleElementMap consMappings, //
			Map<EObject, MatchRelatedRuleElement> consEObject2elements //
	) {
		this.mup = mup;
		this.solver = solver;
		this.srcTrgMatches = srcTrgMatches;
		this.propDomainMapping = propDomainMapping;
		this.oppositeDomainMapping = oppositeDomainMapping;
		this.corrDomainMappings = corrDomainMappings;
		this.consMappings = consMappings;
		this.consEObject2elements = consEObject2elements;

		solve();
	}

	public Map<MatchRelatedRuleElement, MatchRelatedRuleElement> getResult() {
		return mappingResult;
	}

	private void solve() {
		createCandidates();
		BinaryILPProblem ilpProblem = createILPProblem();
		int[] solution = solveILPProblem(ilpProblem);
		Set<ElementCandidate> candidates = convertSolutionToCandidates(solution);
		convertCandidatesToMappingResult(candidates);
	}

	private void createCandidates() {
		propDomainMapping.forEach(this::createPropDomainCandidates);
		oppositeDomainMapping.forEach(this::createOppositeDomainCandidates);
		corrDomainMappings.forEach(this::createCorrDomainCandidates);
		consMappings.forEach(this::createConsCandidates);
		createMatchCandidates();
	}

	private void createPropDomainCandidates(MatchRelatedRuleElement source, Set<MatchRelatedRuleElement> targets) {
		for (MatchRelatedRuleElement target : targets) {
			ElementCandidate candidate = new ElementCandidate(source, target);

			propDomainElement2Candidates.computeIfAbsent(source, k -> cfactory.createIntSet()).add(elementIDCounter);
			candidate2ID.put(candidate, elementIDCounter);
			id2Candidate.put(elementIDCounter, candidate);
			elementIDCounter++;
		}
	}

	private void createOppositeDomainCandidates(MatchRelatedRuleElement source, Set<MatchRelatedRuleElement> targets) {
		for (MatchRelatedRuleElement target : targets) {
			ElementCandidate candidate = new ElementCandidate(source, target);

			if (target.ruleElement().getBindingType() == BindingType.CONTEXT)
				candidatesWithContextTarget.add(elementIDCounter);
			oppositeDomainElement2Candidates.computeIfAbsent(source, k -> cfactory.createIntSet()).add(elementIDCounter);
			candidate2ID.put(candidate, elementIDCounter);
			id2Candidate.put(elementIDCounter, candidate);
			elementIDCounter++;
		}
	}

	private void createCorrDomainCandidates(MatchRelatedRuleElement source, Set<MatchRelatedRuleElement> targets) {
		for (MatchRelatedRuleElement target : targets) {
			ElementCandidate candidate = new ElementCandidate(source, target);

			corrDomainElement2Candidates.computeIfAbsent(source, k -> cfactory.createIntSet()).add(elementIDCounter);
			candidate2ID.put(candidate, elementIDCounter);
			id2Candidate.put(elementIDCounter, candidate);
			elementIDCounter++;
		}
	}

	private void createConsCandidates(MatchRelatedRuleElement source, Set<MatchRelatedRuleElement> targets) {
		for (MatchRelatedRuleElement target : targets) {
			ElementCandidate candidate = new ElementCandidate(source, target);

			element2ConsCandidates.computeIfAbsent(source, k -> cfactory.createIntSet()).add(consIDCounter);
			consElement2ConsCandidates.computeIfAbsent(target, k -> cfactory.createIntSet()).add(consIDCounter);
			consCandidate2ID.put(candidate, consIDCounter);
			id2ConsCandidate.put(consIDCounter, candidate);
			consIDCounter++;
		}
	}

	private void createMatchCandidates() {
		// FIXME: how overlapping matches are defined here, is probably incorrect
		Set<Set<ITGGMatch>> matchSets = propDomainMapping.values().stream() //
				.map(set -> set.stream().map(e -> e.match()).collect(Collectors.toSet())) //
				.collect(Collectors.toSet());

		for (Set<ITGGMatch> matchSet : matchSets) {
			Set<Set<ITGGMatch>> overlappingSets = new HashSet<>();
			for (Set<ITGGMatch> collapsedMatchSet : overlappingMatches) {
				if (!Sets.intersection(matchSet, collapsedMatchSet).isEmpty())
					overlappingSets.add(collapsedMatchSet);
			}

			Set<ITGGMatch> matchSetCopy = new HashSet<>(matchSet);
			for (Set<ITGGMatch> overlappingSet : overlappingSets) {
				matchSetCopy.addAll(overlappingSet);
				overlappingMatches.remove(overlappingSet);
			}
			overlappingMatches.add(matchSetCopy);
		}

		// register match candidates
		srcTrgMatches.forEach(match -> {
			match2ID.put(match, matchIDCounter);
			id2match.put(matchIDCounter, match);
			matchIDCounter++;
		});
	}

	private BinaryILPProblem createILPProblem() {
		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		defineExclusionsForPropDomain(ilpProblem);
		defineExclusionsForOppositeDomain(ilpProblem);
		defineExclusionsForCorrDomain(ilpProblem);
		defineExclusionsForConsMapping(ilpProblem);

		defineExclusionsForMatches(ilpProblem);

		defineImplicationsForPropDomain(ilpProblem);
		defineImplicationsForOppositeDomain(ilpProblem);
		defineImplicationsForCorrDomain(ilpProblem);
		defineImplicationsForConsMapping(ilpProblem);

		defineObjective(ilpProblem);

		return ilpProblem;
	}

	private void defineExclusionsForPropDomain(BinaryILPProblem ilpProblem) {
		// each element has to be mapped onto exactly one other element:
		for (MatchRelatedRuleElement element : propDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = propDomainElement2Candidates.get(element);

			if (candidates.size() <= 1)
				continue;

			ilpProblem.addExclusion(candidates.stream().map(c -> "e" + c), //
					"EXCL_propDomElement_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++, //
					1, 1);
		}
	}

	private void defineExclusionsForOppositeDomain(BinaryILPProblem ilpProblem) {
		// each element has to be mapped onto at most one other element:
		for (MatchRelatedRuleElement element : oppositeDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = oppositeDomainElement2Candidates.get(element);

			if (candidates.size() <= 1)
				continue;

			ilpProblem.addExclusion(candidates.stream().map(c -> "e" + c), //
					"EXCL_oppositeDomElement_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
		}
	}

	private void defineExclusionsForCorrDomain(BinaryILPProblem ilpProblem) {
		// each element has to be mapped onto at most one other element:
		for (MatchRelatedRuleElement element : corrDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = corrDomainElement2Candidates.get(element);

			if (candidates.size() <= 1)
				continue;

			ilpProblem.addExclusion(candidates.stream().map(c -> "e" + c), //
					"EXCL_corrDomElement_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
		}
	}

	private void defineExclusionsForConsMapping(BinaryILPProblem ilpProblem) {
		// each element of a source/target match has to be mapped onto at most one element of the broken
		// consistency matches:
		for (MatchRelatedRuleElement element : element2ConsCandidates.keySet()) {
			Set<Integer> candidates = element2ConsCandidates.get(element);

			if (candidates.size() <= 1)
				continue;

			ilpProblem.addExclusion(candidates.stream().map(c -> "c" + c), //
					"EXCL_consMapping_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
		}

		// each element of a broken consistency match has to be mapped onto at most one element of the
		// source/target matches:
		for (MatchRelatedRuleElement element : consElement2ConsCandidates.keySet()) {
			Set<Integer> candidates = consElement2ConsCandidates.get(element);

			if (candidates.size() <= 1)
				continue;

			ilpProblem.addExclusion(candidates.stream().map(c -> "c" + c), //
					"EXCL_consMapping_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
		}
	}

	private void defineExclusionsForMatches(BinaryILPProblem ilpProblem) {
		// matches that translate the same elements cannot be chosen at the same time:
		for (Set<ITGGMatch> matchSet : overlappingMatches) {
			if (matchSet.size() <= 1)
				continue;

			ilpProblem.addExclusion(matchSet.stream().map(c -> "m" + c), //
					"EXCL_match_" + constraintNameCounter++);
		}
	}

	private void defineImplicationsForPropDomain(BinaryILPProblem ilpProblem) {
		// if an element for a mapping is chosen, the respective match has to be chosen as well:
		for (MatchRelatedRuleElement element : propDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = propDomainElement2Candidates.get(element);

			for (Integer candidateID : candidates) {
				ITGGMatch match = id2Candidate.get(candidateID).target.match();
				ilpProblem.addImplication("e" + candidateID, Stream.of("m" + match2ID.get(match)), //
						"IMPL_propDomElement->match_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
			}
		}
	}

	private void defineImplicationsForOppositeDomain(BinaryILPProblem ilpProblem) {
		for (MatchRelatedRuleElement element : oppositeDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = oppositeDomainElement2Candidates.get(element);

			// if an element for a mapping is chosen, the respective match also has to be chosen:
			for (Integer candidateID : candidates) {
				ITGGMatch match = id2Candidate.get(candidateID).target.match();
				ilpProblem.addImplication("e" + candidateID, Stream.of("m" + match2ID.get(match)), //
						"IMPL_oppositeDomElement->match_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
			}

			// if an edge is chosen, it's source and target nodes have to be chosen as well and all three have
			// to be mapped to the same match:
			if (element.ruleElement() instanceof TGGRuleEdge ruleEdge) {
				MatchRelatedRuleElement srcNodeElement = new MatchRelatedRuleElement(ruleEdge.getSrcNode(), element.match());
				MatchRelatedRuleElement trgNodeElement = new MatchRelatedRuleElement(ruleEdge.getTrgNode(), element.match());

				for (Integer candidateID : candidates) {
					ElementCandidate edgeCandidate = id2Candidate.get(candidateID);
					TGGRuleEdge mappedRuleEdge = (TGGRuleEdge) edgeCandidate.target.ruleElement();
					MatchRelatedRuleElement mappedSrcNodeElement = new MatchRelatedRuleElement( //
							mappedRuleEdge.getSrcNode(), edgeCandidate.target.match());
					MatchRelatedRuleElement mappedTrgNodeElement = new MatchRelatedRuleElement( //
							mappedRuleEdge.getTrgNode(), edgeCandidate.target.match());
					ElementCandidate srcNodeCandidate = new ElementCandidate(srcNodeElement, mappedSrcNodeElement);
					ElementCandidate trgNodeCandidate = new ElementCandidate(trgNodeElement, mappedTrgNodeElement);
					Integer srcNodeCandidateID = candidate2ID.get(srcNodeCandidate);
					Integer trgNodeCandidateID = candidate2ID.get(trgNodeCandidate);

					if (srcNodeCandidateID == null || trgNodeCandidateID == null) {
						ILPLinearExpression expression = ilpProblem.createLinearExpression();
						expression.addTerm("e" + candidateID, 1);
						ilpProblem.addConstraint(expression, Comparator.eq, 0, //
								"CONSTR_doNotChooseEdge_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
					} else {
						ilpProblem.addImplication("e" + candidateID, Stream.of("e" + srcNodeCandidateID), //
								"IMPL_edgePlusSrcNode_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
						ilpProblem.addImplication("e" + candidateID, Stream.of("e" + trgNodeCandidateID), //
								"IMPL_edgePlusTrgNode_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
					}
				}
			}
		}
	}

	private void defineImplicationsForCorrDomain(BinaryILPProblem ilpProblem) {
		// if a correspondence is chosen, it's source and target nodes have to be chosen as well and all
		// three have to be mapped to the same match:
		for (MatchRelatedRuleElement element : corrDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = corrDomainElement2Candidates.get(element);

			TGGRuleCorr ruleCorr = (TGGRuleCorr) element.ruleElement();
			MatchRelatedRuleElement srcNodeElement = new MatchRelatedRuleElement(ruleCorr.getSource(), element.match());
			MatchRelatedRuleElement trgNodeElement = new MatchRelatedRuleElement(ruleCorr.getTarget(), element.match());

			for (Integer candidateID : candidates) {
				ElementCandidate corrCandidate = id2Candidate.get(candidateID);
				TGGRuleCorr mappedRuleCorr = (TGGRuleCorr) corrCandidate.target.ruleElement();
				MatchRelatedRuleElement mappedSrcNodeElement = new MatchRelatedRuleElement( //
						mappedRuleCorr.getSource(), corrCandidate.target.match());
				MatchRelatedRuleElement mappedTrgNodeElement = new MatchRelatedRuleElement( //
						mappedRuleCorr.getTarget(), corrCandidate.target.match());
				ElementCandidate srcNodeCandidate = new ElementCandidate(srcNodeElement, mappedSrcNodeElement);
				ElementCandidate trgNodeCandidate = new ElementCandidate(trgNodeElement, mappedTrgNodeElement);
				Integer srcNodeCandidateID = candidate2ID.get(srcNodeCandidate);
				Integer trgNodeCandidateID = candidate2ID.get(trgNodeCandidate);

				if (srcNodeCandidateID == null || trgNodeCandidateID == null) {
					ILPLinearExpression expression = ilpProblem.createLinearExpression();
					expression.addTerm("e" + candidateID, 1);
					ilpProblem.addConstraint(expression, Comparator.eq, 0, //
							"CONSTR_doNotChooseCorr_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
				} else {
					ilpProblem.addImplication("e" + candidateID, Stream.of("e" + srcNodeCandidateID, "e" + trgNodeCandidateID), //
							"IMPL_corrPlusSrcTrgNodes_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
				}
			}
		}
	}

	private void defineImplicationsForConsMapping(BinaryILPProblem ilpProblem) {
		for (MatchRelatedRuleElement element : element2ConsCandidates.keySet()) {
			Set<Integer> candidates = element2ConsCandidates.get(element);

			// if an element for a mapping is chosen, the respective match also has to be chosen:
			for (Integer candidateID : candidates) {
				Integer matchID = match2ID.get(element.match());
				if (matchID == null)
					continue;
				ilpProblem.addImplication("c" + candidateID, Stream.of("m" + matchID), //
						"IMPL_consMappingElement->match_" + element.ruleElement().eClass().getName() + "_" + constraintNameCounter++);
			}

			// if an edge is chosen, it's source and target nodes have to be chosen as well
			if (element.ruleElement() instanceof TGGRuleEdge ruleEdge) {
				CreateNodeElementsContainer srcNodeElements = getCreateNodeElements(ruleEdge.getSrcNode(), element.match());
				CreateNodeElementsContainer trgNodeElements = getCreateNodeElements(ruleEdge.getTrgNode(), element.match());

				for (Integer candidateID : candidates) {
					ElementCandidate edgeCandidate = id2ConsCandidate.get(candidateID);
					TGGRuleEdge mappedRuleEdge = (TGGRuleEdge) edgeCandidate.target.ruleElement();
					MatchRelatedRuleElement mappedSrcNodeElement = getCreateConsNodeElement(mappedRuleEdge.getSrcNode(), edgeCandidate.target.match());
					MatchRelatedRuleElement mappedTrgNodeElement = getCreateConsNodeElement(mappedRuleEdge.getTrgNode(), edgeCandidate.target.match());

					if (mappedSrcNodeElement != null)
						defineImplicationForEdgeConsMapping(ilpProblem, ruleEdge, candidateID, srcNodeElements, mappedSrcNodeElement);
					if (mappedTrgNodeElement != null)
						defineImplicationForEdgeConsMapping(ilpProblem, ruleEdge, candidateID, trgNodeElements, mappedTrgNodeElement);
				}
			}
		}
	}

	private interface CreateNodeElementsContainer {
	}

	private record CreateInsideMatch(MatchRelatedRuleElement nodeElement) implements CreateNodeElementsContainer {
	}

	private record CreateOutsideMatch(Set<ElementCandidate> nodeElementCandidates) implements CreateNodeElementsContainer {
	}

	private void defineImplicationForEdgeConsMapping( //
			BinaryILPProblem ilpProblem, //
			TGGRuleEdge ruleEdge, //
			Integer edgeCandidateID, //
			CreateNodeElementsContainer nodeElementsContainer, //
			MatchRelatedRuleElement mappedNodeElement //
	) {
		if (nodeElementsContainer instanceof CreateInsideMatch createInsideMatch) {
			MatchRelatedRuleElement nodeElement = createInsideMatch.nodeElement;
			if (nodeElement.ruleElement().getDomainType() == DomainType.SRC) {
				TGGMatchUtil matchUtil = mup.get(nodeElement.match());
				Object object = matchUtil.getObject(nodeElement.ruleElement());
				TGGMatchUtil mappedMatchUtil = mup.get(mappedNodeElement.match());
				Object mappedObject = mappedMatchUtil.getObject(mappedNodeElement.ruleElement());
				if (!object.equals(mappedObject)) {
					ILPLinearExpression expression = ilpProblem.createLinearExpression();
					expression.addTerm("c" + edgeCandidateID, 1);
					ilpProblem.addConstraint(expression, Comparator.eq, 0, //
							"CONSTR_consDoNotChooseEdge_[" + ruleEdge.getName() + "]_" + constraintNameCounter++);
				}
			} else {
				ElementCandidate nodeCandidate = new ElementCandidate(nodeElement, mappedNodeElement);
				Integer nodeCandidateID = consCandidate2ID.get(nodeCandidate);
				if (nodeCandidateID == null) {
					ILPLinearExpression expression = ilpProblem.createLinearExpression();
					expression.addTerm("c" + edgeCandidateID, 1);
					ilpProblem.addConstraint(expression, Comparator.eq, 0, //
							"CONSTR_consDoNotChooseEdge_[" + ruleEdge.getName() + "]_" + constraintNameCounter++);
				} else {
					ilpProblem.addImplication("c" + edgeCandidateID, Stream.of("c" + nodeCandidateID), //
							"IMPL_consEdgePlusNode_[" + ruleEdge.getName() + "]_" + constraintNameCounter++);
				}
			}
		} else if (nodeElementsContainer instanceof CreateOutsideMatch createOutsideMatch) {
			Set<ElementCandidate> nodeConcatCandidates = createOutsideMatch.nodeElementCandidates;
			List<Integer> usedHelperVars = new LinkedList<>();
			for (ElementCandidate nodeConcatCandidate : nodeConcatCandidates) {
				ElementCandidate nodeCandidate = new ElementCandidate(nodeConcatCandidate.target, mappedNodeElement);
				Integer nodeCandidateID = consCandidate2ID.get(nodeCandidate);
				Integer nodeConcatCandidateID = candidate2ID.get(nodeConcatCandidate);
				if (nodeCandidateID != null && nodeConcatCandidateID != null) {
					ILPLinearExpression expression = ilpProblem.createLinearExpression();
					expression.addTerm("c" + nodeCandidateID, 1);
					expression.addTerm("e" + nodeConcatCandidateID, 1);
					expression.addTerm("v" + helperVarCounter, -2);
					ilpProblem.addConstraint(expression, Comparator.ge, 0, "HELPER_consEdgePlusNode");
					usedHelperVars.add(helperVarCounter);
					helperVarCounter++;
				}
			}
			ILPLinearExpression expression = ilpProblem.createLinearExpression();
			expression.addTerm("x" + noConcatVarCounter, 1);
			List<Integer> nodeConcatCandidateIDs = nodeConcatCandidates.stream() //
					.map(c -> candidate2ID.get(c)) //
					.filter(id -> id != null) //
					.toList();
			for (Integer nodeConcatCandidateID : nodeConcatCandidateIDs)
				expression.addTerm("e" + nodeConcatCandidateID, 1);
			ilpProblem.addConstraint(expression, Comparator.eq, 1, "HELPER_consEdgePlusNode");
			ilpProblem.addImplication("c" + edgeCandidateID, Stream.concat(usedHelperVars.stream().map(v -> "v" + v), Stream.of("x" + noConcatVarCounter)), //
					"IMPL_consEdgePlusNode_[" + ruleEdge.getName() + "]_" + constraintNameCounter++);
			noConcatVarCounter++;
		}
	}

	private CreateNodeElementsContainer getCreateNodeElements(TGGRuleNode node, ITGGMatch match) {
		MatchRelatedRuleElement element = new MatchRelatedRuleElement(node, match);
		if (node.getBindingType() == BindingType.CONTEXT) {
			Set<Integer> candidates = oppositeDomainElement2Candidates.get(element);
			if (candidates == null)
				return new CreateOutsideMatch(Collections.emptySet());
			Set<ElementCandidate> result = new HashSet<>();
			for (Integer candidateID : candidates) {
				ElementCandidate candidate = id2Candidate.get(candidateID);
				if (candidate.target.ruleElement().getBindingType() == BindingType.CREATE)
					result.add(candidate);
			}
			return new CreateOutsideMatch(result);
		}
		return new CreateInsideMatch(element);
	}

	private MatchRelatedRuleElement getCreateConsNodeElement(TGGRuleNode node, ITGGMatch match) {
		MatchRelatedRuleElement element = new MatchRelatedRuleElement(node, match);
		if (node.getBindingType() == BindingType.CONTEXT) {
			TGGMatchUtil matchUtil = mup.get(match);
			EObject eObject = matchUtil.getEObject(node);
			return consEObject2elements.get(eObject);
		}
		return element;
	}

	private void defineObjective(BinaryILPProblem ilpProblem) {
		ILPLinearExpression expression = ilpProblem.createLinearExpression();

		if (true) {
			for (int i = 0; i < consIDCounter; i++)
				expression.addTerm("c" + i, 1.0);
			for (int i = 0; i < elementIDCounter; i++)
				expression.addTerm("e" + i, candidatesWithContextTarget.contains(i) ? CONTEXT_TARGET_COEFF : SIDE_OBJECTIVE);
			for (int i = 0; i < noConcatVarCounter; i++)
				expression.addTerm("x" + i, SIDE_OBJECTIVE);
		} else {
			for (int i = 0; i < elementIDCounter; i++)
				expression.addTerm("e" + i, candidatesWithContextTarget.contains(i) ? CONTEXT_TARGET_COEFF : 1);
			for (int i = 0; i < matchIDCounter; i++)
				expression.addTerm("m" + i, 1);
		}

		ilpProblem.setObjective(expression, Objective.maximize);
	}

	private int[] solveILPProblem(BinaryILPProblem ilpProblem) {
		try {
			ILPSolution ilpSolution = ILPSolver.solveBinaryILPProblem(ilpProblem, solver);
			if (!ilpProblem.checkValidity(ilpSolution)) {
				throw new AssertionError("Invalid solution");
			}

			int[] result = new int[elementIDCounter];
			for (int i = 0; i < elementIDCounter; i++) {
				if (ilpSolution.getVariable("e" + i) > 0)
					result[i] = 1;
				else
					result[i] = -1;
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Solving ILP failed", e);
		}
	}

	private Set<ElementCandidate> convertSolutionToCandidates(int[] solution) {
		Set<ElementCandidate> resultingCandidates = new HashSet<>();

		for (int i = 0; i < solution.length; i++) {
			boolean useCandidate = solution[i] == 1;
			if (useCandidate && id2Candidate.containsKey(i))
				resultingCandidates.add(id2Candidate.get(i));
		}

		return resultingCandidates;
	}

	private void convertCandidatesToMappingResult(Set<ElementCandidate> candidates) {
		mappingResult = candidates.stream().collect(Collectors.toMap(c -> c.source, c -> c.target));
	}

	private record ElementCandidate(MatchRelatedRuleElement source, MatchRelatedRuleElement target) {
	}

}
