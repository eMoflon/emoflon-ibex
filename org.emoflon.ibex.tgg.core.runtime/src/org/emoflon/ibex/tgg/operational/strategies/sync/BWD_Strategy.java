package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.patterns.BWDGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.EmptyGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public class BWD_Strategy extends SYNC_Strategy {
	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		return library.getSorted_BWD();
	}
	
	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return patternName.endsWith(PatternSuffixes.BWD);
	}
	
	@Override
	public IGreenPattern revokes(GreenPatternFactory greenFactory, String patternName, String ruleName) {
		if(patternName.equals(ConsistencyPattern.getName(ruleName)))
			return greenFactory.createGreenPattern(BWDGreenPattern.class);
		else
			return greenFactory.createGreenPattern(EmptyGreenPattern.class);
	}
}
