package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

import language.BindingType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class DECHelper {
	
	private static final String DEC_NODE = "DECNode"; 

	protected static int countEdgeInRule(TGGRule rule, EReference edgeType, EdgeDirection eDirection) {
		return rule.getNodes().parallelStream().map(n -> countEdgeInRule(rule, n, edgeType, eDirection)).max((a, b) -> a > b ? a : b).get();
	}

	protected static int countEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection) {
		return eDirection == EdgeDirection.INCOMING ? countIncomingEdgeInRule(rule, edgeType) : countOutgoingEdgeInRule(rule, edgeType);
	}

	protected static int countIncomingEdgeInRule(TGGRule rule, EReference edgeType) {
		return rule.getNodes().parallelStream().map(n -> countIncomingEdgeInRule(rule, n, edgeType)).max((a, b) -> a > b ? a : b).get();
	}

	protected static int countOutgoingEdgeInRule(TGGRule rule, EReference edgeType) {
		return rule.getNodes().parallelStream().map(n -> countOutgoingEdgeInRule(rule, n, edgeType)).max((a, b) -> a > b ? a : b).get();
	}

	protected static int countIncomingEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType) {
		return (int) entryPoint.getIncomingEdges().stream().filter(e -> e.getBindingType().equals(BindingType.CREATE) && e.getType().equals(edgeType)).count();
	}

	protected static int countOutgoingEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType) {
		return (int) entryPoint.getOutgoingEdges().stream().filter(e -> e.getBindingType().equals(BindingType.CREATE) && e.getType().equals(edgeType)).count();
	}
	

	/**
	 * this method returns the type of the currently viewed object which is implicit
	 */
	protected static EClass getType(EReference eType, EdgeDirection eDirection) {
		return eDirection == EdgeDirection.INCOMING ? (EClass) eType.getEType() : (EClass) eType.eContainer();
	}

	/**
	 * this method returns the opposite type of the currently viewed object which is implicit
	 */
	protected static EClass getOppositeType(EReference eType, EdgeDirection eDirection) {
		return eDirection == EdgeDirection.OUTGOING ? (EClass) eType.getEType() : (EClass) eType.eContainer();
	}

	protected static TGGRule createCheckEdgeRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection) {
		TGGRule copy = EcoreUtil.copy(rule);
		
		// find entryPoint
		TGGRuleNode copyEntryPoint = copy.getNodes().stream().filter(n -> n.getName().equals(entryPoint.getName())).findFirst().get();
		
		// find nodes that are to be preserved
		Collection<TGGRuleNode> preservedNodes = new ArrayList<TGGRuleNode>();
		Collection<TGGRuleEdge> preservedEdges = new ArrayList<TGGRuleEdge>();
		preservedNodes.add(copyEntryPoint);
		
		switch(eDirection) {
		case INCOMING: 
			preservedEdges.addAll(copyEntryPoint.getIncomingEdges().stream().filter(edge -> edge.getType().equals(edgeType)).collect(Collectors.toSet()));
			preservedNodes.addAll(preservedEdges.stream().map(edge -> edge.getSrcNode()).collect(Collectors.toSet())); 
			break;
		case OUTGOING: 
			preservedEdges.addAll(copyEntryPoint.getOutgoingEdges().stream().filter(edge -> edge.getType().equals(edgeType)).collect(Collectors.toSet()));
			preservedNodes.addAll(preservedEdges.stream().map(edge -> edge.getTrgNode()).collect(Collectors.toSet())); 
			break;
		}
		
		// delete all unpreserved nodes and edges
		copy.getNodes().stream().filter(n-> !preservedNodes.contains(n)).forEach(n -> EcoreUtil.remove(n));
		copy.getEdges().stream().filter(e-> !preservedEdges.contains(e)).forEach(e -> EcoreUtil.remove(e));
		
		// create a new edge and node to indicate a dangling edge which we try to detect
		TGGRuleNode newNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		newNode.setName(entryPoint.getName() + "_" + edgeType.getName() + "_" + DEC_NODE);
		newNode.setDomainType(entryPoint.getDomainType());
		newNode.setBindingType(BindingType.CREATE);
		newNode.setType(getOppositeType(edgeType, eDirection));
		
		TGGRuleEdge newEdge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		newEdge.setName(edgeType.getName());
		newEdge.setDomainType(entryPoint.getDomainType());
		newEdge.setBindingType(BindingType.CREATE);
		
		switch(eDirection) {
		case INCOMING: 
			newEdge.setSrcNode(newNode);
			newEdge.setTrgNode(copyEntryPoint);
			break;
		case OUTGOING:
			newEdge.setSrcNode(copyEntryPoint);
			newEdge.setTrgNode(newNode);
			break;
		}
		
		return copy;
	}
	
	protected static boolean isDECNodE(TGGRuleNode node) {
		return node.getName().contains(DEC_NODE); 
	}
}
