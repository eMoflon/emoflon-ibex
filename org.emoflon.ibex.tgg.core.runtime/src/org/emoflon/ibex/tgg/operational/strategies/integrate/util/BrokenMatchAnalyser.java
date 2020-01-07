package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class BrokenMatchAnalyser {

	private final INTEGRATE integrate;
	private final Map<String, TGGRule> rules;

	public BrokenMatchAnalyser(INTEGRATE integrate) {
		this.integrate = integrate;
		List<TGGRule> rules = integrate.getOptions().flattenedTGG().getRules();
		this.rules = rules.stream() //
				.collect(Collectors.toMap(rule -> rule.getName(), rule -> rule));
	}

	public AnalysedMatch analyse(ITGGMatch brokenMatch) {
		TGGRule rule = rules.get(brokenMatch.getRuleName());

		Map<TGGRuleNode, EObject> nodeToEObject = new HashMap<>();
		rule.getNodes().forEach(node -> {
			nodeToEObject.put(node, (EObject) brokenMatch.get(node.getName()));
		});
		Map<EObject, TGGRuleNode> eObjectToNode = new HashMap<>();
		nodeToEObject.forEach((node, eObject) -> {
			eObjectToNode.put(eObject, node);
		});

		// --- Deleted elements analysis ---

		Map<TGGRuleElement, Boolean> areRuleEltsDeleted = new HashMap<>();
		nodeToEObject.forEach((node, el) -> {
			boolean hasContainingRes = false;
			if (el.eResource() != null)
				hasContainingRes = isValidResource(el.eResource());
			areRuleEltsDeleted.put(node, !hasContainingRes);
		});
		rule.getEdges().forEach(edge -> {
			boolean deletedEdge = true;
			if (!areRuleEltsDeleted.get(edge.getSrcNode())) {
				Object featureValue = nodeToEObject.get(edge.getSrcNode()).eGet(edge.getType());
				if (featureValue != null) {
					if (featureValue instanceof EObject)
						deletedEdge = false;
					else if (featureValue instanceof EObjectEList) {
						EObjectEList<?> objects = (EObjectEList<?>) featureValue;
						if (objects.contains(nodeToEObject.get(edge.getTrgNode())))
							deletedEdge = false;
					}
				}
			}
			areRuleEltsDeleted.put(edge, deletedEdge);
		});

		// --- Filter NAC analysis ---

		Map<ITGGMatch, DomainType> filterNacViolations = integrate.getFilterNacMatches().stream() //
				.filter(fnm -> fnm.getRuleName().startsWith(brokenMatch.getRuleName())) //
				.filter(fnm -> belongsToBrokenMatch(fnm, brokenMatch)) //
				.collect(Collectors.toMap(fnm -> fnm, fnm -> {
					if (fnm.getRuleName().endsWith("SRC"))
						return DomainType.SRC;
					else
						return DomainType.TRG;
				}));

		return new AnalysedMatch(brokenMatch, areRuleEltsDeleted, filterNacViolations, eObjectToNode);
	}

	private boolean isValidResource(Resource resource) {
		TGGResourceHandler resourceHandler = integrate.getOptions().getResourceHandler();
		if (resource.equals(resourceHandler.getSourceResource()))
			return true;
		if (resource.equals(resourceHandler.getTargetResource()))
			return true;
		if (resource.equals(resourceHandler.getCorrResource()))
			return true;
		return false;
	}

	private boolean belongsToBrokenMatch(ITGGMatch filterNacMatch, ITGGMatch brokenMatch) {
		for (String n : filterNacMatch.getParameterNames()) {
			if (!filterNacMatch.get(n).equals(brokenMatch.get(n)))
				return false;
		}
		return true;
	}
	
	public TGGRule getRule(String name) {
		return rules.get(name);
	}

}
