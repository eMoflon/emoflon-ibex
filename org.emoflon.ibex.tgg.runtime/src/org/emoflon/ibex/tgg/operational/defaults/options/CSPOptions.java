package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

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
