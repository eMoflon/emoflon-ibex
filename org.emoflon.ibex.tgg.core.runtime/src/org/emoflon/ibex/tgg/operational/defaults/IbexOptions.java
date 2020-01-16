package org.emoflon.ibex.tgg.operational.defaults;

import java.io.IOException;
import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.tgg.compiler.defaults.IRegistrationHelper;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACStrategy;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.benchmark.EmptyBenchmarkLogger;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.ConflictResolver;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DefaultConflictResolver;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationPattern;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.operational.strategies.modules.MatchDistributor;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;

import language.TGG;
import language.TGGRule;
import language.TGGRuleNode;

public class IbexOptions {
	private boolean debug;
	private String workspacePath;
	private String projectPath;
	private String projectName;
	private TGG tgg;
	private TGG flattenedTGG;
	private TGGResourceHandler resourceHandler;
	private RuntimeTGGAttrConstraintProvider constraintProvider;
	private RuntimeTGGAttrConstraintFactory userDefinedConstraints;
	private SupportedILPSolver ilpSolver;
	private boolean repairAttributes;
	private EPackage corrMetamodel;
	private FilterNACStrategy lookAheadStrategy;
	private BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjecitity;
	private boolean ignoreDomainConformity;
	private boolean useShortcutRules;
	private boolean optimizeSyncPattern;
	private boolean applyConcurrently;
	private IbexExecutable executable;
	private IRegistrationHelper registrationHelper;
	private MatchDistributor matchDistributor;

	/**
	 * Switch to using edge patterns based on some heuristics (e.g., pattern size).
	 * If this is false (disabled), then edge patterns are never used.
	 */
	private boolean useEdgePatterns;
	private IBlackInterpreter blackInterpreter;

	// Benchmark Logging
	private BenchmarkLogger logger;
	
	// Model Integration
	private IntegrationPattern integrationPattern;
	private ConflictResolver conflictSolver;

	public IbexOptions() {
		debug = Logger.getRootLogger().getLevel() == Level.DEBUG;
		projectPath = "/";
		workspacePath = "./../";
		
		setIlpSolver(SupportedILPSolver.Sat4J);
		useEdgePatterns = false;
		lookAheadStrategy = FilterNACStrategy.FILTER_NACS;
		ignoreInjecitity = (x,y) -> false;
		ignoreDomainConformity = false;
		optimizeSyncPattern = false;
		logger = new EmptyBenchmarkLogger();
		repairAttributes = true;
		useShortcutRules = false;
		integrationPattern = new IntegrationPattern();
		conflictSolver = new DefaultConflictResolver();
		
		applyConcurrently = false;

		try {
			setResourceHandler(new TGGResourceHandler());
		} catch (IOException e) {
			e.printStackTrace();
		}
		matchDistributor = new MatchDistributor(this);
	}

	public IbexOptions debug(boolean debug) {
		this.debug = debug;
		return this;
	}

	public boolean debug() {
		return debug;
	}

	public boolean repairUsingShortcutRules() {
		return useShortcutRules;
	}
	
	public IbexOptions repairUsingShortcutRules(boolean useShortcutRules) {
		this.useShortcutRules = useShortcutRules;
		return this;
	}

	public boolean repairAttributes() {
		return repairAttributes;
	}

	public IbexOptions repairAttributes(boolean repairAttributes) {
		this.repairAttributes = repairAttributes;
		return this;
	}
	
	public boolean applyConcurrently() {
		return applyConcurrently;
	}

	public IbexOptions applyConcurrently(boolean applyConcurrently) {
		this.applyConcurrently = applyConcurrently;
		return this;
	}
	
