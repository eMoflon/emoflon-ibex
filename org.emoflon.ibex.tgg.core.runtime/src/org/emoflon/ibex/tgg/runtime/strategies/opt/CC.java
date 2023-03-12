package org.emoflon.ibex.tgg.runtime.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.common.engine.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

public class CC extends OPT {

	public CC(IbexOptions options) throws IOException {
		super(options);
	}

	protected CC(IbexOptions options, IUpdatePolicy policy) throws IOException {
		super(options, policy);
	}

	@Override
	public double getDefaultWeightForMatch(IMatch comatch, String ruleName) {
		var operationalRule = options.tgg.ruleHandler().getOperationalRule(ruleName);
		return operationalRule.getCreateSource().getNodes().size() + 
				operationalRule.getCreateSource().getEdges().size() + 
				operationalRule.getCreateTarget().getNodes().size() + 
				operationalRule.getCreateTarget().getEdges().size();
	}

	@Override
	protected boolean processOneOperationalRuleMatch() {
		if (operationalMatchContainer.isEmpty())
			return false;

		ITGGMatch match = chooseOneMatch();
		String ruleName = match.getRuleName();

		if (ruleName == null) {
			matchHandler.removeOperationalMatch(match);
			return true;
		}

		processOperationalRuleMatch(ruleName, match);
		matchHandler.removeOperationalMatch(match);

		return true;
	}

	@Override
	protected void wrapUp() {
		Set<EObject> objectsToDelete = new HashSet<EObject>();

		for (int v : chooseTGGRuleApplications()) {
			int id = v < 0 ? -v : v;
			ITGGMatch comatch = idToMatch.get(id);
			TGGOperationalRule operationalRule = options.tgg.ruleHandler().getOperationalRule(matchIdToRuleName.get(id));
			if (v < 0) {
				for (IBeXNode createdCorr : operationalRule.getCreateCorrespondence().getNodes())
					objectsToDelete.add((EObject) comatch.get(createdCorr.getName()));

				objectsToDelete.add(comatch.getRuleApplicationNode());
			} else
				processValidMatch(comatch);
		}

		EMFManipulationUtils.deleteNodes(objectsToDelete, true);
		consistencyReporter.init(this);
	}

	@Override
	protected void prepareMarkerCreation(TGGOperationalRule operationalRule, ITGGMatch comatch, String ruleName) {
		idToMatch.put(idCounter, comatch);
		matchToWeight.put(idCounter, getWeightForMatch(comatch, ruleName));
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

	/**
	 * Processes a valid <code>ITGGMatch</code>.
	 * <p>
	 * <i>Note: Default implementation is no-op. Override this method if needed.</i>
	 * </p>
	 * 
	 * @param match Match to process
	 */
	protected void processValidMatch(ITGGMatch match) { // NO-OP
	}

	public boolean modelsAreConsistent() {
		return getInconsistentSrcNodes().size() + //
				getInconsistentTrgNodes().size() + //
				getInconsistentSrcEdges().size() + //
				getInconsistentTrgEdges().size() == 0;
	}

	public Collection<EObject> getInconsistentSrcNodes() {
		return consistencyReporter.getInconsistentSrcNodes();
	}

	public Collection<EObject> getInconsistentTrgNodes() {
		return consistencyReporter.getInconsistentTrgNodes();
	}

	public Collection<EMFEdge> getInconsistentSrcEdges() {
		return consistencyReporter.getInconsistentSrcEdges();
	}

	public Collection<EMFEdge> getInconsistentTrgEdges() {
		return consistencyReporter.getInconsistentTrgEdges();
	}

	public String generateConsistencyReport() {
		String result = "";
		if (modelsAreConsistent())
			result += "Your models are consistent";
		else {
			result += "Your models are inconsistent. The following elements are not part of a consistent triple:";
			result += "\n" + "Source nodes:" + "\n";
			result += String.join("\n",
					getInconsistentSrcNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Source edges:" + "\n";
			result += String.join("\n",
					getInconsistentSrcEdges().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Target nodes:" + "\n";
			result += String.join("\n",
					getInconsistentTrgNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Target edges:" + "\n";
			result += String.join("\n",
					getInconsistentTrgEdges().stream().map(n -> n.toString()).collect(Collectors.toSet()));
		}
		return result;
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return new HashSet<>(Arrays.asList(PatternType.CC, PatternType.GENForCC));
	}
}