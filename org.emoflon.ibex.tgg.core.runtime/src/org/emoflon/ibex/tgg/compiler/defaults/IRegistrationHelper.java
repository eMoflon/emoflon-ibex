package org.emoflon.ibex.tgg.compiler.defaults;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public interface IRegistrationHelper {
	public void registerMetamodels(ResourceSet rs, OperationalStrategy strategy) throws IOException;
	
	public IbexOptions createIbexOptions();
}
