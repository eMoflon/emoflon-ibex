package org.emoflon.ibex.common.transformation;

import org.eclipse.emf.ecore.EFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticFactory;
import org.emoflon.ibex.common.slimgt.slimGT.EditorFile;
import org.emoflon.ibex.common.slimgt.util.XtextResourceManager;

public abstract class SlimGtToIBeXCoreTransformer<EF extends EditorFile, MODEL extends IBeXModel, FACTORY extends EFactory> {
	final protected EF editorFile;
	final protected MODEL model;
	final protected IBeXCoreModelFactory superFactory = IBeXCoreModelFactory.eINSTANCE;
	final protected IBeXCoreArithmeticFactory arithmeticFactory = IBeXCoreArithmeticFactory.eINSTANCE;
	final protected FACTORY factory;
	final protected XtextResourceManager xtextResources = new XtextResourceManager();

	public SlimGtToIBeXCoreTransformer(final EF editorFile) {
		this.editorFile = editorFile;
		factory = initFactory();
		model = createNewModel();
		model.setMetaData(createModelMetadata());
		model.setFeatureConfig(superFactory.createIBeXFeatureConfig());
		model.setNodeSet(superFactory.createIBeXNodeSet());
		model.setEdgeSet(superFactory.createIBeXEdgeSet());
		model.setPatternSet(superFactory.createIBeXPatternSet());
	}

	public abstract MODEL transform();

	protected abstract FACTORY initFactory();

	protected abstract MODEL createNewModel();

	protected abstract IBeXModelMetadata createModelMetadata();

}
