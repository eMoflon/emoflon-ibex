package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.strategies.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.ExtOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.ConflictDetector;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.BrokenMatchAnalyser;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.ModelChangeProtocol;
import org.emoflon.ibex.tgg.operational.strategies.opt.FixingCopier;
import org.emoflon.ibex.tgg.operational.strategies.sync.repair.strategies.AttributeRepairStrategy;

public abstract class INTEGRATE extends ExtOperationalStrategy {

	private IntegrationPattern pattern;
	private BrokenMatchAnalyser matchAnalyser;
	private ModelChangeProtocol modelChangeProtocol;
	private ConflictDetector conflictDetector;
	private INTEGRATE_OPT integrate_optimizer;

	protected Resource epg;

	// Element classification
	private Set<EObject> undetermined;
	private Set<EObject> toBeTranslated;
	private Set<EObject> toBeDeleted;

	private Set<ITGGMatch> filterNacMatches;
	private Map<ITGGMatch, AnalysedMatch> analysedMatches;
	private Set<Mismatch> mismatches;

	public INTEGRATE(IbexOptions options) throws IOException {
		super(options);
		pattern = new IntegrationPattern();
		undetermined = new HashSet<>();
		toBeTranslated = new HashSet<>();
		toBeDeleted = new HashSet<>();
		filterNacMatches = new HashSet<>();
		analysedMatches = new HashMap<>();
		mismatches = new HashSet<>();
	}

	public void integrate() throws IOException {
		run();
	}

	@Override
	public void run() throws IOException {
		blackInterpreter.updateMatches();

		integRepair();
		analyseAndClassifyMatches();
		detectAndResolveConflicts();
		calculateIntegrationSolution();
		cleanUp();
	}

	protected void integRepair() {
		// TODO adrianm: implement
	}

	protected void analyseAndClassifyMatches() {
		blackInterpreter.updateMatches();

		for (ITGGMatch brokenMatch : getBrokenMatches()) {
			AnalysedMatch analysedMatch = matchAnalyser.analyse(brokenMatch);
			analysedMatches.put(brokenMatch, analysedMatch);
		}

		for (AnalysedMatch am : getAnalysedMatches().values()) {
			for (MatchClassificationComponent mcc : pattern.getMCComponents()) {
				if (mcc.isApplicable(am)) {
					getMismatches().add(mcc.classify(am));
					break;
				}
			}
		}
	}

	protected void detectAndResolveConflicts() {
		conflictDetector.detectDeleteConflicts().forEach(conflict -> {
			options.getConflictSolver().resolveDeleteConflict(conflict).apply(this);
		});
	}

	protected void calculateIntegrationSolution() {
		// TODO adrianm: implement
	}

	protected void cleanUp() {
		modelChangeProtocol = new ModelChangeProtocol(s, t, c);
		undetermined = new HashSet<>();
		toBeTranslated = new HashSet<>();
		toBeDeleted = new HashSet<>();
		analysedMatches = new HashMap<>();
		mismatches = new HashSet<>();
	}

	@Override
	protected void initializeRepairStrategy(IbexOptions options) {
		// TODO adrianm: implement
		if (!repairStrategies.isEmpty())
			return;

		if (options.repairUsingShortcutRules()) {
			repairStrategies.add(new ShortcutRepairStrategy(this));
		}
		if (options.repairAttributes()) {
			repairStrategies.add(new AttributeRepairStrategy(this));
		}
	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new ExtPrecedenceGraph(this);
	}

	public Resource getEPGResource() {
		return epg;
	}

