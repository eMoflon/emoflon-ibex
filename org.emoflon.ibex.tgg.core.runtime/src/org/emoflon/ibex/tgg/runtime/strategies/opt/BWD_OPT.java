package org.emoflon.ibex.tgg.runtime.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.engine.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

public class BWD_OPT extends OPT {

	public BWD_OPT(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	protected void wrapUp() {
		ArrayList<EObject> objectsToDelete = new ArrayList<EObject>();
		for (int v : chooseTGGRuleApplications()) {
			int id = v < 0 ? -v : v;
			ITGGMatch comatch = idToMatch.get(id);
			TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(matchIdToRuleName.get(id));

			if (v < 0) {
				for (IBeXNode createdCorr : operationalRule.getCreateCorrespondence().getNodes())
					objectsToDelete.add((EObject) comatch.get(createdCorr.getName()));

				for (IBeXNode createdSrcNode : operationalRule.getCreateSource().getNodes())
					objectsToDelete.add((EObject) comatch.get(createdSrcNode.getName()));

				objectsToDelete.addAll(getRuleApplicationNodes(comatch));
			}
		}

		EcoreUtil.deleteAll(objectsToDelete, true);
		consistencyReporter.initTrg();
	}

	@Override
	protected void prepareMarkerCreation(TGGOperationalRule operationalRule, ITGGMatch comatch, String ruleName) {
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
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(ruleName);

		return operationalRule.getCreateTarget().getNodes().size() +
				operationalRule.getCreateTarget().getEdges().size();
	}

	public void backward() throws IOException {
		run();
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return Collections.singleton(PatternType.BWD_OPT);
	}
}
