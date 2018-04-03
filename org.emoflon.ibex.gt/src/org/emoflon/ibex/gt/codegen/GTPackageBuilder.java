package org.emoflon.ibex.gt.codegen;

import GTLanguage.GTRuleSet;
import IBeXLanguage.IBeXPatternSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;
import org.emoflon.ibex.gt.editor.gT.Rule;
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilder;
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilderExtension;
import org.emoflon.ibex.gt.transformations.EditorToInternalGTModelTransformation;
import org.emoflon.ibex.gt.transformations.InternalGTModelToIBeXPatternTransformation;
import org.moflon.core.plugins.manifest.ManifestFileUpdater;
import org.moflon.core.utilities.ClasspathUtil;
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
			this.log("The build for GT projects only works for plugin projects.");
			return;
		}

		this.updateManifest(manifest -> this.processManifestForProject(manifest));
		try {
			IFolder folder = this.ensureFolderExists(this.project.getFolder(GTPackageBuilder.SOURCE_GEN_FOLDER));
			ClasspathUtil.makeSourceFolderIfNecessary(folder);
		} catch (CoreException e) {
			this.logError("Could not add src-gen as a source folder.");
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
		IFolder apiPackage = this.ensureSourceGenPackageExists();

		// Load files into editor models.
		XtextResourceSet resourceSet = new XtextResourceSet();
		Map<IFile, GraphTransformationFile> editorModels = new HashMap<IFile, GraphTransformationFile>();
		Set<String> metaModels = new HashSet<String>();
		this.getFiles().forEach(gtFile -> {
			URI uri = URI.createPlatformResourceURI(gtFile.getFullPath().toString(), true);
			Resource file = resourceSet.getResource(uri, true);
			EcoreUtil2.resolveLazyCrossReferences(file, () -> false);

			GraphTransformationFile editorModel = ((GraphTransformationFile) file.getContents().get(0));
			editorModels.put(gtFile, editorModel);
			editorModel.getImports().forEach(i -> metaModels.add(i.getName()));
		});
		EcoreUtil.resolveAll(resourceSet);

		this.checkEditorModelsForDuplicateRuleNames(editorModels);

		// Transform editor models to rules of the internal GT model.
		GTRuleSet gtRuleSet = this.transformEditorModelsToInternalModel(editorModels);
		this.saveModelFile(apiPackage.getFile("gt-rules.xmi"), resourceSet, gtRuleSet);

		// Transform rules into IBeXPatterns.
		IBeXPatternSet ibexPatternSet = this.transformToIBeXPatterns(gtRuleSet);
		this.saveModelFile(apiPackage.getFile("ibex-patterns.xmi"), resourceSet, ibexPatternSet);

		// Generate the Java code.
		this.generateAPI(apiPackage, gtRuleSet, this.loadMetaModels(metaModels, resourceSet));
		this.updateManifest(manifest -> this.processManifestForPackage(manifest));
		this.log("Finished build.");
	}

	/**
	 * Creates the target package.
	 * 
	 * @return the folder
	 */
	private IFolder ensureSourceGenPackageExists() {
		IFolder folder = this.ensureFolderExists(this.project.getFolder(GTPackageBuilder.SOURCE_GEN_FOLDER));
		for (int i = 0; (i < this.path.segmentCount()); i++) {
			folder = this.ensureFolderExists(folder.getFolder(this.path.segment(i)));
		}
		folder = folder.getFolder("api");
		if (folder.exists()) {
			try {
				folder.delete(true, null);
			} catch (CoreException e) {
				this.log("Could not delete old package.");
			}
		}
		return this.ensureFolderExists(folder);
	}

	/**
	 * Returns the list of .gt files.
	 * 
	 * @return the list of files
	 */
	private List<IFile> getFiles() {
		IResource[] allFiles = null;
		try {
			allFiles = this.project.getFolder(GTBuilder.SOURCE_FOLDER).getFolder(this.path).members();
		} catch (CoreException e) {
			this.logError("Could not read files.");
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
		String uriString = this.project.getName() + "/" + file.getProjectRelativePath().toString();
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
			this.log("Could not save " + file.getName());
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
		EClassifiersManager eClassifiersManager = new EClassifiersManager();
		metaModels.forEach(uri -> {
			Resource ecoreFile = resourceSet.getResource(URI.createURI(uri), true);
			try {
				ecoreFile.load(null);
				eClassifiersManager.loadMetaModelClasses(ecoreFile);
			} catch (IOException e) {
				this.log("Could not load meta-model " + uri + ".");
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
	private void checkEditorModelsForDuplicateRuleNames(final Map<IFile, GraphTransformationFile> editorModels) {
		Map<String, IFile> ruleNameToFile = new HashMap<String, IFile>();
		for (final IFile gtFile : editorModels.keySet()) {
			GraphTransformationFile editorModel = editorModels.get(gtFile);
			List<Rule> duplicates = new ArrayList<Rule>();
			editorModel.getRules().forEach(rule -> {
				String ruleName = rule.getName();
				if (ruleNameToFile.containsKey(ruleName)) {
					this.logError(String.format("Rule %s already defined in %s. Ignoring definition in %s.", ruleName,
							ruleNameToFile.get(ruleName).getName(), gtFile.getName())
							+ " Please rename one of the rule definitions.");
					duplicates.add(rule);
				} else {
					ruleNameToFile.put(ruleName, gtFile);
				}
			});
			editorModel.getRules().removeAll(duplicates);
		}
	}

	/**
	 * Transforms the editor models into a GTRuleSet and IBeXPatterns.
	 * 
	 * @param editorModels
	 *            the editor files and models
	 * @return the graph transformation rules
	 */
	private GTRuleSet transformEditorModelsToInternalModel(final Map<IFile, GraphTransformationFile> editorModels) {
		GTRuleSet gtRuleSet = null;
		EditorToInternalGTModelTransformation editor2internal = new EditorToInternalGTModelTransformation();
		for (final IFile gtFile : editorModels.keySet()) {
			gtRuleSet = editor2internal.transform(editorModels.get(gtFile));
			if (editor2internal.hasErrors()) {
				this.logError(String.format("%s errors during editor to internal model transformation of file %s",
						editor2internal.countErrors(), gtFile.getName()));
				editor2internal.getErrors().forEach(e -> this.logError(e));
			}
		}
		return gtRuleSet;
	}

	/**
	 * Transforms the given rules into IBeXPatterns.
	 * 
	 * @param gtRuleSet
	 *            the graph transformation rules
	 * @return the IBeXPatterns
	 */
	private IBeXPatternSet transformToIBeXPatterns(final GTRuleSet gtRuleSet) {
		InternalGTModelToIBeXPatternTransformation internalToPatterns = new InternalGTModelToIBeXPatternTransformation();
		IBeXPatternSet ibexPatternSet = internalToPatterns.transform(gtRuleSet);
		if (internalToPatterns.hasErrors()) {
			this.logError(String.format("%s errors during internal model to pattern transformation",
					internalToPatterns.countErrors()));
			internalToPatterns.getErrors().forEach(e -> this.logError(e));
		}
		return ibexPatternSet;
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
		JavaFileGenerator generator = new JavaFileGenerator(getClassNamePrefix(), packageName, gtRuleSet,
				eClassifiersManager);
		IFolder matchesPackage = this.ensureFolderExists(apiPackage.getFolder("matches"));
		IFolder rulesPackage = this.ensureFolderExists(apiPackage.getFolder("rules"));
		gtRuleSet.getRules().stream() //
				.filter(gtRule -> !gtRule.isAbstract()) // ignore abstract rules
				.forEach(gtRule -> {
					generator.generateMatchClass(matchesPackage, gtRule);
					generator.generateRuleClass(rulesPackage, gtRule);
				});

		String patternPath = project.getName() + "/" + SOURCE_GEN_FOLDER + "/" + path.toString()
				+ "/api/ibex-patterns.xmi";
		generator.generateAPIClass(apiPackage, patternPath);
		generator.generateAppClass(apiPackage);
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
		Logger.getRootLogger().info(this.getProjectAndPackageName() + ": " + message);
	}

	/**
	 * Logs the error message on the console.
	 */
	private void logError(final String message) {
		Logger.getRootLogger().error(this.getProjectAndPackageName() + ": " + message);
	}

	/**
	 * Returns the concatenation of the project name and the package.
	 * 
	 * @return the name string
	 */
	private String getProjectAndPackageName() {
		if (this.packageName == null) {
			return this.project.getName();
		}
		if (this.packageName.equals("")) {
			return this.project.getName() + ", default package";
		}
		return this.project.getName() + ", package " + this.packageName;
	}

	/**
	 * Creates the given folder if the folder does not exist yet.
	 */
	private IFolder ensureFolderExists(final IFolder folder) {
		if (!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				this.log("Could not create folder " + folder.getName() + ".");
			}
		}
		return folder;
	}

	/**
	 * Updates the project's manifest file.
	 */
	private void updateManifest(final Function<Manifest, Boolean> updateFunction) {
		try {
			new ManifestFileUpdater().processManifest(this.project, manifest -> updateFunction.apply(manifest));
		} catch (CoreException e) {
			this.logError("Failed to update MANIFEST.MF.");
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
		List<String> dependencies = Arrays.asList("org.emoflon.ibex.common", "org.emoflon.ibex.gt");

		boolean changedBasics = ExtendedManifestFileUpdater.setBasics(manifest, this.project.getName());
		if (changedBasics) {
			this.log("Initialized MANIFEST.MF.");
		}

		boolean updatedDependencies = ManifestFileUpdater.updateDependencies(manifest, dependencies);
		if (updatedDependencies) {
			this.log("Updated dependencies");
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
		String apiPackageName = (this.packageName.equals("") ? "" : this.packageName + ".") + "api";
		boolean updateExports = ExtendedManifestFileUpdater.updateExports(manifest,
				Arrays.asList(apiPackageName, apiPackageName + ".matches", apiPackageName + ".rules"));
		if (updateExports) {
			this.log("Updated exports");
		}
		return updateExports;
	}
}
