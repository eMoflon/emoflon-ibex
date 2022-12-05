package org.emoflon.ibex.tgg.compiler.builder;

import org.eclipse.core.resources.IProject;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;

public interface TGGEngineBuilderExtension {
	
	static final String BUILDER_EXTENSON_ID = "org.emoflon.ibex.tgg.language.TGGEngineBuilderExtension";

	void run(IProject project, TGGModel editorModel);
}
