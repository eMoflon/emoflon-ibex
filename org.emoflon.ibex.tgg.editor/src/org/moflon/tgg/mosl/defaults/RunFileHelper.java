package org.moflon.tgg.mosl.defaults;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

import java.io.IOException;
import java.util.function.BiFunction;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

public class RunFileHelper {
	private static final String RUN_FILE_PATH = "src/org/emoflon/ibex/tgg/run/";
	private IProject project;
	
	public RunFileHelper(IProject project) {
		this.project = project;
	}
	
	public void createFiles() throws CoreException, IOException {
			createDefaultFile("MODELGEN_App", DefaultFilesHelper::generateModelGenFile);
			createDefaultFile("FWD_SYNC_App", DefaultFilesHelper::generateFwdSyncAppFile);
	}

	private void createDefaultFile(String fileName, BiFunction<String, String, String> generator) throws CoreException {
		String path = RUN_FILE_PATH + fileName + ".java";
		IPath pathToFile = new Path(path);
		IFile file = project.getFile(pathToFile);
		if (!file.exists()){ 
			String defaultContent = generator.apply(project.getName(), fileName);
			addAllFoldersAndFile(project, pathToFile, defaultContent, new NullProgressMonitor());
		}
	}
}
