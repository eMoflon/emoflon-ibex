package org.emoflon.ibex.tgg.ui.ide.admin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

public class IbexWorkspaceUtil {
	
	/**
	 * First of all checks if content is the same as the current targetFile and only saves if this is not the case.
	 * The file targetFile is assumed to exist! 
	 * @param content
	 * @param targetFile
	 * @throws CoreException
	 * @throws IOException 
	 */
	public static void saveIfNecessary(String content, IFile targetFile) throws CoreException, IOException{
		String oldContent = getContent(targetFile);
		
		if(!normalise(content).equals(normalise(oldContent)))
			setContent(content, targetFile);
	}

	private static String normalise(String content) {
		return content.trim().replaceAll("\\s+","\n");
	}

	private static void setContent(String content, IFile targetFile) throws CoreException {
		final ByteArrayInputStream source = new ByteArrayInputStream(content.getBytes());
		targetFile.setContents(source, IFile.FORCE | IFile.KEEP_HISTORY, new NullProgressMonitor());
	}

	private static String getContent(IFile targetFile) throws CoreException, IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(targetFile.getContents()));
		String content = r.lines().collect(Collectors.joining("\n"));
		r.close();
		return content;
	}
}
