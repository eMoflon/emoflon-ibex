package org.emoflon.ibex.tgg.compiler.patterns.gen;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

public class GENForCCPattern extends GENPattern{

	public GENForCCPattern(PatternFactory factory) {
		super(factory);
		
	}
	
	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.GENForCC + PatternSuffixes.CC;
	}

}
