package org.emoflon.ibex.tgg.run;

import java.io.IOException;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

public class SYNC_App extends SYNC {

	public SYNC_App(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	@Override
	protected void registerUserMetamodels() throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveModels() throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void loadModels() throws IOException {
		// TODO Auto-generated method stub
	}
}

