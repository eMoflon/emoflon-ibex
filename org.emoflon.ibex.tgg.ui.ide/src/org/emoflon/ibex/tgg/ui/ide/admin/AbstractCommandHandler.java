package org.emoflon.ibex.tgg.ui.ide.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

/**
 * Super class for all command handlers.
 *
 */
public abstract class AbstractCommandHandler extends org.eclipse.core.commands.AbstractHandler
{

   protected Logger logger;

   public AbstractCommandHandler() {
      this.logger = Logger.getLogger(this.getClass());
   }

   /**
    * Tries to extract the project(s) from the given element.
    * 
    * For files, the containing project is returned.
    * For working sets, the contained projects are returned
    * 
    * @param selectionIterator
    * @return the containing projects (if exists), otherwise an empty list
    */
   protected static List<IProject> getProjects(final Object element)
   {
      final List<IProject> projects = new ArrayList<>();
      if (element instanceof IResource)
      {
         final IResource resource = (IResource) element;
         final IProject project = resource.getProject();
         projects.add(project);
      }
      else if (element instanceof IJavaElement)
      {
         final IJavaElement javaElement = (IJavaElement) element; 
         final IProject project = javaElement.getJavaProject().getProject();
         projects.add(project);
      }
      else if (element instanceof IWorkingSet)
      {
         final IWorkingSet workingSet = (IWorkingSet) element;
         for (final IAdaptable elementInWorkingSet : workingSet.getElements()) {
            if (elementInWorkingSet instanceof IProject)
            {
               projects.add((IProject)elementInWorkingSet);
            }
         }
      }
      return projects;
   }

   protected static IFile getEditedFile(final ExecutionEvent event)
   {
      return (IFile)HandlerUtil.getActiveEditor(event).getEditorInput().getAdapter(IFile.class);
   }

   public void openInEditor(final IFile targetFile) throws CoreException, PartInitException
   {
      IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put(IMarker.LINE_NUMBER, new Integer(1));
      IMarker marker;
   
      marker = targetFile.createMarker(IMarker.TEXT);
   
      marker.setAttributes(map);
      IDE.openEditor(page, targetFile);
      marker.delete();
   }

   static Collection<IProject> getProjectsFromSelection(final IStructuredSelection selection)
   {
      final List<IProject> projects = new ArrayList<>();
      if (selection instanceof StructuredSelection)
      {
         final StructuredSelection structuredSelection = (StructuredSelection) selection;
         for (final Iterator<?> selectionIterator = structuredSelection.iterator(); selectionIterator.hasNext();)
         {
            projects.addAll(getProjects(selectionIterator.next()));
         }
      }
   
      return projects;
   }
}
