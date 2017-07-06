package org.emoflon.moflontohenshin.manipulationrules.corrrules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Parameter;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

import language.TGGRuleNode;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public class TGGInplaceAttributeExpressionToHenshinParameterRule extends TGGInplaceAttributeExpressionAbstractRule{

	@Override
	public EObject forceCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		TGGInplaceAttributeExpression srcInplaceAE = TGGInplaceAttributeExpression.class.cast(src);
		Parameter trgParameter = Parameter.class.cast(trg);
		
		EAttribute type =srcInplaceAE.getAttribute();
		
		this.setVarName(srcInplaceAE, trgParameter::setName);
		trgParameter.setType(type.getEType());		
		
		return ManipulationUtil.getInstance().defaultCreateCorr(node, src, trg, corrR);
	}

	@Override
	protected boolean getTargetCondition(EObject trg) {
		return trg instanceof Parameter;
	}

}
