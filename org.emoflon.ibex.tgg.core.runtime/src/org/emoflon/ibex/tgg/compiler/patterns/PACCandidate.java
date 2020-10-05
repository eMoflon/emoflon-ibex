package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EReference;

import language.DomainType;
import language.TGGRuleNode;


public class PACCandidate extends FilterNACCandidate{

	private List<ConclusionRule> conclusionRules;
	private DomainType domain;

	public PACCandidate(TGGRuleNode nodeInRule, EReference edgeType, EdgeDirection eDirection, DomainType domain) {
		super(nodeInRule, edgeType, eDirection);
		conclusionRules = new LinkedList<ConclusionRule>();
		this.domain = domain;
	}
	
	public void addConclusionRule(ConclusionRule conclusionRule) {
		//Avoid same conclusion
		//This can happen if there are multiple edges in a TGGRule which fulfill the requirement of a PAC-Conclusion
		for(ConclusionRule cRule : conclusionRules) {
			if(cRule.getConclusionRule().equals(conclusionRule.getConclusionRule()))
				return;
		}
		conclusionRules.add(conclusionRule);
	}
	
	public List<ConclusionRule> getConclusionRules() {
		return conclusionRules;
	}
	
	public DomainType getDomain() {
		return domain;
	}

}
