package org.emoflon.ibex.tgg.compiler.defaults;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.emoflon.ibex.tgg.codegen.AttrCondDefLibraryProvider;
import org.emoflon.ibex.tgg.codegen.TGGInternalModelBuilder;
import org.emoflon.ibex.tgg.transformation.TGGProject;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

public class InternalModelBuilder implements TGGInternalModelBuilder {
	
	private IProject project;

	@Override
	public void run(IProject project, TripleGraphGrammarFile editorModel, TripleGraphGrammarFile flattenedEditorModel) {
		this.project = project;
		
		logInfo("Building correlation model..");
		Optional<TGGProject> tggProject = new EditorTGGtoInternalTGG().generateInternalModels(editorModel, flattenedEditorModel, project);
		if(!tggProject.isPresent()) {
			logError("Could not create tgg project.");
			return;
		}
		
		new AttrCondDefLibraryProvider().generateAttrCondLibsAndStubs(tggProject.get(), project);
	}
	
	private void logInfo(final String message) {
		Logger.getRootLogger().info(this.getClass().getSimpleName()+ "(TGG-Project -> " + project.getName() + " ): " + message);
	}
	
	/**
	 * Logs the error message on the console.
	 */
	private void logError(final String message) {
		Logger.getRootLogger().error(this.getClass().getSimpleName()+ "(Project -> " + project.getName() + " ): " + message);
	}

}
