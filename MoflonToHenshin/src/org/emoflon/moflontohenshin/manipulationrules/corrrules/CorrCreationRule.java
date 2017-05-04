package org.emoflon.moflontohenshin.manipulationrules.corrrules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.moflontohenshin.ManipulationHelper;

import language.TGGRuleNode;

public abstract class CorrCreationRule {
	public CorrCreationRule(){
		ManipulationHelper.addCorrCreationRule(this);
	}
	
	public abstract boolean needsForcedCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR);
	public abstract EObject forceCreation(TGGRuleNode node, EObject src, EObject trg, Resource corrR);

}
