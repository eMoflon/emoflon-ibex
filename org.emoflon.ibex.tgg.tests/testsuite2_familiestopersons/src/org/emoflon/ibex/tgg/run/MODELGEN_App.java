package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;

import SimpleFamilies.impl.SimpleFamiliesPackageImpl;
import SimplePersons.impl.SimplePersonsPackageImpl;

public class MODELGEN_App extends MODELGEN {

	public MODELGEN_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	protected void registerUserMetamodels() throws IOException {
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
		rs.getPackageRegistry().put("platform:/resource/SimpleFamilies/model/SimpleFamilies.ecore", SimpleFamiliesPackageImpl.init());
		rs.getPackageRegistry().put("platform:/resource/SimplePersons/model/SimplePersons.ecore", SimplePersonsPackageImpl.init());
	}
}
