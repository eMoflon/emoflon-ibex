package org.moflon.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;

/**
 * A collection of useful helper methods.
 * 
 */
public class MoflonUtil
{

   /**
    * Marker for code passages generated through eMoflon/EMF that are eligible for extracting injections.
    */
   public static final String EOPERATION_MODEL_COMMENT = "// [user code injected with eMoflon]";

   /**
    * Code corresponding to the default implementation of a java method. Is used, when no SDM implementation could be
    * retrieved.
    */
   public final static String DEFAULT_METHOD_BODY = "\n" + EOPERATION_MODEL_COMMENT
         + "\n\n// TODO: implement this method here but do not remove the injection marker \nthrow new UnsupportedOperationException();";

   private static final Logger logger = Logger.getLogger(MoflonUtil.class);

   public static String getDefaultPathToEcoreFileInProject(final String projectName)
   {
      return getDefaultPathToFileInProject(projectName, ".ecore");
   }

   public static String getDefaultPathToGenModelInProject(final String projectName)
   {
      return getDefaultPathToFileInProject(projectName, ".genmodel");
   }

   public static String getDefaultPathToFileInProject(final String projectName, final String ending)
   {
      return "model/" + getDefaultNameOfFileInProjectWithoutExtension(projectName) + ending;
   }

   public static String getDefaultNameOfFileInProjectWithoutExtension(final String projectName)
   {
      return MoflonUtil.lastCapitalizedSegmentOf(projectName);
   }

   public static URI getDefaultURIToEcoreFileInPlugin(final String pluginID)
   {
      return URI.createPlatformPluginURI("/" + pluginID + "/" + getDefaultPathToEcoreFileInProject(pluginID), true);
   }

   /**
    * Copies contents of directory named at sourceDir to directory at destination
    * 
    * @param sourceDir
    *           Directory to be copied. Can be located in a jar or in the local filesystem
    * @param destination
    *           Directory to be created
    * @param filter
    *           Specifies what file/subdirectories are to be ignored
    * @throws IOException
    */
   public static void copyDirToDir(final URL sourceDir, final File destination, final FileFilter filter) throws IOException
   {
      if ("file".equals(sourceDir.getProtocol()))
      {
         // Copy from filesystem
         File dir = new File(sourceDir.getFile());
         FileUtils.copyDirectory(dir, destination, filter);
      } else if ("jar".equals(sourceDir.getProtocol()))
      {
         // Copy from jar
         JarURLConnection conn = (JarURLConnection) sourceDir.openConnection();
         JarFile jar = conn.getJarFile();

         destination.mkdir();
         Enumeration<? extends JarEntry> entries = jar.entries();
         while (entries.hasMoreElements())
         {
            JarEntry entry = entries.nextElement();

            if (entry.getName().startsWith(conn.getEntryName()) && !entry.getName().equals(conn.getEntryName()))
            {
               int beginIndex = entry.getName().indexOf(conn.getEntryName()) + conn.getEntryName().length();
               String childPath = entry.getName().substring(beginIndex);

               if (entry.isDirectory())
               {
                  // Handle directory
                  File dir = new File(destination, childPath);
                  if (filter.accept(dir))
                     dir.mkdir();
               } else
               {
                  // Handle file
                  FileUtils.copyInputStreamToFile(jar.getInputStream(entry), new File(destination, childPath));
               }
            }
         }
      }
   }

   /**
    * Derive the java data type of a given Ecore data type.
    * 
    * @param eCoreType
    *           the name of the Ecore data type class (e.g. EString)
    * @return the name of the java type class (e.g. String)
    */
   public static String eCoreTypeToJavaType(final String eCoreType) throws IllegalArgumentException
   {
      String javaType = "";

      // Derive the java data type from the Ecore class name
      try
      {
         javaType = EcorePackage.eINSTANCE.getEClassifier(eCoreType).getInstanceClass().getSimpleName();
      } catch (Exception e)
      {
         logger.debug("Cannot derive Java data type from the given Ecore data type = '" + eCoreType + "'. Using Ecore type instead.");

         javaType = eCoreType;
      }

      return javaType;
   }

   /**
    * Determine fully qualified name of given element by iterating through package hierarchy.
    * 
    * @param ENamedElement
    * @return
    */
   public static String getFQN(final ENamedElement element)
   {
      String fqn = element.getName();

      ENamedElement e = element;

      while (e.eContainer() != null)
      {
         e = (ENamedElement) e.eContainer();
         fqn = e.getName() + "." + fqn;
      }

      return fqn;
   }

   /**
    * Determine fully qualified name of given GenPackage by iterating through package hierarchy.
    * 
    */
   public static String getFQN(final GenPackage genPackage)
   {
      String fqn = genPackage.getPackageName();

      GenPackage p = genPackage;

      while (p.getSuperGenPackage() != null)
      {
         p = p.getSuperGenPackage();
         fqn = p.getPackageName() + "." + fqn;
      }

      return fqn;
   }

   /**
    * This method realizes our convention for determining the package name of a given EPackage.name In the future this
    * could be replaced by using the genmodel.
    * 
    * @param packageName
    * @return
    */
   public static String determinePackageName(final String packageName)
   {
      switch (packageName)
      {
      case "uml":
         return "UMLPackage";

      default:
         return packageName.substring(0, 1).toUpperCase() + packageName.substring(1) + "Package";
      }
   }

