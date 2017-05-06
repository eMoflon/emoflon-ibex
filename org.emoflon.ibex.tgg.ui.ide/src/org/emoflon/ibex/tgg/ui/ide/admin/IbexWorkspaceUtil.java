package org.emoflon.ibex.tgg.ui.ide.admin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.moflon.util.LogUtils;

public class IbexWorkspaceUtil {
	
	private static final Logger logger = Logger.getLogger(IbexWorkspaceUtil.class);
	
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
	
	public static void openWizard(final String newModelWizardId, final IWorkbenchWindow window) throws CoreException {
		openWizard(newModelWizardId, window, null);
	}

	/**
	 * Opens the specified wizard and initializes it with the given selection.
	 * 
	 * @param newModelWizardId
	 * @param window
	 * @param selection
	 * @throws CoreException
	 */
	public static void openWizard(final String newModelWizardId, final IWorkbenchWindow window,
			final IStructuredSelection selection) throws CoreException {
		// Search for wizard
		IWorkbenchWizard wizard = window.getWorkbench().getNewWizardRegistry().findWizard(newModelWizardId)
				.createWizard();

		// Initialize and open dialogue
		wizard.init(window.getWorkbench(), selection);
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.open();

	}

	/**
	 * Open the default editor for a file in the current workbench page
	 */
	public static void openDefaultEditorForFile(IFile file) {
		Display.getDefault().asyncExec(() -> {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
			try {
				page.openEditor(new FileEditorInput(file), desc.getId());
			} catch (Exception e) {
				LogUtils.error(logger, e, "Unable to open " + file);
			}
		});
	}
}
