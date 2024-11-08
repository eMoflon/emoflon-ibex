package org.emoflon.ibex.tgg.runtime.config.options;

import java.util.Collection;
import java.util.HashSet;

import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.runtime.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;

public class CSPOptions extends IbexSubOptions {
	
	private RuntimeTGGAttrConstraintProvider constraintProvider;
	private Collection<RuntimeTGGAttrConstraintFactory> factoryCache = new HashSet<>();
	
	public CSPOptions(IbexOptions options) {
		super(options);
	}
	
	public IbexOptions constraintProvider(RuntimeTGGAttrConstraintProvider constraintProvider) {
		this.constraintProvider = constraintProvider;
		if(constraintProvider != null) {
			registerConstraintFactories(factoryCache);
			factoryCache.clear();
		}
		return options;
	}

	public RuntimeTGGAttrConstraintProvider constraintProvider() {
		return constraintProvider;
	}

	public void registerConstraintFactories(Collection<RuntimeTGGAttrConstraintFactory> factories) {
		factories.forEach(this::registerConstraintFactory);
	}
	
	public void registerConstraintFactory(RuntimeTGGAttrConstraintFactory factory) {
		if(constraintProvider != null) {
			constraintProvider.registerFactory(factory);
		}
		else {
			factoryCache.add(factory);
		}
	}
}
