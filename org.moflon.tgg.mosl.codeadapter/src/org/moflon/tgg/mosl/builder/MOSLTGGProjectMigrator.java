package org.moflon.tgg.mosl.builder;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.gervarro.eclipse.workspace.autosetup.ProjectConfigurator;
import org.gervarro.eclipse.workspace.util.ProjectStateObserver;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.ide.core.NatureMigrator;
import org.moflon.ide.core.runtime.ProjectNatureAndBuilderConfiguratorTask;

public class MOSLTGGProjectMigrator extends ProjectStateObserver implements ProjectConfigurator {
	
   @Override
	protected void handleResourceChange(final IResource resource, final boolean added) {
		if (added && resource.getType() == IResource.PROJECT) {
			try {
				final ProjectNatureAndBuilderConfiguratorTask task =
						new ProjectNatureAndBuilderConfiguratorTask((IProject) resource, false);
				final NatureMigrator natureMigrator = new NatureMigrator();
				task.updateNatureIDs(natureMigrator, added);
				task.updateBuildSpecs(natureMigrator, added);
				task.updateBuildSpecs(this, added);
				WorkspaceTask.executeInCurrentThread(task, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());
			} catch (CoreException e) {
				// Do nothing
			}
		}
	}

	@Override
	public String[] updateNatureIDs(final String[] natureIDs, boolean added) throws CoreException {
		return natureIDs;
	}

	@Override
	public ICommand[] updateBuildSpecs(IProjectDescription description, ICommand[] buildSpecs, boolean added)
			throws CoreException {
		final String[] natureIDs = description.getNatureIds();
		for (int i = 0; i < natureIDs.length; i++) {
			if (MOSLTGGNature.NATURE_ID.equals(natureIDs[i])) {
				buildSpecs = new MOSLTGGNature().updateBuildSpecs(description, buildSpecs, added);
			}
		}
		return buildSpecs;
	}
}
