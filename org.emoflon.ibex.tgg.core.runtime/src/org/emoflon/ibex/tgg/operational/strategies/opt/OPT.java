package org.emoflon.ibex.tgg.operational.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.emoflon.ibex.common.collections.IntSet;
import org.emoflon.ibex.common.collections.IntToDoubleMap;
import org.emoflon.ibex.common.collections.IntToObjectMap;
import org.emoflon.ibex.common.collections.ObjectToIntMap;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.IWeightCalculationStrategy;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import language.TGGRuleNode;

public abstract class OPT extends OperationalStrategy {

	protected IntToObjectMap<ITGGMatch> idToMatch = cfactory.createIntToObjectHashMap();
	protected Map<EMFEdge, IntSet> edgeToMarkingMatches = cfactory.createEMFEdgeHashMap();
	protected Map<EObject, IntSet> nodeToMarkingMatches = cfactory.createObjectToObjectHashMap();
	protected ConsistencyReporter consistencyReporter;
	protected int nameCounter = 0;
	protected IntToObjectMap<Set<EObject>> matchToContextNodes = cfactory.createIntToObjectHashMap();
	protected Map<EObject, IntSet> contextNodeToNeedingMatches = cfactory.createObjectToObjectHashMap();
	protected Map<EMFEdge, IntSet> contextEdgeToNeedingMatches = cfactory.createEMFEdgeHashMap();
	protected IntToObjectMap<Set<EMFEdge>> matchToContextEdges = cfactory.createIntToObjectHashMap();
	protected IntToDoubleMap matchToWeight = cfactory.createIntToDoubleMap();
	private IWeightCalculationStrategy userDefinedWeightCalculationStrategy = null;

	protected IntToObjectMap<String> matchIdToRuleName = cfactory.createIntToObjectHashMap();
	protected int idCounter = 1;

	public OPT(final IbexOptions options) throws IOException {
		super(options);
		consistencyReporter = new ConsistencyReporter(this);
	}

	public OPT(final IbexOptions options, final IUpdatePolicy policy) throws IOException {
		super(options, policy);
		consistencyReporter = new ConsistencyReporter(this);
	}

	/**
	 * Sets a custom weight calculation strategy to be used when calculating weights
	 * for matches. These weights are used for the optimization in the ILP problem
	 * to choose matches.
	 *
	 * @param strategy the user defined strategy to use
	 */
	public void setUserDefinedWeightCalculationStrategy(final IWeightCalculationStrategy strategy) {
		this.userDefinedWeightCalculationStrategy = strategy;
	}

	@Override
	public void run() throws IOException {
		do
			matchDistributor.updateMatches();
		while (processOneOperationalRuleMatch());

		this.wrapUp();
	}

	protected abstract void wrapUp();

	protected void defineILPExclusions(final BinaryILPProblem ilpProblem) {
		for (EObject node : nodeToMarkingMatches.keySet()) {
			IntSet variables = nodeToMarkingMatches.get(node);
			if (variables.size() <= 1) {
				// there is only one match creating this node, no exclusion needed
				continue;
			}
			ilpProblem.addExclusion(variables.stream().map(v -> "x" + v),
					"EXCL_nodeOnce_" + node.eClass().getName() + "_" + this.nameCounter++);
		}

		for (EMFEdge edge : edgeToMarkingMatches.keySet()) {
			IntSet variables = edgeToMarkingMatches.get(edge);
			if (variables.size() <= 1) {
				// there is only one match creating this edge, no exclusion needed
				continue;
			}
			ilpProblem.addExclusion(variables.stream().map(v -> "x" + v),
					"EXCL_edgeOnce_" + edge.getType().getName() + "_" + this.nameCounter++);
		}
	}

	protected void defineILPImplications(final BinaryILPProblem ilpProblem) {
		for (EObject node : contextNodeToNeedingMatches.keySet()) {
			IntSet needingMatchIDs = contextNodeToNeedingMatches.get(node);
			IntSet creatingMatchIDs = nodeToMarkingMatches.get(node);
			// only one or none of the creating matches can be chosen (defined by
			// exclusions)
			if (creatingMatchIDs != null && !creatingMatchIDs.isEmpty()) {
				ilpProblem.addNegativeImplication(creatingMatchIDs.stream().map(m -> "x" + m),
						needingMatchIDs.stream().map(m -> "x" + m),
						"IMPL_" + node.eClass().getName() + nameCounter++);
			} else {
				// there is no match creating this node -> forbid all matches needing it
				needingMatchIDs.stream().forEach(m -> {
					ilpProblem.fixVariable("x" + m, false);
				});
			}
		}

		for (EMFEdge edge : contextEdgeToNeedingMatches.keySet()) {
			IntSet needingMatchIDs = contextEdgeToNeedingMatches.get(edge);
			IntSet creatingMatchIDs = edgeToMarkingMatches.get(edge);
			// only one or none of the creating matches can be chosen (defined by
			// exclusions)
			if (creatingMatchIDs != null && !creatingMatchIDs.isEmpty()) {
				ilpProblem.addNegativeImplication(creatingMatchIDs.stream().map(m -> "x" + m),
						needingMatchIDs.stream().map(m -> "x" + m),
						"IMPL" + edge.getType().getName() + nameCounter++);
			} else {
				// there is no match creating this node -> forbid all matches needing it
				needingMatchIDs.stream().forEach(m -> {
					ilpProblem.fixVariable("x" + m, false);
				});
			}
		}
	}

