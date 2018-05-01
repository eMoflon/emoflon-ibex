package org.emoflon.ibex.gt.codegen;

import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;

/**
 * Utility to extract comment from the Xtext model.
 */
public class CommentExtractor {

	/**
	 * Extracts the editor comment from the Xtext node of the pattern if such a
	 * comment is available.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @return the text of the comment (may be empty)
	 */
	public static String getComment(final EditorPattern editorPattern) {
		StringBuilder documentation = new StringBuilder();
		ICompositeNode xtextNodeForPattern = NodeModelUtils.getNode(editorPattern);
		INode firstChild = xtextNodeForPattern.getFirstChild();
		if (firstChild instanceof ICompositeNode) {
			ICompositeNode xtextNodeContaingComment = (ICompositeNode) firstChild;
			xtextNodeContaingComment.getChildren().forEach(i -> {
				if (i instanceof HiddenLeafNode) {
					documentation.append(extract(i.getText()));
				}
			});
		}
		return documentation.toString().trim();
	}

	/**
	 * Extracts the text from a comment.
	 * 
	 * @param comment
	 *            a comment
	 * @return the text of the comment
	 */
	private static String extract(final String comment) {
		if (comment == null) {
			return "";
		}

		return comment.replace("/**", "").replace("/*", "") // start of multi-line comment
				.replace("*/", "").replace("*", "") // end and continued line of multi-line comment
				.replace("//", "") // single line comment
				.replace("  ", " ").trim();
	}
}
