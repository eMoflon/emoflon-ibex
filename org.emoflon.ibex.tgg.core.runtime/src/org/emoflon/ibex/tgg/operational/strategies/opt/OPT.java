package org.emoflon.ibex.tgg.operational.strategies.opt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntSet;
import org.emoflon.ibex.common.collections.IntToObjectMap;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.IWeightCalculationStrategy;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.opt.cc.Bundle;
import org.emoflon.ibex.tgg.operational.strategies.opt.cc.ConsistencyReporter;
import org.emoflon.ibex.tgg.operational.strategies.opt.cc.HandleDependencies;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.TGGRuleNode;

public abstract class OPT extends OperationalStrategy {

	protected IntToObjectMap<IMatch> idToMatch = CollectionFactory.INSTANCE.createIntToObjectHashMap();
	protected Map<EMFEdge, IntSet> edgeToMarkingMatches = CollectionFactory.INSTANCE.createEMFEdgeHashMap();
	protected Map<EObject, IntSet> nodeToMarkingMatches = CollectionFactory.INSTANCE.createObjectToObjectHashMap();
	protected ConsistencyReporter consistencyReporter = new ConsistencyReporter();
	protected int nameCounter = 0;
	protected IntToObjectMap<Set<EObject>> matchToContextNodes = CollectionFactory.INSTANCE.createIntToObjectHashMap();
	protected Map<EObject, IntSet> contextNodeToNeedingMatches = CollectionFactory.INSTANCE.createObjectToObjectHashMap();
	protected Map<EMFEdge, IntSet> contextEdgeToNeedingMatches = CollectionFactory.INSTANCE.createEMFEdgeHashMap();
	protected IntToObjectMap<Set<EMFEdge>> matchToContextEdges = CollectionFactory.INSTANCE.createIntToObjectHashMap();
	protected Int2DoubleOpenHashMap matchToWeight = new Int2DoubleOpenHashMap();
	private IWeightCalculationStrategy userDefinedWeightCalculationStrategy = null;

	/**
	 * Collection of constraints to guarantee uniqueness property; key: Complement
	 * rule (CR) match ID; value: other CR matches of the same CR using the same
	 * context as CR match
	 */
	protected IntToObjectMap<IntSet> sameComplementMatches = CollectionFactory.INSTANCE.createIntToObjectHashMap();

	/**
	 * Collection of constraints to guarantee maximality property; value: kernels
	 * whose complement rules did not fulfill maximality property
	 */
	protected IntSet invalidKernels = CollectionFactory.INSTANCE.createIntSet();

	/**
	 * Collection of constraints to guarantee cyclic dependences are avoided; value:
	 * correctly applied bundles (kernel match + its CRs matches)
	 */
	protected Set<Bundle> appliedBundles = new ObjectLinkedOpenHashSet<Bundle>();
	protected Bundle lastAppliedBundle;

	protected IntToObjectMap<String> matchIdToRuleName = CollectionFactory.INSTANCE.createIntToObjectHashMap();
	protected int idCounter = 1;

	// Hash maps to save the old metamodel state
	protected Object2IntOpenHashMap<EReference> referenceToUpperBound = new Object2IntOpenHashMap<>();
	protected Object2IntOpenHashMap<EReference> referenceToLowerBound = new Object2IntOpenHashMap<>();
	protected Object2ObjectOpenHashMap<EReference, EReference> referenceToEOpposite = new Object2ObjectOpenHashMap<EReference, EReference>();
	protected Object2ObjectOpenHashMap<EReference, Boolean> referenceToContainment = new Object2ObjectOpenHashMap<EReference, Boolean>();

	public OPT(final IbexOptions options) throws IOException {
		super(options);
	}

	public OPT(final IbexOptions options, final IUpdatePolicy policy) {
		super(options, policy);
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
			this.blackInterpreter.updateMatches();
		while (this.processOneOperationalRuleMatch());

		this.wrapUp();
	}

	protected abstract void wrapUp();

	public void relaxReferences(final EList<EPackage> model) {
		EPackage[] packages = (EPackage[]) model.toArray();

		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();

			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl) next;

