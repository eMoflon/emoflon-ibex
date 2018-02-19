package org.emoflon.ibex.gt.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.common.operational.IPatternInterpreter;

import IBeXLanguage.IBeXLanguagePackage;
import IBeXLanguage.IBeXPatternSet;

/**
 * The GraphTransformationInterpreter implements rule application based on a
 * pattern matching engine.
 */
public class GraphTransformationInterpreter implements IMatchObserver {
	/**
	 * The folder for debugging output.
	 */
	protected Optional<String> debugPath = Optional.ofNullable(null);

	/**
	 * Whether the IBeXPatternSet was loaded.
	 */
	private boolean patternSetLoaded = false;

	/**
	 * The pattern matching engine.
	 */
	private IPatternInterpreter engine;

	/**
	 * The resource set containing the model file.
	 */
	private ResourceSet model;

	/**
	 * Creates a new GraphTransformationInterpreter.
	 * 
	 * @param engine
	 *            the engine of the pattern matcher
	 * @param model
	 *            the resource set containing the model file
	 */
	public GraphTransformationInterpreter(final IPatternInterpreter engine, final ResourceSet model) {
		this.engine = engine;
		this.model = model;
	}

	/**
	 * Returns the resource set.
	 * 
	 * @return the resource set
	 */
	public ResourceSet getModel() {
		return this.model;
	}

	/**
	 * Sets the debug path.
	 * 
	 * @param debugPath
	 *            the path for the debugging output. If it is <code>null</null>,
	 *            debugging is disabled.
	 */
	public void setDebugPath(final String debugPath) {
		this.debugPath = Optional.ofNullable(debugPath);
		this.engine.setDebugPath(debugPath);
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
			this.engine.initPatterns((IBeXPatternSet) resourceContent);
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
	 * Executes the pattern.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @param match
	 *            the match to execute the pattern on
	 */
	public void execute(final String patternName, final Map<String, EObject> match) {
		// TODO implement
	}

	/**
	 * Finds a match for the pattern.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @return an {@link Optional} for the match
	 */
	public Optional<Map<String, EObject>> findAnyMatch(final String patternName) {
		// TODO implement
		return Optional.empty();
	}

	/**
	 * Finds all matches for the pattern.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @return a {@link Collection} of matches
	 */
	public Collection<Map<String, EObject>> findMatches(final String patternName) {
		// TODO implement
		return new ArrayList<Map<String, EObject>>();
	}

	@Override
	public void addMatch(final IMatch match) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeMatch(final IMatch match) {
		// TODO Auto-generated method stub
	}
}
