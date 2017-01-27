package org.moflon.tgg.mosl.defaults;

import static org.moflon.core.utilities.WorkspaceHelper.addAllFoldersAndFile;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.moflon.tgg.mosl.defaults.RunFileType;

public class RunFileHelper {
	private static final String RUN_FILE_PATH = "src/org/emoflon/ibex/tgg/run/";

	public static void createFiles(IProject project) throws CoreException, IOException {
		for (RunFileType mode : RunFileType.values()) {
			String fileName = mode.name() + "_App";
			String path = RUN_FILE_PATH + fileName + ".java";
			String defaultLib = DefaultFilesHelper.generateRunFile(project.getName(), fileName, mode);
			IPath pathToLib = new Path(path);
			IFile attrLibFile = project.getFile(pathToLib);
			if (!attrLibFile.exists()) 
				addAllFoldersAndFile(project, pathToLib, defaultLib, new NullProgressMonitor());
		}
	}

	public static String getLoadCall(RunFileType mode) {
		String runLoad = "";

		if(mode.equals(RunFileType.SYNCH))
			runLoad += "s.load(null);\n";
		else if (mode.equals(RunFileType.CONSISTENCY_CHECK)){
			runLoad += "s.load(null);\n";
			runLoad += "t.load(null);\n";
		}
		
		return runLoad;
	}

	public static String getCreator(RunFileType mode) {
		String runCreator = "";
		switch (mode) {
		case SYNCH:
			runCreator += "TGGRuntimeUtil tggRuntime = new TGGRuntimeUtil(tgg, s, c, t, p);\n";
			runCreator += "tggRuntime.setMode(OperationMode.FWD);\n";
			break;
		case MODELGEN:
			runCreator += "MODELGENStopCriterion stop = new MODELGENStopCriterion();\n";
			runCreator += "stop.setMaxSrcCount(1000);\n";
			runCreator += "TGGRuntimeUtil tggRuntime = new MODELGEN(tgg, s, c, t, p, stop);\n";
			break;
		case CONSISTENCY_CHECK:
			runCreator += "TGGRuntimeUtil tggRuntime = new CC(tgg, s, c, t, p);\n";
			break;
		default:
			throw new RuntimeException("Did not recognize application mode: " + mode.name());
		}
		return runCreator;
	}

	public static String getSaveCall(RunFileType mode) {
		String runSave = "";

		if(!mode.equals(RunFileType.SYNCH))
			runSave += "s.save(null);\n";
		
		if(!mode.equals(RunFileType.SYNCH))
			runSave += "t.save(null);\n";
		
		runSave += "c.save(null);\n";
		runSave += "p.save(null);\n";

		return runSave;
	}

}
