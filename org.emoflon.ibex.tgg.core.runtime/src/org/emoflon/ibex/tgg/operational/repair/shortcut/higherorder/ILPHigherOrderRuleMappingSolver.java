package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRuleFactory.MatchRelatedRuleElement;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRuleFactory.MatchRelatedRuleElementMap;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import com.google.common.collect.Sets;

import language.BindingType;

public class ILPHigherOrderRuleMappingSolver {

	private final SupportedILPSolver solver;

	private final MatchRelatedRuleElementMap propDomainMapping;
	private final MatchRelatedRuleElementMap oppositeDomainMapping;

	private int elementIDCounter = 0;
	private int matchIDCounter = 0;
	private int constraintNameCounter = 0;

	private Map<MatchRelatedRuleElement, Set<Integer>> propDomainElement2Candidates = cfactory.createObjectToObjectHashMap();
	private Map<MatchRelatedRuleElement, Set<Integer>> oppositeDomainElement2Candidates = cfactory.createObjectToObjectHashMap();

	private Map<ElementCandidate, Integer> propDomainCandidate2ID = cfactory.createObjectToIntHashMap();
	private Map<ElementCandidate, Integer> oppositeDomainCandidate2ID = cfactory.createObjectToIntHashMap();
	private Map<Integer, ElementCandidate> propDomainID2Candidate = cfactory.createIntToObjectHashMap();
	private Map<Integer, ElementCandidate> oppositeDomainID2Candidate = cfactory.createIntToObjectHashMap();

	private Set<Set<ITGGMatch>> overlappingMatches = cfactory.createObjectSet();
	private Map<ITGGMatch, Integer> match2ID = cfactory.createObjectToIntHashMap();
	private Map<Integer, ITGGMatch> id2match = cfactory.createIntToObjectHashMap();
	
	private static final double CONTEXT_TARGET_COEFF = 0.0000001;
	private Set<Integer> candidatesWithContextTarget = cfactory.createIntSet();

	private Map<MatchRelatedRuleElement, MatchRelatedRuleElement> mappingResult;

	public ILPHigherOrderRuleMappingSolver(SupportedILPSolver solver, //
			MatchRelatedRuleElementMap propDomainMapping, //
			MatchRelatedRuleElementMap oppositeDomainMapping) {
		this.solver = solver;
		this.propDomainMapping = propDomainMapping;
		this.oppositeDomainMapping = oppositeDomainMapping;

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
		createMatchCandidates();
	}

	private void createPropDomainCandidates(MatchRelatedRuleElement source, Set<MatchRelatedRuleElement> targets) {
		for (MatchRelatedRuleElement target : targets) {
			ElementCandidate candidate = new ElementCandidate(source, target);

			propDomainElement2Candidates.computeIfAbsent(source, k -> cfactory.createIntSet()).add(elementIDCounter);
			propDomainCandidate2ID.put(candidate, elementIDCounter);
			propDomainID2Candidate.put(elementIDCounter, candidate);
			elementIDCounter++;
		}
	}

	private void createOppositeDomainCandidates(MatchRelatedRuleElement source, Set<MatchRelatedRuleElement> targets) {
		for (MatchRelatedRuleElement target : targets) {
			ElementCandidate candidate = new ElementCandidate(source, target);

			if (target.ruleElement.getBindingType() == BindingType.CONTEXT)
				candidatesWithContextTarget.add(elementIDCounter);
			oppositeDomainElement2Candidates.computeIfAbsent(source, k -> cfactory.createIntSet()).add(elementIDCounter);
			oppositeDomainCandidate2ID.put(candidate, elementIDCounter);
			oppositeDomainID2Candidate.put(elementIDCounter, candidate);
			elementIDCounter++;
		}
	}

	private void createMatchCandidates() {
		// FIXME: how overlapping matches are defined here, is probably incorrect
		Set<Set<ITGGMatch>> matchSets = propDomainMapping.values().stream() //
				.map(set -> set.stream().map(e -> e.match).collect(Collectors.toSet())) //
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

		overlappingMatches.forEach(s -> s.forEach(match -> {
			match2ID.put(match, matchIDCounter);
			id2match.put(matchIDCounter, match);
			matchIDCounter++;
		}));
	}

