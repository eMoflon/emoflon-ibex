package org.emoflon.ibex.tgg.ui.ide.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.ibex.tgg.ui.ide.transformation.EditorTGGtoFlattenedTGG;
import org.moflon.tgg.mosl.defaults.AttrCondDefLibraryProvider;
import org.moflon.tgg.mosl.defaults.RunFileHelper;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;
import org.moflon.util.LogUtils;

public class IbexTGGBuilder extends IncrementalProjectBuilder implements IResourceDeltaVisitor {
	public static final String INTERNAL_TGG_MODEL_EXTENSION = ".tgg.xmi";
	public static final String ECORE_FILE_EXTENSION = ".ecore";
	public static final String TGG_FILE_EXTENSION = ".tgg";
	public static final String EDITOR_MODEL_EXTENSION = ".editor.xmi";
	public static final String EDITOR_FLATTENED_MODEL_EXTENSION = "_flattened.editor.xmi";
	public static final String SRC_FOLDER = "src";
	public static final String MODEL_FOLDER = "model";

	public static final Logger logger = Logger.getLogger(IbexTGGBuilder.class);

	private boolean buildIsNecessary = false;
	
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		switch (kind) {
		case CLEAN_BUILD:
			performClean();
			break;
		case FULL_BUILD:
			performClean();
			generateFiles();
			break;
		case AUTO_BUILD:
		case INCREMENTAL_BUILD:
			generateFilesIfchangeIsRelevant();
			break;
		default:
			break;
		}

