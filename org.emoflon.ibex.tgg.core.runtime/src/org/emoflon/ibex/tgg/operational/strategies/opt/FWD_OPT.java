package org.emoflon.ibex.tgg.operational.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.engine.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.util.config.IbexOptions;

import language.TGGRuleCorr;
import language.TGGRuleNode;

public class FWD_OPT extends OPT {

	public FWD_OPT(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	protected void wrapUp() {
		ArrayList<EObject> objectsToDelete = new ArrayList<EObject>();
		for (int v : chooseTGGRuleApplications()) {
			int id = v < 0 ? -v : v;
			ITGGMatch comatch = idToMatch.get(id);
			if (v < 0) {
				for (TGGRuleCorr createdCorr : greenFactories.get(matchIdToRuleName.get(id)).getGreenCorrNodesInRule())
					objectsToDelete.add((EObject) comatch.get(createdCorr.getName()));

				for (TGGRuleNode createdTrgNode : greenFactories.get(matchIdToRuleName.get(id)).getGreenTrgNodesInRule())
					objectsToDelete.add((EObject) comatch.get(createdTrgNode.getName()));

				objectsToDelete.addAll(getRuleApplicationNodes(comatch));
			}
		}

		EcoreUtil.deleteAll(objectsToDelete, true);
		consistencyReporter.initSrc();
	}

	@Override
	protected void prepareMarkerCreation(IGreenPattern greenPattern, ITGGMatch comatch, String ruleName) {
		idToMatch.put(idCounter, comatch);
		matchIdToRuleName.put(idCounter, ruleName);
		matchToWeight.put(idCounter, this.getWeightForMatch(comatch, ruleName));

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
			if (!contextNodeToNeedingMatches.containsKey(e))
				contextNodeToNeedingMatches.put(e, cfactory.createIntSet());
			contextNodeToNeedingMatches.get(e).add(idCounter);
		});

		getBlackEdges(comatch, ruleName).forEach(e -> {
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
	public double getDefaultWeightForMatch(IMatch comatch, String ruleName) {
		return greenFactories.get(ruleName).getGreenSrcEdgesInRule().size()
				+ greenFactories.get(ruleName).getGreenSrcNodesInRule().size();
	}

	public void forward() throws IOException {
		run();
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return Collections.singleton(PatternType.FWD_OPT);
	}
}
