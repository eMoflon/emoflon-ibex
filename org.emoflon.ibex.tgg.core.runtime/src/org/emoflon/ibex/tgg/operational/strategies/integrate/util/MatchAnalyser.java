package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.Arrays;
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

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class MatchAnalyser {

	private final INTEGRATE integrate;

	private Map<String, TGGRule> nameToRules;
	private Map<ITGGMatch, MatchAnalysis> matchToAnalysis;

	public MatchAnalyser(INTEGRATE integrate) {
		this.integrate = integrate;
		init();
	}

	private void init() {
		nameToRules = integrate.getOptions().tgg.flattenedTGG().getRules().stream() //
				.collect(Collectors.toMap(rule -> rule.getName(), rule -> rule));
		matchToAnalysis = new HashMap<>();
	}

	public MatchAnalysis getAnalysis(ITGGMatch match) {
		return getRawAnalysis(match).update();
	}

	private MatchAnalysis getRawAnalysis(ITGGMatch match) {
		return matchToAnalysis.computeIfAbsent(match, k -> analyseMatch(match));
	}

	private MatchAnalysis analyseMatch(ITGGMatch match) {
		return new MatchAnalysis(integrate, match, nameToRules.get(match.getRuleName()));
	}

	Set<TGGRuleElement> getElts(MatchAnalysis analysis, EltFilter filter) {
		Set<TGGRuleElement> filtered = new HashSet<>();
		for (DomainType domain : filter.domainTypes) {
			for (BindingType binding : filter.bindingTypes) {
				List<TGGRuleElement> group = analysis.groupedElements.get(domain).get(binding);
				if (filter.all)
					filtered.addAll(group);
				else
					group.forEach(elt -> {
						if (filter.deleted == analysis.isElementDeleted(elt))
							filtered.add(elt);
					});
			}
		}
		return filtered;
	}

	public Set<TGGRuleElement> getElts(ITGGMatch match, EltFilter filter) {
		MatchAnalysis analysis = getRawAnalysis(match);
		if (!filter.all)
			analysis.update();

		return getElts(analysis, filter);
	}

	Stream<TGGRuleNode> getNodeStream(ITGGMatch match, EltFilter filter) {
		return getElts(match, filter).stream() //
				.filter(elt -> elt instanceof TGGRuleNode) //
				.map(elt -> (TGGRuleNode) elt);
	}

	public Set<TGGRuleNode> getNodes(ITGGMatch match, EltFilter filter) {
		return getNodeStream(match, filter).collect(Collectors.toSet());
	}

	public Stream<TGGRuleEdge> getEdgeStream(ITGGMatch match, EltFilter filter) {
		return getElts(match, filter).stream() //
				.filter(elt -> elt instanceof TGGRuleEdge) //
				.map(elt -> (TGGRuleEdge) elt);
	}

	public Set<TGGRuleEdge> getEdges(ITGGMatch match, EltFilter filter) {
		return getEdgeStream(match, filter).collect(Collectors.toSet());
	}

	public Set<EObject> getObjects(ITGGMatch match, EltFilter filter) {
		return getNodeStream(match, filter) //
				.map(n -> (EObject) match.get(n.getName())) //
				.collect(Collectors.toSet());
	}

	public Set<EMFEdge> getEMFEdges(ITGGMatch match, EltFilter filter) {
		MatchAnalysis analysis = getRawAnalysis(match);
		return getEdgeStream(match, filter) //
				.map(e -> analysis.getEMFEdge(e)) //
				.collect(Collectors.toSet());
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

		public EltFilter corr() {
			this.domainTypes = Arrays.asList(DomainType.CORR);
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

}
