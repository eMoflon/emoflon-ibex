package org.emoflon.ibex.gt.transformation;

import org.emoflon.ibex.common.transformation.SlimGtToIBeXCoreTransformer;
import org.emoflon.ibex.gt.gtl.gTL.EditorFile;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelFactory;

public class GTLtoGTModelTransformer extends SlimGtToIBeXCoreTransformer<EditorFile, GTModel, IBeXGTModelFactory> {

	protected final String projectName;

	public GTLtoGTModelTransformer(final EditorFile editorFile, final String projectName) {
		super(editorFile);
		this.projectName = projectName;
	}

	@Override
	public GTModel transform() {
		model.setPackage(editorFile.getPackage().getName());
		model.setProject(projectName);

		return model;
	}

	@Override
	protected IBeXGTModelFactory initFactory() {
		return IBeXGTModelFactory.eINSTANCE;
	}

	@Override
	protected GTModel createNewModel() {
		return factory.createGTModel();
	}

}
