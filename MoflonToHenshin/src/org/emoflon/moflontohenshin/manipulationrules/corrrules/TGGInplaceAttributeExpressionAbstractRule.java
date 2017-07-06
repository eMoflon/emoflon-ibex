package org.emoflon.moflontohenshin.manipulationrules.corrrules;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import language.TGGRuleNode;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public abstract class TGGInplaceAttributeExpressionAbstractRule extends CorrCreationRule{

	private static Map<TGGInplaceAttributeExpression, String> nameMapper = new HashMap<>();
	
	@Override
	public boolean needsForcedCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR) {
		return src instanceof TGGInplaceAttributeExpression && getTargetCondition(trg);
	}
	
	protected abstract boolean getTargetCondition(EObject trg);
	
	protected void setVarName(TGGInplaceAttributeExpression srcInplaceAE, Consumer<String> setNameFun){
		String name = nameMapper.get(srcInplaceAE);
		if(name == null){
			EAttribute type =srcInplaceAE.getAttribute();
			name = type.getName() + "_" + nameMapper.size() + "_" + "Var";
		}
		
		setNameFun.accept(name);
	}

}