		return null;
	}

	private void generateFilesIfchangeIsRelevant() throws CoreException {
		getDelta(getProject()).accept(this);
		
		if(buildIsNecessary)
			generateFiles();
		
		buildIsNecessary = false;		
	}

	private void generateFiles() {
		generateEditorModel().ifPresent(editorModel -> 
		{
			generateFlattenedEditorModel(editorModel);
			generateInternalModels(editorModel);
			generateAttrCondLibsAndStubs();
			generateRunFiles();
		});
	}
	
	private void generateFlattenedEditorModel(TripleGraphGrammarFile editorModel) {
		EditorTGGtoFlattenedTGG flattener = new EditorTGGtoFlattenedTGG();
		TripleGraphGrammarFile flattenedTGG = flattener.flatten(editorModel);

		try {
			ResourceSet rs = editorModel.eResource().getResourceSet();
			IFile tggFile = getProject().getFolder(IbexTGGBuilder.MODEL_FOLDER).getFile(getProject().getName() + EDITOR_FLATTENED_MODEL_EXTENSION);
			saveModelInProject(tggFile, rs, flattenedTGG);
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
	}

	private void generateRunFiles() {
		try {
			new RunFileHelper(getProject()).createFiles();
		} catch (CoreException | IOException e) {
			LogUtils.error(logger, e);
		}
	}

	private void generateAttrCondLibsAndStubs() {
		try {
			AttrCondDefLibraryProvider.syncAttrCondDefLibrary(getProject());
			//FIXME [extract GUI component] Call generateAttrCondLibsAndStubs from extension point
			// Should now be handled by org.emoflon.ibex.tgg.compiler.defaults.AttrCondDefLibraryProvider
		} catch (CoreException | IOException e) {
			LogUtils.error(logger, e);
		}
	}

	private Optional<TripleGraphGrammarFile> generateEditorModel() {
		try {
			XtextResourceSet resourceSet = new XtextResourceSet();
			IFile schemaFile = getProject().getFile(IbexTGGNature.SCHEMA_FILE);
			if (schemaFile.exists()) {
				XtextResource schemaResource = loadSchema(resourceSet, schemaFile);
				if (schemaIsOfExpectedType(schemaResource)) {
					TripleGraphGrammarFile xtextParsedTGG = (TripleGraphGrammarFile) schemaResource.getContents().get(0);
					loadAllRulesToTGGFile(xtextParsedTGG, resourceSet, getProject().getFolder(SRC_FOLDER));
					addAttrCondDefLibraryReferencesToSchema(xtextParsedTGG);
					IFile editorFile = getProject().getFolder(IbexTGGBuilder.MODEL_FOLDER).getFile(getProject().getName() + EDITOR_MODEL_EXTENSION);
					saveModelInProject(editorFile, resourceSet, xtextParsedTGG);
					return Optional.of(xtextParsedTGG);
				}
			}
		} catch (CoreException | IOException e) {
			LogUtils.error(logger, e);
		}

		return Optional.empty();
	}

	private void addAttrCondDefLibraryReferencesToSchema(TripleGraphGrammarFile xtextParsedTGG) {
		EList<AttrCondDef> usedAttrCondDefs = new BasicEList<AttrCondDef>();
		for (Rule rule : xtextParsedTGG.getRules()) {
			for (AttrCond attrCond : rule.getAttrConditions()) {
				if (!usedAttrCondDefs.contains(attrCond.getName()) && !attrCond.getName().isUserDefined()) {
					usedAttrCondDefs.add(attrCond.getName());
				}
			}
		}
		xtextParsedTGG.getSchema().getAttributeCondDefs().addAll(usedAttrCondDefs);
	}

	private void loadAllRulesToTGGFile(TripleGraphGrammarFile xtextParsedTGG, XtextResourceSet resourceSet,
			IFolder root) throws CoreException, IOException {
		Collection<Rule> rules = new ArrayList<Rule>();
		for (IResource iResource : root.members()) {
			if (iResource instanceof IFile) {
				Collection<Rule> rulesFromFile = loadRules(iResource, resourceSet);
				rules.addAll(rulesFromFile);
			} else if (iResource instanceof IFolder) {
				loadAllRulesToTGGFile(xtextParsedTGG, resourceSet, IFolder.class.cast(iResource));
			}
		}
		xtextParsedTGG.getRules().addAll(rules);
	}

	private Collection<Rule> loadRules(IResource iResource, XtextResourceSet resourceSet) throws IOException {
		IFile ruleFile = (IFile) iResource;
		if (ruleFile.getName().endsWith(TGG_FILE_EXTENSION)) {
			XtextResource ruleRes = (XtextResource) resourceSet
					.getResource(URI.createPlatformResourceURI(ruleFile.getFullPath().toString(), true), true);
			EcoreUtil.resolveAll(resourceSet);

			EObject tggFile = ruleRes.getContents().get(0);
			if (tggFile instanceof TripleGraphGrammarFile) {
				TripleGraphGrammarFile tggFileWithRules = (TripleGraphGrammarFile) tggFile;
				return new ArrayList<Rule>(tggFileWithRules.getRules());
			}
		}

		return Collections.<Rule>emptyList();
	}

	private boolean schemaIsOfExpectedType(XtextResource schemaResource) {
		return schemaResource.getContents().size() == 1
				&& schemaResource.getContents().get(0) instanceof TripleGraphGrammarFile;
	}

	private XtextResource loadSchema(XtextResourceSet resourceSet, IFile schemaFile) throws IOException {
		XtextResource schemaResource = (XtextResource) resourceSet
				.createResource(URI.createPlatformResourceURI(schemaFile.getFullPath().toString(), false));
		schemaResource.load(null);
		EcoreUtil.resolveAll(resourceSet);
		return schemaResource;
	}

	private void generateInternalModels(TripleGraphGrammarFile xtextParsedTGG) {
		//FIXME [extract GUI component] Delegate to extension point
		// Should now be handled by core.transformation.EditorTGGtoInternalTGG.generateInternalModels(TripleGraphGrammarFile, IProject)
	}

	public static void saveModelInProject(IFile file, ResourceSet rs, EObject model) throws IOException {
		URI uri = URI.createPlatformResourceURI(file.getProject().getName() + "/" + file.getProjectRelativePath().toString(), true);
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

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		if(buildIsNecessary)
			return false;
		
		if (isTggFileToBeCompiled(delta)) {
			buildIsNecessary = true;
			return false;
		}
		
		return true;
	}
	
	private boolean isTggFileToBeCompiled(IResourceDelta delta) {
		return delta.getResource().getName().endsWith(TGG_FILE_EXTENSION)
				&& !delta.getResource().getProjectRelativePath().toString().startsWith("bin/");
	}

	private void performClean() {
		List<String> toDelete = Arrays.asList(MODEL_FOLDER + getProject().getName() + ECORE_FILE_EXTENSION,
				MODEL_FOLDER + getProject().getName() + EDITOR_MODEL_EXTENSION,
				MODEL_FOLDER + getProject().getName() + INTERNAL_TGG_MODEL_EXTENSION);
		toDelete.stream().map(f -> getProject().getFile(f)).filter(IFile::exists).forEach(f -> {
			try {
				f.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				LogUtils.error(logger, e);
			}
		});
	}
}
