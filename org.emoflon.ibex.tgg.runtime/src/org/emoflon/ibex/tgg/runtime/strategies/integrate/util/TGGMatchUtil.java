package org.emoflon.ibex.tgg.runtime.strategies.integrate.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.util.TGGEdgeUtil;

public class TGGMatchUtil {

	final INTEGRATE integrate;
	final ITGGMatch match;
	final TGGRule rule;

	Map<TGGNode, EObject> node2eObject;
	Map<EObject, TGGNode> eObject2node;

	Map<TGGEdge, EMFEdge> edge2emfEdge;
	Map<EMFEdge, TGGEdge> emfEdge2edge;

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
				.filter(e -> node2eObject.containsKey(e.getSource()) && node2eObject.containsKey(e.getTarget())) //
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

	public Set<TGGNode> getNodes() {
		return node2eObject.keySet();
	}

	public Set<TGGEdge> getEdges() {
		return edge2emfEdge.keySet();
	}

	public Set<EObject> getEObjects() {
		return eObject2node.keySet();
	}

	public Set<EMFEdge> getEMFEdges() {
		return emfEdge2edge.keySet();
	}

	public Map<TGGNode, EObject> getNodeToEObject() {
		return node2eObject;
	}

	public Map<EObject, TGGNode> getEObjectToNode() {
		return eObject2node;
	}

	public Map<TGGEdge, EMFEdge> getEdgeToEMFEdge() {
		return edge2emfEdge;
	}

	public Map<EMFEdge, TGGEdge> getEmfEdgeToEdge() {
		return emfEdge2edge;
	}

	public TGGNode getNode(EObject eObject) {
		return eObject2node.get(eObject);
	}

	public EObject getEObject(TGGNode node) {
		return node2eObject.get(node);
	}

	public TGGEdge getEdge(EMFEdge emfEdge) {
		return emfEdge2edge.get(emfEdge);
	}

	public EMFEdge getEMFEdge(TGGEdge edge) {
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

	public Stream<TGGNode> getNodeStream(EltFilter filter) {
		return getElts(filter).stream() //
				.filter(elt -> elt instanceof TGGNode) //
				.map(elt -> (TGGNode) elt);
	}

	public Set<TGGNode> getNodes(EltFilter filter) {
		return getNodeStream(filter).collect(Collectors.toSet());
	}

	public Stream<EObject> getEObjectStream(EltFilter filter) {
		return getNodeStream(filter).map(n -> getEObject(n));
	}

	public Set<EObject> getEObjects(EltFilter filter) {
		return getEObjectStream(filter).collect(Collectors.toSet());
	}

	public Stream<TGGEdge> getEdgeStream(EltFilter filter) {
		return getElts(filter).stream() //
				.filter(elt -> elt instanceof TGGEdge) //
				.map(elt -> (TGGEdge) elt);
	}

	public Set<TGGEdge> getEdges(EltFilter filter) {
		return getEdgeStream(filter).collect(Collectors.toSet());
	}

	public Stream<EMFEdge> getEMFEdgeStream(EltFilter filter) {
		return getEdgeStream(filter).map(e -> getEMFEdge(e));
	}

	public Set<EMFEdge> getEMFEdges(EltFilter filter) {
		return getEMFEdgeStream(filter).collect(Collectors.toSet());
	}

}
