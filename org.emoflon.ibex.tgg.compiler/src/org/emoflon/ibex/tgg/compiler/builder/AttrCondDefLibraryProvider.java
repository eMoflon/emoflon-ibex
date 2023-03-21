package org.emoflon.ibex.tgg.compiler.builder;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.emoflon.ibex.tgg.compiler.codegen.DefaultFilesGenerator;
import org.emoflon.ibex.tgg.compiler.codegen.DefaultFilesHelper;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class AttrCondDefLibraryProvider {
	private static final Logger logger = Logger.getLogger(AttrCondDefLibraryProvider.class);
	
	public static final String DEFAULT_ATTR_COND_LIB_NAME = "DefaultAttributeConditionLibrary";

	private static final String USER_ATTR_PATH = "src/org/emoflon/ibex/tgg/operational/csp/constraints/";
	private static final String USER_ATTR_COND_DEF_FACTORY_PATH = USER_ATTR_PATH + "factories/";
	private static final String USER_ATTR_COND_DEF_FACTORY_NAME = "/UserDefinedRuntimeTGGAttrConstraintFactory.java";
	private static final String USER_ATTR_CONDS_PATH = USER_ATTR_PATH + "custom/";
	private static final String ATTR_COND_DEF_LIBRARY_PATH = "src/org/emoflon/ibex/tgg/csp/lib/" + DEFAULT_ATTR_COND_LIB_NAME + ".tggl";
	public static final String ATTR_COND_DEF_PREDEFINED_PACKAGE = "org.emoflon.ibex.tgg.operational.csp.constraints";
	public static final String ATTR_COND_DEF_USERDEFINED_PACKAGE = "org.emoflon.ibex.tgg.operational.csp.constraints.custom";

	
	public static void userAttrCondDefFactory(IProject project, Collection<String> userDefConstraints) throws CoreException, IOException {
		String path = USER_ATTR_COND_DEF_FACTORY_PATH + MoflonUtil.lastCapitalizedSegmentOf(project.getName()).toLowerCase() + USER_ATTR_COND_DEF_FACTORY_NAME ;
		String userLib = DefaultFilesGenerator.generateUserRuntimeAttrCondFactory(userDefConstraints, MoflonUtil.lastCapitalizedSegmentOf(project.getName()));
		IPath pathToLib = new Path(path);
		WorkspaceHelper.addAllFoldersAndFile(project, pathToLib, userLib, new NullProgressMonitor());
	}

	public static void userAttrCondDefStubs(IProject project, Collection<TGGAttributeConstraintDefinition> userDefConstraints) throws CoreException, IOException {
		for(TGGAttributeConstraintDefinition tacd : userDefConstraints) {
			String path = USER_ATTR_CONDS_PATH + MoflonUtil.lastCapitalizedSegmentOf(project.getName()).toLowerCase() + "/" + UserAttrCondHelper.getFileName(tacd.getName()) + ".java";
			String userLib = DefaultFilesGenerator.generateUserAttrCondDefStub(tacd, MoflonUtil.lastCapitalizedSegmentOf(project.getName()));
			IPath pathToLib = new Path(path);
			IFile userAttrLibFile = project.getFile(pathToLib);
			if (!userAttrLibFile.exists()) {
				WorkspaceHelper.addAllFoldersAndFile(project, pathToLib, userLib, new NullProgressMonitor());
			}
		}
	}
	
	
	public void generateAttrCondLibsAndStubs(TGGModel model, IProject project) {
		// TODO larsF, adrianM hier muss geschaut werden dass keine Stubs für die Standardlib generiert werden
		Collection<TGGAttributeConstraintDefinition> userAttrCondDefs = model
				.getAttributeConstraintDefinitionLibraries() //
				.stream() //
				.flatMap(lib -> lib.getTggAttributeConstraintDefinitions().stream())
//				.filter(ac -> ac.isUserDefined()) //
				.collect(Collectors.toList()); //
		
		Collection<String> userAttrCondNames = userAttrCondDefs.stream()
				.map(udc -> udc.getName())
				.collect(Collectors.toList());
		try {
			AttrCondDefLibraryProvider.userAttrCondDefFactory(project, userAttrCondNames);
			AttrCondDefLibraryProvider.userAttrCondDefStubs(project, userAttrCondDefs);
		} catch (CoreException | IOException e) {
			LogUtils.error(logger, e);
		}
	}
	

	public static void syncAttrCondDefLibrary(IProject project) throws CoreException, IOException {
		String path = ATTR_COND_DEF_LIBRARY_PATH;
		String defaultLib = DefaultFilesHelper.generateDefaultAttrCondDefLibrary();
		IPath pathToLib = new Path(path);
		IFile attrLibFile = project.getFile(pathToLib);
		if (attrLibFile.exists()) {
			File file = new File(attrLibFile.getLocation().toString());
			String contents = FileUtils.readFileToString(file, (String) null);
			if (!contents.equals(defaultLib)) {
				WorkspaceHelper.addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
			}
		} else {
			WorkspaceHelper.addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
		}
	}
}