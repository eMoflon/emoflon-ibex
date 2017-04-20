package org.emoflon.moflontohenshin.manipulationrules;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.moflontohenshin.ManipulationHelper;

import language.TGGRuleNode;

public abstract class NodeCreationRule {
	public NodeCreationRule(){
		ManipulationHelper.addRule(this);
	}
	
	public boolean shouldBeCreated(TGGRuleNode node){
		return getClassForNodeCreation().isInstance(node.getType()) && otherConditions(node);
	}
	
	protected boolean otherConditions(TGGRuleNode node){
		return true;
	}
	
	protected abstract Class<?> getClassForNodeCreation(); 
	public abstract EObject create(TGGRuleNode node);
}
