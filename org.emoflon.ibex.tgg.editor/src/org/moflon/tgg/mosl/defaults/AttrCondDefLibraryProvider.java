package org.moflon.tgg.mosl.defaults;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

public class AttrCondDefLibraryProvider {
	private static final String ATTR_COND_DEF_LIBRARY_PATH = "src/org/emoflon/ibex/tgg/csp/lib/AttrCondDefLibrary.tgg";
	private static final String USER_ATTR_COND_DEF_LIBRARY_PATH = "src/org/emoflon/ibex/tgg/csp/lib/UserAttrCondDefLibrary.tgg";

	public static void syncAttrCondDefLibrary(IProject project) throws CoreException, IOException {
		String path = ATTR_COND_DEF_LIBRARY_PATH;
		String defaultLib = DefaultFilesHelper.generateDefaultAttrCondDefLibrary();
		IPath pathToLib = new Path(path);
		IFile attrLibFile = project.getFile(pathToLib);
		if (attrLibFile.exists()) {
			File file = new File(attrLibFile.getLocation().toString());
			String contents = FileUtils.readFileToString(file);
			if (!contents.equals(defaultLib)) {
				addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
			}
		} else {
			addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
		}
	}

	public static void userAttrCondDefLibrary(IProject project) throws CoreException, IOException {
		String path = USER_ATTR_COND_DEF_LIBRARY_PATH;
		String userLib = DefaultFilesHelper.generateEmptyUserAttrCondDefLibrary();
		IPath pathToLib = new Path(path);
		IFile userAttrLibFile = project.getFile(pathToLib);
		if (!userAttrLibFile.exists()) {
			addAllFoldersAndFile(project, pathToLib, userLib, new NullProgressMonitor());
		}
	}
}