package org.emoflon.ibex.common.visualization;

import java.util.Optional;

import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.moflon.core.ui.visualisation.common.EMoflonVisualiser;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXPatternSet;

/**
 * The IBeXPatternVisualizer provides a PlantUML visualization of an
 * IBeXPatternSet or a single pattern.
 */
public class IBeXPatternVisualizer extends EMoflonVisualiser {

	@Override
	protected String getDiagramBody(final IEditorPart editor, final ISelection selection) {
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