	private BinaryILPProblem createILPProblem() {
		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		defineExclusionsForPropDomain(ilpProblem);
		defineExclusionsForOppositeDomain(ilpProblem);
		defineExclusionsForMatches(ilpProblem);

		defineImplicationsForPropDomain(ilpProblem);
		defineImplicationsForOppositeDomain(ilpProblem);

		defineObjective(ilpProblem);

		return ilpProblem;
	}

	private void defineExclusionsForPropDomain(BinaryILPProblem ilpProblem) {
		for (MatchRelatedRuleElement element : propDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = propDomainElement2Candidates.get(element);

			if (candidates.size() <= 1)
				continue;

			ilpProblem.addExclusion(candidates.stream().map(c -> "e" + c), //
					"EXCL_propDomElement_" + element.ruleElement.eClass().getName() + "_" + constraintNameCounter++, //
					1, 1);
		}
	}

	private void defineExclusionsForOppositeDomain(BinaryILPProblem ilpProblem) {
		for (MatchRelatedRuleElement element : oppositeDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = oppositeDomainElement2Candidates.get(element);

			if (candidates.size() <= 1)
				continue;

			ilpProblem.addExclusion(candidates.stream().map(c -> "e" + c), //
					"EXCL_oppositeDomElement_" + element.ruleElement.eClass().getName() + "_" + constraintNameCounter++);
		}
	}

	private void defineExclusionsForMatches(BinaryILPProblem ilpProblem) {
		for (Set<ITGGMatch> matchSet : overlappingMatches) {
			if (matchSet.size() <= 1)
				continue;

			ilpProblem.addExclusion(matchSet.stream().map(c -> "m" + c), //
					"EXCL_match_" + constraintNameCounter++);
		}
	}

	private void defineImplicationsForPropDomain(BinaryILPProblem ilpProblem) {
		for (MatchRelatedRuleElement element : propDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = propDomainElement2Candidates.get(element);

			for (Integer candidateID : candidates) {
				ITGGMatch match = propDomainID2Candidate.get(candidateID).target.match;
				ilpProblem.addImplication("e" + candidateID, Stream.of("m" + match2ID.get(match)), //
						"IMPL_propDomElement->match_" + element.ruleElement.eClass().getName() + "_" + constraintNameCounter++);
			}
		}
	}

	private void defineImplicationsForOppositeDomain(BinaryILPProblem ilpProblem) {
		for (MatchRelatedRuleElement element : oppositeDomainElement2Candidates.keySet()) {
			Set<Integer> candidates = oppositeDomainElement2Candidates.get(element);

			for (Integer candidateID : candidates) {
				ITGGMatch match = oppositeDomainID2Candidate.get(candidateID).target.match;
				ilpProblem.addImplication("e" + candidateID, Stream.of("m" + match2ID.get(match)), //
						"IMPL_oppositeDomElement->match_" + element.ruleElement.eClass().getName() + "_" + constraintNameCounter++);
			}
		}
	}

	private void defineObjective(BinaryILPProblem ilpProblem) {
		ILPLinearExpression expression = ilpProblem.createLinearExpression();

		for (int i = 0; i < elementIDCounter; i++)
			expression.addTerm("e" + i, candidatesWithContextTarget.contains(i) ? CONTEXT_TARGET_COEFF : 1);
		for (int i = 0; i < matchIDCounter; i++)
			expression.addTerm("m" + i, 1);

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
			if (useCandidate) {
				if (propDomainID2Candidate.containsKey(i))
					resultingCandidates.add(propDomainID2Candidate.get(i));
				if (oppositeDomainID2Candidate.containsKey(i))
					resultingCandidates.add(oppositeDomainID2Candidate.get(i));
			}
		}

		return resultingCandidates;
	}

	private void convertCandidatesToMappingResult(Set<ElementCandidate> candidates) {
		mappingResult = candidates.stream().collect(Collectors.toMap(c -> c.source, c -> c.target));
	}

	private class ElementCandidate {
		final MatchRelatedRuleElement source;
		final MatchRelatedRuleElement target;

		ElementCandidate(MatchRelatedRuleElement source, MatchRelatedRuleElement target) {
			this.source = source;
			this.target = target;
		}
	}

}
