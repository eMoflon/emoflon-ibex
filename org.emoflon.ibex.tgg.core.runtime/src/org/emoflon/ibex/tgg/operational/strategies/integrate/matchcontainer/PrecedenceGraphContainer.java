package org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.jdk.JDKCollectionFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggingMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import precedencegraph.PrecedenceNode;
import precedencegraph.PrecedenceNodeContainer;
import precedencegraph.PrecedencegraphFactory;

public class PrecedenceGraphContainer extends LoggingMatchContainer {

	public static final CollectionFactory cfactory = new JDKCollectionFactory();

	protected final PropagatingOperationalStrategy strategy;

	protected Map<PrecedenceNode, Collection<Object>> requires = cfactory.createObjectToObjectHashMap();
	protected Map<Object, Collection<PrecedenceNode>> requiredBy = cfactory.createObjectToObjectHashMap();
	protected Map<PrecedenceNode, Collection<Object>> translates = cfactory.createObjectToObjectHashMap();
	protected Map<Object, Collection<PrecedenceNode>> translatedBy = cfactory.createObjectToObjectHashMap();

	//// Precedence Graph ////

	protected Resource precedenceGraph;
	protected PrecedenceNodeContainer container;
	protected Map<ITGGMatch, PrecedenceNode> match2node = cfactory.createObjectToObjectHashMap();
	protected Map<PrecedenceNode, ITGGMatch> node2match = cfactory.createObjectToObjectHashMap();
	protected Set<PrecedenceNode> brokenNodes = cfactory.createObjectSet();
	protected Set<PrecedenceNode> implicitBrokenNodes = cfactory.createObjectSet();

	public PrecedenceGraphContainer(PropagatingOperationalStrategy strategy) {
		this.strategy = strategy;
		this.precedenceGraph = strategy.getOptions().resourceHandler().getPrecedenceResource();
		container = PrecedencegraphFactory.eINSTANCE.createPrecedenceNodeContainer();
		precedenceGraph.getContents().add(container);
	}

	public void notifyAddedMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY)
			addConsistencyMatch(match);
	}

	public void notifyRemovedMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY)
			handleBrokenConsistencyMatch(match);
	}

	public void clearBrokenMatches() {
		brokenNodes.forEach(node -> removeConsistencyMatch(getMatch(node), node));
		brokenNodes.clear();
	}

	public void removeMatch(ITGGMatch match) {
		PrecedenceNode node = getNode(match);
		removeConsistencyMatch(match, node);
		brokenNodes.remove(node);
	}

	public ITGGMatch getMatch(PrecedenceNode node) {
		ITGGMatch match = node2match.get(node);
		if (match == null)
			throw new RuntimeException("No consistency match found for precedence node!");
		return match;
	}

	public PrecedenceNode getNode(ITGGMatch match) {
		PrecedenceNode node = match2node.get(match);
		if (node == null)
			throw new RuntimeException("No precedence node found for consistency match!");
		return node;
	}

	public Collection<PrecedenceNode> getNodes() {
		return container.getNodes();
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
			updateImplicitBrokenNodes(node);
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
					node.getRequires().add(reqNode);
			}
		}
		for (Object elt : translatedElts) {
			Collection<PrecedenceNode> dependentNodes = requiredBy.get(elt);
			if (dependentNodes != null) {
				for (PrecedenceNode depNode : dependentNodes)
					node.getRequiredBy().add(depNode);
			}
		}
	}

	private void removeConsistencyMatch(ITGGMatch match, PrecedenceNode node) {
		updateImplicitBrokenNodes(node);
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
		PrecedenceNode node = PrecedencegraphFactory.eINSTANCE.createPrecedenceNode();
		node.setBroken(false);
		container.getNodes().add(node);
		node.setMatchAsString(match.getPatternName() + "(" + match.hashCode() + ")");

		match2node.put(match, node);
		node2match.put(node, match);

		return node;
	}

	private void deleteNode(ITGGMatch match, PrecedenceNode node) {
		EcoreUtil.delete(node, true);
		match2node.remove(match);
		node2match.remove(node);
	}

	private void handleBrokenConsistencyMatch(ITGGMatch match) {
		PrecedenceNode node = getNode(match);
		node.setBroken(true);
		brokenNodes.add(node);
		updateImplicitBrokenNodes(node);
	}

	private void updateImplicitBrokenNodes(PrecedenceNode node) {
		if (node.isBroken()) {
			node.getRollbackCauses().add(node);
			forAllRequiredBy(node, n -> {
				n.getRollbackCauses().add(node);
				implicitBrokenNodes.add(n);
				return true;
			});
		} else {
			node.getRollbackCauses().remove(node);
			forAllRequiredBy(node, n -> {
				n.getRollbackCauses().remove(node);
				if(n.getRollbackCauses().isEmpty())
					implicitBrokenNodes.remove(n);
				return true;
			});
		}
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

}
