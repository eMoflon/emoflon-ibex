package org.emoflon.ibex.tgg.operational.patterns;

import java.util.function.Function;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.util.MAUtil;

public abstract class FusedGreenPattern extends IbexGreenPattern {
	protected GreenFusedPatternFactory fusedFactory;
	
	public FusedGreenPattern(GreenFusedPatternFactory factory) {
		super(factory);
		fusedFactory = factory;
	}
	
	protected void createMarkers(String ruleName, IMatch match, Function<String, String> getName) {
		String kernelName = MAUtil.getKernelName(match.patternName());
		String complementName = MAUtil.getComplementName(match.patternName());
		
		fusedFactory.getKernelFactory()
			.create(getName.apply(kernelName))
			.createMarkers(kernelName, match);
		
		fusedFactory.getComplementFactory()
			.create(getName.apply(complementName))
			.createMarkers(complementName, match);
	}
	
	@Override
	public boolean isToBeIgnored(IMatch match) {
		return getNodesMarkedByPattern().isEmpty() && getEdgesMarkedByPattern().isEmpty();
	}
}
