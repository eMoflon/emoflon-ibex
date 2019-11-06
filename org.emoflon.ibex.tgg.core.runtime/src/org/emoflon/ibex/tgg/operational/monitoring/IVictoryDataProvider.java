package org.emoflon.ibex.tgg.operational.monitoring;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

import language.TGGRule;

public interface IVictoryDataProvider {
	public TGGRule getRule(String pRuleName);

	public Set<ITGGMatch> getMatches();

	public Set<ITGGMatch> getMatches(ITGGMatch match);

	public Set<ITGGMatch> getMatches(String pRuleName);

	public Collection<EObject> getMatchNeighbourhoods(Collection<EObject> nodes, int k);

	public Collection<EObject> getMatchNeighbourhood(EObject node, int k);

	abstract public Set<URI> saveModels(String[] pLocations) throws IOException;

	public Collection<TGGRule> getAllRules();

	public String[][] getDefaultSaveData();
}
