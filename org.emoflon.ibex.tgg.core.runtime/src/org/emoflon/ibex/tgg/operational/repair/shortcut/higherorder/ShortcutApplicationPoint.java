package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;

public class ShortcutApplicationPoint extends RepairApplicationPoint {

	private final PatternType propagationMatchType;
	private final List<PrecedenceNode> originalNodes;
	private final Set<List<PrecedenceNode>> setOfReplacingNodes;

	private final Map<PrecedenceNode, Map<PrecedenceNode, Set<Object>>> original2replacingNodesOverlaps;
	final Set<ShortcutApplicationPoint> subsetScApplications;

	ShortcutApplicationPoint(PrecedenceNode applNode, List<PrecedenceNode> originalNodes, Set<List<PrecedenceNode>> setOfReplacingNodes,
			PatternType propagationMatchType) {
		super(applNode.getMatch(), getRepairType(propagationMatchType));

		this.propagationMatchType = propagationMatchType;
		this.originalNodes = Objects.requireNonNull(originalNodes);
		this.setOfReplacingNodes = Objects.requireNonNull(setOfReplacingNodes);

		this.original2replacingNodesOverlaps = new HashMap<>();

		this.subsetScApplications = new HashSet<>();
	}

	private static PatternType getRepairType(PatternType propagationMatchType) {
		return switch (propagationMatchType) {
			case SRC -> PatternType.FWD;
			case TRG -> PatternType.BWD;
			default -> throw new IllegalArgumentException("Unexpected value: " + propagationMatchType);
		};
	}

	public void addOverlaps(PrecedenceNode originalNode, Map<PrecedenceNode, Set<Object>> replacingNodes2overlappingElts) {
		original2replacingNodesOverlaps.put(originalNode, replacingNodes2overlappingElts);
	}

	public PatternType getPropagationMatchType() {
		return propagationMatchType;
	}

	public List<PrecedenceNode> getOriginalNodes() {
		return originalNodes;
	}

	public Set<List<PrecedenceNode>> getSetOfReplacingNodes() {
		return setOfReplacingNodes;
	}

	public Map<PrecedenceNode, Map<PrecedenceNode, Set<Object>>> getOriginal2replacingNodesOverlaps() {
		return original2replacingNodesOverlaps;
	}

}