	@Override
	public void loadModels() throws IOException {
		long tic = System.currentTimeMillis();
		s = loadResource(options.projectPath() + "/instances/src.xmi");
		t = loadResource(options.projectPath() + "/instances/trg.xmi");
		c = loadResource(options.projectPath() + "/instances/corr.xmi");
		p = loadResource(options.projectPath() + "/instances/protocol.xmi");
		epg = createResource(options.projectPath() + "/instances/epg.xmi");
		EcoreUtil.resolveAll(rs);
		long toc = System.currentTimeMillis();

		logger.info("Loaded all models in: " + (toc - tic) / 1000.0 + "s");
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD) //
				|| patternName.endsWith(PatternSuffixes.BWD) //
				|| patternName.endsWith(PatternSuffixes.CONSISTENCY) //
				|| patternName.endsWith(PatternSuffixes.CC) //
				|| patternName.endsWith(PatternSuffixes.FILTER_NAC);
		// TODO adrianm: add missing pattern suffixes
	}

	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return super.isPatternRelevantForInterpreter(patternName);
	}

	@Override
	public IGreenPattern revokes(ITGGMatch match) {
		// TODO adrianm: implement
		return super.revokes(match);
	}

	@Override
	public void addMatch(org.emoflon.ibex.common.operational.IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.FILTER_NAC))
			filterNacMatches.add((ITGGMatch) match);
		else
			super.addMatch(match);
	}

	@Override
	public void removeMatch(org.emoflon.ibex.common.operational.IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.FILTER_NAC))
			filterNacMatches.remove(match);
		else
			super.removeMatch(match);
	}

	@Override
	public void registerBlackInterpreter(IBlackInterpreter blackInterpreter) throws IOException {
		super.registerBlackInterpreter(blackInterpreter);

		BenchmarkLogger.startTimer();
		initIntegrateDependantTools();
		options.getBenchmarkLogger().addToInitTime(BenchmarkLogger.stopTimer());
	}

	public BrokenMatchAnalyser getMatchAnalyser() {
		return matchAnalyser;
	}

	public ExtPrecedenceGraph getEPG() {
		try {
			return (ExtPrecedenceGraph) operationalMatchContainer;
		} catch (Exception e) {
			return null;
		}
	}

	public ModelChangeProtocol getModelChangeProtocol() {
		return modelChangeProtocol;
	}

	public IBlackInterpreter getBlackInterpreter() {
		return blackInterpreter;
	}

	public IRedInterpreter getRedInterpreter() {
		return redInterpreter;
	}

	public Collection<ITGGMatch> getBrokenMatches() {
		return brokenRuleApplications.values();
	}

	public Set<EObject> getUndeterminedElements() {
		return undetermined;
	}

	public Set<EObject> getToBeTranslatedElements() {
		return toBeTranslated;
	}

	public Set<EObject> getToBeDeletedElements() {
		return toBeDeleted;
	}

	public Set<ITGGMatch> getFilterNacMatches() {
		return filterNacMatches;
	}

	public Map<ITGGMatch, AnalysedMatch> getAnalysedMatches() {
		return analysedMatches;
	}

	public Set<Mismatch> getMismatches() {
		return mismatches;
	}

	/**
	 * Applies deltas to source and target models specified by a
	 * <code>BiConsumer</code>. <br>
	 * <br>
	 * BiConsumer's parameters: Root elements (src, trg)
	 * 
	 * @param delta delta to be applied
	 */
	public void applyDelta(BiConsumer<EObject, EObject> delta) {
		blackInterpreter.updateMatches();
		getEPG().update();

		modelChangeProtocol.attachAdapter();
		delta.accept(s.getContents().get(0), t.getContents().get(0));
		modelChangeProtocol.detachAdapter();
	}

	private void initIntegrateDependantTools() {
		matchAnalyser = new BrokenMatchAnalyser(this);
		modelChangeProtocol = new ModelChangeProtocol(s, t, c);
		conflictDetector = new ConflictDetector(this);
	}
	
	@Override
	public void loadTGG() throws IOException {
		super.loadTGG();
		integrate_optimizer.unrelaxReferences();
	}
	
	@Override
	public void saveModels() throws IOException {
		integrate_optimizer.saveModels();

		// Now save fixed models
		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
		epg.save(null);
	}
}
