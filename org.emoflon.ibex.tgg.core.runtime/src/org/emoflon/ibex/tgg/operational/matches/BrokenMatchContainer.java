package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.jdk.JDKCollectionFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggingMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;


public class BrokenMatchContainer extends LoggingMatchContainer implements IMatchContainer {
	public static final CollectionFactory cfactory = new JDKCollectionFactory();

	protected PropagatingOperationalStrategy strategy;

	protected Collection<Object> translated = cfactory.createObjectSet();
	protected Collection<Object> pendingElts = cfactory.createObjectSet();
	protected Collection<ITGGMatch> pending = cfactory.createObjectSet();

	protected Map<ITGGMatch, Collection<Object>> requires = cfactory.createObjectToObjectHashMap();
	protected Map<Object, Collection<ITGGMatch>> requiredBy = cfactory.createObjectToObjectHashMap();
	protected Map<ITGGMatch, Collection<Object>> translates = cfactory.createObjectToObjectHashMap();
	protected Map<Object, Collection<ITGGMatch>> translatedBy = cfactory.createObjectToObjectHashMap();

	protected Map<TGGRuleApplication, Collection<Object>> raToTranslated = cfactory.createObjectToObjectHashMap();
	protected Map<TGGRuleApplication, ITGGMatch> raToMatch = cfactory.createObjectToObjectHashMap();
	
	protected Set<ITGGMatch> readySet = cfactory.createObjectSet();

	public BrokenMatchContainer(PropagatingOperationalStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public void addMatch(ITGGMatch match) {
		long tic = System.nanoTime();
		pending.add(match);
		addMatchTime += System.nanoTime() - tic;
	}

	private void handleMatch(ITGGMatch m) {
		IGreenPatternFactory gFactory = strategy.getGreenFactory(m.getRuleName());
		IGreenPattern gPattern = gFactory.create(PatternType.CC);

		if (anElementHasAlreadyBeenTranslated(m, gPattern))
			return;
		
		// Register nodes
		for (TGGRuleNode contextNode : gPattern.getMarkedContextNodes()) {
			Object contextObj = m.get(contextNode.getName());

			if (!translated.contains(contextObj)) {
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
				requiredBy.computeIfAbsent(contextObj, (x) -> cfactory.createObjectSet());

				requires.get(m).add(contextObj);
				requiredBy.get(contextObj).add(m);
			}
		}
		for (TGGRuleNode createdNode : gPattern.getNodesMarkedByPattern()) {
			Object createdObj = m.get(createdNode.getName());

			translates.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(createdObj, (x) -> cfactory.createObjectSet());

			translates.get(m).add(createdObj);
			translatedBy.get(createdObj).add(m);
			
			if(requiredBy.containsKey(createdObj))
				requiredBy.get(createdObj).forEach(match -> readySet.remove(match));
		}

		// Register edges
		for (TGGRuleEdge contextEdge : gPattern.getMarkedContextEdges()) {
			Object contextRuntimeEdge = getRuntimeEdge(m, contextEdge);

			if (!translated.contains(contextRuntimeEdge)) {
				requiredBy.computeIfAbsent(contextRuntimeEdge, (x) -> cfactory.createObjectSet());
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());

				requiredBy.get(contextRuntimeEdge).add(m);
				requires.get(m).add(contextRuntimeEdge);
			}
		}
		for (TGGRuleEdge createdEdge : gPattern.getEdgesMarkedByPattern()) {
			Object createdRuntimeEdge = getRuntimeEdge(m, createdEdge);
			translates.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(createdRuntimeEdge, (x) -> cfactory.createObjectSet());

			translates.get(m).add(createdRuntimeEdge);
			translatedBy.get(createdRuntimeEdge).add(m);
			
			if(requiredBy.containsKey(createdRuntimeEdge))
				requiredBy.get(createdRuntimeEdge).forEach(match -> readySet.remove(match));
		}

		Collection<Object> dependentElts = requires.get(m);
		if (dependentElts == null || dependentElts.isEmpty())
			readySet.add(m);
		else {
			// check if all elements have contained in one match!
			boolean foundNoDependentMatch = dependentElts.stream().allMatch(e -> !translatedBy.containsKey(e) || translatedBy.get(e).isEmpty());
			if(foundNoDependentMatch)
				readySet.add(m);
		}
	}

	private boolean anElementHasAlreadyBeenTranslated(ITGGMatch m, IGreenPattern gPattern) {
		for (TGGRuleNode createdNode : gPattern.getNodesMarkedByPattern()) {
			Object createdObj = m.get(createdNode.getName());
			if (translated.contains(createdObj))
				return true;
		}

		for (TGGRuleEdge createdEdge : gPattern.getEdgesMarkedByPattern()) {
			Object createdRuntimeEdge = getRuntimeEdge(m, createdEdge);
			if (translated.contains(createdRuntimeEdge))
				return true;
		}

		return false;
	}
	
