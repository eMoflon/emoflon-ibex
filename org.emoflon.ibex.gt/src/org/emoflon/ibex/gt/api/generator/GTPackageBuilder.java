package org.emoflon.ibex.gt.api.generator;

import GTLanguage.GTRuleSet;
import IBeXLanguage.IBeXPatternSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilder;
import org.emoflon.ibex.gt.editor.ui.builder.GTBuilderExtension;
import org.emoflon.ibex.gt.transformations.EditorToInternalGTModelTransformation;
import org.emoflon.ibex.gt.transformations.InternalGTModelToIBeXPatternTransformation;
import org.moflon.core.plugins.manifest.ManifestFileUpdater;
import org.moflon.core.plugins.manifest.PluginManifestConstants;
import org.moflon.core.utilities.ClasspathUtil;
import org.moflon.core.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;

/**
 * This GTPackageBuilder
 * <ul>
 * <li>transforms the editor files into the internal model and IBeXPatterns</li>
 * <li>and generates code for the API.</li>
 * </ul>
 * 
 * Each package is considered as an rule module with an API.
 */
public class GTPackageBuilder implements GTBuilderExtension {
	/**
	 * The name of the source folder containing the generated API.
	 */
	private final static String SOURCE_GEN_FOLDER = "src-gen";

	/**
	 * The file generator used to generate the Java classes.
	 */
	private JavaFileGenerator fileGenerator;

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

	/**
	 * The folder of the package containing the API.
	 */
	private IFolder apiPackage;

	/**
	 * The graph transformation rules.
	 */
	private GTRuleSet gtRuleSet;

	/**
	 * The mapping between EClass/EDataType names to MetaModelNames
	 */
	private HashMap<String, String> eClassifierNameToMetaModelName = new HashMap<String, String>();

	@Override
	public void run(final IProject project, final IPath packagePath) {
		this.project = project;
		this.path = packagePath;
		this.packageName = this.path.toString().replace("/", ".");
		this.ensureSourceGenPackageExists();
		this.generateModels();
		this.generateAPI();
		this.updateManifest();
		this.log("Finished build.");
	}

	/**
	 * Creates the target package.
	 */
	private IFolder ensureSourceGenPackageExists() {
		IFolder folder = this.ensureFolderExists(this.project.getFolder(GTPackageBuilder.SOURCE_GEN_FOLDER));
		try {
			ClasspathUtil.makeSourceFolderIfNecessary(folder);
		} catch (CoreException e) {
			this.logError("Could not add src-gen as a source folder.");
		}

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
		return this.apiPackage = this.ensureFolderExists(folder);
	}

	/**
	 * Parses the models.
	 */
	private void generateModels() {
		IResource[] allFiles = null;
		try {
			allFiles = this.project.getFolder(GTBuilder.SOURCE_FOLDER).getFolder(this.path).members();
		} catch (CoreException e) {
			this.log("Could not read files.");
		}
		List<IFile> gtFiles = Arrays.stream(allFiles) //
				.filter(f -> f instanceof IFile).map(f -> (IFile) f) //
				.filter(f -> "gt".equals(f.getFileExtension()) && f.exists()) //
				.collect(Collectors.toList());

		// Load files into editor models.
		HashMap<IFile, GraphTransformationFile> editorModels = new HashMap<IFile, GraphTransformationFile>();
		XtextResourceSet resourceSet = new XtextResourceSet();
		HashSet<String> metaModels = new HashSet<String>();
		gtFiles.forEach(it -> {
			URI uri = URI.createPlatformResourceURI(it.getFullPath().toString(), true);
			Resource file = resourceSet.getResource(uri, true);
			EcoreUtil2.resolveLazyCrossReferences(file, () -> false);

			GraphTransformationFile editorModel = ((GraphTransformationFile) file.getContents().get(0));
			editorModels.put(it, editorModel);
			editorModel.getImports().forEach(i -> metaModels.add(i.getName()));
		});
		EcoreUtil.resolveAll(resourceSet);

		// Transform editor models to rules of the internal GT model.
		EditorToInternalGTModelTransformation editor2internal = new EditorToInternalGTModelTransformation();
		editorModels.forEach((IFile gtFile, GraphTransformationFile editorModel) -> {
			this.gtRuleSet = editor2internal.transform(editorModel);
			if (editor2internal.hasErrors()) {
				this.logError(String.format("%s errors during editor to internal model transformation of file %s",
						editor2internal.countErrors(), gtFile.getName()));
				editor2internal.getErrors().forEach(e -> this.logError(e));
			}
		});
		this.saveModelFile(this.apiPackage.getFile("gt-rules.xmi"), resourceSet, this.gtRuleSet);

		// Transform rules into IBeXPatterns.
		InternalGTModelToIBeXPatternTransformation internalToPatterns = new InternalGTModelToIBeXPatternTransformation();
		IBeXPatternSet ibexPatternSet = internalToPatterns.transform(this.gtRuleSet);
		if (internalToPatterns.hasErrors()) {
			this.logError(String.format("%s errors during internal model to pattern transformation",
					internalToPatterns.countErrors()));
			internalToPatterns.getErrors().forEach(e -> this.logError(e));
		}
		this.saveModelFile(this.apiPackage.getFile("ibex-patterns.xmi"), resourceSet, ibexPatternSet);

		// Load meta-models
		HashMap<String, String> metaModelPackages = new HashMap<String, String>();
		metaModels.forEach(metaModel -> {
			metaModelPackages.put(metaModel, this.loadMetaModelClasses(metaModel, resourceSet));
		});

		this.fileGenerator = new JavaFileGenerator(this.packageName, this.gtRuleSet,
				this.eClassifierNameToMetaModelName);
		this.fileGenerator.generateREADME(this.apiPackage, gtFiles, metaModels, metaModelPackages, editorModels);
	}

