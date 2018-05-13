package org.emoflon.ibex.tgg.operational.defaults;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterNACStrategy;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;

import language.TGG;
import language.TGGRule;

public class IbexOptions {

	private FilterNACStrategy filterNACStrategy = FilterNACStrategy.FILTER_NACS;

	private boolean blackInterpSupportsAttrConstrs = true;

	/**
	 * CorrContext nodes are local nodes in the SrcContext and TrgContext pattern
	 */
	private boolean setCorrContextNodesAsLocalNodes = false;

	/**
	 * EdgePatterns are only created if the number of edges in this pattern is at
	 * least this constant
	 */
	private int minimumNumberOfEdgesToCreateEdgePatterns = Integer.MAX_VALUE;

	/**
	 * Indicates if the edge patterns should be typed including attribute conditions
	 * and sub types
	 */
	private boolean stronglyTypedEdgedPatterns = false;

	private boolean debug;
	private String workspacePath;
	private String projectPath;
	private String projectName;
	private TGG tgg;
	private TGG flattenedTGG;
	private RuntimeTGGAttrConstraintProvider constraintProvider;
	private RuntimeTGGAttrConstraintFactory userDefinedConstraints;
	private boolean isModelGen;
	private SupportedILPSolver ilpSolver;

	public IbexOptions() {
		debug = Logger.getRootLogger().getLevel() == Level.DEBUG;
		projectPath = "/";
		workspacePath = "./../";
		setIlpSolver(SupportedILPSolver.Sat4J);
	}

	public boolean isModelGen() {
		return isModelGen;
	}

	public IbexOptions setModelGen(boolean isModelGen) {
		this.isModelGen = isModelGen;
		return this;
	}

	public IbexOptions debug(boolean debug) {
		this.debug = debug;
		return this;
	}

	public boolean debug() {
		return debug;
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

	public FilterNACStrategy getFilterNACStrategy() {
		return filterNACStrategy;
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
	
	public void blackInterpSupportsAttrConstrs(boolean value) {
		blackInterpSupportsAttrConstrs = value;
	}

	public int minimumNumberOfEdgesToCreateEdgePatterns() {
		return minimumNumberOfEdgesToCreateEdgePatterns;
	}

	public IbexOptions minimumNumberOfEdgesToCreateEdgePatterns(int n) {
		minimumNumberOfEdgesToCreateEdgePatterns = n;
		return this;
	}
	
	public boolean setCorrContextNodesAsLocalNodes() {
		return setCorrContextNodesAsLocalNodes;
	}
	
	public IbexOptions setCorrContextNodesAsLocalNodes(boolean value) {
		setCorrContextNodesAsLocalNodes = value;
		return this;
	}

	public boolean stronglyTypedEdgedPatterns() {
		return stronglyTypedEdgedPatterns;
	}

	public IbexOptions stronglyTypedEdgedPatterns(boolean value) {
		stronglyTypedEdgedPatterns = value;
		return this;
	}	
	
	public IbexOptions setFilterNACStrategy(FilterNACStrategy filterNACStrategy) {
		this.filterNACStrategy = filterNACStrategy;
		return this;
	}

	/**
	 * @return the ilpSolver
	 */
	public SupportedILPSolver getIlpSolver() {
		return ilpSolver;
	}

	/**
	 * @param ilpSolver
	 *            the ilpSolver to set
	 */
	public IbexOptions setIlpSolver(SupportedILPSolver ilpSolver) {
		this.ilpSolver = ilpSolver;
		return this;
	}
}
