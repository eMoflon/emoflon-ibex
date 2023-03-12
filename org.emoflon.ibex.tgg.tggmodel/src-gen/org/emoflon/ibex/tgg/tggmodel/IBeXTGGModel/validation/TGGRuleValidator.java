/**
 *
 * $Id$
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.validation;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

/**
 * A sample validator interface for {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TGGRuleValidator {
	boolean validate();

	boolean validateNodes(EList<TGGNode> value);

	boolean validateEdges(EList<TGGEdge> value);

	boolean validateCorrespondenceNodes(EList<TGGCorrespondence> value);

	boolean validateOperationalisations(EList<TGGOperationalRule> value);

	boolean validateAttributeConstraints(TGGAttributeConstraintSet value);

	boolean validateAbstract(boolean value);

	boolean validateContext(IBeXRuleDelta value);

	boolean validateContextSource(IBeXRuleDelta value);

	boolean validateContextCorrespondence(IBeXRuleDelta value);

	boolean validateContextTarget(IBeXRuleDelta value);

	boolean validateCreate(IBeXRuleDelta value);

	boolean validateCreateSource(IBeXRuleDelta value);

	boolean validateCreateCorrespondence(IBeXRuleDelta value);

	boolean validateCreateTarget(IBeXRuleDelta value);

	boolean validateAxiom(boolean value);

	boolean validateSource(IBeXRuleDelta value);

	boolean validateCorrespondence(IBeXRuleDelta value);

	boolean validateTarget(IBeXRuleDelta value);
}
