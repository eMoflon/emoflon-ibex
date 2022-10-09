package org.emoflon.ibex.gt.build;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.emoflon.ibex.common.slimgt.build.SlimGTBuilderExtension;
import org.emoflon.ibex.common.slimgt.util.BuildPropertiesManager;
import org.emoflon.ibex.gt.gtl.gTL.EditorFile;
import org.emoflon.ibex.gt.gtl.ui.builder.GTLNature;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.transformation.GTLtoGTModelTransformer;
import org.moflon.core.plugins.manifest.ManifestFileUpdater;

public class IBeXGtPackageBuilder implements SlimGTBuilderExtension<EditorFile> {

	final public static String GEN_FOLDER = "src-gen";
	final public static String API_FOLDER = "api";
	final public static String MATCH_FOLDER = "match";
	final public static String PATTERN_FOLDER = "pattern";
	final public static String RULE_FOLDER = "rule";

	protected IProject project;
	protected EditorFile editorFile;
	protected GTModel gtModel;
	protected BuildPropertiesManager bpManager;
	protected String pkg;
	protected File pkgPath;
	protected File apiPath;
	protected File matchPath;
	protected File patternPath;
	protected File rulePath;

	@Override
	public void build(IProject project, EditorFile flattenedEditorFile) {
		this.project = project;
		this.editorFile = flattenedEditorFile;
		this.pkg = flattenedEditorFile.getPackage().getName();
		pkgPath = new File(project.getLocation().toPortableString() + "/" + GEN_FOLDER + "/" + pkg.replace(".", "/"));
		apiPath = new File(pkgPath.getPath() + "/" + API_FOLDER);
		matchPath = new File(apiPath.getPath() + "/" + MATCH_FOLDER);
		patternPath = new File(apiPath.getPath() + "/" + PATTERN_FOLDER);
		rulePath = new File(apiPath.getPath() + "/" + RULE_FOLDER);

		try {
			cleanAndSetup();
			transformToGTModel();
		} catch (Exception e) {
			// TODO: Log!
			return;
		}
	}

	protected void cleanAndSetup() throws Exception {
		// Clean old code and create folder if necessary
		if (pkgPath.exists()) {
			pkgPath.delete();
			pkgPath.mkdir();
		} else {
			pkgPath.mkdirs();
		}

		apiPath.mkdir();
		matchPath.mkdir();
		patternPath.mkdir();
		rulePath.mkdir();

		// Create build properties file if it does not exists, update otherwise
		bpManager = new BuildPropertiesManager(project);
		bpManager.addAttributeValue("source..", "src-gen/");
		bpManager.writeBack();

		// Create manifest file if it does not exists, update otherwise
		new ManifestFileUpdater().processManifest(project, this::processManifestForPackage);
	}

	protected void transformToGTModel() {
		GTLtoGTModelTransformer transformer = new GTLtoGTModelTransformer(editorFile, project);
		gtModel = transformer.transform();
	}

	protected void generateAPI() {
		// TODO:
	}

	/**
	 * Updates the exports in the MANIFEST.MF for the API.
	 * 
	 * @param the manifest to update
	 * @return whether the manifest was changed
	 */
	protected boolean processManifestForPackage(final Manifest manifest) {
		boolean changedBasics = ManifestFileUpdater.setBasicProperties(manifest, project.getName());

		List<String> dependencies = new LinkedList<String>();
		dependencies.addAll(List.of("org.emoflon.ibex.common", "org.emoflon.ibex.gt", "org.emoflon.ibex.gt.gtmodel"));
		// TODO: collectEngineExtensions().forEach(engine ->
		// dependencies.addAll(engine.getDependencies()));
		boolean updatedDependencies = ManifestFileUpdater.updateDependencies(manifest, dependencies);

		String apiPackageName = pkg + "." + API_FOLDER;
		String matchPackageName = apiPackageName + "." + MATCH_FOLDER;
		String patternPackageName = apiPackageName + "." + PATTERN_FOLDER;
		String rulePackageName = apiPackageName + "." + RULE_FOLDER;
		boolean updateExports = ManifestFileUpdater.updateExports(manifest,
				List.of(apiPackageName, matchPackageName, patternPackageName, rulePackageName));
		if (updateExports) {
			// TODO: log("Updated exports");
		}

		return updateExports || updatedDependencies || changedBasics;
	}

	@Override
	public boolean hasProperNature(IProject project) {
		try {
			return null != project.getNature(GTLNature.NATURE_ID);
		} catch (CoreException e) {
			return false;
		}
	}

}
