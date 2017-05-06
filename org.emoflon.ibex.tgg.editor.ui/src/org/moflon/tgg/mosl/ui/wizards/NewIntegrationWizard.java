package org.moflon.tgg.mosl.ui.wizards;

import static org.moflon.util.WorkspaceHelper.addAllFolders;
import static org.moflon.util.WorkspaceHelper.addAllFoldersAndFile;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ui.INewWizard;
import org.emoflon.ibex.tgg.ui.ide.admin.IbexTGGNature;
import org.moflon.tgg.mosl.defaults.AttrCondDefLibraryProvider;
import org.moflon.tgg.mosl.defaults.DefaultFilesHelper;
import org.moflon.util.LogUtils;
import org.moflon.util.WorkspaceHelper;

public class NewIntegrationWizard extends AbstractMoflonWizard implements INewWizard {
	protected AbstractMoflonProjectInfoPage projectInfo;

	private static final Logger logger = Logger.getLogger(NewIntegrationWizard.class);

	public static final String NEW_INTEGRATION_PROJECT_WIZARD_ID = "org.moflon.tgg.mosl.newIntegrationProject";

	@Override
	public void addPages() {
		projectInfo = new NewIntegrationProjectInfoPage();
		addPage(projectInfo);
	}

	protected void createProject(IProgressMonitor monitor, IProject project) throws CoreException {
		final SubMonitor subMon = SubMonitor.convert(monitor, "Creating " + project.getName(), 3);
		
		// Create project
		project.create(subMon.split(1));
		project.open(subMon.split(1));
		
		// Add Ibex TGG Nature
		WorkspaceHelper.addNature(project, IbexTGGNature.IBEX_TGG_NATURE_ID, subMon.split(1)); 
	}

	protected void generateDefaultFiles(final IProgressMonitor monitor, IProject project) throws CoreException {
		try {
			final SubMonitor subMon = SubMonitor.convert(monitor, "Generating default files", 3);
			String defaultSchema = DefaultFilesHelper.generateDefaultSchema(project.getName());
			IPath pathToSchema = new Path(IbexTGGNature.SCHEMA_FILE);
			addAllFoldersAndFile(project, pathToSchema, defaultSchema, subMon.split(1));
			addAllFolders(project, "src/org/emoflon/ibex/tgg/rules", subMon.split(1));
			addAllFolders(project, "model", subMon.split(1));
			AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
	}

	@Override
	protected void doFinish(final IProgressMonitor monitor) throws CoreException {
		try {
			final SubMonitor subMon = SubMonitor.convert(monitor, "Creating eMoflon project", 6);

			String projectName = projectInfo.getProjectName();
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			createProject(subMon.split(3), project);
			generateDefaultFiles(subMon.split(3), project);
		} catch (Exception e) {
			LogUtils.error(logger, e);
		}
	}
}
