package org.emoflon.ibex.tgg.runtime.matches.container;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;

import TGGRuntimeModel.TGGRuleApplication;


public class BrokenMatchContainer implements IMatchContainer, TimeMeasurable {
	
	protected final Times times = new Times();

	protected PropagatingOperationalStrategy strategy;
	protected RuleHandler ruleHandler;

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
		TimeRegistry.register(this);
		ruleHandler = strategy.getOptions().tgg.ruleHandler();
	}
	
	public void reset() {
		translated = cfactory.createObjectSet();
		pendingElts = cfactory.createObjectSet();
		pending = cfactory.createObjectSet();

		requires = cfactory.createObjectToObjectHashMap();
		requiredBy = cfactory.createObjectToObjectHashMap();
		translates = cfactory.createObjectToObjectHashMap();
		translatedBy = cfactory.createObjectToObjectHashMap();

		raToTranslated = cfactory.createObjectToObjectHashMap();
		raToMatch = cfactory.createObjectToObjectHashMap();
		
		readySet = cfactory.createObjectSet();
	}

	@Override
	public void addMatch(ITGGMatch match) {
		Timer.start();
		
		pending.add(match);
		
		times.addTo("addMatch", Timer.stop());
	}
	
	@Override
	public ITGGMatch getNext() {
		Timer.start();
		
		Timer.start();
		Set<ITGGMatch> matches = getMatches();
		times.addTo("getNext:getmatches", Timer.stop());
		
//		Timer.start();
//		Iterator<ITGGMatch> iterator = matches.iterator();
//		times.addTo("getNext:iterator", Timer.stop());
//		
//		Timer.start();
//		ITGGMatch next = iterator.next();
//		times.addTo("getNext:next", Timer.stop());
		Timer.start();
		for(ITGGMatch next : matches) {
			times.addTo("getNext:next", Timer.stop());

			times.addTo("getNext", Timer.stop());
			return next;
		}
		
		return null;
		
//		return next;
	}

	private void handleMatch(ITGGMatch m) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(m.getRuleName(), OperationalisationMode.CONSISTENCY_CHECK);
		
		if (anElementHasAlreadyBeenTranslated(m, operationalRule))
			return;
		
		// Register nodes
		for (IBeXNode contextNode : operationalRule.getAlreadyMarked().getNodes()) {
			Object contextObj = m.get(contextNode.getName());

			if (!translated.contains(contextObj)) {
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
				requiredBy.computeIfAbsent(contextObj, (x) -> cfactory.createObjectSet());

				requires.get(m).add(contextObj);
				requiredBy.get(contextObj).add(m);
			}
		}
		for (IBeXNode createdNode : operationalRule.getToBeMarked().getNodes()) {
			Object createdObj = m.get(createdNode.getName());

			translates.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(createdObj, (x) -> cfactory.createObjectSet());

			translates.get(m).add(createdObj);
			translatedBy.get(createdObj).add(m);
			
			if(requiredBy.containsKey(createdObj))
				requiredBy.get(createdObj).forEach(match -> readySet.remove(match));
		}

		// Register edges
		for (IBeXEdge contextEdge : operationalRule.getAlreadyMarked().getEdges()) {
			Object contextRuntimeEdge = getRuntimeEdge(m, contextEdge);

			if (!translated.contains(contextRuntimeEdge)) {
				requiredBy.computeIfAbsent(contextRuntimeEdge, (x) -> cfactory.createObjectSet());
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());

				requiredBy.get(contextRuntimeEdge).add(m);
				requires.get(m).add(contextRuntimeEdge);
			}
		}
		for (IBeXEdge createdEdge : operationalRule.getToBeMarked().getEdges()) {
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

	private boolean anElementHasAlreadyBeenTranslated(ITGGMatch m, TGGOperationalRule operationalRule) {
		for (IBeXNode createdNode : operationalRule.getToBeMarked().getNodes()) {
			Object createdObj = m.get(createdNode.getName());
			if (translated.contains(createdObj))
				return true;
		}

		for (IBeXEdge createdEdge : operationalRule.getToBeMarked().getEdges()) {
			Object createdRuntimeEdge = getRuntimeEdge(m, createdEdge);
			if (translated.contains(createdRuntimeEdge))
				return true;
		}

		return false;
	}
	
	private boolean noElementIsPending(ITGGMatch m) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(m.getOperationalRuleName());
		
		for (IBeXNode createdNode : operationalRule.getToBeMarked().getNodes()) {
			Object createdObj = m.get(createdNode.getName());
			if (pendingElts.contains(createdObj))
				return false;
		}
		
		for (IBeXNode contextNode : operationalRule.getAlreadyMarked().getNodes()) {
			Object contextObj = m.get(contextNode.getName());
			if (pendingElts.contains(contextObj))
				return false;
		}

		return true;
	}

	@Override
	public void matchApplied(ITGGMatch m) {
		Timer.start();
		
		readySet.remove(m);
		
		if (!translates.containsKey(m)) {
			times.addTo("matchApplied", Timer.stop());
			return;
		}

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
		
		times.addTo("matchApplied", Timer.stop());
	}
	
	public void clearPendingElements() {
		pendingElts.clear();
	}

	@Override
	public Set<ITGGMatch> getMatches() {
		Timer.start();
		
		if(!pending.isEmpty()) {
			Timer.start();
			Collection<ITGGMatch> notPendingMatches = pending.parallelStream().filter(this::noElementIsPending).collect(Collectors.toSet());
			times.addTo("getMatches:elementPending", Timer.stop());
			notPendingMatches.forEach(this::handleMatch);
			if(notPendingMatches.size() == pending.size())
				pending.clear();
			else
				pending.removeAll(notPendingMatches);
		}
		Set<ITGGMatch> validate = validate(readySet);
		
		times.addTo("getMatches", Timer.stop());
		return validate;
	}
	
	private Set<ITGGMatch> validate(Set<ITGGMatch> readySet) {
		if(pendingElts.isEmpty())
			return readySet;
		
//		Set<ITGGMatch> filteredReadySet = cfactory.createObjectSet();
//		for(ITGGMatch m : readySet) {
//			if(m.getParameterNames().stream().anyMatch(p -> pendingElts.contains(m.get(p))))
//				continue;
//			filteredReadySet.add(m);
//		}
		
		return readySet.parallelStream().filter(m -> !m.getParameterNames().stream().anyMatch(p -> pendingElts.contains(m.get(p)))).collect(Collectors.toCollection(() -> new LinkedHashSet<>()));
	}
 
	@Override
	public boolean removeMatch(ITGGMatch match) {
		Timer.start();
		
		if (match.getType() == PatternType.CONSISTENCY) {
			boolean removed = removeConsistencyMatch(match);
			
			times.addTo("removeMatch", Timer.stop());
			return removed;
		}
		
		if (pending.contains(match)) {
			pending.remove(match);
			
			times.addTo("removeMatch", Timer.stop());
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
		
		times.addTo("removeMatch", Timer.stop());
		return true;
	}
	
	public boolean removeConsistencyMatch(ITGGMatch m) {
		// Transfer elements to the pending collection
		TGGRuleApplication ra = m.getRuleApplicationNode();
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

	@Override
	public Times getTimes() {
		return times;
	}

}
