package org.emoflon.ibex.gt.codegen;

import java.util.Set;

/**
 * Extension to register an engine for code generation.
 */
public interface GTEngineExtension {
	static final String BUILDER_EXTENSON_ID = "org.emoflon.ibex.gt.GTEngineExtension";

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
