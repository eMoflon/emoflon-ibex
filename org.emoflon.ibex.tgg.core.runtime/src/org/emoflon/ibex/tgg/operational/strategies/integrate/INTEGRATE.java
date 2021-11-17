package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.PrecedenceMatchContainer;
import org.emoflon.ibex.tgg.operational.repair.ConcRepair;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictHandler;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection.MultiplicityCounter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol.ChangeKey;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.FilterNACMatchCollector;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.Revoker;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;
import org.emoflon.ibex.tgg.operational.strategies.opt.CC;
import org.emoflon.ibex.tgg.operational.strategies.opt.LocalCC;

import com.google.common.collect.Sets;

import delta.Delta;
import delta.DeltaContainer;

public class INTEGRATE extends PropagatingOperationalStrategy {

	//// TOOLS ////
	protected ModelChangeProtocol modelChangeProtocol;
	protected ConcRepair repair;
	protected MatchClassifier matchClassifier;
	protected TGGMatchUtilProvider matchUtils;
	protected Revoker revoker;
	protected ConflictHandler conflictHandler;
	protected CC consistencyChecker;
	protected PrecedenceGraph precedenceGraph;
	protected MultiplicityCounter multiplicityCounter;
	protected FilterNACMatchCollector filterNACMatchCollector;

	//// DATA ////
	protected ChangeKey userDeltaKey;
	protected ChangeKey generalDeltaKey;

	public INTEGRATE(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	protected void initializeAdditionalModules(IbexOptions options) throws IOException {
		super.initializeAdditionalModules(options);

		modelChangeProtocol = new ModelChangeProtocol( //
				resourceHandler.getSourceResource(), resourceHandler.getTargetResource(), //
				resourceHandler.getCorrResource(), resourceHandler.getProtocolResource());
		userDeltaKey = new ChangeKey();
		generalDeltaKey = new ChangeKey();
		modelChangeProtocol.registerKey(generalDeltaKey);

		repair = new ConcRepair(this);
		filterNACMatchCollector = new FilterNACMatchCollector(options);
		matchClassifier = new MatchClassifier(this);
		matchUtils = new TGGMatchUtilProvider(this);
		revoker = new Revoker(this);

		conflictHandler = new ConflictHandler(this);
		consistencyChecker = new LocalCC(options) {
			@Override
			protected void processValidMatch(ITGGMatch match) {
				removeBrokenMatchesAfterCCMatchApplication(match);
			}
		};
		options.executable(this);
		precedenceGraph = new PrecedenceGraph(this);
		multiplicityCounter = new MultiplicityCounter(this);
	}

	private void removeBrokenMatchesAfterCCMatchApplication(ITGGMatch ccMatch) {
		Set<EObject> ccObjects = matchUtils.get(ccMatch).getObjects(new EltFilter().srcAndTrg().create());

		Collection<ITGGMatch> brokenMatches = new HashSet<>(matchHandler.getBrokenMatches());
		for (ITGGMatch brokenMatch : brokenMatches) {
			Set<EObject> brokenObjects = matchUtils.get(brokenMatch).getObjects(new EltFilter().srcAndTrg().create());
			if (!Sets.intersection(ccObjects, brokenObjects).isEmpty())
				revoker.removeBrokenMatch(brokenMatch);
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

		matchDistributor.updateMatches();

		times.addTo("run:initialize", Timer.stop());
	}

	protected void cleanUp() {
		Timer.start();

		modelChangeProtocol.clearAll();
		matchClassifier.clear();
		conflictHandler.clear();

		times.addTo("run:cleanUp", Timer.stop());
	}

	@Override
	public void terminate() throws IOException {
		modelChangeProtocol.deregisterKey(generalDeltaKey);
		modelChangeProtocol.detachAdapter();
		super.terminate();
	}

	protected void translateConflictFree() {
//		setUpdatePolicy(new ConflictFreeElementsUpdatePolicy(this));
		translate();
//		setUpdatePolicy(new NextMatchUpdatePolicy());
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
		matchHandler.removeOperationalMatch(match);

		if (result.isPresent()) {
			options.debug.benchmarkLogger().addToNumOfMatchesApplied(1);
			LoggerConfig.log(LoggerConfig.log_ruleApplication(), () -> "Matches: removed (as it has just been applied) " //
					+ match.getPatternName() + "(" + match.hashCode() + ")\n");
		} else {
			LoggerConfig.log(LoggerConfig.log_ruleApplication(), () -> "Matches: removed (as application failed) " //
					+ match.getPatternName() + "(" + match.hashCode() + ")\n");
		}

		times.addTo("translate:ruleApplication", Timer.stop());
		return true;
	}

	@Override
	protected ITGGMatch chooseOneMatch() {
		return this.notifyChooseMatch(new ImmutableMatchContainer(operationalMatchContainer));
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return new HashSet<>(Arrays.asList(PatternType.SRC, PatternType.TRG, PatternType.FWD, PatternType.BWD, PatternType.CC));
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
		Timer.start();

		modelChangeProtocol.attachAdapter();
		matchDistributor.updateMatches();

		logDeltaApplication();

		modelChangeProtocol.registerKey(userDeltaKey);
		delta.accept(resourceHandler.getSourceResource().getContents().get(0), resourceHandler.getTargetResource().getContents().get(0));
		modelChangeProtocol.deregisterKey(userDeltaKey);

		times.addTo("applyDelta", Timer.stop());
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
		Timer.start();

		modelChangeProtocol.attachAdapter();
		matchDistributor.updateMatches();

		logDeltaApplication();

		modelChangeProtocol.registerKey(userDeltaKey);
		for (Delta d : delta.getDeltas())
			d.apply();
		modelChangeProtocol.deregisterKey(userDeltaKey);

		times.addTo("applyDelta", Timer.stop());
	}

	//// GETTER ////

	public ConcRepair repair() {
		return repair;
	}

	public FilterNACMatchCollector filterNACMatchCollector() {
		return filterNACMatchCollector;
	}

	public TGGMatchUtilProvider matchUtils() {
		return matchUtils;
	}

	public MatchClassifier matchClassifier() {
		return matchClassifier;
	}

	public Revoker revoker() {
		return revoker;
	}

	public PrecedenceGraph precedenceGraph() {
		return precedenceGraph;
	}

	public ConflictHandler conflictHandler() {
		return conflictHandler;
	}

	public MultiplicityCounter multiplicityCounter() {
		return multiplicityCounter;
	}

	public ModelChangeProtocol modelChangeProtocol() {
		return modelChangeProtocol;
	}

	public IbexRedInterpreter ibexRedInterpreter() {
		try {
			return (IbexRedInterpreter) redInterpreter;
		} catch (Exception e) {
			throw new RuntimeException("IbexRedInterpreter implementation is needed", e);
		}
	}

	public ModelChanges userModelChanges() {
		return modelChangeProtocol.getModelChanges(userDeltaKey);
	}

	public ModelChanges generalModelChanges() {
		return modelChangeProtocol.getModelChanges(generalDeltaKey);
	}

	private void logDeltaApplication() {
		LoggerConfig.log(LoggerConfig.log_executionStructure(), () -> "Delta Application:\n");
	}

}
