package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import Families.impl.FamiliesPackageImpl;
import Persons.impl.PersonsPackageImpl;

public class SYNC_App extends SYNC {

	public SYNC_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();

		SYNC_App sync = new SYNC_App("testsuite1_familiestopersons", "./../", true);
		
		logger.info("Starting SYNC");
		long tic = System.currentTimeMillis();
		sync.forward();
		long toc = System.currentTimeMillis();
		logger.info("Completed SYNC in: " + (toc - tic) + " ms");

		sync.saveModels();
		sync.terminate();
	}
	
	protected void registerUserMetamodels() throws IOException {
		rs.getPackageRegistry().put("platform:/resource/Families/model/Families.ecore", FamiliesPackageImpl.init());
		rs.getPackageRegistry().put("platform:/resource/Persons/model/Persons.ecore", PersonsPackageImpl.init());
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
	}

	@Override
	public void loadModels() throws IOException {
		s = loadResource(projectPath + "/instances/src.xmi");
		t = loadResource(projectPath + "/instances/trg.xmi");
		c = loadResource(projectPath + "/instances/corr.xmi");
		p = loadResource(projectPath + "/instances/protocol.xmi");
		
		EcoreUtil.resolveAll(rs);
	}
	
	@Override
	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD);
	}
}

