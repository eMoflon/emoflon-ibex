package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.monitoring.data.ProtocolStep;
import org.emoflon.ibex.tgg.operational.monitoring.data.TGGObjectGraph;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public abstract class IbexController implements IbexObserver, IUpdatePolicy {

	private OperationalStrategy op;
	private Set<EObject> resourceList = new HashSet<EObject>();
	private List<ProtocolStep> protocolsStepList = new ArrayList<ProtocolStep>();
	private Map<IMatch, VictoryMatch> matchMapping = new HashMap<>();
    

	public void register(OperationalStrategy pOperationalStrategy) {
		pOperationalStrategy.registerObserver(this);
		pOperationalStrategy.setUpdatePolicy(this);
		op = pOperationalStrategy;
	}

	@Override
	public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
	    updateMatchMapping(matchContainer.getMatches());
	
	    return chooseOneMatch(new VictoryDataPackage(matchMapping.values(), getProtocols()));

	}

	public Collection<VictoryMatch> getMoreMatches(int amount) {

		/*
		 * TODO implement This method needs to provide the specified number of
		 * additional matches. This method will be called by the UI when the user
		 * requests additional matches beyond those that were given to the UI in the
		 * method above. The structure of the map is the same as described above.
		 */

		return null;
	}

	private List<ProtocolStep> getProtocols() {
		int index = protocolsStepList.size();
		HashSet<EObject> srcResourceList = new HashSet<EObject>();
		HashSet<EObject> trgResourceList = new HashSet<EObject>();
		HashSet<EObject> corrResourceList = new HashSet<EObject>();

		Resource p = op.getProtocolResource();
		EList<EObject> protocols = p.getContents();
		if (protocols.size() > 0) {
			EList<EObject> items = protocols.get(protocols.size() - 1).eCrossReferences();
			srcResourceList = getResourceItems(items, op.getSourceResource());
			trgResourceList = getResourceItems(items, op.getTargetResource());
			corrResourceList = getResourceItems(items, op.getCorrResource());
			ProtocolStep protocolStep = new ProtocolStep(index, new TGGObjectGraph(srcResourceList, trgResourceList, corrResourceList));
			protocolsStepList.add(protocolStep);
		}

		return protocolsStepList;
	}

	private HashSet<EObject> getResourceItems(EList<EObject> items, Resource resource) {
		HashSet<EObject> currentResourceList = new HashSet<EObject>();

		for (EObject item : items) {
			if (!resourceList.contains(item) && item.eResource().equals(resource)) {
				currentResourceList.add(item);
				resourceList.add(item);
			}
		}

		return currentResourceList;
	}

	public abstract IMatch chooseOneMatch(VictoryDataPackage pDataPackage);
    
	private void updateMatchMapping(Collection<IMatch> pMatches) {
	
	    for(Iterator<IMatch> iterator = matchMapping.keySet().iterator(); iterator.hasNext();)
		if(!pMatches.contains(iterator.next()))
		    iterator.remove();
	
	    for(IMatch match : pMatches) 
		if(!matchMapping.containsKey(match))
		    matchMapping.put(match, new VictoryMatch(match));
	}

	protected abstract int getRequestedMatchCount();
}
