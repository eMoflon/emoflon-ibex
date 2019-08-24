package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.monitoring.data.Edge;
import org.emoflon.ibex.tgg.operational.monitoring.data.Graph;
import org.emoflon.ibex.tgg.operational.monitoring.data.GraphBuilder;
import org.emoflon.ibex.tgg.operational.monitoring.data.Node;
import org.emoflon.ibex.tgg.operational.monitoring.data.ProtocolStep;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;

public abstract class IbexController implements IbexObserver, IUpdatePolicy {

	private int step = 0;
	private OperationalStrategy operationalStrategy;
	private Set<EObject> resourceList = new HashSet<EObject>();
	private Collection<Node> nodeList = new HashSet<Node>();
	private List<ProtocolStep> protocolsStepList = new ArrayList<ProtocolStep>();
	private Map<IMatch, VictoryMatch> matchMapping = new HashMap<>();
	private IMatch chosenMatch;

	public void register(OperationalStrategy pOperationalStrategy) {
		operationalStrategy = pOperationalStrategy;
		pOperationalStrategy.registerObserver(this);
		pOperationalStrategy.setUpdatePolicy(this);
	}

	@Override
	public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {

		updateMatchMapping(matchContainer.getMatches());
		chosenMatch = chooseOneMatch(new VictoryDataPackage(matchMapping.values(), getProtocols()));
		return chosenMatch;
	}

	public Collection<VictoryMatch> getMoreMatches(int amount) {

		/*
		 * TODO implement This method needs to provide the specified number of
		 * additional matches. This method will be called by the UI when the user
		 * requests additional matches beyond those that were given to the UI in the
		 * method above. The structure of the map is the same as described above.
		 */

		return null;
	}

	private List<ProtocolStep> getProtocols() {
		Resource p = operationalStrategy.getProtocolResource();
		EList<EObject> protocols = p.getContents();

		if (protocols.size() > 0) {
			
			int index = protocolsStepList.size();

			Collection<EObject> srcList = getResourceItems(operationalStrategy.getSourceResource());
			Collection<EObject> trgList = getResourceItems(operationalStrategy.getTargetResource());
			
			EList<EObject> items = protocols.get(protocols.size() - 1).eCrossReferences();
			HashSet<EObject> srcResourceList = getCurrentResourceItems(items, operationalStrategy.getSourceResource());
			HashSet<EObject> trgResourceList = getCurrentResourceItems(items, operationalStrategy.getTargetResource());
			HashSet<EObject> corrResourceList = getCurrentResourceItems(items, operationalStrategy.getCorrResource());
			
			GraphBuilder builder = new GraphBuilder();

			Collection<Node> srcNodes = generateNodes(srcResourceList);
			Collection<Node> trgNodes = generateNodes(trgResourceList);
			Collection<Node> currentNodes = new HashSet<Node>();
			currentNodes.addAll(srcNodes);
			currentNodes.addAll(trgNodes);
			nodeList.addAll(currentNodes);

			builder.addSrcNodes(srcNodes);
			builder.addTrgNodes(trgNodes);


			for (EObject n : corrResourceList) {
				Node srcNode = null;
				Node trgNode = null;
				for (EObject tn : n.eCrossReferences()) {
					Node newNode = generateNode(tn);
					if (srcList.contains(tn)) {
						srcNode = newNode;
					}

					if (trgList.contains(tn)) {
						trgNode = newNode;
					}
				}

				if (!srcNode.equals(null) && !trgNode.equals(null)) {
					Edge e = new Edge(srcNode.getType() + "To" + trgNode.getType(), srcNode, trgNode);
					builder.addCorrEdge(e);
				}
			}

			Collection<Edge> currentEdges = new HashSet<Edge>();

			TGGRule rule = operationalStrategy.getOptions().flattenedTGG().getRules().stream()
					.filter(r -> r.getName().equals(chosenMatch.getRuleName())).findFirst().orElseGet(null);
			Set<TGGRuleEdge> edges = rule.getEdges().stream().filter(
					e -> !e.getDomainType().equals(DomainType.CORR) && !e.getBindingType().equals(BindingType.CONTEXT))
					.collect(Collectors.toSet());
			for (TGGRuleEdge e : edges) {

				Node srcNode = null;
				Node trgNode = null;

				for (Node n : nodeList) {
					if (n.getType().equals(e.getSrcNode().getType().getName())) {
						srcNode = n;
						break;
					} else if (n.getType().equals(e.getTrgNode().getType().getName())) {
						trgNode = n;
						break;
					}
				}

				if (srcNode != null || trgNode != null) {
					for (Node n : nodeList) {

						if (srcNode == null && n.getType().equals(e.getSrcNode().getType().getName())) {
							srcNode = n;
						} else if (trgNode == null && n.getType().equals(e.getTrgNode().getType().getName())) {
							trgNode = n;
						}

						if (srcNode != null && trgNode != null) {
							Edge edge = new Edge(e.getName(), srcNode, trgNode);
							if (!currentEdges.stream().anyMatch(ed -> ed.getLabel().equals(e.getName()))) {
								currentEdges.add(edge);
							}
							break;
						}
					}
				}

			}

			builder.addEdges(currentEdges);
			Graph graph = builder.build();

			ProtocolStep protocolStep = new ProtocolStep(index, graph);
			protocolsStepList.add(protocolStep);
		}

		return protocolsStepList;
	}

