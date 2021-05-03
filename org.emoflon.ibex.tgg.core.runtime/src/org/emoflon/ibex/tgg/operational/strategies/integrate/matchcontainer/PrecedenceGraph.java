package org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.operational.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.benchmark.Times;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.SimpleTGGMatch;
import org.emoflon.ibex.tgg.operational.matches.TGGMatchParameterOrderProvider;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import language.DomainType;
import language.TGGRuleNode;

public class PrecedenceGraph implements TimeMeasurable {

	protected final Times times = new Times();

	protected final PropagatingOperationalStrategy strategy;

	protected Map<PrecedenceNode, Collection<Object>> requires = Collections.synchronizedMap(cfactory.createObjectToObjectHashMap());
	protected Map<Object, LockedSet<PrecedenceNode>> requiredBy = Collections.synchronizedMap(cfactory.createObjectToObjectHashMap());
	protected Map<PrecedenceNode, Collection<Object>> translates = Collections.synchronizedMap(cfactory.createObjectToObjectHashMap());
	protected Map<Object, LockedSet<PrecedenceNode>> translatedBy = Collections.synchronizedMap(cfactory.createObjectToObjectHashMap());

	//// Precedence Graph ////
	protected Set<PrecedenceNode> nodes = Collections.synchronizedSet(cfactory.createObjectSet());
	protected Map<ITGGMatch, PrecedenceNode> match2node = Collections.synchronizedMap(cfactory.createObjectToObjectHashMap());

	//// Caching ////
	protected Set<PrecedenceNode> brokenNodes = Collections.synchronizedSet(cfactory.createObjectSet());
	protected Set<PrecedenceNode> implicitBrokenNodes = Collections.synchronizedSet(cfactory.createObjectSet());

	protected Set<PrecedenceNode> consNodes = Collections.synchronizedSet(cfactory.createObjectSet());
	protected Set<PrecedenceNode> srcNodes = Collections.synchronizedSet(cfactory.createObjectSet());
	protected Set<PrecedenceNode> trgNodes = Collections.synchronizedSet(cfactory.createObjectSet());

	protected Map<ITGGMatch, SrcTrgMatchContainer> consMatch2srcTrgMatches = Collections.synchronizedMap(cfactory.createObjectToObjectHashMap());
	protected Map<ITGGMatch, ITGGMatch> srcTrgMatch2consMatch = Collections.synchronizedMap(cfactory.createObjectToObjectHashMap());
	protected Set<ITGGMatch> pendingSrcTrgMatches = Collections.synchronizedSet(cfactory.createObjectSet());

	public PrecedenceGraph(PropagatingOperationalStrategy strategy) {
		this.strategy = strategy;
		TimeRegistry.register(this);
	}

	public void notifyAddedMatch(ITGGMatch match) {
		Timer.start();

		notifyAddedOneMatch(match);

		times.addTo("notifyAdded", Timer.stop());
	}

	public void notifyAddedMatches(Collection<ITGGMatch> matches) {
		Timer.start();

		Set<ITGGMatch> consMatches = cfactory.createObjectSet();
		Set<ITGGMatch> srcTrgMatches = cfactory.createObjectSet();
		for (ITGGMatch match : matches) {
			if (match.getType() == PatternType.CONSISTENCY)
				consMatches.add(match);
			else if (match.getType() == PatternType.SRC || match.getType() == PatternType.TRG)
				srcTrgMatches.add(match);
		}
		consMatches.parallelStream().forEach(this::addConsMatch);
		srcTrgMatches.parallelStream().forEach(this::addSrcTrgMatch);

		Timer.start();
		consMatches.stream().forEach(this::killRedundantSrcTrgMatches);
		times.addTo("notifyAdded:killRedMatches", Timer.stop());

		times.addTo("notifyAdded", Timer.stop());
	}

