package org.emoflon.ibex.gt.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;
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
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet;

/**
 * The GraphTransformationInterpreter implements rule application based on a
 * pattern matching engine.
 */
public class GraphTransformationInterpreter implements IMatchObserver {
	/**
	 * The pattern set containing the patterns.
	 */
	private IBeXModel ibexModel;
	private IBeXPatternSet patternSet;
	private IBeXRuleSet ruleSet;
	
	private Map<String, IBeXContext> name2Pattern;
	private Map<String, GraphTransformationPattern<?,?>> name2GTPattern;
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
	private Map<String, Collection<IMatch>> matches = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Collection<IMatch>> pendingMatches = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Collection<IMatch>> filteredMatches = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Collection<IMatch>> addedMatches = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Collection<IMatch>> removedMatches = Collections.synchronizedMap(new HashMap<>());

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

	private Map<IMatch, Queue<Consumer<IMatch>>> appearingSubscriptionJobs = Collections.synchronizedMap(new LinkedHashMap<>());
	private Map<IMatch, Queue<Consumer<IMatch>>> disappearingSubscriptionJobs = Collections.synchronizedMap(new LinkedHashMap<>());
	
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
		name2GTPattern = new HashMap<>();
		name2Pattern = new HashMap<>();
		createPatternInterpreter = new GraphTransformationCreateInterpreter(defaultResource, this);
		deletePatternInterpreter = new GraphTransformationDeleteInterpreter(trashResource);
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
		if (resourceContent instanceof IBeXModel) {
			contextPatternInterpreter.initialise(model.getPackageRegistry(), this);

			// Transform into patterns of the concrete engine.
			ibexModel = (IBeXModel) resourceContent;
			patternSet = ibexModel.getPatternSet();
			patternSet.getContextPatterns().forEach(pattern -> {
				matches.put(pattern.getName(), Collections.synchronizedSet(new HashSet<IMatch>()));
				name2Pattern.put(pattern.getName(), pattern);
			});
			ruleSet = ibexModel.getRuleSet();
			contextPatternInterpreter.initPatterns(patternSet);
			contextPatternInterpreter.monitor(model.getResources());
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
		rs.getPackageRegistry().put(IBeXPatternModelPackage.eNS_URI, IBeXPatternModelPackage.eINSTANCE);
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
		return ibexModel != null;
	}

	/**
	 * Terminates the engine.
	 */
	public void terminate() {
		contextPatternInterpreter.terminate();
	}

	/**
	 * Executes the pattern, but does not update beforehand. 
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
		return apply(match, po, parameters, false);
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
	 * @param doUpdate
	 * 			  triggers the incremental recalculation of all matches
	 * @return the match after rule application
	 */
	public Optional<IMatch> apply(final IMatch match, final PushoutApproach po, final Map<String, Object> parameters, boolean doUpdate) {
		String patternName = match.getPatternName();

		IBeXCreatePattern createPattern = IBeXPatternUtils.getCreatePattern(ruleSet, patternName);
		IBeXDeletePattern deletePattern = IBeXPatternUtils.getDeletePattern(ruleSet, patternName);

		// Execute deletion.
		IMatch originalMatch = new SimpleMatch(match);
		Optional<IMatch> comatch = deletePatternInterpreter.apply(deletePattern, originalMatch, po);

		// Execute creation.
		if (comatch.isPresent()) {
			comatch = createPatternInterpreter.apply(createPattern, comatch.get(), parameters);
		}

		// Rule application may invalidate existing or lead to new matches. 
//		-> Which is pretty much obvious to any "normal" user, hence, no more hidden update calls.
		if(doUpdate)
			updateMatches();

		// Return the co-match.
		return comatch;
	}

	/**
	 * Finds all matches for the pattern, but does not update beforehand. 
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @param parameters
	 *            the parameters
	 * @return a {@link Stream} of matches
	 */
	public synchronized Stream<IMatch> matchStream(final String patternName, final Map<String, Object> parameters) {
		return matchStream(patternName, parameters, false);
	}
	
	/**
	 * Finds all matches for the pattern.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @param parameters
	 *            the parameters
	 * @param doUpdate
	 * 			  triggers the incremental recalculation of all matches
	 * @return a {@link Stream} of matches
	 */
	public synchronized Stream<IMatch> matchStream(final String patternName, final Map<String, Object> parameters, boolean doUpdate) {
//		Hiding update calls from the user seems dangerous to me. In my experience this practice more often than not leads to a huge amount of nested update calls, leading to stack overflows.
		if(doUpdate)
			updateMatches();
		
		if(filteredMatches.containsKey(patternName)) {
			return filteredMatches.get(patternName).stream();
		}
		
		Collection<IMatch> patternMatches = Collections.synchronizedSet(new HashSet<IMatch>());
		filteredMatches.put(patternName, patternMatches);
		
		IBeXContext pattern = name2Pattern.get(patternName);
		if (IBeXPatternUtils.isEmptyPattern(pattern)) {
			patternMatches.add(createEmptyMatchForCreatePattern(patternName));
		} else {
			GraphTransformationPattern<?,?> gtPattern = name2GTPattern.get(patternName);
			patternMatches.addAll(MatchFilter.getFilteredMatchStream(pattern, parameters, matches)
					.filter(match -> gtPattern.isMatchValid(match))
					.collect(Collectors.toSet()));
		}
		
		return patternMatches.stream();
	}

	
	
