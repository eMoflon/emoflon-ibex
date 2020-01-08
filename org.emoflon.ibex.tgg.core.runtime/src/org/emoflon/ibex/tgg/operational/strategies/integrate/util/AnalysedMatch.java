package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchModPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class AnalysedMatch {

	private MatchModPattern pattern = new MatchModPattern(Modification.UNCHANGED);
	private ITGGMatch match;
	private Map<TGGRuleElement, Boolean> areRuleEltsDeleted;
	private Map<ITGGMatch, DomainType> filterNacViolations;
	private Map<EObject, TGGRuleNode> eObjectToNode;
	private Map<DomainType, Map<BindingType, List<TGGRuleElement>>> groupedElements;

	public AnalysedMatch(ITGGMatch match, Map<TGGRuleElement, Boolean> areRuleEltsDeleted,
			Map<ITGGMatch, DomainType> filterNacViolations, Map<EObject, TGGRuleNode> eObjectToNode) {
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

	public ITGGMatch getMatch() {
		return match;
	}

	public Boolean isRuleEltDeleted(TGGRuleElement element) {
		return areRuleEltsDeleted.get(element);
	}

	public Map<ITGGMatch, DomainType> getFilterNacViolations() {
		return filterNacViolations;
	}

	public Map<EObject, TGGRuleNode> getEObjectToNode() {
		return eObjectToNode;
	}

	public Map<DomainType, Map<BindingType, List<TGGRuleElement>>> getGroupedElements() {
		return groupedElements;
	}

	public static class EltFilter {

		private List<DomainType> domainTypes = Arrays.asList(DomainType.SRC, DomainType.CORR, DomainType.TRG);
		private List<BindingType> bindingTypes = Arrays.asList(BindingType.CONTEXT, BindingType.CREATE);

		private boolean all = true;
		private boolean deleted;

		public EltFilter domains(DomainType... domainTypes) {
			this.domainTypes = Arrays.asList(domainTypes);
			return this;
		}
		
		public EltFilter src() {
			this.domainTypes = Arrays.asList(DomainType.SRC);
			return this;
		}
		
		public EltFilter trg() {
			this.domainTypes = Arrays.asList(DomainType.TRG);
			return this;
		}
		
		public EltFilter srcAndTrg() {
			this.domainTypes = Arrays.asList(DomainType.SRC, DomainType.TRG);
			return this;
		}

		public EltFilter bindings(BindingType... bindingTypes) {
			this.bindingTypes = Arrays.asList(bindingTypes);
			return this;
		}
		
		public EltFilter create() {
			this.bindingTypes = Arrays.asList(BindingType.CREATE);
			return this;
		}
		
		public EltFilter context() {
			this.bindingTypes = Arrays.asList(BindingType.CONTEXT);
			return this;
		}

		public EltFilter deleted() {
			this.all = false;
			this.deleted = true;
			return this;
		}

		public EltFilter notDeleted() {
			this.all = false;
			this.deleted = false;
			return this;
		}
	}

	public List<TGGRuleElement> getElts(EltFilter filter) {
		List<TGGRuleElement> filtered = new ArrayList<>();

		for (DomainType domain : filter.domainTypes) {
			for (BindingType binding : filter.bindingTypes) {
				List<TGGRuleElement> group = groupedElements.get(domain).get(binding);
				if (filter.all)
					filtered.addAll(group);
				else
					group.forEach(elt -> {
						if (filter.deleted == isRuleEltDeleted(elt))
							filtered.add(elt);
					});
			}
		}

		return filtered;
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
		for (ITGGMatch fnm : filterNacViolations.keySet()) {
			builder.append(fnm.getRuleName() + "\n");
		}
		return builder.length() == 0 ? builder.toString() : builder.substring(0, builder.length() - 1);
	}

}