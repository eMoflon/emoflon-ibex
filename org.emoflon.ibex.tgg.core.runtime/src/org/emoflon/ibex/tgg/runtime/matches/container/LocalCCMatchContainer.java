package org.emoflon.ibex.tgg.runtime.matches.container;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

public class LocalCCMatchContainer implements IMatchContainer{
	private Set<Object> markedElements = new HashSet<>();
	private Set<Object> markedEdges = new HashSet<>();
	private Map<Object, Collection<ITGGMatch>> elt2ccMatches = new HashMap<>();
	private Set<ITGGMatch> consistencyMatches = new HashSet<>();
	private Set<ITGGMatch> unprocessedConsistencyMatches = new HashSet<>();
	private Set<ITGGMatch> unprocessedCCMatches = new HashSet<>();
	private Set<ITGGMatch> ccMatches = new HashSet<>();
	private Set<ITGGMatch> invalidCCMatches = new HashSet<>();
	boolean initFinished = false;
	private IbexGreenInterpreter interpreter;
	private IbexOptions options;
	private RuleHandler ruleHandler;
	
	private LocalCCMatchContainer(LocalCCMatchContainer old) {
		this(old.options, old.interpreter);
		this.consistencyMatches.addAll(old.consistencyMatches);
		this.ccMatches.addAll(old.ccMatches);
		this.markedElements.addAll(old.markedElements);
		this.invalidCCMatches.addAll(old.invalidCCMatches);
		this.elt2ccMatches.putAll(old.elt2ccMatches);
	}
	
	public LocalCCMatchContainer(IbexOptions options, IbexGreenInterpreter interpreter) {
		this.options = options;
		this.interpreter = interpreter;
		ruleHandler = options.tgg.ruleHandler();
	}

	public void addMatch(ITGGMatch match) {
		switch (match.getType()) {
			case CONSISTENCY -> {
				if (!initFinished)
					addConsistencyMatch(match);
				else
					unprocessedConsistencyMatches.add(match);
				return;
			}
			case CC, GENForCC -> {
				unprocessedCCMatches.add(match);
			}
			default -> {
			}
		}
	}

	private void addConsistencyMatch(ITGGMatch match) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());
		
		for(IBeXNode node : operationalRule.getToBeMarked().getNodes()) {
			Object object = match.get(node.getName());
			markedElements.add(object);
		}
		for(IBeXEdge edge : operationalRule.getToBeMarked().getEdges()) {
			markedEdges.add(getRuntimeEdge(match, edge));
		}
		
		consistencyMatches.add(match);
	}
	
	private boolean removeConsistencyMatch(ITGGMatch match) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());

		for(IBeXNode node : operationalRule.getToBeMarked().getNodes()) {
			Object object = match.get(node.getName());
			markedElements.remove(object);
		}
		for(IBeXEdge edge : operationalRule.getToBeMarked().getEdges()) {
			markedEdges.remove(getRuntimeEdge(match, edge));
		}
		
		return consistencyMatches.remove(match);
	}

	private void addCCMatch(ITGGMatch match) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());
		
		for(IBeXNode node : operationalRule.getToBeMarked().getNodes()) {
			Object object = match.get(node.getName());
			if(markedElements.contains(object)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		for(IBeXEdge edge : operationalRule.getToBeMarked().getEdges()) {
			EMFEdge emfEdge = getRuntimeEdge(match, edge);
			if(markedEdges.contains(emfEdge)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		ccMatches.add(match);
	}

	public boolean removeMatch(ITGGMatch match) {
		switch (match.getType()) {
			case CONSISTENCY -> {
				unprocessedConsistencyMatches.remove(match);
				return removeConsistencyMatch(match);
			}
			case CC, GENForCC -> {
				ccMatches.remove(match);
				invalidCCMatches.remove(match);
				return unprocessedCCMatches.remove(match);
			}
			default -> {
				return false;
			}
		}
	}

	public void removeMatches(Collection<ITGGMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	public Set<ITGGMatch> getMatches() {
		initFinished = true;
		unprocessedCCMatches.forEach(this::addCCMatch);
		unprocessedCCMatches.clear();
		return ccMatches;
	}

	public void removeAllMatches() {
		markedElements.clear();
		consistencyMatches.clear();
		ccMatches.clear();
	}

	public void matchApplied(ITGGMatch m) {
		// Default: do nothing
		ccMatches.remove(m);
	}

	public IMatchContainer copy() {
		return new LocalCCMatchContainer(this);
	}
	
	public boolean isMarked(Object obj) {
		return markedElements.contains(obj);
	}

	public boolean isEdgeMarked(EMFEdge e) {
		return markedEdges.contains(e);
	}
	
	protected Set<EMFEdge> getGreenEdges(final ITGGMatch match) {
		Set<EMFEdge> result = cfactory.createEMFEdgeHashSet();
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());
		result.addAll(interpreter.createEdges(match, operationalRule.getCreateSource().getEdges(), false));
		result.addAll(interpreter.createEdges(match, operationalRule.getCreateTarget().getEdges(), false));
		return result;
	}
}
