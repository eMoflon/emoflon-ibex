package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.emoflon.ibex.tgg.operational.strategies.sync.FWD_SYNC;

public class FWD_SYNC_App extends FWD_SYNC {

	public FWD_SYNC_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();

		FWD_SYNC_App synchroniser = new FWD_SYNC_App("FamiliesToPersonsDeterministic", "./../", false);

		logger.info("Starting MODELGEN");
		long tic = System.currentTimeMillis();
		synchroniser.run();
		long toc = System.currentTimeMillis();
		logger.info("Completed MODELGEN in: " + (toc - tic) + " ms");

		synchroniser.saveModels();
		synchroniser.terminate();
	}

	protected void registerUserMetamodels() throws IOException {
		loadAndRegisterMetamodel(projectPath + "/model/Families.ecore");
		loadAndRegisterMetamodel(projectPath + "/model/Persons.ecore");
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
	}
}
