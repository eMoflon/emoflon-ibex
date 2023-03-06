package org.emoflon.ibex.tgg.runtime.strategies.integrate.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.modules.MatchConsumer;
import org.emoflon.ibex.tgg.runtime.strategies.modules.MatchDistributor;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public class FilterNACMatchCollector extends MatchConsumer {

	private final Map<String, Collection<String>> ruleName2filterNacPatternNames;
	private final Map<String, Map<NACOverlap, Collection<ITGGMatch>>> pattern2filterNacMatches;
	private final Map<String, Collection<String>> filterNacPattern2nodeNames;

	public FilterNACMatchCollector(IbexOptions options) {
		super(options);
		ruleName2filterNacPatternNames = options.tgg.getFlattenedConcreteTGGRules().stream() //
				.collect(Collectors.toMap(r -> r.getName(), r -> new LinkedList<>()));
		pattern2filterNacMatches = new HashMap<>();
		filterNacPattern2nodeNames = new HashMap<>();
	}

	@Override
	protected void registerAtMatchDistributor(MatchDistributor matchDistributor) {
		Set<PatternType> patternSet = new HashSet<>(Arrays.asList(PatternType.FILTER_NAC_SRC, PatternType.FILTER_NAC_TRG));
		matchDistributor.registerSingle(patternSet, this::addFilterNACMatch, this::removeFilterNACMatch);
		matchDistributor.registerMultiple(patternSet, m -> m.forEach(this::addFilterNACMatch), m -> m.forEach(this::removeFilterNACMatch));
	}

	public void addFilterNACMatch(ITGGMatch match) {
		if (!pattern2filterNacMatches.containsKey(match.getPatternName())) {
			String ruleName = match.getRuleName().split("_")[0];
			ruleName2filterNacPatternNames.get(ruleName).add(match.getPatternName());

			pattern2filterNacMatches.put(match.getPatternName(), new HashMap<>());
			filterNacPattern2nodeNames.put(match.getPatternName(), match.getParameterNames());
		}

		Map<NACOverlap, Collection<ITGGMatch>> overlap2match = pattern2filterNacMatches.get(match.getPatternName());
		NACOverlap overlap = new NACOverlap(match);
		// the number of matches per overlap should not exceed a certain number
		overlap2match.putIfAbsent(overlap, new LinkedList<>());
		overlap2match.get(overlap).add(match);

		LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received & added " + match.getPatternName() + "(" + match.hashCode() + ")");
	}

	public boolean removeFilterNACMatch(ITGGMatch match) {
		NACOverlap overlap = new NACOverlap(match);
		return pattern2filterNacMatches.get(match.getPatternName()).get(overlap).remove(match);
	}

	public Collection<ITGGMatch> getFilterNACMatches(ITGGMatch match) {
		Collection<ITGGMatch> filterNacMatches = new LinkedList<>();
		for (String nacPatternName : ruleName2filterNacPatternNames.getOrDefault(match.getRuleName(), new LinkedList<>())) {
			Map<NACOverlap, Collection<ITGGMatch>> overlap2matches = pattern2filterNacMatches.get(nacPatternName);
			if (overlap2matches == null)
				continue;
			NACOverlap overlap = new NACOverlap(match, filterNacPattern2nodeNames.get(nacPatternName));
			filterNacMatches.addAll(overlap2matches.getOrDefault(overlap, new LinkedList<>()));
		}
		return filterNacMatches;
	}

	public class NACOverlap {
		private final Collection<String> parameters;
		private final ITGGMatch match;

		private Integer hash = null;

		public NACOverlap(ITGGMatch nacMatch) {
			this.match = nacMatch;
			this.parameters = nacMatch.getParameterNames();
		}

		public NACOverlap(ITGGMatch match, Collection<String> parameters) {
			this.match = match;
			this.parameters = parameters;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof NACOverlap otherOverlap)
				return parameters.stream().allMatch(p -> match.get(p).equals(otherOverlap.match.get(p)));
			return false;
		}

		@Override
		public int hashCode() {
			if (hash == null)
				hash = Arrays.hashCode(parameters.stream().map(n -> match.get(n)).collect(Collectors.toList()).toArray());
			return hash;
		}
	}

}