	/**
	 * Saves the model in the file.
	 */
	private void saveModelFile(final IFile file, final ResourceSet rs, final EObject model) {
		String uriString = this.project.getName() + "/" + file.getProjectRelativePath().toString();
		URI uri = URI.createPlatformResourceURI(uriString, true);
		Resource resource = rs.createResource(uri);
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
	 * Loads the EClasses from the given meta-model URI.
	 */
	private String loadMetaModelClasses(final String metaModelUri, final ResourceSet resourceSet) {
		Resource ecoreFile = resourceSet.getResource(URI.createURI(metaModelUri), true);
		try {
			ecoreFile.load(null);
		} catch (IOException e) {
			this.log("Could not load meta-model " + metaModelUri + ".");
		}
		EcoreUtil2.resolveLazyCrossReferences(ecoreFile, () -> false);
		EcoreUtil.resolveAll(resourceSet);

		EObject rootElement = ecoreFile.getContents().get(0);
		if (rootElement instanceof EPackage) {
			EPackage ePackage = (EPackage) rootElement;
			boolean isEcore = "ecore".equals(ePackage.getName());
			String name = isEcore ? "org.eclipse.emf.ecore" : ePackage.getName();
			ePackage.getEClassifiers().stream().filter(c -> !isEcore || c instanceof EClass) //
					.forEach(c -> this.eClassifierNameToMetaModelName.put(c.getName(), name));
			return name;
		}
		return null;
	}

	/**
	 * Generates Java classes of the API.
	 */
	private void generateAPI() {
		IFolder matchesPackage = this.ensureFolderExists(this.apiPackage.getFolder("matches"));
		IFolder rulesPackage = this.ensureFolderExists(this.apiPackage.getFolder("rules"));
		this.gtRuleSet.getRules().stream() //
				.filter(gtRule -> !gtRule.isAbstract()) // ignore abstract rules
				.forEach(gtRule -> {
					this.fileGenerator.generateMatchJavaFile(matchesPackage, gtRule);
					this.fileGenerator.generateRuleJavaFile(rulesPackage, gtRule);
				});

		String patternPath = this.project.getName() + "/src-gen/" + this.path.toString() + "/api/ibex-patterns.xmi";
		this.fileGenerator.generateAPIJavaFile(this.apiPackage, patternPath);
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
	private void updateManifest() {
		try {
			new ManifestFileUpdater().processManifest(this.project, manifest -> {
				return this.processManifest(manifest);
			});
		} catch (CoreException e) {
			this.logError("Failed to update MANIFEST.MF.");
		}
	}

	private boolean processManifest(final Manifest manifest) {
		// The dependencies of the API.
		List<String> dependencies = Arrays.asList("org.emoflon.ibex.common", "org.emoflon.ibex.gt");

		// the packages for this API
		String apiPackageName = (this.packageName.equals("") ? "" : this.packageName + ".") + "api";
		List<String> exports = Arrays.asList(apiPackageName, apiPackageName + ".matches", apiPackageName + ".rules");

		boolean changedBasics = setBasics(manifest, this.project.getName());
		if (changedBasics) {
			this.log("Initialized MANIFEST.MF.");
		}

		boolean updatedDependencies = ManifestFileUpdater.updateDependencies(manifest, dependencies);
		if (updatedDependencies) {
			this.log("Updated dependencies");
		}

		boolean updateExports = updateExports(manifest, exports);
		if (updateExports) {
			this.log("Updated exports");
		}

		return changedBasics || updatedDependencies || updateExports;
	}

	/**
	 * Sets the required properties of the manifest if not set already.
	 * 
	 * @param manifest
	 *            the manifest to update
	 * @param projectName
	 *            the name of the project
	 * @return whether the property was changed
	 */
	private static boolean setBasics(final Manifest manifest, final String projectName) {
		boolean changed = false;
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.MANIFEST_VERSION, "1.0",
				AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_NAME, projectName,
				AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_MANIFEST_VERSION, "2",
				AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION, "0.0.1",
				AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_SYMBOLIC_NAME,
				projectName + ";singleton:=true", AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_ACTIVATION_POLICY,
				"lazy", AttributeUpdatePolicy.KEEP);
		changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_EXECUTION_ENVIRONMENT,
				"JavaSE-1.8", AttributeUpdatePolicy.KEEP);
		return changed;
	}

	/**
	 * Updates the Export-Package property of the manifest.
	 * 
	 * @param manifest
	 *            the manifest to update
	 * @param newExports
	 *            the exports to add
	 * @return whether the property was changed
	 */
	private static boolean updateExports(final Manifest manifest, final List<String> newExports) {
		String exports = (String) manifest.getMainAttributes().get(PluginManifestConstants.EXPORT_PACKAGE);
		List<String> exportsList = ManifestFileUpdater.extractDependencies(exports);

		boolean updated = false;
		for (String newExport : newExports) {
			if (!exportsList.contains(newExport)) {
				exportsList.add(newExport);
				updated = true;
			}
		}

		if (updated) {
			String newExportsString = exportsList.stream().filter(e -> !e.isEmpty()).collect(Collectors.joining(","));
			manifest.getMainAttributes().put(PluginManifestConstants.EXPORT_PACKAGE, newExportsString);
		}
		return updated;
	}
}
