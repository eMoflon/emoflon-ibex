package org.emoflon.ibex.tgg.operational.strategies.integrate.util2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.util.TGGEdgeUtil;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class TGGMatchUtil {

	final INTEGRATE integrate;
	final ITGGMatch match;
	final TGGRule rule;

	Map<TGGRuleNode, EObject> node2eObject;
	Map<EObject, TGGRuleNode> eObject2node;

	Map<TGGRuleEdge, EMFEdge> edge2emfEdge;
	Map<EMFEdge, TGGRuleEdge> emfEdge2edge;

	Map<DomainType, Map<BindingType, List<TGGRuleElement>>> groupedElements;

	private TGGMatchAnalyzer analyzer;

	public TGGMatchUtil(INTEGRATE integrate, ITGGMatch match, TGGRule rule) {
		this.integrate = integrate;
		this.match = match;
		this.rule = rule;
		init();
	}

	private void init() {
		this.node2eObject = rule.getNodes().stream() //
				.filter(n -> match.get(n.getName()) != null) //
				.collect(Collectors.toMap(n -> n, n -> (EObject) match.get(n.getName())));
		this.eObject2node = new HashMap<>();
		node2eObject.forEach((n, o) -> eObject2node.put(o, n));

		this.edge2emfEdge = rule.getEdges().stream() //
				.filter(e -> node2eObject.containsKey(e.getSrcNode()) && node2eObject.containsKey(e.getTrgNode())) //
				.collect(Collectors.toMap(e -> e, e -> TGGEdgeUtil.getRuntimeEdge(match, e)));
		this.emfEdge2edge = new HashMap<>();
		edge2emfEdge.forEach((e, f) -> emfEdge2edge.put(f, e));

		Set<TGGRuleElement> elements = new HashSet<>();
		elements.addAll(node2eObject.keySet());
		elements.addAll(edge2emfEdge.keySet());
		groupedElements = elements.stream().collect(Collectors.groupingBy( //
				elt -> elt.getDomainType(), //
				Collectors.groupingBy(elt -> elt.getBindingType()) //
		));

		this.analyzer = new TGGMatchAnalyzer(this);
	}

	public TGGMatchAnalyzer analyzer() {
		return analyzer;
	}

	public Set<TGGRuleNode> getNodes() {
		return node2eObject.keySet();
	}

	public Set<TGGRuleEdge> getEdges() {
		return edge2emfEdge.keySet();
	}

	public Set<EObject> getObjects() {
		return eObject2node.keySet();
	}

	public Set<EMFEdge> getEMFEdges() {
		return emfEdge2edge.keySet();
	}

	public Map<TGGRuleNode, EObject> getNodeToEObject() {
		return node2eObject;
	}

	public Map<EObject, TGGRuleNode> getEObjectToNode() {
		return eObject2node;
	}

	public Map<TGGRuleEdge, EMFEdge> getEdgeToEMFEdge() {
		return edge2emfEdge;
	}

	public Map<EMFEdge, TGGRuleEdge> getEmfEdgeToEdge() {
		return emfEdge2edge;
	}

	public TGGRuleNode getNode(EObject object) {
		return eObject2node.get(object);
	}

	public EObject getObject(TGGRuleNode node) {
		return node2eObject.get(node);
	}

	public TGGRuleEdge getEdge(EMFEdge emfEdge) {
		return emfEdge2edge.get(emfEdge);
	}

	public EMFEdge getEMFEdge(TGGRuleEdge edge) {
		return edge2emfEdge.get(edge);
	}

}
