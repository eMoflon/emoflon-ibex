package org.emoflon.ibex.gt.codegen;

import GTLanguage.GTRuleSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.ibex.gt.codegen.JavaFileGenerator;
import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilder;
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilderExtension;
import org.emoflon.ibex.gt.transformations.AbstractModelTransformation;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.emoflon.ibex.gt.transformations.EditorToGTModelTransformation;
import org.moflon.core.plugins.manifest.ManifestFileUpdater;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.utilities.ClasspathUtil;
import org.moflon.core.utilities.ExtensionsUtil;
import org.moflon.core.utilities.WorkspaceHelper;

/**
 * This GTPackageBuilder transforms the editor files into the internal model and
 * IBeXPatterns and generates code for the API. Each package is considered as an
 * rule module with an own API.
 */
public class GTPackageBuilder implements GTBuilderExtension {
	/**
	 * The name of the source folder containing the generated API.
	 */
	private final static String SOURCE_GEN_FOLDER = "src-gen";

	/**
	 * The project which is built.
	 */
	protected IProject project;

	/**
	 * The path of the package which is built.
	 */
	private IPath path;

	/**
	 * The name of the package.
	 */
	private String packageName;

	@Override
	public void run(final IProject project) {
		this.project = project;
		if (!WorkspaceHelper.isPluginProjectNoThrow(project)) {
			logError("The build for GT projects only works for plugin projects.");
			return;
		}

		updateManifest(manifest -> processManifestForProject(manifest));
		try {
			IFolder folder = ensureFolderExists(project.getFolder(SOURCE_GEN_FOLDER));
			ClasspathUtil.makeSourceFolderIfNecessary(folder);
		} catch (CoreException e) {
			logError("Could not add src-gen as a source folder.");
		}
	}

	@Override
	public void run(final IProject project, final IPath packagePath) {
		if (!WorkspaceHelper.isPluginProjectNoThrow(project)) {
			return;
		}
		this.project = project;
		this.path = packagePath;
		this.packageName = this.path.toString().replace("/", ".");
		IFolder apiPackage = ensureSourceGenPackageExists();

		// Load files into editor models.
		XtextResourceSet resourceSet = new XtextResourceSet();
		Map<IFile, EditorGTFile> editorModels = new HashMap<IFile, EditorGTFile>();
		Set<String> metaModels = new HashSet<String>();
		getFiles().forEach(gtFile -> {
			URI uri = URI.createPlatformResourceURI(gtFile.getFullPath().toString(), true);
			Resource file = resourceSet.getResource(uri, true);
			try {
				EcoreUtil2.resolveLazyCrossReferences(file, () -> false);
			} catch (WrappedException e) {
				log(String.format("Error resolving cross references in file %s.", gtFile.getName()));
			}

			EditorGTFile editorModel = (EditorGTFile) file.getContents().get(0);
			editorModels.put(gtFile, editorModel);
			editorModel.getImports().forEach(i -> metaModels.add(i.getName()));
		});
		EcoreUtil.resolveAll(resourceSet);

		checkEditorModelsForDuplicatePatternNames(editorModels);

		// Transform editor models to rules of the GT API model.
		GTRuleSet gtRuleSet = transformEditorModels(editorModels, new EditorToGTModelTransformation(),
				"%s errors during editor to GT API model transformation");
		saveModelFile(apiPackage.getFile("gt-rules.xmi"), resourceSet, gtRuleSet);

		// Transform editor models to IBeXPatterns.
		IBeXPatternSet ibexPatternSet = transformEditorModels(editorModels, new EditorToIBeXPatternTransformation(),
				"%s errors during editor model to pattern transformation");
		saveModelFile(apiPackage.getFile("ibex-patterns.xmi"), resourceSet, ibexPatternSet);

		// Generate the Java code.
		generateAPI(apiPackage, gtRuleSet, loadMetaModels(metaModels, resourceSet));
		updateManifest(manifest -> processManifestForPackage(manifest));
		log("Finished build.");
	}

	/**
	 * Creates the target package.
	 * 
	 * @return the folder
	 */
	private IFolder ensureSourceGenPackageExists() {
		IFolder folder = ensureFolderExists(project.getFolder(GTPackageBuilder.SOURCE_GEN_FOLDER));
		for (int i = 0; (i < path.segmentCount()); i++) {
			folder = ensureFolderExists(folder.getFolder(path.segment(i)));
		}
		folder = folder.getFolder("api");
		if (folder.exists()) {
			try {
				folder.delete(true, null);
			} catch (CoreException e) {
				log("Could not delete old package.");
			}
		}
		return ensureFolderExists(folder);
	}

