package org.emoflon.ibex.tgg.runtime.config.options;

import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;

public class CSPOptions extends IbexSubOptions {
	
	private RuntimeTGGAttrConstraintProvider constraintProvider;
	private RuntimeTGGAttrConstraintFactory userDefinedConstraints;

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

	public IbexOptions userDefinedConstraints(RuntimeTGGAttrConstraintFactory userDefinedConstraints) {
		this.userDefinedConstraints = userDefinedConstraints;
		return options;
	}

	public RuntimeTGGAttrConstraintFactory userDefinedConstraints() {
		return userDefinedConstraints;
	}


}
