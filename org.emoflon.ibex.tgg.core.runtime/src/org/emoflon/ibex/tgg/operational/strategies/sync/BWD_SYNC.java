package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.List;

import org.emoflon.ibex.tgg.operational.OperationalStrategy;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public abstract class BWD_SYNC extends OperationalStrategy {

	public BWD_SYNC(String projectName, String workspacePath, boolean debug) throws IOException {
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
		return library.getSorted_BWD();
	}
}
