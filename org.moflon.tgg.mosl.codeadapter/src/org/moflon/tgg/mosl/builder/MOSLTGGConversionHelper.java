package org.moflon.tgg.mosl.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.tgg.mosl.defaults.AttrCondDefLibraryProvider;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

import language.TGG;

public class MOSLTGGConversionHelper
{
   private static Logger logger = Logger.getLogger(MOSLTGGConversionHelper.class);


   public Resource generateTGGModel(IResource resource)
   {
      try
      {
         IProject project = resource.getProject();

         IFolder moslFolder = IFolder.class.cast(resource);
         XtextResourceSet resourceSet = new XtextResourceSet();

         AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);

         TripleGraphGrammarFile xtextParsedTGG = createTGGFileAndLoadSchema(resourceSet, moslFolder);
         if (xtextParsedTGG != null)
         {
            loadAllRulesToTGGFile(xtextParsedTGG, resourceSet, moslFolder);
            addAttrCondDefLibraryReferencesToSchema(xtextParsedTGG);

            // Save intermediate result of Xtext parsing
            Map<Object, Object> options = new HashMap<Object, Object>();

            // Invoke transformation to produce TGG model
            MOSLTGGtoInternalTGG converter = new MOSLTGGtoInternalTGG();
            TGGProject tggProject = converter.convertXtextTGG(xtextParsedTGG);

            if (tggProject != null)
            {
               return saveInternalTGGModelToXMI(tggProject, resourceSet, options, project.getName());
            }
         }
      } catch (Exception e)
      {
         LogUtils.error(logger, e);
      }
      return null;
   }

   private void addAttrCondDefLibraryReferencesToSchema(TripleGraphGrammarFile xtextParsedTGG)
   {
      EList<AttrCondDef> usedAttrCondDefs = new BasicEList<AttrCondDef>();
      for (Rule rule : xtextParsedTGG.getRules())
      {
         for (AttrCond attrCond : rule.getAttrConditions())
         {
            if (!usedAttrCondDefs.contains(attrCond.getName()) && !attrCond.getName().isUserDefined())
            {
               usedAttrCondDefs.add(attrCond.getName());
            }
         }
      }
      xtextParsedTGG.getSchema().getAttributeCondDefs().addAll(usedAttrCondDefs);
   }

   private void loadAllRulesToTGGFile(TripleGraphGrammarFile xtextParsedTGG, XtextResourceSet resourceSet, IFolder moslFolder) throws CoreException, IOException
   {
      if (moslFolder.exists())
      {
         EList<Rule> rules = new BasicEList<Rule>();

         for (IResource iResource : moslFolder.members())
         {
            if (iResource instanceof IFile)
            {
               Collection<Rule> rulesFromFile = loadRules(iResource, resourceSet, moslFolder);
               rules.addAll(rulesFromFile);
            } else if (iResource instanceof IFolder)
            {
               loadAllRulesToTGGFile(xtextParsedTGG, resourceSet, IFolder.class.cast(iResource));
            }
         }
         xtextParsedTGG.getRules().addAll(rules);
      }

   }

   private Collection<Rule> loadRules(IResource iResource, XtextResourceSet resourceSet, IFolder moslFolder) throws IOException
   {
      IFile ruleFile = (IFile) iResource;
      if (ruleFile.getFileExtension().equals("tgg"))
      {
         XtextResource ruleRes = (XtextResource) resourceSet.getResource(URI.createPlatformResourceURI(ruleFile.getFullPath().toString(), true), true);
         EcoreUtil.resolveAll(resourceSet);

         EObject tggFile = ruleRes.getContents().get(0);
         if (tggFile instanceof TripleGraphGrammarFile)
         {
            TripleGraphGrammarFile tggFileWithRules = (TripleGraphGrammarFile) tggFile;
            return new ArrayList<Rule>(tggFileWithRules.getRules());
         }
      }
      return Collections.<Rule> emptyList();
   }

   private TripleGraphGrammarFile createTGGFileAndLoadSchema(XtextResourceSet resourceSet, IFolder moslFolder) throws IOException, CoreException
   {
      IFile schemaFile = moslFolder.getFile("Schema.tgg");

      if (schemaFile.exists())
      {
         XtextResource schemaResource = (XtextResource) resourceSet.createResource(URI.createPlatformResourceURI(schemaFile.getFullPath().toString(), false));
         schemaResource.load(null);
         EcoreUtil.resolveAll(resourceSet);
         return (TripleGraphGrammarFile) schemaResource.getContents().get(0);
      } else
      {
         for (IResource iResource : moslFolder.members())
         {
            if (iResource instanceof IFile)
            {
               schemaFile = (IFile) iResource;
               if (schemaFile.getFileExtension().equals("tgg"))
               {
                  XtextResource schemaResource = (XtextResource) resourceSet
                        .createResource(URI.createPlatformResourceURI(schemaFile.getFullPath().toString(), false));
                  schemaResource.load(null);
                  EcoreUtil.resolveAll(resourceSet);
                  TripleGraphGrammarFile tgg = (TripleGraphGrammarFile) schemaResource.getContents().get(0);
                  if (tgg.getSchema() != null)
                     return tgg;
               }
            }
         }
         return null;
      }
   }

   private Resource saveInternalTGGModelToXMI(TGGProject tggProject, XtextResourceSet resourceSet, Map<Object, Object> options, String saveTargetName)
         throws IOException, CoreException
   {
      TGG tgg = tggProject.getTggModel();
      EPackage corrPackage = tggProject.getCorrPackage();

      String file = StringUtils.substringAfterLast(saveTargetName, ".");

      if (file.isEmpty())
      {
         file = StringUtils.capitalize(saveTargetName);
      } else
      {
         file = StringUtils.capitalize(file);
      }

      final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(saveTargetName);
      CodeGeneratorPlugin.createPluginToResourceMapping(resourceSet, project);
      URI relativePreEcoreXmiURI = URI.createURI(MoflonUtil.getDefaultPathToFileInProject(file, ".pre.ecore"));
      URI projectURI = CodeGeneratorPlugin.lookupProjectURI(project);
      URI preEcoreXmiURI = relativePreEcoreXmiURI.resolve(projectURI);
      Resource preEcoreResource = resourceSet.createResource(preEcoreXmiURI);
      preEcoreResource.getContents().add(corrPackage);
      preEcoreResource.save(options);

      URI pretggXmiURI = URI.createPlatformResourceURI(saveTargetName + "/" + MoflonUtil.getDefaultPathToFileInProject(file, ".pre.tgg.xmi"), false);
      Resource pretggXmiResource = resourceSet.createResource(pretggXmiURI);
      pretggXmiResource.getContents().add(tgg);
      pretggXmiResource.save(options);
      return preEcoreResource;
   }

   private void saveXtextTGGModelToTGGFile(TripleGraphGrammarFile tggModel, IProject project, String filePath) throws IOException, CoreException
   {
      URI tggFileURI = URI.createPlatformResourceURI(project.getFullPath().toString() + filePath, true);

      XtextResourceSet xtextResourceSet = new XtextResourceSet();
      XtextResource xtextResource = (XtextResource) xtextResourceSet.createResource(tggFileURI);
      AttrCondDefLibraryProvider.syncAttrCondDefLibrary(project);

      xtextResource.getContents().add(tggModel);
      EcoreUtil.resolveAll(xtextResource);

      SaveOptions.Builder options = SaveOptions.newBuilder();
      options.format();
      options.noValidation();
      xtextResource.save(options.getOptions().toOptionsMap());
   }

}
