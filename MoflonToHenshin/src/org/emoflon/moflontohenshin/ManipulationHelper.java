package org.emoflon.moflontohenshin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;
import org.emoflon.moflontohenshin.manipulationrules.NodeCreationRule;

import language.TGGRuleNode;

public class ManipulationHelper {

	private static List<NodeCreationRule> nodeCreationRules = new ArrayList<>();
	
	public static void addRule(NodeCreationRule rule){
		nodeCreationRules.add(rule);
	}
	
	public ManipulationHelper(){
		ManipulationUtil.setNodeCreationFun(this::createNode);
	}
	
	private EObject createNode(TGGRuleNode node){
		for(NodeCreationRule rule : nodeCreationRules){
			if(rule.shouldBeCreated(node))
				return rule.create(node);
		}
		
		return ManipulationUtil.getDefaultNodeCreationFun().apply(node);
	}
	
	
}
