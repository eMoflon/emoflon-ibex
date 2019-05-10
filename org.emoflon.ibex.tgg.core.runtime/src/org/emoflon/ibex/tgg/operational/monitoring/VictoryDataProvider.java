package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collections;
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
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
		Set<TGGRuleNode> candidate = new HashSet<TGGRuleNode>();
		HashMap<String, Set<TGGRuleNode>> nodes = new HashMap<String, Set<TGGRuleNode>>();
		try {
			for (TGGRule rule : op.getOptions().getFlattenedConcreteTGGRules()) {
				for (TGGRuleNode node : rule.getNodes()) {
					if (node.getDomainType() != DomainType.CORR) {
						Set<TGGRuleEdge> edges = node.getOutgoingEdges().stream()
								.filter(x -> x.getBindingType() == BindingType.CREATE).collect(Collectors.toSet());
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
				for (IMatch m : getMatches()) {
					for (String p : m.getParameterNames()) {
						for (TGGRuleNode c : candidate) {
							if (c.getName().equals(p)) {
								list.add((EObject) m.get(p));
								candidate_tmp.add(c);
							}
						}
					}
				}
				candidate = candidate_tmp;
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return list;
	}

	private Set<TGGRuleNode> makeCandidates(HashMap<String, Set<TGGRuleNode>> nodes, IMatch match) {
		Set<TGGRuleNode> candidate = new HashSet<TGGRuleNode>();
		TGGRule rule = getRule(match.getRuleName());
		for (String p : match.getParameterNames()) {
			for (TGGRuleNode node : rule.getNodes()) {
				String nodeName = node.getName();
				if (nodeName.equals(p) && !node.getDomainType().equals(DomainType.CORR)) {
					candidate.addAll(nodes.get(p));
				}
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

