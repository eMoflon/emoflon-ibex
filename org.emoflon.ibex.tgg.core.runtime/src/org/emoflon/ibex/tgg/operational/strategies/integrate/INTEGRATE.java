package org.emoflon.ibex.tgg.operational.strategies.integrate;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.delta.validation.DeltaValidator;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.GeneralConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.HierarchicalConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.MatchConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection.ConflictDetector;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.IntegrateMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol.ChangeKey;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.ConflictFreeElementsUpdatePolicy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.opt.CC;
import org.emoflon.ibex.tgg.operational.strategies.opt.LocalCC;
import org.emoflon.ibex.tgg.operational.updatepolicy.NextMatchUpdatePolicy;

import com.google.common.collect.Sets;

import delta.DeltaContainer;
import runtime.TGGRuleApplication;

public class INTEGRATE extends PropagatingOperationalStrategy {

	//// TOOLS ////
	protected TGGMatchUtil matchUtil;
	protected ModelChangeProtocol modelChangeProtocol;
	protected ConflictDetector conflictDetector;
	protected CC consistencyChecker;

	//// DATA ////
	protected DeltaContainer userDeltaContainer;
	protected BiConsumer<EObject, EObject> userDeltaBiConsumer;
	protected ChangeKey userDeltaKey;
	protected ChangeKey generalDeltaKey;

	protected Set<ITGGMatch> filterNacMatches;
	protected Map<ITGGMatch, BrokenMatch> brokenMatches;
	protected Set<GeneralConflict> conflicts;
	protected Map<ITGGMatch, MatchConflict> match2conflicts;

	public INTEGRATE(IbexOptions options) throws IOException {
		super(options);
		init();
	}

	private void init() throws IOException {
		filterNacMatches = new HashSet<>();
		brokenMatches = new HashMap<>();
		conflicts = new HashSet<>();
		match2conflicts = new HashMap<>();

		matchUtil = new TGGMatchUtil(this);
		modelChangeProtocol = new ModelChangeProtocol( //
				resourceHandler.getSourceResource(), resourceHandler.getTargetResource(), //
				resourceHandler.getCorrResource(), resourceHandler.getProtocolResource());
		conflictDetector = new ConflictDetector(this);
		consistencyChecker = new LocalCC(options) {
			@Override
			protected void processValidMatch(ITGGMatch match) {
				removeBrokenMatchesAfterCCMatchApplication(match);
			}
		};
		options.executable(this);

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
		initialize();
		for (IntegrationFragment fragment : options.integration.pattern().getIntegrationFragments())
			fragment.apply(this);
		cleanUp();
	}

	protected void initialize() {
		initializeRepairStrategy(options);
		matchDistributor.updateMatches();
		getIntegrMatchContainer().update();
		modelChangeProtocol.attachAdapter();
		userDeltaKey = new ChangeKey();
		generalDeltaKey = new ChangeKey();
		modelChangeProtocol.registerKey(generalDeltaKey);
	}

	protected void cleanUp() {
		modelChangeProtocol.deregisterKey(generalDeltaKey);
		modelChangeProtocol.detachAdapter();
		modelChangeProtocol = new ModelChangeProtocol(resourceHandler.getSourceResource(),
				resourceHandler.getTargetResource(), resourceHandler.getCorrResource());
		brokenMatches = new HashMap<>();
		conflicts = new HashSet<>();
		match2conflicts = new HashMap<>();
	}

	protected void classifyBrokenMatches() {
		matchDistributor.updateMatches();
		brokenMatches.clear();
		for (ITGGMatch match : brokenRuleApplications.values()) {
			brokenMatches.put(match, new BrokenMatch(this, match));
		}
	}

	protected void detectConflicts() {
		matchDistributor.updateMatches();
		getIntegrMatchContainer().update();

		conflicts = conflictDetector.detectConflicts();

		match2conflicts = conflicts.stream() //
				.flatMap(gc -> {
					if (gc instanceof HierarchicalConflict)
						return ((HierarchicalConflict) gc).getConflictDependency().stream();
					return Stream.of((MatchConflict) gc);
				}) //
				.collect(Collectors.toMap(mc -> mc.getMatch(), mc -> mc));
	}

