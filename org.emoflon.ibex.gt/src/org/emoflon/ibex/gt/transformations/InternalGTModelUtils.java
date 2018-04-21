package org.emoflon.ibex.gt.transformations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import GTLanguage.GTNode;
import GTLanguage.GTParameter;

/**
 * Utility methods for the GT internal model.
 */
public class InternalGTModelUtils {
	/**
	 * Searches for a GTNode with the given name in the given list.
	 * 
	 * @param nodes
	 *            the node list to search in. The list can be empty, but must not be
	 *            <code>null</code>.
	 * @param name
	 *            the node name to search for, must not be <code>null</code>.
	 * @return an Optional for the node to find
	 */
	public static Optional<GTNode> findGTNodeWithName(final List<GTNode> nodes, final String name) {
		Objects.requireNonNull(nodes, "nodes list must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		return nodes.stream().filter(gtNode -> name.equals(gtNode.getName())).findAny();
	}

	/**
	 * Searches for a GTParameter with the given name in the given list.
	 * 
	 * @param gtParameters
	 *            the parameter list to search in. The list can be empty, but must
	 *            not be <code>null</code>.
	 * @param name
	 *            the parameter name to search for, must not be <code>null</code>.
	 * @return an Optional for the parameter to find
	 */
	public static Optional<GTParameter> findParameterWithName(final List<GTParameter> gtParameters, final String name) {
		Objects.requireNonNull(gtParameters, "The parameters list must not be null!");
		Objects.requireNonNull(name, "The name must not be null!");
		return gtParameters.stream().filter(gtParameter -> name.equals(gtParameter.getName())).findAny();
	}
}
