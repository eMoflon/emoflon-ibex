package org.emoflon.ibex.gt.build;

import org.eclipse.core.resources.IProject;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;

public interface IBeXGTDependingBuilderExtension {
	static final String BUILDER_EXTENSON_ID = "org.emoflon.ibex.gt.GTDependingBuilderExtension";

	/**
	 * Builds the package in the given project. This will be triggered for each
	 * package containing .gt files.
	 * 
	 * @param project     the project to build
	 * @param packagePath the path to the package
	 */
	public void run(IProject project, final GTModel ibexModel);
}
