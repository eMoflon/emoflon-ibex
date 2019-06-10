package org.emoflon.ibex.tgg.operational.monitoring;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.apache.commons.io.FilenameUtils;

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

		Set<EObject> neighbors = new HashSet<EObject>();
		Set<EObject> resourceList = new HashSet<EObject>();
		Set<TGGRuleNode> startingPoint = new HashSet<TGGRuleNode>();
		HashMap<String, Set<TGGRuleNode>> nodeRelations = new HashMap<String, Set<TGGRuleNode>>();

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
								if (nodeRelations.containsKey(node.getName())) {
									nodeRelations.get(node.getName()).add(trgNode);
								} else {
									Set<TGGRuleNode> temp = new HashSet<TGGRuleNode>() {
										/**
										 * 
										 */
										private static final long serialVersionUID = 8535800606662854140L;

										{
											add(trgNode);
										}
									};
									nodeRelations.put(node.getName(), temp);
								}
							}
						}
					}
				}
			}

			startingPoint = makeStartingPoints(nodeRelations, match);

			for (int i = 1; i <= k; i++) {
				Set<TGGRuleNode> startingPoint_tmp = new HashSet<TGGRuleNode>();
				Set<TGGRuleNode> finalNodeRelations = new HashSet<TGGRuleNode>();
				for (TGGRuleNode s : startingPoint) {
					if (nodeRelations.containsKey(s.getName())) {
						finalNodeRelations.addAll(nodeRelations.get(s.getName()));
					}
				}

				for (TGGRuleNode s : startingPoint) {
					TGGRuleNode f = finalNodeRelations.stream().filter(x -> x.getName().equals(s.getName())).findFirst()
							.orElse(null);
					if (f != null) {
						finalNodeRelations.remove(f);
					}
				}

				for (TGGRuleNode n : finalNodeRelations) {
					for (EObject r : resourceList) {
						if (r.toString().toLowerCase().indexOf("." + n.getName() + "impl") > 0) {
							EObject p = (EObject) match.get(n.getName());
							if (!r.equals(p)) {
								neighbors.add(r);
								startingPoint_tmp.add(n);
							}
						}
					}
				}
				startingPoint = startingPoint_tmp;
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return neighbors;
	}

	private Set<TGGRuleNode> makeStartingPoints(HashMap<String, Set<TGGRuleNode>> nodes, IMatch match) {
		Set<TGGRuleNode> startingPoint = new HashSet<TGGRuleNode>();
		TGGRule rule = getRule(match.getRuleName());
		for (String p : match.getParameterNames()) {
			TGGRuleNode node = rule.getNodes().stream().filter(n -> n.getName().equals(p)).findFirst().get();
			if (node.getBindingType().equals(BindingType.CONTEXT) && !node.getDomainType().equals(DomainType.CORR)) {
				startingPoint.add(node);
			}
		}
		return startingPoint;
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

	@Override
	public void saveModels() throws IOException {
		Long time = System.currentTimeMillis();
		
		LinkedHashMap<String, Resource> resources = new LinkedHashMap<String, Resource>();
		LinkedHashMap<String, URI> oldUri = new LinkedHashMap<String, URI>();
		
		// storing resources that needs to be saved
		resources.put("s", op.getSourceResource());
		resources.put("t", op.getTargetResource());
		resources.put("c", op.getCorrResource());
		resources.put("p", op.getProtocolResource());
		
		// save models
		for (Entry<String, Resource> e: resources.entrySet()) {
			oldUri.put(e.getKey(), e.getValue().getURI());
			saveModel(e.getValue(), time);
		}
		
		// revert the URIs to before saving models
		for (Entry<String, URI> e: oldUri.entrySet()) {
			resources.get(e.getKey()).setURI(e.getValue());
		}
		
	}

	private void saveModel(Resource r, Long time) throws IOException {
		String path = r.getURI().toString();
		
		// generating new URI (name and path) base on old URI
		String newPath = FilenameUtils.getPath(path);
		newPath += FilenameUtils.getBaseName(path) + "-";
		newPath += time + "." + FilenameUtils.getExtension(path);
		URI newUri = URI.createURI(newPath);

		r.setURI(newUri);
		r.save(null);
	}
}
