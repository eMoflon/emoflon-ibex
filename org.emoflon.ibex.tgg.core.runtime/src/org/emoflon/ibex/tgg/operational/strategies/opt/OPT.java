package org.emoflon.ibex.tgg.operational.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntSet;
import org.emoflon.ibex.common.collections.IntToDoubleMap;
import org.emoflon.ibex.common.collections.IntToObjectMap;
import org.emoflon.ibex.common.collections.ObjectToIntMap;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGUtil;
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

import language.TGGRule;
import language.TGGRuleNode;

public abstract class OPT extends OperationalStrategy {

	protected int nameCounter = 0;
	protected IntToObjectMap<IMatch> idToMatch = cfactory.createIntToObjectHashMap();
	protected ConsistencyReporter consistencyReporter = new ConsistencyReporter();
	private IWeightCalculationStrategy userDefinedWeightCalculationStrategy = null;

	protected Map<EMFEdge, IntSet> edgeToMarkingMatches = cfactory.createEMFEdgeHashMap();
	protected Map<EObject, IntSet> nodeToMarkingMatches = cfactory.createObjectToObjectHashMap();
	protected Map<EObject, IntSet> contextNodeToNeedingMatches = cfactory.createObjectToObjectHashMap();
	protected Map<EMFEdge, IntSet> contextEdgeToNeedingMatches = cfactory.createEMFEdgeHashMap();

	protected IntToObjectMap<Set<EObject>> matchToContextNodes = cfactory.createIntToObjectHashMap();
	protected IntToObjectMap<Set<EMFEdge>> matchToContextEdges = cfactory.createIntToObjectHashMap();
	protected IntToObjectMap<Set<EObject>> markingMatchToNodes = cfactory.createIntToObjectHashMap();
	protected IntToObjectMap<Set<EMFEdge>> markingMatchToEdges = cfactory.createIntToObjectHashMap();
	protected IntToDoubleMap matchToWeight = cfactory.createIntToDoubleMap();

	// usability-team
	protected Map<EObject, IntSet> negativeNodeToNeedingMatches = cfactory.createObjectToObjectHashMap();
	protected Map<EMFEdge, IntSet> negativeEdgeToNeedingMatches = cfactory.createEMFEdgeHashMap();
	protected IntToObjectMap<Set<EObject>> matchToNegativeNodes = cfactory.createIntToObjectHashMap();
	protected IntToObjectMap<Set<EMFEdge>> matchToNegativeEdges = cfactory.createIntToObjectHashMap();

	/**
	 * Collection of constraints to guarantee uniqueness property; key: Complement
	 * rule (CR) match ID; value: other CR matches of the same CR using the same
	 * context as CR match
	 */
	protected IntToObjectMap<IntSet> sameComplementMatches = cfactory.createIntToObjectHashMap();

	/**
	 * Collection of constraints to guarantee maximality property; value: kernels
	 * whose complement rules did not fulfill maximality property
	 */
	protected IntSet invalidKernels = cfactory.createIntSet();

	/**
	 * Collection of constraints to guarantee cyclic dependences are avoided; value:
	 * correctly applied bundles (kernel match + its CRs matches)
	 */
	protected Set<Bundle> appliedBundles = cfactory.createObjectSet();
	protected Bundle lastAppliedBundle;

	protected IntToObjectMap<String> matchIdToRuleName = cfactory.createIntToObjectHashMap();
	protected Map<String, IntSet> ruleNameToMatchId = cfactory.createObjectToObjectHashMap();

	protected int idCounter = 1;

	// Hash maps to save the old metamodel state
	protected ObjectToIntMap<EReference> referenceToUpperBound = cfactory.createObjectToIntHashMap();
	protected ObjectToIntMap<EReference> referenceToLowerBound = cfactory.createObjectToIntHashMap();
	protected Map<EReference, EReference> referenceToEOpposite = cfactory.createObjectToObjectHashMap();
	protected Map<EReference, Boolean> referenceToContainment = cfactory.createObjectToObjectHashMap();

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

