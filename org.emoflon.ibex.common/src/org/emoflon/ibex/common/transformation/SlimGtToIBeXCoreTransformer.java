package org.emoflon.ibex.common.transformation;

import org.eclipse.emf.ecore.EFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.slimgt.slimGT.EditorFile;

public abstract class SlimGtToIBeXCoreTransformer<EF extends EditorFile, MODEL extends IBeXModel, FACTORY extends EFactory> {
	final protected EF editorFile;
	final protected MODEL model;
	final protected FACTORY factory;

	public SlimGtToIBeXCoreTransformer(final EF editorFile) {
		this.editorFile = editorFile;
		this.factory = initFactory();
		this.model = createNewModel();
	}

	public abstract MODEL transform();

	protected abstract FACTORY initFactory();

	protected abstract MODEL createNewModel();

}
