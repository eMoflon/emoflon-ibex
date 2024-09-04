package org.emoflon.ibex.tgg.runtime.config;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.strategies.modules.IbexExecutable;

public interface IRegistrationHelper {
	public void registerMetamodels(ResourceSet rs, IbexExecutable executable) throws IOException;
	
	public IbexOptions createIbexOptions();
}
