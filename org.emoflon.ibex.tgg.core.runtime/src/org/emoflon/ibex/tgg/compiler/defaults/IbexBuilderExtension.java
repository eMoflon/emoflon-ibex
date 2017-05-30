package org.emoflon.ibex.tgg.compiler.defaults;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.emoflon.ibex.tgg.core.transformation.EditorTGGtoInternalTGG;
import org.emoflon.ibex.tgg.core.transformation.TGGProject;
import org.emoflon.ibex.tgg.ui.ide.admin.BuilderExtension;
import org.emoflon.ibex.tgg.ui.ide.admin.IbexTGGBuilder;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;
import org.moflon.util.LogUtils;

public class IbexBuilderExtension implements BuilderExtension {

	Logger logger = Logger.getLogger(BuilderExtension.class);
	
	@Override
	public void run(IbexTGGBuilder builder, TripleGraphGrammarFile editorModel, TripleGraphGrammarFile flattenedEditorModel) {
		try {
			Optional<TGGProject> internalModel = new EditorTGGtoInternalTGG().generateInternalModels(flattenedEditorModel, builder.getProject());
			internalModel.ifPresent(m -> new AttrCondDefLibraryProvider().generateAttrCondLibsAndStubs(m, builder.getProject()));
			builder.createDefaultFile("MODELGEN_App", DefaultFilesHelper::generateModelGenFile);
			builder.createDefaultFile("SYNC_App", DefaultFilesHelper::generateSyncAppFile);
		} catch (CoreException e) {
			LogUtils.error(logger, e);
		}
	}
}
