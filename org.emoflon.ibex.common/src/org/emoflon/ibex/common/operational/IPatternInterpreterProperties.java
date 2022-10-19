package org.emoflon.ibex.common.operational;

public interface IPatternInterpreterProperties {

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
