package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.moflon.util.MoflonUtil;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.impl.LanguageFactoryImpl;

public class EdgePattern extends IbexBasePattern {
	
	private TGGRuleNode src;
	private TGGRuleNode trg;
	private TGGRuleEdge edge;
	
	public EdgePattern(BlackPatternFactory factory, TGGRuleEdge sampleEdge) {
		super(factory);
		LanguageFactoryImpl lfi = new LanguageFactoryImpl();
		
		src = lfi.createTGGRuleNode();
		src.setType(sampleEdge.getType().getEContainingClass());
		src.setName("source");
		
		trg = lfi.createTGGRuleNode();
		trg.setType(sampleEdge.getType().getEReferenceType());
		trg.setName("target");
		
		edge = lfi.createTGGRuleEdge();
		edge.setSrcNode(src);
		edge.setTrgNode(trg);
		edge.setType(sampleEdge.getType());
		edge.setName("edge");
		
		initialise(factory);
		createPatternNetwork(factory);
	}

	private void createPatternNetwork(BlackPatternFactory factory) {
		// Leaf pattern
	}

	protected void initialise(BlackPatternFactory factory) {
		String name = MoflonUtil.getFQN(src.getType()) + "_" + MoflonUtil.getFQN(edge.getType()) + "_"
				    + MoflonUtil.getFQN(trg.getType()) + "_" + PatternSuffixes.EDGE;
		
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
