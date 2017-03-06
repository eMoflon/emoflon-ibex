package org.emoflon.ibex.tgg.ui.ide.visualisation;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.gervarro.democles.specification.emf.Pattern;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider;

public class IbexDiagramTextProvider implements DiagramTextProvider {

	@Override
	public String getDiagramText(IEditorPart editor) {
		return maybeVisualisePattern(editor)
			   .orElse(maybeVisualiseTGGRule(editor)
	           .orElse(PlantUMLGenerator.emptyDiagram()));
	}

	private Optional<String> maybeVisualiseTGGRule(IEditorPart editor) {
		return Optional.of(editor)
				.flatMap(maybeCast(XtextEditor.class))
				.map(e -> e.getDocument().readOnly(res -> res.getContents().get(0)))
				.flatMap(maybeCast(TripleGraphGrammarFile.class))
				.map(PlantUMLGenerator::visualiseTGGFile);
	}

	private Optional<String> maybeVisualisePattern(IEditorPart editor) {
		return Optional.of(editor)
				.flatMap(maybeCast(EcoreEditor.class))
				.map(EcoreEditor::getSelection)
				.flatMap(maybeCast(TreeSelection.class))
				.map(TreeSelection::getFirstElement)
				.flatMap(maybeCast(Pattern.class))
				.map(PlantUMLGenerator::visualisePattern);
	}

	@Override
	public boolean supportsEditor(IEditorPart editor) {
		return editor instanceof EcoreEditor;
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
}
