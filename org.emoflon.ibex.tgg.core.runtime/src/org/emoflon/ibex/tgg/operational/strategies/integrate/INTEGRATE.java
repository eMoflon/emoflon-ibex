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
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.ExtOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.EltClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.ConflictDetector;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.BrokenMatchAnalyser;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.ModelChangeProtocol;

import language.BindingType;
import language.TGGRuleCorr;
import runtime.TGGRuleApplication;

public abstract class INTEGRATE extends ExtOperationalStrategy {

	private IntegrationPattern pattern;
	private BrokenMatchAnalyser matchAnalyser;
	private ModelChangeProtocol modelChangeProtocol;
	private ConflictDetector conflictDetector;
	private INTEGRATE_OPT integrate_optimizer;

	protected Resource epg;

	// Element classification
	private Map<EObject, EltClassifier> classifiedNodes;
	private Map<EMFEdge, EltClassifier> classifiedEdges;

	private Set<IMatch> filterNacMatches;
	private Map<IMatch, AnalysedMatch> analysedMatches;
	private Set<Mismatch> mismatches;

	public INTEGRATE(IbexOptions options) throws IOException {
		super(options);
		pattern = new IntegrationPattern();
		classifiedNodes = new HashMap<>();
		classifiedEdges = new HashMap<>();
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
		deleteCorrsOfBrokenMatches();
		analyseAndClassifyMatches();
		detectAndResolveConflicts();
		analyseAndClassifyMatches();
		fillClassificationMaps();
		calculateIntegrationSolution();
		cleanUp();
	}

	protected void integRepair() {
		// TODO adrianm: implement
	}

	protected void deleteCorrsOfBrokenMatches() {
		Map<TGGRuleApplication, IMatch> processed = new HashMap<>();
		do {
			blackInterpreter.updateMatches();
		} while (deleteCorrs(processed));
		brokenRuleApplications.putAll(processed);
	}

	protected void analyseAndClassifyMatches() {
		blackInterpreter.updateMatches();
		getEPG().update();

		analysedMatches.clear();
		for (IMatch brokenMatch : getBrokenMatches()) {
			AnalysedMatch analysedMatch = matchAnalyser.analyse(brokenMatch);
			analysedMatches.put(brokenMatch, analysedMatch);
		}

		mismatches.clear();
		for (AnalysedMatch am : getAnalysedMatches().values()) {
			for (MatchClassificationComponent mcc : pattern.getMCComponents()) {
				if (mcc.isApplicable(am)) {
					getMismatches().add(mcc.classify(this, am));
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

	protected void fillClassificationMaps() {
		mismatches.forEach(mm -> {
			classifiedNodes.putAll(mm.getClassifiedNodes());
			classifiedEdges.putAll(mm.getClassifiedEdges());
		});
	}

	protected void calculateIntegrationSolution() {
		// TODO adrianm: implement
	}

	private boolean deleteCorrs(Map<TGGRuleApplication, IMatch> processed) {
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
	private void deleteGreenCorr(IMatch match) {
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
	private void prepareGreenCorrDeletion(IMatch match, Set<EObject> nodesToRevoke, Set<EMFEdge> edgesToRevoke) {
		matchAnalyser.getRule(match.getRuleName()).getNodes().stream() //
				.filter(n -> (n instanceof TGGRuleCorr) && n.getBindingType().equals(BindingType.CREATE)) //
				.map(c -> (EObject) match.get(c.getName())) //
				.forEach(c -> getIbexRedInterpreter().revokeCorr(c, nodesToRevoke, edgesToRevoke));
	}

	protected void cleanUp() {
		modelChangeProtocol = new ModelChangeProtocol(s, t, c);
		classifiedNodes = new HashMap<>();
		classifiedEdges = new HashMap<>();
		analysedMatches = new HashMap<>();
		mismatches = new HashSet<>();
	}

	@Override
	protected void initializeRepairStrategy(IbexOptions options) {
		// TODO adrianm: implement
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
	}

	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return super.isPatternRelevantForInterpreter(patternName);
	}

	@Override
	public IGreenPattern revokes(IMatch match) {
		// TODO adrianm: implement
		return super.revokes(match);
	}

	@Override
	public void addMatch(org.emoflon.ibex.common.operational.IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.FILTER_NAC))
			filterNacMatches.add((IMatch) match);
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
			throw new RuntimeException("ExtPrecedenceGraph implementation is needed", e);
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

	public IbexRedInterpreter getIbexRedInterpreter() {
		try {
			return (IbexRedInterpreter) getRedInterpreter();
		} catch (Exception e) {
			throw new RuntimeException("IbexRedInterpreter implementation is needed", e);
		}
	}

	public Collection<IMatch> getBrokenMatches() {
		return brokenRuleApplications.values();
	}

	public Map<EObject, EltClassifier> getClassifiedNodes() {
		return classifiedNodes;
	}

	public Map<EMFEdge, EltClassifier> getClassifiedEdges() {
		return classifiedEdges;
	}

	public Set<IMatch> getFilterNacMatches() {
		return filterNacMatches;
	}

	public Map<IMatch, AnalysedMatch> getAnalysedMatches() {
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
