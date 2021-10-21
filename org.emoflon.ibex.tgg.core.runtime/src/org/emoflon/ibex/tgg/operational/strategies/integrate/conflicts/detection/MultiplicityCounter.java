package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.matchhandling.MatchConsumer;
import org.emoflon.ibex.tgg.operational.strategies.modules.MatchDistributor;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class MultiplicityCounter extends MatchConsumer {

	protected final INTEGRATE integrate;

	/**
	 * Stores for every rule all created references those multiplicity can potentially be violated. The
	 * context source node of the reference is used as key to uniquely identify references.
	 */
	private Map<String, Map<String, Map<EReference, Integer>>> ruleName2contextNodeName2refs2numOfEdges;

	/**
	 * Stores for every subject and its references the current number of outgoing edges. We only
	 * consider entries that can potentially be affected by multiplicity violations.
	 */
	private Map<EObject, Map<EReference, Integer>> subject2reference2numOfEdges;

	private Map<OutgoingEdge, Map<ITGGMatch, Integer>> outgoingEdge2addedMatches2numOfEdges;
	private Map<OutgoingEdge, Map<ITGGMatch, Integer>> outgoingEdge2removedMatches2numOfEdges;

	public MultiplicityCounter(INTEGRATE integrate) {
		super(integrate.getOptions());
		this.integrate = integrate;
		ruleName2contextNodeName2refs2numOfEdges = new HashMap<>();
		subject2reference2numOfEdges = new HashMap<>();
		outgoingEdge2addedMatches2numOfEdges = new HashMap<>();
		outgoingEdge2removedMatches2numOfEdges = new HashMap<>();

		for (TGGRule rule : options.tgg.getFlattenedConcreteTGGRules()) {
			for (TGGRuleEdge greenEdge : TGGFilterUtil.filterEdges(rule.getEdges(), BindingType.CREATE)) {
				if (greenEdge.getDomainType() == DomainType.CORR)
					continue;

				if (greenEdge.getSrcNode().getBindingType() == BindingType.CONTEXT) {
					if (isViolableReference(greenEdge.getType()))
						storeReference(rule, greenEdge.getSrcNode(), greenEdge.getType());
				}

				// only needed, if in the future TGG rules may contain no opposite edges anymore
//				if (greenEdge.getTrgNode().getBindingType() == BindingType.CONTEXT && greenEdge.getType().getEOpposite() != null) {
//					if (isViolableReference(greenEdge.getType().getEOpposite()))
//						storeReference(rule, greenEdge.getTrgNode(), greenEdge.getType().getEOpposite());
//				}
			}
		}
	}

	@Override
	protected void registerAtMatchDistributor(MatchDistributor matchDistributor) {
		Set<PatternType> patternSet = Collections.singleton(PatternType.CONSISTENCY);
		matchDistributor.registerSingle(patternSet, this::notifyAddedMatch, this::notifyRemovedMatch);
		matchDistributor.registerMultiple(patternSet, m -> m.forEach(this::notifyAddedMatch), m -> m.forEach(this::notifyRemovedMatch));
	}

	public static class OutgoingEdge {
		public final EObject subject;
		public final EReference reference;

		public OutgoingEdge(EObject subject, EReference reference) {
			this.subject = subject;
			this.reference = reference;
		}

		@Override
		public int hashCode() {
			return Objects.hash(reference, subject);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof OutgoingEdge))
				return false;
			OutgoingEdge other = (OutgoingEdge) obj;
			return Objects.equals(reference, other.reference) && Objects.equals(subject, other.subject);
		}
	}

	public void notifyAddedMatch(ITGGMatch match) {
		// check, if the match potentially violates the multiplicity of references
		Map<String, Map<EReference, Integer>> contextNode2refs2numOfEdges = ruleName2contextNodeName2refs2numOfEdges.get(match.getRuleName());
		if (contextNode2refs2numOfEdges == null)
			return;

		for (Entry<String, Map<EReference, Integer>> entry : contextNode2refs2numOfEdges.entrySet()) {
			EObject subject = (EObject) match.get(entry.getKey());

			if (subject == null)
				throw new RuntimeException("Node must exist in match!");

			Map<EReference, Integer> reference2numOfEdges = subject2reference2numOfEdges.computeIfAbsent(subject, k -> new HashMap<>());
			entry.getValue().forEach((ref, numOfEdges) -> {
				reference2numOfEdges.compute(ref, (k, v) -> v == null ? 1 : v + numOfEdges);
				outgoingEdge2addedMatches2numOfEdges.computeIfAbsent(new OutgoingEdge(subject, ref), k -> new HashMap<>()).put(match, numOfEdges);
			});
		}
	}

	public void notifyRemovedMatch(ITGGMatch match) {
		// check, if the match potentially violates the multiplicity of references
		Map<String, Map<EReference, Integer>> contextNode2refs2numOfEdges = ruleName2contextNodeName2refs2numOfEdges.get(match.getRuleName());
		if (contextNode2refs2numOfEdges == null)
			return;

		for (Entry<String, Map<EReference, Integer>> entry : contextNode2refs2numOfEdges.entrySet()) {
			EObject subject = (EObject) match.get(entry.getKey());

			if (subject == null)
				throw new RuntimeException("MultiplicityCounter: node must exist in match!");

			if (integrate.getGeneralModelChanges().isDeleted(subject)) {
				if (subject2reference2numOfEdges.remove(subject) != null) {
					entry.getValue().forEach((ref, numOfEdges) -> {
						OutgoingEdge outgoingEdge = new OutgoingEdge(subject, ref);
						outgoingEdge2addedMatches2numOfEdges.remove(outgoingEdge);
						outgoingEdge2removedMatches2numOfEdges.remove(outgoingEdge);
					});
				}
			} else {
				boolean isBroken = match.getType() == PatternType.CONSISTENCY && integrate.precedenceGraph().getNode(match).isBroken();

				Map<EReference, Integer> reference2numOfEdges = subject2reference2numOfEdges.get(subject);
				entry.getValue().forEach((ref, numOfEdges) -> {
					if (reference2numOfEdges.get(ref) == 0)
						throw new IllegalStateException("Number of edges cannot be decreased, when it already is 0!");
					reference2numOfEdges.compute(ref, (k, v) -> v - numOfEdges);

					OutgoingEdge outgoingEdge = new OutgoingEdge(subject, ref);
					Map<ITGGMatch, Integer> matches = outgoingEdge2addedMatches2numOfEdges.get(outgoingEdge);
					if (matches != null)
						matches.remove(match);

					if (isBroken)
						outgoingEdge2removedMatches2numOfEdges.computeIfAbsent(outgoingEdge, k -> new HashMap<>()).put(match, numOfEdges);
				});
			}
		}
	}

	public void removeMatch(ITGGMatch match) {
		Set<EObject> greenSubjects = integrate.matchUtils().get(match).getObjects(new EltFilter().create().srcAndTrg());
		for (EObject subject : greenSubjects) {
			if (subject2reference2numOfEdges.remove(subject) != null) {
				subject.eClass().getEAllReferences().forEach(ref -> {
					OutgoingEdge outgoingEdge = new OutgoingEdge(subject, ref);
					outgoingEdge2addedMatches2numOfEdges.remove(outgoingEdge);
					outgoingEdge2removedMatches2numOfEdges.remove(outgoingEdge);
				});
			}
		}
	}

	public void clearRemovedMatches() {
		outgoingEdge2removedMatches2numOfEdges.clear();
	}

	private boolean isViolableReference(EReference reference) {
		return reference.getLowerBound() != 0 || reference.getUpperBound() != -1;
	}

	private void storeReference(TGGRule rule, TGGRuleNode contextNode, EReference reference) {
		Map<String, Map<EReference, Integer>> contextNodeName2refs2numOfEdges = ruleName2contextNodeName2refs2numOfEdges.computeIfAbsent( //
				rule.getName(), k -> new HashMap<>());
		Map<EReference, Integer> refs2numOfEdges = contextNodeName2refs2numOfEdges.computeIfAbsent(contextNode.getName(), k -> new HashMap<>());
		refs2numOfEdges.compute(reference, (k, v) -> v == null ? 1 : v + 1);
	}

	/**
	 * Calculates, if the given number of edges violates the multiplicity of the given reference.
	 * 
	 * @param reference  the reference
	 * @param numOfEdges number of edges
	 * @return the number of exceeded edges (if result is positive) or the number of missing edges (if
	 *         result is negative). If result is <code>0</code>, there is no multiplicity violation.
	 */
	public int violatesMultiplicity(EReference reference, int numOfEdges) {
		// lower bound violation
		if (numOfEdges < reference.getLowerBound())
			return numOfEdges - reference.getLowerBound();
		// upper bound violation
		if (reference.getUpperBound() != -1 && numOfEdges > reference.getUpperBound())
			return numOfEdges - reference.getUpperBound();
		return 0;
	}

	public Map<String, Map<String, Map<EReference, Integer>>> getRuleName2contextNodeName2refs2numOfEdges() {
		return ruleName2contextNodeName2refs2numOfEdges;
	}

	public Map<EObject, Map<EReference, Integer>> getSubject2reference2numOfEdges() {
		return subject2reference2numOfEdges;
	}

	public Map<OutgoingEdge, Map<ITGGMatch, Integer>> getOutgoingEdge2addedMatches2numOfEdges() {
		return outgoingEdge2addedMatches2numOfEdges;
	}

	public Map<OutgoingEdge, Map<ITGGMatch, Integer>> getOutgoingEdge2removedMatches2numOfEdges() {
		return outgoingEdge2removedMatches2numOfEdges;
	}

}
