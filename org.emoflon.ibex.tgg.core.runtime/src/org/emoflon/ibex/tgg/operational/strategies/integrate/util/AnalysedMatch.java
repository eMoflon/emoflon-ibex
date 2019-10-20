package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.BindingType;
import language.DomainType;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class AnalysedMatch {

	private MatchModPattern pattern = new MatchModPattern(Modification.UNCHANGED);
	private IMatch match;
	private Map<TGGRuleElement, Boolean> areRuleEltsDeleted;
	private Map<IMatch, DomainType> filterNacViolations;
	private Map<EObject, TGGRuleNode> eObjectToNode;
	private Map<DomainType, Map<BindingType, List<TGGRuleElement>>> groupedElements;

	public AnalysedMatch(IMatch match, Map<TGGRuleElement, Boolean> areRuleEltsDeleted,
			Map<IMatch, DomainType> filterNacViolations, Map<EObject, TGGRuleNode> eObjectToNode) {
		this.match = match;
		this.areRuleEltsDeleted = areRuleEltsDeleted;
		this.filterNacViolations = filterNacViolations;
		this.eObjectToNode = eObjectToNode;
		this.groupedElements = splitUp(areRuleEltsDeleted);
		
		Predicate<TGGRuleElement> delPred = el -> areRuleEltsDeleted.get(el);
		groupedElements.forEach((domain, bindingMap) -> {
			bindingMap.forEach((binding, elements) -> {
				Modification mod;
				if (elements.stream().allMatch(delPred))
					mod = Modification.COMPL_DEL;
				else if (elements.stream().anyMatch(delPred))
					mod = Modification.PART_DEL;
				else
					mod = Modification.UNCHANGED;
				pattern.setModType(domain, binding, mod);
			});
		});
	}

	private Map<DomainType, Map<BindingType, List<TGGRuleElement>>> splitUp(Map<TGGRuleElement, Boolean> map) {
		return map.keySet().stream() //
				.collect(Collectors.groupingBy( //
						el -> el.getDomainType(), //
						Collectors.groupingBy(el -> el.getBindingType()) //
				));
	}

	public MatchModPattern getModPattern() {
		return pattern;
	}

	public IMatch getMatch() {
		return match;
	}

	public Map<TGGRuleElement, Boolean> getAreRuleEltsDeleted() {
		return areRuleEltsDeleted;
	}

	public Map<IMatch, DomainType> getFilterNacViolations() {
		return filterNacViolations;
	}

	public Map<EObject, TGGRuleNode> getEObjectToNode() {
		return eObjectToNode;
	}

	public Map<DomainType, Map<BindingType, List<TGGRuleElement>>> getGroupedElements() {
		return groupedElements;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AnalysedMatch [\n");
		builder.append("  " + print().replace("\n", "\n  "));
		builder.append("\n]");
		return builder.toString();
	}

	private String print() {
		StringBuilder builder = new StringBuilder();
		builder.append("Match [\n");
		builder.append("  " + match.getPatternName());
		builder.append("\n]\n");
		builder.append(pattern.toString() + "\n");
		builder.append("FilterNAC Violations [\n");
		builder.append("  " + printFilterNacViolations().replace("\n", "\n  "));
		builder.append("\n]");
		return builder.toString();
	}

	private String printFilterNacViolations() {
		StringBuilder builder = new StringBuilder();
		for (IMatch fnm : filterNacViolations.keySet()) {
			builder.append(fnm.getRuleName() + "\n");
		}
		return builder.length() == 0 ? builder.toString() : builder.substring(0, builder.length() - 1);
	}

}
