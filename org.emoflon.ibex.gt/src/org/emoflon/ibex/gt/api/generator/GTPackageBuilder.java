package org.emoflon.ibex.gt.api.generator;

import GTLanguage.GTLanguageFactory;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;
import IBeXLanguage.IBeXPatternSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

/**
 * This GTPackageBuilder implements
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
	 * The IBeXPatterns.
	 */
	private IBeXPatternSet ibexPatternSet;

	/**
	 * The mapping between EClassNames to MetaModelNames
	 */
	private HashMap<String, String> eClassNameToMetaModelName = new HashMap<String, String>();

	@Override
	public void run(final IProject project, final IPath packagePath) {
		this.project = project;
		this.path = packagePath;
		this.packageName = this.path.toString().replace("/", ".");
		this.ensureSourceGenPackageExists();
		this.generateModels();
		this.generateAPI();
		this.log("Finished build.");
	}

	/**
	 * Creates the target package.
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
			this.log("Could not read files");
		}
		List<IFile> gtFiles = Arrays.stream(allFiles) //
				.filter(f -> f instanceof IFile).map(f -> (IFile) f) //
				.filter(f -> "gt".equals(f.getFileExtension())) //
				.filter(f -> f.exists()) //
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
		HashSet<GTRule> gtRules = new HashSet<GTRule>();
		editorModels.forEach((IFile gtFile, GraphTransformationFile editorModel) -> {
			EditorToInternalGTModelTransformation transformation = new EditorToInternalGTModelTransformation();
			GTRuleSet internalModel = transformation.transform(editorModel);
			gtRules.addAll(internalModel.getRules());
		});

		// Save internal GT model.
		this.gtRuleSet = GTLanguageFactory.eINSTANCE.createGTRuleSet();
		gtRules.stream().sorted((a, b) -> a.getName().compareTo(b.getName())).forEach(gtRule -> {
			gtRuleSet.getRules().add(gtRule);
		});
		this.saveModelFile(this.apiPackage.getFile("gt-rules.xmi"), resourceSet, this.gtRuleSet);

		// Transform rules into IBeXPatterns.
		InternalGTModelToIBeXPatternTransformation transformation = new InternalGTModelToIBeXPatternTransformation();
		this.ibexPatternSet = transformation.transform(this.gtRuleSet);

		// Save IBeXPatterns.
		this.saveModelFile(this.apiPackage.getFile("ibex-patterns.xmi"), resourceSet, this.ibexPatternSet);

		// Load meta-models
		HashMap<String, String> metaModelPackages = new HashMap<String, String>();
		metaModels.forEach(metaModel -> {
			metaModelPackages.put(metaModel, this.loadMetaModelClasses(metaModel, resourceSet));
		});

		this.fileGenerator = new JavaFileGenerator(this.packageName, this.gtRuleSet, this.eClassNameToMetaModelName);
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
			String name = ("ecore".equals(ePackage.getName())) ? "org.eclipse.emf.ecore" : ePackage.getName();
			ePackage.getEClassifiers().stream().filter(c -> c instanceof EClass).map(c -> (EClass) c) //
					.forEach(eClass -> {
						this.eClassNameToMetaModelName.put(eClass.getName(), name);
					});
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
		this.gtRuleSet.getRules().forEach(gtRule -> {
			this.fileGenerator.generateMatchJavaFile(matchesPackage, gtRule);
			this.fileGenerator.generateRuleJavaFile(rulesPackage, gtRule);
		});

		String patternUri = "../" + this.project.getName() + "/src-gen/" + this.path.toString()
				+ "/api/ibex-patterns.xmi";
		this.fileGenerator.generateAPIJavaFile(this.apiPackage, patternUri);
	}

	/**
	 * Logs the message on the console.
	 */
	private void log(final String message) {
		Logger.getRootLogger().info(this.project.getName() + " - package " + this.packageName + ": " + message);
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
}
