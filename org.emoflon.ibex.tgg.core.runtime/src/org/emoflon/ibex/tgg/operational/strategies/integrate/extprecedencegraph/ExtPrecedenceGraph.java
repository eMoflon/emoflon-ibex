package org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.sync.PrecedenceGraph;

import precedencegraph.PrecedenceNode;
import precedencegraph.PrecedenceNodeContainer;
import precedencegraph.PrecedencegraphFactory;

public class ExtPrecedenceGraph extends PrecedenceGraph {

	private INTEGRATE strategy;

	private PrecedenceNodeContainer nodes;
	private Map<ITGGMatch, PrecedenceNode> matchToNode = new HashMap<>();
	private Map<PrecedenceNode, ITGGMatch> nodeToMatch = new HashMap<>();

	public ExtPrecedenceGraph(INTEGRATE strategy) {
		super(strategy);
		this.strategy = strategy;
		nodes = PrecedencegraphFactory.eINSTANCE.createPrecedenceNodeContainer();
	}

	@Override
	public boolean removeMatch(ITGGMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY))
			handleBrokenConsistencyMatch(match);
		else
			deleteNode(match);
		return super.removeMatch(match);
	}

	public PrecedenceNodeContainer getExtGraph() {
		update();
		return nodes;
	}

	public void update() {
		prepareResource();

		getMatches();

		Set<ITGGMatch> matches = new HashSet<>();
		matches.addAll(readySet);
		matches.addAll(requires.keySet());
		matches.removeIf(m -> m.getPatternName().endsWith(PatternSuffixes.CC));

		Set<ITGGMatch> restoredMatches = new HashSet<>();
		matches.removeIf(m -> {
			PrecedenceNode n = matchToNode.get(m);
			if (n == null)
				return false;
			if (n.isBroken())
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

	public void removeAllBrokenNodes() {
		nodes.getNodes().forEach(n -> {
			if (n.isBroken())
				deleteNode(getMatch(n));
		});
	}

	private void updateNode(ITGGMatch match) {
		PrecedenceNode node = matchToNode.get(match);
		IGreenPatternFactory gFactory = strategy.getGreenFactory(match.getRuleName());

		Set<Object> eltsBasedOn = new HashSet<>();
		gFactory.getBlackSrcNodesInRule().forEach(n -> eltsBasedOn.add(match.get(n.getName())));
		gFactory.getBlackTrgNodesInRule().forEach(n -> eltsBasedOn.add(match.get(n.getName())));
		gFactory.getBlackSrcEdgesInRule().forEach(e -> eltsBasedOn.add(strategy.getRuntimeEdge(match, e)));
		gFactory.getBlackTrgEdgesInRule().forEach(e -> eltsBasedOn.add(strategy.getRuntimeEdge(match, e)));

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
		if (strategy.getEPGResource().getContents().isEmpty())
			strategy.getEPGResource().getContents().add(nodes);
	}

}
