package org.emoflon.ibex.gt.api;

import java.io.IOException;
import java.util.Objects;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

/**
 * An application using an {@link GraphTransformationAPI}.
 */
public abstract class GraphTransformationApp<API extends GraphTransformationAPI> {
	/**
	 * The resource set.
	 */
	protected ResourceSet resourceSet = new ResourceSetImpl();

	/**
	 * The workspace path.
	 */
	protected String workspacePath = "../";

	/**
	 * Creates the model file with the given URI.
	 * 
	 * @param uri
	 *            the URI of the model file
	 * @return the resource
	 */
	protected Resource createModel(final URI uri) {
		prepareResourceSet();
		return resourceSet.createResource(uri);
	}

	/**
	 * Loads the model file with the given URI.
	 * 
	 * @param uri
	 *            the URI of the model file
	 * @return the resource
	 */
	protected Resource loadModel(final URI uri) {
		prepareResourceSet();
		return resourceSet.getResource(uri, true);
	}

	/**
	 * Saves all resources in the resource set.
	 * 
	 * @throws IOException
	 *             if an IOException is thrown on save
	 */
	protected void saveResourceSet() throws IOException {
		for (Resource resource : resourceSet.getResources()) {
			resource.save(null);
		}
	}

	/**
	 * Initializes the package registry of the resource set.
	 */
	private void prepareResourceSet() {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		registerMetaModels();
	}

	/**
	 * Add the meta-models to the package registry.
	 */
	protected abstract void registerMetaModels();

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
	 * Sets the workspace path to the given path.
	 * 
	 * @param workspacePath
	 *            the workspace path to set
	 */
	protected void setWorkspacePath(final String workspacePath) {
		Objects.requireNonNull(workspacePath, "The workspace path must not be null!");
		this.workspacePath = workspacePath;
	}

	/**
	 * Creates a new API.
	 * 
	 * @return the created API
	 */
	protected abstract API initAPI(IContextPatternInterpreter engine);
}
