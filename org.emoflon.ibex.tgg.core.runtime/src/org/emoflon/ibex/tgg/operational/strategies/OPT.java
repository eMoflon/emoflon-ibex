package org.emoflon.ibex.tgg.operational.strategies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
import org.emoflon.ibex.common.utils.EMFEdge;
import org.emoflon.ibex.common.utils.EMFEdgeHashingStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.cc.Bundle;
import org.emoflon.ibex.tgg.operational.strategies.cc.ConsistencyReporter;
import org.emoflon.ibex.tgg.operational.strategies.cc.HandleDependencies;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Comparator;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import com.google.common.collect.Sets;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.TGGRuleNode;

public abstract class OPT extends OperationalStrategy {

	protected Int2ObjectOpenHashMap<IMatch> idToMatch = new Int2ObjectOpenHashMap<>();
	protected Object2ObjectOpenCustomHashMap<EMFEdge, IntOpenHashSet> edgeToMarkingMatches = new Object2ObjectOpenCustomHashMap<>(
			new EMFEdgeHashingStrategy());
	protected Object2ObjectOpenHashMap<EObject, IntOpenHashSet> nodeToMarkingMatches = new Object2ObjectOpenHashMap<>();
	protected ConsistencyReporter consistencyReporter = new ConsistencyReporter();
	protected int nameCounter = 0;
	protected Int2ObjectOpenHashMap<ObjectOpenHashSet<EObject>> matchToContextNodes = new Int2ObjectOpenHashMap<>();
	protected Object2ObjectOpenHashMap<EObject, IntOpenHashSet> contextNodeToNeedingMatches = new Object2ObjectOpenHashMap<>();
	protected Object2ObjectOpenCustomHashMap<EMFEdge, IntOpenHashSet> contextEdgeToNeedingMatches = new Object2ObjectOpenCustomHashMap<>(
			new EMFEdgeHashingStrategy());
	protected Int2ObjectOpenHashMap<ObjectOpenCustomHashSet<EMFEdge>> matchToContextEdges = new Int2ObjectOpenHashMap<>();
	protected Int2IntOpenHashMap matchToWeight = new Int2IntOpenHashMap();

	/**
	 * Collection of constraints to guarantee uniqueness property; key: Complement
	 * rule (CR) match ID; value: other CR matches of the same CR using the same
	 * context as CR match
	 */
	protected Int2ObjectOpenHashMap<IntOpenHashSet> sameComplementMatches = new Int2ObjectOpenHashMap<>();

	/**
	 * Collection of constraints to guarantee maximality property; value: kernels
	 * whose complement rules did not fulfill maximality property
	 */
	protected IntOpenHashSet invalidKernels = new IntOpenHashSet();

	/**
	 * Collection of constraints to guarantee cyclic dependences are avoided; value:
	 * correctly applied bundles (kernel match + its CRs matches)
	 */
	protected ObjectLinkedOpenHashSet<Bundle> appliedBundles = new ObjectLinkedOpenHashSet<Bundle>();
	protected Bundle lastAppliedBundle;

	protected Int2ObjectOpenHashMap<String> matchIdToRuleName = new Int2ObjectOpenHashMap<>();
	protected int idCounter = 1;

	// Hash maps to save the old metamodel state
	Object2IntOpenHashMap<EReference> referenceToUpperBound = new Object2IntOpenHashMap<>();
	Object2IntOpenHashMap<EReference> referenceToLowerBound = new Object2IntOpenHashMap<>();
	Object2ObjectOpenHashMap<EReference, EReference> referenceToEOpposite = new Object2ObjectOpenHashMap<EReference, EReference>();
	Object2ObjectOpenHashMap<EReference, Boolean> referenceToContainment = new Object2ObjectOpenHashMap<EReference, Boolean>();

	public OPT(IbexOptions options) throws IOException {
		super(options);
	}

	public OPT(IbexOptions options, IUpdatePolicy policy) {
		super(options, policy);
	}

