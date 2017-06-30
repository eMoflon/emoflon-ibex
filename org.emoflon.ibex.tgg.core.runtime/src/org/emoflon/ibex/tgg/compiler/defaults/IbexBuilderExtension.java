package org.emoflon.ibex.tgg.compiler.defaults;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.core.transformation.EditorTGGtoInternalTGG;
import org.emoflon.ibex.tgg.core.transformation.TGGProject;
import org.emoflon.ibex.tgg.ide.admin.BuilderExtension;
import org.emoflon.ibex.tgg.ide.admin.IbexTGGBuilder;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

public class IbexBuilderExtension implements BuilderExtension {

	Logger logger = Logger.getLogger(BuilderExtension.class);
	
	@Override
	public void run(IbexTGGBuilder builder, TripleGraphGrammarFile editorModel, TripleGraphGrammarFile flattenedEditorModel) {
		Optional<TGGProject> internalModel = new EditorTGGtoInternalTGG().generateInternalModels(editorModel, flattenedEditorModel, builder.getProject());
		internalModel.ifPresent(m -> new AttrCondDefLibraryProvider().generateAttrCondLibsAndStubs(m, builder.getProject()));
	}
}
