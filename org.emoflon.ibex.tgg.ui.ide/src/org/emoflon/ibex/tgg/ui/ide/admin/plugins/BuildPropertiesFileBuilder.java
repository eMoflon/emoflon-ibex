package org.emoflon.ibex.tgg.ui.ide.admin.plugins;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.util.WorkspaceHelper;

public class BuildPropertiesFileBuilder
{

   private static final String BUILD_PROPERTIES_NAME = "build.properties";

   public void createBuildProperties(final IProject currentProject, final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Creating build.properties", 2);

         IFile file = getBuildPropertiesFile(currentProject);
         if (file.exists())
         {
            // Do not touch existing build.properties
            subMon.worked(2);
         } else
         {
            Properties buildProperties = new Properties();
            buildProperties.put("bin.includes", "META-INF/, bin/, model/, plugin.xml, moflon.properties.xmi");
            buildProperties.put("source..", "src/,gen/");
            buildProperties.put("output..", "bin/");

            subMon.worked(1);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            buildProperties.store(stream, "");

            if (!file.exists())
            {
               WorkspaceHelper.addFile(currentProject, BUILD_PROPERTIES_NAME, stream.toString(), subMon.newChild(1));
            } else
            {
               file.setContents(new ByteArrayInputStream(stream.toByteArray()), true, true, subMon.newChild(1));
            }
         }
      } catch (IOException e)
      {
         throw new CoreException(new Status(IStatus.ERROR, WorkspaceHelper.getPluginId(getClass()), "Error while creating build.properties: " + e.getMessage()));
      } finally
      {
         monitor.done();
      }
   }

   public IFile getBuildPropertiesFile(final IProject currentProject)
   {
      return currentProject.getFile(BUILD_PROPERTIES_NAME);
   }

}
