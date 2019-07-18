package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collection;
import java.util.List;

import org.emoflon.ibex.tgg.operational.monitoring.data.ProtocolStep;

public class VictoryDataPackage {
    private Collection<VictoryMatch> matches;
    private List<ProtocolStep> protocol;

    public VictoryDataPackage(Collection<VictoryMatch> pMatches, List<ProtocolStep> pProtocol) {
	matches = pMatches;
	protocol = pProtocol;
    }

    public Collection<VictoryMatch> getMatches() {
	return matches;
    }

    public List<ProtocolStep> getProtocol() {
	return protocol;
    }
}
