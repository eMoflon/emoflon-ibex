package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.monitoring.data.ProtocolStep;
import org.emoflon.ibex.tgg.operational.monitoring.data.TGGObjectGraph;

public class DataPackage {
	private Collection<IbexMatch> matches;
	private List<ProtocolStep> protocol;
	private TGGObjectGraph tripleGraph;

	public DataPackage(Collection<IbexMatch> pMatches, List<ProtocolStep> pProtocol,
			TGGObjectGraph tripleGraph) {
		matches = pMatches;
		protocol = pProtocol;
		this.tripleGraph = tripleGraph;
	}

	public Collection<IbexMatch> getMatches() {
		return matches;
	}

	public List<ProtocolStep> getProtocol() {
		return protocol;
	}
	
	public TGGObjectGraph getTripleGraph() {
		return tripleGraph;
	}

}
