package org.moflon.tgg.mosl.builder;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.ide.core.runtime.ProjectNatureAndBuilderConfiguratorTask;

public class AddRemoveMOSLTGGNatureHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection) {
			for (Iterator<?> it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				if (project != null) {
					try {
						final boolean hasNature = project.hasNature(MOSLTGGNature.NATURE_ID);
						final ProjectNatureAndBuilderConfiguratorTask projectConfiguratorTask =
								new ProjectNatureAndBuilderConfiguratorTask(project, false);
						final MOSLTGGNature moslTGGProjectConfigurator = new MOSLTGGNature();
						projectConfiguratorTask.updateNatureIDs(moslTGGProjectConfigurator, !hasNature);
						projectConfiguratorTask.updateBuildSpecs(moslTGGProjectConfigurator, !hasNature);
						WorkspaceTask.executeInCurrentThread(projectConfiguratorTask, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());
					} catch (CoreException e) {
						throw new ExecutionException("Failed to toggle nature", e);
					}
				}
			}
		}
		return null;
	}
}