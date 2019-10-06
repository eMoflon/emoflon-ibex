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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.monitoring.data.*;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public abstract class IbexController implements IbexObserver, IUpdatePolicy {

	private int step = 0;
	private OperationalStrategy operationalStrategy;
	private Set<EObject> resourceList = new HashSet<EObject>();
	private List<ProtocolStep> protocolsStepList = new ArrayList<ProtocolStep>();
	private Map<IMatch, IbexMatch> matchMapping = new HashMap<>();
	HashMap<Integer, Collection<Node>> nodeList = new HashMap<>();
	private IMatch chosenMatch;

	public void register(OperationalStrategy pOperationalStrategy) {
		operationalStrategy = pOperationalStrategy;
		pOperationalStrategy.registerObserver(this);
		pOperationalStrategy.setUpdatePolicy(this);
	}

	@Override
	public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		updateMatchMapping(matchContainer.getMatches());
		chosenMatch = chooseOneMatch(new DataPackage(matchMapping.values(), getProtocols()));
		return chosenMatch;
	}

	public Collection<IbexMatch> getMoreMatches(int amount) {

		/*
		 * TODO implement This method needs to provide the specified number of
		 * additional matches. This method will be called by the UI when the user
		 * requests additional matches beyond those that were given to the UI in the
		 * method above. The structure of the map is the same as described above.
		 */

		return null;
	}

	public TGGObjectGraph constructTGGObjectGraph(int index, Collection<EObject> pSrcElements,
			Collection<EObject> pTrgElements, Collection<EObject> pCorrElements, String ruleName) {

		HashMap<String, HashSet<EObject>> resources = new HashMap<String, HashSet<EObject>>();

		Collection<EObject> srcList = getResourceItems(operationalStrategy.getSourceResource());
		Collection<EObject> trgList = getResourceItems(operationalStrategy.getTargetResource());

		TGGObjectGraphBuilder builder = new TGGObjectGraphBuilder();

		Collection<Node> tSrcNodes = generateNodes(pSrcElements, Domain.SRC, Action.CREATE);
		Collection<Node> tTrgNodes = generateNodes(pTrgElements, Domain.TRG, Action.CREATE);
		Collection<Node> srcNodes = new HashSet<Node>();
		Collection<Node> trgNodes = new HashSet<Node>();

		Collection<Node> currentNodes = new HashSet<Node>();
		Collection<Node> nodeListUntilNow = new HashSet<Node>();

		for (int i = 0; i <= index; i++) {
			nodeListUntilNow.addAll(nodeList.get(i));
		}

		for (Node sn : tSrcNodes) {
			Node s = null;
			for (Node n : nodeListUntilNow) {
				if (n.getName().equals(sn.getName()) && n.getType().equals(sn.getType())
						&& n.getDomain().equals(sn.getDomain())) {
					s = n;
					break;
				}
			}
			if (s != null) {
				srcNodes.add(s);
			} else {
				srcNodes.add(sn);
			}
		}

		for (Node tn : tTrgNodes) {
			Node t = null;
			for (Node n : nodeListUntilNow) {
				if (n.getName().equals(tn.getName()) && n.getType().equals(tn.getType())
						&& n.getDomain().equals(tn.getDomain())) {
					t = n;
					break;
				}
			}
			if (t != null) {
				trgNodes.add(t);
			} else {
				trgNodes.add(tn);
			}
		}

		currentNodes.addAll(srcNodes);
		currentNodes.addAll(trgNodes);

		System.out.println("srcNodes: " + srcNodes.size());
		System.out.println("trgNodes: " + trgNodes.size());
		System.out.println("currentNodes: " + currentNodes.size());
		System.out.println("nodeList: " + nodeListUntilNow.size());

		builder.addSrcNodes(srcNodes);
		builder.addTrgNodes(trgNodes);

		for (EObject n : pCorrElements) {
			Node srcNode = null;
			Node trgNode = null;
			EObject eNode = null;
			for (EObject tn : n.eCrossReferences()) {
				eNode = tn;
				Node newNode = generateNode(tn, null, Action.CONTEXT);
				for (Node nl : currentNodes) {
					if (nl.getName().equals(newNode.getName()) && nl.getType().equals(newNode.getType())) {
						newNode = nl;
						newNode.setAction(Action.CREATE);
					}
				}

				if (pSrcElements.contains(tn)) {
					newNode.setDomain(Domain.SRC);
				} else if (pTrgElements.contains(tn)) {
					newNode.setDomain(Domain.TRG);
				}

				if (srcList.contains(tn)) {
					srcNode = newNode;
				}

				if (trgList.contains(tn)) {
					trgNode = newNode;
				}
			}

			if (!srcNode.equals(null) && !trgNode.equals(null)) {
				Edge e = new EdgeImpl(":" + eNode.eClass().getName(), srcNode, trgNode, EdgeType.CORR, Action.CREATE);
				builder.addEdge(e);
				builder.addNode(srcNode);
				builder.addNode(trgNode);
			}
		}

		Collection<Edge> currentEdges = new HashSet<Edge>();
		TGGRule rule = operationalStrategy.getOptions().flattenedTGG().getRules().stream()
				.filter(r -> r.getName().equals(ruleName)).findFirst().orElseGet(null);
		Set<TGGRuleEdge> edges = rule.getEdges().stream().filter(
				e -> !e.getDomainType().equals(DomainType.CORR) && !e.getBindingType().equals(BindingType.CONTEXT))
				.collect(Collectors.toSet());
		for (TGGRuleEdge e : edges) {

			Node srcNode = null;
			Node trgNode = null;

			for (Node n : nodeListUntilNow) {
				if (n.getType().equals(e.getSrcNode().getType().getName())) {
					srcNode = n;
					break;
				} else if (n.getType().equals(e.getTrgNode().getType().getName())) {
					trgNode = n;
					break;
				}
			}

			if (srcNode != null || trgNode != null) {
				for (Node n : nodeListUntilNow) {

					if (srcNode == null && n.getType().equals(e.getSrcNode().getType().getName())) {
						srcNode = n;
					} else if (trgNode == null && n.getType().equals(e.getTrgNode().getType().getName())) {
						trgNode = n;
					}

					if (srcNode != null && trgNode != null) {
						Action eAction = Action.valueOf(e.getBindingType().toString());
						Edge edge = new EdgeImpl(e.getTrgNode().getName(), srcNode, trgNode, EdgeType.NORMAL, eAction);

						if (!currentEdges.stream().anyMatch(ed -> ed.getLabel().equals(e.getName()))) {
							currentEdges.add(edge);
							if (!currentNodes.contains(srcNode)) {
								srcNode.setAction(Action.CONTEXT);
							} else {
								srcNode.setAction(Action.CREATE);
							}
							if (!currentNodes.contains(trgNode)) {
								trgNode.setAction(Action.CONTEXT);
							} else {
								trgNode.setAction(Action.CREATE);
							}
							builder.addNode(srcNode);
							builder.addNode(trgNode);
						}
						break;
					}
				}
			}

		}

		builder.addEdges(currentEdges);
		builder.setResources(resources);
		return builder.build();
	}

	private List<ProtocolStep> getProtocols() {
		Resource p = operationalStrategy.getProtocolResource();
		EList<EObject> protocols = p.getContents();

		if (protocols.size() > 0) {

			int index = protocolsStepList.size();
			TGGRule rule = operationalStrategy.getOptions().flattenedTGG().getRules().stream()
					.filter(r -> r.getName().equals(chosenMatch.getRuleName())).findFirst().orElseGet(null);

			EList<EObject> items = protocols.get(protocols.size() - 1).eCrossReferences();
			HashSet<EObject> srcResourceList = getCurrentResourceItems(items, operationalStrategy.getSourceResource());
			HashSet<EObject> trgResourceList = getCurrentResourceItems(items, operationalStrategy.getTargetResource());
			HashSet<EObject> corrResourceList = getCurrentResourceItems(items, operationalStrategy.getCorrResource());
			HashSet<EObject> matchSrcResourceList = new HashSet<EObject>();
			HashSet<EObject> matchTrgResourceList = new HashSet<EObject>();

			Collection<Node> srcNodes = generateNodes(srcResourceList, Domain.SRC, Action.CREATE);
			Collection<Node> trgNodes = generateNodes(trgResourceList, Domain.TRG, Action.CREATE);
			Collection<Node> allNodes = new HashSet<Node>();

			allNodes.addAll(srcNodes);
			allNodes.addAll(trgNodes);
			nodeList.put(index, allNodes);

			for (String pn : chosenMatch.getParameterNames()) {
				for (TGGRuleNode n : rule.getNodes()) {
					if (n.getName().equals(pn)) {
						switch (n.getDomainType()) {
						case SRC:
							matchSrcResourceList.add((EObject) chosenMatch.get(pn));
							break;
						case TRG:
							matchTrgResourceList.add((EObject) chosenMatch.get(pn));
							break;
						default:
							break;
						}
					}
				}
			}

			ProtocolStep protocolStep = new ProtocolStep(index, srcResourceList, trgResourceList, corrResourceList,
					matchSrcResourceList, matchTrgResourceList, rule.getName());
			protocolsStepList.add(protocolStep);
		}

		return protocolsStepList;
	}

	private Collection<Node> generateNodes(Collection<EObject> nodes, Domain domain, Action action) {
		Collection<Node> newNodes = new HashSet<Node>();
		for (EObject node : nodes) {
			newNodes.add(generateNode(node, domain, action));
		}
		return newNodes;
	}

	public Node generateNode(EObject node, Domain domain, Action action) {
		String type = getType(node);
		String name = getNodeName(node);

		List<String> attributes = new ArrayList<>();
		for (EAttribute attr : node.eClass().getEAttributes()) {
			attributes.add(attr.getEType().getName() + " " + attr.getName() + " = " + node.eGet(attr));
		}

		Node newNode = new NodeImpl(type, name, domain, action, attributes);
		return newNode;
	}

	private String getNodeName(EObject node) {
		String nodeName = node.toString();
		String phrase = "(name: ";
		int start = nodeName.indexOf(phrase);
		int end = nodeName.substring(start + phrase.length()).indexOf(")");

		return nodeName.substring(start + phrase.length(), start + phrase.length() + end);
	}

	private String getType(EObject node) {
		return node.eClass().getName();
	}

	private Collection<EObject> getResourceItems(Resource r) {
		Collection<EObject> rList = new HashSet<EObject>();
		TreeIterator<EObject> rItr = r.getAllContents();
		while (rItr.hasNext()) {
			rList.add(rItr.next());
		}
		return rList;
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

		IbexMatch.startMatchCreation(step);

		for (IMatch match : pMatches)
			if (matchMapping.containsKey(match))
				matchMapping.get(match).setBlockingReason(null);
			else
				matchMapping.put(match, IbexMatch.newMatch(match));

		if (operationalStrategy.getBlockedMatches() != null)
			operationalStrategy.getBlockedMatches().forEach((match, reason) -> {
				if (matchMapping.containsKey(match))
					matchMapping.get(match).setBlockingReason(reason);
				else {
					IbexMatch vMatch = IbexMatch.newMatch(match);
					vMatch.setBlockingReason(reason);
					matchMapping.put(match, vMatch);
				}
			});

		IbexMatch.finishMatchCreation();
		step++;
	}

	public void update(ObservableEvent pEventType, Object... pAdditionalInformation) {
		// ignore by default
	}

	public abstract IMatch chooseOneMatch(DataPackage pDataPackage);
}