	protected void wrapUp() {
		ArrayList<EObject> objectsToDelete = new ArrayList<EObject>();
		
		for (int v : chooseTGGRuleApplications()) {
			int id = v < 0 ? -v : v;
			IMatch comatch = idToMatch.get(id);
			if (v < 0 && !TGGUtil.isNac(matchIdToRuleName.get(id), options)) {
				addObjectsToDelete(objectsToDelete, comatch, id);
			}
		}

		EcoreUtil.deleteAll(objectsToDelete, true);
		consistencyReporter.init(this);
	}

	protected abstract void addObjectsToDelete(List<EObject> objectsToDelete, IMatch comatch, int id);

	public void relaxReferences(final EList<EPackage> model) {
		EPackage[] packages = (EPackage[]) model.toArray();

		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();

			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl) next;

					for (EReference reference : nextEClassImpl.getEAllReferences()) {
						if (this.referenceToUpperBound.containsKey(reference)
								&& this.referenceToLowerBound.containsKey(reference)
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
			ilpProblem.addExclusion(variables.stream().map(v -> "x" + v), "EXCL_sameCompl" + this.nameCounter++);
		}

		if (!this.invalidKernels.isEmpty()) {
			IntSet variables = this.invalidKernels;
			variables.stream().forEach(v -> {
				ilpProblem.fixVariable("x" + v, false);
			});
		}

		HandleDependencies handleCycles = new HandleDependencies(this.appliedBundles, this.edgeToMarkingMatches,
				this.nodeToMarkingMatches, this.matchToContextNodes, this.matchToContextEdges);

		List<Set<List<Integer>>> cyclicConstraints = handleCycles.getCyclicConstraints();
		for (Set<List<Integer>> constraints : cyclicConstraints) {
			for (List<Integer> variables : constraints) {
				ilpProblem.addExclusion(variables.stream().map(v -> "x" + v), "EXCL_cycle" + this.nameCounter++,
						variables.size() - 1);
			}
		}
	}

