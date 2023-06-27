/**
 *
 * $Id$
 */
package org.emoflon.ibex.gt.StateModel.validation;

import org.eclipse.emf.ecore.EObject;

/**
 * A sample validator interface for {@link org.emoflon.ibex.gt.StateModel.Parameter}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ParameterValidator {
	boolean validate();

	boolean validateName(String value);

	boolean validateParameter(EObject value);
}