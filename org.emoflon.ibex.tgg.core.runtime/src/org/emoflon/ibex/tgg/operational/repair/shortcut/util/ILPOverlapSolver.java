package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Comparator;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import language.DomainType;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ILPOverlapSolver {

	// GENERAL //
	private SupportedILPSolver solver;

	// INPUT //
	private Collection<NodeCandidate> inputNodeCdts;
	private Collection<EdgeCandidate> inputEdgeCdts;
	private Collection<OverlapCandidate> fixedCdts;

	// SOLVING //
	protected Map<Integer, NodeCandidate> id2nodeCdt = cfactory.createIntToObjectHashMap();
	protected Map<Integer, EdgeCandidate> id2edgeCdt = cfactory.createIntToObjectHashMap();
	protected Map<NodeCandidate, Integer> nodeCdt2id = cfactory.createObjectToIntHashMap();
	protected Map<EdgeCandidate, Integer> edgeCdt2id = cfactory.createObjectToIntHashMap();
	protected Map<TGGRuleNode, Set<Integer>> node2cdts = cfactory.createObjectToObjectHashMap();
	protected Map<TGGRuleEdge, Set<Integer>> edge2cdts = cfactory.createObjectToObjectHashMap();
	protected int idCounter = 0;
	protected int nameCounter = 0;
	protected int tmpVarIdCounter = 0;

	// OUTPUT //
	private Collection<NodeCandidate> outputNodeCdts = new LinkedList<>();
	private Collection<EdgeCandidate> outputEdgeCdts = new LinkedList<>();

	public ILPOverlapSolver(Collection<NodeCandidate> nodeCandidates, Collection<EdgeCandidate> edgeCandidates,
			SupportedILPSolver solver) {
		this(nodeCandidates, edgeCandidates, Collections.emptyList(), solver);
	}
	
	public ILPOverlapSolver(Collection<NodeCandidate> nodeCandidates, Collection<EdgeCandidate> edgeCandidates, Collection<OverlapCandidate> fixedCandidates,
			SupportedILPSolver solver) {
		this.inputNodeCdts = nodeCandidates;
		this.inputEdgeCdts = edgeCandidates;
		this.fixedCdts = fixedCandidates;
		this.solver = solver;
		solve();
	}

	public Collection<NodeCandidate> solvedNodeCandidates() {
		return outputNodeCdts;
	}

	public Collection<EdgeCandidate> solvedEdgeCandidates() {
		return outputEdgeCdts;
	}

	private void solve() {
		registerCdts();
		int[] solution = createAndSolveILPProblem();
		convertILPSolutionToCdts(solution);
	}

	private void registerCdts() {
		inputNodeCdts.forEach(this::registerNodeCdt);
		inputEdgeCdts.forEach(this::registerEdgeCdt);
	}

	private void registerNodeCdt(NodeCandidate cdt) {
		nodeCdt2id.put(cdt, idCounter);
		id2nodeCdt.put(idCounter, cdt);
		idCounter++;
		addNode2CdtMapping(cdt.originalNode(), cdt);
		addNode2CdtMapping(cdt.replacingNode(), cdt);
	}

	private void addNode2CdtMapping(TGGRuleNode node, NodeCandidate cdt) {
		Set<Integer> nodeCandidateIDs = node2cdts.getOrDefault(node, cfactory.createIntSet());
		nodeCandidateIDs.add(nodeCdt2id.get(cdt));
		node2cdts.put(node, nodeCandidateIDs);
	}

	private void registerEdgeCdt(EdgeCandidate cdt) {
		if (!node2cdts.containsKey(cdt.originalEdge().getSrcNode()))
			return;
		if (!node2cdts.containsKey(cdt.originalEdge().getTrgNode()))
			return;
		if (!node2cdts.containsKey(cdt.replacingEdge().getSrcNode()))
			return;
		if (!node2cdts.containsKey(cdt.replacingEdge().getTrgNode()))
			return;
		edgeCdt2id.put(cdt, idCounter);
		id2edgeCdt.put(idCounter, cdt);
		idCounter++;
		addEdge2CdtMapping(cdt.originalEdge(), cdt);
		addEdge2CdtMapping(cdt.replacingEdge(), cdt);
	}

	private void addEdge2CdtMapping(TGGRuleEdge edge, EdgeCandidate cdt) {
		Set<Integer> edgeCandidateIDs = edge2cdts.getOrDefault(edge, cfactory.createIntSet());
		edgeCandidateIDs.add(edgeCdt2id.get(cdt));
		edge2cdts.put(edge, edgeCandidateIDs);
	}

	private int[] createAndSolveILPProblem() {
		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		defineFixedCandidates(ilpProblem);
		defineILPExclusions(ilpProblem);
		defineILPImplications(ilpProblem);
		defineMoreILPConditions(ilpProblem);
		defineILPObjective(ilpProblem);
		
		LoggerConfig.log(LoggerConfig.log_ilp_extended(), () -> "ILP problem:\n" + ilpProblem + "\n");

		try {
			ILPSolution ilpSolution = ILPSolver.solveBinaryILPProblem(ilpProblem, solver);
			if (!ilpProblem.checkValidity(ilpSolution)) {
				throw new AssertionError("Invalid solution");
			}
			
			LoggerConfig.log(LoggerConfig.log_ilp_extended(), () -> printILPSolution(ilpSolution) + "\n");

			int[] result = new int[idCounter];
			for (int i = 0; i < idCounter; i++) {
				if (ilpSolution.getVariable("x" + i) > 0)
					result[i] = 1;
				else
					result[i] = -1;
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Solving ILP failed", e);
		}
	}

	private void defineFixedCandidates(BinaryILPProblem ilpProblem) {
		for (OverlapCandidate fixedCdt : fixedCdts) {
			Integer fixedCdtID = nodeCdt2id.get(fixedCdt);
			if (fixedCdtID == null) {
				fixedCdtID = edgeCdt2id.get(fixedCdt);
				if (fixedCdtID == null)
					continue;
			}
			ILPLinearExpression expr = ilpProblem.createLinearExpression();
			expr.addTerm("x" + fixedCdtID, 1);
			ilpProblem.addConstraint(expr, Comparator.eq, 1.0, //
					"CONSTR_fixedCandidate_" + nameCounter++);
		}
	}

	private void defineILPImplications(BinaryILPProblem ilpProblem) {
		defineEdgeSrcTrgNodeImplications(ilpProblem);
		defineCorrNodeSrcTrgEdgeImplications(ilpProblem);
		defineCorrNodeSrcTrgNodeImplications(ilpProblem);
	}

	private void defineEdgeSrcTrgNodeImplications(BinaryILPProblem ilpProblem) {
		for (EdgeCandidate edgeCdt : edgeCdt2id.keySet()) {
			Integer edgeCdtID = edgeCdt2id.get(edgeCdt);
			
			NodeCandidate srcNodeCdt = new NodeCandidate(edgeCdt.originalEdge().getSrcNode(), edgeCdt.replacingEdge().getSrcNode());
			NodeCandidate trgNodeCdt = new NodeCandidate(edgeCdt.originalEdge().getTrgNode(), edgeCdt.replacingEdge().getTrgNode());
			Integer srcNodeCdtID = nodeCdt2id.get(srcNodeCdt);
			Integer trgNodeCdtID = nodeCdt2id.get(trgNodeCdt);
			
			if (srcNodeCdtID == null || trgNodeCdtID == null) {
				ILPLinearExpression expr = ilpProblem.createLinearExpression();
				expr.addTerm("x" + edgeCdtID, 1);
				ilpProblem.addConstraint(expr, Comparator.eq, 0.0, //
						"CONSTR_doNotChooseEdge_" + edgeCdt.originalEdge().getType().getName() + "_" + nameCounter++);
			} else {
				ilpProblem.addImplication("x" + edgeCdtID, Stream.of("x" + srcNodeCdtID), //
						"IMPL_edge->srcNode_" + edgeCdt.originalEdge().getType().getName() + "_" + nameCounter++);
				ilpProblem.addImplication("x" + edgeCdtID, Stream.of("x" + trgNodeCdtID), //
						"IMPL_edge->trgNode_" + edgeCdt.originalEdge().getType().getName() + "_" + nameCounter++);
			}
		}
	}

	private void defineCorrNodeSrcTrgEdgeImplications(BinaryILPProblem ilpProblem) {
		for (NodeCandidate nodeCdt : nodeCdt2id.keySet()) {
			if (!(nodeCdt.originalNode() instanceof TGGRuleCorr origCorr && nodeCdt.replacingNode() instanceof TGGRuleCorr replCorr ))
				continue;
			
			TGGRuleEdge origSrcEdge = null;
			TGGRuleEdge origTrgEdge = null;
			for (TGGRuleEdge edgeOrigCorr : origCorr.getOutgoingEdges()) {
				if (edgeOrigCorr.getTrgNode().getDomainType() == DomainType.SRC)
					origSrcEdge = edgeOrigCorr;
				else if (edgeOrigCorr.getTrgNode().getDomainType() == DomainType.TRG)
					origTrgEdge = edgeOrigCorr;
			}
			
			TGGRuleEdge replSrcEdge = null;
			TGGRuleEdge replTrgEdge = null;
			for (TGGRuleEdge edgeReplCorr : replCorr.getOutgoingEdges()) {
				if (edgeReplCorr.getTrgNode().getDomainType() == DomainType.SRC)
					replSrcEdge = edgeReplCorr;
				else if (edgeReplCorr.getTrgNode().getDomainType() == DomainType.TRG)
					replTrgEdge = edgeReplCorr;
			}
			
			EdgeCandidate srcEdgeCdt = new EdgeCandidate(origSrcEdge, replSrcEdge);
			EdgeCandidate trgEdgeCdt = new EdgeCandidate(origTrgEdge, replTrgEdge);
			Integer srcEdgeCdtID = edgeCdt2id.get(srcEdgeCdt);
			Integer trgEdgeCdtID = edgeCdt2id.get(trgEdgeCdt);
			Integer nodeCdtID = nodeCdt2id.get(nodeCdt);
			
			if (srcEdgeCdtID == null || trgEdgeCdtID == null) {
				ILPLinearExpression expr = ilpProblem.createLinearExpression();
				expr.addTerm("x" + nodeCdtID, 1);
				ilpProblem.addConstraint(expr, Comparator.eq, 0.0, //
						"CONSTR_doNotChooseCorrNode_" + nodeCdt.originalNode().getName() + "_" + nameCounter++);
			} else {
				ilpProblem.addImplication("x" + nodeCdtID, Stream.of("x" + srcEdgeCdtID), //
						"IMPL_corrNode->srcEdge_" + nodeCdt.originalNode().getName() + "_" + nameCounter++);
				ilpProblem.addImplication("x" + nodeCdtID, Stream.of("x" + trgEdgeCdtID), //
						"IMPL_corrNode->trgEdge_" + nodeCdt.originalNode().getName() + "_" + nameCounter++);
			}
		}
	}

	private void defineCorrNodeSrcTrgNodeImplications(BinaryILPProblem ilpProblem) {
		Set<TGGRuleCorr> replCorrs = nodeCdt2id.keySet().stream() //
				.map(cdt -> cdt.replacingNode()) //
				.filter(n -> n instanceof TGGRuleCorr) //
				.map(n -> (TGGRuleCorr) n) //
				.collect(Collectors.toSet());
		
		for (TGGRuleCorr corr : replCorrs) {
			Set<Integer> corrCdtIDs = node2cdts.get(corr);
			if (corrCdtIDs == null || corrCdtIDs.isEmpty())
				continue;
			
			Set<Integer> srcNodeCdtIDs = node2cdts.get(corr.getSource());
			Set<Integer> trgNodeCdtIDs = node2cdts.get(corr.getTarget());
			
			boolean noSrcNodeCdts = srcNodeCdtIDs == null || srcNodeCdtIDs.isEmpty();
			boolean noTrgNodeCdts = trgNodeCdtIDs == null || trgNodeCdtIDs.isEmpty();
			
			if (noSrcNodeCdts || noTrgNodeCdts)
				continue;
			
			ILPLinearExpression mainExpr = ilpProblem.createLinearExpression();
			for (int corrCdtID : corrCdtIDs)
				mainExpr.addTerm("x" + corrCdtID, 1.0);
			
			int mainConstant = 1;
			if (srcNodeCdtIDs.size() == 1) {
				mainExpr.addTerm("x" + srcNodeCdtIDs.iterator().next(), -1.0);
				mainConstant--;
			} else {
				mainExpr.addTerm("s" + tmpVarIdCounter, 1.0);
				
				ILPLinearExpression subSrcExpr = ilpProblem.createLinearExpression();
				for (int srcNodeCdtID : srcNodeCdtIDs)
					subSrcExpr.addTerm("x" + srcNodeCdtID, 1.0);
				subSrcExpr.addTerm("s" + tmpVarIdCounter, srcNodeCdtIDs.size());
				
				ilpProblem.addConstraint(subSrcExpr, Comparator.ge, 1, //
						"CONSTR_corrSrcTrg_subSrc_lb_" + corr.getName() + "_" + nameCounter);
				ilpProblem.addConstraint(subSrcExpr, Comparator.le, srcNodeCdtIDs.size(), //
						"CONSTR_corrSrcTrg_subSrc_ub_" + corr.getName() + "_" + nameCounter);
			}
			
			if (trgNodeCdtIDs.size() == 1) {
				mainExpr.addTerm("x" + trgNodeCdtIDs.iterator().next(), -1.0);
				mainConstant--;
			} else {
				mainExpr.addTerm("t" + tmpVarIdCounter, 1.0);
				
				ILPLinearExpression subTrgExpr = ilpProblem.createLinearExpression();
				for (int trgNodeCdtID : trgNodeCdtIDs)
					subTrgExpr.addTerm("x" + trgNodeCdtID, 1.0);
				subTrgExpr.addTerm("t" + tmpVarIdCounter, trgNodeCdtIDs.size());
				
				ilpProblem.addConstraint(subTrgExpr, Comparator.ge, 1, //
						"CONSTR_corrSrcTrg_subTrg_lb_" + corr.getName() + "_" + nameCounter);
				ilpProblem.addConstraint(subTrgExpr, Comparator.le, trgNodeCdtIDs.size(), //
						"CONSTR_corrSrcTrg_subTrg_ub_" + corr.getName() + "_" + nameCounter);
			}
			tmpVarIdCounter++;
			
			ilpProblem.addConstraint(mainExpr, Comparator.ge, mainConstant, //
					"CONSTR_corrSrcTrg_main_" + corr.getName() + "_" + nameCounter++);
		}
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem) {
		node2cdts.keySet().forEach(node -> defineILPExclusions(ilpProblem, node));
		edge2cdts.keySet().forEach(edge -> defineILPExclusions(ilpProblem, edge));
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem, TGGRuleNode node) {
		Set<Integer> candidates = node2cdts.get(node);
		if (candidates.size() <= 1)
			return;

		ilpProblem.addExclusion(candidates.stream().map(v -> "x" + v),
				"EXCL_nodeJustOnce_" + node.eClass().getName() + "_" + nameCounter++);
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem, TGGRuleEdge edge) {
		Set<Integer> candidates = edge2cdts.get(edge);
		if (candidates.size() <= 1)
			return;

		ilpProblem.addExclusion(candidates.stream().map(v -> "x" + v),
				"EXCL_edgeJustOnce_" + edge.eClass().getName() + "_" + nameCounter++);
	}

	protected void defineMoreILPConditions(BinaryILPProblem ilpProblem) {
		// NO-OP
	}

	protected void defineILPObjective(BinaryILPProblem ilpProblem) {
		ILPLinearExpression expr = ilpProblem.createLinearExpression();

		for (int i = 0; i < idCounter; i++)
			expr.addTerm("x" + i, 1);

		ilpProblem.setObjective(expr, Objective.maximize);
	}

	private void convertILPSolutionToCdts(int[] solution) {
		for (int i = 0; i < solution.length; i++) {
			boolean useCandidate = solution[i] == 1;
			if (!useCandidate)
				continue;
			if (id2nodeCdt.containsKey(i))
				outputNodeCdts.add(id2nodeCdt.get(i));
			if (id2edgeCdt.containsKey(i))
				outputEdgeCdts.add(id2edgeCdt.get(i));
		}
	}

	private String printILPSolution(ILPSolution ilpSolution) {
		StringBuilder builder = new StringBuilder();
		builder.append("Mappings:\n");
		for (int i = 0; i < idCounter; i++) {
			String v = "x" + i;
			if (id2nodeCdt.containsKey(i))
				builder.append("  " + v + ": " + ilpSolution.getVariable(v) + " | " + id2nodeCdt.get(i) + "\n");
			if (id2edgeCdt.containsKey(i))
				builder.append("  " + v + ": " + ilpSolution.getVariable(v) + " | " + id2edgeCdt.get(i) + "\n");
		}
		return builder.toString();
	}

}
