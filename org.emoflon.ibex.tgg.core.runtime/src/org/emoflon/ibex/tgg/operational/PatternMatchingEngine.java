package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.tgg.operational.util.IbexOptions;

public interface PatternMatchingEngine {
	public void registerInternalMetamodels();
	public void initialise(ResourceSet rs, OperationalStrategy operationalStrategy, IbexOptions options);
	public void initialiseAgain(ResourceSet rs);
	public ResourceSet createAndPrepareResourceSet(String workspacePath);
	public void updateMatches();
	public void terminate();
}
