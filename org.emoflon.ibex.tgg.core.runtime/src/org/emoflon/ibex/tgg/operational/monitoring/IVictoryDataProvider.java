package org.emoflon.ibex.tgg.operational.monitoring;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRule;

public interface IVictoryDataProvider {
    public TGGRule getRule(String pRuleName);
    public Set<IMatch> getMatches();
    public Set<IMatch> getMatches(IMatch match);
	public Set<IMatch> getMatches(String pRuleName);
    public Set<EObject> getMatchNeighbourhood(IMatch match, int k);
    abstract public void saveModels(String[] pLocations) throws IOException;
    public Collection<TGGRule> getAllRules();
    public String[][] getDefaultSaveData();
}
