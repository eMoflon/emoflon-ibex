package org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceNode;

public class ShortcutApplicationPoint extends RepairApplicationPoint {

	private final PatternType propagationMatchType;

	private final List<PrecedenceNode> originalNodes;
	private final String originalNodesID;
	private final Map<String, List<PrecedenceNode>> id2replacingNodes;

	private final Map<PrecedenceNode, Map<PrecedenceNode, Set<Object>>> original2replacingNodesOverlaps;
	final Set<ShortcutApplicationPoint> subsetScApplications;

	ShortcutApplicationPoint(PrecedenceNode applNode, List<PrecedenceNode> originalNodes, Set<List<PrecedenceNode>> setOfReplacingNodes,
			PatternType propagationMatchType) {
		super(applNode.getMatch(), getRepairType(propagationMatchType));

		this.propagationMatchType = propagationMatchType;

		this.originalNodes = Objects.requireNonNull(originalNodes);
		this.originalNodesID = getNodeListIdentifier(originalNodes);
		this.id2replacingNodes = Objects.requireNonNull(setOfReplacingNodes).stream() //
				.collect(Collectors.toMap(s -> getNodeListIdentifier(s), s -> s));

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

	private static String getNodeListIdentifier(List<PrecedenceNode> nodes) {
		StringBuilder b = new StringBuilder();
		List<String> originalNodeNames = nodes.stream() //
				.map(n -> n.getMatch().getRuleName()) //
				.toList();
		b.append(String.join(">>>", originalNodeNames));
		return b.toString();
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

	public Collection<List<PrecedenceNode>> getSetOfReplacingNodes() {
		return id2replacingNodes.values();
	}

	public Map<PrecedenceNode, Map<PrecedenceNode, Set<Object>>> getOriginal2replacingNodesOverlaps() {
		return original2replacingNodesOverlaps;
	}

	public String getOriginalNodesID() {
		return originalNodesID;
	}

	public Map<String, List<PrecedenceNode>> getID2replacingNodes() {
		return id2replacingNodes;
	}

}
