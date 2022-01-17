/**
 *
 * $Id$
 */
package org.emoflon.ibex.modelXML.validation;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.modelXML.Attribute;
import org.emoflon.ibex.modelXML.Node;
import org.emoflon.ibex.modelXML.Value;

/**
 * A sample validator interface for {@link org.emoflon.ibex.modelXML.Node}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface NodeValidator {
	boolean validate();

	boolean validateAttributes(EList<Attribute> value);

	boolean validateChildren(EList<Node> value);

	boolean validateCrossref(EList<Node> value);

	boolean validateValue(Value value);
}
