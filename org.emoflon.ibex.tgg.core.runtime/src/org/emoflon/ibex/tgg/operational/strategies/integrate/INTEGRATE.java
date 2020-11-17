package org.emoflon.ibex.tgg.operational.strategies.integrate;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.delta.validation.DeltaValidator;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.PrecedenceMatchContainer;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection.ConflictDetector;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection.MultiplicityCounter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol.ChangeKey;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.NACOverlap;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.operational.strategies.opt.CC;

import com.google.common.collect.Sets;
import delta.DeltaContainer;
import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;
import runtime.TGGRuleApplication;

public class INTEGRATE extends PropagatingOperationalStrategy {

	//// TOOLS ////
	protected TGGMatchUtil matchUtil;
	protected ModelChangeProtocol modelChangeProtocol;
	protected ConflictDetector conflictDetector;
	protected CC consistencyChecker;
	protected PrecedenceGraph precedenceGraph;
	protected MultiplicityCounter multiplicityCounter;

	//// DATA ////
	protected DeltaContainer userDeltaContainer;
	protected BiConsumer<EObject, EObject> userDeltaBiConsumer;
	protected ChangeKey userDeltaKey;
	protected ChangeKey generalDeltaKey;

	protected Map<String, Collection<String>> ruleName2filterNacPatternNames;
	protected Map<String, Map<NACOverlap, Collection<ITGGMatch>>> pattern2filterNacMatches;
	protected Map<String, Collection<String>> filterNacPattern2nodeNames;
	protected Map<ITGGMatch, BrokenMatch> classifiedBrokenMatches;
	protected Set<ConflictContainer> conflicts;
	protected Map<ITGGMatch, ConflictContainer> match2conflicts;

	public INTEGRATE(IbexOptions options) throws IOException {
		super(options);
		init();
	}

	private void init() throws IOException {
		ruleName2filterNacPatternNames = this.options.tgg.getFlattenedConcreteTGGRules().stream() //
				.collect(Collectors.toMap(r -> r.getName(), r -> new LinkedList<>()));
		pattern2filterNacMatches = new HashMap<>();
		filterNacPattern2nodeNames = new HashMap<>();
		classifiedBrokenMatches = new HashMap<>();
		conflicts = new HashSet<>();
		match2conflicts = new HashMap<>();

		matchUtil = new TGGMatchUtil(this);
		modelChangeProtocol = new ModelChangeProtocol( //
				resourceHandler.getSourceResource(), resourceHandler.getTargetResource(), //
				resourceHandler.getCorrResource(), resourceHandler.getProtocolResource());
		conflictDetector = new ConflictDetector(this);
//		consistencyChecker = new LocalCC(options) {
//			@Override
//			protected void processValidMatch(ITGGMatch match) {
//				removeBrokenMatchesAfterCCMatchApplication(match);
//			}
//		};
		options.executable(this);
		precedenceGraph = new PrecedenceGraph(this);
		multiplicityCounter = new MultiplicityCounter(this);

		Collection<PatternType> patternsRelevantForPrecedenceGraph = Arrays.asList(PatternType.CONSISTENCY, PatternType.SRC, PatternType.TRG);
		matchDistributor.registerSingle(patternsRelevantForPrecedenceGraph, precedenceGraph::notifyAddedMatch, precedenceGraph::notifyRemovedMatch);
		matchDistributor.registerMultiple(patternsRelevantForPrecedenceGraph, precedenceGraph::notifyAddedMatches, precedenceGraph::notifyRemovedMatches);
	}

	private void removeBrokenMatchesAfterCCMatchApplication(ITGGMatch ccMatch) {
		Set<EObject> ccObjects = matchUtil.getObjects(ccMatch, new EltFilter().srcAndTrg());
		for (ITGGMatch brokenMatch : brokenRuleApplications.values()) {
			Set<EObject> brokenObjects = matchUtil.getObjects(brokenMatch, new EltFilter().srcAndTrg().deleted());
			if (!Sets.intersection(ccObjects, brokenObjects).isEmpty())
				removeBrokenMatch(brokenMatch);
		}
	}

	public void integrate() throws IOException {
		run();
	}

