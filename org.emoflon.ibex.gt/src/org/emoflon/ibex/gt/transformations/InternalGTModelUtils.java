package org.emoflon.ibex.gt.transformations;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import GTLanguage.GTBindingType;
import GTLanguage.GTEdge;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;
import GTLanguage.GTRule;

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

	/**
	 * Checks whether the graph of the rule contains nodes or edges of the given
	 * binding type.
	 * 
	 * @param gtRule
	 *            the rule
	 * @param bindingType
	 *            the binding type
	 * @return <code>true</code> if and only if the graph contains at least one node
	 *         or edge of the binding type
	 */
	public static boolean hasElementsOfBindingType(final GTRule gtRule, final GTBindingType bindingType) {
		Objects.requireNonNull(gtRule, "Rule must not be null!");
		return gtRule.getGraph().getNodes().stream().anyMatch(node -> node.getBindingType().equals(bindingType))
				|| gtRule.getGraph().getEdges().stream().anyMatch(node -> node.getBindingType().equals(bindingType));
	}

	/**
	 * Filters the nodes of the rule for the ones with the given binding type.
	 * 
	 * @param gtRule
	 *            the rule
	 * @param bindingType
	 *            the binding type
	 * @return the stream of nodes, sorted alphabetically by the name
	 */
	public static Stream<GTNode> filterNodesByBindingTypes(final GTRule gtRule, final GTBindingType... bindingTypes) {
		Objects.requireNonNull(gtRule, "Rule must not be null!");
		List<GTBindingType> bindingTypesList = validateBindingTypes(bindingTypes);
		return gtRule.getGraph().getNodes().stream()
				.filter(gtNode -> bindingTypesList.contains(gtNode.getBindingType()))
				.sorted((a, b) -> a.getName().compareTo(b.getName()));
	}

	/**
	 * Filters the edges of the rule for the ones with the given binding type.
	 * 
	 * @param gtRule
	 *            the rule
	 * @param bindingTypes
	 *            the binding types
	 * @return the stream of edges, sorted alphabetically by the name
	 */
	public static Stream<GTEdge> filterEdgesByBindingTypes(final GTRule gtRule, final GTBindingType... bindingTypes) {
		Objects.requireNonNull(gtRule, "Rule must not be null!");
		List<GTBindingType> bindingTypesList = validateBindingTypes(bindingTypes);
		return gtRule.getGraph().getEdges().stream()
				.filter(gtEdge -> bindingTypesList.contains(gtEdge.getBindingType()))
				.sorted((a, b) -> a.getName().compareTo(b.getName()));
	}

	/**
	 * Check that the array of bindingTypes contains at lest one element.
	 * 
	 * @param bindingTypes
	 *            the array of binding types
	 * @return the list of binding types
	 */
	private static List<GTBindingType> validateBindingTypes(final GTBindingType... bindingTypes) {
		if (bindingTypes.length < 1) {
			throw new IllegalArgumentException("At least one binding type required!");
		}
		return Arrays.asList(bindingTypes);
	}
}
