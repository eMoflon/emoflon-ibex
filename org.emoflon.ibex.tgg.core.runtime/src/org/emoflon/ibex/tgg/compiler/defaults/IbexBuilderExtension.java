package org.emoflon.ibex.tgg.compiler.defaults;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.transformations.EditorTGGtoInternalTGG;
import org.emoflon.ibex.tgg.ide.admin.BuilderExtension;
import org.emoflon.ibex.tgg.ide.admin.IbexTGGBuilder;
import org.emoflon.ibex.tgg.transformation.TGGProject;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

public class IbexBuilderExtension implements BuilderExtension {

	Logger logger = Logger.getLogger(BuilderExtension.class);
	
	@Override
	public void run(IbexTGGBuilder builder, TripleGraphGrammarFile editorModel, TripleGraphGrammarFile flattenedEditorModel) {
		Optional<TGGProject> internalModel = builder.computeOrGetFromBlackboard(
				EditorTGGtoInternalTGG.INTERNAL_TGG_MODEL, 
				() -> new EditorTGGtoInternalTGG().generateInternalModels(editorModel, flattenedEditorModel, builder.getProject()));
		internalModel.ifPresent(m -> new AttrCondDefLibraryProvider().generateAttrCondLibsAndStubs(m, builder.getProject()));
	}
}
