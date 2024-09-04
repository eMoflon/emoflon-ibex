package org.emoflon.ibex.tgg.codegen;

import org.eclipse.core.resources.IProject;
import org.emoflon.ibex.tgg.editor.tgg.TripleGraphGrammarFile;

public interface TGGInternalModelBuilder {
	static final String BUILDER_EXTENSON_ID = "org.emoflon.ibex.tgg.language.TGGInternalModelBuilderExtension";

	void run(IProject project, TripleGraphGrammarFile editorModel, TripleGraphGrammarFile flattenedEditorModel);
}
