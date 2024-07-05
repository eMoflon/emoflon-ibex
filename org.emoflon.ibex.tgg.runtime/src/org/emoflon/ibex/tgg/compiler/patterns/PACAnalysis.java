package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class PACAnalysis extends ACAnalysis {
	private TGG tgg;

	public PACAnalysis(TGG tgg, IbexOptions options) {
		super(tgg, options);
		this.tgg = tgg;

		initializeCaching();
	}

	public Collection<PACCandidate> computePACCandidates(TGGRule rule, DomainType domain) {
		final Collection<PACCandidate> pacCandidates = new ArrayList<>();
		// iterate over all green nodes in the specified domain:
		for (TGGRuleNode node : rule.getNodes()) {
			if (!node.getBindingType().equals(BindingType.CREATE) || !node.getDomainType().equals(domain))
				continue;

			EClass nodeClass = node.getType();
			for (EReference type : extractEReferences(nodeClass)) {
				for (EdgeDirection edgeDirection : EdgeDirection.values()) {
					if (typeDoesNotFitToDirection(node, type, edgeDirection))
						continue;
					if (onlyPossibleEdgeIsAlreadyTranslatedInRule(rule, node, type, edgeDirection))
						continue;
					if (edgeIsNeverTranslatedInTGG(domain, type, edgeDirection, tgg))
						continue;

					// find all rules which are able to translate an edge with the given type after applying
					// the rule given above
					List<TGGRule> savingRules = determineSavingRules(domain, type, edgeDirection, tgg);

					PACCandidate pacCandidate = null;
					// if no saving rules exist, we have to create a filter NAC
					if (savingRules.isEmpty()) {
						pacCandidate = new PACCandidate(new FilterNACCandidate(node, type, edgeDirection));
					// otherwise, check first if any other rules are able to create the given node plus edge, //
					// since having no such rules, a look ahead strategy would be redundant
					} else if (nodeAndEdgeAreMarkedInOtherRule(node, type, edgeDirection)) {
						for (TGGRule savingRule : savingRules) {
							for (TGGRuleEdge edge : savingRule.getEdges()) {
								// search the saving rules for rules, which only translate an edge of the given type, //
								// assuming its connected nodes are already translated
								if (edge.getType().equals(type) && edge.getBindingType().equals(BindingType.CREATE) //
										&& edge.getSrcNode().getBindingType().equals(BindingType.CONTEXT) //
										&& edge.getTrgNode().getBindingType().equals(BindingType.CONTEXT)) {
									if (pacCandidate == null)
										pacCandidate = new PACCandidate(new FilterNACCandidate(node, type, edgeDirection));
									// if an edge of the given type is found, at least one match for these rules also have to be found
									// to allow applying the rule given above
									pacCandidate.addConclusionRule(new ConclusionRule(
											savingRule,
											edgeDirection == EdgeDirection.INCOMING ? edge.getTrgNode() : edge.getSrcNode()));
								}
							}
						}
					}
					if (pacCandidate != null)
						pacCandidates.add(pacCandidate);
				}
			}
		}

		final Collection<PACCandidate> optimisedPACs = pacCandidates.stream() //
				.filter(pac -> !isRedundantDueToEMFContainmentSemantics(rule, pac.getPremise())) //
				.collect(Collectors.toList());

		optimisedPACs.removeAll(ignoreDueToEOppositeSemantics(pacCandidates));

		return optimisedPACs;
	}

	protected boolean nodeAndEdgeAreMarkedInOtherRule(TGGRuleNode n, EReference eTypeEdge, EdgeDirection eDirection) {
		for (TGGRule rule : ref2rules.get(eTypeEdge)) {
			for (TGGRuleEdge edge : rule.getEdges()) {
				if (edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(eTypeEdge)) {
					if ((eDirection.equals(EdgeDirection.INCOMING) && edge.getTrgNode().getBindingType().equals(BindingType.CREATE) //
							&& edge.getSrcNode().getBindingType().equals(BindingType.CONTEXT)) //
							|| (eDirection.equals(EdgeDirection.OUTGOING) && edge.getSrcNode().getBindingType().equals(BindingType.CREATE) //
									&& edge.getTrgNode().getBindingType().equals(BindingType.CONTEXT))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private Collection<PACCandidate> ignoreDueToEOppositeSemantics(Collection<PACCandidate> allPacs) {
		return allPacs.stream() //
				.filter(pac -> {
					return allPacs.stream().anyMatch(otherPAC -> isGreaterEOpposite(otherPAC.getPremise(), pac.getPremise()));
				}) //
				.collect(Collectors.toList());
	}
}
