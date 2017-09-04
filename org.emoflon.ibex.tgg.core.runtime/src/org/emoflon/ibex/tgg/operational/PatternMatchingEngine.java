package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.util.IbexOptions;

public interface PatternMatchingEngine {
	public ResourceSet createAndPrepareResourceSet(String workspacePath);
	public void registerInternalMetamodels();
	public void initialise(Registry registry, OperationalStrategy operationalStrategy, IbexOptions options);
	
	public void monitor(ResourceSet rs);
	public void updateMatches();

	public void terminate();
}
