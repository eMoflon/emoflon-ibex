package org.emoflon.ibex.tgg.compiler.patterns;

import language.TGG;

public class NACCache {

	private static NACCache util;
	
	private TGG tgg;
	
	public static NACCache getInstance(TGG tgg) {
		if(util == null) {
			util = new NACCache(tgg);
		}
		return util;
	}
	
	private NACCache(TGG tgg) {
		this.tgg = tgg;
		initialize();
	}

	private void initialize() {
		
	}
	
	
	
}
