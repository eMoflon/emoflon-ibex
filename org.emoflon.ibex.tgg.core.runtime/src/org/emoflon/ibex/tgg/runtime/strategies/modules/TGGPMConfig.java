package org.emoflon.ibex.tgg.runtime.strategies.modules;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;

public class TGGPMConfig {

	public final IbexExecutable executable;
	public final IbexOptions options;
	public final Registry registry;
	
	public TGGPMConfig(IbexExecutable executable, IbexOptions options, Registry registry) {
		this.executable = executable;
		this.options = options;
		this.registry = registry;
	}
}
