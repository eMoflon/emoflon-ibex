package org.emoflon.ibex.tgg.compiler.patterns.gen;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

public class GENForCCPattern extends GENBlackPattern {
	public GENForCCPattern(BlackPatternFactory factory) {
		super(factory);
		name = factory.getRule().getName() + PatternSuffixes.GENForCC + PatternSuffixes.CC;
	}
}
