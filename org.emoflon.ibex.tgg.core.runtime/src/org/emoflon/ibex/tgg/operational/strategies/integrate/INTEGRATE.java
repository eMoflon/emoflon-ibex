package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.ExtOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.BrokenMatchAnalyser;

import precedencegraph.Node;
import precedencegraph.NodeContainer;

public abstract class INTEGRATE extends ExtOperationalStrategy {

	private IntegrationPattern pattern;
	private BrokenMatchAnalyser matchAnalyser;

	// Element classification
	private Set<EObject> undetermined;
	private Set<EObject> toBeTranslated;
	private Set<EObject> toBeDeleted;

	private Set<IMatch> filterNacMatches;

	public INTEGRATE(IbexOptions options) throws IOException {
		super(options);
		pattern = new IntegrationPattern();
		undetermined = new HashSet<>();
		toBeTranslated = new HashSet<>();
		toBeDeleted = new HashSet<>();
		filterNacMatches = new HashSet<>();
	}

	public void integrate() throws IOException {
		run();
	}

	@Override
	public void run() throws IOException {
		blackInterpreter.updateMatches();

//		repair();
		setup();

		NodeContainer nodes = getEPG().getExtGraph();
		Set<AnalysedMatch> analysedMatches = new HashSet<>();
		for (Node node : nodes.getNodes()) {
			if (node.isBroken()) {
				IMatch match = getEPG().getMatch(node);
				analysedMatches.add(matchAnalyser.analyse(match));
			}
		}
	}

	protected void setup() {
		initMatchAnalyser();
		pattern.getComponents().forEach(c -> c.apply(this));
	}

	@Override
	protected void initializeRepairStrategy(IbexOptions options) {
		// TODO Auto-generated method stub

	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new ExtPrecedenceGraph(this);
	}

	@Override
	public void saveModels() throws IOException {
		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
	}

	@Override
	public void loadModels() throws IOException {
		long tic = System.currentTimeMillis();
		s = loadResource(options.projectPath() + "/instances/src.xmi");
		t = loadResource(options.projectPath() + "/instances/trg.xmi");
		c = loadResource(options.projectPath() + "/instances/corr.xmi");
		p = loadResource(options.projectPath() + "/instances/protocol.xmi");
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

	/**
	 * Applies deltas to source and target models specified by a
	 * <code>BiConsumer</code>. <br>
	 * <br>
	 * BiConsumers parameters: Root elements (src, trg)
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

	private ExtPrecedenceGraph getEPG() {
		try {
			return (ExtPrecedenceGraph) operationalMatchContainer;
		} catch (Exception e) {
			return null;
		}
	}

}
