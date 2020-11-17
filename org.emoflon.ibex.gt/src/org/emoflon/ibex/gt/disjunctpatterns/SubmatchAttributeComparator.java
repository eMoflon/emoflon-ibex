package org.emoflon.ibex.gt.disjunctpatterns;

import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.arithmetic.RuntimeArithmeticExtensionCalculator;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.gt.engine.MatchFilter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;

/**
 * Helper class for sorting subpatterns
 *
 */
public class SubmatchAttributeComparator implements Comparator<IMatch>{
	
	private final IBeXConstraint constraint;
	private final IBeXRelation relation;
	private final boolean arithmetic;
	private final List<IBeXContextPattern> sourcePatterns;
	private final List<IBeXContextPattern> targetPatterns;
	private final GraphTransformationInterpreter interpreter;
	
	public SubmatchAttributeComparator(final IBeXConstraint constraint, final List<IBeXContextPattern> sourcePatterns, final List<IBeXContextPattern> targetPatterns, 
			final GraphTransformationInterpreter interpreter) {
		this.constraint = constraint;
		//it is sorted from smallest to biggest
		arithmetic = constraint instanceof IBeXAttributeConstraint;
		
		if(constraint instanceof IBeXAttributeConstraint) {
			relation = ((IBeXAttributeConstraint) constraint).getRelation();
		}
		else {
			relation = ((IBeXArithmeticConstraint) constraint).getRelation();		
		}
		
		this.sourcePatterns = sourcePatterns;
		this.targetPatterns = targetPatterns;
		this.interpreter = interpreter;
	}
	

	@Override
	public int compare(final IMatch o1, final IMatch o2) {
		if(o1.equals(o2)) {
			return 0;
		}
		else {
			int value = compareValue(o1, o2);
			if(value == 0) {
				return o1.hashCode() - o2.hashCode();
			}
			return value;
		}			
	}
	
	/**
	 * compare the value between two matches; is 0 when the value is equal, not when the objects are equal
	 */
	public int compareValue(final IMatch o1, final IMatch o2) {
		if(DisjunctPatternHelper.matchFromSubpattern(o1, targetPatterns) && DisjunctPatternHelper.matchFromSubpattern(o2, targetPatterns)) {
			Object newO1 = calculateSourceValue(o1, true);
			Object newO2 = calculateSourceValue(o2, true);
			
			if(newO1.equals(newO2)) return 0;
			else {
				return MatchFilter.compare(newO1, newO2, IBeXRelation.SMALLER)? -1: 1;
			}
		}
		else if(!DisjunctPatternHelper.matchFromSubpattern(o1, targetPatterns) &&  !DisjunctPatternHelper.matchFromSubpattern(o2, targetPatterns)) {
			Object newO1 = calculateSourceValue(o1, false);
			Object newO2 = calculateSourceValue(o2, false);
			
			if(newO1.equals(newO2)) return 0;
			else {
				return MatchFilter.compare(newO1, newO2, IBeXRelation.SMALLER)? -1: 1;
			}
			
		}
		else if(DisjunctPatternHelper.matchFromSubpattern(o1, targetPatterns)) {
			Object newO1 = calculateSourceValue(o1, true);
			Object newO2 = calculateSourceValue(o2, false);
			
			if(newO1.equals(newO2)) return 0;
			else {
				return MatchFilter.compare(newO1, newO2, IBeXRelation.SMALLER)? -1: 1;
			}
		}
		else {
			Object newO1 = calculateSourceValue(o1, false);
			Object newO2 = calculateSourceValue(o2, true);
			
			if(newO1.equals(newO2)) return 0;
			else {
				return MatchFilter.compare(newO1, newO2, IBeXRelation.SMALLER)? -1: 1;
			}
		}
	}
	/**
	 * compares two matches with the given relation
	 * @param o1 the target match
	 * @param o2 the source match
	 */
	public boolean compareWithEquals(final IMatch o1, final IMatch o2) {
		return MatchFilter.compare(calculateSourceValue(o1, true), calculateSourceValue(o2, false),	
				relation);				
	}
	
	/**
	 * calculates the value of the source attribute
	 */
	private final Object calculateSourceValue(final IMatch o, final boolean isLhs) {
		if(arithmetic) {
			//edouble is used for the arithmetic calculation since the correct type of the expression can not be calculated easily
			if(isLhs) {
				return RuntimeArithmeticExtensionCalculator.calculateValue(interpreter, ((IBeXArithmeticConstraint) constraint).getLhs(), o);						
			}
			else {
				return RuntimeArithmeticExtensionCalculator.calculateValue(interpreter, ((IBeXArithmeticConstraint) constraint).getRhs(), o);							
			}
		}
		else{
			if(isLhs) {
				return ((EObject) o.get((((IBeXAttributeExpression)((IBeXAttributeConstraint) constraint).getLhs()).getNode().getName())))
						.eGet((((IBeXAttributeExpression)((IBeXAttributeConstraint) constraint).getLhs()).getAttribute()));				
			}
			else {
				return ((EObject) o.get((((IBeXAttributeExpression)((IBeXAttributeConstraint) constraint).getRhs()).getNode().getName())))
						.eGet((((IBeXAttributeExpression)((IBeXAttributeConstraint) constraint).getRhs()).getAttribute()));	
			}
		}
	}
	
	/**
	 * checks if a match has a valid value; mainly used for arithmetic values
	 */
	public boolean isLegal(final IMatch o) {
		if(DisjunctPatternHelper.matchFromSubpattern(o, targetPatterns) ) {
			if(arithmetic) {
				try {
					//if it is from target pattern then it is lhs
					calculateSourceValue(o, true);
					return true;
				}catch(IllegalArgumentException e) {
					return false;
				}
			}
			else return true;
		}
		else {
			if(arithmetic) {
				try {
					calculateSourceValue(o, false);
					return true;
				}catch(IllegalArgumentException e) {
					return false;
				}
			}
			else return true;
		}
	}
	
	public final IBeXRelation getRelation() {
		return relation;
	}
	
	public final List<IBeXContextPattern> getSourcePatterns(){
		return sourcePatterns;
	}
	
	public final List<IBeXContextPattern> getTargetPatterns() {
		return targetPatterns;
	}
}