package org.emoflon.ibex.tgg.ui.ide.admin;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.PlatformUI;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.util.plugins.BuildPropertiesFileBuilder;
import org.moflon.util.plugins.manifest.ManifestFileUpdater;
import org.moflon.util.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;
import org.moflon.util.plugins.manifest.PluginManifestConstants;

public class IbexTGGNature implements IProjectNature {
	public static final String IBEX_TGG_NATURE_ID = "org.emoflon.ibex.tgg.ui.ide.nature";
	public static final String XTEXT_BUILDER_ID = "org.eclipse.xtext.ui.shared.xtextBuilder";
	public static final String XTEXT_NATURE_ID = "org.eclipse.xtext.ui.shared.xtextNature";
	public static final String VIATRA_NATURE_ID = "org.eclipse.viatra.query.projectnature";
	
	private IProject project;

	private Logger logger = Logger.getLogger(IbexTGGNature.class);
	
	@Override
	public void configure() throws CoreException {
		WorkspaceHelper.setUpAsJavaProject(project, new NullProgressMonitor());

		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					setUpAsPluginProject();
					setUpAsXtextProject();
					setUpAsViatraProject();
				} catch (CoreException e) {
					LogUtils.error(logger, e);
				}
			}
		});
	}

	private void setUpAsXtextProject() throws CoreException {
		WorkspaceHelper.addNature(project, XTEXT_NATURE_ID, new NullProgressMonitor());
	}
	
	private void setUpAsViatraProject() throws CoreException {
		WorkspaceHelper.addNature(project, VIATRA_NATURE_ID, new NullProgressMonitor());
	}

	private void setUpAsPluginProject() throws CoreException {
        setUpBuildProperties();
        setUpManifestFile();
	}

	private void setUpBuildProperties() throws CoreException {
		logger.debug("Adding build.properties");
        new BuildPropertiesFileBuilder().createBuildProperties(project, new NullProgressMonitor());
	}

	private void setUpManifestFile() throws CoreException {
		try
        {
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
        } catch (IOException e)
        {
           LogUtils.error(logger, e);
        }
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