	@Override
	public void run() throws IOException {
		Timer.start();

		initialize();

		Timer.start();
		for (IntegrationFragment fragment : options.integration.pattern().getIntegrationFragments())
			fragment.apply(this);
		times.addTo("run:fragments", Timer.stop());

		cleanUp();

		times.addTo("run", Timer.stop());
	}

	protected void initialize() {
		Timer.start();

		initializeRepairStrategy(options);
		matchDistributor.updateMatches();
		modelChangeProtocol.attachAdapter();
		userDeltaKey = new ChangeKey();
		generalDeltaKey = new ChangeKey();
		modelChangeProtocol.registerKey(generalDeltaKey);

		times.addTo("run:initialize", Timer.stop());
	}

	protected void cleanUp() {
		Timer.start();

		modelChangeProtocol.deregisterKey(generalDeltaKey);
		modelChangeProtocol.detachAdapter();
		modelChangeProtocol = new ModelChangeProtocol(resourceHandler.getSourceResource(), resourceHandler.getTargetResource(),
				resourceHandler.getCorrResource());
		classifiedBrokenMatches = new HashMap<>();
		conflicts = new HashSet<>();
		match2conflicts = new HashMap<>();

		times.addTo("run:cleanUp", Timer.stop());
	}

	protected void classifyBrokenMatches(boolean includeImplicitBroken) {
		matchDistributor.updateMatches();

		Timer.start();

		classifiedBrokenMatches.clear();

		Collection<PrecedenceNode> brokenNodes = new HashSet<>(precedenceGraph.getBrokenNodes());
		if (includeImplicitBroken)
			brokenNodes.addAll(precedenceGraph.getImplicitBrokenNodes().parallelStream() //
					.filter(n -> n.getMatch().getType() == PatternType.CONSISTENCY).collect(Collectors.toSet()) //
			);
		classifiedBrokenMatches = brokenNodes.parallelStream().collect( //
				Collectors.toMap( //
						node -> node.getMatch(), //
						node -> new BrokenMatch(this, node.getMatch(), !node.isBroken())));

		times.addTo("operations:classifyBrokenMatches", Timer.stop());
	}

