package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.patterns.EmptyGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.FWDGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public class FWD_Strategy extends SYNC_Strategy {
	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		return library.getSorted_FWD();
	}

	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD);
	}

	@Override
	public IGreenPattern revokes(IGreenPatternFactory greenFactory, String patternName, String ruleName) {
		if(patternName.equals(ConsistencyPattern.getName(ruleName)))
			return greenFactory.createGreenPattern(FWDGreenPattern.class);
		else
			return greenFactory.createGreenPattern(EmptyGreenPattern.class);
	}
}
