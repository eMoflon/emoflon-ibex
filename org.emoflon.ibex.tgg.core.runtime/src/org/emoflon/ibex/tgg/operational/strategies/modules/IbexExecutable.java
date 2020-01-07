package org.emoflon.ibex.tgg.operational.strategies.modules;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public interface IbexExecutable {

	public void run() throws IOException;
	
	public void saveModels() throws IOException;
	
	public IbexOptions getOptions();
	
	public void terminate() throws IOException;
	
	public TGGResourceHandler getResourceHandler();
	
	public void setResourceHandler(TGGResourceHandler resourceHandler);
}
