/**
 *
 * $Id$
 */
package org.emoflon.ibex.gt.StateModel.validation;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.gt.StateModel.AllMatches;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.IBeXMatch;
import org.emoflon.ibex.gt.StateModel.MatchDelta;
import org.emoflon.ibex.gt.StateModel.Parameter;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StructuralDelta;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

/**
 * A sample validator interface for {@link org.emoflon.ibex.gt.StateModel.RuleState}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface RuleStateValidator {
	boolean validate();

	boolean validateParent(State value);

	boolean validateRule(IBeXRule value);

	boolean validateStructuralDelta(StructuralDelta value);

	boolean validateAttributeDeltas(EList<AttributeDelta> value);

	boolean validateMatch(IBeXMatch value);

	boolean validateCoMatch(IBeXMatch value);

	boolean validateParameters(EList<Parameter> value);

	boolean validateAllMatches(EList<AllMatches> value);

	boolean validateDeletedMatches(EList<MatchDelta> value);

	boolean validateCreatedMatches(EList<MatchDelta> value);

	boolean validateMatchCount(long value);
}