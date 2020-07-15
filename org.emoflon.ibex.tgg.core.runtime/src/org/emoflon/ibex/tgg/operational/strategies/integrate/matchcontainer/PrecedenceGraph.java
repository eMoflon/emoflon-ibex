package org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.operational.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.benchmark.Times;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

public class PrecedenceGraph implements TimeMeasurable {

	protected final Times times = new Times();

	protected final PropagatingOperationalStrategy strategy;

	protected Map<PrecedenceNode, Collection<Object>> requires = cfactory.createObjectToObjectHashMap();
	protected Map<Object, Collection<PrecedenceNode>> requiredBy = cfactory.createObjectToObjectHashMap();
	protected Map<PrecedenceNode, Collection<Object>> translates = cfactory.createObjectToObjectHashMap();
	protected Map<Object, Collection<PrecedenceNode>> translatedBy = cfactory.createObjectToObjectHashMap();

	//// Precedence Graph ////
	protected Set<PrecedenceNode> nodes = cfactory.createObjectSet();
	protected Map<ITGGMatch, PrecedenceNode> match2node = cfactory.createObjectToObjectHashMap();
	protected Set<PrecedenceNode> brokenNodes = cfactory.createObjectSet();
	protected Set<PrecedenceNode> implicitBrokenNodes = Collections.synchronizedSet(cfactory.createObjectSet());
	
	protected Set<PrecedenceNode> nodesToBeUpdated = cfactory.createObjectSet();

	public PrecedenceGraph(PropagatingOperationalStrategy strategy) {
		this.strategy = strategy;
		TimeRegistry.register(this);
	}

	public void notifyAddedMatch(ITGGMatch match) {
		Timer.start();

		if (match.getType() == PatternType.CONSISTENCY)
			addConsistencyMatch(match);

		times.addTo("notifyAdded", Timer.stop());
	}

	public void notifyRemovedMatch(ITGGMatch match) {
		Timer.start();

		if (match.getType() == PatternType.CONSISTENCY)
			handleBrokenConsistencyMatch(match);

		times.addTo("notifyRemoved", Timer.stop());
	}
	
	public void updateImplicitBrokenNodes() {
		nodesToBeUpdated.parallelStream().forEach(this::updateImplicitBrokenNodes);
		for (Iterator<PrecedenceNode> it = implicitBrokenNodes.iterator(); it.hasNext();) {
			PrecedenceNode node = (PrecedenceNode) it.next();
			if(node.getRollbackCauses().isEmpty())
				it.remove();
		}
	}

	public void clearBrokenMatches() {
		brokenNodes.forEach(node -> removeConsistencyMatch(node.getMatch(), node));
		brokenNodes.clear();
	}

	public void removeMatch(ITGGMatch match) {
		PrecedenceNode node = getNode(match);
		removeConsistencyMatch(match, node);
		brokenNodes.remove(node);
	}

