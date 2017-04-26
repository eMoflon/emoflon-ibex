package org.emoflon.moflontohenshin.utils;

import org.emoflon.moflontohenshin.ManipulationHelper;
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
		new RuleNodeCreationRule();
		new DontCreateLHSRHS();
	}
}
