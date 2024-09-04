package org.emoflon.ibex.tgg.runtime.config.options;

import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.util.ConflictResolver;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.util.DefaultConflictResolver;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.pattern.IntegrationPattern;

public class IntegrationOptions extends IbexSubOptions {

	private IntegrationPattern integrationPattern;
	private ConflictResolver conflictSolver;

	public IntegrationOptions(IbexOptions options) {
		super(options);
		
		integrationPattern = new IntegrationPattern();
		conflictSolver = new DefaultConflictResolver();
	}

	public IntegrationPattern pattern() {
		return integrationPattern;
	}

	public IbexOptions pattern(IntegrationPattern integrationPattern) {
		this.integrationPattern = integrationPattern;
		return options;
	}

	public ConflictResolver conflictSolver() {
		return conflictSolver;
	}

	public IbexOptions conflictSolver(ConflictResolver conflictSolver) {
		this.conflictSolver = conflictSolver;
		return options;
	}

}
