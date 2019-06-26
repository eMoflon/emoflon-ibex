package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.monitoring.data.ProtocolStep;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public abstract class IbexController implements IbexObserver, IUpdatePolicy {

	private OperationalStrategy op;
	private Set<EObject> resourceList = new HashSet<EObject>();
	private List<ProtocolStep> protocolsStepList = new ArrayList<ProtocolStep>();
	public void register(OperationalStrategy pOperationalStrategy) {
		pOperationalStrategy.registerObserver(this);
		pOperationalStrategy.setUpdatePolicy(this);
		op = pOperationalStrategy;
	}

	@Override
	public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {

		/*
		 * TODO implement This method should do the following things:
		 * 
		 * 1) Limit the number of matches to the number the UI requests This number is
		 * provided by calling getRequestedMatchCount()
		 * 
		 * 2) Map each match to its excluded matches The keys of the map should be the
		 * (limited) list of matches provided by part 1) The values should be a
		 * collection for each key-match that contains all matches that are excluded if
		 * the key-match is applied If there are no matches that get excluded, simply
		 * map the match to null (rather than creating an empty collection), so as not
		 * to waste performance
		 * 
		 * -------------
		 * 
		 * Note that the code below is just to ensure that the UI keeps working. Feel
		 * free to delete and replace it with your own implementation.
		 */
		
		List<ProtocolStep> protocols = getProtocols();
		Map<IMatch, Collection<IMatch>> matches = new HashMap<>();
		matchContainer.getMatches().forEach(match -> matches.put(match, null));
		
		return chooseOneMatch(new VictoryDataPackage(matches, protocols)); // TODO add protocol here
	}

	public Map<IMatch, Collection<IMatch>> getMoreMatches(int amount) {

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
		HashSet<EObject> srcResourceList = getResourceItems(op.getSourceResource());
		HashSet<EObject> trgResourceList = getResourceItems(op.getTargetResource());
		HashSet<EObject> corrResourceList = getResourceItems(op.getCorrResource());

		ProtocolStep protocolStep = new ProtocolStep(index, srcResourceList, trgResourceList, corrResourceList);
		protocolsStepList.add(protocolStep);

		return protocolsStepList;
	}
	
	private HashSet<EObject> getResourceItems(Resource resource) {
		HashSet<EObject> currentResourceList = new HashSet<EObject>();
		
		for (EObject ri: resource.getContents()) {
			if (!resourceList.contains(ri)) {
				resourceList.add(ri);
				currentResourceList.add(ri);
			}
		}
		
		return currentResourceList;
	}

	public abstract IMatch chooseOneMatch(VictoryDataPackage pDataPackage);

	protected abstract int getRequestedMatchCount();
}
