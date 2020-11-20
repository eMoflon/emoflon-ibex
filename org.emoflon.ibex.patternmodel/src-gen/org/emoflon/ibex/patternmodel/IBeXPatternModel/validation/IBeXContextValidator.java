/**
 *
 * $Id$
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.validation;

import org.eclipse.emf.common.util.EList;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;

/**
 * A sample validator interface for {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface IBeXContextValidator {
	boolean validate();

	boolean validateApiPatternDependencies(EList<IBeXContext> value);

}
