package org.emoflon.ibex.tgg.ui.ide.visualisation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.ibex.tgg.ui.ide.transformation.EditorTGGtoFlattenedTGG;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider;

public class IbexDiagramTextProvider implements DiagramTextProvider {

	Logger logger = Logger.getLogger(IbexDiagramTextProvider.class);
	
	@Override
	public String getDiagramText(IEditorPart editor, ISelection selection){
		try {
			return PlantUMLGenerator.wrapInTags(getDiagramBody(editor));
		} catch (Exception e) {
			logger.error("Unable to visualise current selection.");
			return PlantUMLGenerator.wrapInTags(PlantUMLGenerator.emptyDiagram());
		}
	}
	
	private String getDiagramBody(IEditorPart editor) {
		return maybeVisualisePattern(editor).orElse(
			   maybeVisualisePatternBody(editor).orElse(
			   maybeVisualiseTGGRule(editor).orElse(
			   maybeVisualiseMetamodel(editor).orElse(
			   maybeVisualiseModel(editor).orElse(
			   PlantUMLGenerator.emptyDiagram())))));
	}

	private Optional<String> maybeVisualiseModel(IEditorPart editor) {
		return Optional.of(editor)
				.flatMap(this::multiSelectionInEcoreEditor)
				.map(this::determineObjectsAndLinksToVisualise)
				.map(p -> p.getLeft().isEmpty()? null : p) 
				.map(p -> PlantUMLGenerator.visualiseModelElements(p.getLeft(), p.getRight()));
	}

	private Optional<List> multiSelectionInEcoreEditor(IEditorPart editor){
		return Optional.of(editor)
				.flatMap(maybeCast(EcoreEditor.class))
				.map(EcoreEditor::getSelection)
				.flatMap(maybeCast(IStructuredSelection.class))
				.map(IStructuredSelection::toList);
	}
	
	private Optional<String> maybeVisualiseMetamodel(IEditorPart editor) {
		return Optional.of(editor)
				.flatMap(this::multiSelectionInEcoreEditor)
				.map(this::determineClassesAndRefsToVisualise)
				.map(p -> p.getLeft().isEmpty()? null : p)
				.map(p -> PlantUMLGenerator.visualiseEcoreElements(p.getLeft(), p.getRight()));
	}
	
	private Pair<Collection<EObject>, Collection<Pair<String, Pair<EObject, EObject>>>> determineObjectsAndLinksToVisualise(Collection<Object> selection){
		Collection<EObject> chosenObjects = selection
				.stream()
				.filter(EObject.class::isInstance)
				.map(EObject.class::cast)
				.collect(Collectors.toSet());
		
		Collection<Pair<String, Pair<EObject, EObject>>> refs = new HashSet<>();
		for (EObject o : new ArrayList<EObject>(chosenObjects)) {
			for (EContentsEList.FeatureIterator featureIterator = (EContentsEList.FeatureIterator) o.eCrossReferences().iterator(); featureIterator.hasNext();) {
				EObject eObject = (EObject) featureIterator.next();
				EReference eReference = (EReference) featureIterator.feature();
				if(chosenObjects.contains(eObject))
					refs.add(Pair.of(eReference.getName(), Pair.of(o, eObject)));
			}
			for (EContentsEList.FeatureIterator featureIterator = (EContentsEList.FeatureIterator) o.eContents().iterator(); featureIterator.hasNext();) {
				EObject eObject = (EObject) featureIterator.next();
				EReference eReference = (EReference) featureIterator.feature();
				if(chosenObjects.contains(eObject))
					refs.add(Pair.of(eReference.getName(), Pair.of(o, eObject)));
			}
		}
		
		return Pair.of(chosenObjects, refs);
	}
	
	private Pair<Collection<EClass>, Collection<EReference>> determineClassesAndRefsToVisualise(Collection<Object> selection){
		Collection<EClass> chosenClasses = selection
				.stream()
				.filter(EClass.class::isInstance)
				.map(EClass.class::cast)
				.collect(Collectors.toSet());
		
		Collection<EReference> refs = chosenClasses
				.stream()
				.flatMap(c -> c.getEReferences().stream())
				.collect(Collectors.toSet());
		
		return Pair.of(chosenClasses, refs);
	}
	
	private Optional<String> maybeVisualiseTGGRule(IEditorPart editor) {
		return Optional.of(editor)
				.flatMap(maybeCast(XtextEditor.class))
				.map(e -> e.getDocument().readOnly(res -> res.getContents().get(0)))
				.flatMap(maybeCast(TripleGraphGrammarFile.class))
				.map(file -> file.getSchema() == null? file : null)
				.map(this::flattenIfRequested)
				.map(PlantUMLGenerator::visualiseTGGFile);
	}

	private TripleGraphGrammarFile flattenIfRequested(TripleGraphGrammarFile file){
		//TODO[anjorin]: Add a view extension to enable this configurable option
		// For the moment, we always display the flattened TGG file.
		/* if(ToggleRefinementHandler.flattenRefinements()) return flatten(file); */
		
		return flatten(file);
	}
	
	private TripleGraphGrammarFile flatten(TripleGraphGrammarFile file) {
		return new EditorTGGtoFlattenedTGG().flatten(file);
	}

	private Optional<String> maybeVisualisePatternBody(IEditorPart editor) {
		return Optional.of(editor)
				.flatMap(this::selectionInEcoreEditor)
				.flatMap(maybeCast(PatternBody.class))
				.map(b -> PlantUMLGenerator.visualisePatternBody(b, "0\\"));
	}
	
	private Optional<Object> selectionInEcoreEditor(IEditorPart editor){
		return Optional.of(editor)
				.flatMap(maybeCast(EcoreEditor.class))
				.map(EcoreEditor::getSelection)
				.flatMap(maybeCast(TreeSelection.class))
				.map(TreeSelection::getFirstElement);
	}
	
	private Optional<String> maybeVisualisePattern(IEditorPart editor) {
		return Optional.of(editor)
				.flatMap(this::selectionInEcoreEditor)
				.flatMap(maybeCast(Pattern.class))
				.flatMap(this::patternHasExactlyOneBody)					
				.map(p -> PlantUMLGenerator.visualisePatternBody(p.getBodies().get(0), "0\\"));
	}

	private Optional<Pattern> patternHasExactlyOneBody(Pattern p){
		if(p.getBodies().size() != 1) 
			return Optional.empty();
		else
			return Optional.of(p);
	}
	
	@Override
	public boolean supportsEditor(IEditorPart editor) {
		return true;
	}

	private <T> Function<Object, Optional<T>> maybeCast(Class<T> type){
		return (input) -> {
			if(type.isInstance(input)){
				return Optional.of(type.cast(input));
			} else {
				return Optional.empty();
			}
		};
	}

	@Override
	public boolean supportsSelection(ISelection selection) {
		return true;
	}
}
