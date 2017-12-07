package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;

import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class WholeRulePattern extends IbexBasePattern {
	protected PatternFactory factory;

	public WholeRulePattern(PatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork();	
	}
	
	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.WHOLE;

		Collection<TGGRuleNode> signatureNodes = new ArrayList<>(rule.getNodes());
		
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	protected void createPatternNetwork() {
		addPositiveInvocation(factory.create(SrcPattern.class));
		addPositiveInvocation(factory.create(TrgPattern.class));
		addPositiveInvocation(factory.create(CorrPattern.class));
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).create(WholeRulePattern.class));
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
}
