package org.emoflon.ibex.tgg.compiler.patterns.gen;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

public class GENForCOPattern extends GENBlackPattern {
	public GENForCOPattern(BlackPatternFactory factory) {
		super(factory);
		name = factory.getRule().getName() + PatternSuffixes.GENForCO + PatternSuffixes.CO;
	}
}
