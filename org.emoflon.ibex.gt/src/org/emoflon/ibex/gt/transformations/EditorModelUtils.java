package org.emoflon.ibex.gt.transformations;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorReference;

/**
 * Utility methods for the editor model.
 */
public class EditorModelUtils {

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
				.sorted((a, b) -> a.getName().compareTo(b.getName())) //
				.collect(Collectors.toList());
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
	public static List<EditorReference> getReferencesByOperator(final EditorPattern editorPattern,
			final EditorOperator... operators) {
		Objects.requireNonNull(editorPattern, "The editor pattern must not be null!");
		List<EditorOperator> operatorsList = Arrays.asList(operators);
		return editorPattern.getNodes().stream() //
				.flatMap(n -> n.getReferences().stream()) //
				.filter(r -> operatorsList.contains(r.getOperator()))
				.sorted((a, b) -> a.getType().getName().compareTo(b.getType().getName())) //
				.collect(Collectors.toList());
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
