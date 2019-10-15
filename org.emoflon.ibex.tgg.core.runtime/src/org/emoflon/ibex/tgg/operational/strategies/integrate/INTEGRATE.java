package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.ExtOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.BrokenMatchAnalyser;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.ModelChangeProtocol;

import precedencegraph.NodeContainer;

public abstract class INTEGRATE extends ExtOperationalStrategy {

	private IntegrationPattern pattern;
	private BrokenMatchAnalyser matchAnalyser;
	private ModelChangeProtocol modelChangeProtocol;

	protected Resource epg;

	// Element classification
	private Set<EObject> undetermined;
	private Set<EObject> toBeTranslated;
	private Set<EObject> toBeDeleted;

	private Set<IMatch> filterNacMatches;

	private Map<EObject, List<EObject>> addedElts;
	private Map<IMatch, AnalysedMatch> analysedMatches;

	public INTEGRATE(IbexOptions options) throws IOException {
		super(options);
		pattern = new IntegrationPattern();
		undetermined = new HashSet<>();
		toBeTranslated = new HashSet<>();
		toBeDeleted = new HashSet<>();
		filterNacMatches = new HashSet<>();
		addedElts = new HashMap<>();
		analysedMatches = new HashMap<>();
	}

	public void integrate() throws IOException {
		run();
	}

	@Override
	public void run() throws IOException {
		blackInterpreter.updateMatches();

//		repair();
		setup();
		detectConflicts();
		cleanUp();
	}

	protected void setup() {
		pattern.getComponents().forEach(c -> c.apply(this));
	}

	protected void detectConflicts() {
		NodeContainer nodes = getEPG().getExtGraph();
		// TODO Continue here...
	}

	protected void cleanUp() {
		undetermined = new HashSet<>();
		toBeTranslated = new HashSet<>();
		toBeDeleted = new HashSet<>();
		addedElts = new HashMap<>();
		analysedMatches = new HashMap<>();
	}

	@Override
	protected void initializeRepairStrategy(IbexOptions options) {
		// TODO Auto-generated method stub

	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new ExtPrecedenceGraph(this);
	}

	public Resource getEPGResource() {
		return epg;
	}

	@Override
	public void saveModels() throws IOException {
		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
		epg.save(null);
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
				|| patternName.endsWith(PatternSuffixes.FILTER_NAC);
		// TODO Add missing pattern suffixes
	}

	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return super.isPatternRelevantForInterpreter(patternName);
	}

	@Override
	public IGreenPattern revokes(IMatch match) {
		// TODO Auto-generated method stub
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

		initModelChangeProtocol();
		initMatchAnalyser();

		options.getBenchmarkLogger().addToInitTime(BenchmarkLogger.stopTimer());
	}

	public BrokenMatchAnalyser getMatchAnalyser() {
		return matchAnalyser;
	}

	public IBlackInterpreter getBlackInterpreter() {
		return blackInterpreter;
	}

	public IRedInterpreter getRedInterpreter() {
		return redInterpreter;
	}

	public Collection<IMatch> getBrokenMatches() {
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

	public Set<IMatch> getFilterNacMatches() {
		return filterNacMatches;
	}

	public Map<IMatch, AnalysedMatch> getAnalysedMatches() {
		return analysedMatches;
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
		delta.accept(s.getContents().get(0), t.getContents().get(0));
	}

	private void initMatchAnalyser() {
		if (matchAnalyser == null)
			matchAnalyser = new BrokenMatchAnalyser(this);
	}

	private void initModelChangeProtocol() {
		modelChangeProtocol = new ModelChangeProtocol();
		modelChangeProtocol.attachProtocolAdapterTo(s);
		modelChangeProtocol.attachProtocolAdapterTo(t);
	}

	private ExtPrecedenceGraph getEPG() {
		try {
			return (ExtPrecedenceGraph) operationalMatchContainer;
		} catch (Exception e) {
			return null;
		}
	}

}
