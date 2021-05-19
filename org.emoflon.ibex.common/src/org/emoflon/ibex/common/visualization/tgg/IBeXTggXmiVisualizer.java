package org.emoflon.ibex.common.visualization.tgg;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.moflon.core.ui.VisualiserUtilities;
import org.moflon.core.ui.visualisation.common.EMoflonDiagramTextProvider;

import language.TGG;
import language.TGGRule;
import language.repair.ExternalShortcutRule;
import language.repair.TGGRuleElementMapping;

public class IBeXTggXmiVisualizer implements EMoflonDiagramTextProvider {

	@Override
	public String getDiagramBody(IEditorPart editor, ISelection selection) {
		if (selection instanceof IStructuredSelection)
			return visualizeSelection((IStructuredSelection) selection);

		throw new IllegalArgumentException("Invalid selection: " + selection);
	}

	@SuppressWarnings("unchecked")
	private String visualizeSelection(IStructuredSelection selection) {
		Object element = selection.getFirstElement();
		if (element instanceof TGG)
			return IBeXTggXmiPlantUMLGenerator.visualizeTGG((TGG) element);
		if (element instanceof TGGRule)
			return IBeXTggXmiPlantUMLGenerator.visualizeTGGRule((TGGRule) element);
		if (element instanceof ExternalShortcutRule)
			return IBeXTggXmiPlantUMLGenerator.visualizeSCRuleMerged((ExternalShortcutRule) element);
		if (element instanceof TGGRuleElementMapping) {
			if (selection.size() <= 1)
				return IBeXTggXmiPlantUMLGenerator.visualizeMapping((TGGRuleElementMapping) element);
			else if (selection.toList().stream().allMatch(e -> e instanceof TGGRuleElementMapping))
				return IBeXTggXmiPlantUMLGenerator.visualizeMappings(selection.toList());
		}

		throw new IllegalArgumentException("Invalid selection: " + selection);
	}
	
	@Override
	public boolean supportsSelection(ISelection selection) {
		Collection<EObject> elements = VisualiserUtilities.extractEcoreSelection(selection);
		if(elements == null)
			return false;

		return elements.stream().filter(elt -> elt.eClass().getEPackage().getName().contains("language")).findAny().isPresent();
	}

	@Override
	public boolean supportsEditor(final IEditorPart editor) {
		return Optional.of(editor) //
				.flatMap(maybeCast(EcoreEditor.class)) //
				.map(EcoreEditor::getSelection) //
				.flatMap(maybeCast(TreeSelection.class)) //
				.map(TreeSelection::getFirstElement) //
				.filter(elt -> elt instanceof TGG || elt instanceof TGGRule || elt instanceof ExternalShortcutRule
						|| elt instanceof TGGRuleElementMapping) //
				.isPresent();
	}

}
