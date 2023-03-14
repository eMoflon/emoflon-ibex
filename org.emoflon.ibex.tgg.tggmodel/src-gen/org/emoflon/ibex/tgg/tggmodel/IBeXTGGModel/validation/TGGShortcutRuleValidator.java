/**
 *
 * $Id$
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.validation;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping;

/**
 * A sample validator interface for {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TGGShortcutRuleValidator {
	boolean validate();

	boolean validateOriginalRule(TGGRule value);

	boolean validateReplacingRule(TGGRule value);

	boolean validateMappings(EList<TGGShortcutRuleElementMapping> value);

	boolean validateUnmappedOriginalElements(EList<TGGRuleElement> value);

	boolean validateUnmappedReplacingElements(EList<TGGRuleElement> value);
}
