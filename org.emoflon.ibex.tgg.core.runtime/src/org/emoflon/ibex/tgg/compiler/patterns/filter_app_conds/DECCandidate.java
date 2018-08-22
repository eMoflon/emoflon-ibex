package org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds;

import org.eclipse.emf.ecore.EReference;

import language.TGGRuleNode;

public class DECCandidate {
	public TGGRuleNode node;
	public EReference edgeType;
	public EdgeDirection direction;
	
	public DECCandidate(TGGRuleNode node, EReference edge, EdgeDirection direction) {
		this.node = node;
		this.edgeType = edge;
		this.direction = direction;
	}
}
