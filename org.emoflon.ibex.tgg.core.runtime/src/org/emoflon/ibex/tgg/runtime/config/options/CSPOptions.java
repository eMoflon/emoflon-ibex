package org.emoflon.ibex.tgg.runtime.config.options;

import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;

public class CSPOptions extends IbexSubOptions {
	
	private RuntimeTGGAttrConstraintProvider constraintProvider;

	public CSPOptions(IbexOptions options) {
		super(options);
	}
	
	public IbexOptions constraintProvider(RuntimeTGGAttrConstraintProvider constraintProvider) {
		this.constraintProvider = constraintProvider;
		return options;
	}

	public RuntimeTGGAttrConstraintProvider constraintProvider() {
		return constraintProvider;
	}

}
