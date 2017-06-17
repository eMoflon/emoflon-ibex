package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.ResourceSet;

import language.TGG;

public interface PatternMatchingEngine {
	public void registerInternalMetamodels();
	public void initialise(ResourceSet rs, OperationalStrategy operationalStrategy, TGG tgg, TGG flattenedTgg, String projectPath, boolean debug);
	public ResourceSet createAndPrepareResourceSet(String workspacePath);
	public void updateMatches();
	public void terminate();
}
