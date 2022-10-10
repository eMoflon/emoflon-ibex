package org.emoflon.ibex.common.engine;

import java.util.Collection;
import java.util.stream.Stream;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.operational.IMatch;

public abstract class IMatchFilter<IBEX_MODEL extends IBeXModel> {

	final protected IBeXPatternMatchingEngine<IBEX_MODEL> patternMatcher;

	public IMatchFilter(final IBeXPatternMatchingEngine<IBEX_MODEL> patternMatcher) {
		this.patternMatcher = patternMatcher;
	}

	public abstract Stream<IMatch> filterMatchStream(final IBeXPattern pattern);

	public abstract Collection<IMatch> filterMatches(final IBeXPattern pattern);
}
