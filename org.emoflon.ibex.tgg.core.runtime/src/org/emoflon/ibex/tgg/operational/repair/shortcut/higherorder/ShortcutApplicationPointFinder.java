package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.ClassifiedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;

import language.DomainType;

public class ShortcutApplicationPointFinder {

	private final PrecedenceGraph pg;
	private final MatchClassifier mc;

	public ShortcutApplicationPointFinder(PrecedenceGraph pg, MatchClassifier mc) {
		this.pg = pg;
		this.mc = mc;
	}

	public Set<ShortcutApplicationPoint> searchForShortcutApplications() {
		mc.clearAndClassifyAllByNodes(pg.getBrokenNodes());

		Set<ShortcutApplicationPoint> results = new HashSet<>();
		for (ClassifiedMatch classifiedMatch : mc.getAllClassifiedMatches()) {
			ShortcutApplicationPoint shortcutApplication = exploreShortcutApplicationPoint(classifiedMatch);
			if (shortcutApplication != null)
				results.add(shortcutApplication);
		}

		processSubsetShortcutApplications(results);

		return results;
	}

	private ShortcutApplicationPoint exploreShortcutApplicationPoint(ClassifiedMatch classifiedMatch) {
		PrecedenceNode applNode = pg.getNode(classifiedMatch.getMatch());

		PatternType propagationType = calcPropagationType(classifiedMatch);
		if (propagationType == null)
			return null;

		Map<PrecedenceNode, Set<Object>> overlappingNodes = pg.findOverlappingNodes(applNode, propagationType);

		if (overlappingNodes.isEmpty())
			return null;

		LinkedList<PrecedenceNode> originalNodes = new LinkedList<>();
		originalNodes.addFirst(applNode);
		applNode.forAllRequires((act, pre) -> {
			if (act.getMatch().getType() != PatternType.CONSISTENCY)
				return false;

			ClassifiedMatch classifiedAct = mc.get(act.getMatch());
			if (propagationType == calcPropagationType(classifiedAct)) {
				originalNodes.addFirst(act);
				return true;
			}

			return false;
		});

		Set<List<PrecedenceNode>> setOfReplacingNodes = new HashSet<>();
		for (PrecedenceNode overlappingNode : overlappingNodes.keySet()) {
			LinkedList<PrecedenceNode> replacingNodes = new LinkedList<>();
			replacingNodes.addFirst(overlappingNode);
			overlappingNode.forAllRequires((act, pre) -> {
				if (act.getMatch().getType() != propagationType)
					return false;

				replacingNodes.addFirst(act);
				return true;
			});
			setOfReplacingNodes.add(replacingNodes);
		}

		ShortcutApplicationPoint shortcutApplPoint = new ShortcutApplicationPoint(applNode, originalNodes, setOfReplacingNodes, propagationType);
		shortcutApplPoint.addOverlaps(applNode, overlappingNodes);
		return shortcutApplPoint;
	}

	private PatternType calcPropagationType(ClassifiedMatch classifiedMatch) {
		DeletionType deletionType = classifiedMatch.getDeletionType();
		if (DeletionType.propFWDCandidates.contains(deletionType) || classifiedMatch.getFilterNacViolations().containsValue(DomainType.SRC))
			return PatternType.SRC;
		else if (DeletionType.propBWDCandidates.contains(deletionType) || classifiedMatch.getFilterNacViolations().containsValue(DomainType.TRG))
			return PatternType.TRG;
		return null;
	}

	private void processSubsetShortcutApplications(Set<ShortcutApplicationPoint> shortcutApplications) {
		Map<PrecedenceNode, ShortcutApplicationPoint> upperNode2scApplication = shortcutApplications.stream() //
				.collect(Collectors.toMap(a -> a.originalNodes.get(0), a -> a));

		Set<ShortcutApplicationPoint> subsetScApplications = new HashSet<>();
		for (ShortcutApplicationPoint shortcutApplication : shortcutApplications) {
			for (int i = 1; i < shortcutApplication.originalNodes.size(); i++) {
				PrecedenceNode originalNode = shortcutApplication.originalNodes.get(i);
				if (upperNode2scApplication.containsKey(originalNode)) {
					ShortcutApplicationPoint subsetScAppl = upperNode2scApplication.get(originalNode);
					shortcutApplication.subsetScApplications.add(subsetScAppl);
					subsetScApplications.add(subsetScAppl);

					// add missing overlaps
					Map<PrecedenceNode, Set<Object>> replacingNodeOverlaps = subsetScAppl.original2replacingNodesOverlaps.get(originalNode);
					shortcutApplication.addOverlaps(originalNode, replacingNodeOverlaps);
				}
			}
		}
		shortcutApplications.removeAll(subsetScApplications);
	}

	public class ShortcutApplicationPoint {
		public final PrecedenceNode applNode;
		public final List<PrecedenceNode> originalNodes;
		public final Set<List<PrecedenceNode>> setOfReplacingNodes;
		public final PatternType propagationType;

		public final Map<PrecedenceNode, Map<PrecedenceNode, Set<Object>>> original2replacingNodesOverlaps;
		final Set<ShortcutApplicationPoint> subsetScApplications;

		ShortcutApplicationPoint(PrecedenceNode applNode, List<PrecedenceNode> originalNodes, Set<List<PrecedenceNode>> setOfReplacingNodes,
				PatternType propagationType) {
			this.applNode = applNode;
			this.originalNodes = originalNodes;
			this.setOfReplacingNodes = setOfReplacingNodes;
			this.propagationType = propagationType;

			this.original2replacingNodesOverlaps = new HashMap<>();

			this.subsetScApplications = new HashSet<>();
		}

		public void addOverlaps(PrecedenceNode originalNode, Map<PrecedenceNode, Set<Object>> replacingNodes2overlappingElts) {
			original2replacingNodesOverlaps.put(originalNode, replacingNodes2overlappingElts);
		}
	}

}
