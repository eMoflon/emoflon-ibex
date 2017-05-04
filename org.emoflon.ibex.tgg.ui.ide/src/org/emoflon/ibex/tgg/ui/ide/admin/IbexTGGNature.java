package org.emoflon.ibex.tgg.ui.ide.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.PlatformUI;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.util.plugins.BuildPropertiesFileBuilder;
import org.moflon.util.plugins.manifest.ManifestFileUpdater;
import org.moflon.util.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;
import org.moflon.util.plugins.manifest.PluginManifestConstants;

public class IbexTGGNature implements IProjectNature {
	public static final String IBEX_TGG_NATURE_ID  = "org.emoflon.ibex.tgg.ui.ide.nature";
	public static final String IBEX_TGG_BUILDER_ID = "org.emoflon.ibex.tgg.ui.ide.builder";
	public static final String XTEXT_NATURE_ID     = "org.eclipse.xtext.ui.shared.xtextNature";
	public static final String PLUGIN_NATURE_ID = "org.eclipse.pde.PluginNature";
	public static final String SCHEMA_FILE = "src/org/emoflon/ibex/tgg/Schema.tgg";
	
	private IProject project;

	private static Logger logger = Logger.getLogger(IbexTGGNature.class);
	
	@Override
	public void configure() throws CoreException {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					performSetUpRoutines();
					addIBexTGGBuilder(); 
				} catch (CoreException | IOException e) {
					LogUtils.error(logger, e);
				}
			}

		});
	}

	private void addIBexTGGBuilder() throws CoreException {
		IProjectDescription projectDescription = project.getDescription();
		ICommand[] buildSpec = projectDescription.getBuildSpec();
		ICommand command = projectDescription.newCommand();
		command.setBuilderName(IBEX_TGG_BUILDER_ID);
		Collection<ICommand> list = new ArrayList<>();
		list.add(command);
		list.addAll(Arrays.asList(buildSpec));
		projectDescription.setBuildSpec(list.toArray(new ICommand[list.size()]));
		project.setDescription(projectDescription, new NullProgressMonitor());
	}

	private void performSetUpRoutines() throws CoreException, IOException {
		WorkspaceHelper.setUpAsJavaProject(project, new NullProgressMonitor());
		setUpAsPluginProject();
		setUpAsXtextProject();
		setUpAsIbexProject();
	}
	
	private void setUpAsIbexProject() throws CoreException, IOException {
		new ManifestFileUpdater().processManifest(project, manifest -> {
			boolean changed = false;
			changed |= ManifestFileUpdater.updateDependencies(manifest, Arrays.asList(
					// Ibex deps
					"org.emoflon.ibex.tgg.core.runtime",
					"org.emoflon.ibex.tgg.core.language",
					
					// Moflon deps
					"org.moflon.core.utilities",
					
					// EMF deps
					"org.eclipse.emf.ecore.xmi",
					
					// Democles deps
					" org.gervarro.democles.common",
					"org.gervarro.democles.specification.emf",
					"org.gervarro.democles.interpreter",
					"org.gervarro.democles.codegen.emf",
					"org.gervarro.democles.emf",
					"org.gervarro.democles.interpreter.emf",
					"org.gervarro.democles.interpreter.incremental",
					"org.gervarro.democles.interpreter.incremental.emf",
					"org.gervarro.democles.interpreter.lightning",
					"org.gervarro.democles.notification.emf",
					"org.gervarro.democles.plan",
					"org.gervarro.democles.plan.emf",
					"org.gervarro.democles.plan.incremental.leaf",
					"org.gervarro.util",
					"org.gervarro.notification",
					"org.gervarro.plan.dynprog"
					));
			return changed;
		});
	}

	private void setUpAsXtextProject() throws CoreException {
		WorkspaceHelper.addNature(project, XTEXT_NATURE_ID, new NullProgressMonitor());
	}

	private void setUpAsPluginProject() throws CoreException, IOException {
		WorkspaceHelper.addNature(project, PLUGIN_NATURE_ID, new NullProgressMonitor());
		setUpBuildProperties();
        setUpManifestFile();
        addContainerToBuildPath(project, "org.eclipse.pde.core.requiredPlugins");	
    }
	
	/**
	 * Adds the given container to the build path of the given project if it
	 * contains no entry with the same name, yet.
	 */
	public static void addContainerToBuildPath(final IProject project, final String container) {
		addContainerToBuildPath(JavaCore.create(project), container);
	}

	/**
	 * Adds the given container to the build path of the given java project.
	 */
	private static void addContainerToBuildPath(final IJavaProject iJavaProject, final String container) {
		try {
			// Get current entries on the classpath
			Collection<IClasspathEntry> classpathEntries = new ArrayList<>(
					Arrays.asList(iJavaProject.getRawClasspath()));

			addContainerToBuildPath(classpathEntries, container);

			setBuildPath(iJavaProject, classpathEntries);
		} catch (JavaModelException e) {
			LogUtils.error(logger, e, "Unable to set classpath variable");
		}
	}

	/**
	 * Adds the given container to the list of build path entries (if not
	 * included, yet)
	 */
	private static void addContainerToBuildPath(final Collection<IClasspathEntry> classpathEntries,
			final String container) {
		IClasspathEntry entry = JavaCore.newContainerEntry(new Path(container));
		for (IClasspathEntry iClasspathEntry : classpathEntries) {
			if (iClasspathEntry.getPath().equals(entry.getPath())) {
				// No need to add variable - already on classpath
				return;
			}
		}

		classpathEntries.add(entry);
	}

	private static void setBuildPath(final IJavaProject javaProject, final Collection<IClasspathEntry> entries,
			final IProgressMonitor monitor) throws JavaModelException {
		final SubMonitor subMon = SubMonitor.convert(monitor, "Set build path", 1);
		// Create new buildpath
		IClasspathEntry[] newEntries = new IClasspathEntry[entries.size()];
		entries.toArray(newEntries);

		// Set new classpath with added entries
		javaProject.setRawClasspath(newEntries, subMon.newChild(1));
	}

	private static void setBuildPath(final IJavaProject javaProject, final Collection<IClasspathEntry> entries)
			throws JavaModelException {
		setBuildPath(javaProject, entries, new NullProgressMonitor());
	}

	private void setUpBuildProperties() throws CoreException {
		logger.debug("Adding build.properties");
        new BuildPropertiesFileBuilder().createBuildProperties(project, new NullProgressMonitor());
	}

	private void setUpManifestFile() throws CoreException, IOException {
		logger.debug("Adding MANIFEST.MF");
		new ManifestFileUpdater().processManifest(project, manifest -> {
			boolean changed = false;
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.MANIFEST_VERSION, "1.0", AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_MANIFEST_VERSION, "2", AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_NAME, project.getName(), AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_SYMBOLIC_NAME, project.getName() + ";singleton:=true", AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION, "1.0", AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VENDOR, "eMoflon IBeX", AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_ACTIVATION_POLICY, "lazy", AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_EXECUTION_ENVIRONMENT, "JavaSE-1.8", AttributeUpdatePolicy.KEEP);
			return changed;
		});
	}

	@Override
	public void deconfigure() throws CoreException {
		// Not required
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
}
