package org.emoflon.moflontohenshin.manipulationrules.corrrules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Edge;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

import language.TGGRuleEdge;
import language.TGGRuleNode;

public class TGGEdgeToHenshinEdgeCorrCreationRule extends CorrCreationRule {

	@Override
	public boolean needsForcedCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		return src instanceof TGGRuleEdge && trg instanceof Edge;
	}

	@Override
	public EObject forceCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		TGGRuleEdge srcEdge = TGGRuleEdge.class.cast(src);
		Edge trgEdge = Edge.class.cast(trg);
		EReference ref = srcEdge.getType();
		trgEdge.setType(ref);
	
		return ManipulationUtil.getInstance().defaultCreateCorr(node, src, trg, corrR);
	}

}
