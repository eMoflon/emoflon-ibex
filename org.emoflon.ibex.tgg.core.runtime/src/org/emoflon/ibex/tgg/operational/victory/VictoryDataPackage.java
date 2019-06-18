package org.emoflon.ibex.tgg.operational.victory;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public class VictoryDataPackage {
    private Collection<VictoryMatch> matches;
    private List<Set<EObject>> protocol;

    public VictoryDataPackage(Collection<VictoryMatch> pMatches, List<Set<EObject>> pProtocol) {
	matches = pMatches;
	protocol = pProtocol;
    }

    public Collection<VictoryMatch> getMatches() {
	return matches;
    }

    public List<Set<EObject>> getProtocol() {
	return protocol;
    }
}
