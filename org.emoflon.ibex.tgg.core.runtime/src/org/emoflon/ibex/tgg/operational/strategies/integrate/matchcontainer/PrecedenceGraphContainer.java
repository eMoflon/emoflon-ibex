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
	protected PrecedenceNodeContainer nodes;
	protected Map<ITGGMatch, PrecedenceNode> matchToNode = cfactory.createObjectToObjectHashMap();
	protected Map<PrecedenceNode, ITGGMatch> nodeToMatch = cfactory.createObjectToObjectHashMap();
	protected Set<PrecedenceNode> brokenNodes = cfactory.createObjectSet();

	public PrecedenceGraphContainer(PropagatingOperationalStrategy strategy) {
		this.strategy = strategy;
		this.precedenceGraph = strategy.getOptions().resourceHandler().getPrecedenceResource();
		nodes = PrecedencegraphFactory.eINSTANCE.createPrecedenceNodeContainer();
		precedenceGraph.getContents().add(nodes);
	}

	public void addMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY)
			addConsistencyMatch(match);
	}

	protected void addConsistencyMatch(ITGGMatch match) {
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
		PrecedenceNode node = createNode(match);

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

	public void notifyRemoveMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY)
			handleBrokenConsistencyMatch(match);
	}

	private void removeMatch(ITGGMatch match) {
		PrecedenceNode node = matchToNode.get(match);
		if (node == null)
			throw new RuntimeException("No PrecedenceNode found for consistency match!");

		updateImplicitBrokenNodes(node);
		deleteNode(match, node);
//		brokenNodes.remove(node);

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

	public void clearBrokenMatches() {
		
	}

	public ITGGMatch getMatch(PrecedenceNode node) {
		return nodeToMatch.get(node);
	}

	public PrecedenceNode getNode(ITGGMatch match) {
		return matchToNode.get(match);
	}

	public PrecedenceNodeContainer getGraph() {
		return nodes;
	}

	private PrecedenceNode createNode(ITGGMatch match) {
		PrecedenceNode node = PrecedencegraphFactory.eINSTANCE.createPrecedenceNode();
		node.setBroken(false);
		nodes.getNodes().add(node);
		node.setMatchAsString(match.getPatternName() + "(" + match.hashCode() + ")");

		matchToNode.put(match, node);
		nodeToMatch.put(node, match);

		return node;
	}

	private void deleteNode(ITGGMatch match, PrecedenceNode node) {
		EcoreUtil.delete(node, true);
		matchToNode.remove(match);
		nodeToMatch.remove(node);
	}

	private void handleBrokenConsistencyMatch(ITGGMatch match) {
		PrecedenceNode node = matchToNode.get(match);
		if (node == null)
			throw new RuntimeException("No PrecedenceNode found for consistency match!");

		node.setBroken(true);
		brokenNodes.add(node);
		updateImplicitBrokenNodes(node);
	}

	private void updateImplicitBrokenNodes(PrecedenceNode node) {
		if (node.isBroken()) {
			node.getRollbackCauses().add(node);
			forAllRequiredBy(node, n -> {
				n.getRollbackCauses().add(node);
				return true;
			});
		} else {
			node.getRollbackCauses().remove(node);
			forAllRequiredBy(node, n -> {
				n.getRollbackCauses().remove(node);
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
