package org.emoflon.ibex.tgg.run;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGEN;

import Families.impl.FamiliesPackageImpl;
import Persons.impl.PersonsPackageImpl;

public class MODELGEN_App extends MODELGEN {

	public MODELGEN_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	protected void registerUserMetamodels() throws IOException {
		rs.getPackageRegistry().put("platform:/resource/Families/model/Families.ecore", FamiliesPackageImpl.init());
		rs.getPackageRegistry().put("platform:/resource/Persons/model/Persons.ecore", PersonsPackageImpl.init());
		loadAndRegisterMetamodel(projectPath + "/model/" + projectPath + ".ecore");
	}
}
