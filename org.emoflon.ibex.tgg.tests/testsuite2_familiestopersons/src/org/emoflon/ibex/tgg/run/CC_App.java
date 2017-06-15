package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.emoflon.ibex.tgg.operational.strategies.cc.CC;

import SimpleFamilies.impl.SimpleFamiliesPackageImpl;
import SimplePersons.impl.SimplePersonsPackageImpl;

public class CC_App extends CC {

	public CC_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	public CC_App(String projectName, String workspacePath, boolean flatten, boolean debug) throws IOException {
		super(projectName, workspacePath, flatten, debug);
	}

	public static void main(String[] args) throws IOException {
			BasicConfigurator.configure();
	
			CC_App cc = new CC_App("testsuite2_familiestopersons", "./../", true);
			
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
		
		rs.getPackageRegistry().put("platform:/resource/SimpleFamilies/model/SimpleFamilies.ecore", SimpleFamiliesPackageImpl.init());
		rs.getPackageRegistry().put("platform:/resource/SimplePersons/model/SimplePersons.ecore", SimplePersonsPackageImpl.init());
	}
}