	public void relaxReferences(EList<EPackage> model) {

		EPackage[] packages = (EPackage[]) model.toArray();

		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();

			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl) next;

					for (EReference reference : nextEClassImpl.getEAllReferences()) {
						if (referenceToUpperBound.containsKey(reference) && referenceToLowerBound.containsKey(reference)
								&& referenceToContainment.containsKey(reference)
								&& referenceToEOpposite.containsKey(reference)) {
							// Reference already exists, values must not be overwritten
							continue;
						}

						// Save metamodel values
						referenceToUpperBound.put(reference, reference.getUpperBound());
						referenceToLowerBound.put(reference, reference.getLowerBound());
						referenceToContainment.put(reference, reference.isContainment());
						referenceToEOpposite.put(reference, reference.getEOpposite());

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

	public void unrelaxReferences(EList<EPackage> model) {

		EPackage[] packages = (EPackage[]) model.toArray();

		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();

			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl) next;

					for (EReference reference : nextEClassImpl.getEAllReferences()) {
						// Get old metamodel values
						int upperBound = referenceToUpperBound.getInt(reference);
						int lowerBound = referenceToLowerBound.getInt(reference);
						boolean containment = referenceToContainment.get(reference);
						EReference eOpposite = referenceToEOpposite.get(reference);

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

	protected void defineILPExclusions(ILPProblem ilpProblem) {
		for (EObject node : nodeToMarkingMatches.keySet()) {
			IntOpenHashSet variables = nodeToMarkingMatches.get(node);
			if (variables.size() <= 1) {
				// there is only one match creating this node, no exclusion needed
				continue;
			}
			ILPLinearExpression expr = ilpProblem.createLinearExpression();
			variables.stream().forEach(v -> {
				expr.addTerm("x" + v, 1.0);
			});

			ilpProblem.addConstraint(expr, Comparator.le, 1.0, "EXCL_nodeOnce_" + node.toString() + nameCounter++);
		}

		for (EMFEdge edge : edgeToMarkingMatches.keySet()) {
			IntOpenHashSet variables = edgeToMarkingMatches.get(edge);
			if (variables.size() <= 1) {
				// there is only one match creating this edge, no exclusion needed
				continue;
			}
			ILPLinearExpression expr = ilpProblem.createLinearExpression();
			variables.stream().forEach(v -> {
				expr.addTerm("x" + v, 1.0);
			});
			ilpProblem.addConstraint(expr, Comparator.le, 1.0, "EXCL_edgeOnce" + nameCounter++);
		}

		for (int match : sameComplementMatches.keySet()) {
			IntOpenHashSet variables = sameComplementMatches.get(match);
			ILPLinearExpression expr = ilpProblem.createLinearExpression();
			variables.stream().forEach(v -> {
				expr.addTerm("x" + v, 1.0);
			});
			ilpProblem.addConstraint(expr, Comparator.le, 1.0, "EXCL_sameCompl" + nameCounter++);
		}

		if (!invalidKernels.isEmpty()) {
			IntOpenHashSet variables = invalidKernels;
			ILPLinearExpression expr = ilpProblem.createLinearExpression();
			variables.stream().forEach(v -> {
				expr.addTerm("x" + v, 1.0);
			});
			ilpProblem.addConstraint(expr, Comparator.le, 0.0, "EXCL_invKern" + nameCounter++);
		}

		HandleDependencies handleCycles = new HandleDependencies(appliedBundles, edgeToMarkingMatches,
				nodeToMarkingMatches, matchToContextNodes, matchToContextEdges);
		Set<Integer> allCyclicBundles = handleCycles.getCyclicDependenciesBetweenBundles().keySet();

		for (int cycle : allCyclicBundles) {
			Set<List<Integer>> cyclicConstraints = getCyclicConstraints(
					handleCycles.getComplementRuleApplications(cycle));
			for (List<Integer> variables : cyclicConstraints) {
				ILPLinearExpression expr = ilpProblem.createLinearExpression();
				variables.forEach(v -> {
					expr.addTerm("x" + v, 1.0);
				});
				ilpProblem.addConstraint(expr, Comparator.le, variables.size() - 1, "EXCL" + nameCounter++);
			}
		}
	}

	private Set<List<Integer>> getCyclicConstraints(List<IntLinkedOpenHashSet> dependentRuleApplications) {
		// TODO:  Perhaps implement cartesian product directly if this is a performance bottle neck
		List<HashSet<Integer>> sets = new ArrayList<>();
		for (IntLinkedOpenHashSet tHashSet : dependentRuleApplications) {
			HashSet<Integer> set = new HashSet<>();
			IntIterator it = tHashSet.iterator();
			while(it.hasNext())
				set.add(it.nextInt());
			sets.add(set);
		}
		return Sets.cartesianProduct(sets);
	}

	protected void defineILPImplications(ILPProblem ilpProblem) {
		for (EObject node : contextNodeToNeedingMatches.keySet()) {
			IntOpenHashSet needingMatchIDs = contextNodeToNeedingMatches.get(node);
			int needingMatches = needingMatchIDs.size();
			IntOpenHashSet creatingMatchIDs = nodeToMarkingMatches.get(node);
			ILPLinearExpression expr = ilpProblem.createLinearExpression();
			needingMatchIDs.stream().forEach(m -> {
				expr.addTerm("x" + m, 1);
			});
			// only one or none of the creating matches can be chosen (defined by
			// exclusions)
			if (creatingMatchIDs != null && !creatingMatchIDs.isEmpty()) {
				creatingMatchIDs.stream().forEach(m -> {
					expr.addTerm("x" + m, -needingMatches);
				});
			} else {
				// there is no match creating this node -> forbid all matches needing it
				needingMatchIDs.stream().forEach(m -> {
					ilpProblem.fixVariable("x" + m, 0);
				});
			}
			ilpProblem.addConstraint(expr, Comparator.le, 0.0, "IMPL" + nameCounter++);
		}

		for (EMFEdge edge : contextEdgeToNeedingMatches.keySet()) {
			IntOpenHashSet needingMatchIDs = contextEdgeToNeedingMatches.get(edge);
			int needingMatches = needingMatchIDs.size();
			IntOpenHashSet creatingMatchIDs = edgeToMarkingMatches.get(edge);
			ILPLinearExpression expr = ilpProblem.createLinearExpression();
			needingMatchIDs.stream().forEach(m -> {
				expr.addTerm("x" + m, 1);
			});
			// only one or none of the creating matches can be chosen (defined by
			// exclusions)
			if (creatingMatchIDs != null && !creatingMatchIDs.isEmpty()) {
				creatingMatchIDs.stream().forEach(m -> {
					expr.addTerm("x" + m, -needingMatches);
				});
			} else {
				// there is no match creating this node -> forbid all matches needing it
				needingMatchIDs.stream().forEach(m -> {
					ilpProblem.fixVariable("x" + m, 0);
				});
			}
			ilpProblem.addConstraint(expr, Comparator.le, 0.0, "IMPL" + nameCounter++);
		}
	}

	protected void defineILPObjective(ILPProblem ilpProblem) {
		ILPLinearExpression expr = ilpProblem.createLinearExpression();
		matchToWeight.keySet().stream().forEach((IntConsumer) (v -> {
			int weight = matchToWeight.get(v);
			expr.addTerm("x" + v, weight);
		}));
		ilpProblem.setObjective(expr, Objective.maximize);
	}

	protected int[] chooseTGGRuleApplications() {
		logger.debug("Creating ILP problem for " + idToMatch.size() + " matches");

		ILPProblem ilpProblem = ILPFactory.createILPProblem();

		logger.debug("Adding exclusions...");
		defineILPExclusions(ilpProblem);

		logger.debug("Problem has " + ilpProblem.getConstraints().size() + " constraints.");
		logger.debug("Adding implications...");

		defineILPImplications(ilpProblem);

		logger.debug("Problem has " + ilpProblem.getConstraints().size() + " constraints.");
		logger.debug("Adding user defined constraints...");

		addUserDefinedConstraints(ilpProblem);

		logger.debug("Problem has " + ilpProblem.getConstraints().size() + " constraints.");
		logger.debug("Defining objective...");

		defineILPObjective(ilpProblem);

		try {
			ILPSolution ilpSolution = ILPSolver.solveBinaryILPProblem(ilpProblem, this.options.getIlpSolver());
			if (!ilpProblem.checkValidity(ilpSolution)) {
				throw new AssertionError("Invalid solution");
			}
			;

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

	protected void addUserDefinedConstraints(ILPProblem ilpProblem) {

	}

	protected void handleBundles(IMatch comatch, String ruleName) {
		if (!getComplementRule(ruleName).isPresent()) {
			Bundle appliedBundle = new Bundle(idCounter);
			appliedBundles.add(appliedBundle);
			lastAppliedBundle = appliedBundle;
		}

		lastAppliedBundle.addMatch(idCounter);

		// add context nodes and edges of this concrete match to its bundle
		lastAppliedBundle.addBundleContextNodes(getBlackNodes(comatch, ruleName));
		lastAppliedBundle.addBundleContextEdges(getBlackEdges(comatch, ruleName));
	}

	protected ObjectOpenHashSet<EObject> getGreenNodes(IMatch comatch, String ruleName) {
		ObjectOpenHashSet<EObject> result = new ObjectOpenHashSet<>();
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenSrcNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenTrgNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenCorrNodesInRule()));
		return result;
	}

	protected ObjectOpenHashSet<EObject> getBlackNodes(IMatch comatch, String ruleName) {
		ObjectOpenHashSet<EObject> result = new ObjectOpenHashSet<>();
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackSrcNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackTrgNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackCorrNodesInRule()));
		return result;
	}

