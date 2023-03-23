package org.emoflon.ibex.tgg.analysis;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

/*
 * Provides a connection between the conclusion and the premise over a shared node Used for PAC
 * strategy
 */
public class ConclusionRule {

	private TGGRule conclusionRule;
	private IBeXNode premiseConclusionNode;

	protected ConclusionRule(TGGRule conclusionRule, IBeXNode premiseConclusionNode) {
		this.conclusionRule = conclusionRule;
		this.premiseConclusionNode = premiseConclusionNode;
	}

	public TGGRule getConclusionRule() {
		return conclusionRule;
	}

	public IBeXNode getPremiseConclusionNode() {
		return premiseConclusionNode;
	}
}
