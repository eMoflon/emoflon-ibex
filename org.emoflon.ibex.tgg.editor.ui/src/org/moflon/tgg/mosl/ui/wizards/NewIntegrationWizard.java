package org.moflon.tgg.mosl.ui.wizards;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFolders;
import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.core.utilities.LogUtils;
import org.moflon.ide.core.runtime.MoflonProjectCreator;
import org.moflon.ide.core.runtime.ProjectNatureAndBuilderConfiguratorTask;
import org.moflon.ide.ui.admin.wizards.metamodel.AbstractMoflonProjectInfoPage;
import org.moflon.ide.ui.admin.wizards.metamodel.AbstractMoflonWizard;
import org.moflon.tgg.mosl.builder.MOSLTGGNature;
import org.moflon.tgg.mosl.defaults.AttrCondDefLibraryProvider;
import org.moflon.tgg.mosl.defaults.DefaultFilesHelper;
import org.moflon.util.plugins.MetamodelProperties;
import org.moflon.util.plugins.PluginProducerWorkspaceRunnable;

public class NewIntegrationWizard extends AbstractMoflonWizard {
	protected AbstractMoflonProjectInfoPage projectInfo;

	private static final Logger logger = Logger.getLogger(NewIntegrationWizard.class);

	public static final String NEW_INTEGRATION_PROJECT_WIZARD_ID = "org.moflon.tgg.mosl.newIntegrationProject";

	@Override
	public void addPages() {
		projectInfo = new NewIntegrationProjectInfoPage();
		addPage(projectInfo);
	}

	protected void createProject(IProgressMonitor monitor, IProject project, MetamodelProperties metamodelProperties)
			throws CoreException {
		metamodelProperties.put(MetamodelProperties.TYPE_KEY, MetamodelProperties.INTEGRATION_KEY);
		MoflonProjectCreator createMoflonProject = new MoflonProjectCreator(project, metamodelProperties);
		final SubMonitor subMon = SubMonitor.convert(monitor, "Creating project", 2);
		ResourcesPlugin.getWorkspace().run(createMoflonProject, subMon.newChild(1));

		final ProjectNatureAndBuilderConfiguratorTask natureAndBuilderConfiguratorTask = new ProjectNatureAndBuilderConfiguratorTask(
				project, false);
		final MOSLTGGNature natureAndBuilderConfigurator = new MOSLTGGNature();
		natureAndBuilderConfiguratorTask.updateNatureIDs(natureAndBuilderConfigurator, true);
		natureAndBuilderConfiguratorTask.updateBuildSpecs(natureAndBuilderConfigurator, true);
		WorkspaceTask.executeInCurrentThread(natureAndBuilderConfiguratorTask, IWorkspace.AVOID_UPDATE,
				subMon.newChild(1));
	}

	protected void generateDefaultFiles(final IProgressMonitor monitor, IProject project) throws CoreException {
		String defaultSchema = DefaultFilesHelper.generateDefaultSchema(project.getName());
		IPath pathToSchema = new Path("src/org/moflon/tgg/mosl/Schema.tgg");
		final SubMonitor subMon = SubMonitor.convert(monitor, "Generating default files", 2);
		addAllFoldersAndFile(project, pathToSchema, defaultSchema, subMon.newChild(1));

		addAllFolders(project, "src/org/moflon/tgg/mosl/rules", subMon.newChild(1));

		try {
			AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
	}

	@Override
	protected void doFinish(final IProgressMonitor monitor) throws CoreException {
		try {
			final SubMonitor subMon = SubMonitor.convert(monitor, "Creating eMoflon project", 8);

			String projectName = projectInfo.getProjectName();

			MetamodelProperties properties = new MetamodelProperties();
			properties.setDefaultValues();
			properties.put(MetamodelProperties.PLUGIN_ID_KEY, projectName);
			properties.put(MetamodelProperties.NAME_KEY, projectName);

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			createProject(subMon, project, properties);
			subMon.worked(3);

			generateDefaultFiles(subMon, project);
			subMon.worked(3);

			ResourcesPlugin.getWorkspace().run(new PluginProducerWorkspaceRunnable(project, properties),
					subMon.newChild(1));
			subMon.worked(2);
		} catch (Exception e) {
			LogUtils.error(logger, e);
		}
	}
}
