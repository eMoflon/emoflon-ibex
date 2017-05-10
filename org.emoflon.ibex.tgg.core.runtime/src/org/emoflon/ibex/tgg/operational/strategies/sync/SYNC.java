package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public abstract class SYNC extends OperationalStrategy {

	private SYNC_Strategy strategy;
	
	public SYNC(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	@Override
	protected boolean manipulateSrc() {
		return strategy.manipulateSrc();
	}

	@Override
	protected boolean manipulateTrg() {
		return strategy.manipulateTrg();
	}

	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		return strategy.getConstraints(library);
	}

	@Override
	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith(PatternSuffixes.BWD) 
			|| patternName.endsWith(PatternSuffixes.FWD)
			|| patternName.endsWith(PatternSuffixes.WHOLE);
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
}
