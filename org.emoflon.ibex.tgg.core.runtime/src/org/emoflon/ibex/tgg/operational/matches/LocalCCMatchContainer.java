package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;

import language.TGG;
import language.TGGRuleNode;

public class LocalCCMatchContainer implements IMatchContainer{
	private TGG tgg;
	private Set<Object> markedElements = new HashSet<>();
	private Map<Object, Collection<ITGGMatch>> elt2ccMatches = new HashMap<>();
	private Set<ITGGMatch> consistencyMatches = new HashSet<>();
	private Set<ITGGMatch> unprocessedConsistencyMatches = new HashSet<>();
	private Set<ITGGMatch> unprocessedCCMatches = new HashSet<>();
	private Set<ITGGMatch> ccMatches = new HashSet<>();
	private Set<ITGGMatch> invalidCCMatches = new HashSet<>();
	private Map<String, IGreenPatternFactory> rule2factory = new HashMap<>();
	private Map<String, IGreenPattern> rule2pattern = new HashMap<>();
	boolean initFinished = false;
	private Set<Object> markedEdges = new HashSet<>();
	private IbexGreenInterpreter interpreter;
	
	private LocalCCMatchContainer(LocalCCMatchContainer old) {
		this(old.tgg, old.interpreter);
		this.consistencyMatches.addAll(old.consistencyMatches);
		this.ccMatches.addAll(old.ccMatches);
		this.markedElements.addAll(old.markedElements);
		this.invalidCCMatches.addAll(old.invalidCCMatches);
		this.elt2ccMatches.putAll(old.elt2ccMatches);
	}
	
	public LocalCCMatchContainer(TGG tgg, IbexGreenInterpreter interpreter) {
		this.tgg = tgg;
		this.interpreter = interpreter;
	}

	public void addMatch(ITGGMatch match) {
		if(!rule2pattern.containsKey(match.getRuleName())) {
			IGreenPatternFactory factory = new GreenPatternFactory(match.getRuleName());
			rule2factory.put(match.getRuleName(), factory);
			rule2pattern.put(match.getRuleName(), factory.create(PatternType.GEN));
		}
		
		switch(match.getType()) {
		case CONSISTENCY: 
			if(initFinished)
				addConsistencyMatch(match);
			else 
				unprocessedConsistencyMatches.add(match);
			return;
		case CC:
		case GENForCC:
			unprocessedCCMatches.add(match);
		default:
			break;
		}
	}

	private void addConsistencyMatch(ITGGMatch match) {
		if(!rule2pattern.containsKey(match.getRuleName())) {
			IGreenPatternFactory factory = new GreenPatternFactory(match.getRuleName());
			rule2pattern.put(match.getRuleName(), factory.create(PatternType.GEN));
		}
		
		IGreenPattern greenPattern = rule2pattern.get(match.getRuleName());
		for(TGGRuleNode srcNode : greenPattern.getSrcNodes()) {
			Object srcObject = match.get(srcNode.getName());
			markedElements.add(srcObject);
		}
		for(TGGRuleNode trgNode : greenPattern.getTrgNodes()) {
			Object trgObject = match.get(trgNode.getName());
			markedElements.add(trgObject);
		}
		markedEdges.addAll(getGreenEdges(match));
		
		consistencyMatches.add(match);
	}
	
	private boolean removeConsistencyMatch(ITGGMatch match) {
		if(!rule2pattern.containsKey(match.getRuleName())) {
			IGreenPatternFactory factory = new GreenPatternFactory(match.getRuleName());
			rule2pattern.put(match.getRuleName(), factory.create(PatternType.GEN));
		}
		
		IGreenPattern greenPattern = rule2pattern.get(match.getRuleName());
		for(TGGRuleNode srcNode : greenPattern.getSrcNodes()) {
			Object srcObject = match.get(srcNode.getName());
			markedElements.remove(srcObject);
		}
		for(TGGRuleNode trgNode : greenPattern.getTrgNodes()) {
			Object trgObject = match.get(trgNode.getName());
			markedElements.remove(trgObject);
		}
		markedEdges.removeAll(match.getCreatedEdges());
		
		return consistencyMatches.remove(match);
	}

	private void addCCMatch(ITGGMatch match) {
		IGreenPattern greenPattern = rule2pattern.get(match.getRuleName());
		for(TGGRuleNode srcNode : greenPattern.getSrcNodes()) {
			Object srcObject = match.get(srcNode.getName());
			if(markedElements.contains(srcObject)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		for(TGGRuleNode trgNode : greenPattern.getTrgNodes()) {
			Object trgObject = match.get(trgNode.getName());
			markedElements.add(trgObject);
			if(markedElements.contains(trgObject)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		for(EMFEdge edge : getGreenEdges(match)) {
			if(markedEdges.contains(edge)) {
				invalidCCMatches.add(match);
				return;
			}
		}
		ccMatches.add(match);
	}

	public boolean removeMatch(ITGGMatch match) {
		switch(match.getType()) {
		case CONSISTENCY: 
			unprocessedConsistencyMatches.remove(match);
			return removeConsistencyMatch(match);
		case CC:
		case GENForCC: 
			ccMatches.remove(match);
			invalidCCMatches.remove(match);
			return unprocessedCCMatches.remove(match);
		default:
			return false;
		}
	}

	public void removeMatches(Collection<ITGGMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	public Set<ITGGMatch> getMatches() {
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
		result.addAll(interpreter.createEdges(match, rule2factory.get(match.getRuleName()).getGreenSrcEdgesInRule(), false));
		result.addAll(interpreter.createEdges(match, rule2factory.get(match.getRuleName()).getGreenTrgEdgesInRule(), false));
		return result;
	}
}
