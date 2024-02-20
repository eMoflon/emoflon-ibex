/**
 *
 * $Id$
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.validation;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;

/**
 * A sample validator interface for {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TGGAttributeConstraintSetValidator {
	boolean validate();

	boolean validateTggAttributeConstraints(EList<TGGAttributeConstraint> value);

	boolean validateParameters(EList<TGGAttributeConstraintParameterValue> value);
}