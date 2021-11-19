package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.ClassifiedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;

import language.DomainType;

public class HigherOrderShortcutRepairStrategy {

	private final PrecedenceGraph pg;
	private final MatchClassifier mc;
	private final HigherOrderTGGRuleFactory factory;

	public HigherOrderShortcutRepairStrategy(PrecedenceGraph pg, MatchClassifier mc, TGGMatchUtilProvider mup) {
		this.pg = pg;
		this.mc = mc;
		this.factory = new HigherOrderTGGRuleFactory(pg, mup);
	}

	public void repair() {
		Set<PotentialShortcutApplication> shortcutApplications = searchForShortcutApplications();

		// TODO

	}

	private Set<PotentialShortcutApplication> searchForShortcutApplications() {
		mc.clearAndClassifyAllByNodes(pg.getBrokenNodes());

		Set<PotentialShortcutApplication> result = new HashSet<>();
		for (ClassifiedMatch classifiedMatch : mc.getAllClassifiedMatches()) {
			PrecedenceNode node = pg.getNode(classifiedMatch.getMatch());

			PatternType propagationType = calcPropagationType(classifiedMatch);
			if (propagationType == null)
				continue;

			Set<PrecedenceNode> overlappingNodes = pg.findOverlappingNodes(node, propagationType);

			if (overlappingNodes.isEmpty())
				continue;

			List<PrecedenceNode> originalNodes = new LinkedList<>();
			originalNodes.add(node);
			node.forAllRequires((act, pre) -> {
				if (act.getMatch().getType() != PatternType.CONSISTENCY)
					return false;

				ClassifiedMatch classifiedAct = mc.get(act.getMatch());
				if (propagationType == calcPropagationType(classifiedAct)) {
					originalNodes.add(act);
					return true;
				}

				return false;
			});

			Set<List<PrecedenceNode>> setOfReplacingNodes = new HashSet<>();
			for (PrecedenceNode overlappingNode : overlappingNodes) {
				List<PrecedenceNode> replacingNodes = new LinkedList<>();
				replacingNodes.add(overlappingNode);
				overlappingNode.forAllRequires((act, pre) -> {
					if (act.getMatch().getType() != propagationType)
						return false;

					replacingNodes.add(act);
					return true;
				});
			}

			result.add(new PotentialShortcutApplication(originalNodes, setOfReplacingNodes));
		}

		return result;
	}

	private PatternType calcPropagationType(ClassifiedMatch classifiedMatch) {
		DeletionType deletionType = classifiedMatch.getDeletionType();
		if (DeletionType.propFWDCandidates.contains(deletionType) || classifiedMatch.getFilterNacViolations().containsValue(DomainType.SRC))
			return PatternType.SRC;
		else if (DeletionType.propBWDCandidates.contains(deletionType) || classifiedMatch.getFilterNacViolations().containsValue(DomainType.TRG))
			return PatternType.TRG;
		return null;
	}

	private class PotentialShortcutApplication {
		final List<PrecedenceNode> originalNodes;
		final Set<List<PrecedenceNode>> setOfReplacingNodes;

		PotentialShortcutApplication(List<PrecedenceNode> originalNodes, Set<List<PrecedenceNode>> setOfReplacingNodes) {
			this.originalNodes = originalNodes;
			this.setOfReplacingNodes = setOfReplacingNodes;
		}
	}

}
