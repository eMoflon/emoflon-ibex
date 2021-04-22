package org.emoflon.ibex.common.visualization;

import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.moflon.core.ui.VisualiserUtilities;
import org.moflon.core.ui.visualisation.common.EMoflonDiagramTextProvider;

/**
 * The IBeXPatternVisualizer provides a PlantUML visualization of an
 * IBeXPatternSet or a single pattern.
 */
public class IBeXPatternVisualizer implements EMoflonDiagramTextProvider {

	@Override
	public String getDiagramBody(final IEditorPart editor, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			return visualizeSelection((IStructuredSelection) selection);
		}

		throw new IllegalArgumentException("Invalid selection: " + selection);
	}

	/**
	 * Visualizes the first selected element
	 * 
	 * @param selection
	 *            the selection
	 * @return the PlantUMl code for the visualization
	 */
	private static String visualizeSelection(final IStructuredSelection selection) {
		Object element = selection.getFirstElement();

		if (isPatternSet(element)) {
			return IBeXPatternPlantUMLGenerator.visualizePatternInvocations((IBeXPatternSet) element);
		}

		if (isContextPattern(element)) {
			return IBeXPatternPlantUMLGenerator.visualizeContextPattern((IBeXContextPattern) element);
		}

		if (isCreatePattern(element)) {
			return IBeXPatternPlantUMLGenerator.visualizeCreatePattern((IBeXCreatePattern) element);
		}

		if (isDeletePattern(element)) {
			return IBeXPatternPlantUMLGenerator.visualizeDeletePattern((IBeXDeletePattern) element);
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
				.filter(o -> isPatternSet(o) || isContextPattern(o) || isCreatePattern(o) || isDeletePattern(o)) //
				.isPresent();
	}
	
	@Override
	public boolean supportsSelection(ISelection selection) {
		Collection<EObject> elements = VisualiserUtilities.extractEcoreSelection(selection);
		if(elements == null)
			return false;

		return !elements.stream().filter(elt -> !elt.eClass().getEPackage().getName().contains("org.emoflon.ibex.patternmodel")).findAny().isPresent();
	}

	private static boolean isPatternSet(final Object object) {
		return object instanceof IBeXPatternSet;
	}

	private static boolean isContextPattern(final Object object) {
		return object instanceof IBeXContextPattern;
	}

	private static boolean isCreatePattern(final Object object) {
		return object instanceof IBeXCreatePattern;
	}

	private static boolean isDeletePattern(final Object object) {
		return object instanceof IBeXDeletePattern;
	}

}
