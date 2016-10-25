package org.moflon.tgg.mosl.builder;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.gervarro.eclipse.workspace.util.RelevantElementCollector;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.ProjectDependencyAnalyzer;
import org.moflon.ide.core.runtime.builders.AbstractVisitorBuilder;
import org.moflon.ide.core.runtime.builders.MetamodelBuilder;

public class MoslTGGBuilder extends AbstractVisitorBuilder {
	public static final Logger logger = Logger.getLogger(MoslTGGBuilder.class);
	public static final String BUILDER_ID = "org.moflon.tgg.mosl.codeadapter.mosltggbuilder";

	public MoslTGGBuilder() {
		super(new AntPatternCondition(new String[] { "src/org/moflon/tgg/mosl/*.tgg", "src/org/moflon/tgg/mosl/**/*.tgg" }));
	}

	@Override
	protected AntPatternCondition getTriggerCondition(IProject project) {
		try {
			if (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) ||
					project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID)) {
				return new AntPatternCondition(new String[] { "gen/**" });
			}
		} catch (final CoreException e) {
			// Do nothing
		}
		return new AntPatternCondition(new String[0]);
	}
	
	protected void postprocess(final RelevantElementCollector buildVisitor, int kind,
			final Map<String, String> args, final IProgressMonitor monitor) {
		kind = correctBuildTrigger(kind);
		if (getCommand().isBuilding(kind)) {
			final IFolder moslFolder = getProject().getFolder(new Path("src/org/moflon/tgg/mosl"));
			if (kind == INCREMENTAL_BUILD || kind == AUTO_BUILD) {
				if (!buildVisitor.getRelevantDeltas().isEmpty()) {
					processResource(moslFolder, kind, args, monitor);
				}
			} else if (kind == FULL_BUILD) {
				if (!buildVisitor.getRelevantResources().isEmpty()) {
					processResource(moslFolder, kind, args, monitor);
				}
			}
		}		
		if (buildVisitor.getRelevantDeltas().isEmpty() && (kind == INCREMENTAL_BUILD || kind == AUTO_BUILD)) {
			final SubMonitor subMonitor = SubMonitor.convert(monitor, triggerProjects.size());
			try {
				for (final IProject project : triggerProjects) {
					final RelevantElementCollector relevantElementCollector = new RelevantElementCollector(project, getTriggerCondition(project)) {
						public boolean handleResourceDelta(final IResourceDelta delta) {
							final int deltaKind = delta.getKind();
							if (deltaKind == IResourceDelta.ADDED || deltaKind == IResourceDelta.CHANGED) {
								super.handleResourceDelta(delta);
							}
							return false;
						}
					};
					final IResourceDelta delta = getDelta(project);
					if (delta != null) {

						delta.accept(relevantElementCollector, IResource.NONE);
						if (!relevantElementCollector.getRelevantDeltas().isEmpty()) {
							// Perform a full build if a triggering project changed
							build(FULL_BUILD, args, subMonitor.newChild(1));
							return;
						} else {
							subMonitor.worked(1);
						}
					} else {
						subMonitor.worked(1);
					}
				}
			} catch (final CoreException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	@Override
	protected void processResource(IResource resource, int kind, Map<String, String> args, IProgressMonitor monitor) {
		try {
			final Resource ecoreResource = new MOSLTGGConversionHelper().generateTGGModel(resource);
			removeXtextMarkers();
			if (ecoreResource != null && ecoreResource.getContents().get(0) instanceof EPackage) {
				final ProjectDependencyAnalyzer projectDependencyAnalyzer =
						new ProjectDependencyAnalyzer(this, getProject(), getProject(),
								(EPackage) ecoreResource.getContents().get(0));
				final Set<IProject> interestingProjects =
						new TreeSet<IProject>(MetamodelBuilder.PROJECT_COMPARATOR);
				for (final IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
					interestingProjects.add(project);
				}
				projectDependencyAnalyzer.setInterestingProjects(interestingProjects);
				
				// Project dependency analysis should be carried out on a locked workspace in a separate thread
				// to avoid inconsistency in the build configuration caused by the analysis process
				final WorkspaceJob job = new WorkspaceJob(projectDependencyAnalyzer.getTaskName()) {
					@Override
					public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
						return projectDependencyAnalyzer.run(monitor);
					}
				};
				job.setRule(ResourcesPlugin.getWorkspace().getRoot());
				job.schedule();
			} else {
				processProblemStatus(new Status(IStatus.ERROR, CoreActivator.getModuleID(),
						"Unable to construct the correspondence metamodel from the Xtext specification", null),
				      resource);
			}
		} catch (CoreException e) {
			LogUtils.error(logger, e, "Unable to update created projects: " + e.getMessage());
		}

	}

   // Hack related to Issue #781 (see https://github.com/eMoflon/emoflon-issues/issues/781) (rkluge)
	private final void removeXtextMarkers() {
		try {
			getProject().deleteMarkers(org.eclipse.xtext.ui.MarkerTypes.FAST_VALIDATION, true, IResource.DEPTH_INFINITE);
		} catch (final CoreException e) {
         LogUtils.error(logger, e);
		}
	}
}
