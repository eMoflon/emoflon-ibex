package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.IbexPatternOptimiser;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.moflon.core.utilities.MoflonUtil;

import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.impl.LanguageFactoryImpl;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public class EdgePattern extends IbexBasePattern {

	private TGGRuleNode src;
	private TGGRuleNode trg;
	private TGGRuleEdge edge;

	public EdgePattern(BlackPatternFactory factory, TGGRuleEdge sampleEdge, boolean stronglyTyped) {
		super(factory);
		LanguageFactoryImpl lfi = new LanguageFactoryImpl();

		src = lfi.createTGGRuleNode();
		src.setType(sampleEdge.getType().getEContainingClass());
		src.setName("source");

		// Set attribute expressions for source node
		if (stronglyTyped) {
			for (TGGInplaceAttributeExpression aeOld : sampleEdge.getSrcNode().getAttrExpr()) {
				TGGInplaceAttributeExpression aeNew = EcoreUtil.copy(aeOld);
				src.getAttrExpr().add(aeNew);
			}
		}

		trg = lfi.createTGGRuleNode();
		trg.setType(sampleEdge.getType().getEReferenceType());
		trg.setName("target");

		// Set attribute expressions for target node
		if (stronglyTyped) {
			for (TGGInplaceAttributeExpression aeOld : sampleEdge.getTrgNode().getAttrExpr()) {
				TGGInplaceAttributeExpression aeNew = EcoreUtil.copy(aeOld);
				trg.getAttrExpr().add(aeNew);
			}
		}

		edge = lfi.createTGGRuleEdge();
		edge.setSrcNode(src);
		edge.setTrgNode(trg);
		edge.setType(sampleEdge.getType());
		edge.setName("edge");

		initialise(factory, stronglyTyped);
		createPatternNetwork(factory);
	}

	private void createPatternNetwork(BlackPatternFactory factory) {
		// Leaf pattern
	}

	protected void initialise(BlackPatternFactory factory, boolean stronglyTyped) {
		String attrExtension = "";

		if (stronglyTyped) {
			attrExtension += IbexPatternOptimiser.convertToString(src.getAttrExpr()) + "_"
					+ IbexPatternOptimiser.convertToString(trg.getAttrExpr()) + "_";
		}

		String name = MoflonUtil.getFQN(src.getType()) + "_" + MoflonUtil.getFQN(edge.getType()) + "_"
				+ MoflonUtil.getFQN(trg.getType()) + "_" + attrExtension + PatternSuffixes.EDGE;

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
