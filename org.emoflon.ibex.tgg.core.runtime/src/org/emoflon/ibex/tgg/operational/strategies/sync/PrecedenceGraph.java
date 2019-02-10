package org.emoflon.ibex.tgg.operational.strategies.sync;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.MAUtil.isFusedPatternMatch;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;

import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public class PrecedenceGraph implements IMatchContainer {
	private SYNC strategy;

	private Collection<Object> translated = cfactory.createObjectSet();;
	private Collection<Object> pendingElts = cfactory.createObjectSet();;
	private Collection<IMatch> pending = cfactory.createObjectSet();

	private Map<IMatch, Collection<Object>> requires = cfactory.createObjectToObjectHashMap();
	private Map<Object, Collection<IMatch>> requiredBy = cfactory.createObjectToObjectHashMap();
	private Map<IMatch, Collection<Object>> translates = cfactory.createObjectToObjectHashMap();
	private Map<Object, Collection<IMatch>> translatedBy = cfactory.createObjectToObjectHashMap();

	private Map<TGGRuleApplication, Collection<Object>> raToTranslated = cfactory.createObjectToObjectHashMap();
	
	private Set<IMatch> readySet = cfactory.createObjectSet();

	public PrecedenceGraph(SYNC strategy) {
		this.strategy = strategy;
	}

	@Override
	public void addMatch(IMatch match) {
		pending.add(match);
	}

	private void handleMatch(IMatch m) {
		IGreenPatternFactory gFactory = strategy.getGreenFactory(m.getRuleName());
		IGreenPattern gPattern = gFactory.create(m.getPatternName());

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
		}

		// Register edges
		for (TGGRuleEdge contextEdge : gPattern.getMarkedContextEdges()) {
			Object contextRuntimeEdge = strategy.getRuntimeEdge(m, contextEdge);

			if (!translated.contains(contextRuntimeEdge)) {
				requiredBy.computeIfAbsent(contextRuntimeEdge, (x) -> cfactory.createObjectSet());
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());

				requiredBy.get(contextRuntimeEdge).add(m);
				requires.get(m).add(contextRuntimeEdge);
			}
		}
		for (TGGRuleEdge createdEdge : gPattern.getEdgesMarkedByPattern()) {
			Object createdRuntimeEdge = strategy.getRuntimeEdge(m, createdEdge);
			translates.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(createdRuntimeEdge, (x) -> cfactory.createObjectSet());

			translates.get(m).add(createdRuntimeEdge);
			translatedBy.get(createdRuntimeEdge).add(m);
		}

		Collection<Object> dependentElts = requires.get(m);
		if (dependentElts == null || dependentElts.isEmpty())
			readySet.add(m);
	}

	private boolean anElementHasAlreadyBeenTranslated(IMatch m, IGreenPattern gPattern) {
		for (TGGRuleNode createdNode : gPattern.getNodesMarkedByPattern()) {
			Object createdObj = m.get(createdNode.getName());
			if (translated.contains(createdObj))
				return true;
		}

		for (TGGRuleEdge createdEdge : gPattern.getEdgesMarkedByPattern()) {
			Object createdRuntimeEdge = strategy.getRuntimeEdge(m, createdEdge);
			if (translated.contains(createdRuntimeEdge))
				return true;
		}

		return false;
	}
	
	private boolean noElementIsPending(IMatch m) {
		IGreenPatternFactory gFactory = strategy.getGreenFactory(m.getRuleName());
		IGreenPattern gPattern = gFactory.create(m.getPatternName());
		
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
	public void matchApplied(IMatch m) {
		if (m.getPatternName().endsWith(PatternSuffixes.CONSISTENCY)) {
			consistencyMatchApplied(m);
			return;
		}
		
		if (!translates.containsKey(m))
			return;

		Collection<Object> translatedElts = translates.get(m);
		for (Object translatedElement : translatedElts) {
			// Handle children: this parent has now been translated and can be removed
			Collection<IMatch> dependentMatches = requiredBy.remove(translatedElement);
			if (dependentMatches != null) {
				for (IMatch dependentMatch : dependentMatches) {
					Collection<Object> requiredElts = requires.get(dependentMatch);
					requiredElts.remove(translatedElement);
					if (requiredElts.isEmpty())
						readySet.add(dependentMatch);
				}
			}

			// Kill siblings
			if (translatedBy.containsKey(translatedElement)) {
				Collection<IMatch> siblings = cfactory.createObjectSet();
				siblings.addAll(translatedBy.get(translatedElement));
				siblings.remove(m);

				readySet.removeAll(siblings);
				removeMatches(siblings);
			}
		}

		translated.addAll(translatedElts);
	}
	
	private void consistencyMatchApplied(IMatch m) {
		TGGRuleApplication ra = strategy.getRuleApplicationNode(m);
		if(raToTranslated.containsKey(ra))
			return;
		
		IGreenPatternFactory gFactory = strategy.getGreenFactory(m.getRuleName());

		// Add translated elements
		Collection<Object> translatedElts = cfactory.createObjectSet();

		gFactory.getGreenSrcNodesInRule().forEach(n -> translatedElts.add(m.get(n.getName())));
		gFactory.getGreenTrgNodesInRule().forEach(n -> translatedElts.add(m.get(n.getName())));
		gFactory.getGreenSrcEdgesInRule().forEach(e -> translatedElts.add(strategy.getRuntimeEdge(m, e)));
		gFactory.getGreenTrgEdgesInRule().forEach(e -> translatedElts.add(strategy.getRuntimeEdge(m, e)));

		raToTranslated.put(ra, translatedElts);

		translated.addAll(translatedElts);
		pending.removeAll(translatedElts);
		
		for (Object translatedElement : translatedElts) {
			// Handle children: this parent has now been translated and can be removed
			Collection<IMatch> dependentMatches = requiredBy.remove(translatedElement);
			if (dependentMatches != null) {
				for (IMatch dependentMatch : dependentMatches) {
					Collection<Object> requiredElts = requires.get(dependentMatch);
					requiredElts.remove(translatedElement);
					if (requiredElts.isEmpty())
						readySet.add(dependentMatch);
				}
			}
		}
	}
	
	public void clearPendingElements() {
		pendingElts.clear();
	}

	@Override
	public Set<IMatch> getMatches() {
		Collection<IMatch> notPendingMatches = pending.stream().filter(this::noElementIsPending).collect(Collectors.toList());
		notPendingMatches.forEach(this::handleMatch);
		pending.removeAll(notPendingMatches);
		return validate(readySet);
	}
	
	private Set<IMatch> validate(Set<IMatch> readySet) {
		if(pendingElts.isEmpty())
			return readySet;
		
		Set<IMatch> filteredReadySet = cfactory.createObjectSet();
		for(IMatch m : readySet) {
			if(m.getParameterNames().stream().anyMatch(p -> pendingElts.contains(m.get(p))))
				continue;
			filteredReadySet.add(m);
		}
		return filteredReadySet;
	}
 
	@Override
	public boolean removeMatch(IMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY)) {
			return removeConsistencyMatch(match);
		}
		
		if (pending.contains(match)) {
			pending.remove(match);
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

		return true;
	}
	
	public boolean removeConsistencyMatch(IMatch m) {
		// Transfer elements to the pending collection
		TGGRuleApplication ra = strategy.getRuleApplicationNode(m);
		if(!raToTranslated.containsKey(ra))
			return true;
		
		translated.removeAll(raToTranslated.get(ra));
		pendingElts.addAll(raToTranslated.remove(ra));
		
		return true;
	}

	@Override
	public void removeMatches(Collection<IMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	@Override
	public IMatch getNextKernel() {
		return getMatches().stream()//
				.filter(m -> strategy.isComplementMatch(m.getRuleName()))//
				.findAny()//
				.get();
	}

	@Override
	public String getRuleName(IMatch match) {
		if (isFusedPatternMatch(match.getPatternName()))
			return match.getPatternName();
		return match.getRuleName();
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
