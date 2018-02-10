package org.emoflon.ibex.gt.engine;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

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
	public void loadPatterns(final Resource ibexPatternResource) {
		Objects.requireNonNull(ibexPatternResource, "Resource must not be null!");
		EObject resourceContent = ibexPatternResource.getContents().get(0);
		Objects.requireNonNull("Resource must not be empty");
		if (resourceContent instanceof IBeXPatternSet) {
			// Transform into patterns of the concrete engine.
			this.transformPatterns((IBeXPatternSet) resourceContent);
		}
	}

	/**
	 * Transforms the IBeXPattern into the patterns of the engine.
	 * 
	 * @param ibexPatternSet
	 *            the IBeXPatternSet to transform
	 */
	protected abstract void transformPatterns(final IBeXPatternSet ibexPatternSet);
}