	/**
	 * Returns the list of .gt files.
	 * 
	 * @return the list of files
	 */
	private List<IFile> getFiles() {
		IResource[] allFiles = null;
		try {
			allFiles = project.getFolder(GTBuilder.SOURCE_FOLDER).getFolder(path).members();
		} catch (CoreException e) {
			logError("Could not read files.");
		}
		return Arrays.stream(allFiles) //
				.filter(f -> f instanceof IFile).map(f -> (IFile) f) //
				.filter(f -> "gt".equals(f.getFileExtension()) && f.exists()) //
				.collect(Collectors.toList());
	}

	/**
	 * Saves the model in the file.
	 * 
	 * @param file
	 *            the file
	 * @param resourceSet
	 *            the resource set
	 * @param model
	 *            the model to save
	 */
	private void saveModelFile(final IFile file, final ResourceSet resourceSet, final EObject model) {
		String uriString = project.getName() + "/" + file.getProjectRelativePath().toString();
		URI uri = URI.createPlatformResourceURI(uriString, true);
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(model);
		Map<Object, Object> options = ((XMLResource) resource).getDefaultSaveOptions();
		options.put(XMIResource.OPTION_SAVE_ONLY_IF_CHANGED, XMIResource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		options.put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl() {
			@Override
			public URI deresolve(final URI uri) {
				return uri;
			}
		});
		try {
			resource.save(options);
		} catch (IOException e) {
			log("Could not save " + file.getName());
		}
	}

	/**
	 * Loads the EClassifiers from the meta-models into an EClassifiersManager.
	 * 
	 * @param metaModels
	 *            the meta-model URIs
	 * @param resourceSet
	 *            the resource set
	 * @return the mapping between EClassifier names to meta-model names
	 */
	private EClassifiersManager loadMetaModels(final Set<String> metaModels, final ResourceSet resourceSet) {
		Map<String, String> map = MoflonPropertiesContainerHelper.loadIfExists(project) //
				.map(m -> m.getImportMappings()) //
				.map(i -> MoflonPropertiesContainerHelper.mappingsToMap(i)) //
				.orElse(new HashMap<String, String>());

		EClassifiersManager eClassifiersManager = new EClassifiersManager(map);
		metaModels.forEach(uri -> {
			try {
				Resource ecoreFile = resourceSet.getResource(URI.createURI(uri), true);
				ecoreFile.load(null);
				eClassifiersManager.loadMetaModelClasses(ecoreFile);
			} catch (Exception e) {
				log("Could not load meta-model " + uri + ".");
			}
		});
		return eClassifiersManager;
	}

	/**
	 * Checks the editor models for duplicate rule definitions. For duplicates an
	 * error is logged and the rule is removed.
	 * 
	 * @param editorModels
	 *            the editor files and models
	 */
	private void checkEditorModelsForDuplicatePatternNames(final Map<IFile, EditorGTFile> editorModels) {
		Map<String, IFile> ruleNameToFile = new HashMap<String, IFile>();
		for (final IFile gtFile : editorModels.keySet()) {
			EditorGTFile editorModel = editorModels.get(gtFile);
			List<EditorPattern> duplicates = new ArrayList<EditorPattern>();
			editorModel.getPatterns().forEach(rule -> {
				String ruleName = rule.getName();
				if (ruleNameToFile.containsKey(ruleName)) {
					logError(String.format("Rule %s already defined in %s. Ignoring definition in %s.", ruleName,
							ruleNameToFile.get(ruleName).getName(), gtFile.getName())
							+ " Please rename one of the rule definitions.");
					duplicates.add(rule);
				} else {
					ruleNameToFile.put(ruleName, gtFile);
				}
			});
			editorModel.getPatterns().removeAll(duplicates);
		}
	}

	/**
	 * Transforms the given editor models to the target model.
	 * 
	 * @param editorModels
	 *            the editor files and models
	 * @return the target model
	 */
	private <T> T transformEditorModels(final Map<IFile, EditorGTFile> editorModels,
			final AbstractModelTransformation<EditorGTFile, T> transformation, final String errorCountMessageFormat) {
		T targetModel = null;
		for (final IFile gtFile : editorModels.keySet()) {
			EditorGTFile editorModel = editorModels.get(gtFile);
			targetModel = transformation.transform(editorModel);
		}
		if (transformation.hasErrors()) {
			logError(String.format(errorCountMessageFormat, transformation.countErrors()));
			transformation.getErrors().forEach(e -> logError(e));
		}
		return targetModel;
	}

