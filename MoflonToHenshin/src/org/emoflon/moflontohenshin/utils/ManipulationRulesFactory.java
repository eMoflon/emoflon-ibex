package org.emoflon.moflontohenshin.utils;

import org.emoflon.moflontohenshin.ManipulationHelper;
import org.emoflon.moflontohenshin.manipulationrules.RuleNodeCreationRule;

public class SingletonFactory {
	
	private static SingletonFactory instance;
	
	private SingletonFactory(){
		createAllInstances();
	}
	
	public static void createInstance(){
		if(instance == null)
			instance = new SingletonFactory();
	}
	
	private void createAllInstances(){
		createAllCreationRules();
		new ManipulationHelper();
	}
	
	private void createAllCreationRules(){
		new RuleNodeCreationRule();
	}
}
