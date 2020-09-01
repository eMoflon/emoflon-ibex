package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.LinkedList;
import java.util.List;


public class PACCandidate {

	FilterNACCandidate premise;
	List<ConclusionRule> conclusionRules;

	
	//TODO not really the best solution to create a dummy DomainTypePatternTransformation
	public PACCandidate(FilterNACCandidate premise) {
		this.premise = premise;
		conclusionRules = new LinkedList<ConclusionRule>();
	}
	
	public void addConclusionRule(ConclusionRule conclusionRule) {
		conclusionRules.add(conclusionRule);
	}

	public FilterNACCandidate getPremise() {
		return premise;
	}
	
	public List<ConclusionRule> getConclusionRules() {
		return conclusionRules;
	}

}
