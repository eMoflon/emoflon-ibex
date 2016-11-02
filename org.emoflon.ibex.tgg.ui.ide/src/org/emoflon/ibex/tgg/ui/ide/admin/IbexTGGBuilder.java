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
		generateEditorModel().ifPresent(editorModel -> 
		generateInternalModels(editorModel).ifPresent(internalModel ->
		generatePatterns(internalModel)));
	}

	private Optional<TripleGraphGrammarFile> generateEditorModel() {
		try {
			AttrCondDefLibraryProvider.syncAttrCondDefLibrary(getProject());
			XtextResourceSet resourceSet = new XtextResourceSet();
			IFile schemaFile = getProject().getFile("Schema.tgg");
			if (schemaFile.exists()) {
				XtextResource schemaResource = loadSchema(resourceSet, schemaFile);
				if (schemaIsOfExpectedType(schemaResource)) {
					TripleGraphGrammarFile xtextParsedTGG = (TripleGraphGrammarFile) schemaResource.getContents()
							.get(0);
					loadAllRulesToTGGFile(xtextParsedTGG, resourceSet, getProject().getFolder("src"));
					addAttrCondDefLibraryReferencesToSchema(xtextParsedTGG);
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
		if (ruleFile.getFileExtension().equals("tgg")) {
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
		String contents = TGGCompiler.getViatraPatterns(tggProject.getTggModel());
		IFile file = getProject().getFolder("model").getFile(tggProject.getTggModel().getName() + ".vql");
		try {
			if (file.exists())
				file.delete(true, new NullProgressMonitor());
			WorkspaceHelper.addFile(getProject(), file.getProjectRelativePath().toString(), contents,
					new NullProgressMonitor());
		} catch (CoreException e) {
			LogUtils.error(logger, e);
		}
	}

	private Optional<TGGProject> generateInternalModels(TripleGraphGrammarFile xtextParsedTGG) {
		EditorTGGtoInternalTGG converter = new EditorTGGtoInternalTGG();
		TGGProject tggProject = converter.convertXtextTGG(xtextParsedTGG);
		
		try {
			IFile corrFile = getProject().getFolder("model").getFile("correspondenceModel.xmi");
			URI preEcoreXmiURI = URI.createPlatformResourceURI(
					getProject().getName() + "/" + corrFile.getProjectRelativePath().toString(), true);
			Resource preEcoreResource = xtextParsedTGG.eResource().getResourceSet().createResource(preEcoreXmiURI);
			preEcoreResource.getContents().add(tggProject.getCorrPackage());
			preEcoreResource.save(null);

			IFile tggFile = getProject().getFolder("model").getFile("tripleGraphGrammar.xmi");
			URI pretggXmiURI = URI.createPlatformResourceURI(
					getProject().getName() + "/" + tggFile.getProjectRelativePath().toString(), true);
			Resource pretggXmiResource = xtextParsedTGG.eResource().getResourceSet().createResource(pretggXmiURI);
			pretggXmiResource.getContents().add(tggProject.getTggModel());
			pretggXmiResource.save(null);
		} catch (IOException e) {
			LogUtils.error(logger, e);
		}
		
		return Optional.of(tggProject);
	}


	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		if (delta.getResource().getName().endsWith(".tgg")) {
			generateFiles();
			return false;
		}

		return true;
	}
	
	private void performClean() {
		try {
			WorkspaceHelper.clearFolder(getProject(), "model", new NullProgressMonitor());
		} catch (CoreException | URISyntaxException | IOException e) {
			LogUtils.error(logger, e);
		}
	}
}
