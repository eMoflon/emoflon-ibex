package org.emoflon.ibex.gt.transformations;

import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;

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
	public static GTBindingType convertOperatorToBindingType(final EditorOperator operator) {
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
	public static IBeXRelation convertRelation(final EditorRelation relation) {
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
	 * Checks whether the editor rule contains at least one created or deleted node.
	 * 
	 * @param editorRule
	 *            the editor rule
	 * @return true if the rule contains a created or deleted node.
	 */
	public static boolean hasCreatedOrDeletedNode(final EditorPattern editorRule) {
		return editorRule.getNodes().stream() //
				.anyMatch(node -> node.getOperator() != EditorOperator.CONTEXT);
	}

	/**
	 * Checks whether the editor pattern contains at least one created or deleted
	 * reference.
	 * 
	 * @param editorPattern
	 *            the editor rule
	 * @return true if the pattern contains a created or deleted reference.
	 */
	public static boolean hasCreatedOrDeletedReference(final EditorPattern editorPattern) {
		return editorPattern.getNodes().stream() //
				.map(node -> node.getReferences()) //
				.flatMap(references -> references.stream())
				.anyMatch(reference -> reference.getOperator() != EditorOperator.CONTEXT);
	}

	/**
	 * Checks whether the editor pattern contains at least one operator node or
	 * reference.
	 * 
	 * @param editorPattern
	 *            the editor rule
	 * @return true if the pattern contains an attribute assignment.
	 */
	public static boolean hasAttributeAssignment(final EditorPattern editorPattern) {
		return editorPattern.getNodes().stream() //
				.map(node -> node.getAttributes()) //
				.flatMap(attributes -> attributes.stream()) //
				.anyMatch(attribute -> attribute.getRelation() == EditorRelation.ASSIGNMENT);
	}
}
