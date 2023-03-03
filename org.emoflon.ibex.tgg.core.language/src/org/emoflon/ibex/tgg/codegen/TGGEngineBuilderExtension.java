package org.emoflon.ibex.tgg.codegen;

import org.eclipse.core.resources.IProject;
import org.emoflon.ibex.tgg.tggl.tGGL.EditorFile;

public interface TGGEngineBuilderExtension {
	
	static final String BUILDER_EXTENSON_ID = "org.emoflon.ibex.tgg.language.TGGEngineBuilderExtension";

	void run(IProject project, EditorFile editorModel, EditorFile flattenedEditorModel);

}
