package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.inv.DomainTypePatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class PACAnalysis extends FilterNACAnalysis {
	private TGG tgg;
	private IbexOptions options;

	public PACAnalysis(TGG tgg, IbexOptions options, Collection<DomainTypePatternTransformation> domainPatterns) {
		super(tgg, options);
		this.tgg = tgg;
		this.options = options;
		
		initializeCaching();
	}
	
	public PACAnalysis(TGG tgg, IbexOptions options) {
		super(tgg, options);
		this.tgg = tgg;
		this.options = options;
		
		initializeCaching();
	}
	
	public Collection<PACCandidate> computePACCandidates(TGGRule rule, DomainType domain) {
		final Collection<PACCandidate> pacCandidates = new ArrayList<>();
		for(TGGRuleNode n : rule.getNodes()) {
			if(!n.getBindingType().equals(BindingType.CREATE) || !n.getDomainType().equals(domain))
				continue;
			EClass nodeClass = n.getType();
			for (EReference eType : extractEReferences(nodeClass)) {
				for (EdgeDirection eDirection : EdgeDirection.values()) {
					if (typeDoesNotFitToDirection(n, eType, eDirection))
						continue;
					if (onlyPossibleEdgeIsAlreadyTranslatedInRule(rule, n, eType, eDirection))
						continue;
					if (edgeIsNeverTranslatedInTGG(domain, eType, eDirection, tgg))
						continue;
					
					List<TGGRule> savingRules = determineSavingRules(domain, eType, eDirection, tgg);
					PACCandidate pacCandidate = null;
					if(savingRules.isEmpty())
						pacCandidate = new PACCandidate(new FilterNACCandidate(n, eType, eDirection));
					else {
						if(nodeAndEdgeAreMarkedInOtherRule(n, eType, eDirection))
							for(TGGRule savingRule : savingRules) {
								for(TGGRuleEdge edge : savingRule.getEdges()) {
									if(edge.getType().equals(eType) && edge.getBindingType().equals(BindingType.CREATE) && edge.getSrcNode().getBindingType().equals(BindingType.CONTEXT) && edge.getTrgNode().getBindingType().equals(BindingType.CONTEXT)) {
										if(pacCandidate == null)
											pacCandidate = new PACCandidate(new FilterNACCandidate(n, eType, eDirection));
								 			pacCandidate.addConclusionRule(new ConclusionRule(savingRule, eDirection == EdgeDirection.INCOMING ? edge.getTrgNode() : edge.getSrcNode()));
									}
								}
							}
						}
					if(pacCandidate != null)
						pacCandidates.add(pacCandidate);
				}
			}
		}
		
		final Collection<PACCandidate> optimisedPACs = pacCandidates.stream().filter(pac -> !isRedundantDueToEMFContainmentSemantics(rule, pac.getPremise())).collect(Collectors.toList());
		
		optimisedPACs.removeAll(ignoreDueToEOppositeSemantics(pacCandidates));

		return optimisedPACs;
	}
	
	public Collection<ConclusionRule> onlyEdgeIsMarkedInOtherRules2(TGGRule toCheckRule, EClass nodeClass, EReference eTypeEdge, DomainType domain, EdgeDirection eDirection) {
		List<ConclusionRule> cRules = new LinkedList<ConclusionRule>();
		for(TGGRule rule: ref2rules.get(eTypeEdge)) {
			for(TGGRuleEdge edge : rule.getEdges()) {
				if(edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(eTypeEdge) && edge.getSrcNode().getBindingType().equals(BindingType.CONTEXT) && edge.getTrgNode().getBindingType().equals(BindingType.CONTEXT)) {
					TGGRuleNode n = eDirection == EdgeDirection.INCOMING ? edge.getTrgNode() : edge.getSrcNode();
					cRules.add(new ConclusionRule(rule, n));
				}
			}
		}
		return cRules;		
	}
	protected boolean nodeAndEdgeAreMarkedInOtherRule(TGGRuleNode n, EReference eTypeEdge,EdgeDirection eDirection) {
		for(TGGRule rule: ref2rules.get(eTypeEdge)) {
			for(TGGRuleEdge edge : rule.getEdges()) {
				if(edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(eTypeEdge))
					if((eDirection.equals(EdgeDirection.INCOMING) && edge.getTrgNode().getBindingType().equals(BindingType.CREATE)) || (eDirection.equals(EdgeDirection.OUTGOING) && edge.getSrcNode().getBindingType().equals(BindingType.CREATE)))
						return true;
			}
		}
		return false;
	}

	private Collection<PACCandidate> ignoreDueToEOppositeSemantics(Collection<PACCandidate> allPacs) {
		return allPacs.stream()//
				.filter(pac -> {
					return allPacs.stream().anyMatch(otherPAC -> isGreaterEOpposite(otherPAC.getPremise(), pac.getPremise()));
				})
				.collect(Collectors.toList());
	}
}
