package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

public class PACAnalysis extends FilterNACAnalysis {
	private DomainType domain;
	private TGG tgg;
	private IbexOptions options;
	
	private Map<EReference, Collection<TGGRule>> ref2rules = new HashMap<>();

	public PACAnalysis(TGG tgg, IbexOptions options) {
		super(tgg, options);
		this.tgg = tgg;
		this.options = options;
		
		initializeCaching();
	}
	
	private void initializeCaching() {
		for(TGGRule rule : tgg.getRules()) {
			for(TGGRuleEdge edge : rule.getEdges()) {
				if(!ref2rules.containsKey(edge.getType())) {
					Collection<TGGRule> rules = new HashSet<>();
					ref2rules.put(edge.getType(), rules);
				}
				
				ref2rules.get(edge.getType()).add(rule);
			}
		}
	}
	
//	public Collection<FilterNACCandidate> computePACCandidates(TGGRule rule, DomainType domain) {
	@Override
	public Collection<FilterNACCandidate> computeFilterNACCandidates(TGGRule rule, DomainType domain) {
		final Collection<FilterNACCandidate> pacCandidates = new ArrayList<>();
		if (options.patterns.lookAheadStrategy().equals(FilterNACStrategy.NONE))
			return pacCandidates;
		
		for (TGGRuleNode n : rule.getNodes()) {
			//Node needs to have the same domain 
			if(!n.getDomainType().equals(domain))
				continue;
			//Node needs to be translated ("create") by the rule
			if(!n.getBindingType().equals(BindingType.CREATE))
				continue;
			EClass nodeClass = n.getType();
			// Get all edges from the node from the metamodel
			for (EReference eType : extractEReferences(nodeClass)) {
				for (EdgeDirection eDirection : EdgeDirection.values()) {
					TGG tgg = (TGG) rule.eContainer();

					if (typeDoesNotFitToDirection(n, eType, eDirection))
						continue;
					if (onlyPossibleEdgeIsAlreadyTranslatedInRule(rule, n, eType, eDirection))
						continue;
					if (edgeIsNotInTGG(eType))
						continue;

					// Collect all Filter NACs, but do not add them yet as negative invocations
					if (determineSavingRules(domain, eType, eDirection, tgg).isEmpty()) {
//						System.out.println("Candidate found: " + rule.getName());
						pacCandidates.add(new FilterNACCandidate(n, eType, eDirection));
					}
				}
			}
		}
		// Use optimiser to remove some of the filter NACs
		final Collection<FilterNACCandidate> optimisedFilterNACs = pacCandidates.stream().filter(nac -> !isRedundantDueToEMFContainmentSemantics(rule, nac)).collect(Collectors.toList());

		optimisedFilterNACs.removeAll(ignoreDueToEOppositeSemantics(optimisedFilterNACs));

		// TODO: Further optimisation: Avoid redundant filter NACs for eOpp edges that
		// are already translated by the rule. Example:
		// ClassInhHier2DB::SubClassToTable_FWD currently has a redundant NAC.
//		System.out.println(optimisedFilterNACs);
		System.out.println("NAC from: " + rule.getName() + " -> " + optimisedFilterNACs);
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
	
	private boolean typeDoesNotFitToDirection(TGGRuleNode node, EReference eType, EdgeDirection direction){
		if(getType(eType, direction).equals(node.getType()))
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
	
	private boolean edgeIsNotInTGG(EReference eType) {
		return !ref2rules.containsKey(eType);
	}
	
	private boolean onlyPossibleEdgeIsAlreadyTranslatedInRule(TGGRule rule, TGGRuleNode node, EReference eType, EdgeDirection direction) {
		int numOfEdges = countEdgeInRule(rule, eType, direction, false, domain).getEdgeCount();
		return eType.getUpperBound() == 1 && numOfEdges == 1;
	}
	
	private MaxIncidentEdgeCount countEdgeInRule(TGGRule rule, EReference edgeType, EdgeDirection eDirection, boolean findRescuePattern, DomainType mode) {
//		for(TGGRuleNode n: rule.getNodes()) {
			if(eDirection == EdgeDirection.INCOMING)
				return countIncomingEdge(rule, edgeType, mode, findRescuePattern);
			else return countOutgoingEdge(rule, edgeType, mode, findRescuePattern);
//		}
//		return new MaxIncidentEdgeCount(0, null, null);
	}
	
	private MaxIncidentEdgeCount countIncomingEdge(TGGRule rule, EReference edgeType, DomainType domain, boolean findResucePattern) {
		MaxIncidentEdgeCount maxEdgeCount = new MaxIncidentEdgeCount(0, null, null);
		for(TGGRuleNode n : rule.getNodes()) {
			if(!n.getDomainType().equals(domain))
				continue;
			MaxIncidentEdgeCount tmp;
			if(!findResucePattern) 
				tmp = countOutgoingEdge(n, edgeType, findResucePattern);
			else tmp = countIncomingEdge(n, edgeType, findResucePattern);	
			if(tmp.getEdgeCount() > maxEdgeCount.getEdgeCount())
				maxEdgeCount = tmp;
		}
		return maxEdgeCount;
	}
	
	private MaxIncidentEdgeCount countOutgoingEdge(TGGRule rule, EReference edgeType, DomainType domain, boolean findResucePattern) {
		MaxIncidentEdgeCount maxEdgeCount = new MaxIncidentEdgeCount(0, null, null);
		for(TGGRuleNode n : rule.getNodes()) {
			if(!n.getDomainType().equals(domain))
				continue;
			MaxIncidentEdgeCount tmp;
			if(!findResucePattern) 
				tmp = countIncomingEdge(n, edgeType, findResucePattern);
			else tmp = countOutgoingEdge(n, edgeType, findResucePattern);	
			if(tmp.getEdgeCount() > maxEdgeCount.getEdgeCount())
				maxEdgeCount = tmp;
		}
		return maxEdgeCount;
	}
	
	private MaxIncidentEdgeCount countIncomingEdge(TGGRuleNode entryPoint, EReference edgeType, boolean findRescuePattern) {
		int edgeCounter = 0;
		TGGRuleNode otherNode = null;
		for(TGGRuleEdge edge : entryPoint.getIncomingEdges()) {
			//TODO: Test change edge.getTrgNode() to entryPoint should be the same and change to .equals(BindingType.CONTEXT)
			if((!findRescuePattern || edge.getTrgNode().getBindingType() == BindingType.CONTEXT) && edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(edgeType)) {
				if(edgeCounter == 0)
					otherNode = edge.getSrcNode();
				edgeCounter++;
			}
		}
		if(edgeCounter == 0)
			return new MaxIncidentEdgeCount(edgeCounter, null, null);
		else return new MaxIncidentEdgeCount(edgeCounter, entryPoint, otherNode);
	}
	private MaxIncidentEdgeCount countOutgoingEdge(TGGRuleNode entryPoint, EReference edgeType, boolean findRescuePattern) {
		int edgeCounter = 0;
		TGGRuleNode otherNode = null;
		for(TGGRuleEdge edge : entryPoint.getOutgoingEdges()) {
			if((!findRescuePattern || edge.getSrcNode().getBindingType() == BindingType.CONTEXT) && edge.getBindingType().equals(BindingType.CREATE) && edge.getType().equals(edgeType)) {
				if(edgeCounter == 0) 
					otherNode = edge.getTrgNode();
				edgeCounter++;
			}
		}
		if(edgeCounter == 0)
			return new MaxIncidentEdgeCount(edgeCounter, null, null);
		else return new MaxIncidentEdgeCount(edgeCounter, entryPoint, otherNode);
	}
	
	private List<TGGRule> determineSavingRules(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
//		return tgg.getRules().stream().filter(r -> isSavingRule(domain, eType, eDirection, r)).collect(Collectors.toList());
		return ref2rules.get(eType).stream().filter(r -> isSavingRule(domain, eType, eDirection, r)).collect(Collectors.toList());
	}
	
	private boolean isSavingRule(DomainType domain, EReference eType, EdgeDirection eDirection, TGGRule r) {
		return countEdgeInRule(r, eType, eDirection, true, domain).getEdgeCount() > 0;
	}
}