	protected void defineILPImplications(final BinaryILPProblem ilpProblem) {
		for (EObject node : this.contextNodeToNeedingMatches.keySet()) {
			IntSet needingMatchIDs = this.contextNodeToNeedingMatches.get(node);
			IntSet creatingMatchIDs = this.nodeToMarkingMatches.get(node);
			// only one or none of the creating matches can be chosen (defined by
			// exclusions)
			if (creatingMatchIDs != null && !creatingMatchIDs.isEmpty()) {
				ilpProblem.addNegativeImplication(creatingMatchIDs.stream().map(m -> "x" + m),
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
				ilpProblem.addNegativeImplication(creatingMatchIDs.stream().map(m -> "x" + m),
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

	// usability-team
	protected void defineNACImplications(final BinaryILPProblem ilpProblem) {
		
		// Context for nodes and edges
		for (int nacMatchId : idToMatch.keySet()) {
			String nacName = idToMatch.get(nacMatchId).getRuleName();
			if (TGGUtil.isNac(nacName, options)) {
				logger.debug("Processing NAC " + nacName + " with id " + nacMatchId + "...");
				IntSet auxiliaryVariableIDs = CollectionFactory.cfactory.createIntSet();
				
				// Check that all negative nodes and edges are indeed matched, otherwise no constraint is created
				if (!this.matchToNegativeNodes.get(nacMatchId).stream().findAny().map(n -> nodeToMarkingMatches.get(n)).isPresent())
					continue;
				
				if (!this.matchToNegativeEdges.get(nacMatchId).stream().findAny().map(n -> edgeToMarkingMatches.get(n)).isPresent())
					continue;
				
				for (EObject node : this.matchToNegativeNodes.get(nacMatchId)) {
					logger.debug("Node: " + node.getClass() + " with ID " + idCounter);
					auxiliaryVariableIDs.add(idCounter);
					ilpProblem.addNegativeImplication(Stream.of("x" + idCounter++), 
							this.nodeToMarkingMatches.get(node).stream().map(m -> "x" + m), 
							"NAC_AUX_" + node.eClass().getName() + this.nameCounter++);
				}
				
				for (EMFEdge edge : this.matchToNegativeEdges.get(nacMatchId)) {
					logger.debug("Edge: " + edge.getType() + " with ID " + idCounter);
					auxiliaryVariableIDs.add(idCounter);
					ilpProblem.addNegativeImplication(Stream.of("x" + idCounter++), 
							this.edgeToMarkingMatches.get(edge).stream().map(m -> "x" + m), 
							"NAC_AUX_" + edge.getType().getName() + this.nameCounter++);
				}
				
				ilpProblem.addImplication(auxiliaryVariableIDs.stream().map(m -> "x" + m),
						Stream.of("x" + nacMatchId), "NAC_IMPL_" + nacName + this.nameCounter++);
			}
		}

		// Negative implications for rules
		for (int nacMatchId : idToMatch.keySet()) {
			String nacName = idToMatch.get(nacMatchId).getRuleName();
			if (TGGUtil.isNac(nacName, options)) {
				Set<EObject> nacContextNodes = matchToContextNodes.get(nacMatchId);
				Set<EMFEdge> nacContextEdges = matchToContextEdges.get(nacMatchId);
				Set<EObject> nacNegativeNodes = matchToNegativeNodes.get(nacMatchId);
				Set<EMFEdge> nacNegativeEdges = matchToNegativeEdges.get(nacMatchId);

				for (TGGRule rule : TGGUtil.getRulesForNac(nacName, options)) {
					for (int ruleMatchId : ruleNameToMatchId.get(rule.getName())) {
						Set<EObject> ruleContextNodes = matchToContextNodes.get(ruleMatchId);
						Set<EMFEdge> ruleContextEdges = matchToContextEdges.get(ruleMatchId);
						Set<EObject> ruleMarkedNodes = markingMatchToNodes.get(ruleMatchId);
						Set<EMFEdge> ruleMarkedEdges = markingMatchToEdges.get(ruleMatchId);

						if (nacContextNodes.equals(ruleContextNodes) && nacContextEdges.equals(ruleContextEdges) //
								&& TGGUtil.intersect(nacNegativeNodes, ruleMarkedNodes).isEmpty() //
								&& TGGUtil.intersect(nacNegativeEdges, ruleMarkedEdges).isEmpty() 
						){
							ilpProblem.addExclusion(Stream.of("x" + nacMatchId, "x" + ruleMatchId),
									"NAC_EXCL_" + nacName + this.nameCounter++);
						}
					}
				}

			}
		}
	}

	protected void defineILPObjective(final BinaryILPProblem ilpProblem) {
		ILPLinearExpression expr = ilpProblem.createLinearExpression();
		this.matchToWeight.keySet().stream().forEach(v -> {
			double weight = this.matchToWeight.get(v);
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
		this.defineILPExclusions(ilpProblem);

		OperationalStrategy.logger.debug("Adding implications...");
		this.defineILPImplications(ilpProblem);

		// usability-team
		OperationalStrategy.logger.debug("Adding NAC handling...");
		this.defineNACImplications(ilpProblem);

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
		Bundle appliedBundle = new Bundle(this.idCounter);
		this.appliedBundles.add(appliedBundle);
		this.lastAppliedBundle = appliedBundle;

		this.lastAppliedBundle.addMatch(this.idCounter);

		// add context nodes and edges of this concrete match to its bundle
		this.lastAppliedBundle.addBundleContextNodes(this.getBlackNodes(comatch, ruleName));
		this.lastAppliedBundle.addBundleContextEdges(this.getBlackEdges(comatch, ruleName));
	}

	protected Set<EObject> getGreenNodes(final IMatch comatch, final String ruleName) {
		Set<EObject> result = cfactory.createObjectSet();
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getGreenSrcNodesInRule()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getGreenTrgNodesInRule()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getGreenCorrNodesInRule()));
		return result;
	}

	protected Set<EObject> getBlackNodes(final IMatch comatch, final String ruleName) {
		Set<EObject> result = cfactory.createObjectSet();
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getBlackSrcNodesInRule()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getBlackTrgNodesInRule()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getBlackCorrNodesInRule()));
		return result;
	}

	// usability-team
	// implementing negative or nac nodes
	protected Set<EObject> getNegativeNodes(final IMatch comatch, final String ruleName) {
		Set<EObject> result = cfactory.createObjectSet();
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getNegativeSrcNodesInNac()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getNegativeTrgNodesInNac()));
		result.addAll(this.getNodes(comatch, this.getGreenFactory(ruleName).getNegativeCorrNodesInNac()));
		return result;
	}

	protected Set<EObject> getNodes(final IMatch comatch, final Collection<? extends TGGRuleNode> specNodes) {
		Set<EObject> result = cfactory.createObjectSet();
		specNodes.forEach(n -> {
			result.add((EObject) comatch.get(n.getName()));
		});
		return result;
	}

	protected Set<EMFEdge> getGreenEdges(final IMatch comatch, final String ruleName) {
		Set<EMFEdge> result = cfactory.createEMFEdgeHashSet();
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getGreenSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getGreenTrgEdgesInRule(), false));
		return result;
	}

	protected Set<EMFEdge> getBlackEdges(final IMatch comatch, final String ruleName) {
		Set<EMFEdge> result = cfactory.createEMFEdgeHashSet();
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getBlackSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getBlackTrgEdgesInRule(), false));
		return result;
	}

	// usability-team
	// implementing negative or nac edges
	protected Set<EMFEdge> getNegativeEdges(final IMatch comatch, final String ruleName) {
		Set<EMFEdge> result = cfactory.createEMFEdgeHashSet();
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getNegativeSrcEdgesInNac(), false));
		result.addAll(((IbexGreenInterpreter) this.greenInterpreter).createEdges(comatch,
				this.getGreenFactory(ruleName).getNegativeTrgEdgesInNac(), false));
		return result;
	}

	protected Collection<EObject> getRuleApplicationNodes(final IMatch comatch) {
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

	// usability-team
	// removing premarker from fwd bwd n cc n moving it to opt
	@Override
	protected void prepareMarkerCreation(IGreenPattern greenPattern, IMatch comatch, String ruleName) {
		idToMatch.put(idCounter, comatch);
		matchIdToRuleName.put(idCounter, ruleName);

		IntSet ids = ruleNameToMatchId.get(ruleName);
		if (ids == null) {
			ids = cfactory.createIntSet();
		}
		ids.add(idCounter);
		ruleNameToMatchId.put(ruleName, ids);

		matchToWeight.put(idCounter, this.getWeightForMatch(comatch, ruleName));

		getBlackNodes(comatch, ruleName).forEach(e -> {
			if (!contextNodeToNeedingMatches.containsKey(e))
				contextNodeToNeedingMatches.put(e, cfactory.createIntSet());
			contextNodeToNeedingMatches.get(e).add(idCounter);
		});

		getBlackEdges(comatch, ruleName).forEach(e -> {
			if (!contextEdgeToNeedingMatches.containsKey(e)) {
				contextEdgeToNeedingMatches.put(e, cfactory.createIntSet());
			}
			contextEdgeToNeedingMatches.get(e).add(idCounter);
		});

		matchToContextNodes.put(idCounter, cfactory.createObjectSet());
		matchToContextNodes.get(idCounter).addAll(getBlackNodes(comatch, ruleName));

		matchToContextEdges.put(idCounter, cfactory.createEMFEdgeHashSet());
		matchToContextEdges.get(idCounter).addAll(getBlackEdges(comatch, ruleName));

		if (TGGUtil.isNac(ruleName, options)) {
			// usability-team
			// declaring negative nodes n edges
			getNegativeNodes(comatch, ruleName).forEach(e -> {
				if (!negativeNodeToNeedingMatches.containsKey(e))
					negativeNodeToNeedingMatches.put(e, cfactory.createIntSet());
				negativeNodeToNeedingMatches.get(e).add(idCounter);
				logger.info("NAC node in rule " + ruleName + " for match " + idCounter);
			});

			getNegativeEdges(comatch, ruleName).forEach(e -> {
				if (!negativeEdgeToNeedingMatches.containsKey(e)) {
					negativeEdgeToNeedingMatches.put(e, cfactory.createIntSet());
				}
				negativeEdgeToNeedingMatches.get(e).add(idCounter);
				logger.info("NAC edge in rule " + ruleName + " for match " + idCounter);
			});

			// usability-team
			// match to NAC edges and nodes
			matchToNegativeNodes.put(idCounter, cfactory.createObjectSet());
			matchToNegativeNodes.get(idCounter).addAll(getNegativeNodes(comatch, ruleName));
			matchToNegativeEdges.put(idCounter, cfactory.createEMFEdgeHashSet());
			matchToNegativeEdges.get(idCounter).addAll(getNegativeEdges(comatch, ruleName));
		}
		// Green nodes and edges for rule matches
		else {
			getGreenNodes(comatch, ruleName).forEach(e -> {
				if (!nodeToMarkingMatches.containsKey(e))
					nodeToMarkingMatches.put(e, cfactory.createIntSet());
				nodeToMarkingMatches.get(e).add(idCounter);
			});

			getGreenEdges(comatch, ruleName).forEach(e -> {
				if (!edgeToMarkingMatches.containsKey(e)) {
					edgeToMarkingMatches.put(e, cfactory.createIntSet());
				}
				edgeToMarkingMatches.get(e).add(idCounter);
			});

			markingMatchToNodes.put(idCounter, cfactory.createObjectSet());
			markingMatchToNodes.get(idCounter).addAll(getGreenNodes(comatch, ruleName));

			markingMatchToEdges.put(idCounter, cfactory.createEMFEdgeHashSet());
			markingMatchToEdges.get(idCounter).addAll(getGreenEdges(comatch, ruleName));
		}

		handleBundles(comatch, ruleName);

		idCounter++;
	}
	
	public abstract boolean modelsAreConsistent();
	
	public Collection<EObject> getInconsistentSrcNodes() {
		return consistencyReporter.getInconsistentSrcNodes();
	}

	public Collection<EObject> getInconsistentTrgNodes() {
		return consistencyReporter.getInconsistentTrgNodes();
	}
	
	public Collection<EObject> getInconsistentCorrNodes() {
		return consistencyReporter.getInconsistentCorrNodes();
	}

	public Collection<EMFEdge> getInconsistentSrcEdges() {
		return consistencyReporter.getInconsistentSrcEdges();
	}

	public Collection<EMFEdge> getInconsistentTrgEdges() {
		return consistencyReporter.getInconsistentTrgEdges();
	}

	public String generateConsistencyReport() {
		String result = "";
		if (modelsAreConsistent())
			result += "Your models are consistent";
		else {
			result += "Your models are inconsistent. The following elements are not part of a consistent triple:";
			result += "\n" + "Source nodes:" + "\n";
			result += String.join("\n",
					getInconsistentSrcNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Source edges:" + "\n";
			result += String.join("\n",
					getInconsistentSrcEdges().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Correspondence nodes:" + "\n";
			result += String.join("\n",
					getInconsistentCorrNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Target nodes:" + "\n";
			result += String.join("\n",
					getInconsistentTrgNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Target edges:" + "\n";
			result += String.join("\n",
					getInconsistentTrgEdges().stream().map(n -> n.toString()).collect(Collectors.toSet()));
		}
		return result;
	}

}
