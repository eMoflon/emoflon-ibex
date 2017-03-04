package org.emoflon.ibex.tgg.ui.ide.visualisation;

import java.util.Optional;

import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.gervarro.democles.specification.emf.Pattern;

import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider;

public class IbexDiagramTextProvider implements DiagramTextProvider {

	@Override
	public String getDiagramText(IEditorPart editor) {
		return Optional.of(editor)
				.filter(EcoreEditor.class::isInstance)
				.map(EcoreEditor.class::cast)
				.map(EcoreEditor::getSelection)
				.filter(TreeSelection.class::isInstance)
				.map(TreeSelection.class::cast)
				.map(TreeSelection::getFirstElement)
				.filter(Pattern.class::isInstance)
				.map(Pattern.class::cast)
				.map(PlantUMLGenerator::visualisePattern)
				.orElse(PlantUMLGenerator.emptyDiagram());
	}

	@Override
	public boolean supportsEditor(IEditorPart editor) {
		return editor instanceof EcoreEditor;
	}

}
