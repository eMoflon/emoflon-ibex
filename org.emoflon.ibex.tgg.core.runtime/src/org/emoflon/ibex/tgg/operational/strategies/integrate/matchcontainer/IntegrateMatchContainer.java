package org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		this.precedenceGraph = strategy.getOptions().getResourceHandler().getPrecedenceResource();
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
			if (matchToNode.containsKey(match) && getNode(match).isBroken())
				getNode(match).setBroken(false);
		}
	}

	public PrecedenceNodeContainer getExtGraph() {
		return nodes;
	}

	public void update() {
		prepareResource();

		Set<ITGGMatch> matches = getMatches();
		matches.removeIf(m -> m.getType() == PatternType.CC);

		Set<ITGGMatch> restoredMatches = new HashSet<>();
		matches.removeIf(m -> {
			if (!matchToNode.containsKey(m))
				return false;
			if (getNode(m).isBroken())
				restoredMatches.add(m);
			return true;
		});
		matches.forEach(m -> createNode(m));
		matches.forEach(m -> updateNode(m));
		restoredMatches.forEach(m -> getNode(m).setBroken(false));
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
		if (getNode(match).isBroken()) {
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
		IGreenPatternFactory gFactory = strategy.getGreenFactory(match.getRuleName());

		Set<Object> eltsBasedOn = new HashSet<>();
		gFactory.getBlackSrcNodesInRule().forEach(n -> eltsBasedOn.add(match.get(n.getName())));
		gFactory.getBlackTrgNodesInRule().forEach(n -> eltsBasedOn.add(match.get(n.getName())));
		gFactory.getBlackSrcEdgesInRule().forEach(e -> eltsBasedOn.add(getRuntimeEdge(match, e)));
		gFactory.getBlackTrgEdgesInRule().forEach(e -> eltsBasedOn.add(getRuntimeEdge(match, e)));

		for (Object elt : eltsBasedOn) {
			raToTranslated.forEach((ra, objs) -> {
				if (objs.contains(elt)) {
					PrecedenceNode nodeBasedOn = matchToNode.get(raToMatch.get(ra));
					if (nodeBasedOn != null)
						node.getBasedOn().add(nodeBasedOn);
				}
			});
		}

		Collection<Object> requiredObjs = requires.get(match);
		if (requiredObjs != null && !requiredObjs.isEmpty()) {
			for (Object reqObj : requiredObjs) {
				Collection<ITGGMatch> requiredMatches = translatedBy.get(reqObj);
				if (requiredMatches != null && !requiredMatches.isEmpty()) {
					for (ITGGMatch reqMatch : requiredMatches) {
						PrecedenceNode nodeReq = matchToNode.get(reqMatch);
						if (nodeReq != null) {
							node.getRequires().add(nodeReq);
						}
					}
				}
			}
		}
	}

	private void createNode(ITGGMatch match) {
		PrecedenceNode node = PrecedencegraphFactory.eINSTANCE.createPrecedenceNode();
		node.setBroken(false);
		nodes.getNodes().add(node);
		node.setMatchAsString(match.getPatternName());

		matchToNode.put(match, node);
		nodeToMatch.put(node, match);
	}

	private void handleBrokenConsistencyMatch(ITGGMatch match) {
		PrecedenceNode node = matchToNode.get(match);
		if (node != null)
			node.setBroken(true);

		readySet.remove(match);
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

}
