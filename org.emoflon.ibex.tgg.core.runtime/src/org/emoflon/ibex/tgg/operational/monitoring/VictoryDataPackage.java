package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

public class VictoryDataPackage {
    private Map<IMatch, Collection<IMatch>> matches;
    private List<Set<EObject>> protocol;

    public VictoryDataPackage(Map<IMatch, Collection<IMatch>> pMatches, List<Set<EObject>> pProtocol) {
	matches = pMatches;
	protocol = pProtocol;
    }

    public Collection<IMatch> getMatches() {
	return matches.keySet();
    }

    public Collection<IMatch> getExcludedMatches(IMatch pMatch) {
	return matches.get(pMatch);
    }

    public List<Set<EObject>> getProtocol() {
	return protocol;
    }
}
