package org.emoflon.ibex.common.visualization;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;
import org.moflon.core.ui.VisualiserUtilities;
import org.moflon.core.ui.visualisation.common.EMoflonDiagramTextProvider;

/**
 * The IBeXPatternVisualizer provides a PlantUML visualization of an
 * IBeXPatternSet or a single pattern.
 */
public class IBeXPatternVisualizer implements EMoflonDiagramTextProvider {

	@Override
	public String getDiagramBody(final IEditorPart editor, final ISelection selection) {
		if (selection instanceof IStructuredSelection structuredSelection) {
			return visualizeSelection(structuredSelection);
		}

		throw new IllegalArgumentException("Invalid selection: " + selection);
	}

	/**
	 * Visualizes the first selected element
	 * 
	 * @param selection the selection
	 * @return the PlantUMl code for the visualization
	 */
	private static String visualizeSelection(final IStructuredSelection selection) {
		Object element = selection.getFirstElement();

		if (isPatternSet(element)) {
			return IBeXPatternPlantUMLGenerator.visualizePatternInvocations((IBeXPatternSet) element);
		}

		if (isPattern(element)) {
			return IBeXPatternPlantUMLGenerator.visualizeContextPattern((GTPattern) element);
		}

		if (isRule(element)) {
			return IBeXPatternPlantUMLGenerator.visualizeRule((GTRule) element);
		}

		throw new IllegalArgumentException("Invalid selection: " + element);
	}

	@Override
	public boolean supportsEditor(final IEditorPart editor) {
		return Optional.of(editor) //
				.flatMap(maybeCast(EcoreEditor.class)) //
				.map(EcoreEditor::getSelection) //
				.flatMap(maybeCast(TreeSelection.class)) //
				.map(TreeSelection::getFirstElement) //
				.filter(o -> isPatternSet(o) || isPattern(o) || isRule(o)) //
				.isPresent();
	}

	@Override
	public boolean supportsSelection(ISelection selection) {
		Collection<EObject> elements = VisualiserUtilities.extractEcoreSelection(selection);
		if (elements == null)
			return false;

		return elements.stream()
				.filter(elt -> elt.eClass().getEPackage().getName().contains("org.emoflon.ibex.patternmodel")).findAny()
				.isPresent();
	}

	private static boolean isPatternSet(final Object object) {
		return object instanceof IBeXPatternSet;
	}

	private static boolean isPattern(final Object object) {
		return object instanceof GTPattern;
	}

	private static boolean isRule(final Object object) {
		return object instanceof GTRule;
	}

}
