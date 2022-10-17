package org.emoflon.ibex.gt.engine;

import java.util.Map;

public abstract class IBeXGTCoMatch<CM extends IBeXGTCoMatch<CM, CP, R, P, M>, CP extends IBeXGTCoPattern<CP, CM, R, P, M>, R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>>
		extends IBeXGTMatch<CM, CP> {

	final protected R typedRule;

	public IBeXGTCoMatch(final R typedRule, final CP typedCoPattern, final Map<String, Object> nodes) {
		super(typedCoPattern, nodes);
		this.typedRule = typedRule;
	}

	public IBeXGTCoMatch(CM other) {
		super(other);
		this.typedRule = other.typedRule;
	}

}
