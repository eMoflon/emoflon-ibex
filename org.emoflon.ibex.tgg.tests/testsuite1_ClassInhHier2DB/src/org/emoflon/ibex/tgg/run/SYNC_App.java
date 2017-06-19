package org.emoflon.ibex.tgg.run;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

public class SYNC_App extends SYNC {

	public SYNC_App(String projectName, String workspacePath, boolean debug) throws IOException {
				super(projectName, workspacePath, debug);
			}

	public SYNC_App(String projectName, String workspacePath, boolean flatten, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	public static void main(String[] args) throws IOException {
			BasicConfigurator.configure();
	
			SYNC_App sync = new SYNC_App("testsuite1_ClassInhHier2DB", "./../", true);
			
			logger.info("Starting SYNC");
			long tic = System.currentTimeMillis();
			sync.forward();
			long toc = System.currentTimeMillis();
			logger.info("Completed SYNC in: " + (toc - tic) + " ms");
	
			sync.saveModels();
			sync.terminate();
		}

	@Override
	protected void registerUserMetamodels() throws IOException {
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
		
		//FIXME load and register source and target metamodels
	}
}

