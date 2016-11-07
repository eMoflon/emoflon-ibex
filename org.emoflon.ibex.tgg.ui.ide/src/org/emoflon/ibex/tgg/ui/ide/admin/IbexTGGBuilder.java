package org.emoflon.ibex.tgg.ui.ide.admin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.ibex.tgg.core.compiler.TGGCompiler;
import org.emoflon.ibex.tgg.ui.ide.transformation.EditorTGGtoInternalTGG;
import org.emoflon.ibex.tgg.ui.ide.transformation.TGGProject;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.tgg.mosl.defaults.AttrCondDefLibraryProvider;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttrCondDef;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

public class IbexTGGBuilder extends IncrementalProjectBuilder implements IResourceDeltaVisitor {
	private static final String INTERNAL_TGG_MODEL_EXTENSION = ".tgg.xmi";
	private static final String ECORE_FILE_EXTENSION = ".ecore";
	private static final String VIATRA_QUERY_FILE_EXTENSION = ".vql";
	private static final String TGG_FILE_EXTENSION = ".tgg";
	private static final String EDITOR_MODEL_EXTENSION = ".editor.xmi";
	private static final String SRC_FOLDER = "src";
	private static final String MODEL_FOLDER = "model";
	private static final String MODEL_PATTERNS_FOLDER = MODEL_FOLDER + "/patterns";

	public static final Logger logger = Logger.getLogger(IbexTGGBuilder.class);
	
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		switch (kind) {
		case CLEAN_BUILD:
			performClean();
			break;
		case FULL_BUILD:
			generateFiles();
			break;
		case AUTO_BUILD:
		case INCREMENTAL_BUILD:
			generateFilesIfchangeIsRelevant();
		default:
			break;
		}

		return null;
	}

	private void generateFilesIfchangeIsRelevant() throws CoreException {
		getDelta(getProject()).accept(this);
	}

	private void generateFiles() {
		performClean();
		generateEditorModel().ifPresent(editorModel -> 
		generateInternalModels(editorModel).ifPresent(internalModel ->
		generatePatterns(internalModel)));
		generateAttrCondLib();
	}

	private void generateAttrCondLib() {
		try {
			AttrCondDefLibraryProvider.syncAttrCondDefLibrary(getProject());
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
					TripleGraphGrammarFile xtextParsedTGG = (TripleGraphGrammarFile) schemaResource.getContents()
							.get(0);
					loadAllRulesToTGGFile(xtextParsedTGG, resourceSet, getProject().getFolder(SRC_FOLDER));
					addAttrCondDefLibraryReferencesToSchema(xtextParsedTGG);
					saveModelInProject(MODEL_FOLDER, getProject().getName() + EDITOR_MODEL_EXTENSION, resourceSet, xtextParsedTGG);
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

	private Collection<Rule> loadRules(IResource iResource, XtextResourceSet resourceSet)
			throws IOException {
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

	private void generatePatterns(TGGProject tggProject) {
		TGGCompiler compiler = new TGGCompiler();
		tggProject.getTggModel().getRules().forEach(r -> {
			String contents = compiler.getViatraPatterns(r);			
			IFile file = getProject().getFolder(MODEL_PATTERNS_FOLDER).getFile(r.getName() + VIATRA_QUERY_FILE_EXTENSION);
			try {
				if (file.exists()) file.delete(true, new NullProgressMonitor());
				WorkspaceHelper.addAllFoldersAndFile(getProject(), file.getProjectRelativePath(), contents, new NullProgressMonitor());
			} catch (CoreException e) {
				LogUtils.error(logger, e);
			}
		});		
	}

	private Optional<TGGProject> generateInternalModels(TripleGraphGrammarFile xtextParsedTGG) {
		EditorTGGtoInternalTGG converter = new EditorTGGtoInternalTGG();
		TGGProject tggProject = converter.convertXtextTGG(xtextParsedTGG);
		
		try {
			ResourceSet rs = xtextParsedTGG.eResource().getResourceSet();
			saveModelInProject(MODEL_FOLDER, getProject().getName() + ECORE_FILE_EXTENSION, rs, tggProject.getCorrPackage());
			saveModelInProject(MODEL_FOLDER, getProject().getName() + INTERNAL_TGG_MODEL_EXTENSION, rs, tggProject.getTggModel());
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
		
		return Optional.of(tggProject);
	}

	private void saveModelInProject(String folder, String fileName, ResourceSet rs, EObject model) throws IOException{
		IFile file = getProject().getFolder(folder).getFile(fileName);
		URI uri = URI.createPlatformResourceURI(
				getProject().getName() + "/" + file.getProjectRelativePath().toString(), true);
		Resource resource = rs.createResource(uri);
		resource.getContents().add(model);
		resource.save(null);
	}

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		if (delta.getResource().getName().endsWith(TGG_FILE_EXTENSION)) {
			generateFiles();
			return false;
		}

		return true;
	}
	
	private void performClean() {
		try {
			if(getProject().getFolder(MODEL_FOLDER).exists()){
				WorkspaceHelper.clearFolder(getProject(), MODEL_FOLDER, new NullProgressMonitor());
				if(getProject().getFolder(MODEL_PATTERNS_FOLDER).exists())
					WorkspaceHelper.clearFolder(getProject(), MODEL_PATTERNS_FOLDER, new NullProgressMonitor());
			}
		} catch (CoreException | URISyntaxException | IOException e) {
			LogUtils.error(logger, e);
		}
	}
}
