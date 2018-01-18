package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.List;

import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public abstract class SYNC_Strategy {
	public abstract List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library);
	public abstract boolean isPatternRelevantForInterpreter(String patternName);
	public abstract IGreenPattern revokes(IGreenPatternFactory greenFactory, String patternName, String ruleName);
}
