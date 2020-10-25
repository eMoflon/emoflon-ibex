package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class MultiplicityCounter {

	protected final INTEGRATE integrate;

	/**
	 * Stores for every rule all created references those multiplicity can potentially be violated. The
	 * context source node of the reference is used as key to uniquely identify references.
	 */
	private Map<String, Map<String, List<EReference>>> ruleName2contextNodeName2references;

	/**
	 * Stores for every subject and its references the current number of outgoing edges. We only
	 * consider entries that can potentially be affected by multiplicity violations.
	 */
	private Map<EObject, Map<EReference, Integer>> subject2reference2numOfEdges;

	private Map<OutgoingEdge, Set<ITGGMatch>> outgoingEdge2edgeAddingMatches;
	private Map<OutgoingEdge, Set<ITGGMatch>> outgoingEdge2edgeRemovingMatches;

	public MultiplicityCounter(INTEGRATE integrate) {
		this.integrate = integrate;
		ruleName2contextNodeName2references = new HashMap<>();
		subject2reference2numOfEdges = new HashMap<>();
		outgoingEdge2edgeAddingMatches = new HashMap<>();
		outgoingEdge2edgeRemovingMatches = new HashMap<>();

		for (TGGRule rule : integrate.getOptions().tgg.getFlattenedConcreteTGGRules()) {
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
		Map<String, List<EReference>> contextNode2ref = ruleName2contextNodeName2references.get(match.getRuleName());
		if (contextNode2ref == null)
			return;

		for (Entry<String, List<EReference>> entry : contextNode2ref.entrySet()) {
			EObject subject = (EObject) match.get(entry.getKey());

			if (subject == null)
				throw new RuntimeException("Node must exist in match!");

			Map<EReference, Integer> reference2numOfEdges = subject2reference2numOfEdges.computeIfAbsent(subject, k -> new HashMap<>());
			for (EReference ref : entry.getValue()) {
				reference2numOfEdges.compute(ref, (k, v) -> v == null ? 1 : v + 1);
				outgoingEdge2edgeAddingMatches.computeIfAbsent(new OutgoingEdge(subject, ref), k -> new HashSet<>()).add(match);
			}
		}
	}

	public void notifyRemovedMatch(ITGGMatch match) {
		// check, if the match potentially violates the multiplicity of references
		Map<String, List<EReference>> contextNode2ref = ruleName2contextNodeName2references.get(match.getRuleName());
		if (contextNode2ref == null)
			return;

		for (Entry<String, List<EReference>> entry : contextNode2ref.entrySet()) {
			EObject subject = (EObject) match.get(entry.getKey());

			if (subject == null)
				throw new RuntimeException("MultiplicityCounter: node must exist in match!");

			if (integrate.getGeneralModelChanges().isDeleted(subject)) {
				if (subject2reference2numOfEdges.remove(subject) != null) {
					for (EReference ref : entry.getValue()) {
						OutgoingEdge outgoingEdge = new OutgoingEdge(subject, ref);
						outgoingEdge2edgeAddingMatches.remove(outgoingEdge);
						outgoingEdge2edgeRemovingMatches.remove(outgoingEdge);
					}
				}
			} else {
				boolean isBroken = match.getType() == PatternType.CONSISTENCY && integrate.getPrecedenceGraph().getNode(match).isBroken();

				Map<EReference, Integer> reference2numOfEdges = subject2reference2numOfEdges.get(subject);
				for (EReference ref : entry.getValue()) {
					if (reference2numOfEdges.get(ref) == 0)
						throw new IllegalStateException("Number of edges cannot be decreased, when it already is 0!");
					reference2numOfEdges.compute(ref, (k, v) -> v - 1);

					OutgoingEdge outgoingEdge = new OutgoingEdge(subject, ref);
					Set<ITGGMatch> matches = outgoingEdge2edgeAddingMatches.get(outgoingEdge);
					if (matches != null)
						matches.remove(match);

					if (isBroken)
						outgoingEdge2edgeRemovingMatches.computeIfAbsent(outgoingEdge, k -> new HashSet<>()).add(match);
				}
			}
		}
	}

	public void clearRemovedMatches() {
		outgoingEdge2edgeRemovingMatches.clear();
	}

	private boolean isViolableReference(EReference reference) {
		return reference.getLowerBound() != 0 || reference.getUpperBound() != -1;
	}

	private void storeReference(TGGRule rule, TGGRuleNode contextNode, EReference reference) {
		Map<String, List<EReference>> contextNodeName2references = ruleName2contextNodeName2references.computeIfAbsent( //
				rule.getName(), k -> new HashMap<>());
		List<EReference> references = contextNodeName2references.computeIfAbsent(contextNode.getName(), k -> new LinkedList<>());
		references.add(reference);
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

	public Map<EObject, Map<EReference, Integer>> getSubject2reference2numOfEdges() {
		return subject2reference2numOfEdges;
	}

	public Map<OutgoingEdge, Set<ITGGMatch>> getOutgoingEdge2edgeAddingMatches() {
		return outgoingEdge2edgeAddingMatches;
	}

	public Map<OutgoingEdge, Set<ITGGMatch>> getOutgoingEdge2edgeRemovingMatches() {
		return outgoingEdge2edgeRemovingMatches;
	}

}
