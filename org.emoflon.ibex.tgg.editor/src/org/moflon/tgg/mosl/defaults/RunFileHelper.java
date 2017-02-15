package org.moflon.tgg.mosl.defaults;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

public class RunFileHelper {
	private static final String RUN_FILE_PATH = "src/org/emoflon/ibex/tgg/run/";

	public static void createFiles(IProject project) throws CoreException, IOException {
			String fileName = "MODELGEN_App";
			String path = RUN_FILE_PATH + fileName + ".java";
			String defaultLib = DefaultFilesHelper.generateModelGenFile(project.getName(), fileName);
			IPath pathToLib = new Path(path);
			IFile attrLibFile = project.getFile(pathToLib);
			if (!attrLibFile.exists()) 
				addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
			
			// TODO:  Generate stubs for sync and cc...
	}
}
