package org.moflon.tgg.mosl.defaults;

import static org.moflon.util.WorkspaceHelper.addAllFoldersAndFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.moflon.tgg.mosl.tgg.AttrCond;

import language.csp.definition.TGGAttributeConstraintDefinition;

public class AttrCondDefLibraryProvider {
	private static final String ATTR_COND_DEF_LIBRARY_PATH = "src/org/emoflon/ibex/tgg/csp/lib/AttrCondDefLibrary.tgg";
	private static final String USER_ATTR_COND_DEF_LIBRARY_PATH = "src/org/emoflon/ibex/tgg/csp/lib/UserAttrCondDefLibrary.tgg";
	private static final String USER_ATTR_COND_DEF_FACTORY_PATH = "src/org/emoflon/ibex/tgg/operational/csp/constraints/factories/UserDefinedRuntimeTGGAttrConstraintFactory.java";
	private static final String USER_ATTR_CONDS_PATH = "src/org/emoflon/ibex/tgg/operational/csp/constraints/custom/";

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

	public static void userAttrCondDefFactory(IProject project, Collection<String> userDefConstraints) throws CoreException, IOException {
		String path = USER_ATTR_COND_DEF_FACTORY_PATH;
		String userLib = DefaultFilesHelper.generateUserRuntimeAttrCondFactory(userDefConstraints);
		IPath pathToLib = new Path(path);
		addAllFoldersAndFile(project, pathToLib, userLib, new NullProgressMonitor());
	}

	public static void userAttrCondDefStubs(IProject project, Collection<TGGAttributeConstraintDefinition> userDefConstraints) throws CoreException, IOException {
		for(TGGAttributeConstraintDefinition tacd : userDefConstraints) {
			String path = USER_ATTR_CONDS_PATH + UserAttrCondHelper.getFileName(tacd.getName()) + ".java";
			String userLib = DefaultFilesHelper.generateUserAttrCondDefStub(tacd);
			IPath pathToLib = new Path(path);
			IFile userAttrLibFile = project.getFile(pathToLib);
			if (!userAttrLibFile.exists()) {
				addAllFoldersAndFile(project, pathToLib, userLib, new NullProgressMonitor());
			}
		}
	}
}