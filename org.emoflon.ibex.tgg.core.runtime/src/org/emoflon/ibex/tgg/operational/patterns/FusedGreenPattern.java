package org.emoflon.ibex.tgg.operational.patterns;

import java.util.function.Function;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.util.MultiAmalgamationUtil;

public abstract class FusedGreenPattern extends IbexGreenPattern {
	protected GreenFusedPatternFactory fusedFactory;
	
	public FusedGreenPattern(GreenFusedPatternFactory factory) {
		super(factory);
		fusedFactory = factory;
	}
	
	protected void createMarkers(String ruleName, IMatch match, Function<String, String> getName) {
		String kernelName = MultiAmalgamationUtil.getKernelName(match.patternName());
		String complementName = MultiAmalgamationUtil.getComplementName(match.patternName());
		
		fusedFactory.getKernelFactory()
			.create(getName.apply(kernelName))
			.createMarkers(kernelName, match);
		
		fusedFactory.getComplementFactory()
			.create(getName.apply(complementName))
			.createMarkers(complementName, match);
	}
}
