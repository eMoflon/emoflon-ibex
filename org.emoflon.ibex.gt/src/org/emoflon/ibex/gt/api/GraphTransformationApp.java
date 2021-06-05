package org.emoflon.ibex.gt.api;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;

import persistence.XtendXMIResource;
import persistence.XtendXMIResourceFactoryImpl;

/**
 * An application using an {@link GraphTransformationAPI}.
 */
public abstract class GraphTransformationApp<API extends GraphTransformationAPI> {
	/**
	 * The resource set.
	 */
	protected ResourceSet resourceSet;

	/**
	 * The pattern matching engine.
	 */
	protected final IContextPatternInterpreter engine;

	/**
	 * The workspace path.
	 */
	protected String workspacePath = "../";

	/**
	 * The default resource if set explicitly.
	 */
	protected Optional<Resource> defaultResource = Optional.empty();

	/**
	 * Creates the application with the given engine.
	 * 
	 * @param engine
	 *            the pattern matching engine
	 */
	public GraphTransformationApp(final IContextPatternInterpreter engine) {
		this.engine = engine;
		this.resourceSet = engine.createAndPrepareResourceSet(workspacePath);
	}

	/**
	 * Creates the application with the given engine.
	 * 
	 * @param engine
	 *            the pattern matching engine
	 * @param workspacePath
	 *            the workspace path
	 */
	public GraphTransformationApp(final IContextPatternInterpreter engine, final String workspacePath) {
		this.engine = engine;
		this.resourceSet = engine.createAndPrepareResourceSet(workspacePath);
		this.workspacePath = workspacePath;
	}

	/**
	 * Creates the model file with the given URI.
	 * 
	 * @param uri
	 *            the URI of the model file
	 * @return the resource
	 */
	public Resource createModel(final URI uri) {
		prepareResourceSet();
		return resourceSet.createResource(uri);
//		return resourceSet.getResource(uri, true);
	}

	/**
	 * Loads the model file with the given URI.
	 * 
	 * @param uri
	 *            the URI of the model file
	 * @return the resource
	 */
	public Resource loadModel(final URI uri) {
		prepareResourceSet();
		return resourceSet.getResource(uri, true);
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	public ResourceSet getModel() {
		return resourceSet;
	}
	
	/**
	 * Sets the model.
	 * 
	 * @param the model
	 */
	public void setModel(final ResourceSet model) {
		resourceSet = model;
	}

	/**
	 * Saves all resources in the resource set.
	 * 
	 * @throws IOException
	 *             if an IOException is thrown on save
	 */
	public void saveResourceSet() throws IOException {
		for (final Resource resource : resourceSet.getResources()) {
			resource.save(null);
		}
	}

	/**
	 * Initializes the package registry of the resource set.
	 */
	private void prepareResourceSet() {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("xmi", new XtendXMIResourceFactoryImpl());
		registerMetaModels();
	}

	/**
	 * Add the meta-models to the package registry.
	 */
	public abstract void registerMetaModels();

	/**
	 * Registers the given EPackage as a meta-model.
	 * 
	 * @param ePackage
	 *            the package to register.
	 */
	protected void registerMetaModel(final EPackage ePackage) {
		resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
	}

	/**
	 * Sets the default resource.
	 * 
	 * @param defaultResource
	 *            the default resource
	 */
	public void setDefaultResource(final Resource defaultResource) {
		if (!resourceSet.getResources().contains(defaultResource)) {
			throw new IllegalArgumentException(defaultResource.getURI() + " is not part of the model");
		}
		this.defaultResource = Optional.of(defaultResource);
	}

	/**
	 * Creates a new API.
	 * 
	 * @return the created API
	 */
	public abstract API initAPI();
}
