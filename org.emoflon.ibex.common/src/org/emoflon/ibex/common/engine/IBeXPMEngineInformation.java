package org.emoflon.ibex.common.engine;

import java.util.Set;

import org.emoflon.ibex.common.operational.IPatternInterpreterProperties;

public interface IBeXPMEngineInformation {
	static final String PLUGIN_EXTENSON_ID = "org.emoflon.ibex.common.IBeXPMEngineExtension";

	/**
	 * Returns the required dependencies of the engine.
	 * 
	 * @return the names of the plugins required by the engine
	 */
	public Set<String> getDependencies();

	/**
	 * Returns the list of imports necessary for initialization.
	 * 
	 * @return the paths of the classes to import
	 */
	public Set<String> getImports();

	/**
	 * Returns the engines features/capabilities.
	 * 
	 * @return IPatternInterpreterProperties
	 */
	public IPatternInterpreterProperties getEngineProperties();

	/**
	 * Returns the name of the engine.
	 * 
	 * @return the name of the engine
	 */
	public String getEngineName();

	/**
	 * Returns the name of the class implementing the engine (the
	 * <code>IContextPatternInterpreter</code> interface).
	 * 
	 * @return the name of the class implementing the engine.
	 */
	public String getEngineClassName();
}