	protected void detectConflicts() {
		matchDistributor.updateMatches();

		Timer.start();

		match2conflicts = conflictDetector.detectConflicts();
		conflicts = buildContainerHierarchy(match2conflicts);

		times.addTo("operations:detectConflicts", Timer.stop());

		if (!match2conflicts.isEmpty())
			LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "");
	}

	private Set<ConflictContainer> buildContainerHierarchy(Map<ITGGMatch, ConflictContainer> match2conflicts) {
		Set<ConflictContainer> conflicts = new HashSet<>(match2conflicts.values());
		for (ITGGMatch match : match2conflicts.keySet()) {
			precedenceGraph.getNode(match).forAllRequiredBy((n, pre) -> {
				ITGGMatch m = n.getMatch();
				if (match2conflicts.containsKey(m)) {
					ConflictContainer cc = match2conflicts.get(m);
					match2conflicts.get(match).getSubContainers().add(cc);
					conflicts.remove(cc);
					return false;
				}
				return true;
			});
		}
		return conflicts;
	}

	protected void resolveConflicts() {
		for (ConflictContainer c : conflicts)
			options.integration.conflictSolver().resolveConflict(c);

		if (!conflicts.isEmpty())
			LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "");
	}

	protected void detectAndResolveOpMultiplicityConflicts() {
		Set<ConflictContainer> opMultiConflicts = buildContainerHierarchy(conflictDetector.detectOpMultiplicityConflicts());
		for (ConflictContainer c : opMultiConflicts)
			options.integration.conflictSolver().resolveConflict(c);

		if (!opMultiConflicts.isEmpty())
			LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "");
	}

	protected void translateConflictFree() {
//		setUpdatePolicy(new ConflictFreeElementsUpdatePolicy(this));
		translate();
//		setUpdatePolicy(new NextMatchUpdatePolicy());
	}

	protected void resolveBrokenMatches() {
		Timer.start();

		classifiedBrokenMatches.values().forEach(brokenMatch -> brokenMatch.rollbackBrokenMatch());
		((PrecedenceMatchContainer) operationalMatchContainer).clearPendingElements();

		times.addTo("operations:resolveBrokenMatches", Timer.stop());

		if (!classifiedBrokenMatches.isEmpty())
			LoggerConfig.log(LoggerConfig.log_ruleApplication(), () -> "");
	}

	protected ChangeKey revokeBrokenCorrsAndRuleApplNodes() {
		ChangeKey key = new ChangeKey();
		modelChangeProtocol.registerKey(key);

		Map<TGGRuleApplication, ITGGMatch> processed = new HashMap<>();
		do {
			matchDistributor.updateMatches();
		} while (deleteCorrsAndRAs(processed));
		brokenRuleApplications.putAll(processed);

		modelChangeProtocol.deregisterKey(key);
		return key;
	}

	private boolean deleteCorrsAndRAs(Map<TGGRuleApplication, ITGGMatch> processed) {
		if (brokenRuleApplications.isEmpty())
			return false;
		brokenRuleApplications.forEach((ra, m) -> {
			deleteGreenCorrs(m);
//			EcoreUtil.delete(ra, true);
//			ra.setProtocol(null);
			ra.eClass().getEAllReferences().forEach(r -> ra.eSet(r, null));
			ra.eResource().getContents().remove(ra);
		});
		processed.putAll(brokenRuleApplications);
		brokenRuleApplications.clear();
		return true;
	}

	/**
	 * Deletes all green correspondence elements of the given match.
	 * 
	 * @param match Match
	 * 
	 */
	public void deleteGreenCorrs(ITGGMatch match) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();
		prepareGreenCorrDeletion(match, nodesToRevoke, edgesToRevoke);
		getRedInterpreter().revoke(nodesToRevoke, edgesToRevoke);
	}

	/**
	 * <p>
	 * Determines green correspondence elements and adds them to the passed sets (nodesToRevoke &
	 * edgesToRevoke).
	 * </p>
	 * <p>
	 * To delete this elements call {@link IbexRedInterpreter#revoke(nodesToRevoke, edgesToRevoke)}.
	 * </p>
	 * 
	 * @param match
	 * @param nodesToRevoke
	 * @param edgesToRevoke
	 */
	private void prepareGreenCorrDeletion(ITGGMatch match, Set<EObject> nodesToRevoke, Set<EMFEdge> edgesToRevoke) {
		matchUtil.getObjects(match, new EltFilter().corr().create()).forEach(obj -> getIbexRedInterpreter().revokeCorr(obj, nodesToRevoke, edgesToRevoke));
	}

	protected void restoreBrokenCorrsAndRuleApplNodes(ChangeKey key) {
		matchDistributor.updateMatches();
		ModelChanges changes = modelChangeProtocol.getModelChanges(key);
		brokenRuleApplications.forEach((ra, m) -> matchUtil.getObjects(m, new EltFilter().corr().create()).forEach(obj -> restoreNode(changes, obj)));
		brokenRuleApplications.forEach((ra, m) -> restoreNode(changes, ra));
	}

	private void restoreNode(ModelChanges changes, EObject node) {
		Resource resource = changes.containedInResource(node);
		if (resource != null) {
			resource.getContents().add(node);
			changes.getDeletedEdges(node).forEach(e -> {
				if (node.equals(e.getSource()))
					ModelChangeUtil.createEdge(e);
			});
		}
	}

	protected void clearBrokenRuleApplications() {
		brokenRuleApplications.clear();
	}

	protected void revokeUntranslatedElements() {
		Set<EObject> untranslated = new HashSet<>();
		resourceHandler.getSourceResource().getAllContents().forEachRemaining(n -> untranslated.add(n));
		resourceHandler.getTargetResource().getAllContents().forEachRemaining(n -> untranslated.add(n));
		resourceHandler.getProtocolResource().getContents().forEach(ra -> ra.eCrossReferences().forEach(obj -> untranslated.remove(obj)));

		getRedInterpreter().revoke(untranslated, Collections.emptySet());
	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new PrecedenceMatchContainer(this);
	}

	@Override
	protected boolean processOneOperationalRuleMatch() {
		Timer.start();

		this.updateBlockedMatches();
		if (operationalMatchContainer.isEmpty()) {
			times.addTo("translate:ruleApplication", Timer.stop());
			return false;
		}

		ITGGMatch match = chooseOneMatch();
		if (match == null) {
			times.addTo("translate:ruleApplication", Timer.stop());
			return false;
		}
		String ruleName = match.getRuleName();

		Optional<ITGGMatch> result = processOperationalRuleMatch(ruleName, match);
		removeOperationalRuleMatch(match);

		if (result.isPresent()) {
			options.debug.benchmarkLogger().addToNumOfMatchesApplied(1);
			LoggerConfig.log(LoggerConfig.log_ruleApplication(),
					() -> "Matches: removed (as it has just been applied) " + match.getPatternName() + "(" + match.hashCode() + ")\n");
		} else {
			LoggerConfig.log(LoggerConfig.log_ruleApplication(),
					() -> "Matches: removed (as application failed) " + match.getPatternName() + "(" + match.hashCode() + ")\n");
		}

		times.addTo("translate:ruleApplication", Timer.stop());
		return true;
	}

	@Override
	protected ITGGMatch chooseOneMatch() {
		return this.notifyChooseMatch(new ImmutableMatchContainer(operationalMatchContainer));
	}

	@Override
	public Set<PatternType> getShortcutPatternTypes() {
		Set<PatternType> set = new HashSet<>();
		set.add(PatternType.FWD);
		set.add(PatternType.BWD);
		set.add(PatternType.CC);
		set.add(PatternType.SRC);
		set.add(PatternType.TRG);
		return set;
	}

	@Override
	protected boolean repairBrokenMatches() {
		Timer.start();

		Collection<ITGGMatch> alreadyProcessed = cfactory.createObjectSet();
		dependencyContainer.reset();
		brokenRuleApplications.values().stream() //
				.filter(m -> {
					DeletionPattern pattern = classifiedBrokenMatches.get(m).getDeletionPattern();
					DomainModification srcModType = pattern.getModType(DomainType.SRC, BindingType.CREATE);
					DomainModification trgModType = pattern.getModType(DomainType.TRG, BindingType.CREATE);
					return !(srcModType == DomainModification.COMPL_DEL && trgModType == DomainModification.UNCHANGED || //
					srcModType == DomainModification.UNCHANGED && trgModType == DomainModification.COMPL_DEL);
				}) //
				.forEach(dependencyContainer::addMatch);

		boolean processedOnce = true;
		while (processedOnce) {
			processedOnce = false;
			while (!dependencyContainer.isEmpty()) {
				processedOnce = true;
				ITGGMatch repairCandidate = dependencyContainer.getNext();

				if (alreadyProcessed.contains(repairCandidate))
					continue;

				boolean repairedSth = false;
				BrokenMatch brokenMatch = classifiedBrokenMatches.get(repairCandidate);

				ITGGMatch repairedMatch = repairAttributes(brokenMatch);
				if (repairedMatch != null) {
					repairedSth = true;
				}

				repairedMatch = repairViaShortcut(brokenMatch);
				if (repairedMatch != null) {
					repairedSth = true;

					brokenRuleApplications.remove(getRuleApplicationNode(repairCandidate));
					precedenceGraph.removeMatch(repairCandidate);
					brokenRuleApplications.put(getRuleApplicationNode(repairedMatch), repairedMatch);
					precedenceGraph.notifyAddedMatch(repairedMatch);
					precedenceGraph.notifyRemovedMatch(repairedMatch);
					alreadyProcessed.add(repairedMatch);
				}

				if (repairedSth)
					alreadyProcessed.add(repairCandidate);
				dependencyContainer.matchApplied(repairCandidate);
			}
			alreadyProcessed.addAll(brokenRuleApplications.values());
			Timer.start();
			classifyBrokenMatches(false);
			times.subtractFrom("operations:repairBrokenMatches", Timer.stop());
			brokenRuleApplications.values().stream() //
					.filter(m -> !alreadyProcessed.contains(m)) //
					.forEach(dependencyContainer::addMatch);
		}

		times.addTo("operations:repairBrokenMatches", Timer.stop());
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "");
		return !alreadyProcessed.isEmpty();
	}

	private ITGGMatch repairAttributes(BrokenMatch brokenMatch) {
		Set<ConstrainedAttributeChanges> attrChanges = brokenMatch.getConstrainedAttrChanges();
		if (attrChanges.isEmpty())
			return null;

		boolean repairedSth = false;
		for (ConstrainedAttributeChanges attrCh : attrChanges) {
			boolean srcChange = false;
			boolean trgChange = false;
			for (TGGAttributeExpression param : attrCh.affectedParams.keySet()) {
				switch (param.getObjectVar().getDomainType()) {
				case SRC:
					srcChange = true;
					break;
				case TRG:
					trgChange = true;
				default:
					break;
				}
			}
			if (srcChange ^ trgChange) {
				PatternType type = srcChange ? PatternType.FWD : PatternType.BWD;
				List<TGGAttributeConstraint> constraints = new LinkedList<>();
				constraints.add(attrCh.constraint);
				ITGGMatch repairedMatch = getAttributeRepairStrat().repair(constraints, brokenMatch.getMatch(), type);
				if (repairedMatch != null)
					repairedSth = true;
			}
		}

		return repairedSth ? brokenMatch.getMatch() : null;
	}

	private ITGGMatch repairViaShortcut(BrokenMatch brokenMatch) {
		DeletionType delType = brokenMatch.getDeletionType();
		if (DeletionType.getShortcutCCCandidates().contains(delType)) {
			return getShortcutRepairStrat().repair(brokenMatch.getMatch(), PatternType.CC);
		} else if (DeletionType.getShortcutPropCandidates().contains(delType)) {
			PatternType type = delType == DeletionType.SRC_PARTLY_TRG_NOT ? PatternType.FWD : PatternType.BWD;
			ITGGMatch repairedMatch = getShortcutRepairStrat().repair(brokenMatch.getMatch(), type);
			if (repairedMatch == null) {
				repairedMatch = getShortcutRepairStrat().repair(brokenMatch.getMatch(), PatternType.CC);
			}
			return repairedMatch;
		}
		return null;
	}

	public ITGGMatch repairOneMatch(AbstractRepairStrategy rStrat, ITGGMatch repairCandidate, PatternType type) {
		ITGGMatch repairedMatch = null;
		if (type != null)
			repairedMatch = rStrat.repair(repairCandidate, type);

		if (repairedMatch != null) {
			brokenRuleApplications.remove(getRuleApplicationNode(repairCandidate));
			precedenceGraph.removeMatch(repairCandidate);
			brokenRuleApplications.put(getRuleApplicationNode(repairedMatch), repairedMatch);
			precedenceGraph.notifyAddedMatch(repairedMatch);
			precedenceGraph.notifyRemovedMatch(repairedMatch);
		}

		return repairedMatch;
	}

	@Override
	public boolean isPatternRelevantForInterpreter(PatternType type) {
		switch (type) {
		case SRC:
		case TRG:
		case FWD:
		case BWD:
		case CONSISTENCY:
//		case CC:
		case FILTER_NAC_SRC:
		case FILTER_NAC_TRG:
			return true;
		default:
			return false;
		}
	}

	@Override
	public Collection<PatternType> getPatternRelevantForCompiler() {
		return PatternType.getIntegrateTypes();
	}

	@Override
	protected void addOperationalRuleMatch(ITGGMatch match) {
		if (match.getType() == PatternType.FILTER_NAC_SRC || match.getType() == PatternType.FILTER_NAC_TRG)
			addFilterNacMatch(match);
		else {
			if (match.getType() == PatternType.CONSISTENCY)
				multiplicityCounter.notifyAddedMatch(match);
			super.addOperationalRuleMatch(match);
		}
	}

	@Override
	public boolean removeOperationalRuleMatch(ITGGMatch match) {
		if (match.getType() == PatternType.FILTER_NAC_SRC || match.getType() == PatternType.FILTER_NAC_TRG)
			return removeFilterNacMatch(match);
		else {
			if (match.getType() == PatternType.CONSISTENCY)
				multiplicityCounter.notifyRemovedMatch(match);
			return super.removeOperationalRuleMatch(match);
		}
	}

	private void addFilterNacMatch(ITGGMatch match) {
		if (!pattern2filterNacMatches.containsKey(match.getPatternName())) {
			String ruleName = match.getRuleName().split("_")[0];
			ruleName2filterNacPatternNames.get(ruleName).add(match.getPatternName());

			pattern2filterNacMatches.put(match.getPatternName(), new HashMap<>());
			filterNacPattern2nodeNames.put(match.getPatternName(), match.getParameterNames());
		}

		Map<NACOverlap, Collection<ITGGMatch>> overlap2match = pattern2filterNacMatches.get(match.getPatternName());
		NACOverlap overlap = new NACOverlap(match);
		// the number of matches per overlap should not exceed a certain number
		overlap2match.putIfAbsent(overlap, new LinkedList<>());
		overlap2match.get(overlap).add(match);

		LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received & added " + match.getPatternName() + "(" + match.hashCode() + ")");
	}

	private boolean removeFilterNacMatch(ITGGMatch match) {
		NACOverlap overlap = new NACOverlap(match);
		return pattern2filterNacMatches.get(match.getPatternName()).get(overlap).remove(match);
	}

	public TGGMatchUtil getMatchUtil() {
		return matchUtil;
	}

	public PrecedenceGraph getPrecedenceGraph() {
		return precedenceGraph;
	}

	public MultiplicityCounter getMultiplicityCounter() {
		return multiplicityCounter;
	}

	public ModelChangeProtocol getModelChangeProtocol() {
		return modelChangeProtocol;
	}

	public IbexRedInterpreter getIbexRedInterpreter() {
		try {
			return (IbexRedInterpreter) redInterpreter;
		} catch (Exception e) {
			throw new RuntimeException("IbexRedInterpreter implementation is needed", e);
		}
	}

	public void removeBrokenMatch(ITGGMatch brokenMatch) {
		TGGRuleApplication ra = getRuleApplicationNode(brokenMatch);
		brokenRuleApplications.remove(ra);
		ra.setProtocol(null);
		for (EReference ref : ra.eClass().getEReferences()) {
			ra.eSet(ref, null);
		}

		precedenceGraph.removeMatch(brokenMatch);
	}

	public Collection<ITGGMatch> getFilterNacMatches(ITGGMatch match) {
		Collection<ITGGMatch> filterNacMatches = new LinkedList<>();
		for (String nacPatternName : ruleName2filterNacPatternNames.getOrDefault(match.getRuleName(), new LinkedList<>())) {
			Map<NACOverlap, Collection<ITGGMatch>> overlap2matches = pattern2filterNacMatches.get(nacPatternName);
			if (overlap2matches == null)
				continue;
			NACOverlap overlap = new NACOverlap(match, filterNacPattern2nodeNames.get(nacPatternName));
			filterNacMatches.addAll(overlap2matches.getOrDefault(overlap, new LinkedList<>()));
		}
		return filterNacMatches;
	}

	public Map<ITGGMatch, BrokenMatch> getClassifiedBrokenMatches() {
		return classifiedBrokenMatches;
	}

	public Map<ITGGMatch, ConflictContainer> getConflicts() {
		return match2conflicts;
	}

	public ModelChanges getUserModelChanges() {
		return modelChangeProtocol.getModelChanges(userDeltaKey);
	}

	public ModelChanges getGeneralModelChanges() {
		return modelChangeProtocol.getModelChanges(generalDeltaKey);
	}

	public ShortcutRepairStrategy getShortcutRepairStrat() {
		return (ShortcutRepairStrategy) repairStrategies.stream() //
				.filter(r -> r instanceof ShortcutRepairStrategy).findFirst().get();
	}

	public AttributeRepairStrategy getAttributeRepairStrat() {
		return (AttributeRepairStrategy) repairStrategies.stream() //
				.filter(r -> r instanceof AttributeRepairStrategy).findFirst().get();
	}

	/**
	 * Applies a given delta to source and target models specified by a {@link BiConsumer} providing the
	 * source and target root elements.
	 * <p>
	 * Alternatively use {@link INTEGRATE#applyDelta(DeltaContainer)}.
	 * </p>
	 * 
	 * @param delta delta to be applied
	 */
	public void applyDelta(BiConsumer<EObject, EObject> delta) {
		this.userDeltaBiConsumer = delta;
	}

	/**
	 * Applies a given delta to source and target models specified by a {@link delta.DeltaContainer
	 * DeltaContainer}.
	 * <p>
	 * Alternatively use {@link INTEGRATE#applyDelta(BiConsumer)}.
	 * </p>
	 * 
	 * @param delta delta to be applied
	 * @throws InvalidDeltaException if a <code>Delta</code> of the given <code>DeltaContainer</code>
	 *                               has an invalid structure or invalid components
	 */
	public void applyDelta(DeltaContainer delta) throws InvalidDeltaException {
		DeltaValidator.validate(delta);
		this.userDeltaContainer = delta;
	}

}
