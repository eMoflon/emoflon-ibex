package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

public class SYNC_App extends SYNC {

	public SYNC_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();

		SYNC_App synchroniser = new SYNC_App("FamiliesToPersonsDeterministic", "./../", true);

		logger.info("Starting forward");
		long tic = System.currentTimeMillis();
		synchroniser.forward();
		long toc = System.currentTimeMillis();
		logger.info("Completed forward in: " + (toc - tic) + " ms");

		synchroniser.saveModels();
		synchroniser.terminate();
	}

	protected void registerUserMetamodels() throws IOException {
		loadAndRegisterMetamodel(projectPath + "/model/Families.ecore");
		loadAndRegisterMetamodel(projectPath + "/model/Persons.ecore");
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
	}

	@Override
	protected void saveModels() throws IOException {
		s.save(null);
	 	t.save(null);
	 	c.save(null);
	 	p.save(null);
	}

	@Override
	protected void loadModels() throws IOException {
		s = loadResource(projectPath + "/instances/src.xmi");

		t = createResource(projectPath + "/instances/trg.xmi");
		c = createResource(projectPath + "/instances/corr.xmi");
		p = createResource(projectPath + "/instances/protocol.xmi");
		
		EcoreUtil.resolveAll(rs);
	}
}
