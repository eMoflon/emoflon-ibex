package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collection;
import java.util.List;

import org.emoflon.ibex.tgg.operational.monitoring.data.ProtocolStep;

public class DataPackage {
    private Collection<IbexMatch> matches;
    private List<ProtocolStep> protocol;

    public DataPackage(Collection<IbexMatch> pMatches, List<ProtocolStep> pProtocol) {
	matches = pMatches;
	protocol = pProtocol;
    }

    public Collection<IbexMatch> getMatches() {
	return matches;
    }

    public List<ProtocolStep> getProtocol() {
	return protocol;
    }
}
