package org.emoflon.ibex.tgg.operational.util;

import language.TGG;

public class IbexOptions {
	private boolean debug;
	private boolean useFlattenedTGG;
	private String projectPath;
	private TGG tgg;
	private TGG flattenedTGG;
	
	public IbexOptions() {
		debug = false;
		useFlattenedTGG = false;
		projectPath = "/";
	}
	
	public void debug(boolean debug) {
		this.debug = debug;
	}
	
	public boolean debug(){
		return debug;
	}

	public void useFlattenedTGG(boolean useFlattenedTGG) {
		this.useFlattenedTGG = useFlattenedTGG; 
	}
	
	public boolean useFlattenedTGG(){
		return useFlattenedTGG;
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
}
