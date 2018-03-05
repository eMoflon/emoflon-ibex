package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.TGGRuleEdge;
import language.TGGRuleNode;

public class EdgePattern extends IbexBasePattern {
	
	private TGGRuleNode src;
	private TGGRuleNode trg;
	private TGGRuleEdge edge;
	
	public EdgePattern(BlackPatternFactory factory, TGGRuleEdge edge) {
		super(factory);
		this.src = edge.getSrcNode();
		this.trg = edge.getTrgNode();
		this.edge = edge;
		
		initialise(factory);
		createPatternNetwork(factory);
	}

	private void createPatternNetwork(BlackPatternFactory factory) {
		// Leaf pattern
	}

	protected void initialise(BlackPatternFactory factory) {
		String name = factory.getRule().getName() + PatternSuffixes.EDGE;
		
		Collection<TGGRuleNode> signatureNodes = new ArrayList<TGGRuleNode>();
		signatureNodes.add(src);
		signatureNodes.add(trg);
		
		Collection<TGGRuleEdge> localEdges = new ArrayList<TGGRuleEdge>();
		localEdges.add(edge);
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		// Leaf pattern so we have to check injectivity here
		return false;
	}
	
	public TGGRuleEdge getEdge() {
		return edge;
	}
}
