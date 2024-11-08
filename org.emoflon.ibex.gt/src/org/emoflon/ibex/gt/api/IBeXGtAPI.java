package org.emoflon.ibex.gt.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.gt.engine.IBeXGTEngine;
import org.emoflon.ibex.gt.engine.IBeXGTPatternFactory;
import org.emoflon.ibex.gt.engine.IBeXGTPatternMatcher;
import org.emoflon.ibex.gt.engine.IBeXGTRuleFactory;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

/**
 * This abstract API is the super class for all concrete APIs generated for a
 * set of model resources.
 * 
 * The concrete implementation provide methods to perform queries and rule
 * applications on the given resource set which must not be empty.
 * 
 * All elements created during rule applications are added to the default
 * resource. The default resource can be explicitly set in the constructor,
 * otherwise the first resource in the resource set will be used.
 * 
 * Per default, all rule applications use the single pushout approach. This
 * behavior can be changed for the whole API or for single rules.
 */
public abstract class IBeXGtAPI<PM extends IBeXGTPatternMatcher<?>, PF extends IBeXGTPatternFactory, RF extends IBeXGTRuleFactory> {

	protected String workspacePath;
	protected String projectPath;
	protected String ibexModelPath;
	protected String projectName;

	protected ResourceSet model;
	protected GTModel ibexModel;

	/**
	 * The engine to use for queries and transformations.
	 */
	protected IBeXGTEngine<PM> gtEngine;

	protected PF patternFactory;
	protected RF ruleFactory;

	/**
	 * Default constructor for instantiating a new IBeXGtAPI object with the default
	 * IBeX GT pattern model taken from the default path determined during compile
	 * time of the project.
	 */
	public IBeXGtAPI() {
		init(getDefaultIBeXModelPath());
	}

	/**
	 * Constructor for instantiating a new IBeXGtAPI object with a passed IBeX GT
	 * pattern model (XMI) path as an argument. This constructor overrides the
	 * default hard-coded pattern path.
	 * 
	 * @param patternPath IBeX GT pattern model (XMI) path to load.
	 */
	public IBeXGtAPI(final String patternPath) {
		init(patternPath);
	}