	/**
	 * Trigger the engine to update the pattern network.
	 */
	public synchronized void updateMatches() {
		// Clear old state
		filteredMatches.clear();
		addedMatches.clear();
		removedMatches.clear();
		
		// Fetch matches from pm
		contextPatternInterpreter.updateMatches();
		
		// (1) ADDED: Check for appearing match subscribers and filter matches
		subscriptionsForAppearingMatchesOfPattern.keySet().parallelStream().forEach(patternName -> {
			// Check if pending matches became valid again due to attribute changes
			// Fill filtered matches Map by calling the match stream
			matchStream(patternName, name2GTPattern.get(patternName).getParameters());
			// Check if existing matches recently became valid (pending) and add removal jobs
			matches.get(patternName).parallelStream()
				.filter(match -> filteredMatches.get(patternName).contains(match))
				.filter(match -> pendingMatches.containsKey(patternName) && pendingMatches.get(patternName).contains(match))
				.forEach(match -> {
					Collection<IMatch> pending = pendingMatches.get(patternName);
					pending.remove(match);
					
					Collection<IMatch> added = addedMatches.get(patternName);
					if(added == null) {
						added = Collections.synchronizedSet(new HashSet<>());
						removedMatches.put(patternName, added);
					}
					added.add(match);
					
					Queue<Consumer<IMatch>> subs = appearingSubscriptionJobs.get(match);
					if(subs == null) {
						subs = new LinkedBlockingQueue<>();
						appearingSubscriptionJobs.put(match, subs);
					}
					subs.addAll(subscriptionsForAppearingMatchesOfPattern.get(patternName));
			});
			
			
			if(addedMatches.containsKey(patternName)) {
				addedMatches.get(patternName).forEach(match -> {
					Queue<Consumer<IMatch>> subs = appearingSubscriptionJobs.get(match);
					if(subs == null) {
						subs = new LinkedBlockingQueue<>();
						appearingSubscriptionJobs.put(match, subs);
					}
					subs.addAll(subscriptionsForAppearingMatchesOfPattern.get(patternName));
				});
			}
			
		});
		
		// (2) DELETED: Check for disappearing match subscribers and filter matches
		subscriptionsForDisappearingMatchesOfPattern.keySet().parallelStream().forEach(patternName -> {
			// Check if existing matches became invalid due to attribute changes
			// Fill filtered matches Map by calling the match stream
			matchStream(patternName, name2GTPattern.get(patternName).getParameters());
			// Check if existing matches recently became invalid (not pending) and add removal jobs
			matches.get(patternName).parallelStream()
				.filter(match -> !filteredMatches.get(patternName).contains(match))
				.filter(match -> !pendingMatches.containsKey(patternName) || !pendingMatches.get(patternName).contains(match))
				.forEach(match -> {
					Collection<IMatch> pending = pendingMatches.get(patternName);
					if(pending == null) {
						pending = Collections.synchronizedSet(new HashSet<>());
						pendingMatches.put(patternName, pending);
					}
					pending.add(match);
					Collection<IMatch> removed = removedMatches.get(patternName);
					if(removed == null) {
						removed = Collections.synchronizedSet(new HashSet<>());
						removedMatches.put(patternName, removed);
					}
					removed.add(match);
					
					Queue<Consumer<IMatch>> subs = disappearingSubscriptionJobs.get(match);
					if(subs == null) {
						subs = new LinkedBlockingQueue<>();
						disappearingSubscriptionJobs.put(match, subs);
					}
					subs.addAll(subscriptionsForDisappearingMatchesOfPattern.get(patternName));
				});
			
			if(removedMatches.containsKey(patternName)) {
				removedMatches.get(patternName).forEach(match -> {
					Queue<Consumer<IMatch>> subs = disappearingSubscriptionJobs.get(match);
					if(subs == null) {
						subs = new LinkedBlockingQueue<>();
						disappearingSubscriptionJobs.put(match, subs);
					}
					subs.addAll(subscriptionsForDisappearingMatchesOfPattern.get(patternName));
				});
			}
		});
		
		// Check if any match removed subscriptions are triggered by removals
		Set<IMatch> removals = subscriptionsForDisappearingMatches.keySet().parallelStream()
			.filter(match -> removedMatches.containsKey(match.getPatternName()))
			.filter(match -> removedMatches.get(match.getPatternName()).contains(match))
			.collect(Collectors.toSet());
		
		for(IMatch removal : removals) {
			Queue<Consumer<IMatch>> subs = disappearingSubscriptionJobs.get(removal);
			if(subs == null) {
				subs = new LinkedBlockingQueue<>();
				disappearingSubscriptionJobs.put(removal, subs);
			}
			subs.addAll(subscriptionsForDisappearingMatchesOfPattern.get(removal.getPatternName()));
		}
		
		// Remove jobs for appearing matches for each disappeared match
		for(IMatch removal : subscriptionsForDisappearingMatches.keySet()) {
			appearingSubscriptionJobs.remove(removal);
		}
		
		notifySubscriptions();
	}
	
