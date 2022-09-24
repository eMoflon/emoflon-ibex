package org.emoflon.ibex.gt.transformation;

import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.transformation.SlimGtToIBeXCoreTransformer;
import org.emoflon.ibex.gt.gtl.gTL.EditorFile;
import org.emoflon.ibex.gt.gtl.gTL.Import;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelFactory;

public class GTLtoGTModelTransformer extends SlimGtToIBeXCoreTransformer<EditorFile, GTModel, IBeXGTModelFactory> {

	protected final IProject project;

	public GTLtoGTModelTransformer(final EditorFile editorFile, final IProject project) {
		super(editorFile);
		this.project = project;
	}

	@Override
	public GTModel transform() {

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

	@Override
	protected IBeXModelMetadata createModelMetadata() {
		IBeXModelMetadata metadata = superFactory.createIBeXModelMetadata();
		metadata.setProject(project.getName());
		metadata.setProjectPath(project.getLocation().toPortableString());
		metadata.setPackage(editorFile.getPackage().getName());
		metadata.setPackagePath("/src/" + metadata.getPackage().replace(".", "/"));

		for (Import imp : editorFile.getImports().stream().map(imp -> (Import) imp).collect(Collectors.toList())) {
			EPackageDependency dependency = transform(imp);
			metadata.getDependencies().add(dependency);
			metadata.getName2package().put(dependency.getSimpleName(), dependency);
		}
		// TODO Auto-generated method stub
		return null;
	}

	protected EPackageDependency transform(final Import imp) {
		EPackageDependency dependency = superFactory.createEPackageDependency();

		return dependency;
	}

}
