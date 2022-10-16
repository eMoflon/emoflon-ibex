package org.emoflon.ibex.gt.engine;

public abstract class IBeXGTPatternFactory {
	protected final IBeXGTPatternMatcher<?, ?> patternMatcher;

	public IBeXGTPatternFactory(final IBeXGTPatternMatcher<?, ?> patternMatcher) {
		this.patternMatcher = patternMatcher;
	}

	protected void registerTypedPattern(IBeXGTPattern<?, ?> pattern) {
		patternMatcher.registerTypedPattern(pattern);
	}
}
