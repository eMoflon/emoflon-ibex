package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.stream.Collectors;

import language.TGG;
import language.TGGRule;

public class IbexOptions {
	private boolean debug;
	private String projectPath;
	private TGG tgg;
	private TGG flattenedTGG;
	
	public IbexOptions() {
		debug = false;
		projectPath = "/";
	}
	
	public void debug(boolean debug) {
		this.debug = debug;
	}
	
	public boolean debug(){
		return debug;
	}

	public void projectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	
	public String projectPath(){
		return projectPath;
	}

	public void tgg(TGG tgg) {
		this.tgg = tgg;
	}
	
	public TGG tgg(){
		return tgg;
	}

	public void flattenedTgg(TGG flattenedTGG) {
		this.flattenedTGG = flattenedTGG;
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
}
