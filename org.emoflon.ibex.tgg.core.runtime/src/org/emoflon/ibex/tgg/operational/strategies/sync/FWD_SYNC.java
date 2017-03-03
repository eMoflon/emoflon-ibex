package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.List;

import org.emoflon.ibex.tgg.operational.OperationalStrategy;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public abstract class FWD_SYNC extends OperationalStrategy {

	public FWD_SYNC(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	@Override
	protected boolean manipulateSrc() {
		return false;
	}

	@Override
	protected boolean manipulateTrg() {
		return true;
	}

	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		return library.getSorted_FWD();
	}

	@Override
	public boolean isPatternRelevant(String patternName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void saveModels() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void loadModels() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void wrapUp() {
		
	}
}
