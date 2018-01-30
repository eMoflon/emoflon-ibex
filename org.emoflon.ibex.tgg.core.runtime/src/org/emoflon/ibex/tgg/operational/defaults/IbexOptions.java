package org.emoflon.ibex.tgg.operational.defaults;

import java.util.Collection;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;

import language.TGG;
import language.TGGRule;

public class IbexOptions {
	public static boolean blackInterpSupportsAttrConstrs = true;
	
	private boolean debug;
	private String workspacePath;
	private String projectPath;
	private String projectName;
	private TGG tgg;
	private TGG flattenedTGG;
	private RuntimeTGGAttrConstraintProvider constraintProvider;
	private RuntimeTGGAttrConstraintFactory userDefinedConstraints;
	private boolean isModelGen;
	
	public IbexOptions() {
		debug = false;
		projectPath = "/";
		workspacePath = "./../";
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
	
	public boolean debug(){
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
	
	public String projectPath(){
		return projectPath;
	}

	public IbexOptions projectName(String projectName) {
		this.projectPath = projectName;
		return this;
	}
	
	public String projectName(){
		return projectName;
	}

	
	public IbexOptions tgg(TGG tgg) {
		this.tgg = tgg;
		return this;
	}
	
	public TGG tgg(){
		return tgg;
	}

	public IbexOptions flattenedTgg(TGG flattenedTGG) {
		this.flattenedTGG = flattenedTGG;
		return this;
	}
	
	public TGG flattenedTGG(){
		return flattenedTGG;
	}

	public Collection<TGGRule> getFlattenedConcreteTGGRules() {
		return flattenedTGG.getRules()
				.stream()
				.filter(r -> !r.isAbstract())
				.collect(Collectors.toList());
	}

	public Collection<TGGRule> getConcreteTGGRules() {
		return tgg.getRules()
				.stream()
				.filter(r -> !r.isAbstract())
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
}
