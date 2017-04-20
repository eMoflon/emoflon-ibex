package org.emoflon.moflontohenshin.manipulationrules.noderules;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.moflontohenshin.ManipulationHelper;

import language.TGGRuleNode;

public abstract class NodeCreationRule {
	
	private EClass contextEClass;
	
	public NodeCreationRule(EClass context){
		contextEClass = context;
		ManipulationHelper.addNodeCreationRule(this);
	}
	
	public boolean needsForcedCreation(TGGRuleNode node){
		EClass clazz = node.getType();		 
		boolean isInstance = contextEClass.isSuperTypeOf(clazz);
		return isInstance && otherConditions(node);
	}
	
	protected boolean otherConditions(TGGRuleNode node){
		return true;
	}
	

	public abstract EObject forceCreation(TGGRuleNode node);
}
