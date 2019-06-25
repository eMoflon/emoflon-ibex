package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.monitoring.data.ProtocolStep;

public class VictoryDataPackage {
    private Map<IMatch, Collection<IMatch>> matches;
    private List<ProtocolStep> protocol;

    public VictoryDataPackage(Map<IMatch, Collection<IMatch>> pMatches, List<ProtocolStep> pProtocol) {
	matches = pMatches;
	protocol = pProtocol;
    }

    public Collection<IMatch> getMatches() {
	return matches.keySet();
    }

    public Collection<IMatch> getExcludedMatches(IMatch pMatch) {
	return matches.get(pMatch);
    }

    public List<ProtocolStep> getProtocol() {
	return protocol;
    }
}
