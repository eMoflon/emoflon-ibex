/**
 *
 * $Id$
 */
package org.emoflon.ibex.gt.StateModel.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * A sample validator interface for {@link org.emoflon.ibex.gt.StateModel.Link}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface LinkValidator {
	boolean validate();

	boolean validateType(EReference value);

	boolean validateSrc(EObject value);

	boolean validateTrg(EObject value);
}
