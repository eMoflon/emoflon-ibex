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
import org.eclipse.emf.ecore.EPackage;
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
	private DomainType domain;
	private TGG tgg;
	private IbexOptions options;
	private Map<EReference, Collection<TGGRule>> ref2rules = new HashMap<>();

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
	
	private void initializeCaching() {
		for(TGGRule rule : tgg.getRules()) {
			for(TGGRuleEdge edge : rule.getEdges()) {
				if(!edge.getBindingType().equals(BindingType.CREATE))
					continue;
				if(!ref2rules.containsKey(edge.getType())) {
					Collection<TGGRule> rules = new HashSet<>();
					ref2rules.put(edge.getType(), rules);
				}
				
				ref2rules.get(edge.getType()).add(rule);
			}
		}
	}
	
	public Collection<PACCandidate> computePACCandidates(TGGRule rule, DomainType domain) {
		final Collection<PACCandidate> pacCandidates = new ArrayList<>();
		
		for(TGGRuleNode n : rule.getNodes()) {
			if(!n.getBindingType().equals(BindingType.CREATE) || !n.getDomainType().equals(domain))
				continue;
			EClass nodeClass = n.getType();
			for (EReference eType : extractEReferences(nodeClass)) {
				for (EdgeDirection eDirection : EdgeDirection.values()) {
					if (typeDoesNotFitToDirection(nodeClass, eType, eDirection))
						continue;
					if (onlyPossibleEdgeIsAlreadyTranslatedInRule(rule, eType, eDirection))
						continue;
					if (edgeIsNeverTransaltedInTGG(eType))
						continue;
					//Check if node and edge are marked in other Rule if true then add premise
					if(nodeAndEdgeAreMarkedinOtherRule(rule, nodeClass, eType, domain, eDirection)) {
						PACCandidate pacCandidate = new PACCandidate(new FilterNACCandidate(n, eType, eDirection));
						for(ConclusionRule conRule : onlyEdgeIsMarkedInOtherRules(rule, nodeClass, eType, domain, eDirection)) {
							pacCandidate.addConclusionRule(conRule);
						}
						pacCandidates.add(pacCandidate);
					}
				}
			}
		}
		final Collection<PACCandidate> optimisedPACs = pacCandidates.stream().filter(pac -> !isRedundantDueToEMFContainmentSemantics(rule, pac)).collect(Collectors.toList());
		// Use optimiser to remove some of the filter NACs
		optimisedPACs.removeAll(ignoreDueToEOppositeSemantics(pacCandidates));
		
//		optimisedPACs.forEach(x ->  {
//				System.out.println("PAC from: " + rule.getName() + " -> Premise: " + x.getPremise() + " Conclusions: " + x.getConclusionRules());
//		});
		return optimisedPACs;
	}
	
	private List<ConclusionRule> onlyEdgeIsMarkedInOtherRules(TGGRule toCheckRule, EClass nodeClass, EReference eTypeEdge, DomainType domain, EdgeDirection eDirection) {
//		return ref2rules.get(eTypeEdge).stream().anyMatch(r -> !r.equals(toCheckRule) && countEdgeInRule(r, eTypeEdge, eDirection, true, domain, false) > 0);
		List<ConclusionRule> cRules = new LinkedList<ConclusionRule>();
		for(TGGRule rule: ref2rules.get(eTypeEdge)) {
			MaxIncidentEdgeCount maxIncEdgeCount = countEdgeInRule(rule, eTypeEdge, eDirection, true, domain, false);
			if(maxIncEdgeCount.getEdgeCount() > 0) {
				cRules.add(new ConclusionRule(rule, maxIncEdgeCount.getOneNode()));
			}
		}
		return cRules;
		//		return ref2rules.get(eTypeEdge).stream().filter(r -> countEdgeInRule(r, eTypeEdge, eDirection, true, domain, false) > 0).collect(Collectors.toList());
	}
	
	private boolean nodeAndEdgeAreMarkedinOtherRule(TGGRule toCheckRule, EClass nodeClass, EReference eTypeEdge, DomainType domain, EdgeDirection eDirection) {
//		return ref2rules.get(eTypeEdge).stream().anyMatch(r -> !r.equals(toCheckRule) && countEdgeInRule(r, eTypeEdge, eDirection, true, domain, true) > 0);
		return ref2rules.get(eTypeEdge).stream().anyMatch(r -> countEdgeInRule(r, eTypeEdge, eDirection, true, domain, true).getEdgeCount() > 0);
	}
	
	private boolean isRedundantDueToEMFContainmentSemantics(TGGRule rule, PACCandidate pac) {
//		if(!pac.getConclusionRules().isEmpty()) {
//			return false;
//		}
		for (TGGRuleEdge edge : rule.getEdges()) {
			// Edges must be of same type and be containment
			if (edge.getType().equals(pac.getPremise().getEdgeType()) && edge.getType().isContainment()) {
				// Edges contain the same node (impossible so filter NAC can be ignored)
				if (pac.getPremise().getEDirection().equals(EdgeDirection.INCOMING) && edge.getTrgNode().equals(pac.getPremise().getNodeInRule()))
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

	private boolean isGreaterEOpposite(FilterNACCandidate nac1, FilterNACCandidate nac2) {
		if (nac2.getEdgeType().equals(nac1.getEdgeType().getEOpposite())) {
			if (nac1.getNodeInRule().getName().equals(nac2.getNodeInRule().getName()))
				if (nac1.getEDirection() != nac2.getEDirection()) {
					return nac1.getEdgeType().getName().compareTo(nac2.getEdgeType().getName()) >= 0;
				}
		}

		return false;
	}
	
	//Get all EReferences of the metamodel that equals the EClass
	private Collection<EReference> extractEReferences(EClass nodeClass) {
		EPackage pkg = (EPackage) nodeClass.eContainer();

		if (pkg == null)
			throw new IllegalArgumentException("Unable to resolve the container of " + nodeClass + ".  Please check your metamodel registration code.");

		return pkg.getEClassifiers().stream().filter(c -> (c instanceof EClass)).flatMap(c -> ((EClass) c).getEReferences().stream()).filter(r -> r.getEType().equals(nodeClass) || r.eContainer().equals(nodeClass))
				.collect(Collectors.toList());
	}
	
	private boolean typeDoesNotFitToDirection(EClass nodeClass, EReference eType, EdgeDirection direction){
		if(getType(eType, direction).equals(nodeClass))
			return false;
		return true;
	}
	
	/**
	 * this method returns the type of the currently viewed object which is implicit
	 */
	private EClass getType(EReference eType, EdgeDirection eDirection) {
		//eType.getEType returns the incoming Type and eType.eContainer the outgoing Type
		return eDirection == EdgeDirection.INCOMING ? (EClass) eType.getEType() : (EClass) eType.eContainer();
	}
	
	private boolean edgeIsNeverTransaltedInTGG(EReference eType) {
		return !ref2rules.containsKey(eType);
	}
	
	private boolean onlyPossibleEdgeIsAlreadyTranslatedInRule(TGGRule rule, EReference eType, EdgeDirection direction) {
		return eType.getUpperBound() == 1 && countEdgeInRule(rule, eType, direction, false, domain, false).getEdgeCount() == 1;
	}
	
	private MaxIncidentEdgeCount countEdgeInRule(TGGRule rule, EReference edgeType, EdgeDirection eDirection, boolean checkOtherRules, DomainType mode, boolean checkNodeAndEdge) {
		if(eDirection == EdgeDirection.INCOMING)
			return countIncomingEdge(rule, edgeType, mode, checkOtherRules, checkNodeAndEdge);
		else return countOutgoingEdge(rule, edgeType, mode, checkOtherRules, checkNodeAndEdge);
	}
	
	private MaxIncidentEdgeCount countIncomingEdge(TGGRule rule, EReference edgeType, DomainType domain, boolean checkOtherRules, boolean checkNodeAndEdge) {
		MaxIncidentEdgeCount maxIncEdgeCount = new MaxIncidentEdgeCount(0, null, null);
		for(TGGRuleNode n : rule.getNodes()) {
			if(!n.getDomainType().equals(domain))
				continue;
			int tmp;
			if(!checkOtherRules) 
				tmp = countOutgoingEdge(n, edgeType, checkOtherRules, checkNodeAndEdge);
			else tmp = countIncomingEdge(n, edgeType, checkOtherRules, checkNodeAndEdge);	
			if(tmp > maxIncEdgeCount.getEdgeCount()) {
				maxIncEdgeCount.setOneNode(n);
				maxIncEdgeCount.setEdgeCount(tmp);
			}
		}
		return maxIncEdgeCount;
	}
	
	private MaxIncidentEdgeCount countOutgoingEdge(TGGRule rule, EReference edgeType, DomainType domain, boolean checkOtherRules, boolean checkNodeAndEdge) {
		MaxIncidentEdgeCount maxIncEdgeCount = new MaxIncidentEdgeCount(0, null, null);
		for(TGGRuleNode n : rule.getNodes()) {
			if(!n.getDomainType().equals(domain))
				continue;
			int tmp;
			if(!checkOtherRules) 
				tmp = countIncomingEdge(n, edgeType, checkOtherRules, checkNodeAndEdge);
			else tmp = countOutgoingEdge(n, edgeType, checkOtherRules, checkNodeAndEdge);	
			if(tmp > maxIncEdgeCount.getEdgeCount()) {
				maxIncEdgeCount.setOneNode(n);
				maxIncEdgeCount.setEdgeCount(tmp);
			}
		}
		return maxIncEdgeCount;
	}
	
	private int countIncomingEdge(TGGRuleNode entryPoint, EReference edgeType, boolean checkOtherRules, boolean checkNodeAndEdge) {
		int edgeCounter = 0;
		for(TGGRuleEdge edge : entryPoint.getIncomingEdges()) {
			//TODO: Test change edge.getTrgNode() to entryPoint should be the same and change to .equals(BindingType.CONTEXT)
			if(checkNodeAndEdge) {
//				if((!checkOtherRules || edge.getTrgNode().getBindingType() == BindingType.CONTEXT) && edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(edgeType))
				if((!checkOtherRules || edge.getSrcNode().getBindingType() == BindingType.CONTEXT) && (!checkOtherRules || edge.getTrgNode().getBindingType().equals(BindingType.CREATE)) && edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(edgeType))
					edgeCounter++;
			}
			else if((!checkOtherRules || edge.getSrcNode().getBindingType() == BindingType.CONTEXT) && (!checkOtherRules || edge.getTrgNode().getBindingType().equals(BindingType.CONTEXT)) && edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(edgeType)) {
				edgeCounter++;
			}
		}
		return edgeCounter;
	}
	
	private int countOutgoingEdge(TGGRuleNode entryPoint, EReference edgeType, boolean checkOtherRules, boolean checkNodeAndEdge) {
		int edgeCounter = 0;
		for(TGGRuleEdge edge : entryPoint.getOutgoingEdges()) {
			if(checkNodeAndEdge) {
//				if((!checkOtherRules || edge.getSrcNode().getBindingType() == BindingType.CONTEXT) && edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(edgeType)) 
				if((!checkOtherRules || edge.getTrgNode().getBindingType() == BindingType.CONTEXT) && (!checkOtherRules || edge.getSrcNode().getBindingType().equals(BindingType.CREATE)) && edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(edgeType)) 
					edgeCounter++;
			}
			else if((!checkOtherRules || edge.getTrgNode().getBindingType() == BindingType.CONTEXT) && (!checkOtherRules || edge.getSrcNode().getBindingType().equals(BindingType.CONTEXT)) && edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(edgeType)) {
				edgeCounter++;
			}
		}
		return edgeCounter;
	}
	
}
