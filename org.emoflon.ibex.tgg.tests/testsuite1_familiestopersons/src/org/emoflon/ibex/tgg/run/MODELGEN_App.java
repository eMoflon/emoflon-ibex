package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;
import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;

import SimpleFamilies.impl.SimpleFamiliesPackageImpl;
import SimplePersons.impl.SimplePersonsPackageImpl;

public class MODELGEN_App extends MODELGEN {

	public MODELGEN_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	public MODELGEN_App(String projectName, String workspacePath, boolean flatten, boolean debug) throws IOException {
		super(projectName, workspacePath, flatten, debug);
	}
	
	public static void main(String[] args) throws IOException {
		MODELGEN_App refinedGenerator = new MODELGEN_App("testsuite1_familiestopersons", "./../", false, false);
		MODELGEN_App flatGenerator = new MODELGEN_App("testsuite1_familiestopersons", "./../", true, false);
		
		runPerformanceTest(refinedGenerator, "refinedGenerator");
		runPerformanceTest(flatGenerator, "flatGenerator");
	}
	
	private static void runPerformanceTest(MODELGEN_App generator, String generatorName) throws IOException {
		MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.getTGG());

		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 4);
		stop.setMaxRuleCount("FamilyMemberToPerson", 0);
		stop.setMaxRuleCount("ExistingFamilyToPerson", 0);
		stop.setMaxRuleCount("FatherToMale", 5);
		stop.setMaxRuleCount("MotherToFemale", 5);
		stop.setMaxRuleCount("NewFamilyDaughterToFemale", 0);
		stop.setMaxRuleCount("DaughterToFemale", 30000);
		stop.setMaxRuleCount("SonToMale", 30000);
		stop.setMaxRuleCount("FatherToNothing", 0);
		stop.setMaxRuleCount("FatherAndMale", 0);
		stop.setMaxRuleCount("IgnoreTwoFamilies", 0);
		stop.setMaxRuleCount("CreateFourthFamily", 0);
		stop.setMaxRuleCount("CreateFourthAndFifthFamily", 0);
		stop.setMaxRuleCount("CreateFather", 0);
		stop.setMaxRuleCount("ConnectFather", 0);
		
		long startTime = System.nanoTime();
		generator.setStopCriterion(stop); 
		generator.run();
		generator.terminate();
		System.out.println((System.nanoTime() - startTime)/1000000000.0 + " s for "+ generatorName);
		
	}
	


	protected void registerUserMetamodels() throws IOException {
		rs.getPackageRegistry().put("platform:/resource/SimpleFamilies/model/SimpleFamilies.ecore", SimpleFamiliesPackageImpl.init());
		rs.getPackageRegistry().put("platform:/resource/SimplePersons/model/SimplePersons.ecore", SimplePersonsPackageImpl.init());
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
	}
}
