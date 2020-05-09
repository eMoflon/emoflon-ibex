package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchModification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil.EltFilter;
import org.emoflon.ibex.tgg.util.TGGEdgeUtil;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class MatchAnalysis {

	private final INTEGRATE opStrat;
	private final ITGGMatch match;
	private final TGGRule rule;

	Map<TGGRuleNode, EObject> nodeToEObject;
	Map<EObject, TGGRuleNode> eObjectToNode;

	Map<TGGRuleEdge, EMFEdge> edgeToEMFEdge;
	Map<EMFEdge, TGGRuleEdge> emfEdgeToEdge;

	Map<DomainType, Map<BindingType, List<TGGRuleElement>>> groupedElements;

	private Set<TGGRuleElement> deletedElements;

	MatchAnalysis(INTEGRATE opStrat, ITGGMatch match, TGGRule rule) {
		this.opStrat = opStrat;
		this.match = match;
		this.rule = rule;
		init();
	}

	private void init() {
		this.nodeToEObject = rule.getNodes().stream() //
				.collect(Collectors.toMap(n -> n, n -> (EObject) match.get(n.getName())));
		this.eObjectToNode = new HashMap<>();
		nodeToEObject.forEach((n, o) -> eObjectToNode.put(o, n));

		this.edgeToEMFEdge = rule.getEdges().stream() //
				.collect(Collectors.toMap(e -> e, e -> TGGEdgeUtil.getRuntimeEdge(match, e)));
		this.emfEdgeToEdge = new HashMap<>();
		edgeToEMFEdge.forEach((e, f) -> emfEdgeToEdge.put(f, e));

		Set<TGGRuleElement> elements = new HashSet<>();
		elements.addAll(rule.getNodes());
		elements.addAll(rule.getEdges());
		groupedElements = elements.stream().collect(Collectors.groupingBy( //
				elt -> elt.getDomainType(), //
				Collectors.groupingBy(elt -> elt.getBindingType()) //
		));

		this.deletedElements = new HashSet<>();
	}

	MatchAnalysis update() {
		getDeletions();
		return this;
	}

	private void getDeletions() {
		deletedElements.clear();
		nodeToEObject.forEach((node, obj) -> {
			if (opStrat.getGeneralModelChanges().isDeleted(obj))
				deletedElements.add(node);
		});
		edgeToEMFEdge.forEach((edge, emfEdge) -> {
			if (opStrat.getGeneralModelChanges().isDeleted(emfEdge))
				deletedElements.add(edge);
		});
	}

	boolean isElementDeleted(TGGRuleElement element) {
		return deletedElements.contains(element);
	}

	public MatchModification createModPattern() {
		getDeletions();
		MatchModification pattern = new MatchModification(DomainModification.UNCHANGED);
		Predicate<TGGRuleElement> isDel = e -> isElementDeleted(e);
		groupedElements.forEach((domain, bindingMap) -> {
			bindingMap.forEach((binding, elements) -> {
				DomainModification mod;
				if (elements.stream().allMatch(isDel))
					mod = DomainModification.COMPL_DEL;
				else if (elements.stream().anyMatch(isDel))
					mod = DomainModification.PART_DEL;
				else
					mod = DomainModification.UNCHANGED;
				pattern.setModType(domain, binding, mod);
			});
		});
		return pattern;
	}

	public Map<ITGGMatch, DomainType> analyseFilterNACViolations() {
		return opStrat.getFilterNacMatches().stream() //
				.filter(fnm -> fnm.getRuleName().startsWith(match.getRuleName())) //
				.filter(fnm -> belongsToMatch(fnm, match)) //
				.collect(Collectors.toMap(fnm -> fnm,
						fnm -> fnm.getRuleName().endsWith("SRC") ? DomainType.SRC : DomainType.TRG));
	}

	private boolean belongsToMatch(ITGGMatch filterNacMatch, ITGGMatch match) {
		for (String n : filterNacMatch.getParameterNames()) {
			if (!filterNacMatch.get(n).equals(match.get(n)))
				return false;
		}
		return true;
	}

	public Set<TGGRuleElement> getElts(EltFilter filter) {
		return opStrat.getMatchUtil().getElts(this, filter);
	}

	public Set<TGGRuleNode> getNodes() {
		return nodeToEObject.keySet();
	}

	public Set<TGGRuleEdge> getEdges() {
		return edgeToEMFEdge.keySet();
	}

	public Set<EObject> getObjects() {
		return eObjectToNode.keySet();
	}

	public Set<EMFEdge> getEMFEdges() {
		return emfEdgeToEdge.keySet();
	}

	public Map<TGGRuleNode, EObject> getNodeToEObject() {
		return nodeToEObject;
	}

	public Map<EObject, TGGRuleNode> getEObjectToNode() {
		return eObjectToNode;
	}

	public Map<TGGRuleEdge, EMFEdge> getEdgeToEMFEdge() {
		return edgeToEMFEdge;
	}

	public Map<EMFEdge, TGGRuleEdge> getEmfEdgeToEdge() {
		return emfEdgeToEdge;
	}

	public TGGRuleNode getNode(EObject object) {
		return eObjectToNode.get(object);
	}

	public EObject getObject(TGGRuleNode node) {
		return nodeToEObject.get(node);
	}

	public TGGRuleEdge getEdge(EMFEdge emfEdge) {
		return emfEdgeToEdge.get(emfEdge);
	}

	public EMFEdge getEMFEdge(TGGRuleEdge edge) {
		return edgeToEMFEdge.get(edge);
	}

}
