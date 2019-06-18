package org.emoflon.ibex.tgg.operational.defaults;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EPackage;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACStrategy;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;

import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;

import language.TGG;
import language.TGGRule;
import language.TGGRuleNode;

public class IbexOptions {
	private boolean blackInterpSupportsAttrConstrs = true;
	private boolean debug;
	private String workspacePath;
	private String projectPath;
	private String projectName;
	private TGG tgg;
	private TGG flattenedTGG;
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

	/**
	 * Switch to using edge patterns based on some heuristics (e.g., pattern size).
	 * If this is false (disabled), then edge patterns are never used.
	 */
	private boolean useEdgePatterns;
	private IBlackInterpreter blackInterpreter;

	public IbexOptions() {
		debug = Logger.getRootLogger().getLevel() == Level.DEBUG;
	
		projectPath = "/";
		workspacePath = "./../";
		
		ilpSolver = SupportedILPSolver.Sat4J;
		
		lookAheadStrategy = FilterNACStrategy.FILTER_NACS;
		
		ignoreInjecitity = (x,y) -> false;
		ignoreDomainConformity = false;

		useEdgePatterns = false;
		optimizeSyncPattern = false;

		repairAttributes = true;
		useShortcutRules = false;
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

	public boolean blackInterpSupportsAttrConstrs() {
		return blackInterpSupportsAttrConstrs;
	}

	public IbexOptions blackInterpSupportsAttrConstrs(boolean value) {
		blackInterpSupportsAttrConstrs = value;
		return this;
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
}