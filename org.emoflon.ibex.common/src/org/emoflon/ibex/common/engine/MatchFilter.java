package org.emoflon.ibex.common.engine;

import java.util.Collection;
import java.util.stream.Stream;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.IPatternInterpreterProperties;

public abstract class MatchFilter<PM extends PatternMatchingEngine<IBEX_MODEL, ?, M>, IBEX_MODEL extends IBeXModel, M extends IMatch> {

	final protected PM patternMatcher;
	final protected IPatternInterpreterProperties engineProperties;

	public MatchFilter(final PM patternMatcher) {
		this.patternMatcher = patternMatcher;
		this.engineProperties = patternMatcher.getEngineProperties();
	}

	public abstract Stream<M> filterMatchStream(final IBeXPattern pattern);

	public abstract Collection<M> filterMatches(final IBeXPattern pattern);
}
