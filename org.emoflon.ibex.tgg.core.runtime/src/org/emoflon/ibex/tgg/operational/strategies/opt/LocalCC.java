package org.emoflon.ibex.tgg.operational.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.LocalCCMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import language.TGGRuleCorr;

public class LocalCC extends CC {

	public LocalCC(IbexOptions options) throws IOException {
		super(options);
	}

	protected LocalCC(IbexOptions options, IUpdatePolicy policy) throws IOException {
		super(options, policy);
	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new LocalCCMatchContainer(options, (IbexGreenInterpreter) greenInterpreter);
	}
	
	@Override
	protected void addConsistencyMatch(ITGGMatch match) {
		operationalMatchContainer.addMatch(match);
		super.addConsistencyMatch(match);
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
			if(mContainer.isMarked(e))
				return;
			
			if (!contextNodeToNeedingMatches.containsKey(e))
				contextNodeToNeedingMatches.put(e, cfactory.createIntSet());
			contextNodeToNeedingMatches.get(e).add(idCounter);
		});

		getBlackEdges(comatch, ruleName).forEach(e -> {
			if(mContainer.isEdgeMarked(e))
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
}