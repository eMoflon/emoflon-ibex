package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DECHelper {

	public static final String DEC_NODE = "DECNode";

	/*
	 * These edge counting rules only work in the context of dec because we filter those rules where our entry point is not set to context
	 */
	protected static boolean isEdgeInTGG(TGG tgg, EReference eType, EdgeDirection eDirection, boolean findRescuePattern, DomainType mode) {
		return tgg.getRules().parallelStream().filter(r -> DECHelper.countEdgeInRule(r, eType, eDirection, findRescuePattern, mode).getLeft() > 0).count() != 0;
	}

	protected static Triple<Integer, TGGRuleNode, TGGRuleNode> countEdgeInRule(TGGRule rule, EReference edgeType, EdgeDirection eDirection, boolean findRescuePattern, DomainType mode) {
		return rule.getNodes().stream().map(n -> countEdgeInRule(rule, n, edgeType, eDirection, findRescuePattern, mode)).max((t1, t2) -> Integer.compare(t1.getLeft(),  t2.getLeft())).get();
	}

	// TODO can be merged into the upper method
	protected static Triple<Integer, TGGRuleNode, TGGRuleNode> countEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, boolean findRescuePattern, DomainType mode) {
		return eDirection == EdgeDirection.INCOMING ? countIncomingEdgeInRule(rule, edgeType, findRescuePattern, mode) : countOutgoingEdgeInRule(rule, edgeType, findRescuePattern, mode);
	}

	protected static Triple<Integer, TGGRuleNode, TGGRuleNode> countIncomingEdgeInRule(TGGRule rule, EReference edgeType, boolean findRescuePattern, DomainType mode) {
		Stream<TGGRuleNode>  stream = rule.getNodes().stream().filter(n -> n.getDomainType() == mode);
		if (!findRescuePattern)
			return stream.map(n -> countOutgoingEdgeInRule(rule, n, edgeType, findRescuePattern)).max((t1, t2) -> Integer.compare(t1.getLeft(),  t2.getLeft())).orElse(Triple.of(0, null, null));

		return stream.map(n -> countIncomingEdgeInRule(rule, n, edgeType, findRescuePattern)).max((t1, t2) -> Integer.compare(t1.getLeft(),  t2.getLeft())).orElse(Triple.of(0, null, null));
	}

	protected static Triple<Integer, TGGRuleNode, TGGRuleNode> countOutgoingEdgeInRule(TGGRule rule, EReference edgeType, boolean findRescuePattern, DomainType mode) {
		Stream<TGGRuleNode>  stream = rule.getNodes().stream().filter(n -> n.getDomainType() == mode);
		if (!findRescuePattern)
			return stream.map(n -> countIncomingEdgeInRule(rule, n, edgeType, findRescuePattern)).max((t1, t2) -> Integer.compare(t1.getLeft(),  t2.getLeft())).orElse(Triple.of(0, null, null));

		return stream.map(n -> countOutgoingEdgeInRule(rule, n, edgeType, findRescuePattern)).max((t1, t2) -> Integer.compare(t1.getLeft(),  t2.getLeft())).orElse(Triple.of(0, null, null));
	}

	protected static Triple<Integer, TGGRuleNode, TGGRuleNode> countIncomingEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, boolean findRescuePattern) {
		List<TGGRuleEdge> edges = entryPoint.getIncomingEdges().stream()
				.filter(e -> (!findRescuePattern || e.getTrgNode().getBindingType() == BindingType.CONTEXT) && e.getBindingType() == BindingType.CREATE && e.getType().equals(edgeType))
				.collect(Collectors.toList());
		return Triple.of(edges.size(), edges.size() == 0 ? null : edges.get(0).getTrgNode(), edges.size() == 0 ? null : edges.get(0).getSrcNode());
	}

	protected static Triple<Integer, TGGRuleNode, TGGRuleNode> countOutgoingEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, boolean findRescuePattern) {
		List<TGGRuleEdge> edges = entryPoint.getOutgoingEdges().stream()
				.filter(e -> (!findRescuePattern || e.getSrcNode().getBindingType() == BindingType.CONTEXT) && e.getBindingType().equals(BindingType.CREATE) && e.getType().equals(edgeType))
				.collect(Collectors.toList());
		return Triple.of(edges.size(), edges.size() == 0 ? null : edges.get(0).getSrcNode(), edges.size() == 0 ? null : edges.get(0).getTrgNode());
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

		switch (eDirection) {
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
		Collection<TGGRuleElement> ruleElements = new ArrayList<TGGRuleElement>();
		ruleElements.addAll(copy.getNodes().stream().filter(n -> !preservedNodes.contains(n)).collect(Collectors.toSet()));
		ruleElements.addAll(copy.getEdges().stream().filter(e -> !preservedEdges.contains(e)).collect(Collectors.toSet()));

		for (TGGRuleElement element : ruleElements)
			EcoreUtil.remove(element);

		// create a new edge and node to indicate a dangling edge which we try to detect
		TGGRuleNode newNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		newNode.setName(entryPoint.getName() + "_" + edgeType.getName() + "_" + DEC_NODE);
		newNode.setDomainType(entryPoint.getDomainType());
		newNode.setBindingType(BindingType.CREATE);
		newNode.setType(getOppositeType(edgeType, eDirection));
		copy.getNodes().add(newNode);

		TGGRuleEdge newEdge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		newEdge.setName(edgeType.getName());
		newEdge.setDomainType(entryPoint.getDomainType());
		newEdge.setBindingType(BindingType.CREATE);
		newEdge.setType(edgeType);
		copy.getEdges().add(newEdge);

		switch (eDirection) {
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

	public static boolean isDECNode(TGGRuleNode node) {
		return node.getName().contains(DEC_NODE);
	}

	protected static Collection<EReference> extractEReferences(EClass nodeClass) {
		EPackage pkg = (EPackage) nodeClass.eContainer();
		return pkg.getEClassifiers().stream().filter(c -> (c instanceof EClass)).flatMap(c -> ((EClass) c).getEReferences().stream())
				.filter(r -> r.getEType().equals(nodeClass) || r.eContainer().equals(nodeClass)).collect(Collectors.toList());
	}

	protected static TGGRuleNode getDECNode(TGGRule rule) {
		return rule.getNodes().stream().filter(n -> n.getName().endsWith(DEC_NODE)).findFirst().get();
	}

	/**
	 * Here we get all DECPatterns and search for external patterns that we have to import into the viatra pattern generated for the given rule
	 * 
	 * @param rule
	 * @param collect
	 * @return
	 */
	public static Collection<String> determineImports(TGGRule rule, List<IbexPattern> decPatterns) {
		return decPatterns.stream().flatMap(p -> p.getNegativeInvocations().stream()).filter(p -> !(p instanceof SearchEdgePattern)).map(p -> p.getRule().getName().toLowerCase() + "." + p.getName())
				.collect(Collectors.toSet());
	}
}
