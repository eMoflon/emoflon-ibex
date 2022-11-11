package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	Map<TGGRuleElement, Object> element2object;
	Map<Object, TGGRuleElement> object2element;

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

		element2object = new HashMap<>();
		element2object.putAll(node2eObject);
		element2object.putAll(edge2emfEdge);

		object2element = new HashMap<>();
		object2element.putAll(eObject2node);
		object2element.putAll(emfEdge2edge);

		Set<TGGRuleElement> elements = new HashSet<>();
		elements.addAll(node2eObject.keySet());
		elements.addAll(edge2emfEdge.keySet());
		groupedElements = elements.stream().collect(Collectors.groupingBy( //
				elt -> elt.getDomainType(), //
				Collectors.groupingBy(elt -> elt.getBindingType()) //
		));

		this.analyzer = new TGGMatchAnalyzer(this);
	}

	public TGGRule getRule() {
		return rule;
	}

	public TGGMatchAnalyzer analyzer() {
		return analyzer;
	}

	//// GETTER, CONVERTER ////

	public Set<TGGRuleNode> getNodes() {
		return node2eObject.keySet();
	}

	public Set<TGGRuleEdge> getEdges() {
		return edge2emfEdge.keySet();
	}

	public Set<EObject> getEObjects() {
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

	public TGGRuleNode getNode(EObject eObject) {
		return eObject2node.get(eObject);
	}

	public EObject getEObject(TGGRuleNode node) {
		return node2eObject.get(node);
	}

	public TGGRuleEdge getEdge(EMFEdge emfEdge) {
		return emfEdge2edge.get(emfEdge);
	}

	public EMFEdge getEMFEdge(TGGRuleEdge edge) {
		return edge2emfEdge.get(edge);
	}

	public TGGRuleElement getElement(Object object) {
		return object2element.get(object);
	}

	public Object getObject(TGGRuleElement element) {
		return element2object.get(element);
	}

	//// FILTER ////

	public Set<TGGRuleElement> getElts(EltFilter filter) {
		Set<TGGRuleElement> filtered = new HashSet<>();
		for (DomainType domain : filter.domainTypes) {
			for (BindingType binding : filter.bindingTypes) {
				Map<BindingType, List<TGGRuleElement>> map = groupedElements.get(domain);
				if (map == null)
					continue;

				List<TGGRuleElement> group = map.get(binding);
				if (group == null)
					continue;

				if (filter.all) {
					filtered.addAll(group);
				} else {
					Set<TGGRuleElement> delElts = analyzer.getAllDeletedElements();
					group.forEach(elt -> {
						if (filter.deleted == delElts.contains(elt))
							filtered.add(elt);
					});
				}
			}
		}
		return filtered;
	}

	public Set<Object> getObjects(EltFilter filter) {
		return getElts(filter).stream() //
				.map(elt -> element2object.get(elt)) //
				.collect(Collectors.toSet());
	}

	public Stream<TGGRuleNode> getNodeStream(EltFilter filter) {
		return getElts(filter).stream() //
				.filter(elt -> elt instanceof TGGRuleNode) //
				.map(elt -> (TGGRuleNode) elt);
	}

	public Set<TGGRuleNode> getNodes(EltFilter filter) {
		return getNodeStream(filter).collect(Collectors.toSet());
	}

	public Stream<EObject> getEObjectStream(EltFilter filter) {
		return getNodeStream(filter).map(n -> getEObject(n));
	}

	public Set<EObject> getEObjects(EltFilter filter) {
		return getEObjectStream(filter).collect(Collectors.toSet());
	}

	public Stream<TGGRuleEdge> getEdgeStream(EltFilter filter) {
		return getElts(filter).stream() //
				.filter(elt -> elt instanceof TGGRuleEdge) //
				.map(elt -> (TGGRuleEdge) elt);
	}

	public Set<TGGRuleEdge> getEdges(EltFilter filter) {
		return getEdgeStream(filter).collect(Collectors.toSet());
	}

	public Stream<EMFEdge> getEMFEdgeStream(EltFilter filter) {
		return getEdgeStream(filter).map(e -> getEMFEdge(e));
	}

	public Set<EMFEdge> getEMFEdges(EltFilter filter) {
		return getEMFEdgeStream(filter).collect(Collectors.toSet());
	}

}
