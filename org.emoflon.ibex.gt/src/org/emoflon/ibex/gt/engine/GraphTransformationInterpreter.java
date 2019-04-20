package org.emoflon.ibex.gt.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.common.operational.ICreatePatternInterpreter;
import org.emoflon.ibex.common.operational.IDeletePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;

import IBeXLanguage.IBeXContext;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXLanguagePackage;
import IBeXLanguage.IBeXPatternSet;

/**
 * The GraphTransformationInterpreter implements rule application based on a
 * pattern matching engine.
 */
public class GraphTransformationInterpreter implements IMatchObserver {
	/**
	 * The pattern set containing the patterns.
	 */
	private IBeXPatternSet patternSet;

	/**
	 * The pattern matching engine.
	 */
	private IContextPatternInterpreter contextPatternInterpreter;

	/**
	 * The interpreter for creation of elements.
	 */
	private ICreatePatternInterpreter createPatternInterpreter;

	/**
	 * The interpreter for deletion of elements.
	 */
	private IDeletePatternInterpreter deletePatternInterpreter;

	/**
	 * The resource set containing the model file.
	 */
	private ResourceSet model;

	/**
	 * The matches (key: pattern name, value: list of matches).
	 */
	private Map<String, List<IMatch>> matches = new HashMap<String, List<IMatch>>();

	/**
	 * Subscriptions for notification of new matches (key: pattern name, value: list
	 * of consumers).
	 */
	private Map<String, List<Consumer<IMatch>>> subscriptionsForAppearingMatchesOfPattern //
			= new HashMap<String, List<Consumer<IMatch>>>();

	/**
	 * Subscriptions for notification of disappearing matches (key: pattern name,
	 * value: list of consumers).
	 */
	private Map<String, List<Consumer<IMatch>>> subscriptionsForDisappearingMatchesOfPattern //
			= new HashMap<String, List<Consumer<IMatch>>>();

	/**
	 * Subscriptions for notification of disappearing matches (key: match, value:
	 * list of consumers).
	 */
	private Map<IMatch, List<Consumer<IMatch>>> subscriptionsForDisappearingMatches //
			= new HashMap<IMatch, List<Consumer<IMatch>>>();

	/**
	 * Creates a new GraphTransformationInterpreter for queries and modifications on
	 * the given resource set. The default resource is set to the first resource in
	 * the resource set.
	 * 
	 * @param engine
	 *            the engine of the pattern matcher
	 * @param model
	 *            the resource set containing at least one model resource
	 */
	public GraphTransformationInterpreter(final IContextPatternInterpreter engine, final ResourceSet model) {
		Objects.requireNonNull(model, "The resource set must not be null!");
		if (model.getResources().size() == 0) {
			throw new IllegalArgumentException("Resource set must not be empty!");
		}
		init(engine, model, model.getResources().get(0));
	}

	/**
	 * Creates a new GraphTransformationInterpreter for queries and modifications on
	 * the given resource set with the given resource as default resource.
	 * 
	 * @param engine
	 *            the engine of the pattern matcher
	 * @param model
	 *            the resource set containing at least one model resource
	 * @param defaultResource
	 *            the default resource
	 */
	public GraphTransformationInterpreter(final IContextPatternInterpreter engine, final ResourceSet model,
			final Resource defaultResource) {
		Objects.requireNonNull(defaultResource, "The default resource must not be null!");
		init(engine, model, defaultResource);
	}

	/**
	 * Initializes the GraphTransformationInterpreter.
	 * 
	 * @param engine
	 *            the engine of the pattern matcher
	 * @param model
	 *            the resource set containing at least one model resource
	 * @param defaultResource
	 *            the default resource
	 */
	private void init(final IContextPatternInterpreter engine, final ResourceSet model,
			final Resource defaultResource) {
		Objects.requireNonNull(engine, "The engine must not be null!");
		Objects.requireNonNull(model, "The resource set must not be null!");
		Objects.requireNonNull(defaultResource, "The resource must not be null!");

		URI trashURI = defaultResource.getURI().trimFileExtension();
		trashURI = trashURI.trimSegments(1).appendSegment(trashURI.lastSegment() + "-trash").appendFileExtension("xmi");
		Resource trashResource = model.createResource(trashURI);

		this.contextPatternInterpreter = engine;
		this.model = model;
		createPatternInterpreter = new GraphTransformationCreateInterpreter(defaultResource);
		deletePatternInterpreter = new GraphTransformationDeleteInterpreter(trashResource);
	}

