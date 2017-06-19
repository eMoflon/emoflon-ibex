package org.emoflon.ibex.tgg.run;

import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.emoflon.ibex.tgg.operational.strategies.cc.CC;

public class CC_App extends CC {

	public CC_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	public CC_App(String projectName, String workspacePath, boolean flatten, boolean debug) throws IOException {
		super(projectName, workspacePath, flatten, debug);
	}

	public static void main(String[] args) throws IOException {
			BasicConfigurator.configure();
	
			CC_App cc = new CC_App("testsuite1_ClassInhHier2DB", "./../", true);
			
			logger.info("Starting CC");
			long tic = System.currentTimeMillis();
			cc.run();
			long toc = System.currentTimeMillis();
			logger.info("Completed CC in: " + (toc - tic) + " ms");
	
			cc.saveModels();
			cc.terminate();
		}

	@Override
	protected void registerUserMetamodels() throws IOException {
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
		
		//FIXME load and register source and target metamodels
	}
}

