package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FilterNACAnalysis {
	private DomainType domain;
	private TGG tgg;
	private IbexOptions options;

	public FilterNACAnalysis(TGG tgg, IbexOptions options) {
		this.tgg = tgg;
		this.options = options;
	}

	public Collection<FilterNACCandidate> computeFilterNACCandidates(TGGRule rule, DomainType domain) {
		final Collection<FilterNACCandidate> filterNACs = new ArrayList<>();

		if (options.patterns.lookAheadStrategy().equals(FilterNACStrategy.NONE))
			return filterNACs;

		for (TGGRuleNode n : rule.getNodes()) {
			EClass nodeClass = n.getType();

			if (nodeIsNotTranslatedByThisRule(n))
				continue;
			if (nodeIsNotRelevant(domain, n))
				continue;

			// Create DECPatterns as negative children in the network
			for (EReference eType : extractEReferences(nodeClass)) {
				for (EdgeDirection eDirection : EdgeDirection.values()) {
					TGG tgg = (TGG) rule.eContainer();

					if (typeDoesNotFitToDirection(n, eType, eDirection))
						continue;
					if (onlyPossibleEdgeIsAlreadyTranslatedInRule(rule, n, eType, eDirection))
						continue;
					if (edgeIsNeverTranslatedInTGG(domain, eType, eDirection, tgg))
						continue;

					// Collect all Filter NACs, but do not add them yet as negative invocations
					if (thereIsNoSavingRule(domain, eType, eDirection, tgg))
						filterNACs.add(new FilterNACCandidate(n, eType, eDirection));
				}
			}
		}

		// Use optimiser to remove some of the filter NACs
		final Collection<FilterNACCandidate> optimisedFilterNACs = filterNACs.stream().filter(nac -> !isRedundantDueToEMFContainmentSemantics(rule, nac)).collect(Collectors.toList());

		optimisedFilterNACs.removeAll(ignoreDueToEOppositeSemantics(optimisedFilterNACs));

		// TODO: Further optimisation: Avoid redundant filter NACs for eOpp edges that
		// are already translated by the rule. Example:
		// ClassInhHier2DB::SubClassToTable_FWD currently has a redundant NAC.

		return optimisedFilterNACs;
	}

	private boolean isRedundantDueToEMFContainmentSemantics(TGGRule rule, FilterNACCandidate filterNAC) {
		for (TGGRuleEdge edge : rule.getEdges()) {
			// Edges must be of same type and be containment
			if (edge.getType().equals(filterNAC.getEdgeType()) && edge.getType().isContainment()) {
				// Edges contain the same node (impossible so filter NAC can be ignored)
				if (filterNAC.getEDirection().equals(EdgeDirection.INCOMING) && edge.getTrgNode().equals(filterNAC.getNodeInRule()))
					return true;
			}
		}

		return false;
	}

	private Collection<FilterNACCandidate> ignoreDueToEOppositeSemantics(Collection<FilterNACCandidate> allFilterNACs) {
		return allFilterNACs.stream()//
				.filter(nac -> {
					return allFilterNACs.stream().anyMatch(otherNAC -> isGreaterEOpposite(otherNAC, nac));
				})//
				.collect(Collectors.toList());
	}

	private boolean isGreaterEOpposite(FilterNACCandidate nac1, FilterNACCandidate nac2) {
		if (nac2.getEdgeType().equals(nac1.getEdgeType().getEOpposite())) {
			if (nac1.getNodeInRule().getName().equals(nac2.getNodeInRule().getName()))
				if (nac1.getEDirection() != nac2.getEDirection()) {
					return nac1.getEdgeType().getName().compareTo(nac2.getEdgeType().getName()) >= 0;
				}
		}

		return false;
	}

	private boolean thereIsNoSavingRule(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
		return determineSavingRules(domain, eType, eDirection, tgg).isEmpty();
	}

	private boolean edgeIsNeverTranslatedInTGG(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
		return !isEdgeInTGG(tgg, eType, eDirection, false, domain);
	}

	private boolean onlyPossibleEdgeIsAlreadyTranslatedInRule(TGGRule rule, TGGRuleNode n, EReference eType, EdgeDirection eDirection) {
		int numOfEdges = countEdgeInRule(rule, n, eType, eDirection, false, domain).getEdgeCount();
		return eType.getUpperBound() == 1 && numOfEdges == 1;
	}

	private boolean typeDoesNotFitToDirection(TGGRuleNode n, EReference eType, EdgeDirection eDirection) {
		return !getType(eType, eDirection).equals(n.getType());
	}

	private boolean nodeIsNotTranslatedByThisRule(TGGRuleNode n) {
		return !n.getBindingType().equals(BindingType.CREATE);
	}

	private boolean nodeIsNotRelevant(DomainType domain, TGGRuleNode n) {
		return !n.getDomainType().equals(domain) || n.getDomainType().equals(DomainType.CORR);
	}

	private List<TGGRule> determineSavingRules(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
		return tgg.getRules().stream().filter(r -> isSavingRule(domain, eType, eDirection, r)).collect(Collectors.toList());
	}

	private boolean isSavingRule(DomainType domain, EReference eType, EdgeDirection eDirection, TGGRule r) {
		return countEdgeInRule(r, eType, eDirection, true, domain).getEdgeCount() > 0;
	}

	/*
	 * These edge counting rules only work in the context of dec because we filter
	 * those rules where our entry point is not set to context
	 */
	private boolean isEdgeInTGG(TGG tgg, EReference eType, EdgeDirection eDirection, boolean findRescuePattern, DomainType mode) {
		return tgg.getRules().stream().filter(r -> countEdgeInRule(r, eType, eDirection, findRescuePattern, mode).getEdgeCount() > 0).count() != 0;
	}

	private MaxIncidentEdgeCount countEdgeInRule(TGGRule rule, EReference edgeType, EdgeDirection eDirection, boolean findRescuePattern, DomainType mode) {
		return rule.getNodes().stream().map(n -> countEdgeInRule(rule, n, edgeType, eDirection, findRescuePattern, mode)).max((t1, t2) -> Integer.compare(t1.getEdgeCount(), t2.getEdgeCount()))
				.orElseGet(() -> new MaxIncidentEdgeCount(0, null, null));
	}

	private MaxIncidentEdgeCount countEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, boolean findRescuePattern, DomainType mode) {
		return eDirection == EdgeDirection.INCOMING ? countIncomingEdgeInRule(rule, edgeType, findRescuePattern, mode) : countOutgoingEdgeInRule(rule, edgeType, findRescuePattern, mode);
	}

	private MaxIncidentEdgeCount countIncomingEdgeInRule(TGGRule rule, EReference edgeType, boolean findRescuePattern, DomainType mode) {
		Stream<TGGRuleNode> stream = rule.getNodes().stream().filter(n -> n.getDomainType() == mode);
		if (!findRescuePattern)
			return stream.map(n -> countOutgoingEdgeInRule(rule, n, edgeType, findRescuePattern)).max((t1, t2) -> Integer.compare(t1.getEdgeCount(), t2.getEdgeCount())).orElse(new MaxIncidentEdgeCount(0, null, null));

		return stream.map(n -> countIncomingEdgeInRule(rule, n, edgeType, findRescuePattern)).max((t1, t2) -> Integer.compare(t1.getEdgeCount(), t2.getEdgeCount())).orElse(new MaxIncidentEdgeCount(0, null, null));
	}

	private MaxIncidentEdgeCount countOutgoingEdgeInRule(TGGRule rule, EReference edgeType, boolean findRescuePattern, DomainType mode) {
		Stream<TGGRuleNode> stream = rule.getNodes().stream().filter(n -> n.getDomainType() == mode);
		if (!findRescuePattern)
			return stream.map(n -> countIncomingEdgeInRule(rule, n, edgeType, findRescuePattern)).max((t1, t2) -> Integer.compare(t1.getEdgeCount(), t2.getEdgeCount())).orElse(new MaxIncidentEdgeCount(0, null, null));

		return stream.map(n -> countOutgoingEdgeInRule(rule, n, edgeType, findRescuePattern)).max((t1, t2) -> Integer.compare(t1.getEdgeCount(), t2.getEdgeCount())).orElse(new MaxIncidentEdgeCount(0, null, null));
	}

	private MaxIncidentEdgeCount countIncomingEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, boolean findRescuePattern) {
		List<TGGRuleEdge> edges = entryPoint.getIncomingEdges().stream()
				.filter(e -> (!findRescuePattern || e.getTrgNode().getBindingType() == BindingType.CONTEXT) && e.getBindingType() == BindingType.CREATE && e.getType().equals(edgeType))
				.collect(Collectors.toList());
		return new MaxIncidentEdgeCount(edges.size(), edges.size() == 0 ? null : edges.get(0).getTrgNode(), edges.size() == 0 ? null : edges.get(0).getSrcNode());
	}

	private MaxIncidentEdgeCount countOutgoingEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, boolean findRescuePattern) {
		List<TGGRuleEdge> edges = entryPoint.getOutgoingEdges().stream()
				.filter(e -> (!findRescuePattern || e.getSrcNode().getBindingType() == BindingType.CONTEXT) && e.getBindingType().equals(BindingType.CREATE) && e.getType().equals(edgeType))
				.collect(Collectors.toList());
		return new MaxIncidentEdgeCount(edges.size(), edges.size() == 0 ? null : edges.get(0).getSrcNode(), edges.size() == 0 ? null : edges.get(0).getTrgNode());
	}

	/**
	 * this method returns the type of the currently viewed object which is implicit
	 */
	private EClass getType(EReference eType, EdgeDirection eDirection) {
		return eDirection == EdgeDirection.INCOMING ? (EClass) eType.getEType() : (EClass) eType.eContainer();
	}

	private Collection<EReference> extractEReferences(EClass nodeClass) {
		EPackage pkg = (EPackage) nodeClass.eContainer();

		if (pkg == null)
			throw new IllegalArgumentException("Unable to resolve the container of " + nodeClass + ".  Please check your metamodel registration code.");

		return pkg.getEClassifiers().stream().filter(c -> (c instanceof EClass)).flatMap(c -> ((EClass) c).getEReferences().stream()).filter(r -> r.getEType().equals(nodeClass) || r.eContainer().equals(nodeClass))
				.collect(Collectors.toList());
	}
}