	public IbexOptions workspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
		return this;
	}

	public String workspacePath() {
		return workspacePath;
	}

	public IbexOptions projectPath(String projectPath) {
		this.projectPath = projectPath;
		return this;
	}

	public String projectPath() {
		return projectPath;
	}

	public IbexOptions projectName(String projectName) {
		this.projectName = projectName;
		return this;
	}

	public String projectName() {
		return projectName;
	}

	public IbexOptions tgg(TGG tgg) {
		this.tgg = tgg;
		return this;
	}

	public TGG tgg() {
		return tgg;
	}

	public IbexOptions flattenedTgg(TGG flattenedTGG) {
		this.flattenedTGG = flattenedTGG;
		return this;
	}

	public TGG flattenedTGG() {
		return flattenedTGG;
	}

	public Collection<TGGRule> getFlattenedConcreteTGGRules() {
		return flattenedTGG.getRules()//
				.stream()//
				.filter(r -> !r.isAbstract())//
				.collect(Collectors.toList());
	}

	public Collection<TGGRule> getConcreteTGGRules() {
		return tgg.getRules()//
				.stream()//
				.filter(r -> !r.isAbstract())//
				.collect(Collectors.toList());
	}

	public IbexOptions setConstraintProvider(RuntimeTGGAttrConstraintProvider constraintProvider) {
		this.constraintProvider = constraintProvider;
		return this;
	}

	public RuntimeTGGAttrConstraintProvider constraintProvider() {
		return constraintProvider;
	}

	public IbexOptions userDefinedConstraints(RuntimeTGGAttrConstraintFactory userDefinedConstraints) {
		this.userDefinedConstraints = userDefinedConstraints;
		return this;
	}

	public RuntimeTGGAttrConstraintFactory userDefinedConstraints() {
		return userDefinedConstraints;
	}

	public IBlackInterpreter getBlackInterpreter() {
		return blackInterpreter;
	}
	
	public IbexOptions setBlackInterpreter(IBlackInterpreter blackInterpreter) {
		this.blackInterpreter = blackInterpreter;
		return this;
	}

	/**
	 * @return the ilpSolver
	 */
	public SupportedILPSolver getIlpSolver() {
		return ilpSolver;
	}

	/**
	 * @param ilpSolver the ilpSolver to set
	 */
	public IbexOptions setIlpSolver(SupportedILPSolver ilpSolver) {
		this.ilpSolver = ilpSolver;
		return this;
	}

	public void setCorrMetamodel(EPackage pack) {
		this.corrMetamodel = pack;
	}

	public EPackage getCorrMetamodel() {
		return this.corrMetamodel;
	}

	public boolean getUseEdgePatterns() {
		return useEdgePatterns;
	}

	public IbexOptions setUseEdgePatterns(boolean value) {
		useEdgePatterns = value;
		return this;
	}

	public FilterNACStrategy getLookAheadStrategy() {
		return lookAheadStrategy;
	}
	
	public IbexOptions setLookAheadStrategy(FilterNACStrategy lookAheadStrategy) {
		this.lookAheadStrategy = lookAheadStrategy;
		return this;
	}

	public boolean optimizeSyncPattern() {
		return optimizeSyncPattern;
	}

	public IbexOptions setOptimizeSyncPattern(boolean optimizeSyncPattern) {
		this.optimizeSyncPattern = optimizeSyncPattern;
		return this;
	}

	public BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjectivity() {
		return ignoreInjecitity;
	}
	
	public IbexOptions ignoreInjectivity(BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjecitity) {
		this.ignoreInjecitity = ignoreInjecitity;
		return this;
	}

	public boolean ignoreDomainConformity() {
		return ignoreDomainConformity;
	}
	
	public IbexOptions ignoreDomainConformity(boolean ignoreDomainConformity) {
		this.ignoreDomainConformity = ignoreDomainConformity;
		return this;
	}

	public IntegrationPattern getIntegrationPattern() {
		return integrationPattern;
	}

	public void setIntegrationPattern(IntegrationPattern integrationPattern) {
		this.integrationPattern = integrationPattern;
	}

	public BenchmarkLogger getBenchmarkLogger() {
		return logger;
	}

	public IbexOptions setBenchmarkLogger(BenchmarkLogger logger) {
		this.logger = logger;
		return this;
	}

	public ConflictResolver getConflictSolver() {
		return conflictSolver;
	}

	public void setConflictSolver(ConflictResolver conflictSolver) {
		this.conflictSolver = conflictSolver;
	}
	
	public TGGResourceHandler getResourceHandler() {
		return resourceHandler;
	}

	public IbexOptions setResourceHandler(TGGResourceHandler resourceHandler) {
		this.resourceHandler = resourceHandler;
		resourceHandler.setOptions(this);
		if(executable != null)
			executable.setResourceHandler(resourceHandler);
		return this;
	}
	
	public MatchDistributor getMatchDistributor() {
		return matchDistributor;
	}
	
	public IbexOptions setExecutable(IbexExecutable executable) {
		this.executable = executable;
		if(resourceHandler != null)
			resourceHandler.setExecutable(executable);
		return this;
	}
	
	public IbexExecutable getExecutable() {
		return executable;
	}
	
	public void registrationHelper(IRegistrationHelper registrationHelper) {
		this.registrationHelper = registrationHelper;
	}
	
	public IRegistrationHelper registrationHelper() {
		return registrationHelper;
	}
}