package org.emoflon.ibex.gt.engine;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import IBeXLanguage.IBeXLanguagePackage;
import IBeXLanguage.IBeXPatternSet;

/**
 * Engine for unidirectional graph transformations which needs to be implemented
 * for a concrete pattern matcher.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public abstract class GTEngine {
	protected Optional<String> debugPath = Optional.ofNullable(null);
	private boolean patternSetLoaded = false;

	/**
	 * Sets the debug path.
	 * 
	 * @param debugPath
	 *            the path for the debugging output. If it is <code>null</null>,
	 *            debugging is disabled.
	 * 
	 */
	public void setDebugPath(final String debugPath) {
		this.debugPath = Optional.ofNullable(debugPath);
	}

	/**
	 * Loads IBeXPatterns from the resource set.
	 * 
	 * @param ibexPatternResource
	 *            a resource containing IBeXPatterns
	 */
	public void loadPatternSet(final Resource ibexPatternResource) {
		Objects.requireNonNull(ibexPatternResource, "Resource must not be null!");
		EObject resourceContent = ibexPatternResource.getContents().get(0);
		Objects.requireNonNull("Resource must not be empty!");
		if (resourceContent instanceof IBeXPatternSet) {
			// Transform into patterns of the concrete engine.
			this.transformPatterns((IBeXPatternSet) resourceContent);
			this.patternSetLoaded = true;
		} else {
			throw new IllegalArgumentException("Expecting a IBeXPatternSet root element!");
		}
	}

	/**
	 * Loads IBeXPatterns from the given URI.
	 * 
	 * @param uri
	 *            the URI of the ibex-pattern.xmi file
	 */
	public void loadPatternSet(final URI uri) {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getPackageRegistry().put(IBeXLanguagePackage.eNS_URI, IBeXLanguagePackage.eINSTANCE);

		Resource ibexPatternResource = rs.getResource(uri, true);
		this.loadPatternSet(ibexPatternResource);
	}

	/**
	 * Returns whether the IBeXPatterns are loaded.
	 * 
	 * @return true if <code>loadPatterns</code> has been called successfully.
	 */
	public boolean isPatternSetLoaded() {
		return this.patternSetLoaded;
	}

	/**
	 * Transforms the IBeXPattern into the patterns of the engine.
	 * 
	 * @param ibexPatternSet
	 *            the IBeXPatternSet to transform
	 */
	protected abstract void transformPatterns(final IBeXPatternSet ibexPatternSet);
}
