package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRule;

public interface IVictoryDataProvider {
    public TGGRule getRule(String pRuleName);
    public Set<IMatch> getMatches();
    public Set<IMatch> getMatches(IMatch match);
	public Set<IMatch> getMatches(String pRuleName);
	public Set<IMatch> getNeighboringMatches(IMatch match, int k);
}