	private Collection<Node> generateNodes(Collection<EObject> nodes) {
		Collection<Node> newNodes = new HashSet<Node>();
		for (EObject node : nodes) {
			newNodes.add(generateNode(node));
		}
		return newNodes;
	}

	private Node generateNode(EObject node) {
		Collection<String> attr = new HashSet<String>();
		String type = getType(node);
		String label = getNodeName(node);
		Node newNode = new Node(type, label, attr);
		return newNode;
	}

	private String getNodeName(EObject node) {
		String nodeName = node.toString();
		String phrase = "(name: ";
		int start = nodeName.indexOf(phrase);
		int end = nodeName.substring(start + phrase.length()).indexOf(")");

		return nodeName.substring(start + phrase.length(), start + phrase.length() + end);
	}

	private Collection<EObject> getResourceItems(Resource r) {
		Collection<EObject> rList = new HashSet<EObject>();
		TreeIterator<EObject> rItr = r.getAllContents();
		while (rItr.hasNext()) {
			rList.add(rItr.next());
		}
		return rList;
	}

	private String getType(EObject node) {
		return node.eClass().getName();
	}

	private HashSet<EObject> getCurrentResourceItems(EList<EObject> items, Resource resource) {
		HashSet<EObject> currentResourceList = new HashSet<EObject>();

		for (EObject item : items) {
			if (!resourceList.contains(item) && item.eResource().equals(resource)) {
				currentResourceList.add(item);
				resourceList.add(item);
			}
		}

		return currentResourceList;
	}

	private void updateMatchMapping(Collection<IMatch> pMatches) {

		for (Iterator<IMatch> iterator = matchMapping.keySet().iterator(); iterator.hasNext();) {
			IMatch match = iterator.next();
			if (pMatches.contains(match))
				continue;
			else if (operationalStrategy.getBlockedMatches().containsKey(match))
				continue;
			else
				iterator.remove();
		}

		VictoryMatch.startMatchCreation(step);

		for (IMatch match : pMatches)
			if (matchMapping.containsKey(match))
				matchMapping.get(match).setBlockingReason(null);
			else
				matchMapping.put(match, VictoryMatch.newMatch(match));

		if (operationalStrategy.getBlockedMatches() != null)
			operationalStrategy.getBlockedMatches().forEach((match, reason) -> {
				if (matchMapping.containsKey(match))
					matchMapping.get(match).setBlockingReason(reason);
				else {
					VictoryMatch vMatch = VictoryMatch.newMatch(match);
					vMatch.setBlockingReason(reason);
					matchMapping.put(match, vMatch);
				}
			});

		VictoryMatch.finishMatchCreation();
		step++;
	}

	public abstract IMatch chooseOneMatch(VictoryDataPackage pDataPackage);

	protected abstract int getRequestedMatchCount();
}
