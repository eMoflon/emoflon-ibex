package org.emoflon.ibex.gt.engine;

import java.util.Map;

import org.emoflon.ibex.common.engine.HashUtil;
import org.emoflon.ibex.common.engine.IMatch;

public abstract class IBeXGTMatch<M extends IBeXGTMatch<M, P>, P extends IBeXGTPattern<P, M>> implements IMatch {

	protected P typedPattern;

	protected long hash;
	protected boolean hashInit = false;

	public IBeXGTMatch(final P typedPattern, final Map<String, Object> nodes) {
		this.typedPattern = typedPattern;
		initialize(nodes);
	}

	public IBeXGTMatch(final M other) {
		this.typedPattern = other.typedPattern;
		initialize(other);
	}

	protected abstract void initialize(final Map<String, Object> nodes);

	protected abstract void initialize(final M other);

	public abstract boolean checkBindings();

	public abstract boolean checkConditions();

	public abstract M copy();

	@Override
	public String getPatternName() {
		return typedPattern.patternName;
	}

	@Override
	public long getHashCode() {
		if (!hashInit) {
			hash = HashUtil.collectionToHash(getObjects());
			hashInit = true;
		}
		return hash;
	}

	@Override
	public int hashCode() {
		return (int) getHashCode();
	}

	public void setHashCode(long hashCode) {
		this.hash = hashCode;
		hashInit = true;
	}

	@Override
	public boolean equals(final Object object) {
		return object instanceof IMatch && isEqual((IMatch) object);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Match for ").append(getPatternName()).append("(" + this.hashCode() + ") {")
				.append(System.lineSeparator());

		for (final String parameterName : getParameterNames()) {
			s.append("	").append(parameterName);
			s.append(" -> ").append(get(parameterName)).append(System.lineSeparator());
		}
		s.append("}");

		return s.toString();
	}

	public P getPattern() {
		return typedPattern;
	}
}
