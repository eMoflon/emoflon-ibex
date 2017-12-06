package org.emoflon.ibex.tgg.compiler.patterns.gen;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

public class GENForCCPattern extends GENPattern {
	public GENForCCPattern(PatternFactory factory) {
		super(factory);
		name = factory.getRule().getName() + PatternSuffixes.GENForCC + PatternSuffixes.CC;
	}
}
