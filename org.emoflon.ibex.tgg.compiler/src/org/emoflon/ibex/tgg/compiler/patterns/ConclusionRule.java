package org.emoflon.ibex.tgg.compiler.patterns;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

/*
 * Provides a connection between the conclusion and the premise over a shared node Used for PAC
 * strategy
 */
public class ConclusionRule {

	private TGGRule conclusionRule;
	private TGGNode premiseConclusionNode;

	protected ConclusionRule(TGGRule conclusionRule, TGGNode premiseConclusionNode) {
		this.conclusionRule = conclusionRule;
		this.premiseConclusionNode = premiseConclusionNode;
	}

	public TGGRule getConclusionRule() {
		return conclusionRule;
	}

	public TGGNode getPremiseConclusionNode() {
		return premiseConclusionNode;
	}
}
