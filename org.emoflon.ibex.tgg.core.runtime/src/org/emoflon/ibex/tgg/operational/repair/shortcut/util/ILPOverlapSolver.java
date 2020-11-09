package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapUtil.EdgeCandidate;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapUtil.NodeCandidate;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ILPOverlapSolver {

	// GENERAL //
	private SupportedILPSolver solver;

	// INPUT //
	private Collection<NodeCandidate> inputNodeCdts;
	private Collection<EdgeCandidate> inputEdgeCdts;

	// SOLVING //
	protected Map<Integer, NodeCandidate> id2nodeCdt = cfactory.createIntToObjectHashMap();
	protected Map<Integer, EdgeCandidate> id2edgeCdt = cfactory.createIntToObjectHashMap();
	protected Map<NodeCandidate, Integer> nodeCdt2id = cfactory.createObjectToIntHashMap();
	protected Map<EdgeCandidate, Integer> edgeCdt2id = cfactory.createObjectToIntHashMap();
	protected Map<TGGRuleNode, Set<Integer>> node2cdts = cfactory.createObjectToObjectHashMap();
	protected Map<TGGRuleEdge, Set<Integer>> edge2cdts = cfactory.createObjectToObjectHashMap();
	protected int idCounter = 0;
	protected int nameCounter = 0;

	// OUTPUT //
	private Collection<NodeCandidate> outputNodeCdts = new LinkedList<>();
	private Collection<EdgeCandidate> outputEdgeCdts = new LinkedList<>();

	public ILPOverlapSolver(Collection<NodeCandidate> nodeCandidates, Collection<EdgeCandidate> edgeCandidates,
			SupportedILPSolver solver) {
		this.inputNodeCdts = nodeCandidates;
		this.inputEdgeCdts = edgeCandidates;
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
		addNode2CdtMapping(cdt.originalNode, cdt);
		addNode2CdtMapping(cdt.replacingNode, cdt);
	}

	private void addNode2CdtMapping(TGGRuleNode node, NodeCandidate cdt) {
		Set<Integer> nodeCandidateIDs = node2cdts.getOrDefault(node, cfactory.createIntSet());
		nodeCandidateIDs.add(nodeCdt2id.get(cdt));
		node2cdts.put(node, nodeCandidateIDs);
	}

	private void registerEdgeCdt(EdgeCandidate cdt) {
		if (!node2cdts.containsKey(cdt.originalEdge.getSrcNode()))
			return;
		if (!node2cdts.containsKey(cdt.originalEdge.getTrgNode()))
			return;
		if (!node2cdts.containsKey(cdt.replacingEdge.getSrcNode()))
			return;
		if (!node2cdts.containsKey(cdt.replacingEdge.getTrgNode()))
			return;
		edgeCdt2id.put(cdt, idCounter);
		id2edgeCdt.put(idCounter, cdt);
		idCounter++;
		addEdge2CdtMapping(cdt.originalEdge, cdt);
		addEdge2CdtMapping(cdt.replacingEdge, cdt);
	}

	private void addEdge2CdtMapping(TGGRuleEdge edge, EdgeCandidate cdt) {
		Set<Integer> edgeCandidateIDs = edge2cdts.getOrDefault(edge, cfactory.createIntSet());
		edgeCandidateIDs.add(edgeCdt2id.get(cdt));
		edge2cdts.put(edge, edgeCandidateIDs);
	}

	private int[] createAndSolveILPProblem() {
		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		defineILPExclusions(ilpProblem);
		defineILPImplications(ilpProblem);
		defineMoreILPConditions(ilpProblem);
		defineILPObjective(ilpProblem);

		try {
			ILPSolution ilpSolution = ILPSolver.solveBinaryILPProblem(ilpProblem, solver);
			if (!ilpProblem.checkValidity(ilpSolution)) {
				throw new AssertionError("Invalid solution");
			}

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

	private void defineILPImplications(BinaryILPProblem ilpProblem) {
		for (TGGRuleEdge edge : edge2cdts.keySet()) {
			Set<Integer> srcNodeIDs = node2cdts.getOrDefault(edge.getSrcNode(), cfactory.createIntSet());
			ilpProblem.addNegativeImplication(srcNodeIDs.stream().map(m -> "x" + m),
					edge2cdts.get(edge).stream().map(m -> "x" + m),
					"IMPL_" + edge.getType().getName() + nameCounter++);

			Set<Integer> trgNodeIDs = node2cdts.getOrDefault(edge.getTrgNode(), cfactory.createIntSet());
			ilpProblem.addNegativeImplication(trgNodeIDs.stream().map(m -> "x" + m),
					edge2cdts.get(edge).stream().map(m -> "x" + m),
					"IMPL_" + edge.getType().getName() + nameCounter++);
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

}