	private boolean noElementIsPending(ITGGMatch m) {
		IGreenPatternFactory gFactory = strategy.getGreenFactory(m.getRuleName());
		IGreenPattern gPattern = gFactory.create(m.getType());
		
		for (TGGRuleNode createdNode : gPattern.getNodesMarkedByPattern()) {
			Object createdObj = m.get(createdNode.getName());
			if (pendingElts.contains(createdObj))
				return false;
		}
		
		for (TGGRuleNode contextNode : gPattern.getMarkedContextNodes()) {
			Object contextObj = m.get(contextNode.getName());
			if (pendingElts.contains(contextObj))
				return false;
		}

		return true;
	}

	@Override
	public void matchApplied(ITGGMatch m) {
		long tic = System.nanoTime();
		
		readySet.remove(m);
		
		if (!translates.containsKey(m))
			return;

		Collection<Object> translatedElts = translates.get(m);
		for (Object translatedElement : translatedElts) {
			// Handle children: this parent has now been translated and can be removed
			Collection<ITGGMatch> dependentMatches = requiredBy.remove(translatedElement);
			if (dependentMatches != null) {
				for (ITGGMatch dependentMatch : dependentMatches) {
					Collection<Object> requiredElts = requires.get(dependentMatch);
					requiredElts.remove(translatedElement);
					if (requiredElts.isEmpty())
						readySet.add(dependentMatch);
					else {
						// check if all elements have contained in one match!
						// TODO lfritsche, amoeller: hopefully we do not have too many matches...
						boolean foundNoDependentMatch = requiredElts.stream().allMatch(e -> !translatedBy.containsKey(e) || translatedBy.get(e).isEmpty());
						if(foundNoDependentMatch)
							readySet.add(dependentMatch); 
					}
				}
			}

			// Kill siblings
			if (translatedBy.containsKey(translatedElement)) {
				Collection<ITGGMatch> siblings = cfactory.createObjectSet();
				siblings.addAll(translatedBy.get(translatedElement));
				siblings.remove(m);

				readySet.removeAll(siblings);
				removeMatches(siblings);
			}
		}

		translated.addAll(translatedElts);
		
		matchAppliedTime += System.nanoTime() - tic;
	}
	
	public void clearPendingElements() {
		pendingElts.clear();
	}

	@Override
	public Set<ITGGMatch> getMatches() {
		long tic = System.nanoTime();
		Collection<ITGGMatch> notPendingMatches = pending.parallelStream().filter(this::noElementIsPending).collect(Collectors.toList());
		notPendingMatches.forEach(this::handleMatch);
		if(notPendingMatches.size() == pending.size())
			pending.clear();
		else
			pending.removeAll(notPendingMatches);
		Set<ITGGMatch> validate = validate(readySet);
		getMatchTime += System.nanoTime() - tic;
		return validate;
	}
	
	private Set<ITGGMatch> validate(Set<ITGGMatch> readySet) {
		if(pendingElts.isEmpty())
			return readySet;
		
		Set<ITGGMatch> filteredReadySet = cfactory.createObjectSet();
		for(ITGGMatch m : readySet) {
			if(m.getParameterNames().stream().anyMatch(p -> pendingElts.contains(m.get(p))))
				continue;
			filteredReadySet.add(m);
		}
		return filteredReadySet;
	}
 
	@Override
	public boolean removeMatch(ITGGMatch match) {
		long tic = System.nanoTime();
		
		if (match.getType() == PatternType.CONSISTENCY) {
			boolean removed = removeConsistencyMatch(match);
			
			removeMatchTime += System.nanoTime() - tic;
			return removed;
		}
		
		if (pending.contains(match)) {
			pending.remove(match);
			
			removeMatchTime += System.nanoTime() - tic;
			return true;
		}

		Collection<Object> dependentObjects = requires.remove(match);
		if (dependentObjects != null) {
			for (Object o : dependentObjects) {
				if (requiredBy.containsKey(o))
					requiredBy.get(o).remove(match);
			}
		}

		Collection<Object> translatedElements = translates.remove(match);
		if (translatedElements != null) {
			for (Object o : translatedElements) {
				if (translatedBy.containsKey(o)) {
					translatedBy.get(o).remove(match);
					if (translatedBy.get(o).isEmpty())
						translatedBy.remove(o);
				}
			}
		}

		readySet.remove(match);
		
		removeMatchTime += System.nanoTime() - tic;
		return true;
	}
	
	public boolean removeConsistencyMatch(ITGGMatch m) {
		// Transfer elements to the pending collection
		TGGRuleApplication ra = strategy.getRuleApplicationNode(m);
		if(!raToTranslated.containsKey(ra))
			return true;
		
		translated.removeAll(raToTranslated.get(ra));
		pendingElts.addAll(raToTranslated.remove(ra));
		raToMatch.remove(ra);
		
		return true;
	}

	@Override
	public void removeMatches(Collection<ITGGMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	@Override
	public void removeAllMatches() {
		translatedBy.clear();
		requiredBy.clear();
		requires.clear();
		translates.clear();

		readySet.clear();
	}

}
