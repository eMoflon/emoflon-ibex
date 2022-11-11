package org.emoflon.ibex.tgg.util.config;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;

public interface IRegistrationHelper {
	public void registerMetamodels(ResourceSet rs, IbexExecutable executable) throws IOException;
	
	public IbexOptions createIbexOptions();
}
