package org.emoflon.ibex.gt.transformations;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;

/**
 * Utility methods for the editor model.
 */
public class EditorModelUtils {
	/**
	 * A comparator for editor attributes.
	 */
	private static final Comparator<EditorAttribute> sortAttribute = (a, b) -> {
		int compareAttributes = a.getAttribute().getName().compareTo(b.getAttribute().getName());
		if (compareAttributes != 0) {
			return compareAttributes;
		}

		return a.getRelation().compareTo(b.getRelation());
	};

	/**
	 * Returns the source node of the editor reference.
	 * 
	 * @param editorReference
	 *            the editor reference
	 * @return the editor node
	 */
	public static EditorNode getSourceNode(final EditorReference editorReference) {
		return (EditorNode) editorReference.eContainer();
	}

	/**
	 * Returns the node of the editor attribute.
	 * 
	 * @param editorAttribute
	 *            the editor attribute
	 * @return the editor node
	 */
	public static EditorNode getNode(final EditorAttribute editorAttribute) {
		return (EditorNode) editorAttribute.eContainer();
	}

	/**
	 * Returns the attribute assignments of the given node.
	 * 
	 * @param editorNode
	 *            the editor node
	 * @return the attribute assignments
	 */
	public static Stream<EditorAttribute> getAttributeAssignments(final EditorNode editorNode) {
		return editorNode.getAttributes().stream() //
				.filter(a -> a.getRelation().equals(EditorRelation.ASSIGNMENT)) //
				.sorted(sortAttribute);
	}

	/**
	 * Returns the attribute conditions of the given node.
	 * 
	 * @param editorNode
	 *            the editor node
	 * @return the attribute conditions
	 */
	public static Stream<EditorAttribute> getAttributeConditions(final EditorNode editorNode) {
		return editorNode.getAttributes().stream() //
				.filter(a -> !a.getRelation().equals(EditorRelation.ASSIGNMENT)) //
				.sorted(sortAttribute);
	}

	/**
	 * Filters the nodes of the rule for the ones with the given operator.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param operators
	 *            the operators
	 * @return the stream of nodes, sorted alphabetically by the name
	 */
	public static List<EditorNode> getNodesByOperator(final EditorPattern editorPattern,
			final EditorOperator... operators) {
		Objects.requireNonNull(editorPattern, "The editor pattern must not be null!");
		List<EditorOperator> operatorsList = Arrays.asList(operators);
		return editorPattern.getNodes().stream() //
				.filter(n -> operatorsList.contains(n.getOperator()))
				.sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toList());
	}

	/**
	 * Returns the references of all nodes in the given pattern.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @return the references
	 */
	public static Stream<EditorReference> getReferences(final EditorPattern editorPattern) {
		return editorPattern.getNodes().stream() //
				.flatMap(n -> n.getReferences().stream());
	}

	/**
	 * Filters the references of the editor pattern for the ones with the given
	 * operator.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param operators
	 *            the operators
	 * @return the stream of edges, sorted alphabetically by the type name
	 */
	public static Stream<EditorReference> getReferencesByOperator(final EditorPattern editorPattern,
			final EditorOperator... operators) {
		Objects.requireNonNull(editorPattern, "The editor pattern must not be null!");
		List<EditorOperator> operatorsList = Arrays.asList(operators);
		return getReferences(editorPattern) //
				.filter(r -> operatorsList.contains(r.getOperator()))
				.sorted((a, b) -> a.getType().getName().compareTo(b.getType().getName()));
	}

	/**
	 * Checks whether a node is local.
	 * 
	 * @param editorNode
	 *            the editor node
	 * @return <code>true</code> if and only if the node is local
	 */
	public static boolean isLocal(final EditorNode editorNode) {
		return editorNode.getName().startsWith("_");
	}
}