	public PrecedenceNode getNode(ITGGMatch match) {
		PrecedenceNode node = match2node.get(match);
		if (node == null)
			throw new RuntimeException("No precedence node found for consistency match!");
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

	private void addConsistencyMatch(ITGGMatch match) {
		PrecedenceNode node = match2node.get(match);
		if (node != null && node.isBroken()) {
			node.setBroken(false);
			brokenNodes.remove(node);
			if(!node.getRollbackCauses().isEmpty())
				implicitBrokenNodes.add(node);
			nodesToBeUpdated.add(node);
			return;
		}

		IGreenPatternFactory gFactory = strategy.getGreenFactory(match.getRuleName());

		Collection<Object> requiredElts = cfactory.createObjectSet();
		gFactory.getBlackSrcNodesInRule().forEach(n -> requiredElts.add(match.get(n.getName())));
		gFactory.getBlackTrgNodesInRule().forEach(n -> requiredElts.add(match.get(n.getName())));
		gFactory.getBlackSrcEdgesInRule().forEach(e -> requiredElts.add(getRuntimeEdge(match, e)));
		gFactory.getBlackTrgEdgesInRule().forEach(e -> requiredElts.add(getRuntimeEdge(match, e)));

		Collection<Object> translatedElts = cfactory.createObjectSet();
		gFactory.getGreenSrcNodesInRule().forEach(n -> translatedElts.add(match.get(n.getName())));
		gFactory.getGreenTrgNodesInRule().forEach(n -> translatedElts.add(match.get(n.getName())));
		gFactory.getGreenSrcEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(match, e)));
		gFactory.getGreenTrgEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(match, e)));

		// Create node
		node = createNode(match);

		// Register elements
		for (Object elt : requiredElts) {
			requires.computeIfAbsent(node, (x) -> cfactory.createObjectSet());
			requiredBy.computeIfAbsent(elt, (x) -> cfactory.createObjectSet());
			requires.get(node).add(elt);
			requiredBy.get(elt).add(node);
		}
		for (Object elt : translatedElts) {
			translates.computeIfAbsent(node, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(elt, (x) -> cfactory.createObjectSet());
			translates.get(node).add(elt);
			translatedBy.get(elt).add(node);
		}

		// Create links
		for (Object elt : requiredElts) {
			Collection<PrecedenceNode> requiredNode = translatedBy.get(elt);
			if (requiredNode != null) {
				for (PrecedenceNode reqNode : requiredNode)
					node.addRequires(reqNode);
			}
		}
		for (Object elt : translatedElts) {
			Collection<PrecedenceNode> dependentNodes = requiredBy.get(elt);
			if (dependentNodes != null) {
				for (PrecedenceNode depNode : dependentNodes)
					node.addRequiredBy(depNode);
			}
		}
	}

	private void removeConsistencyMatch(ITGGMatch match, PrecedenceNode node) {
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
	}

	private PrecedenceNode createNode(ITGGMatch match) {
		PrecedenceNode node = new PrecedenceNode(match);
		nodes.add(node);
		match2node.put(match, node);
		return node;
	}

	private void deleteNode(ITGGMatch match, PrecedenceNode node) {
		nodes.remove(node);
		node.clearRequires();
		node.clearRequiredBy();
		node.clearRollbackCauses();
		node.clearRollsBack();

		match2node.remove(match);
		brokenNodes.remove(node);
		implicitBrokenNodes.remove(node);
	}

	private void handleBrokenConsistencyMatch(ITGGMatch match) {
		PrecedenceNode node = getNode(match);
		node.setBroken(true);
		brokenNodes.add(node);
		implicitBrokenNodes.remove(node);
		nodesToBeUpdated.add(node);
	}

	private void updateImplicitBrokenNodes(PrecedenceNode node) {
		if (node.isBroken()) {
			node.addRollbackCause(node);
			forAllRequiredBy(node, n -> {
				n.addRollbackCause(node);
				if (!n.isBroken())
					implicitBrokenNodes.add(n);
				return true;
			});
		} else
			node.clearRollsBack();
	}

	public void forAllRequires(PrecedenceNode node, Predicate<? super PrecedenceNode> action) {
		Set<PrecedenceNode> processed = cfactory.createObjectSet();
		forAllRequires(node, action, processed);
	}

	private void forAllRequires(PrecedenceNode node, Predicate<? super PrecedenceNode> action,
			Set<PrecedenceNode> processed) {
		for (PrecedenceNode n : node.getRequires()) {
			if (!processed.contains(n)) {
				processed.add(n);
				if (action.test(n))
					forAllRequires(n, action, processed);
			}
		}
	}

	public void forAllRequiredBy(PrecedenceNode node, Predicate<? super PrecedenceNode> action) {
		Set<PrecedenceNode> processed = cfactory.createObjectSet();
		forAllRequiredBy(node, action, processed);
	}

	private void forAllRequiredBy(PrecedenceNode node, Predicate<? super PrecedenceNode> action,
			Set<PrecedenceNode> processed) {
		for (PrecedenceNode n : node.getRequiredBy()) {
			if (!processed.contains(n)) {
				processed.add(n);
				if (action.test(n))
					forAllRequiredBy(n, action, processed);
			}
		}
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
