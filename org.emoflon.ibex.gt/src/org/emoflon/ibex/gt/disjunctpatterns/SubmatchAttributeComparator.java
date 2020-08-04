package org.emoflon.ibex.gt.disjunctpatterns;

import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.arithmetics.IBeXArithmeticsCalculatorHelper;
import org.emoflon.ibex.gt.engine.MatchFilter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;

/**
 * Helper class for sorting subpatterns
 *
 */
public class SubmatchAttributeComparator implements Comparator<IMatch>{
	
	private IBeXAttributeConstraint attribute;
	private boolean notArithmetic;
	private IBeXRelation relation;
	private List<IBeXContextPattern> sourcePatterns;
	private IBeXContextPattern targetPattern;
	
	public SubmatchAttributeComparator(final IBeXAttributeConstraint attribute, List<IBeXContextPattern> sourcePatterns, IBeXContextPattern targetPattern) {
		this.attribute = attribute;
		//it is sorted from smallest to biggest
		relation = attribute.getRelation();
		notArithmetic = !(attribute.getValue() instanceof IBeXArithmeticValue);
		this.sourcePatterns = sourcePatterns;
		this.targetPattern = targetPattern;
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
		if(DisjunctPatternHelper.matchFromSubpattern(o1, targetPattern) && DisjunctPatternHelper.matchFromSubpattern(o2, targetPattern)) {
			Object newO1= ((EObject) o1.get(attribute.getNode().getName())).eGet(attribute.getType());
			Object newO2 = ((EObject) o2.get(attribute.getNode().getName())).eGet(attribute.getType());
			
			if(newO1.equals(newO2)) return 0;
			else {
				return MatchFilter.compare(newO1, newO2, IBeXRelation.SMALLER)? -1: 1;
			}
		}
		else if(!DisjunctPatternHelper.matchFromSubpattern(o1, targetPattern) &&  !DisjunctPatternHelper.matchFromSubpattern(o2, targetPattern)) {
			Object newO1= calculateSourceValue(o1);
			Object newO2 = calculateSourceValue(o2);
			
			if(newO1.equals(newO2)) return 0;
			else {
				return MatchFilter.compare(newO1, newO2, IBeXRelation.SMALLER)? -1: 1;
			}
			
		}
		else if(DisjunctPatternHelper.matchFromSubpattern(o1, targetPattern)) {
			Object newO1= ((EObject) o1.get(attribute.getNode().getName())).eGet(attribute.getType());
			Object newO2 = calculateSourceValue(o2);
			
			if(newO1.equals(newO2)) return 0;
			else {
				return MatchFilter.compare(newO1, newO2, IBeXRelation.SMALLER)? -1: 1;
			}
		}
		else {
			Object newO1 = calculateSourceValue(o1);
			Object newO2 =((EObject) o2.get(attribute.getNode().getName())).eGet(attribute.getType());
			
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
		return MatchFilter.compare(((EObject) o1.get(attribute.getNode().getName())).eGet(attribute.getType()), calculateSourceValue(o2),	
				relation);				
	}
	
	/**
	 * calculates the value of the source attribute
	 */
	private final Object calculateSourceValue(final IMatch o) {
		if(notArithmetic) {
			return ((EObject) o.get(((IBeXAttributeExpression) attribute.getValue()).getNode().getName())).eGet(((IBeXAttributeExpression) attribute.getValue()).getAttribute());
		}
		else{
			return IBeXArithmeticsCalculatorHelper.getValue((IBeXArithmeticValue) attribute.getValue(), o, attribute.getType().getEAttributeType());
		}
	}
	
	/**
	 * checks if a match has a valid value; mainly used for arithmetic values
	 */
	public boolean isLegal(final IMatch o) {
		if(DisjunctPatternHelper.matchFromSubpattern(o, targetPattern) ) {
			return true;
		}
		else {
			if(notArithmetic) return true;
			try {
				calculateSourceValue(o);
				return true;
			}catch(IllegalArgumentException e) {
				return false;
			}
		}
	}
	
	public final IBeXRelation getRelation() {
		return relation;
	}
	
	public final List<IBeXContextPattern> getSourcePatterns(){
		return sourcePatterns;
	}
	
	public final IBeXContextPattern getTargetPattern() {
		return targetPattern;
	}
}