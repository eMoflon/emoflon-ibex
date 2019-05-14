package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public class VictoryDataProvider implements IVictoryDataProvider {

	private final static Logger logger = Logger.getLogger(VictoryDataProvider.class);

	OperationalStrategy op;

	public VictoryDataProvider(OperationalStrategy pOperationalStrategy) {
		this.op = pOperationalStrategy;
	}

	@Override
	public TGGRule getRule(String pRuleName) {
		try {
			return op.getOptions().flattenedTGG().getRules().stream().filter(r -> r.getName().equals(pRuleName))
					.findFirst().get();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Set<EObject> getMatchNeighbourhood(IMatch match, int k) {

		Set<EObject> list = new HashSet<EObject>();
		Set<EObject> resourceList = new HashSet<EObject>();
		Set<TGGRuleNode> candidate = new HashSet<TGGRuleNode>();
		HashMap<String, Set<TGGRuleNode>> nodes = new HashMap<String, Set<TGGRuleNode>>();

		try {

			Resource src = op.getSourceResource();
			Resource trg = op.getTargetResource();

			TreeIterator<EObject> srcTreeIterator = src.getAllContents();
			while (srcTreeIterator.hasNext()) {
				resourceList.add(srcTreeIterator.next());
			}

			TreeIterator<EObject> trgTreeIterator = trg.getAllContents();
			while (trgTreeIterator.hasNext()) {
				resourceList.add(trgTreeIterator.next());
			}

			for (TGGRule rule : op.getOptions().getFlattenedConcreteTGGRules()) {
				for (TGGRuleNode node : rule.getNodes()) {
					if (!node.getDomainType().equals(DomainType.CORR)) {
						Set<TGGRuleEdge> edges = node.getOutgoingEdges().stream()
								.filter(x -> x.getBindingType().equals(BindingType.CREATE)).collect(Collectors.toSet());
						if (edges.size() > 0) {
							for (TGGRuleEdge edge : edges) {
								TGGRuleNode trgNode = edge.getTrgNode();
								if (nodes.containsKey(node.getName())) {
									nodes.get(node.getName()).add(trgNode);
								} else {
									Set<TGGRuleNode> temp = new HashSet<TGGRuleNode>() {
										{
											add(trgNode);
										}
									};
									nodes.put(node.getName(), temp);
								}
							}
						}
					}
				}
			}

			candidate = makeCandidates(nodes, match);
			for (int i = 1; i <= k; i++) {
				Set<TGGRuleNode> candidate_tmp = new HashSet<TGGRuleNode>();

				Set<TGGRuleNode> finalNodes = new HashSet<TGGRuleNode>();
				for (TGGRuleNode c : candidate) {
					if (nodes.containsKey(c.getName())) {
						finalNodes.addAll(nodes.get(c.getName()));
					}
				}

				for (TGGRuleNode c : candidate) {
					TGGRuleNode f = finalNodes.stream().filter(x -> x.getName().equals(c.getName())).findFirst().orElse(null);
					if (f != null) {
						finalNodes.remove(f);
					}
				}

				for (TGGRuleNode n : finalNodes) {
					for (EObject r : resourceList) {
						if (r.toString().toLowerCase().indexOf("." + n.getName() + "impl") > 0) {
							EObject p = (EObject) match.get(n.getName());
							if (!r.equals(p)) {
								list.add(r);
								candidate_tmp.add(n);
							}
						}
					}
				}
				candidate = candidate_tmp;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private Set<TGGRuleNode> makeCandidates(HashMap<String, Set<TGGRuleNode>> nodes, IMatch match) {
		Set<TGGRuleNode> candidate = new HashSet<TGGRuleNode>();
		TGGRule rule = getRule(match.getRuleName());
		for (String p : match.getParameterNames()) {
			TGGRuleNode node = rule.getNodes().stream().filter(n -> n.getName().equals(p)).findFirst().get();
			if (node.getBindingType().equals(BindingType.CONTEXT) && !node.getDomainType().equals(DomainType.CORR)) {
				candidate.add(node);
			}
		}
		return candidate;
	}

	@Override
	public Set<IMatch> getMatches() {
		try {
			return op.getMatchContainer().getMatches();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Set<IMatch> getMatches(String pRuleName) {
		try {
			return this.getMatches().stream().filter(r -> r.getRuleName().equals(pRuleName))
					.collect(Collectors.toSet());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public Set<IMatch> getMatches(IMatch match) {
		try {
			return this.getMatches(match.getRuleName());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}
