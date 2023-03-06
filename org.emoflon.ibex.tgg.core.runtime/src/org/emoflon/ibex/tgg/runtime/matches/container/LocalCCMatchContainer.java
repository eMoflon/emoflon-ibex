package org.emoflon.ibex.tgg.runtime.matches.container;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

import language.TGGRuleEdge;
import language.TGGRuleNode;

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
	
	private LocalCCMatchContainer(LocalCCMatchContainer old) {
		this(old.options, old.interpreter);
		this.options = old.options;
		this.consistencyMatches.addAll(old.consistencyMatches);
		this.ccMatches.addAll(old.ccMatches);
		this.markedElements.addAll(old.markedElements);
		this.invalidCCMatches.addAll(old.invalidCCMatches);
		this.elt2ccMatches.putAll(old.elt2ccMatches);
	}
	
	public LocalCCMatchContainer(IbexOptions options, IbexGreenInterpreter interpreter) {
		this.options = options;
		this.interpreter = interpreter;
	}

	public void addMatch(ITGGMatch match) {
		if (!rule2pattern.containsKey(match.getRuleName())) {
			IGreenPatternFactory factory = new GreenPatternFactory(options, match.getRuleName());
			rule2factory.put(match.getRuleName(), factory);
			rule2pattern.put(match.getRuleName(), factory.create(PatternType.GEN));
		}

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
		if(!rule2pattern.containsKey(match.getRuleName())) {
			IGreenPatternFactory factory = new GreenPatternFactory(options, match.getRuleName());
			rule2pattern.put(match.getRuleName(), factory.create(PatternType.GEN));
		}
		
		IGreenPattern greenPattern = rule2pattern.get(match.getRuleName());
		for(TGGNode srcNode : greenPattern.getSrcNodes()) {
			Object srcObject = match.get(srcNode.getName());
			markedElements.add(srcObject);
		}
		for(TGGNode corrNode : greenPattern.getCorrNodes()) {
			Object corrObject = match.get(corrNode.getName());
			markedElements.add(corrObject);
		}
		for(TGGNode trgNode : greenPattern.getTrgNodes()) {
			Object trgObject = match.get(trgNode.getName());
			markedElements.add(trgObject);
		}
		for(TGGEdge edge : greenPattern.getSrcEdges()) {
			markedEdges.add(getRuntimeEdge(match, edge));
		}
		for(TGGEdge edge : greenPattern.getTrgEdges()) {
			markedEdges.add(getRuntimeEdge(match, edge));
		}
		
		consistencyMatches.add(match);
	}
	
	private boolean removeConsistencyMatch(ITGGMatch match) {
		if(!rule2pattern.containsKey(match.getRuleName())) {
			IGreenPatternFactory factory = new GreenPatternFactory(options, match.getRuleName());
			rule2pattern.put(match.getRuleName(), factory.create(PatternType.GEN));
		}
		
		IGreenPattern greenPattern = rule2pattern.get(match.getRuleName());
		for(TGGNode srcNode : greenPattern.getSrcNodes()) {
			Object srcObject = match.get(srcNode.getName());
			markedElements.remove(srcObject);
		}
		for(TGGNode corrNode : greenPattern.getCorrNodes()) {
			Object corrObject = match.get(corrNode.getName());
			markedElements.remove(corrObject);
		}
		for(TGGNode trgNode : greenPattern.getTrgNodes()) {
			Object trgObject = match.get(trgNode.getName());
			markedElements.remove(trgObject);
		}
		for(TGGEdge edge : greenPattern.getSrcEdges()) {
			markedEdges.remove(getRuntimeEdge(match, edge));
		}
		for(TGGEdge edge : greenPattern.getTrgEdges()) {
			markedEdges.remove(getRuntimeEdge(match, edge));
		}
		
		return consistencyMatches.remove(match);
	}

	private void addCCMatch(ITGGMatch match) {
		IGreenPattern greenPattern = rule2pattern.get(match.getRuleName());
		for(TGGNode srcNode : greenPattern.getSrcNodes()) {
			Object srcObject = match.get(srcNode.getName());
			if(markedElements.contains(srcObject)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		for(TGGNode corrNode : greenPattern.getCorrNodes()) {
			Object corrObject = match.get(corrNode.getName());
			if(markedElements.contains(corrObject)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		for(TGGNode trgNode : greenPattern.getTrgNodes()) {
			Object trgObject = match.get(trgNode.getName());
			if(markedElements.contains(trgObject)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		for(TGGEdge srcEdge : greenPattern.getSrcEdges()) {
			EMFEdge emfEdge = getRuntimeEdge(match, srcEdge);
			if(markedEdges.contains(emfEdge)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		for(TGGEdge trgEdge : greenPattern.getTrgEdges()) {
			EMFEdge emfEdge = getRuntimeEdge(match, trgEdge);
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
		TGGOperationalRule operationalRule = options.
		result.addAll(interpreter.createEdges(match, rule2factory.get(match.getRuleName()).getGreenSrcEdgesInRule(), false));
		result.addAll(interpreter.createEdges(match, rule2factory.get(match.getRuleName()).getGreenTrgEdgesInRule(), false));
		return result;
	}
}