   public static boolean toBePluralized(final String roleName, final String packageName, final String typeName, final boolean toOne)
   {
      switch (packageName)
      {
      case "uml":
         // For UML, toMany links are pluralized!
         return !toOne;

      default:
         return false;
      }
   }

   public static String handlePrefixForBooleanAttributes(final String packageName, final String attribute)
   {
      final String is = "is";
      final String prefix = ".is" + StringUtils.capitalize(attribute);

      switch (packageName)
      {
      case "uml":
         // For UML only return prefix if the attribute does not already start with an "is"
         return attribute.startsWith(is) ? "." + attribute : prefix;

      default:
         return prefix;
      }
   }

   public static String lastSegmentOf(final String name)
   {
      int startOfLastSegment = name.lastIndexOf(".");

      if (startOfLastSegment == -1)
         startOfLastSegment = 0;
      else
         startOfLastSegment++;

      return name.substring(startOfLastSegment);
   }

   public static String allSegmentsButLast(final String name)
   {
      int startOfLastSegment = name.lastIndexOf(".");
      return startOfLastSegment == -1 ? "" : name.substring(0, startOfLastSegment);
   }

   public static String lastCapitalizedSegmentOf(final String name)
   {
      return StringUtils.capitalize(lastSegmentOf(name));
   }

   public static final URI lookupProjectURI(final IProject project)
   {
      IPluginModelBase pluginModel = PluginRegistry.findModel(project);
      if (pluginModel != null)
      {
         String pluginID = project.getName();

         if (pluginModel.getBundleDescription() != null)
            pluginID = pluginModel.getBundleDescription().getSymbolicName();

         // Plugin projects in the workspace
         return URI.createPlatformPluginURI(pluginID + "/", true);
      } else
      {
         // Regular projects in the workspace
         return URI.createPlatformResourceURI(project.getName() + "/", true);
      }
   }

   public static final boolean isAPluginDependency(final IProject project, final IProject dependency)
   {
      IPluginModelBase pluginModel = PluginRegistry.findModel(project);
      IPluginModelBase dependencyPlugin = PluginRegistry.findModel(dependency);

      String pluginID = dependency.getName();

      if (pluginModel == null || dependencyPlugin == null)
         return false;

      if (dependencyPlugin.getBundleDescription() != null)
         pluginID = dependencyPlugin.getBundleDescription().getName();

      if (pluginModel.getBundleDescription() == null)
         return false;

      for (BundleSpecification spec : pluginModel.getBundleDescription().getRequiredBundles())
      {
         if (spec.getName().equals(pluginID))
            return true;
      }

      return false;
   }

   /**
    * This function replaces the first matching prefix of the given package name with the corresponding value of the
    * package name map
    * 
    * @param fullyQualifiedPackageName
    *           the package name to be transformed
    * @param packageNameMap
    *           a map from source package name prefix to target package name prefix
    * @return the transformed package
    */
   public static String transformPackageNameUsingImportMapping(final String fullyQualifiedPackageName, final Map<String, String> packageNameMap)
   {
      // Break path up into all segments
      List<String> inputSegments = Arrays.asList(fullyQualifiedPackageName.split(Pattern.quote(".")));
      for (int i = inputSegments.size(); i >= 1; --i)
      {
         final String currentPrefix = joinPackageNameSegments(inputSegments.subList(0, i));
         if (packageNameMap.containsKey(currentPrefix))
         {
            String suffixToKeep = joinPackageNameSegments(inputSegments.subList(i, inputSegments.size()));
            return packageNameMap.get(currentPrefix) + (suffixToKeep.isEmpty() ? "" : "." + suffixToKeep);
         }
      }

      // No prefix match - return input
      return fullyQualifiedPackageName;
   }

   public static String joinPackageNameSegments(final List<String> l)
   {
      return l.stream().collect(Collectors.joining("."));
   }

   /**
    * Formats the given exception for debugging purposes.
    * 
    * If available, the root cause and its stacktrace are formatted. Else, the reason of the exception is shown.
    * 
    * @param e
    *           the exception to be formatted
    * @return the formatted exception
    */
   public static String displayExceptionAsString(final Exception e)
   {
      try
      {
         final String message;
         if (null != e.getCause())
         {
            message = "Cause: " + ExceptionUtils.getRootCauseMessage(e) + "\nStackTrace: " + ExceptionUtils.getStackTrace(ExceptionUtils.getRootCause(e));
         } else
         {
            message = "Reason: " + e.getMessage();
         }
         return message;
      } catch (Exception new_e)
      {
         return e.getMessage();
      }
   }

   public static void throwCoreExceptionAsError(final String message, final String plugin, final Exception lowLevelException) throws CoreException
   {
      IStatus status = new Status(IStatus.ERROR, plugin, IStatus.OK, message, lowLevelException);
      throw new CoreException(status);
   }

   /**
    * Writes the given string to file.
    * 
    * If the file does not exist, it gets created
    * 
    * @param content
    *           the new file content
    * @param file
    *           the file
    * @param monitor
    * @throws CoreException
    */
   public static void writeContentToFile(final String content, final IFile file, final IProgressMonitor monitor) throws CoreException
   {
      SubMonitor subMon = SubMonitor.convert(monitor, "Write to file", 1);
      if (!file.exists())
      {
         file.create(new ByteArrayInputStream(content.getBytes()), true, subMon.newChild(1));
      } else
      {
         file.setContents(new ByteArrayInputStream(content.getBytes()), true, true, subMon.newChild(1));
      }
   }
}
