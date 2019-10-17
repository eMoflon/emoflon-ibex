package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

	private int step = 0;
	private OperationalStrategy operationalStrategy;
	private List<ProtocolStep> protocolsStepList = new ArrayList<ProtocolStep>();
	private Map<IMatch, IbexMatch> matchMapping = new HashMap<>();
	private String previouslyAppliedRule;

	public void register(OperationalStrategy pOperationalStrategy) {
		operationalStrategy = pOperationalStrategy;
		pOperationalStrategy.registerObserver(this);
		pOperationalStrategy.setUpdatePolicy(this);
	}

	@Override
	public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {

		updateMatchMapping(matchContainer.getMatches());
		updateProtocols(previouslyAppliedRule);

		IMatch chosenMatch = chooseOneMatch(new DataPackage(matchMapping.values(), protocolsStepList));
		previouslyAppliedRule = chosenMatch.getRuleName();
		return chosenMatch;
	}

	public Collection<IbexMatch> getMoreMatches(int amount) {

		/*
		 * TODO implement This method needs to provide the specified number of
		 * additional matches. This method will be called by the UI when the user
		 * requests additional matches beyond those that were given to the UI in the
		 * method above. The structure of the map is the same as described above.
		 */

		return null;
	}

	private void updateProtocols(String appliedRuleName) {

		EList<EObject> protocols = operationalStrategy.getProtocolResource().getContents();
		if (protocols.isEmpty())
			return;

		int index = protocols.size() - 1;
		EList<EObject> items = protocols.get(index).eCrossReferences();
		ProtocolStep protocolStep = new ProtocolStep(index,
				new TGGObjectGraph(filterResourceItems(items, operationalStrategy.getSourceResource()),
						filterResourceItems(items, operationalStrategy.getTargetResource()),
						filterResourceItems(items, operationalStrategy.getCorrResource())),
				operationalStrategy.getTGG().getRules().stream().filter(rule -> rule.getName().equals(appliedRuleName))
						.findFirst().orElse(null));
		protocolsStepList.add(protocolStep);
	}

	private Set<EObject> filterResourceItems(EList<EObject> items, Resource resource) {
		return items.stream().filter(item -> item.eResource().equals(resource)).collect(Collectors.toSet());
	}

	private void updateMatchMapping(Collection<IMatch> pMatches) {

		for (Iterator<IMatch> iterator = matchMapping.keySet().iterator(); iterator.hasNext();) {
			IMatch match = iterator.next();
			if (pMatches.contains(match))
				continue;
			else if (operationalStrategy.getBlockedMatches().containsKey(match))
				continue;
			else
				iterator.remove();
		}

		IbexMatch.startMatchCreation(step);

		for (IMatch match : pMatches)
			if (matchMapping.containsKey(match))
				matchMapping.get(match).setBlockingReason(null);
			else
				matchMapping.put(match, IbexMatch.newMatch(match));

		if (operationalStrategy.getBlockedMatches() != null)
			operationalStrategy.getBlockedMatches().forEach((match, reason) -> {
				if (matchMapping.containsKey(match))
					matchMapping.get(match).setBlockingReason(reason);
				else {
					IbexMatch vMatch = IbexMatch.newMatch(match);
					vMatch.setBlockingReason(reason);
					matchMapping.put(match, vMatch);
				}
			});

		IbexMatch.finishMatchCreation();
		step++;
	}

	public void update(ObservableEvent pEventType, Object... pAdditionalInformation) {
		// ignore by default
	}

	public abstract IMatch chooseOneMatch(DataPackage pDataPackage);
}