	private void notifyAddedOneMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY) {
			addConsMatch(match);
			killRedundantSrcTrgMatches(match);
		} else if (match.getType() == PatternType.SRC || match.getType() == PatternType.TRG)
			addSrcTrgMatch(match);
	}

	private void addConsMatch(ITGGMatch match) {
		if (!handleRestoredConsistencyMatch(match)) {
			addMatch(match);
			if (!consMatch2srcTrgMatches.containsKey(match))
				generateSrcTrgMatches(match);
		}
	}

	private void addSrcTrgMatch(ITGGMatch match) {
		if (checkConsistencyOverlap(match))
			pendingSrcTrgMatches.add(match);
		else
			addMatch(match);
	}

	public void notifyRemovedMatch(ITGGMatch match) {
		Timer.start();

		notifyRemovedOneMatch(match);

		times.addTo("notifyRemoved", Timer.stop());
	}

	public void notifyRemovedMatches(Collection<ITGGMatch> matches) {
		Timer.start();

		matches.stream().forEach(this::notifyRemovedOneMatch);

		times.addTo("notifyRemoved", Timer.stop());
	}

	private void notifyRemovedOneMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY) {
			handleBrokenConsistencyMatch(match);
		} else if (match.getType() == PatternType.SRC || match.getType() == PatternType.TRG) {
			if (match2node.containsKey(match))
				removeMatch(match, getNode(match));
			else
				pendingSrcTrgMatches.remove(match);
		}
	}

	public void clearBrokenMatches() {
		brokenNodes.forEach(node -> removeMatch(node.getMatch(), node));
		brokenNodes.clear();
	}

	public void removeMatch(ITGGMatch match) {
		Timer.start();

		PrecedenceNode node = getNode(match);
		removeMatch(match, node);
		brokenNodes.remove(node);

		times.addTo("removeMatch", Timer.stop());
	}

	public PrecedenceNode getNode(ITGGMatch match) {
		PrecedenceNode node = match2node.get(match);
		if (node == null)
			throw new RuntimeException("No precedence node found for this match!");
		return node;
	}

	public Collection<PrecedenceNode> getNodes() {
		return nodes;
	}

	public Collection<PrecedenceNode> getBrokenNodes() {
		return brokenNodes;
	}

	public Collection<PrecedenceNode> getImplicitBrokenNodes() {
		return implicitBrokenNodes;
	}

	public Set<PrecedenceNode> getConsistencyNodes() {
		return consNodes;
	}

	public Set<PrecedenceNode> getSourceNodes() {
		return srcNodes;
	}

	public Set<PrecedenceNode> getTargetNodes() {
		return trgNodes;
	}

	public Collection<PrecedenceNode> getNodesTranslating(Object elt) {
		return translatedBy.getOrDefault(elt, new LockedSet<>());
	}

	public boolean hasAnyConsistencyOverlap(PrecedenceNode srcTrgNode) {
		ITGGMatch srcTrgMatch = srcTrgNode.getMatch();

		IGreenPatternFactory gFactory = strategy.getGreenFactory(srcTrgMatch.getRuleName());
		Collection<Object> translatedElts = cfactory.createObjectSet();
		if (srcTrgMatch.getType() == PatternType.SRC) {
			gFactory.getGreenSrcNodesInRule().forEach(n -> translatedElts.add(srcTrgMatch.get(n.getName())));
			gFactory.getGreenSrcEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(srcTrgMatch, e)));
		} else if (srcTrgMatch.getType() == PatternType.TRG) {
			gFactory.getGreenTrgNodesInRule().forEach(n -> translatedElts.add(srcTrgMatch.get(n.getName())));
			gFactory.getGreenTrgEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(srcTrgMatch, e)));
		}

		return translatedElts.parallelStream() //
				.flatMap(elt -> this.getNodesTranslating(elt).stream()) //
				.anyMatch(n -> n.getMatch().getType() == PatternType.CONSISTENCY);
	}

	private void addMatch(ITGGMatch match) {
		IGreenPatternFactory gFactory = strategy.getGreenFactory(match.getRuleName());

		Collection<Object> requiredElts = cfactory.createObjectSet();
		Collection<Object> translatedElts = cfactory.createObjectSet();

		if (match.getType() != PatternType.TRG) {
			gFactory.getBlackSrcNodesInRule().forEach(n -> requiredElts.add(match.get(n.getName())));
			gFactory.getBlackSrcEdgesInRule().forEach(e -> requiredElts.add(getRuntimeEdge(match, e)));
			gFactory.getGreenSrcNodesInRule().forEach(n -> translatedElts.add(match.get(n.getName())));
			gFactory.getGreenSrcEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(match, e)));
		}
		if (match.getType() != PatternType.SRC) {
			gFactory.getBlackTrgNodesInRule().forEach(n -> requiredElts.add(match.get(n.getName())));
			gFactory.getBlackTrgEdgesInRule().forEach(e -> requiredElts.add(getRuntimeEdge(match, e)));
			gFactory.getGreenTrgNodesInRule().forEach(n -> translatedElts.add(match.get(n.getName())));
			gFactory.getGreenTrgEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(match, e)));
		}

		// Create node
		PrecedenceNode node = createNode(match);

		// Register elements
		for (Object elt : requiredElts) {
			requires.computeIfAbsent(node, x -> cfactory.createObjectSet());
			requiredBy.computeIfAbsent(elt, x -> new LockedSet<>());
			requires.get(node).add(elt);
			requiredBy.get(elt).add(node);
		}
		for (Object elt : translatedElts) {
			translates.computeIfAbsent(node, x -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(elt, x -> new LockedSet<>());
			translates.get(node).add(elt);
			translatedBy.get(elt).add(node);
		}

		// Create links
		for (Object elt : requiredElts) {
			LockedSet<PrecedenceNode> requiredNodes = translatedBy.get(elt);
			if (requiredNodes != null) {
				requiredNodes.lock_for_read();
				for (PrecedenceNode reqNode : requiredNodes)
					node.addRequires(reqNode);
				requiredNodes.unlock_for_read();
			}
		}
		for (Object elt : translatedElts) {
			LockedSet<PrecedenceNode> dependentNodes = requiredBy.get(elt);
			if (dependentNodes != null) {
				dependentNodes.lock_for_read();
				for (PrecedenceNode depNode : dependentNodes)
					node.addRequiredBy(depNode);
				dependentNodes.unlock_for_read();
			}
		}

		// check if implicit broken & mark node
		synchronized (node.getRequires()) {
			for (PrecedenceNode n : node.getRequires()) {
				if (n.isBroken() || !n.getToBeRolledBackBy().isEmpty())
					node.addToBeRolledBackBy(n);
			}
		}
		if (!node.getToBeRolledBackBy().isEmpty()) {
			implicitBrokenNodes.add(node);
			node.forAllRequiredBy((n, pre) -> {
				boolean wasIntact = n.getToBeRolledBackBy().isEmpty();
				n.addToBeRolledBackBy(pre);
				if (wasIntact && !n.isBroken())
					implicitBrokenNodes.add(n);
				return wasIntact;
			});
		}

		// caching
		switch (match.getType()) {
		case CONSISTENCY:
			consNodes.add(node);
			break;
		case SRC:
			srcNodes.add(node);
			break;
		case TRG:
			trgNodes.add(node);
			break;
		default:
			break;
		}

		LoggerConfig.log(LoggerConfig.log_pg(), () -> "Precedence graph: added " + match.getPatternName() + "(" + match.hashCode() + ")");
	}

	private boolean handleRestoredConsistencyMatch(ITGGMatch match) {
		PrecedenceNode node = match2node.get(match);
		if (node != null && node.isBroken()) {
			node.setBroken(false);
			brokenNodes.remove(node);
			updateImplicitBrokenNodes(node);
			return true;
		}
		return false;
	}

	private void removeMatch(ITGGMatch match, PrecedenceNode node) {
		deleteNode(match, node);

		Collection<Object> dependentElts = requires.remove(node);
		if (dependentElts != null) {
			for (Object elt : dependentElts) {
				if (requiredBy.containsKey(elt))
					requiredBy.get(elt).remove(node);
			}
		}
		Collection<Object> translatedElts = translates.remove(node);
		if (translatedElts != null) {
			for (Object elt : translatedElts) {
				if (translatedBy.containsKey(elt)) {
					translatedBy.get(elt).remove(node);
					if (translatedBy.get(elt).isEmpty())
						translatedBy.remove(elt);
				}
			}
		}

		if (match.getType() == PatternType.CONSISTENCY) {
			SrcTrgMatchContainer srcTrgMatches = consMatch2srcTrgMatches.get(match);
			if (srcTrgMatches.srcMatch != null && pendingSrcTrgMatches.contains(srcTrgMatches.srcMatch)) {
				pendingSrcTrgMatches.remove(srcTrgMatches.srcMatch);
				addMatch(srcTrgMatches.srcMatch);
			}
			if (srcTrgMatches.trgMatch != null && pendingSrcTrgMatches.contains(srcTrgMatches.trgMatch)) {
				pendingSrcTrgMatches.remove(srcTrgMatches.trgMatch);
				addMatch(srcTrgMatches.trgMatch);
			}
		}

		// caching
		switch (match.getType()) {
		case CONSISTENCY:
			consNodes.remove(node);
			break;
		case SRC:
			srcNodes.remove(node);
			break;
		case TRG:
			trgNodes.remove(node);
			break;
		default:
			break;
		}

		LoggerConfig.log(LoggerConfig.log_pg(), () -> "Precedence graph: removed " + match.getPatternName() + "(" + match.hashCode() + ")");
	}

	private PrecedenceNode createNode(ITGGMatch match) {
		PrecedenceNode node = new PrecedenceNode(match);
		nodes.add(node);
		match2node.put(match, node);
		return node;
	}

	private void deleteNode(ITGGMatch match, PrecedenceNode node) {
		node.clearToBeRolledBackBy();
		node.getRequiredBy().forEach(n -> n.removeToBeRolledBackBy(node));
		node.clearRequires();
		node.clearRequiredBy();

		nodes.remove(node);
		match2node.remove(match);
		brokenNodes.remove(node);
		implicitBrokenNodes.remove(node);
	}

	private void handleBrokenConsistencyMatch(ITGGMatch match) {
		if (!match2node.containsKey(match))
			return;
		PrecedenceNode node = getNode(match);
		node.setBroken(true);
		brokenNodes.add(node);
		implicitBrokenNodes.remove(node);
		updateImplicitBrokenNodes(node);
	}

	private void updateImplicitBrokenNodes(PrecedenceNode node) {
		Timer.start();

		if (node.getToBeRolledBackBy().isEmpty()) {
			if (node.isBroken()) {
				node.forAllRequiredByMultiVisit((n, pre) -> {
					boolean wasIntact = n.getToBeRolledBackBy().isEmpty();
					n.addToBeRolledBackBy(pre);
					if (wasIntact && !n.isBroken())
						implicitBrokenNodes.add(n);
					return wasIntact;
				});
			} else {
				node.forAllRequiredByMultiVisit((n, pre) -> {
					n.removeToBeRolledBackBy(pre);
					boolean isIntact = n.getToBeRolledBackBy().isEmpty();
					if (isIntact && !n.isBroken())
						implicitBrokenNodes.remove(n);
					return isIntact;
				});
			}
		}

		times.addTo("updateImplBrokenNodes", Timer.stop());
	}

	private void killRedundantSrcTrgMatches(ITGGMatch match) {
		SrcTrgMatchContainer srcTrgMatches = consMatch2srcTrgMatches.get(match);
		if (srcTrgMatches == null)
			srcTrgMatches = generateSrcTrgMatches(match);

		PrecedenceNode srcNode = match2node.get(srcTrgMatches.srcMatch);
		if (srcNode != null) {
			removeMatch(srcNode.getMatch(), srcNode);
			pendingSrcTrgMatches.add(srcNode.getMatch());
		}

		PrecedenceNode trgNode = match2node.get(srcTrgMatches.trgMatch);
		if (trgNode != null) {
			removeMatch(trgNode.getMatch(), trgNode);
			pendingSrcTrgMatches.add(trgNode.getMatch());
		}
	}

	private boolean checkConsistencyOverlap(ITGGMatch srcTrgMatch) {
		ITGGMatch consMatch = srcTrgMatch2consMatch.get(srcTrgMatch);
		if (consMatch != null && match2node.containsKey(consMatch))
			return true;

		return false;
	}

	private SrcTrgMatchContainer generateSrcTrgMatches(ITGGMatch consMatch) {
		if (!TGGMatchParameterOrderProvider.isInitialized())
			throw new RuntimeException("PrecedenceGraph: TGGMatchParameterOrderProvider must be initialized!");

		String ruleName = PatternSuffixes.removeSuffix(consMatch.getPatternName());
		Map<String, TGGRuleNode> param2node = TGGMatchParameterOrderProvider.getParam2NodeMap(ruleName);

		ITGGMatch srcMatch = new SimpleTGGMatch(ruleName + PatternSuffixes.SRC);
		if (srcMatch.getType() == null) {
			srcMatch = null;
		} else {
			for (String p : consMatch.getParameterNames()) {
				TGGRuleNode node = param2node.get(p);
				if (node != null && node.getDomainType() == DomainType.SRC)
					srcMatch.put(p, consMatch.get(p));
			}
		}

		ITGGMatch trgMatch = new SimpleTGGMatch(ruleName + PatternSuffixes.TRG);
		if (trgMatch.getType() == null) {
			trgMatch = null;
		} else {
			for (String p : consMatch.getParameterNames()) {
				TGGRuleNode node = param2node.get(p);
				if (node != null && node.getDomainType() == DomainType.TRG)
					trgMatch.put(p, consMatch.get(p));
			}
		}

		SrcTrgMatchContainer container = new SrcTrgMatchContainer(srcMatch, trgMatch);
		consMatch2srcTrgMatches.put(consMatch, container);
		srcTrgMatch2consMatch.put(srcMatch, consMatch);
		srcTrgMatch2consMatch.put(trgMatch, consMatch);
		return container;
	}

	@Override
	public Times getTimes() {
		return times;
	}

	private class SrcTrgMatchContainer {
		final ITGGMatch srcMatch;
		final ITGGMatch trgMatch;

		SrcTrgMatchContainer(ITGGMatch srcMatch, ITGGMatch trgMatch) {
			this.srcMatch = srcMatch;
			this.trgMatch = trgMatch;
		}
	}

}
