/**
 *
 * $Id$
 */
package org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.validation;

import org.eclipse.emf.ecore.EObject;

/**
 * A sample validator interface for {@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Correspondence}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface CorrespondenceValidator {
	boolean validate();

	boolean validateSource(EObject value);

	boolean validateTarget(EObject value);
}