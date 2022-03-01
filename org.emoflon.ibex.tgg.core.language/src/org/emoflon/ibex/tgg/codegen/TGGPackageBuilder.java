package org.emoflon.ibex.tgg.codegen;

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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.ibex.tgg.builder.TGGBuildUtil;
import org.emoflon.ibex.tgg.builder.TGGBuilderExtension;
import org.emoflon.ibex.tgg.ide.transformation.EditorTGGtoFlattenedTGG;
import org.moflon.core.plugins.manifest.ManifestFileUpdater;
import org.moflon.core.utilities.ExtensionsUtil;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

public class TGGPackageBuilder implements TGGBuilderExtension{
	
	private IProject project;

	@Override
	public void run(IProject project, Resource schemaResource) {
		this.project = project;
		
		logInfo("Generating Attribute Condition Libraries..");
		generateAttrCondLib();
		
		//Create editor model
		logInfo("Creating editor model..");
		TripleGraphGrammarFile editorModel = null;
		try {
			editorModel = generateEditorModel(schemaResource);
		}catch(RuntimeException e) {
//			logError(e);
			return;
		}
		
		//Flatten editor model
		logInfo("Flattening editor model..");
		TripleGraphGrammarFile flattenedEditorModel = null;
		try {
			flattenedEditorModel = generateFlattenedEditorModel(editorModel);
		} catch(Exception e) {
			logError(e);
			return;
		}
		
		//Store flattened editor model
		ResourceSet rs = editorModel.eResource().getResourceSet();
		IFile tggFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER).getFile(filenameFromProject() + TGGBuildUtil.EDITOR_FLATTENED_MODEL_EXTENSION);
		try {
			saveModelInProject(tggFile, rs, flattenedEditorModel);
		} catch (Exception e) {
			logError(e);
		}
		
		logInfo("Running internal model builder..");
		buildInternalModel(editorModel, flattenedEditorModel);
		logInfo("Running engine builder..");
		generatePMEngineCode(editorModel, flattenedEditorModel);
		
