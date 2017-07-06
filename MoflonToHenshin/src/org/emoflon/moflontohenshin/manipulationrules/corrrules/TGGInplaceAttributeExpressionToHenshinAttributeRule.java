package org.emoflon.moflontohenshin.manipulationrules.corrrules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Attribute;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

import language.TGGRuleNode;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public class TGGInplaceAttributeExpressionToHenshinAttributeRule extends TGGInplaceAttributeExpressionAbstractRule{

	@Override
	public EObject forceCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		TGGInplaceAttributeExpression srcInplaceAE = TGGInplaceAttributeExpression.class.cast(src);
		Attribute trgAttribute = Attribute.class.cast(trg);
		
		this.setVarName(srcInplaceAE, trgAttribute::setValue);
		
		EAttribute type =srcInplaceAE.getAttribute();
		trgAttribute.setType(type);
		
		return ManipulationUtil.getInstance().defaultCreateCorr(node, src, trg, corrR);
	}

	@Override
	protected boolean getTargetCondition(EObject trg) {
		return trg instanceof Attribute;
	}

}
