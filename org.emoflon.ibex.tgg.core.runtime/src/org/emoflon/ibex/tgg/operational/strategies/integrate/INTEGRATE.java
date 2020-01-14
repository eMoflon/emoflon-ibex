package org.emoflon.ibex.tgg.operational.strategies.integrate;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.delta.validation.DeltaValidator;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.ConflictDetector;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol.GroupKey;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.ConflictFreeElementsUpdatePolicy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;
import org.emoflon.ibex.tgg.operational.updatepolicy.NextMatchUpdatePolicy;

import delta.DeltaContainer;
import runtime.TGGRuleApplication;

public class INTEGRATE extends PropagatingOperationalStrategy {
	private IntegrationPattern pattern;
	private MatchAnalyser matchAnalyser;
	private ModelChangeProtocol modelChangeProtocol;
	private ConflictDetector conflictDetector;

	protected DeltaContainer userDeltaContainer;
	protected BiConsumer<EObject, EObject> userDeltaBiConsumer;
	protected GroupKey userDeltaKey;

	private Set<ITGGMatch> filterNacMatches;
	private Map<ITGGMatch, Mismatch> mismatches;
	private Map<ITGGMatch, DeleteConflict> conflicts;

	public INTEGRATE(IbexOptions options) throws IOException {
		super(options);
		pattern = new IntegrationPattern();
		filterNacMatches = new HashSet<>();
		mismatches = new HashMap<>();
		conflicts = new HashMap<>();

		matchAnalyser = new MatchAnalyser(this);
		modelChangeProtocol = new ModelChangeProtocol( //
				resourceHandler.getSourceResource(), resourceHandler.getTargetResource(), //
				resourceHandler.getCorrResource(), resourceHandler.getProtocolResource());
		conflictDetector = new ConflictDetector(this);
	}

	public void integrate() throws IOException {
		run();
	}

	@Override
	public void run() throws IOException {
		initialize();
		modelChangeProtocol.setGroupKey(userDeltaKey);
		applyUserDelta();

		matchDistributor.updateMatches();
		getEPG().update();

		do {
			classifyMatches();
			detectConflicts();
			translateConflictFreeElements();
		} while (repairBrokenMatches());

		detectAndResolveConflicts();
		translateConflictFreeElements();
//		deleteCorrsOfBrokenMatches();
//		classifyMatches();
//		resolveMismatches();
//		translate();

		modelChangeProtocol.unsetGroupKey();
		cleanUp();
	}

	protected void initialize() {
		initializeRepairStrategy(options);
		matchDistributor.updateMatches();
		getEPG().update();
		modelChangeProtocol.attachAdapter();
		userDeltaKey = modelChangeProtocol.new GroupKey();
	}

	protected void applyUserDelta() {
		if (userDeltaContainer != null) {
			modelChangeProtocol.util.applyUserDelta(userDeltaContainer);
			userDeltaContainer = null;
		} else if (userDeltaBiConsumer != null) {
			userDeltaBiConsumer.accept(resourceHandler.getSourceResource().getContents().get(0),
					resourceHandler.getTargetResource().getContents().get(0));
			userDeltaBiConsumer = null;
		}

	}

	protected void deleteCorrsOfBrokenMatches() {
		Map<TGGRuleApplication, ITGGMatch> processed = new HashMap<>();
		do {
			matchDistributor.updateMatches();
		} while (deleteCorrs(processed));
		brokenRuleApplications.putAll(processed);
	}

	protected void classifyMatches() {
		matchDistributor.updateMatches();

		mismatches.clear();
		for (ITGGMatch brokenMatch : getBrokenMatches()) {
			MatchAnalysis analysis = matchAnalyser.getAnalysis(brokenMatch);
			for (MatchClassificationComponent mcc : pattern.getMCComponents()) {
				if (mcc.isApplicable(analysis)) {
					mismatches.put(analysis.getMatch(), mcc.classify(this, analysis));
					break;
				}
			}
		}
	}

	protected void detectConflicts() {
		matchDistributor.updateMatches();

		conflicts = conflictDetector.detectDeleteConflicts().stream()
				.collect(Collectors.toMap(c -> c.getMatch(), c -> c));
	}

	protected void detectAndResolveConflicts() {
		matchDistributor.updateMatches();
		getEPG().update();

		conflictDetector.detectDeleteConflicts().forEach(conflict -> {
			options.getConflictSolver().resolveDeleteConflict(conflict).apply(this);
			conflicts.remove(conflict.getMatch());
		});
	}