	protected void defineILPObjective(final BinaryILPProblem ilpProblem) {
		ILPLinearExpression expr = ilpProblem.createLinearExpression();
		this.matchToWeight.keySet().stream().forEach(v -> {
			double weight = matchToWeight.get(v);
			expr.addTerm("x" + v, weight);
		});
		ilpProblem.setObjective(expr, Objective.maximize);
	}

	/**
	 * Creates the ILP Problem. Matches become binary variables to choose.
	 * Dependencies between matches are encoded as constraints
	 * 
	 * @return the ILP Problem
	 */
	protected BinaryILPProblem createILPProblem() {
		OperationalStrategy.logger.debug("Creating ILP problem for " + this.idToMatch.size() + " matches");

		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		OperationalStrategy.logger.debug("Adding exclusions...");
		defineILPExclusions(ilpProblem);

		OperationalStrategy.logger.debug("Adding implications...");
		defineILPImplications(ilpProblem);

		OperationalStrategy.logger.debug("Defining objective...");
		defineILPObjective(ilpProblem);

		OperationalStrategy.logger.debug("Adding user defined constraints...");
		addUserDefinedConstraints(ilpProblem);

		return ilpProblem;
	}

	protected int[] chooseTGGRuleApplications() {

		BinaryILPProblem ilpProblem = this.createILPProblem();

		try {
			OperationalStrategy.logger.debug("Attempting to solve ILP");
			ILPSolution ilpSolution = ILPSolver.solveBinaryILPProblem(ilpProblem, this.options.getIlpSolver());
			if (!ilpProblem.checkValidity(ilpSolution)) {
				throw new AssertionError("Invalid solution");
			}

			int[] result = new int[idToMatch.size()];
			idToMatch.keySet().stream().forEach(v -> {
				if (ilpSolution.getVariable("x" + v) > 0)
					result[v - 1] = v;
				else
					result[v - 1] = -v;
			});

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Solving ILP failed", e);
		}
	}

	protected void addUserDefinedConstraints(final BinaryILPProblem ilpProblem) {

	}

	protected Set<EObject> getGreenNodes(final ITGGMatch comatch, final String ruleName) {
		Set<EObject> result = cfactory.createObjectSet();
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenSrcNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenTrgNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenCorrNodesInRule()));
		return result;
	}

	protected Set<EObject> getBlackNodes(final ITGGMatch comatch, final String ruleName) {
		Set<EObject> result = cfactory.createObjectSet();
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackSrcNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackTrgNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackCorrNodesInRule()));
		return result;
	}

	protected Set<EObject> getNodes(final ITGGMatch comatch, final Collection<? extends TGGRuleNode> specNodes) {
		Set<EObject> result = cfactory.createObjectSet();
		specNodes.forEach(n -> {
			result.add((EObject) comatch.get(n.getName()));
		});
		return result;
	}

	protected Set<EMFEdge> getGreenEdges(final ITGGMatch comatch, final String ruleName) {
		Set<EMFEdge> result = cfactory.createEMFEdgeHashSet();
		result.addAll(((IbexGreenInterpreter) greenInterpreter).createEdges(comatch, getGreenFactory(ruleName).getGreenSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter) greenInterpreter).createEdges(comatch, getGreenFactory(ruleName).getGreenTrgEdgesInRule(), false));
		return result;
	}

	protected Set<EMFEdge> getBlackEdges(final ITGGMatch comatch, final String ruleName) {
		Set<EMFEdge> result = cfactory.createEMFEdgeHashSet();
		result.addAll(((IbexGreenInterpreter) greenInterpreter).createEdges(comatch, getGreenFactory(ruleName).getBlackSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter) greenInterpreter).createEdges(comatch, getGreenFactory(ruleName).getBlackTrgEdgesInRule(), false));
		return result;
	}

	protected Collection<EObject> getRuleApplicationNodes(final ITGGMatch comatch) {
		return comatch.getParameterNames().stream().filter(p -> p.endsWith(TGGPatternUtil.protocolNodeSuffix))
				.map(comatch::get).map(EObject.class::cast).collect(Collectors.toList());
	}

	/**
	 * Calculates the weight of the match to be used in the objective function of
	 * the ILP. If a user defined weighting strategy is defined, it is used,
	 * otherwise the strategie's default weighting is used
	 *
	 * @param comatch  The match to calculate the weight for
	 * @param ruleName The name of the rule
	 * @return The calculated weight
	 */
	public final double getWeightForMatch(final IMatch comatch, final String ruleName) {
		if (userDefinedWeightCalculationStrategy != null)
			return userDefinedWeightCalculationStrategy.calculateWeight(ruleName, comatch);
		return getDefaultWeightForMatch(comatch, ruleName);
	}

	/**
	 * Calculates the weight of the match according to the strategie's default
	 * strategy. This is only used if the user has not defined an own
	 * WeightCalculationStrategy
	 *
	 * @param comatch  The match to calculate the weight for
	 * @param ruleName The name of the rule
	 * @return The calculated weight
	 */
	public abstract double getDefaultWeightForMatch(IMatch comatch, String ruleName);

	@Override
	public void terminate() {
		matchDistributor.removeBlackInterpreter();
		for (ITGGMatch m : idToMatch.values()) {
			for (String parameter : m.getParameterNames()) {
				EObject object = (EObject) m.get(parameter);
				object.eAdapters().clear();
				object.eAllContents().forEachRemaining(c -> c.eAdapters().clear());
			}
		}
	}
}
