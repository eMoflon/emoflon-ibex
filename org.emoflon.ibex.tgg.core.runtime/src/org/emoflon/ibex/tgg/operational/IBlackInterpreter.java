package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public interface IBlackInterpreter {
	public ResourceSet createAndPrepareResourceSet(String workspacePath);
	public void registerInternalMetamodels();
	public void initialise(Registry registry, IMatchObserver matchObserver, IbexOptions options);
	
	public void monitor(ResourceSet rs);
	public void updateMatches();

	public void terminate();
}