	protected ObjectOpenHashSet<EObject> getNodes(IMatch comatch, Collection<? extends TGGRuleNode> specNodes) {
		ObjectOpenHashSet<EObject> result = new ObjectOpenHashSet<>();
		specNodes.forEach(n -> {
			result.add((EObject) comatch.get(n.getName()));
		});
		return result;
	}

	protected ObjectOpenHashSet<EMFEdge> getGreenEdges(IMatch comatch, String ruleName) {
		ObjectOpenHashSet<EMFEdge> result = new ObjectOpenHashSet<>();
		result.addAll(((IbexGreenInterpreter) greenInterpreter).createEdges(comatch,
				getGreenFactory(ruleName).getGreenSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter) greenInterpreter).createEdges(comatch,
				getGreenFactory(ruleName).getGreenTrgEdgesInRule(), false));
		return result;
	}

	protected ObjectOpenHashSet<EMFEdge> getBlackEdges(IMatch comatch, String ruleName) {
		ObjectOpenHashSet<EMFEdge> result = new ObjectOpenHashSet<>();
		result.addAll(((IbexGreenInterpreter) greenInterpreter).createEdges(comatch,
				getGreenFactory(ruleName).getBlackSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter) greenInterpreter).createEdges(comatch,
				getGreenFactory(ruleName).getBlackTrgEdgesInRule(), false));
		return result;
	}

	protected Collection<EObject> getRuleApplicationNodes(IMatch comatch) {
		return comatch.getParameterNames().stream().filter(p -> p.endsWith(ConsistencyPattern.protocolNodeSuffix))
				.map(comatch::get).map(EObject.class::cast).collect(Collectors.toList());
	}

	protected abstract int getWeightForMatch(IMatch comatch, String ruleName);

	@Override
	public Resource loadResource(String workspaceRelativePath) throws IOException {
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
