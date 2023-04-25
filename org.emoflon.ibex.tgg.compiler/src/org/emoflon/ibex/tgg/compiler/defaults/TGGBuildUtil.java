package org.emoflon.ibex.tgg.compiler.defaults;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiFunction;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.WorkspaceHelper;

public class TGGBuildUtil {

	public static final String SCHEMA_FILE = "src/org/emoflon/ibex/tgg/Schema.tgg";

	public static final String INTERNAL_TGG_MODEL_EXTENSION = ".tgg.xmi";
	public static final String ECORE_FILE_EXTENSION = ".ecore";
	public static final String GENMODEL_FILE_EXTENSION = ".genmodel";
	public static final String TGG_FILE_EXTENSION = ".tgg";
	public static final String EDITOR_MODEL_EXTENSION = ".editor.xmi";
	public static final String EDITOR_FLATTENED_MODEL_EXTENSION = "_flattened.editor.xmi";
	public static final String SRC_FOLDER = "src";
	public static final String MODEL_FOLDER = "model";
	public static final String RUN_FILE_PATH_PREFIX = "src/org/emoflon/ibex/tgg/run/";
	public static final String CONFIG_FILE_PATH_PREFIX = RUN_FILE_PATH_PREFIX + "config/";
	
	/**
	 * Creates a new file as RUN_FILE_PATH + fileName + ".java"
	 * 
	 * @param fileName
	 *            The name of the file to be generated
	 * @param generator
	 *            A bi-function used to generate the string content of the new
	 *            file of the form: (project name, file name) -> file contents
	 * @throws CoreException
	 */
	public static void createDefaultRunFile(IProject project, String fileName, BiFunction<String, String, String> generator) throws CoreException {
		establishDefaultRunFile(project, fileName, false, generator, false);
	}

	public static void createDefaultConfigFile(IProject project, String fileName, BiFunction<String, String, String> generator) throws CoreException {
		establishDefaultConfigFile(project, fileName, generator, false);
	}

	public static void createDefaultDebugRunFile(IProject project, String fileName, BiFunction<String, String, String> generator) throws CoreException {
		establishDefaultRunFile(project, fileName, true, generator, false);
	}

	public void enforceDefaultRunFile(IProject project, String fileName, BiFunction<String, String, String> generator) throws CoreException {
		establishDefaultRunFile(project, fileName, false, generator, true);
	}

	public static void enforceDefaultConfigFile(IProject project, String fileName, BiFunction<String, String, String> generator) throws CoreException {
		establishDefaultConfigFile(project, fileName, generator, true);
	}

	public static void establishDefaultRunFile(IProject project, String fileName, boolean debug, BiFunction<String, String, String> generator, Boolean force) throws CoreException {
		createIfNotExists(project, RUN_FILE_PATH_PREFIX + MoflonUtil.lastCapitalizedSegmentOf(project.getName()).toLowerCase() + (debug ? "/debug/" : "/"), fileName, ".java", generator, force);
	}

	public static void establishDefaultConfigFile(IProject project, String fileName, BiFunction<String, String, String> generator, Boolean force) throws CoreException {
		createIfNotExists(project, RUN_FILE_PATH_PREFIX + MoflonUtil.lastCapitalizedSegmentOf(project.getName()).toLowerCase() + "/config/", fileName, ".java", generator, force);
	}

	/**
	 * Creates a new file as path + fileName + ending
	 * 
	 * @param path
	 *            The project relative path to the file
	 * @param ending
	 *            The file extension to use
	 * @param fileName
	 *            The name of the file to be generated
	 * @param generator
	 *            A bi-function used to generate the string content of the new
	 *            file of the form: (project name, file name) -> file contents
	 * @throws CoreException
	 */
	public static void createIfNotExists(IProject project, String path, String fileName, String ending, BiFunction<String, String, String> generator, Boolean force) throws CoreException {
		IPath pathToFile = new Path(path + fileName + ending);
		IFile file = project.getFile(pathToFile);

		if (force)
			file.delete(true, new NullProgressMonitor());

		if (!file.exists()) {
			String defaultContent = generator.apply(project.getName(), fileName);
			WorkspaceHelper.addAllFoldersAndFile(project, pathToFile, defaultContent, new NullProgressMonitor());
		}
	}
	
	public static void saveModelInProject(IFile file, ResourceSet rs, EObject model) throws IOException {
		URI uri = URI.createPlatformResourceURI(file.getProject().getName()//
				+ "/" + file.getProjectRelativePath().toString(), true);
		Resource resource = rs.createResource(uri);
		resource.getContents().add(model);
		Map<Object, Object> options = ((XMLResource) resource).getDefaultSaveOptions();
		options.put(XMIResource.OPTION_SAVE_ONLY_IF_CHANGED, XMIResource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		options.put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl() {
			@Override
			public URI deresolve(URI uri) {
				return uri;
			}
		});
		resource.save(options);
	}

}