	/**
	 * Returns the resource set.
	 * 
	 * @return the resource set
	 */
	public ResourceSet getModel() {
		return model;
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
			contextPatternInterpreter.initialise(model.getPackageRegistry(), this);

			// Transform into patterns of the concrete engine.
			patternSet = (IBeXPatternSet) resourceContent;
			contextPatternInterpreter.initPatterns(patternSet);
			contextPatternInterpreter.monitor(model);
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
		rs.getPackageRegistry().putAll(model.getPackageRegistry());
		rs.getPackageRegistry().put(IBeXLanguagePackage.eNS_URI, IBeXLanguagePackage.eINSTANCE);
		Resource ibexPatternResource = rs.getResource(uri, true);
		EcoreUtil.resolveAll(rs);
		loadPatternSet(ibexPatternResource);
	}

	/**
	 * Returns whether the IBeXPatterns are loaded.
	 * 
	 * @return true if <code>loadPatterns</code> has been called successfully.
	 */
	public boolean isPatternSetLoaded() {
		return patternSet != null;
	}

	/**
	 * Terminates the engine.
	 */
	public void terminate() {
		contextPatternInterpreter.terminate();
	}

	/**
	 * Executes the pattern.
	 * 
	 * @param match
	 *            the match to execute the pattern on
	 * @param po
	 *            the pushout approach to use
	 * @param parameters
	 *            the parameters to pass
	 * @return the match after rule application
	 */
	public Optional<IMatch> apply(final IMatch match, final PushoutApproach po, final Map<String, Object> parameters) {
		String patternName = match.getPatternName();

		IBeXCreatePattern createPattern = IBeXPatternUtils.getCreatePattern(patternSet, patternName);
		IBeXDeletePattern deletePattern = IBeXPatternUtils.getDeletePattern(patternSet, patternName);

		// Execute deletion.
		IMatch originalMatch = new SimpleMatch(match);
		Optional<IMatch> comatch = deletePatternInterpreter.apply(deletePattern, originalMatch, po);

		// Execute creation.
		if (comatch.isPresent()) {
			comatch = createPatternInterpreter.apply(createPattern, comatch.get(), parameters);
		}

		// Rule application may invalidate existing or lead to new matches.
		updateMatches();

		// Return the co-match.
		return comatch;
	}

	/**
	 * Finds all matches for the pattern.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @param parameters
	 *            the parameters
	 * @return a {@link Stream} of matches
	 */
	public Stream<IMatch> matchStream(final String patternName, final Map<String, Object> parameters) {
		updateMatches();

		IBeXContext pattern = IBeXPatternUtils.getContextPattern(patternSet, patternName);
		if (IBeXPatternUtils.isEmptyPattern(pattern)) {
			return Stream.of(createEmptyMatchForCreatePattern(patternName));
		}

		return MatchFilter.getFilteredMatchStream(pattern, parameters, matches);
	}

	/**
	 * Creates an empty match for the create pattern with the given name. The
	 * created nodes are mapped to <code>null</code>.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @return the created match
	 */
	private IMatch createEmptyMatchForCreatePattern(final String patternName) {
		Optional<IBeXCreatePattern> pattern = patternSet.getCreatePatterns().stream()
				.filter(p -> p.getName().equals(patternName)) //
				.findAny();
		if (pattern.isPresent()) {
			IMatch match = new SimpleMatch(patternName);
			pattern.get().getCreatedNodes().forEach(node -> match.put(node.getName(), null));
			return match;
		} else {
			throw new IllegalArgumentException(String.format("No create pattern called %s", patternName));
		}
	}

	/**
	 * Subscribes notifications whenever a new match for the pattern is found.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @param consumer
	 *            the consumer to add
	 */
	public void subscribeAppearing(final String patternName, final Consumer<IMatch> consumer) {
		if (!subscriptionsForAppearingMatchesOfPattern.containsKey(patternName)) {
			subscriptionsForAppearingMatchesOfPattern.put(patternName, new ArrayList<Consumer<IMatch>>());
		}
		subscriptionsForAppearingMatchesOfPattern.get(patternName).add(consumer);
	}

