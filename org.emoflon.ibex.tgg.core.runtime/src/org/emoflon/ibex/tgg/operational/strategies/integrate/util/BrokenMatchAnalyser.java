package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;

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

	public AnalysedMatch analyse(IMatch brokenMatch) {
		TGGRule rule = rules.get(brokenMatch.getRuleName());

		Map<TGGRuleNode, EObject> nodeElements = new HashMap<>();
		rule.getNodes().forEach(node -> {
			nodeElements.put(node, (EObject) brokenMatch.get(node.getName()));
		});

		// --- Deleted elements analysis ---

		Map<TGGRuleElement, Boolean> areElementsDeleted = new HashMap<>();
		nodeElements.forEach((node, el) -> {
			boolean hasContainingRes = false;
			if (el.eResource() != null)
				hasContainingRes = isValidResource(el.eResource());
			areElementsDeleted.put(node, !hasContainingRes);
		});
		rule.getEdges().forEach(edge -> {
			boolean deletedEdge = true;
			if (!areElementsDeleted.get(edge.getSrcNode())) {
				Object featureValue = nodeElements.get(edge.getSrcNode()).eGet(edge.getType());
				if (featureValue != null) {
					if (featureValue instanceof EObject)
						deletedEdge = false;
					else if (featureValue instanceof EObjectEList) {
						EObjectEList<?> objects = (EObjectEList<?>) featureValue;
						if (objects.contains(nodeElements.get(edge.getTrgNode())))
							deletedEdge = false;
					}
				}
			}
			areElementsDeleted.put(edge, deletedEdge);
		});

		// --- Filter NAC analysis ---

		Map<IMatch, DomainType> filterNacViolations = integrate.getFilterNacMatches().stream() //
				.filter(fnm -> fnm.getRuleName().startsWith(brokenMatch.getRuleName())) //
				.filter(fnm -> belongsToBrokenMatch(fnm, brokenMatch)) //
				.collect(Collectors.toMap(fnm -> fnm, fnm -> {
					if (fnm.getRuleName().endsWith("SRC"))
						return DomainType.SRC;
					else
						return DomainType.TRG;
				}));

		return new AnalysedMatch(brokenMatch, areElementsDeleted, filterNacViolations);
	}

	private boolean isValidResource(Resource resource) {
		if (resource.equals(integrate.getSourceResource()))
			return true;
		if (resource.equals(integrate.getTargetResource()))
			return true;
		if (resource.equals(integrate.getCorrResource()))
			return true;
		return false;
	}

	private boolean belongsToBrokenMatch(IMatch filterNacMatch, IMatch brokenMatch) {
		for (String n : filterNacMatch.getParameterNames()) {
			if (!filterNacMatch.get(n).equals(brokenMatch.get(n)))
				return false;
		}
		return true;
	}

}
