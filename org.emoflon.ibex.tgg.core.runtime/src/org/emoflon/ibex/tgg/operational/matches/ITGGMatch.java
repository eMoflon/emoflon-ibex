package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.engine.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.compiler.patterns.PatternUtil;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;

import runtime.TGGRuleApplication;

public interface ITGGMatch extends IMatch {

	ITGGMatch copy();

	default Collection<EMFEdge> getCreatedEdges() {
		throw new UnsupportedOperationException("This match does not support keeping track of matched edges!");
	}

	default String getRuleName() {
		return PatternUtil.getRuleName(getPatternName());
	}

	PatternType getType();

	/**
	 * If there is a {@link TGGRuleApplication} node covered by this match, it gets returned. Otherwise
	 * it returns {@code null}.
	 * 
	 * @return TGGRuleApplication node or {@code null} if it does not exist
	 */
	default TGGRuleApplication getRuleApplicationNode() {
		return (TGGRuleApplication) get(TGGPatternUtil.getProtocolNodeName(//
				PatternSuffixes.removeSuffix(getPatternName())));
	}
}
