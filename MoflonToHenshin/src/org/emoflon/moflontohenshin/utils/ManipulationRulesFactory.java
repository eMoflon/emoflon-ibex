package org.emoflon.moflontohenshin.utils;

import org.emoflon.moflontohenshin.ManipulationHelper;
import org.emoflon.moflontohenshin.manipulationrules.corrrules.TGGNodeToHenshinNodeCorrCreationRule;
import org.emoflon.moflontohenshin.manipulationrules.noderules.DontCreateLHSRHS;
import org.emoflon.moflontohenshin.manipulationrules.noderules.RuleNodeCreationRule;

public class ManipulationRulesFactory {
	
	private static ManipulationRulesFactory instance;
	
	private ManipulationRulesFactory(){
		createAllInstances();
	}
	
	public static void createInstances(){
		if(instance == null)
			instance = new ManipulationRulesFactory();
	}
	
	private void createAllInstances(){
		createAllCreationRules();
		new ManipulationHelper();
	}
	
	private void createAllCreationRules(){
		createNodeCreationRules();
		createEdgeCreationRules();
		createCorrCreationRules();
	}
	
	private void createNodeCreationRules(){
		new RuleNodeCreationRule();
		new DontCreateLHSRHS();
	}
	
	private void createEdgeCreationRules(){
		
	}
	
	private void createCorrCreationRules(){
		new TGGNodeToHenshinNodeCorrCreationRule();
	}
}
