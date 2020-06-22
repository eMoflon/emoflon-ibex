package org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.PrecedenceMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;

import precedencegraph.PrecedenceNode;
import precedencegraph.PrecedenceNodeContainer;
import precedencegraph.PrecedencegraphFactory;

public class IntegrateMatchContainer extends PrecedenceMatchContainer {

	private INTEGRATE strategy;
	private Resource precedenceGraph;

	private PrecedenceNodeContainer nodes;
	private Map<ITGGMatch, PrecedenceNode> matchToNode = new HashMap<>();
	private Map<PrecedenceNode, ITGGMatch> nodeToMatch = new HashMap<>();

	public IntegrateMatchContainer(INTEGRATE strategy) {
		super(strategy);
		this.strategy = strategy;
		this.precedenceGraph = strategy.getOptions().resourceHandler().getPrecedenceResource();
		nodes = PrecedencegraphFactory.eINSTANCE.createPrecedenceNodeContainer();
	}

	@Override
	public boolean removeMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY)
			handleBrokenConsistencyMatch(match);
		else
			deleteNode(match);
		return super.removeMatch(match);
	}

	@Override
	public void matchApplied(ITGGMatch match) {
		super.matchApplied(match);
		if (match.getType() == PatternType.CONSISTENCY) {
			PrecedenceNode node = getNode(match);
			if (matchToNode.containsKey(match) && node.isBroken()) {
				node.setBroken(false);
				updateRollbackImpact(node);
			}
		}
	}

	public PrecedenceNodeContainer getGraph() {
		return nodes;
	}

	public void update() {
		prepareResource();

		Set<ITGGMatch> matches = new HashSet<>(getMatches());
		// this is bad. basically you use the NEW matches and then to add ALL those that you already found to them
		// TODO lfritsche, amoeller
		matches.addAll(raToMatch.values());
		matches.removeIf(m -> m.getType() == PatternType.CC);

		Set<ITGGMatch> restoredMatches = new HashSet<>();
		
		// what does this mean? what are restored matches? isn't this something that I can check on the newly added matches? old ones should still be broken
		// TODO lfritsche, amoeller
		matches.removeIf(m -> {
			if (!matchToNode.containsKey(m))
				return false;
			if (getNode(m).isBroken())
				restoredMatches.add(m);
			return true;
		});
		
		matches.forEach(m -> createNode(m));
		
		// hotspot but probably because of the upper stuff?
		// TODO lfritsche, amoeller
		matches.forEach(m -> updateNode(m));
		restoredMatches.forEach(m -> {
			PrecedenceNode node = getNode(m);
			node.setBroken(false);
			updateRollbackImpact(node);
		});
	}

	public ITGGMatch getMatch(PrecedenceNode node) {
		return nodeToMatch.get(node);
	}

	public PrecedenceNode getNode(ITGGMatch match) {
		return matchToNode.get(match);
	}

	public void removeAllBrokenMatches() {
		nodes.getNodes().forEach(n -> {
			if (n.isBroken())
				deleteNode(getMatch(n));
		});
		clearPendingElements();
	}

	public void removeBrokenMatch(ITGGMatch match) {
		PrecedenceNode node = getNode(match);
		if (node.isBroken()) {
			updateRollbackImpact(node);
			deleteNode(match);

			IGreenPatternFactory gFactory = strategy.getGreenFactory(match.getRuleName());
			Collection<Object> translatedElts = new ArrayList<>();
			gFactory.getGreenSrcNodesInRule().forEach(n -> translatedElts.add(match.get(n.getName())));
			gFactory.getGreenTrgNodesInRule().forEach(n -> translatedElts.add(match.get(n.getName())));
			gFactory.getGreenSrcEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(match, e)));
			gFactory.getGreenTrgEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(match, e)));
			pendingElts.removeAll(translatedElts);
		}
	}

	private void updateNode(ITGGMatch match) {
		PrecedenceNode node = matchToNode.get(match);

		Collection<Object> required = requires.get(match);
		if(required != null)
			for (Object elt : required) {
				// that looks VERY expensive. why do we have to go over ALL rule applications? there is a requiredBy map which is exactly for this purpose to also navigate back!
				// TODO lfritsche, amoeller
				Collection<ITGGMatch> translatingMatches = translatedBy.get(elt); 
				if(translatingMatches == null)
					continue;
				
				translatingMatches.forEach(m -> {
						PrecedenceNode requiredNode = matchToNode.get(m);
						if (requiredNode != null)
							node.getRequires().add(requiredNode);
				});
			}

		Collection<Object> translated = translates.get(match);
		if(translated != null)
			for(Object elt : translates.get(match)) {
				Collection<ITGGMatch> requiringMatches = requiredBy.get(elt);
				if(requiringMatches.isEmpty())
					continue;
				
				requiringMatches.forEach(m -> {
					PrecedenceNode requiringNode = matchToNode.get(m);
					if (requiringNode != null)
						requiringNode.getRequires().add(node);
				});
			}
	}

	private void createNode(ITGGMatch match) {
		PrecedenceNode node = PrecedencegraphFactory.eINSTANCE.createPrecedenceNode();
		node.setBroken(false);
		
		// this is probably bad. make this a hashset and use addall perfore persisting the pg
		// TODO lfritsche, amoeller
		nodes.getNodes().add(node);
		node.setMatchAsString(match.getPatternName() + "(" + match.hashCode() + ")");

		matchToNode.put(match, node);
		nodeToMatch.put(node, match);
	}

	private void handleBrokenConsistencyMatch(ITGGMatch match) {
		PrecedenceNode node = matchToNode.get(match);
		if (node != null) {
			node.setBroken(true);
			updateRollbackImpact(node);
		}

		readySet.remove(match);
	}

	private void updateRollbackImpact(PrecedenceNode node) {
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

	private void deleteNode(ITGGMatch match) {
		PrecedenceNode node = matchToNode.get(match);
		if (node != null) {
			EcoreUtil.delete(node, true);
			matchToNode.remove(match);
			nodeToMatch.remove(node);
		}
	}

	private void prepareResource() {
		if (precedenceGraph.getContents().isEmpty())
			precedenceGraph.getContents().add(nodes);
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
