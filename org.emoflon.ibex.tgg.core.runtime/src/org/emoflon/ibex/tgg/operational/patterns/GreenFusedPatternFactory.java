package org.emoflon.ibex.tgg.operational.patterns;

import java.lang.reflect.InvocationTargetException;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.util.MultiAmalgamationUtil;

import language.csp.TGGAttributeConstraintLibrary;

public class GreenFusedPatternFactory extends GreenPatternFactory{
	
	OperationalStrategy strategy;
	protected IGreenPatternFactory kernelFactory;
	protected IGreenPatternFactory complementFactory;
	String fusedName;
	
	public GreenFusedPatternFactory(String fusedName, IbexOptions options, OperationalStrategy strategy) {
		super(options, strategy);
		this.fusedName = fusedName;
		this.strategy = strategy;
		String kernelName = MultiAmalgamationUtil.getKernelName(fusedName);
		String complementName = MultiAmalgamationUtil.getComplementName(fusedName);
		kernelFactory = strategy.getGreenFactory(kernelName);
		complementFactory = strategy.getGreenFactory(complementName);
		
		fullInBookkeepingData();
	}

	private void fullInBookkeepingData() {
		greenSrcNodesInRule = kernelFactory.getGreenSrcNodesInRule();
		greenSrcNodesInRule.addAll(complementFactory.getGreenSrcNodesInRule());
		
		greenTrgNodesInRule = kernelFactory.getGreenTrgNodesInRule();
		greenTrgNodesInRule.addAll(complementFactory.getGreenTrgNodesInRule());
		
		greenCorrNodesInRule = kernelFactory.getGreenCorrNodesInRule();
		greenCorrNodesInRule.addAll(complementFactory.getGreenCorrNodesInRule());
		
		greenSrcEdgesInRule = kernelFactory.getGreenSrcEdgesInRule();
		greenSrcEdgesInRule.addAll(complementFactory.getGreenSrcEdgesInRule());
		
		greenTrgEdgesInRule = kernelFactory.getGreenTrgEdgesInRule();
		greenTrgEdgesInRule.addAll(complementFactory.getGreenTrgEdgesInRule());
		
		blackSrcNodesInRule = kernelFactory.getBlackSrcNodesInRule();
		blackSrcNodesInRule.addAll(complementFactory.getBlackSrcNodesInRule());
		
		blackTrgNodesInRule = kernelFactory.getBlackTrgNodesInRule();
		blackTrgNodesInRule.addAll(complementFactory.getBlackTrgNodesInRule());
		
		blackCorrNodesInRule = kernelFactory.getBlackCorrNodesInRule();
		blackCorrNodesInRule.addAll(complementFactory.getBlackCorrNodesInRule());
		
		blackSrcEdgesInRule = kernelFactory.getBlackSrcEdgesInRule();
		blackSrcEdgesInRule.addAll(complementFactory.getBlackSrcEdgesInRule());
		
		blackTrgEdgesInRule = kernelFactory.getBlackTrgEdgesInRule();
		blackTrgEdgesInRule.addAll(complementFactory.getBlackTrgEdgesInRule());
		
	}

	@Override
	public IGreenPattern create(String patternName) {
		if(isBWDFusedPattern(patternName))
			return createGreenPattern(BWDFusedGreenPattern.class);
		
		if(isFWDFusedPattern(patternName))
			return createGreenPattern(FWDFusedGreenPattern.class);
		
		return createGreenPattern(EmptyGreenPattern.class);
	}
	
	private boolean isFWDFusedPattern(String patternName) {
		return MultiAmalgamationUtil.isFusedPatternMatch(patternName) && patternName.endsWith(PatternSuffixes.FWD);
	}

	private boolean isBWDFusedPattern(String patternName) {
		return MultiAmalgamationUtil.isFusedPatternMatch(patternName) && patternName.endsWith(PatternSuffixes.BWD);
	}
	
	@Override
	public TGGAttributeConstraintLibrary getRuleCSPConstraintLibrary() {
		return kernelFactory.getRuleCSPConstraintLibrary(); 
	}
	
	public IGreenPatternFactory getKernelFactory() {
		return kernelFactory;
	}
	
	public IGreenPatternFactory getComplementFactory() {
		return complementFactory;
	}
	
	public IGreenPattern createGreenPattern(Class<? extends IGreenPattern> c) {
		return createPattern(c.getName(), () -> {
			try {
				return c.getConstructor(GreenFusedPatternFactory.class).newInstance(this);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
		});
	}
}