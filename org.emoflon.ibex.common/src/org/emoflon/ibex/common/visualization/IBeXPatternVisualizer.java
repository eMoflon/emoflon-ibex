package org.emoflon.ibex.common.visualization;

import java.util.Optional;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.moflon.core.ui.visualisation.EMoflonVisualiser;

import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXPatternSet;

/**
 * The IBeXPatternVisualizer provides a PlantUML visualization of an
 * IBeXPatternSet.
 */
public class IBeXPatternVisualizer extends EMoflonVisualiser {

	@Override
	protected String getDiagramBody(final IEditorPart editor, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();

			if (isPatternSet(element)) {
				return IBeXPatternPlantUMLGenerator.visualizePatternInvocations((IBeXPatternSet) element);
			}

			if (isPattern(element)) {
				return IBeXPatternPlantUMLGenerator.visualizePattern((IBeXPattern) element);
			}

			if (isCreatePattern(element)) {
				return IBeXPatternPlantUMLGenerator.visualizeCreatePattern((IBeXCreatePattern) element);
			}

			if (isDeletePattern(element)) {
				return IBeXPatternPlantUMLGenerator.visualizeDeletePattern((IBeXDeletePattern) element);
			}
		}

		throw new IllegalArgumentException("Invalid selection: " + selection);
	}

	@Override
	public boolean supportsEditor(final IEditorPart editor) {
		return Optional.of(editor) //
				.flatMap(maybeCast(EcoreEditor.class)) //
				.map(EcoreEditor::getSelection) //
				.flatMap(maybeCast(TreeSelection.class)) //
				.map(TreeSelection::getFirstElement) //
				.filter(o -> isIBeXObject(o)).isPresent();
	}

	private static boolean isIBeXObject(Object object) {
		return isPatternSet(object) || isPattern(object) || isCreatePattern(object) || isDeletePattern(object);
	}

	private static boolean isPatternSet(Object object) {
		return object instanceof IBeXPatternSet;
	}

	private static boolean isPattern(Object object) {
		return object instanceof IBeXPattern;
	}

	private static boolean isCreatePattern(Object object) {
		return object instanceof IBeXCreatePattern;
	}

	private static boolean isDeletePattern(Object object) {
		return object instanceof IBeXDeletePattern;
	}
}