	/**
	 * Deletes the subscription of the notifications for the given pattern and
	 * consumer.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @param consumer
	 *            the consumer to remove
	 */
	public void unsubscibeAppearing(final String patternName, final Consumer<IMatch> consumer) {
		if (subscriptionsForAppearingMatchesOfPattern.containsKey(patternName)) {
			subscriptionsForAppearingMatchesOfPattern.get(patternName).remove(consumer);
		}
	}

	/**
	 * Subscribes notifications whenever a match for the pattern disappears.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @param consumer
	 *            the consumer to add
	 */
	public void subscribeDisappearing(final String patternName, final Consumer<IMatch> consumer) {
		if (!subscriptionsForDisappearingMatchesOfPattern.containsKey(patternName)) {
			subscriptionsForDisappearingMatchesOfPattern.put(patternName, new ArrayList<Consumer<IMatch>>());
		}
		subscriptionsForDisappearingMatchesOfPattern.get(patternName).add(consumer);
	}

	/**
	 * Deletes the subscription of the notifications for the given pattern and
	 * consumer.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @param consumer
	 *            the consumer to remove
	 */
	public void unsubscibeDisappearing(final String patternName, final Consumer<IMatch> consumer) {
		if (subscriptionsForDisappearingMatchesOfPattern.containsKey(patternName)) {
			subscriptionsForDisappearingMatchesOfPattern.get(patternName).remove(consumer);
		}
	}

	/**
	 * Subscribes notifications whenever the given match disappears.
	 * 
	 * @param match
	 *            the match
	 * @param consumer
	 *            the consumer to add
	 */
	public void subscribeMatchDisappears(final IMatch match, final Consumer<IMatch> consumer) {
		if (!subscriptionsForDisappearingMatches.containsKey(match)) {
			subscriptionsForDisappearingMatches.put(match, new ArrayList<Consumer<IMatch>>());
		}
		subscriptionsForDisappearingMatches.get(match).add(consumer);
	}

	/**
	 * Deletes the subscription of notifications when the given match disappears.
	 * 
	 * @param match
	 *            the match
	 * @param consumer
	 *            the consumer to add
	 */
	public void unsubscribeMatchDisappears(final IMatch match, final Consumer<IMatch> consumer) {
		if (subscriptionsForDisappearingMatches.containsKey(match)) {
			subscriptionsForDisappearingMatches.get(match).remove(consumer);
		}
	}

	/**
	 * Trigger the engine to update the pattern network.
	 */
	public void updateMatches() {
		contextPatternInterpreter.updateMatches();
	}

	@Override
	public void addMatch(final IMatch match) {
		String patternName = match.getPatternName();
		if (!matches.containsKey(patternName)) {
			matches.put(patternName, new ArrayList<IMatch>());
		}
		matches.get(patternName).add(match);

		// Notify subscribers registered for all new matches of the pattern.
		if (subscriptionsForAppearingMatchesOfPattern.containsKey(patternName)) {
			subscriptionsForAppearingMatchesOfPattern.get(patternName).forEach(c -> c.accept(match));
		}
	}

	@Override
	public void removeMatch(final IMatch match) {
		String patternName = match.getPatternName();
		if (matches.containsKey(patternName)) {
			matches.get(patternName).remove(match);

			// Notify subscribers registered for all disappearing matches of the pattern.
			if (subscriptionsForDisappearingMatchesOfPattern.containsKey(patternName)) {
				subscriptionsForDisappearingMatchesOfPattern.get(patternName).forEach(c -> c.accept(match));
			}

			// Notify subscribers registered for the disappearing match.
			if (subscriptionsForDisappearingMatches.containsKey(match)) {
				subscriptionsForDisappearingMatches.get(match).forEach(c -> c.accept(match));
				subscriptionsForDisappearingMatches.remove(match);
			}
		} else {
			throw new IllegalArgumentException("Cannot remove a match which was never added!");
		}
	}

	public IBeXPatternSet getPatternSet() {
		return patternSet;
	}
}
