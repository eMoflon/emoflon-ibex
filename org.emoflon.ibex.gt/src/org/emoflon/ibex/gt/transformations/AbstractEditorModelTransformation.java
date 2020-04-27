package org.emoflon.ibex.gt.transformations;

import java.util.Optional;

import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTFlattener;


/**
 * This is a super class for model transformations from the editor model to
 * another model.
 *
 * @param <TargetModel>
 *            the type of the target model
 */
public abstract class AbstractEditorModelTransformation<TargetModel>
		extends AbstractModelTransformation<EditorGTFile, TargetModel> {

	/**
	 * Returns the flattened pattern if it exists and the pattern is not abstract.
	 * 
	 * @param editorPattern
	 *            editor pattern
	 * @return an Optional for the flattened pattern
	 */
	protected Optional<EditorPattern> getFlattenedPattern(final EditorPattern editorPattern) {
		if (editorPattern.getSuperPatterns().isEmpty()) {
			return Optional.of(editorPattern);
		}

		GTFlattener flattener = new GTFlattener(editorPattern);
		if (flattener.hasErrors()) {
			flattener.getErrors().forEach(e -> logError("Flattening of %s: %s", editorPattern.getName(), e));
			return Optional.empty();
		} else {
			return Optional.of(flattener.getFlattenedPattern());
		}
	}
	
}
