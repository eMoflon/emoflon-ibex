package org.emoflon.ibex.tgg.runtime.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;

import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.IMatchContainer;
import org.emoflon.ibex.tgg.runtime.matches.container.LocalCCMatchContainer;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.runtime.updatepolicy.IUpdatePolicy;

public class LocalCC extends CC {

	public LocalCC(IbexOptions options) throws IOException {
		super(options);
	}

	protected LocalCC(IbexOptions options, IUpdatePolicy policy) throws IOException {
		super(options, policy);
	}

	@Override
	protected void initializeAdditionalModules(IbexOptions options) throws IOException {
		super.initializeAdditionalModules(options);
		matchHandler.handleConsistencyMatches(operationalMatchContainer);
	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new LocalCCMatchContainer(options, (IbexGreenInterpreter) greenInterpreter);
	}

	@Override
	protected void prepareMarkerCreation(IGreenPattern greenPattern, ITGGMatch comatch, String ruleName) {
		LocalCCMatchContainer mContainer = (LocalCCMatchContainer) operationalMatchContainer;
		idToMatch.put(idCounter, comatch);
		matchToWeight.put(idCounter, this.getWeightForMatch(comatch, ruleName));
		matchIdToRuleName.put(idCounter, ruleName);

		getGreenNodes(comatch, ruleName).forEach(e -> {
			if (!nodeToMarkingMatches.containsKey(e))
				nodeToMarkingMatches.put(e, cfactory.createIntSet());
			nodeToMarkingMatches.get(e).add(idCounter);
		});

		getGreenEdges(comatch, ruleName).forEach(e -> {
			if (!edgeToMarkingMatches.containsKey(e)) {
				edgeToMarkingMatches.put(e, cfactory.createIntSet());
			}
			edgeToMarkingMatches.get(e).add(idCounter);
		});

		getBlackNodes(comatch, ruleName).forEach(e -> {
			if (mContainer.isMarked(e))
				return;

			if (!contextNodeToNeedingMatches.containsKey(e))
				contextNodeToNeedingMatches.put(e, cfactory.createIntSet());
			contextNodeToNeedingMatches.get(e).add(idCounter);
		});

		getBlackEdges(comatch, ruleName).forEach(e -> {
			if (mContainer.isEdgeMarked(e))
				return;

			if (!contextEdgeToNeedingMatches.containsKey(e)) {
				contextEdgeToNeedingMatches.put(e, cfactory.createIntSet());
			}
			contextEdgeToNeedingMatches.get(e).add(idCounter);
		});

		matchToContextNodes.put(idCounter, cfactory.createObjectSet());
		matchToContextNodes.get(idCounter).addAll(getBlackNodes(comatch, ruleName));

		matchToContextEdges.put(idCounter, cfactory.createEMFEdgeHashSet());
		matchToContextEdges.get(idCounter).addAll(getBlackEdges(comatch, ruleName));

		idCounter++;
	}

	@Override
	public void terminate() {
		idToMatch = cfactory.createIntToObjectHashMap();
		edgeToMarkingMatches = cfactory.createEMFEdgeHashMap();
		nodeToMarkingMatches = cfactory.createObjectToObjectHashMap();
		nameCounter = 0;
		matchToContextNodes = cfactory.createIntToObjectHashMap();
		contextNodeToNeedingMatches = cfactory.createObjectToObjectHashMap();
		contextEdgeToNeedingMatches = cfactory.createEMFEdgeHashMap();
		matchToContextEdges = cfactory.createIntToObjectHashMap();
		matchToWeight = cfactory.createIntToDoubleMap();
		matchIdToRuleName = cfactory.createIntToObjectHashMap();
		idCounter = 1;
	}

}