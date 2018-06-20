package org.emoflon.ibex.tgg.operational.repair.strategies.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.TGG;
import language.TGGRule;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class TGGOverlap {
	protected final static Logger logger = Logger.getLogger(TGGOverlap.class);
	private IbexOptions options;

	private Object2IntOpenHashMap<TGGRuleElement> obj2id;
	private int idCounter;

	public TGGOverlap(IbexOptions options) {
		this.options = options;
		obj2id = new Object2IntOpenHashMap<>();
		reset();
	}

	protected void createILPProblem(TGG tgg) {
		logger.debug("Creating ILP problems for ShortCut-Rules");

		for (int i=0; i < tgg.getRules().size(); i++) {
			for (int j=i; j < tgg.getRules().size(); j++) {
				TGGRule sourceRule = tgg.getRules().get(i);
				TGGRule targetRule = tgg.getRules().get(j);
				if (ruleMatches(sourceRule, targetRule))
					if (sourceRule.equals(targetRule)) {
						createSimpleMapping(sourceRule);
					} else {
						createMappings(sourceRule, targetRule);
					}
			}
		}
	}

	private Overlap createSimpleMapping(TGGRule rule) {
		Collection<Pair<String, String>> mappings = new ArrayList<>();
		for(TGGRuleNode node : rule.getNodes()) {
			mappings.add(Pair.of(node.getName(), node.getName()));
		}
		return new Overlap(rule, rule, mappings, new ArrayList<>(), new ArrayList<>());
	}

	private void createMappings(TGGRule sourceRule, TGGRule targetRule) {
		createILPProblem(sourceRule, targetRule);
	}

	private void createILPProblem(TGGRule sourceRule, TGGRule targetRule) {
		reset();

		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		registerElements(sourceRule);
		registerElements(targetRule);

		logger.debug("Adding exclusions...");
		defineILPExclusions(ilpProblem, sourceRule, targetRule);

		logger.debug("Adding implications...");
		defineILPImplications(ilpProblem, sourceRule, targetRule);

		logger.debug("Defining objective...");
		defineILPObjective(ilpProblem, sourceRule, targetRule);

//		try {
//			logger.debug("Attempting to solve ILP");
//			ILPSolution ilpSolution = ILPSolver.solveBinaryILPProblem(ilpProblem, this.options.getIlpSolver());
//			if (!ilpProblem.checkValidity(ilpSolution)) {
//				throw new AssertionError("Invalid solution");
//			}
//
//			int[] result = new int[idToMatch.size()];
//			idToMatch.keySet().stream().forEach(v -> {
//				if (ilpSolution.getVariable("x" + v) > 0)
//					result[v - 1] = v;
//				else
//					result[v - 1] = -v;
//			});
//
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("Solving ILP failed", e);
//		}
	}

	private void reset() {
		idCounter = 0;
		obj2id.clear();
	}

	private void registerElements(TGGRule rule) {
		for (TGGRuleNode node : rule.getNodes()) {
			obj2id.put(node, idCounter);
			idCounter++;
		}
	}

	private void defineILPImplications(BinaryILPProblem ilpProblem, TGGRule sourceRule, TGGRule targetRule) {

	}

	private void defineILPObjective(BinaryILPProblem ilpProblem, TGGRule sourceRule, TGGRule targetRule) {

	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem, TGGRule sourceRule, TGGRule targetRule) {

	}

	private boolean ruleMatches(TGGRule sourceRule, TGGRule targetRule) {
		// TODO lfritsche : extend inheritance concept
		Set<EClass> classes = new ObjectOpenHashSet<>();
		// TODO lfritsche : insert operationalisation (FWD BWD) splitting
		classes.addAll(sourceRule.getNodes().stream().map(c -> c.getType()).collect(Collectors.toList()));
		for (TGGRuleNode targetNode : targetRule.getNodes()) {
			if (classes.contains(targetNode.getType()))
				return true;
		}
		return false;
	}

	private boolean typeMatches(TGGRuleNode sourceNode, TGGRuleNode targetNode) {
		boolean domainMatches = sourceNode.getDomainType().equals(targetNode.getDomainType());
		boolean typeMatches = sourceNode.getType().equals(targetNode.getType());
		boolean bindingMatches = sourceNode.getBindingType().equals(targetNode.getBindingType());
		return domainMatches && typeMatches && bindingMatches;
	}

	class Overlap {
		public TGGRule sourceRule;
		public TGGRule targetRule;
		public Collection<Pair<String, String>> mappings;
		public Collection<TGGRuleNode> deletions;
		public Collection<TGGRuleNode> creations;

		public Overlap(TGGRule sourceRule, TGGRule targetRule, Collection<Pair<String, String>> mappings, Collection<TGGRuleNode> deletions, Collection<TGGRuleNode> creations) {
			this.sourceRule = sourceRule;
			this.targetRule = targetRule;
			this.mappings = mappings;
			this.deletions = deletions;
			this.creations = creations;
		}
	}
}
