package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.engine.MatchFilter;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;

public class IBeXGTMatchFilter extends MatchFilter<IBeXGTPatternMatcher<?>, GTModel, IBeXGTMatch<?, ?>> {

	public IBeXGTMatchFilter(IBeXGTPatternMatcher<?> patternMatcher) {
		super(patternMatcher);
	}

	@Override
	public Stream<IBeXGTMatch<?, ?>> filterMatchStream(IBeXPattern pattern) {
		IBeXGTPattern<?, ?> typedPattern = patternMatcher.name2typedPattern.get(pattern.getName());

		if ((pattern.getConditions() == null || pattern.getConditions().isEmpty())
				&& typedPattern.getBindings().isEmpty())
			return patternMatcher.getMatches(pattern.getName(), false).stream();

		if (!typedPattern.getBindings().isEmpty() && !typedPattern.requiresChecks(engineProperties))
			return patternMatcher.getMatches(pattern.getName(), false).parallelStream().filter(m -> m.checkBindings());

		if (typedPattern.getBindings().isEmpty() && typedPattern.requiresChecks(engineProperties))
			return patternMatcher.getMatches(pattern.getName(), false).parallelStream()
					.filter(m -> m.checkConditions());

		return patternMatcher.getMatches(pattern.getName(), false).parallelStream().filter(m -> m.checkBindings())
				.filter(m -> m.checkConditions());
	}

	@Override
	public Collection<IBeXGTMatch<?, ?>> filterMatches(IBeXPattern pattern) {
		IBeXGTPattern<?, ?> typedPattern = patternMatcher.name2typedPattern.get(pattern.getName());

		if ((pattern.getConditions() == null || pattern.getConditions().isEmpty())
				&& typedPattern.getBindings().isEmpty())
			return patternMatcher.getMatches(pattern.getName(), false);

		if (!typedPattern.getBindings().isEmpty() && !typedPattern.requiresChecks(engineProperties))
			return patternMatcher.getMatches(pattern.getName(), false).parallelStream().filter(m -> m.checkBindings())
					.collect(Collectors.toList());

		if (typedPattern.getBindings().isEmpty() && typedPattern.requiresChecks(engineProperties))
			return patternMatcher.getMatches(pattern.getName(), false).parallelStream().filter(m -> m.checkConditions())
					.collect(Collectors.toList());

		return patternMatcher.getMatches(pattern.getName(), false).parallelStream().filter(m -> m.checkBindings())
				.filter(m -> m.checkConditions()).collect(Collectors.toList());
	}

}