					for (EReference reference : nextEClassImpl.getEAllReferences()) {
						if (this.referenceToUpperBound.containsKey(reference) && this.referenceToLowerBound.containsKey(reference)
								&& this.referenceToContainment.containsKey(reference)
								&& this.referenceToEOpposite.containsKey(reference)) {
							// Reference already exists, values must not be overwritten
							continue;
						}

						// Save metamodel values
						this.referenceToUpperBound.put(reference, reference.getUpperBound());
						this.referenceToLowerBound.put(reference, reference.getLowerBound());
						this.referenceToContainment.put(reference, reference.isContainment());
						this.referenceToEOpposite.put(reference, reference.getEOpposite());

						// Change metamodel values
						reference.setUpperBound(-1);
						reference.setLowerBound(0);
						reference.setContainment(false);
						reference.setEOpposite(null);
					}
				}
			}
		}
	}

	public void unrelaxReferences(final EList<EPackage> model) {

		EPackage[] packages = (EPackage[]) model.toArray();

		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();

			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl) next;

					for (EReference reference : nextEClassImpl.getEAllReferences()) {
						// Get old metamodel values
						int upperBound = this.referenceToUpperBound.getInt(reference);
						int lowerBound = this.referenceToLowerBound.getInt(reference);
						boolean containment = this.referenceToContainment.get(reference);
						EReference eOpposite = this.referenceToEOpposite.get(reference);

						// Change metamodel values
						reference.setUpperBound(upperBound);
						reference.setLowerBound(lowerBound);
						reference.setContainment(containment);
						reference.setEOpposite(eOpposite);

						// Reset setting for reference
						((EStructuralFeatureImpl) reference).setSettingDelegate(null);
					}
				}
			}
		}
	}

	protected void defineILPExclusions(final BinaryILPProblem ilpProblem) {
		for (EObject node : this.nodeToMarkingMatches.keySet()) {
			IntSet variables = this.nodeToMarkingMatches.get(node);
			if (variables.size() <= 1) {
				// there is only one match creating this node, no exclusion needed
				continue;
			}
			ilpProblem.addExclusion(variables.stream().map(v -> "x" + v),
					"EXCL_nodeOnce_" + node.eClass().getName() + "_" + this.nameCounter++);
		}

		for (EMFEdge edge : this.edgeToMarkingMatches.keySet()) {
			IntSet variables = this.edgeToMarkingMatches.get(edge);
			if (variables.size() <= 1) {
				// there is only one match creating this edge, no exclusion needed
				continue;
			}
			ilpProblem.addExclusion(variables.stream().map(v -> "x" + v),
					"EXCL_edgeOnce_" + edge.getType().getName() + "_" + this.nameCounter++);
		}

		for (int match : this.sameComplementMatches.keySet()) {
			IntSet variables = this.sameComplementMatches.get(match);
			ilpProblem.addExclusion(variables.stream().map(v -> "x" + v),
					"EXCL_sameCompl" + this.nameCounter++);
		}

		if (!this.invalidKernels.isEmpty()) {
			IntSet variables = this.invalidKernels;
			variables.stream().forEach(v -> {
				ilpProblem.fixVariable("x" + v, false);
			});
		}

		HandleDependencies handleCycles = new HandleDependencies(this.appliedBundles, this.edgeToMarkingMatches,
				this.nodeToMarkingMatches, this.matchToContextNodes, this.matchToContextEdges);
		Set<Integer> allCyclicBundles = handleCycles.getCyclicDependenciesBetweenBundles().keySet();

		for (int cycle : allCyclicBundles) {
			Set<List<Integer>> cyclicConstraints = this.getCyclicConstraints(handleCycles.getRuleApplications(cycle));
			for (List<Integer> variables : cyclicConstraints) {
				ilpProblem.addExclusion(variables.stream().map(v -> "x" + v),
						"EXCL_cycle" + this.nameCounter++, variables.size() - 1);
			}
		}
	}

	private Set<List<Integer>> getCyclicConstraints(final List<IntLinkedOpenHashSet> dependentRuleApplications) {
		List<HashSet<Integer>> sets = new ArrayList<>();
		for (IntLinkedOpenHashSet tHashSet : dependentRuleApplications) {
			HashSet<Integer> set = new HashSet<>();
			IntIterator it = tHashSet.iterator();
			while (it.hasNext())
				set.add(it.nextInt());
			sets.add(set);
		}
		return Sets.cartesianProduct(sets);
	}

	protected void defineILPImplications(final BinaryILPProblem ilpProblem) {
		for (EObject node : this.contextNodeToNeedingMatches.keySet()) {
			IntSet needingMatchIDs = this.contextNodeToNeedingMatches.get(node);
			IntSet creatingMatchIDs = this.nodeToMarkingMatches.get(node);
			// only one or none of the creating matches can be chosen (defined by
			// exclusions)
			if (creatingMatchIDs != null && !creatingMatchIDs.isEmpty()) {
				ilpProblem.addNegativeImplication(
						creatingMatchIDs.stream().map(m -> "x" + m),
						needingMatchIDs.stream().map(m -> "x" + m),
						"IMPL_" + node.eClass().getName() + this.nameCounter++);
			} else {
				// there is no match creating this node -> forbid all matches needing it
				needingMatchIDs.stream().forEach(m -> {
					ilpProblem.fixVariable("x" + m, false);
				});
			}
		}

		for (EMFEdge edge : this.contextEdgeToNeedingMatches.keySet()) {
			IntSet needingMatchIDs = this.contextEdgeToNeedingMatches.get(edge);
			IntSet creatingMatchIDs = this.edgeToMarkingMatches.get(edge);
			// only one or none of the creating matches can be chosen (defined by
			// exclusions)
			if (creatingMatchIDs != null && !creatingMatchIDs.isEmpty()) {
				ilpProblem.addNegativeImplication(
						creatingMatchIDs.stream().map(m -> "x" + m),
						needingMatchIDs.stream().map(m -> "x" + m),
						"IMPL" + edge.getType().getName() + this.nameCounter++);
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
		this.matchToWeight.keySet().stream().forEach((IntConsumer) (v -> {
			double weight = this.matchToWeight.get(v);
			expr.addTerm("x" + v, weight);
		}));
		ilpProblem.setObjective(expr, Objective.maximize);
	}
	
	/**
	 * Creates the ILP Problem. Matches become binary variables to choose. Dependencies between matches are
	 * encoded as constraints 
	 * @return the ILP Problem
	 */
	protected BinaryILPProblem createILPProblem() {
		OperationalStrategy.logger.debug("Creating ILP problem for " + this.idToMatch.size() + " matches");

		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		OperationalStrategy.logger.debug("Adding exclusions...");
		this.defineILPExclusions(ilpProblem);

		OperationalStrategy.logger.debug("Adding implications...");
		this.defineILPImplications(ilpProblem);

		OperationalStrategy.logger.debug("Defining objective...");
		this.defineILPObjective(ilpProblem);

		OperationalStrategy.logger.debug("Adding user defined constraints...");
		this.addUserDefinedConstraints(ilpProblem);
		
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

			int[] result = new int[this.idToMatch.size()];
			this.idToMatch.keySet().stream().forEach(v -> {
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

	protected void handleBundles(final IMatch comatch, final String ruleName) {
		if (!this.getComplementRule(ruleName).isPresent()) {
			Bundle appliedBundle = new Bundle(this.idCounter);
			this.appliedBundles.add(appliedBundle);
			this.lastAppliedBundle = appliedBundle;
		}

		this.lastAppliedBundle.addMatch(this.idCounter);

		// add context nodes and edges of this concrete match to its bundle
		this.lastAppliedBundle.addBundleContextNodes(this.getBlackNodes(comatch, ruleName));
		this.lastAppliedBundle.addBundleContextEdges(this.getBlackEdges(comatch, ruleName));
	}

	protected ObjectOpenHashSet<EObject> getGreenNodes(final IMatch comatch, final String ruleName) {
		ObjectOpenHashSet<EObject> result = new ObjectOpenHashSet<>();
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getGreenSrcNodesInRule()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getGreenTrgNodesInRule()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getGreenCorrNodesInRule()));
		return result;
	}

	protected ObjectOpenHashSet<EObject> getBlackNodes(final IMatch comatch, final String ruleName) {
		ObjectOpenHashSet<EObject> result = new ObjectOpenHashSet<>();
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getBlackSrcNodesInRule()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getBlackTrgNodesInRule()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getBlackCorrNodesInRule()));
		return result;
	}

	protected ObjectOpenHashSet<EObject> getNodes(final IMatch comatch, final Collection<? extends TGGRuleNode> specNodes) {
		ObjectOpenHashSet<EObject> result = new ObjectOpenHashSet<>();
		specNodes.forEach(n -> {
			result.add((EObject) comatch.get(n.getName()));
		});
		return result;
	}

	protected ObjectOpenHashSet<EMFEdge> getGreenEdges(final IMatch comatch, final String ruleName) {
		ObjectOpenHashSet<EMFEdge> result = new ObjectOpenHashSet<>();
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getGreenSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getGreenTrgEdgesInRule(), false));
		return result;
	}

	protected ObjectOpenHashSet<EMFEdge> getBlackEdges(final IMatch comatch, final String ruleName) {
		ObjectOpenHashSet<EMFEdge> result = new ObjectOpenHashSet<>();
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getBlackSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getBlackTrgEdgesInRule(), false));
		return result;
	}

	protected Collection<EObject> getRuleApplicationNodes(final IMatch comatch) {
		return comatch.getParameterNames().stream().filter(p -> p.endsWith(ConsistencyPattern.protocolNodeSuffix))
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
		if (this.userDefinedWeightCalculationStrategy != null)
			return this.userDefinedWeightCalculationStrategy.calculateWeight(ruleName, comatch);
		return this.getDefaultWeightForMatch(comatch, ruleName);
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
	public Resource loadResource(final String workspaceRelativePath) throws IOException {
		return super.loadResource(workspaceRelativePath);
	}

	@Override
	protected void removeBlackInterpreter() {
		super.removeBlackInterpreter();
		for (IMatch m : this.idToMatch.values()) {
			for (String parameter : m.getParameterNames()) {
				EObject object = (EObject) m.get(parameter);
				object.eAdapters().clear();
				object.eAllContents().forEachRemaining(c -> c.eAdapters().clear());
			}
		}
	}
}
