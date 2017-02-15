package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.emoflon.ibex.tgg.operational.strategies.MODELGEN;
import org.emoflon.ibex.tgg.operational.strategies.MODELGENStopCriterion;

public class MODELGEN_App extends MODELGEN {

	public MODELGEN_App(String projectName) {
		super(projectName);
	}

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		
		MODELGEN_App generator = new MODELGEN_App("FamiliesToPersonsDeterministic");
		
		generator.createAndPrepareResourceSet("./../");
		generator.registerInternalMetamodels(); 
		generator.registerUserMetamodels();
		generator.loadTGG();
		generator.initialiseEngine();
		generator.loadModels();
		
		MODELGENStopCriterion stop = new MODELGENStopCriterion();
		stop.setMaxSrcCount(1000);
		generator.setStopCriterion(stop);

		logger.info("Starting MODELGEN");
		long tic = System.currentTimeMillis();
		generator.run();
		long toc = System.currentTimeMillis();
		logger.info("Completed MODELGEN in: " + (toc-tic) + " ms");
	 
	 	generator.saveModels();
	 	generator.terminate();
	}
	
	protected void registerUserMetamodels() throws IOException {
		loadAndRegisterMetamodel(projectPath + "/model/Families.ecore");
		loadAndRegisterMetamodel(projectPath + "/model/Persons.ecore");
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
	}
}
