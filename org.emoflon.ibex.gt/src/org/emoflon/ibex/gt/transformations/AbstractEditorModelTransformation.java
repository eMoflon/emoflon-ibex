package org.emoflon.ibex.gt.transformations;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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
	
	protected Map<EditorPattern, EditorPattern> pattern2flattened = new HashMap<>();

	/**
	 * Returns the flattened pattern if it exists and the pattern is not abstract. 
	 * Side Effect: Stores the flattened pattern in a map for later usage.
	 * 
	 * @param editorPattern
	 * @return the flattened pattern
	 */
	public EditorPattern calcFlattenedPattern(final EditorPattern editorPattern) {
		if (editorPattern.getSuperPatterns().isEmpty()) {
			pattern2flattened.put(editorPattern, editorPattern);
			return editorPattern;
		}

		GTFlattener flattener = new GTFlattener(editorPattern);
		if (flattener.hasErrors()) {
			flattener.getErrors().forEach(e -> logError("Flattening of " + editorPattern.getName() + " "+e) );
			return null;
		} else {
			pattern2flattened.put(editorPattern, flattener.getFlattenedPattern());
			return flattener.getFlattenedPattern();
		}
	}
	
	/**
	 * Returns the flattened pattern if it exists, otherwise it will be constructed if the pattern is not abstract. 
	 * 
	 * @param editorPattern
	 * @return the flattened pattern
	 */
	public EditorPattern getFlattenedPattern(final EditorPattern editorPattern) {
		if(!pattern2flattened.containsKey(editorPattern))
			return calcFlattenedPattern(editorPattern);
		
		return pattern2flattened.get(editorPattern);
	}
	
	/**
	 * Returns the flattened pattern if it exists, otherwise it will be constructed if the pattern is not abstract. 
	 * 
	 * @param editorPattern
	 * @param errorLogger Some logging function, consuming a string.
	 * @return the flattened pattern
	 */
	public static EditorPattern flattenPattern(final EditorPattern editorPattern, Consumer<String> errorLogger) {
		if (editorPattern.getSuperPatterns().isEmpty()) {
			return editorPattern;
		}

		GTFlattener flattener = new GTFlattener(editorPattern);
		if (flattener.hasErrors()) {
			flattener.getErrors().forEach(e -> errorLogger.accept("Flattening of " + editorPattern.getName() + " "+e) );
			return null;
		} else {
			return flattener.getFlattenedPattern();
		}
	}
	
	
}
