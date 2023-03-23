package org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.ClassifiedMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;

public class ShortcutApplicationPointFinder {

	private final PrecedenceGraph pg;
	private final MatchClassifier mc;
	
	private Map<ITGGMatch, PatternType> followUpRepairTypes;

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
	
	public Set<ShortcutApplicationPoint> searchForShortcutApplications(Map<ITGGMatch, PatternType> followUpRepairTypes) {
		this.followUpRepairTypes = followUpRepairTypes;
		return searchForShortcutApplications();
	}

	private ShortcutApplicationPoint exploreShortcutApplicationPoint(ClassifiedMatch classifiedMatch) {
		PrecedenceNode applNode = pg.getNode(classifiedMatch.getMatch());

		PatternType propagationType = filterMatchesAndCalcPropagationType(classifiedMatch);
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
			if (propagationType == filterMatchesAndCalcPropagationType(classifiedAct)) {
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

	private PatternType filterMatchesAndCalcPropagationType(ClassifiedMatch classifiedMatch) {
		// TODO differentiate between application node and subsequent node for deletion type
		// -> important: then, subset application points won't work
		
		boolean srcViolations = false;
		boolean trgViolations = false;

		// deletions:
		DeletionType deletionType = classifiedMatch.getDeletionType();
		if (DeletionType.propFWDCandidates.contains(deletionType))
			srcViolations = true;
		else if (DeletionType.propBWDCandidates.contains(deletionType))
			trgViolations = true;
		else if (deletionType != DeletionType.NOTHING)
			return null;

		// filter NACs & inplace attributes:
		if (classifiedMatch.getFilterNacViolations().containsValue(DomainType.SOURCE)
				|| classifiedMatch.getInplaceAttrChanges().containsValue(DomainType.SOURCE)) {
			if (trgViolations)
				return null;
			srcViolations = true;
		}
		if (classifiedMatch.getFilterNacViolations().containsValue(DomainType.TARGET)
				|| classifiedMatch.getInplaceAttrChanges().containsValue(DomainType.TARGET)) {
			if (srcViolations)
				return null;
			trgViolations = true;
		}

		if (srcViolations)
			return PatternType.SRC;
		if (trgViolations)
			return PatternType.TRG;
		
		if (followUpRepairTypes != null) {
			PatternType followUpRepairType = followUpRepairTypes.get(classifiedMatch.getMatch());
			if (followUpRepairType != null)
				return switch (followUpRepairType) {
					case FWD -> PatternType.SRC;
					case BWD -> PatternType.TRG;
					default -> null;
				};
		}
		return null;
	}

	private void processSubsetShortcutApplications(Set<ShortcutApplicationPoint> shortcutApplications) {
		Map<PrecedenceNode, ShortcutApplicationPoint> applNode2scApplication = shortcutApplications.stream() //
				.collect(Collectors.toMap(a -> pg.getNode(a.getApplicationMatch()), a -> a));

		Set<ShortcutApplicationPoint> subsetScApplications = new HashSet<>();
		for (ShortcutApplicationPoint shortcutApplication : shortcutApplications) {
			for (int i = 0; i < shortcutApplication.getOriginalNodes().size() - 1; i++) {
				PrecedenceNode originalNode = shortcutApplication.getOriginalNodes().get(i);
				if (applNode2scApplication.containsKey(originalNode)) {
					ShortcutApplicationPoint subsetScAppl = applNode2scApplication.get(originalNode);
					shortcutApplication.subsetScApplications.add(subsetScAppl);
					subsetScApplications.add(subsetScAppl);

					// add missing overlaps
					Map<PrecedenceNode, Set<Object>> replacingNodeOverlaps = subsetScAppl.getOriginal2replacingNodesOverlaps().get(originalNode);
					shortcutApplication.addOverlaps(originalNode, replacingNodeOverlaps);
				}
			}
		}
		shortcutApplications.removeAll(subsetScApplications);
	}

}
