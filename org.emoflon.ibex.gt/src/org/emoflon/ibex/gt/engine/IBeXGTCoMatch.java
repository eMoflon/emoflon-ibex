package org.emoflon.ibex.gt.engine;

import java.util.Map;

public abstract class IBeXGTCoMatch<CM extends IBeXGTCoMatch<CM, CP, R, P, M>, CP extends IBeXGTCoPattern<CP, CM, R, P, M>, R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>>
		extends IBeXGTMatch<CM, CP> {

	final public R typedRule;
	final public M typedMatch;

	public IBeXGTCoMatch(final R typedRule, final CP typedCoPattern, final M typedMatch,
			final Map<String, Object> nodes) {
		super(typedCoPattern, nodes);
		this.typedRule = typedRule;
		this.typedMatch = typedMatch;
	}

	public IBeXGTCoMatch(CM other) {
		super(other);
		this.typedRule = other.typedRule;
		this.typedMatch = other.typedMatch;
	}

}
