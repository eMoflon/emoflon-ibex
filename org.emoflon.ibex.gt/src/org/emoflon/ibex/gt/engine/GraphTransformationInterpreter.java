package org.emoflon.ibex.gt.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
	 * The matches.
	 */
	private Map<String, List<IMatch>> matches = new HashMap<String, List<IMatch>>();

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
			this.engine.initialise(this.model.getPackageRegistry(), this);

			// Transform into patterns of the concrete engine.
			this.engine.initPatterns((IBeXPatternSet) resourceContent);
			this.patternSetLoaded = true;

			this.engine.monitor(this.model);
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
	 * Terminates the engine.
	 */
	public void terminate() {
		this.engine.terminate();
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
		this.updateMatches();

		if (!this.matches.containsKey(patternName) || this.matches.get(patternName).isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(this.convertMatch(this.matches.get(patternName).get(0)));
	}

	/**
	 * Finds all matches for the pattern.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @return a {@link Collection} of matches
	 */
	public Collection<Map<String, EObject>> findMatches(final String patternName) {
		this.updateMatches();

		ArrayList<Map<String, EObject>> matchesForPattern = new ArrayList<Map<String, EObject>>();
		if (!this.matches.containsKey(patternName)) {
			return matchesForPattern;
		}
		this.matches.get(patternName).forEach(match -> matchesForPattern.add(this.convertMatch(match)));
		return matchesForPattern;
	}

	private Map<String, EObject> convertMatch(final IMatch match) {
		HashMap<String, EObject> mapping = new HashMap<String, EObject>();
		match.getParameterNames().forEach(p -> mapping.put(p, (EObject) match.get(p)));
		return mapping;
	}

	/**
	 * Trigger the engine to update the pattern network.
	 */
	private void updateMatches() {
		this.engine.updateMatches();
	}

	@Override
	public void addMatch(final IMatch match) {
		String patternName = match.getPatternName();
		if (!this.matches.containsKey(patternName)) {
			this.matches.put(patternName, new ArrayList<IMatch>());
		}
		this.matches.get(patternName).add(match);
	}

	@Override
	public void removeMatch(final IMatch match) {
		String patternName = match.getPatternName();
		if (this.matches.containsKey(patternName)) {
			this.matches.get(patternName).remove(match);
		} else {
			throw new IllegalArgumentException("Cannot remove a match which was never added!");
		}
	}
}