	protected void translateConflictFreeElements() {
		setUpdatePolicy(new ConflictFreeElementsUpdatePolicy(this));
		translate();
		setUpdatePolicy(new NextMatchUpdatePolicy());
	}

	protected void resolveBrokenMatches() {
		brokenMatches.values().forEach(brokenMatch -> brokenMatch.resolveBrokenMatch());
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
			EcoreUtil.delete(ra, true);
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
	 * Determines green correspondence elements and adds them to the passed sets
	 * (nodesToRevoke & edgesToRevoke).
	 * </p>
	 * <p>
	 * To delete this elements call
	 * {@link IbexRedInterpreter#revoke(nodesToRevoke, edgesToRevoke)}.
	 * </p>
	 * 
	 * @param match
	 * @param nodesToRevoke
	 * @param edgesToRevoke
	 */
	private void prepareGreenCorrDeletion(ITGGMatch match, Set<EObject> nodesToRevoke, Set<EMFEdge> edgesToRevoke) {
		matchUtil.getObjects(match, new EltFilter().corr().create())
				.forEach(obj -> getIbexRedInterpreter().revokeCorr(obj, nodesToRevoke, edgesToRevoke));
	}

	protected void restoreBrokenCorrsAndRuleApplNodes(ChangeKey key) {
		matchDistributor.updateMatches();
		ModelChanges changes = modelChangeProtocol.getModelChanges(key);
		brokenRuleApplications.forEach((ra, m) -> matchUtil.getObjects(m, new EltFilter().corr().create())
				.forEach(obj -> restoreNode(changes, obj)));
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
		resourceHandler.getProtocolResource().getContents()
				.forEach(ra -> ra.eCrossReferences().forEach(obj -> untranslated.remove(obj)));

		getRedInterpreter().revoke(untranslated, Collections.emptySet());
	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new IntegrateMatchContainer(this);
	}

	@Override
	protected boolean processOneOperationalRuleMatch() {
		long tic = System.nanoTime();

		this.updateBlockedMatches();
		if (operationalMatchContainer.isEmpty())
			return false;

		ITGGMatch match = chooseOneMatch();
		if (match == null)
			return false;
		String ruleName = match.getRuleName();

		Optional<ITGGMatch> result = processOperationalRuleMatch(ruleName, match);
		removeOperationalRuleMatch(match);

		if (result.isPresent()) {
			options.debug.benchmarkLogger().addToNumOfMatchesApplied(1);
			LoggerConfig.log(LoggerConfig.log_matchApplication(), () -> "Removed as it has just been applied: ");
		} else
			LoggerConfig.log(LoggerConfig.log_matchApplication(), () -> "Removed as application failed: ");
		LoggerConfig.log(LoggerConfig.log_matchApplication(), () -> "" + match);

		matchApplicationTime += System.nanoTime() - tic;
		return true;
	}

	@Override
	protected ITGGMatch chooseOneMatch() {
		return this.notifyChooseMatch(new ImmutableMatchContainer(operationalMatchContainer));
	}

	@Override
	protected void addOperationalRuleMatch(ITGGMatch match) {
		if (match.getType() == PatternType.FILTER_NAC)
			filterNacMatches.add((ITGGMatch) match);
		else
			super.addOperationalRuleMatch(match);
	}

	@Override
	protected boolean repairBrokenMatches() {
		long tic = System.nanoTime();

		Collection<ITGGMatch> alreadyProcessed = cfactory.createObjectSet();
		for (AbstractRepairStrategy rStrategy : repairStrategies) {
			// TODO adrianm: also use attribute repair strategy for integrate
			if (rStrategy instanceof AttributeRepairStrategy)
				continue;

			for (ITGGMatch repairCandidate : rStrategy.chooseMatches(brokenRuleApplications)) {
				if (alreadyProcessed.contains(repairCandidate))
					continue;

				ITGGMatch repairedMatch = null;
				BrokenMatch mismatch = brokenMatches.get(repairCandidate);
				if (mismatch != null)
					repairedMatch = repairOneMatch(rStrategy, repairCandidate, mismatch.getPropagationDirection());
				else
					repairedMatch = repairOneMatch(rStrategy, repairCandidate, PropagationDirection.UNDEFINED);

				if (repairedMatch != null) {
					alreadyProcessed.add(repairCandidate);

					TGGRuleApplication oldRa = getRuleApplicationNode(repairCandidate);
					brokenRuleApplications.remove(oldRa);

					TGGRuleApplication newRa = getRuleApplicationNode(repairedMatch);
					brokenRuleApplications.put(newRa, repairedMatch);
					alreadyProcessed.add(repairedMatch);
				}
			}
		}

		repairTime += System.nanoTime() - tic;
		return !alreadyProcessed.isEmpty();
	}

	private ITGGMatch repairOneMatch(AbstractRepairStrategy rStrategy, ITGGMatch match,
			PropagationDirection propDirection) {
		ITGGMatch repairedMatch = null;
		if (propDirection == PropagationDirection.UNDEFINED) {
			repairedMatch = rStrategy.repair(match, PropagationDirection.FORWARD);
			if (repairedMatch == null)
				repairedMatch = rStrategy.repair(match, PropagationDirection.BACKWARD);
		} else
			repairedMatch = rStrategy.repair(match, propDirection);
		return repairedMatch;
	}

	@Override
	public boolean isPatternRelevantForInterpreter(PatternType type) {
		switch (type) {
		case FWD:
		case BWD:
		case CONSISTENCY:
		case CC:
		case FILTER_NAC:
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
	public boolean removeOperationalRuleMatch(ITGGMatch match) {
		if (match.getType() == PatternType.FILTER_NAC)
			return filterNacMatches.remove(match);
		else
			return super.removeOperationalRuleMatch(match);
	}

	public TGGMatchUtil getMatchUtil() {
		return matchUtil;
	}

	public IntegrateMatchContainer getIntegrMatchContainer() {
		try {
			return (IntegrateMatchContainer) operationalMatchContainer;
		} catch (Exception e) {
			throw new RuntimeException("ExtPrecedenceGraph implementation is needed", e);
		}
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
		EcoreUtil.delete(ra, true);
		getIntegrMatchContainer().removeBrokenMatch(brokenMatch);

	}

	public Set<ITGGMatch> getFilterNacMatches() {
		return filterNacMatches;
	}

	public Map<ITGGMatch, BrokenMatch> getBrokenMatches() {
		return brokenMatches;
	}

	public Map<ITGGMatch, MatchConflict> getConflicts() {
		return match2conflicts;
	}

	public ModelChanges getUserModelChanges() {
		return modelChangeProtocol.getModelChanges(userDeltaKey);
	}
	
	public ModelChanges getGeneralModelChanges() {
		return modelChangeProtocol.getModelChanges(generalDeltaKey);
	}

	/**
	 * Applies a given delta to source and target models specified by a
	 * {@link BiConsumer} providing the source and target root elements.
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
	 * Applies a given delta to source and target models specified by a
	 * {@link delta.DeltaContainer DeltaContainer}.
	 * <p>
	 * Alternatively use {@link INTEGRATE#applyDelta(BiConsumer)}.
	 * </p>
	 * 
	 * @param delta delta to be applied
	 * @throws InvalidDeltaException if a <code>Delta</code> of the given
	 *                               <code>DeltaContainer</code> has an invalid
	 *                               structure or invalid components
	 */
	public void applyDelta(DeltaContainer delta) throws InvalidDeltaException {
		DeltaValidator.validate(delta);
		this.userDeltaContainer = delta;
	}

}
