package org.emoflon.moflontohenshin.manipulationrules.corrrules;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Node;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

import language.TGGRuleNode;

public class TGGNodeToHenshinNodeCorrCreationRule extends CorrCreationRule {

	@Override
	public boolean needsForcedCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		return src instanceof TGGRuleNode && trg instanceof Node;
	}

	@Override
	public EObject forceCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		TGGRuleNode srcNode = TGGRuleNode.class.cast(src);
		Node trgNode = Node.class.cast(trg);
		EClass type = srcNode.getType();
		EcoreUtil.resolveAll(type);
		trgNode.setType(type);
		return ManipulationUtil.getInstance().defaultCreateCorr(node, src, trg, corrR);
	}

}
