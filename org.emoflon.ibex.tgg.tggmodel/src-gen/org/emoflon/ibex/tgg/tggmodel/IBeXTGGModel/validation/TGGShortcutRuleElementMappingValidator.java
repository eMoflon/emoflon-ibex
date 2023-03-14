/**
 *
 * $Id$
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.validation;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

/**
 * A sample validator interface for {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TGGShortcutRuleElementMappingValidator {
	boolean validate();

	boolean validateOriginal(TGGRuleElement value);

	boolean validateReplacing(TGGRuleElement value);
}
