package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.Collection;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;

import language.TGGRuleNode;

public abstract class SYNC_Strategy {
	public abstract PatternType getType();

	public boolean isPatternRelevantForInterpreter(PatternType type) {
		return type == getType();
	}

	public abstract IGreenPattern revokes(IGreenPatternFactory greenFactory, String patternName, String ruleName);

	public IRuntimeTGGAttrConstrContainer determineCSP(IGreenPatternFactory greenFactory, ITGGMatch m) {
		ITGGMatch copy = m.copy();

		IGreenPattern greenPattern = greenFactory.create(getType());

		copy.getParameterNames().removeAll(//
				getNodesInOutputDomain(greenPattern).stream()//
						.map(n -> n.getName())//
						.collect(Collectors.toList()));

		return greenPattern.getAttributeConstraintContainer(copy);
	}

	public abstract Collection<TGGRuleNode> getNodesInOutputDomain(IGreenPattern greenPattern);
}