	public void registerGraphTransformationPattern(final String patternName, final GraphTransformationPattern<?,?> pattern) {
		if(name2GTPattern.containsKey(patternName))
			throw new IllegalArgumentException("Pattern already registered with interpreter: " + name2Pattern.get(patternName));
		
		name2GTPattern.put(patternName, pattern);
	}
	
	public GraphTransformationPattern<?,?> getRegisteredGraphTransformationPattern(final String patternName) {
		if(!name2GTPattern.containsKey(patternName))
			throw new IllegalArgumentException("Pattern not registered with interpreter: " + name2Pattern.get(patternName));
		
		return name2GTPattern.get(patternName);
	}
	
	@Override
	public synchronized void notifySubscriptions() {
		while(!disappearingSubscriptionJobs.isEmpty()) {
			IMatch nextMatch = disappearingSubscriptionJobs.keySet().iterator().next();
			Queue<Consumer<IMatch>> subs = disappearingSubscriptionJobs.get(nextMatch);
			disappearingSubscriptionJobs.remove(nextMatch);
			while(!subs.isEmpty()) {
				subs.poll().accept(nextMatch);
			}
		}
		
		Set<IMatch> jobs = new LinkedHashSet<>(appearingSubscriptionJobs.keySet());
		for(IMatch nextMatch : jobs) {
			Queue<Consumer<IMatch>> subs = appearingSubscriptionJobs.get(nextMatch);
			if(subs == null || subs.isEmpty()) {
				appearingSubscriptionJobs.remove(nextMatch);
				continue;
			}
			
			appearingSubscriptionJobs.remove(nextMatch);
			while(matches.get(nextMatch.getPatternName()).contains(nextMatch) && !subs.isEmpty()) {
				subs.poll().accept(nextMatch);
			}
		}
	}

	@Override
	public void addMatch(final IMatch match) {
		String patternName = match.getPatternName();
		if (!matches.containsKey(patternName)) {
			matches.put(patternName, Collections.synchronizedSet(new HashSet<IMatch>()));
		}
		matches.get(patternName).add(match);
		
//		Check whether there are any subscribers, if not return. -> No need to track deltas.
		if(subscriptionsForAppearingMatchesOfPattern.isEmpty() && subscriptionsForDisappearingMatches.isEmpty() && subscriptionsForDisappearingMatchesOfPattern.isEmpty())
			return;
		
		if (!addedMatches.containsKey(patternName)) {
			addedMatches.put(patternName, Collections.synchronizedSet(new HashSet<IMatch>()));
		}
		addedMatches.get(patternName).add(match);
		
	}

	@Override
	public void removeMatch(final IMatch match) {
		String patternName = match.getPatternName();
		if (matches.containsKey(patternName)) {
			matches.get(patternName).remove(match);
			
//			Check whether there are any subscribers, if not return. -> No need to track deltas.
			if(subscriptionsForAppearingMatchesOfPattern.isEmpty() && subscriptionsForDisappearingMatches.isEmpty() && subscriptionsForDisappearingMatchesOfPattern.isEmpty())
				return;
			
			if (!removedMatches.containsKey(patternName)) {
				removedMatches.put(patternName, Collections.synchronizedSet(new HashSet<IMatch>()));
			}
			removedMatches.get(patternName).add(match);
			
		} else {
			throw new IllegalArgumentException("Cannot remove a match which was never added!");
		}
		
	}

	public IBeXPatternSet getPatternSet() {
		return patternSet;
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
	 * Creates an empty match for the create pattern with the given name. The
	 * created nodes are mapped to <code>null</code>.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @return the created match
	 */
	private IMatch createEmptyMatchForCreatePattern(final String patternName) {
		IBeXCreatePattern pattern = IBeXPatternUtils.getCreatePattern(ruleSet, patternName);
		if (pattern != null) {
			IMatch match = new SimpleMatch(patternName);
			pattern.getCreatedNodes().forEach(node -> match.put(node.getName(), null));
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
		if(name2Pattern.get(patternName) instanceof IBeXContextAlternatives)
			throw new IllegalArgumentException("Subscriptions to Pattern-Alternatives not supported: Invalid pattern " + name2Pattern.get(patternName));
		
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
		if(name2Pattern.get(patternName) instanceof IBeXContextAlternatives)
			throw new IllegalArgumentException("Subscriptions to Pattern-Alternatives not supported: Invalid pattern " + name2Pattern.get(patternName));
		
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
		if(name2Pattern.get(match.getPatternName()) instanceof IBeXContextAlternatives)
			throw new IllegalArgumentException("Subscriptions to Pattern-Alternatives not supported: Invalid pattern " + name2Pattern.get(match.getPatternName()));
			
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
}
