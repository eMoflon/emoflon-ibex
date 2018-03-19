package org.emoflon.ibex.gt.transformations;

import org.emoflon.ibex.gt.editor.gT.Operator;
import org.emoflon.ibex.gt.editor.gT.Relation;
import org.emoflon.ibex.gt.editor.gT.Rule;

import GTLanguage.GTBindingType;
import IBeXLanguage.IBeXRelation;

/**
 * Utility methods for the editor to internal model transformation.
 */
public class EditorToInternalGTModelUtils {
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
	public static IBeXRelation convertRelation(final Relation relation) {
		switch (relation) {
		case GREATER:
			return IBeXRelation.GREATER;
		case GREATER_OR_EQUAL:
			return IBeXRelation.GREATER_OR_EQUAL;
		case EQUAL:
			return IBeXRelation.EQUAL;
		case UNEQUAL:
			return IBeXRelation.UNEQUAL;
		case SMALLER:
			return IBeXRelation.SMALLER;
		case SMALLER_OR_EQUAL:
			return IBeXRelation.SMALLER_OR_EQUAL;
		case ASSIGNMENT:
			throw new IllegalArgumentException("Cannot convert assignment relation.");
		default:
			throw new IllegalArgumentException("Cannot convert relation.");
		}
	}

	/**
	 * Checks whether the editor rule contains at least one operator node or
	 * reference.
	 * 
	 * @param editorRule
	 *            the editor rule
	 * @return true if the rule contains an operator node.
	 */
	public static boolean hasOperatorNodeOrReference(final Rule editorRule) {
		boolean hasOperatorNode = editorRule.getNodes().stream() //
				.anyMatch(node -> node.getOperator() != Operator.CONTEXT);
		return hasOperatorNode || editorRule.getNodes().stream() //
				.map(node -> node.getReferences()) //
				.flatMap(references -> references.stream())
				.anyMatch(reference -> reference.getOperator() != Operator.CONTEXT);
	}
}
