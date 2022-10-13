package org.emoflon.ibex.gt.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.gt.engine.IBeXGTEngine;
import org.emoflon.ibex.gt.engine.IBeXGTPatternMatcher;
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
public abstract class IBeXGtAPI<PM extends IBeXGTPatternMatcher<PM, ?>> {

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

	/**
	 * TODO: Reimplement this once the new patter base-types are complete... Map
	 * with all the rules and patterns of the model
	 * 
	 * @throws Exception
	 */
//	protected Map<String, Supplier<? extends GraphTransformationPattern<?,?>>> patternMap;
//	TODO: Same..
//	protected abstract Map<String, Supplier<? extends GraphTransformationPattern<?,?>>> initiatePatternMap();
//	TODO: Same..
//	/**
//	 * returns all the patterns and rules of the model that do not need an input
//	 * parameter
//	 */
//	public Map<String, Supplier<? extends GraphTransformationPattern<?, ?>>> getAllPatterns() {
//		return patternMap;
//	}
//
//	public GraphTransformationPattern<?, ?> getPattern(String patternName) {
//		return patternMap.get(patternName).get();
//	}

	public IBeXGtAPI() throws Exception {
		workspacePath = getWorkspacePath();
		projectPath = getProjectPath();
		ibexModelPath = getIBeXModelPath();
		projectName = getProjectName();
		ibexModel = loadGTModel();
	}

	public abstract String getWorkspacePath();

	public abstract String getProjectPath();

	public abstract String getIBeXModelPath();

	public abstract String getProjectName();

	protected GTModel loadGTModel() throws Exception {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getPackageRegistry().put(IBeXGTModelPackage.eINSTANCE.getNsURI(), IBeXGTModelPackage.eINSTANCE);
		Resource r = rs.getResource(URI.createFileURI(ibexModelPath), false);
		return (GTModel) r.getContents().get(0);
	}

	public void setModel(final String modelPath) throws Exception {
		prepareModelResourceSet();
		File modelFile = new File(modelPath);
		URI modelUri = null;
		if (modelFile.exists() && modelFile.isFile() && modelFile.isAbsolute()) {
			modelUri = URI.createFileURI(modelPath);
		} else {
			String path = Paths.get(projectPath).resolve(Paths.get(modelPath)).toFile().getCanonicalPath();
			modelUri = URI.createFileURI(path);
		}

		Resource r = model.getResource(modelUri, false);
		if (r == null)
			throw new IOException("Model path could not be resolved: " + modelPath);
	}

	public void setModel(final URI modelUri) throws Exception {
		setModel(modelUri.toFileString());
	}

	public void setModel(final ResourceSet model) {
		this.model = model;
	}

	public void initializeEngine() {
		gtEngine = new IBeXGTEngine<PM>(createPatternMatcher(), ibexModel, model);
	}

	/**
	 * Initializes the package registry of the resource set.
	 */
	protected void prepareModelResourceSet() {
		model = new ResourceSetImpl();
		model.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi",
				new SmartEMFResourceFactoryImpl(workspacePath));
		registerModelMetamodels();
	}

	/**
	 * Add the meta-models to the package registry.
	 */
	protected abstract void registerModelMetamodels();

	protected abstract PM createPatternMatcher();

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
