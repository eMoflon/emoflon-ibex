package org.emoflon.ibex.common.engine;

import java.util.Collection;
import java.util.stream.Stream;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.operational.IMatch;

public abstract class MatchFilter<IBEX_MODEL extends IBeXModel, M extends IMatch> {

	final protected PatternMatchingEngine<IBEX_MODEL, ?, M> patternMatcher;

	public MatchFilter(final PatternMatchingEngine<IBEX_MODEL, ?, M> patternMatcher) {
		this.patternMatcher = patternMatcher;
	}

	public abstract Stream<M> filterMatchStream(final IBeXPattern pattern);

	public abstract Collection<M> filterMatches(final IBeXPattern pattern);
}