	/**
	 * Initializes the API object with the given IBeX GT pattern model (XMI) path.
	 * 
	 * @param patternPath IBeX GT pattern model (XMI) path.
	 */
	private void init(final String patternPath) {
		workspacePath = getWorkspacePath();
		projectPath = getProjectPath();
		ibexModelPath = patternPath;
		projectName = getProjectName();
		try {
			ibexModel = loadGTModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract String getWorkspacePath();

	public abstract String getProjectPath();

	/**
	 * Returns the actual IBeX GT pattern model (XMI) path. In default mode, this is
	 * the hard-coded value determined during compile time of the project or a
	 * specific custom path given to the constructor.
	 * 
	 * @return Actual IBeX GT pattern model (XMI) path used by this API.
	 */
	public String getIBeXModelPath() {
		return this.ibexModelPath;
	}

	/**
	 * Returns the default IBeX model path, i.e., the default path found during
	 * compile time of the project. (This value will be hard-coded during compile
	 * time.)
	 * 
	 * @return Default IBeX model path.
	 */
	public abstract String getDefaultIBeXModelPath();

	public abstract String getProjectName();

	protected abstract PM createPatternMatcher();

	protected abstract PF createPatternFactory();

	protected abstract RF createRuleFactory();

	protected abstract void initializeRules();

	protected abstract void initializePatterns();

	/**
	 * Add the meta-models to the package registry.
	 */
	protected abstract void registerMetamodels(final ResourceSet rs);

	protected GTModel loadGTModel() throws Exception {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getPackageRegistry().put(IBeXCoreModelPackage.eINSTANCE.getNsURI(), IBeXCoreModelPackage.eINSTANCE);
		rs.getPackageRegistry().put(IBeXGTModelPackage.eINSTANCE.getNsURI(), IBeXGTModelPackage.eINSTANCE);
		registerMetamodels(rs);
		File f = new File(ibexModelPath);
		if (f.exists()) {
			Resource r = rs.getResource(URI.createFileURI(f.getCanonicalPath()), true);
			return (GTModel) r.getContents().get(0);
		}
		throw new IOException("IBeX-GT model path could not be resolved: " + ibexModelPath);
	}

	public Resource addModel(final String modelPath) throws Exception {
		prepareModelResourceSet();
		File modelFile = new File(modelPath);
		URI modelUri = null;
		if (modelFile.exists() && modelFile.isFile() && modelFile.isAbsolute()) {
			modelUri = URI.createFileURI(modelFile.getCanonicalPath());
		} else {
			String path = Paths.get(projectPath).resolve(Paths.get(modelPath)).toFile().getCanonicalPath();
			modelUri = URI.createFileURI(path);
		}

		Resource r = model.getResource(modelUri, true);
		if (r == null)
			throw new IOException("Model path could not be resolved: " + modelPath);

		return r;
	}

	public Resource addModel(final URI modelUri) throws Exception {
		return addModel(modelUri.toFileString());
	}

	public void setModel(final ResourceSet model) {
		this.model = model;
	}

	public Resource createModel(final String modelPath) throws Exception {
		prepareModelResourceSet();
		URI modelUri = null;
		String path = Paths.get(projectPath).resolve(Paths.get(modelPath)).toFile().getCanonicalPath();
		modelUri = URI.createFileURI(path);

		Resource r = model.createResource(modelUri);
		if (r == null)
			throw new IOException("Model path could not be resolved: " + modelPath);

		return r;
	}

	public Resource createModel(final URI modelUri) throws Exception {
		return createModel(modelUri.toFileString());
	}

	public void writeBackModel() throws Exception {
		for (Resource r : model.getResources().stream().filter(r -> !r.getURI().toFileString().contains("-trash"))
				.collect(Collectors.toSet())) {
			r.save(null);
		}
	}

	public void writeBackModel(final URI modelUri) throws Exception {
		model.getResources().stream().filter(r -> r.getURI().toFileString().equals(modelUri.toFileString())).findAny()
				.ifPresent(r -> {
					try {
						r.save(null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}

	public void writeBackModel(final Resource r) throws Exception {
		if (model.getResources().contains(r)) {
			r.save(null);
		}
	}

	public void saveModelTo(final URI sourceUri, final URI targetUri) throws Exception {
		model.getResources().stream().filter(r -> r.getURI().toFileString().equals(sourceUri.toFileString())).findAny()
				.ifPresent(r -> {
					try {
						Resource newR = model.createResource(targetUri);
						newR.getContents().addAll(r.getContents());
						newR.save(null);
						r.getContents().addAll(newR.getContents());
						newR.unload();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}

	public void saveModelTo(final Resource sourceR, final Resource targetR) throws Exception {
		if (model.getResources().contains(sourceR)) {
			targetR.getContents().addAll(sourceR.getContents());
			targetR.save(null);
			sourceR.getContents().addAll(targetR.getContents());
		}
	}

	public void initializeEngine() {
		gtEngine = new IBeXGTEngine<PM>(createPatternMatcher(), ibexModel, model);
		patternFactory = createPatternFactory();
		ruleFactory = createRuleFactory();
		initializeRules();
		initializePatterns();
	}

	/**
	 * Initializes the package registry of the resource set.
	 */
	protected void prepareModelResourceSet() {
		if (model != null)
			return;

		model = new ResourceSetImpl();
		model.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
				new SmartEMFResourceFactoryImpl(workspacePath));
		registerMetamodels(model);
	}

	/**
	 * Returns the resource set opened for transformations with the API.
	 * 
	 * @return the resource set containing the model file
	 */
	public ResourceSet getModel() {
		return model;
	}

	/**
	 * Triggers an incremental update of the matches.
	 */
	public void updateMatches() {
		gtEngine.updateMatches();
	}

	/**
	 * Terminates the interpreter.
	 */
	public void terminate() {
		gtEngine.terminate();
	}

	public IBeXGTEngine<PM> getGTEngine() {
		return gtEngine;
	}

}
