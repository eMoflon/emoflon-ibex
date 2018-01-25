package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public abstract class SYNC extends OperationalStrategy {

	protected SYNC_Strategy strategy;
	
	public SYNC(IbexOptions options) throws IOException {
		super(options);
	}
	
	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.BWD) 
			|| patternName.endsWith(PatternSuffixes.FWD)
			|| patternName.endsWith(PatternSuffixes.CONSISTENCY);
	}
	
	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return strategy.isPatternRelevantForInterpreter(patternName);
	}

	@Override
	protected void wrapUp() {
		
	}
	
	@Override
	public void saveModels() throws IOException {
		s.save(null);
	 	t.save(null);
	 	c.save(null);
	 	p.save(null);
	}
	
	@Override
	public void loadModels() throws IOException {
		s = loadResource(projectPath + "/instances/src.xmi");
		t = loadResource(projectPath + "/instances/trg.xmi");
		c = loadResource(projectPath + "/instances/corr.xmi");
		p = loadResource(projectPath + "/instances/protocol.xmi");
		
		EcoreUtil.resolveAll(rs);
	}
	
	public void forward() throws IOException {
		strategy = new FWD_Strategy();
		run();
	}
	
	public void backward() throws IOException {
		strategy = new BWD_Strategy();
		run();
	}
	
	@Override
	public IGreenPattern revokes(IMatch match) {
		String ruleName = getRuleApplicationNode(match).getName();
		return strategy.revokes(getGreenFactory(ruleName), match.patternName(), ruleName);
	}
}
