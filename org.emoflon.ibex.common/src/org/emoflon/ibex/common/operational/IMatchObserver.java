package org.emoflon.ibex.common.operational;

public interface IMatchObserver {
	public void addMatch(IMatch match);

	public void removeMatch(IMatch match);

	public boolean isPatternRelevantForCompiler(String patternName);
}
