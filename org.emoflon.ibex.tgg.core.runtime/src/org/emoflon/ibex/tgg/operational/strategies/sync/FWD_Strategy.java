package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public class FWD_Strategy extends SYNC_Strategy {
	
	@Override
	public boolean manipulateSrc() {
		return false;
	}

	@Override
	public boolean manipulateTrg() {
		return true;
	}
	
	@Override
	public boolean manipulateCorr() {
		return true;
	}
	
	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		return library.getSorted_FWD();
	}

	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD);
	}
}
