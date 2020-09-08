package org.emoflon.ibex.gt.codegen;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;

public interface GTEngineBuilderExtension {
	
	static final String BUILDER_EXTENSON_ID = "org.emoflon.ibex.gt.GTEngineBuilderExtension";
	
	/**
	 * Builds the package in the given project. This will be triggered for each
	 * package containing .gt files.
	 * 
	 * @param project
	 *            the project to build
	 * @param packagePath
	 *            the path to the package
	 */
	public void run(IProject project, IPath packagePath, final IBeXModel ibexModel);
}
