package org.emoflon.ibex.tgg.runtime.matches.container;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

public class LocalCCMatchContainer implements IMatchContainer {
	private Set<Object> markedElements = new HashSet<>();
	private Set<Object> markedEdges = new HashSet<>();
	private Map<Object, List<ITGGMatch>> elt2ccMatches = new HashMap<>();
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
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), OperationalisationMode.GENERATE);

		for (IBeXNode node : operationalRule.getCreate().getNodes()) {
			Object object = match.get(node.getName());
			markedElements.add(object);

			var ccMatches = elt2ccMatches.get(object);
			if (ccMatches != null) {
				while (!ccMatches.isEmpty()) {
					var ccMatch = ccMatches.get(0);
					removeCCMatch(ccMatch);
					addMatch(ccMatch);
				}
			}
		}
		for (IBeXEdge edge : operationalRule.getCreateSourceAndTarget().getEdges()) {
			markedEdges.add(getRuntimeEdge(match, edge));

			var ccMatches = elt2ccMatches.get(edge);
			if (ccMatches != null) {
				while (!ccMatches.isEmpty()) {
					var ccMatch = ccMatches.get(0);
					removeCCMatch(ccMatch);
					addMatch(ccMatch);
				}
			}
		}

		consistencyMatches.add(match);
	}

	private boolean removeConsistencyMatch(ITGGMatch match) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), OperationalisationMode.GENERATE);

		for (IBeXNode node : operationalRule.getCreate().getNodes()) {
			Object object = match.get(node.getName());
			markedElements.remove(object);

			// in case that there is no Consistency match that blocks the current CC match,
			// we add it anew
			var ccMatches = elt2ccMatches.get(object);
			if (ccMatches != null) {
				while (!ccMatches.isEmpty()) {
					var ccMatch = ccMatches.get(0);
					if (removeCCMatch(ccMatch))
						addMatch(ccMatch);
				}
			}
		}
		for (IBeXEdge edge : operationalRule.getCreateSourceAndTarget().getEdges()) {
			markedEdges.remove(getRuntimeEdge(match, edge));

			// in case that there is no Consistency match that blocks the current CC match,
			// we add it anew
			var ccMatches = elt2ccMatches.get(edge);
			if (ccMatches != null) {
				if (ccMatches != null) {
					while (!ccMatches.isEmpty()) {
						var ccMatch = ccMatches.get(0);
						if (removeCCMatch(ccMatch))
							addMatch(ccMatch);
					}
				}
			}
		}

		return consistencyMatches.remove(match);
	}

	private void addCCMatch(ITGGMatch match) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), OperationalisationMode.GENERATE);

		boolean matchInvalid = false;

		for (IBeXNode node : operationalRule.getCreate().getNodes()) {
			Object object = match.get(node.getName());
			elt2ccMatches.computeIfAbsent(object, (a) -> new LinkedList<>()).add(match);
			if (!matchInvalid && markedElements.contains(object)) {
				invalidCCMatches.add(match);
				matchInvalid = true;
			}
		}
		for (IBeXEdge edge : operationalRule.getCreateSourceAndTarget().getEdges()) {
			EMFEdge emfEdge = getRuntimeEdge(match, edge);
			elt2ccMatches.computeIfAbsent(emfEdge, (a) -> new LinkedList<>()).add(match);
			if (!matchInvalid && markedEdges.contains(emfEdge)) {
				invalidCCMatches.add(match);
				matchInvalid = true;
			}
		}
		if (!matchInvalid)
			ccMatches.add(match);
	}

	public boolean removeMatch(ITGGMatch match) {
		switch (match.getType()) {
		case CONSISTENCY -> {
			unprocessedConsistencyMatches.remove(match);
			return removeConsistencyMatch(match);
		}
		case CC, GENForCC -> {
			return removeCCMatch(match);
		}
		default -> {
			return false;
		}
		}
	}

	private boolean removeCCMatch(ITGGMatch match) {
		if (unprocessedCCMatches.remove(match))
			return true;

		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), OperationalisationMode.GENERATE);

		for (IBeXNode node : operationalRule.getCreate().getNodes()) {
			Object object = match.get(node.getName());
			var matches = elt2ccMatches.get(object);
			if (matches == null)
				continue;
			matches.remove(match);
			if (matches.isEmpty()) {
				elt2ccMatches.remove(object);
			}
		}
		for (IBeXEdge edge : operationalRule.getCreateSourceAndTarget().getEdges()) {
			EMFEdge emfEdge = getRuntimeEdge(match, edge);
			var matches = elt2ccMatches.get(emfEdge);
			if (matches == null)
				continue;
			matches.remove(match);
			if (matches.isEmpty()) {
				elt2ccMatches.remove(emfEdge);
			}
		}

		invalidCCMatches.remove(match);
		ccMatches.remove(match);
		return true;
	}

	public void removeMatches(Collection<ITGGMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	public Set<ITGGMatch> getMatches() {
		initFinished = true;

		unprocessedConsistencyMatches.forEach(this::addConsistencyMatch);
		unprocessedConsistencyMatches.clear();

		unprocessedCCMatches.forEach(this::addCCMatch);
		unprocessedCCMatches.clear();
		return ccMatches;
	}

	public void removeAllMatches() {
		markedElements.clear();
		markedEdges.clear();

		consistencyMatches.clear();
		ccMatches.clear();
		invalidCCMatches.clear();

		unprocessedCCMatches.clear();
		unprocessedConsistencyMatches.clear();

		elt2ccMatches.clear();
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
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), OperationalisationMode.GENERATE);
		result.addAll(interpreter.createEdges(match, operationalRule.getCreateSource().getEdges(), false));
		result.addAll(interpreter.createEdges(match, operationalRule.getCreateTarget().getEdges(), false));
		return result;
	}
}
