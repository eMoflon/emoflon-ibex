/**
 *
 * $Id$
 */
package org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.validation;

import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Protocol;

/**
 * A sample validator interface for {@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TGGRuleApplicationValidator {
	boolean validate();

	boolean validateProtocol(Protocol value);
}
