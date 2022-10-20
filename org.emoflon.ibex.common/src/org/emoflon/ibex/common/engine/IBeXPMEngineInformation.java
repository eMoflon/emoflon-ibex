package org.emoflon.ibex.common.engine;

import java.util.Set;

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

	default boolean needs_trash_resource() {
		return false;
	}

	default boolean supports_dynamic_emf() {
		return false;
	}

	default boolean supports_attribute_indices() {
		return false;
	}

	default boolean supports_boolean_attr_constraints() {
		return false;
	}

	default boolean supports_arithmetic_attr_constraints() {
		return false;
	}

	default boolean supports_parameter_attr_constraints() {
		return false;
	}

	default boolean supports_count_matches() {
		return false;
	}

	default boolean supports_transitive_closure() {
		return false;
	}

	default boolean supports_disjoint_pattern_optimization() {
		return false;
	}

	default boolean needs_paranoid_modificiations() {
		return false;
	}

	default boolean uses_reactive_matching() {
		return false;
	}

	default boolean uses_synchroneous_matching() {
		return false;
	}
}
