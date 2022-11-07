/**
 *
 * $Id$
 */
package language.validation;

import language.TGGRuleNode;

import org.eclipse.emf.ecore.EReference;

/**
 * A sample validator interface for {@link language.TGGRuleEdge}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TGGRuleEdgeValidator {
	boolean validate();

	boolean validateSrcNode(TGGRuleNode value);

	boolean validateTrgNode(TGGRuleNode value);

	boolean validateType(EReference value);
}