	/**
	 * Generate a Rule and a Match class for every rule and the API class.
	 * 
	 * @param apiPackage
	 *            the package containing the API code
	 * @param gtRuleSet
	 *            the graph transformation rules
	 * @param eClassifiersManager
	 *            the EClassifiers handler
	 */
	private void generateAPI(final IFolder apiPackage, final GTRuleSet gtRuleSet,
			final EClassifiersManager eClassifiersManager) {
		JavaFileGenerator generator = new JavaFileGenerator(getClassNamePrefix(), packageName, eClassifiersManager);
		IFolder matchesPackage = ensureFolderExists(apiPackage.getFolder("matches"));
		IFolder rulesPackage = ensureFolderExists(apiPackage.getFolder("rules"));
		gtRuleSet.getRules().forEach(gtRule -> {
			generator.generateMatchClass(matchesPackage, gtRule);
			generator.generateRuleClass(rulesPackage, gtRule);
		});

		generator.generateAPIClass(apiPackage, gtRuleSet,
				String.format("%s/%s/%s/api/ibex-patterns.xmi", project.getName(), SOURCE_GEN_FOLDER, path.toString()));
		generator.generateAppClass(apiPackage);
		collectEngineExtensions().forEach(e -> generator.generateAppClassForEngine(apiPackage, e));
	}

	/**
	 * Gets the prefix for API/App classes based on path or project name.
	 * 
	 * @return the prefix for the class names
	 */
	private String getClassNamePrefix() {
		String lastSegment = path.lastSegment();
		if (path.segmentCount() == 0 || lastSegment.length() == 0) {
			return project.getName();
		}
		return Character.toUpperCase(lastSegment.charAt(0)) + lastSegment.substring(1);
	}

	/**
	 * Logs the message on the console.
	 */
	private void log(final String message) {
		Logger.getRootLogger().info(getProjectAndPackageName() + ": " + message);
	}

	/**
	 * Logs the error message on the console.
	 */
	private void logError(final String message) {
		Logger.getRootLogger().error(getProjectAndPackageName() + ": " + message);
	}

	/**
	 * Returns the concatenation of the project name and the package.
	 * 
	 * @return the name string
	 */
	private String getProjectAndPackageName() {
		if (packageName == null) {
			return project.getName();
		}
		if (packageName.equals("")) {
			return project.getName() + ", default package";
		}
		return project.getName() + ", package " + packageName;
	}

	/**
	 * Creates the given folder if the folder does not exist yet.
	 */
	private IFolder ensureFolderExists(final IFolder folder) {
		if (!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				log("Could not create folder " + folder.getName() + ".");
			}
		}
		return folder;
	}

	/**
	 * Collects the GTEngine builder extensions.
	 * 
	 * @return the extensions
	 */
	private Collection<GTEngineExtension> collectEngineExtensions() {
		return ExtensionsUtil.collectExtensions(GTEngineExtension.BUILDER_EXTENSON_ID, "class",
				GTEngineExtension.class);
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
		List<String> dependencies = new ArrayList<String>();
		dependencies.addAll(Arrays.asList("org.emoflon.ibex.common", "org.emoflon.ibex.gt"));
		collectEngineExtensions().forEach(engine -> dependencies.addAll(engine.getDependencies()));

		boolean changedBasics = ManifestFileUpdater.setBasicProperties(manifest, project.getName());
		if (changedBasics) {
			log("Initialized MANIFEST.MF.");
		}

		boolean updatedDependencies = ManifestFileUpdater.updateDependencies(manifest, dependencies);
		if (updatedDependencies) {
			log("Updated dependencies");
		}

		return changedBasics || updatedDependencies;
	}

	/**
	 * Updates the exports in the MANIFEST.MF for the API.
	 * 
	 * @param the
	 *            manifest to update
	 * @return whether the manifest was changed
	 */
	private boolean processManifestForPackage(final Manifest manifest) {
		String apiPackageName = (packageName.equals("") ? "" : packageName + ".") + "api";
		boolean updateExports = ManifestFileUpdater.updateExports(manifest,
				Arrays.asList(apiPackageName, apiPackageName + ".matches", apiPackageName + ".rules"));
		if (updateExports) {
			log("Updated exports");
		}
		return updateExports;
	}
}
