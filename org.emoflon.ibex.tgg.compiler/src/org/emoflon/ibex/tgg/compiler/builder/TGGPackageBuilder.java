package org.emoflon.ibex.tgg.compiler.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.emoflon.ibex.tgg.compiler.TGGLToTGGModelTransformer;
import org.emoflon.ibex.tgg.compiler.defaults.AttrCondDefLibraryProvider;
import org.emoflon.ibex.tgg.compiler.defaults.EditorTGGtoInternalTGG;
import org.emoflon.ibex.tgg.compiler.defaults.TGGBuildUtil;
import org.emoflon.ibex.tgg.compiler.defaults.TGGProject;
import org.emoflon.ibex.tgg.tggl.generator.TGGBuilderExtension;
import org.emoflon.ibex.tgg.tggl.tGGL.EditorFile;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.moflon.core.plugins.manifest.ManifestFileUpdater;
import org.moflon.core.utilities.ExtensionsUtil;
import org.moflon.core.utilities.MoflonUtil;

public class TGGPackageBuilder implements TGGBuilderExtension {
	
	private IProject project;

	@Override
	public void run(IProject project, Resource schemaResource) {
		this.project = project;
		
		logInfo("Generating Attribute Condition Libraries..");
		generateAttrCondLib();
		
		//Create editor model
		logInfo("Creating editor model..");
		TGGModel tggModel = null;
		try {
			tggModel = generateInternalModel(schemaResource);
		}catch(RuntimeException e) {
//			logError(e);
			return;
		}
		
		//Store editor model
		ResourceSet rs = tggModel.eResource().getResourceSet();
		IFile tggFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER).getFile(filenameFromProject() + TGGBuildUtil.EDITOR_MODEL_EXTENSION);
		try {
			saveModelInProject(tggFile, rs, tggModel);
		} catch (Exception e) {
			logError(e);
		}
		
		new AttrCondDefLibraryProvider().generateAttrCondLibsAndStubs(tggModel, project);
		
		logInfo("Running engine builder..");
		generatePMEngineCode(tggModel);
		
		updateManifest(manifest -> processManifestForProject(manifest));
	}
	
	private TGGModel generateInternalModel(Resource schemaResource) throws RuntimeException {
		if(schemaResource.getContents().get(0) instanceof EditorFile editorFile) {
			var transformer = new TGGLToTGGModelTransformer(editorFile, project);
			return transformer.transform();
		}
		throw new RuntimeException("Could not find and transform any schema");
	}
	
	private void generatePMEngineCode(TGGModel editorModel) {
		ISafeRunnable runnable = new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				logError(e.getMessage());
			}

			@Override
			public void run() throws Exception {
				Collection<TGGEngineBuilderExtension> extensions = ExtensionsUtil.collectExtensions(TGGEngineBuilderExtension.BUILDER_EXTENSON_ID, "class", TGGEngineBuilderExtension.class);
				extensions.forEach(builderExt -> {
					builderExt.run(project, editorModel);
				});
			}
		};
		SafeRunner.run(runnable);
	}
	
	private <ACC> void visitAllFiles(ACC accumulator, IFolder root, BiConsumer<IFile, ACC> action) throws CoreException, IOException {
		for (IResource iResource : root.members()) {
			if (iResource instanceof IFile) {
				action.accept((IFile) iResource, accumulator);
			} else if (iResource instanceof IFolder) {
				visitAllFiles(accumulator, IFolder.class.cast(iResource), action);
			}
		}
	}
	
	private void loadRules(IFile file, ResourceSet resourceSet) {
		if (file.getName().endsWith(TGGBuildUtil.TGG_FILE_EXTENSION)) {
			resourceSet.getResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true), true);
		}
	}
	
	public static void saveModelInProject(IFile file, ResourceSet rs, EObject model) throws IOException {
		URI uri = URI.createPlatformResourceURI(file.getProject().getName()//
				+ "/" + file.getProjectRelativePath().toString(), true);
		Resource resource = rs.createResource(uri);
		resource.getContents().add(model);
		Map<Object, Object> options = ((XMLResource) resource).getDefaultSaveOptions();
		options.put(XMIResource.OPTION_SAVE_ONLY_IF_CHANGED, XMIResource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		options.put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl() {
			@Override
			public URI deresolve(URI uri) {
				return uri;
			}
		});
		resource.save(options);
	}
	
	
	
	private void generateAttrCondLib() {
		try {
			org.emoflon.ibex.tgg.editor.defaults.AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);
		} catch (CoreException | IOException e) {
			logError(e);
		}
	}
	
	private String filenameFromProject() {
		return MoflonUtil.lastCapitalizedSegmentOf(project.getName());
	}
	
	private void logInfo(final String message) {
		Logger.getRootLogger().info(this.getClass().getSimpleName()+ "(TGG-Project -> " + project.getName() + " ): " + message);
	}
	
	/**
	 * Logs the error message on the console.
	 */
	private void logError(final Exception exception) {
		Logger.getRootLogger().error(this.getClass().getSimpleName()+ "(TGG-Project -> " + project.getName() + " ): " + exception.getMessage());
	}
	
	/**
	 * Logs the error message on the console.
	 */
	private void logError(final String message) {
		Logger.getRootLogger().error(this.getClass().getSimpleName()+ "(TGG-Project -> " + project.getName() + " ): " + message);
	}
	
	/**
	 * Updates the project's manifest file.
	 */
	private void updateManifest(final Function<Manifest, Boolean> updateFunction) {
		try {
			new ManifestFileUpdater().processManifest(project, manifest -> updateFunction.apply(manifest));
		} catch (CoreException e) {
			logError("Failed to update MANIFEST.MF.");
		}
	}
	
	/**
	 * Updates the manifest such that it contains at least basic properties and the
	 * dependencies for the API.
	 * 
	 * @param manifest
	 *            the manifest to update
	 * @return whether the manifest was changed
	 */
	private boolean processManifestForProject(final Manifest manifest) {
		boolean changedBasics = ManifestFileUpdater.setBasicProperties(manifest, project.getName());
		return changedBasics;
	}
}
