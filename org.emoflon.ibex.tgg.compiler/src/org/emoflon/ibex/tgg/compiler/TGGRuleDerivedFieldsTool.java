package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

public class TGGRuleDerivedFieldsTool {
	public static void fillDerivedTGGRuleFields(TGGRule rule) {
		
	}

	public static void fillDerivedTGGOperationalRuleFields(TGGOperationalRule op) {
		fillDerivedTGGRuleFields(op);
	}
	
	public static IBeXRuleDelta createRuleDelta(Collection<? extends IBeXNode> nodes, Collection<? extends IBeXEdge> edges) {
		var delta = IBeXCoreModelFactory.eINSTANCE.createIBeXRuleDelta();
		delta.getNodes().addAll(nodes);
		delta.getEdges().addAll(edges);
		return delta;
	}
}