	protected void translateConflictFreeElements() {
		setUpdatePolicy(new ConflictFreeElementsUpdatePolicy(this));
		translate();
		setUpdatePolicy(new NextMatchUpdatePolicy());
	}

	protected void resolveMismatches() {
		mismatches.values().forEach(mismatch -> {
			mismatch.resolveMismatch(this);
			// TODO adrianm: implement
		});
	}

	protected void cleanUp() {
		modelChangeProtocol.detachAdapter();
		modelChangeProtocol = new ModelChangeProtocol(resourceHandler.getSourceResource(),
				resourceHandler.getTargetResource(), resourceHandler.getCorrResource());
		mismatches = new HashMap<>();
		conflicts = new HashMap<>();
	}

	private boolean deleteCorrs(Map<TGGRuleApplication, ITGGMatch> processed) {
		if (brokenRuleApplications.isEmpty())
			return false;
		getBrokenMatches().forEach(m -> deleteGreenCorr(m));
		processed.putAll(brokenRuleApplications);
		brokenRuleApplications.clear();
		return true;
	}

	/**
	 * Deletes all green correspondence elements.
	 * 
	 * @param match
	 */
	private void deleteGreenCorr(ITGGMatch match) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();
		prepareGreenCorrDeletion(match, nodesToRevoke, edgesToRevoke);
		getIbexRedInterpreter().revoke(nodesToRevoke, edgesToRevoke);
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
		matchAnalyser.getNodes(match, new EltFilter().corr().create()).forEach(c -> //
		getIbexRedInterpreter().revokeCorr((EObject) match.get(c.getName()), nodesToRevoke, edgesToRevoke));
	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new ExtPrecedenceGraph(this);
	}

	@Override
	protected boolean processOneOperationalRuleMatch() {
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
			options.getBenchmarkLogger().addToNumOfMatchesApplied(1);
			logger.debug("Removed as it has just been applied: ");
		} else
			logger.debug("Removed as application failed: ");

		logger.debug(match);

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
		Collection<ITGGMatch> alreadyProcessed = cfactory.createObjectSet();
		for (AbstractRepairStrategy rStrategy : repairStrategies) {
			// TODO adrianm: also use attribute repair strategy for integrate
			if (rStrategy instanceof AttributeRepairStrategy)
				continue;

			for (ITGGMatch repairCandidate : rStrategy.chooseMatches(brokenRuleApplications)) {
				if (alreadyProcessed.contains(repairCandidate))
					continue;

				ITGGMatch repairedMatch = null;
				Mismatch mismatch = mismatches.get(repairCandidate);
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

					options.getBenchmarkLogger().addToNumOfMatchesRepaired(1);
				}
			}
		}
		return !alreadyProcessed.isEmpty();
	}

	private ITGGMatch repairOneMatch(AbstractRepairStrategy rStrategy, ITGGMatch match, PropagationDirection propDirection) {
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
	public boolean removeOperationalRuleMatch(ITGGMatch match) {
		if (match.getType() == PatternType.FILTER_NAC)
			return filterNacMatches.remove(match);
		else
			return super.removeOperationalRuleMatch(match);
	}

	public MatchAnalyser getMatchAnalyser() {
		return matchAnalyser;
	}

	public ExtPrecedenceGraph getEPG() {
		try {
			return (ExtPrecedenceGraph) operationalMatchContainer;
		} catch (Exception e) {
			throw new RuntimeException("ExtPrecedenceGraph implementation is needed", e);
		}
	}

	public ModelChangeProtocol getModelChangeProtocol() {
		return modelChangeProtocol;
	}

	public IRedInterpreter getRedInterpreter() {
		return redInterpreter;
	}

	public IbexRedInterpreter getIbexRedInterpreter() {
		try {
			return (IbexRedInterpreter) getRedInterpreter();
		} catch (Exception e) {
			throw new RuntimeException("IbexRedInterpreter implementation is needed", e);
		}
	}

	public Collection<ITGGMatch> getBrokenMatches() {
		return brokenRuleApplications.values();
	}

	public Set<ITGGMatch> getFilterNacMatches() {
		return filterNacMatches;
	}

	public Map<ITGGMatch, Mismatch> getMismatches() {
		return mismatches;
	}

	public Map<ITGGMatch, DeleteConflict> getConflicts() {
		return conflicts;
	}

	public ModelChanges getUserModelChanges() {
		return modelChangeProtocol.getModelChanges(userDeltaKey);
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

	@Override
	public Collection<PatternType> getPatternRelevantForCompiler() {
		return PatternType.getIntegrateTypes();
	}
}
