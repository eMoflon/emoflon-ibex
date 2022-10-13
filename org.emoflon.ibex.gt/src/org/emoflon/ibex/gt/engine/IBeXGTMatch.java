package org.emoflon.ibex.gt.engine;

import java.util.Map;

import org.emoflon.ibex.common.operational.IMatch;

public abstract class IBeXGTMatch<M extends IBeXGTMatch<M, P>, P extends IBeXGTPattern<P, M>> implements IMatch {

	protected P typedPattern;

	public IBeXGTMatch(final P typedPattern, final Map<String, Object> nodes) {
		this.typedPattern = typedPattern;
		initialize(nodes);
	}

	protected abstract void initialize(final Map<String, Object> nodes);

	@Override
	public String getPatternName() {
		return typedPattern.patternName;
	}

	public abstract boolean checkConditions();

}