		updateManifest(manifest -> processManifestForProject(manifest));
	}
	
	private TripleGraphGrammarFile generateEditorModel(Resource schemaResource) throws RuntimeException{
		try {
			ResourceSet resourceSet = new XtextResourceSet();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
			
			logInfo(project.getName());
			logInfo(project.getLocation().toOSString());

			if (schemaIsOfExpectedType(schemaResource)) {
				// Combine to form single tgg model
				TripleGraphGrammarFile xtextParsedTGG = //
						(TripleGraphGrammarFile) schemaResource.getContents().get(0);
				
				collectAllRules(xtextParsedTGG, schemaResource.getResourceSet());
				addAttrCondDefLibraryReferencesToSchema(xtextParsedTGG);

				// Persist and return
				IFile editorFile = project.getFolder(TGGBuildUtil.MODEL_FOLDER)//
						.getFile(filenameFromProject() + TGGBuildUtil.EDITOR_MODEL_EXTENSION);

				saveModelInProject(editorFile, resourceSet, xtextParsedTGG);

				// Validate
				validateEditorTGGModel(xtextParsedTGG);
				return xtextParsedTGG;
			} else {
				throw new RuntimeException("Schema is of unexpected type.");
			}
		} catch (CoreException | IOException e) {
			logError(e);
			throw new RuntimeException("Could not generate editor model. Message: "+e.getMessage());
		}
	}
	
	private TripleGraphGrammarFile generateFlattenedEditorModel(TripleGraphGrammarFile editorModel) throws Exception {
		EditorTGGtoFlattenedTGG flattener = new EditorTGGtoFlattenedTGG();
		Optional<TripleGraphGrammarFile> flattenedTGGOp = flattener.flatten(editorModel);
		if(!flattenedTGGOp.isPresent())
			throw new RuntimeException("Could not flatten tgg editor model.");
		
		return flattenedTGGOp.get();
	}
	
	private void buildInternalModel(TripleGraphGrammarFile editorModel, TripleGraphGrammarFile flattenedEditorModel) {
		ISafeRunnable runnable = new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				logError(e.getMessage());
			}

			@Override
			public void run() throws Exception {
				Collection<TGGInternalModelBuilder> extensions = ExtensionsUtil.collectExtensions(TGGInternalModelBuilder.BUILDER_EXTENSON_ID, "class", TGGInternalModelBuilder.class);
				extensions.forEach(builderExt -> {
					builderExt.run(project, editorModel, flattenedEditorModel);
				});
			}
		};
		SafeRunner.run(runnable);
	}
	
	private void generatePMEngineCode(TripleGraphGrammarFile editorModel, TripleGraphGrammarFile flattenedEditorModel) {
		ISafeRunnable runnable = new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				logError(e.getMessage());
			}

			@Override
			public void run() throws Exception {
				Collection<TGGEngineBuilderExtension> extensions = ExtensionsUtil.collectExtensions(TGGEngineBuilderExtension.BUILDER_EXTENSON_ID, "class", TGGEngineBuilderExtension.class);
				extensions.forEach(builderExt -> {
					builderExt.run(project, editorModel, flattenedEditorModel);
				});
			}
		};
		SafeRunner.run(runnable);
	}
	
	private XtextResource loadSchema(ResourceSet resourceSet, IFile schemaFile) throws IOException {
		XtextResource schemaResource = (XtextResource) resourceSet.createResource(URI.createPlatformResourceURI(schemaFile.getFullPath().toString(), false));
		schemaResource.load(null);
		return schemaResource;
	}
	
	private boolean schemaIsOfExpectedType(Resource schemaResource) {
		return schemaResource.getContents().size() == 1 && schemaResource.getContents().get(0) instanceof TripleGraphGrammarFile;
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
	
	private void collectAllRules(TripleGraphGrammarFile xtextParsedTGG, ResourceSet resourceSet) {
		Collection<Resource> resources = resourceSet.getResources();
		for (Resource resource : resources) {
			assert (resource.getContents().size() == 1);
			if (!resource.getContents().isEmpty()) {
				EObject root = resource.getContents().get(0);
				if (root instanceof TripleGraphGrammarFile) {
					TripleGraphGrammarFile f = (TripleGraphGrammarFile) root;
					xtextParsedTGG.getRules().addAll(f.getRules());
				}
			}
		}
	}
	
	private void addAttrCondDefLibraryReferencesToSchema(TripleGraphGrammarFile xtextParsedTGG) {
		EList<AttrCondDef> usedAttrCondDefs = new BasicEList<AttrCondDef>();
		for (Rule rule : xtextParsedTGG.getRules()) {
			for (AttrCond attrCond : rule.getAttrConditions()) {
				if (!usedAttrCondDefs.contains(attrCond.getName()) && !attrCond.getName().isUserDefined()) {
					usedAttrCondDefs.add(attrCond.getName());
				}
			}
		}
		xtextParsedTGG.getSchema().getAttributeCondDefs().addAll(usedAttrCondDefs);
	}
	
	private void validateEditorTGGModel(TripleGraphGrammarFile xtextParsedTGG) throws CoreException {
		noTwoRulesWithTheSameName(xtextParsedTGG);
	}
	
	private void noTwoRulesWithTheSameName(TripleGraphGrammarFile xtextParsedTGG) {
		Stream<String> names = xtextParsedTGG.getRules().stream().map(r -> r.getName());
		names = names.sorted();

		List<String> namesIterator = names.collect(Collectors.toList());
		for (int i = 0; i < namesIterator.size() - 1; i++) {
			String next = namesIterator.get(i);
			String nextNext = namesIterator.get(i + 1);
			if (nextNext.equals(next)) {
				getTGGFileContainingName(next).forEach(f -> {
					try {
						IMarker m = f.createMarker(IMarker.PROBLEM);
						m.setAttribute(IMarker.MESSAGE, "At least one other rule or NAC in your TGG has this name already: " + next);
						m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
						m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		}
	}

	private Collection<IFile> getTGGFileContainingName(String name) {
		try {
			List<IFile> allTGGFiles = new ArrayList<>();
			visitAllFiles(allTGGFiles, project.getFolder("src"), (file, acc) -> {
				if (file.getFileExtension().equals("tgg")) {
					try {
						String contents = FileUtils.readFileToString(file.getLocation().toFile());
						if (contents.split("#rule\\s+" + name).length > 1)
							acc.add(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			return allTGGFiles;
		} catch (Exception e) {
			return Collections.emptyList();
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
			org.moflon.tgg.mosl.defaults.AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);
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
