package org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.sync.PrecedenceGraph;

import precedencegraph.Node;
import precedencegraph.NodeContainer;
import precedencegraph.PrecedencegraphFactory;

public class ExtPrecedenceGraph extends PrecedenceGraph {

	private NodeContainer nodes;
	private Map<IMatch, Node> matchToNode = new HashMap<>();
	private Map<Node, IMatch> nodeToMatch = new HashMap<>();

	public ExtPrecedenceGraph(INTEGRATE strategy) {
		super(strategy);
		nodes = PrecedencegraphFactory.eINSTANCE.createNodeContainer();
	}

	@Override
	public boolean removeMatch(IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY))
			handleBrokenConsistencyMatch(match);
		else
			deleteNode(match);
		return super.removeMatch(match);
	}

	public NodeContainer getExtGraph() {
		update();
		return nodes;
	}

	public void update() {
		getMatches();

		Set<IMatch> matches = new HashSet<>();
		matches.addAll(readySet);
		matches.addAll(requires.keySet());
		
		matches.removeIf(m -> matchToNode.containsKey(m));
		matches.forEach(m -> createNode(m));
		matches.forEach(m -> updateNode(m));
	}
	
	public IMatch getMatch(Node node) {
		return nodeToMatch.get(node);
	}
	
	public Node getNode(IMatch match) {
		return matchToNode.get(match);
	}

	private void updateNode(IMatch match) {
		Node node = matchToNode.get(match);
		IGreenPatternFactory gFactory = strategy.getGreenFactory(match.getRuleName());

		Set<Object> eltsBasedOn = new HashSet<>();
		gFactory.getBlackSrcNodesInRule().forEach(n -> eltsBasedOn.add(match.get(n.getName())));
		gFactory.getBlackTrgNodesInRule().forEach(n -> eltsBasedOn.add(match.get(n.getName())));
		gFactory.getBlackSrcEdgesInRule().forEach(e -> eltsBasedOn.add(strategy.getRuntimeEdge(match, e)));
		gFactory.getBlackTrgEdgesInRule().forEach(e -> eltsBasedOn.add(strategy.getRuntimeEdge(match, e)));

		for (Object elt : eltsBasedOn) {
			raToTranslated.forEach((ra, objs) -> {
				if (objs.contains(elt)) {
					Node nodeBasedOn = matchToNode.get(raToMatch.get(ra));
					if (nodeBasedOn != null)
						node.getBasedOn().add(nodeBasedOn);
				}
			});
		}
		
		Collection<Object> requiredObjs = requires.get(match);
		if(requiredObjs != null && !requiredObjs.isEmpty()) {
			for(Object reqObj : requiredObjs) {
				Collection<IMatch> requiredMatches = translatedBy.get(reqObj);
				if(requiredMatches != null && !requiredMatches.isEmpty()) {
					for(IMatch reqMatch : requiredMatches) {
						Node nodeReq = matchToNode.get(reqMatch);
						if(nodeReq != null) {
							node.getRequires().add(nodeReq);
						}
					}
				}
			}
		}
	}

	private void createNode(IMatch match) {
		Node node = PrecedencegraphFactory.eINSTANCE.createNode();
		node.setBroken(false);
		nodes.getNodes().add(node);

		matchToNode.put(match, node);
		nodeToMatch.put(node, match);
	}

	private void handleBrokenConsistencyMatch(IMatch match) {
		Node node = matchToNode.get(match);
		if (node != null)
			node.setBroken(true);
	}

	private void deleteNode(IMatch match) {
		Node node = matchToNode.get(match);
		if (node != null) {
			EcoreUtil.delete(node, true);
			matchToNode.remove(match);
			nodeToMatch.remove(node);
		}
	}

}
