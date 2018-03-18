package org.emoflon.ibex.gt.transformations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.emoflon.ibex.gt.editor.gT.Operator;
import org.emoflon.ibex.gt.editor.gT.Relation;
import org.emoflon.ibex.gt.editor.gT.Rule;

import GTLanguage.GTBinaryRelation;
import GTLanguage.GTBindingType;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;

/**
 * Utility methods for the editor to internal model transformation.
 */
public class EditorToInternalModelUtils {
	/**
	 * Converts the operator from the editor model to the equivalent binding type
	 * from the internal GT model.
	 * 
	 * @param operator
	 *            the operator from the editor model
	 * @return the binding type
	 */
	public static GTBindingType convertOperatorToBindingType(final Operator operator) {
		switch (operator) {
		case CREATE:
			return GTBindingType.CREATE;
		case DELETE:
			return GTBindingType.DELETE;
		case CONTEXT:
		default:
			return GTBindingType.CONTEXT;
		}
	}

	/**
	 * Converts the relation from the editor model to the equivalent from the
	 * internal GT model.
	 * 
	 * @param relation
	 *            the relation from the editor model
	 * @return the binary relation
	 */
	public static GTBinaryRelation convertRelation(final Relation relation) {
		switch (relation) {
		case ASSIGNMENT:
			throw new IllegalArgumentException("Cannot convert assignment relation.");
		case GREATER:
			return GTBinaryRelation.GREATER;
		case GREATER_OR_EQUAL:
			return GTBinaryRelation.GREATER_OR_EQUAL;
		case UNEQUAL:
			return GTBinaryRelation.UNEQUAL;
		case SMALLER:
			return GTBinaryRelation.SMALLER;
		case SMALLER_OR_EQUAL:
			return GTBinaryRelation.SMALLER_OR_EQUAL;
		case EQUAL:
		default:
			return GTBinaryRelation.EQUAL;
		}
	}

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
	 * Checks whether the editor rule contains at least one operator node or
	 * reference.
	 * 
	 * @param editorRule
	 *            the editor rule
	 * @return true if the rule contains an operator node.
	 */
	public static boolean hasOperatorOrReference(final Rule editorRule) {
		boolean hasOperatorNode = editorRule.getNodes().stream() //
				.anyMatch(node -> node.getOperator() != Operator.CONTEXT);
		return hasOperatorNode || editorRule.getNodes().stream() //
				.map(node -> node.getReferences()).flatMap(references -> references.stream())
				.anyMatch(reference -> reference.getOperator() != Operator.CONTEXT);
	}
}
