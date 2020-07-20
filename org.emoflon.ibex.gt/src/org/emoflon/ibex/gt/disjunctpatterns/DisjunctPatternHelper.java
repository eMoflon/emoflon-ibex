package org.emoflon.ibex.gt.disjunctpatterns;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.stream.Collectors;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;

/**
 * Helper class for the GraphTransformationDisjunctPatternInterpreter
 *
 */
public class DisjunctPatternHelper {

	/**
	 * calculates all the matches that contain the specific submatch

	 */
	public static final Set<IMatch> createMatchesWithThisSubmatch(final IMatch match, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap, final String name,
			final Map<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> attributes){
		Set<IMatch> cartesianProduct = new HashSet<IMatch>();
		cartesianProduct.add(match);
		for(Set<IMatch> matches: submatchesMap.values()) {
			if(!matches.isEmpty()) {
				if(!matches.iterator().next().getPatternName().equals(match.getPatternName())) {
					cartesianProduct = cartesianProduct(cartesianProduct, matches, name, attributes);
				}else {
					continue;
				}
				
			}else {
				return new HashSet<IMatch>();
			}
			
		}
		return cartesianProduct;
	}

	/**
	 * calculates the cartesian product between two matches
	 * 
	 * @param match1 the first matchSet
	 * @param match2 the second matchSet
	 * @return the cartesian product
	 */
	public static final Set<IMatch> cartesianProduct(final Set<IMatch> match1, final Set<IMatch> match2, final String name,
			final Map<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> attributes){
		Set<IMatch> newSubMatches = new HashSet<IMatch>();
		if(match1.isEmpty() || match2.isEmpty()) {
			return newSubMatches;		
		}
			
		else{
			return newSubMatches = match1.parallelStream().flatMap(match -> match2.stream().filter(otherMatch ->{
				return isDisjunct(match, otherMatch) && DisjunctPatternHelper.checkAttributeConstraint(match, otherMatch, attributes);
			}).map(otherMatch -> merge(match, otherMatch, name))).collect(Collectors.toSet());
		}
					
	}
	/**
	 * creates an updated cartesian product with the given sets
	 */
	public static final Set<IMatch> createUpdatedCartesianProduct(final IBeXDisjunctContextPattern pattern, final List<IBeXContextPattern> patternSequence, 
			final IBeXContextPattern updatedPattern, final Set<IMatch> updatedMatches, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap,
			final Map<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> attributes){

		Set<IMatch> newCartesianMatches = new HashSet<IMatch>();
		//calculate the cartesian product with the specific sequence
		for(IBeXContextPattern subpattern: patternSequence){
			if(subpattern.equals(updatedPattern)) {
				if(updatedMatches.isEmpty()) return new HashSet<IMatch>();
				if(newCartesianMatches.isEmpty()) {
					newCartesianMatches = new HashSet<IMatch>(updatedMatches);
				}
				else {
					newCartesianMatches = new HashSet<IMatch>(DisjunctPatternHelper.cartesianProduct(newCartesianMatches, updatedMatches, 
							pattern.getName(), attributes));
				}
			}else {
				if(submatchesMap.get(subpattern).isEmpty()) return new HashSet<IMatch>();
				if(newCartesianMatches.isEmpty()) {
					newCartesianMatches = new HashSet<IMatch>(submatchesMap.get(subpattern));
				}
				else {
					newCartesianMatches = new HashSet<IMatch>(DisjunctPatternHelper.cartesianProduct(newCartesianMatches, submatchesMap.get(subpattern), 
					pattern.getName(), attributes));
				}		
			}
		}	
		return newCartesianMatches;
	}
	/**
	 * sees if two matches are equal; this function is necessary since the .equals() method does not work properly when disjunct matches are generated dynamically
	 */
	public static boolean areMatchesEqual(IMatch match1, IMatch match2) {
		if(match1.getParameterNames().size() == match2.getParameterNames().size()) {
			for(String parameter: match1.getParameterNames()) {
				if(match2.isInMatch(parameter)) {
					if(!match1.get(parameter).equals(match2.get(parameter))) {
						return false;
					}
				}
				else {
					return false;
				}
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	/**
	 * sees if a match is a submatch of another match
	 */
	public static boolean isASubmatch(IMatch submatch, IMatch match) {
		for(String parameter: submatch.getParameterNames()) {
			if(match.isInMatch(parameter)) {
				if(!submatch.get(parameter).equals(match.get(parameter))) {
					return false;
				}
			}
			else{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * finds out if the specific match exists
	 */
	public static boolean doesMatchExists(IMatch match, Map<IBeXContextPattern, Set<IMatch>> submatchesMap) {
		for(Entry<IBeXContextPattern, Set<IMatch>> entrySet: submatchesMap.entrySet()) {
			boolean isInSubmatch = entrySet.getValue().parallelStream().anyMatch(submatch -> {
				for(String parameter: submatch.getParameterNames()) {
					if(!match.get(parameter).equals(submatch.get(parameter))) {
						return false;
					}
				}
				return true;
			});
			if(!isInSubmatch) return false;
		}
		return true;
	}
	
	/**
	 * check if the match has the given object under one of the parameter names
	 */
	public static boolean hasObject(final IMatch match, final List<String> parameters, final Object object) {
		for(String parameter: parameters) {
			if(match.get(parameter).equals(object)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * check if two submatches are disjunct
	 */
	public static boolean isDisjunct(final IMatch match1, final IMatch match2) {
		for(String name1: match1.getParameterNames()) {
			for(String name2: match2.getParameterNames()) {
				if(match1.get(name1).equals(match2.get(name2))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * check if two submatches do not break attribute constraints
	 */
	public static boolean checkAttributeConstraint(final IMatch match1, final IMatch match2, final Map<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> attributes) {
		for(Entry<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> attribute: attributes.entrySet()) {
			for(SubmatchAttributeComparator constraint: attribute.getValue()) {
				if(!compare(match1, match2, attribute.getKey(), constraint)) return false;			
			}
		}
		return true;
	}
	
	/**
	 * finds all the attributes in the constraint map that do not fulfill the attribute constraints;reversed when looking from source to target
	 */
	public static final Set<IMatch> findSubsetWithAttributeConstraint(final SubmatchAttributeComparator comparator, 
			final TreeSet<IMatch> constraintSet, final IMatch match, boolean reversed){
		Set<IMatch> invalidMatches = new HashSet<IMatch>();
		IBeXRelation rel = comparator.getRelation();
		if(rel == IBeXRelation.EQUAL ) {
			//search for all unequal matches
			invalidMatches.addAll(constraintSet.tailSet(findHigherMatchValue(match, 
					constraintSet, comparator), true));
			invalidMatches.addAll(constraintSet.headSet(findLowerMatchValue(match, 
					constraintSet, comparator), true));	
		}
		else if(rel == IBeXRelation.UNEQUAL) {
			//search for all equal matches
			invalidMatches.addAll(constraintSet.subSet(findLowerMatchValue(match, 
					constraintSet, comparator), false, findHigherMatchValue(match, 
							constraintSet, comparator), false));				
		} 
		else if(!reversed && rel == IBeXRelation.GREATER || 
				reversed && rel == IBeXRelation.SMALLER_OR_EQUAL) {
			invalidMatches.addAll(constraintSet.tailSet(findHigherMatchValue(match, 
					constraintSet, comparator), (rel == IBeXRelation.GREATER) ? true : false));
		}
		else if(!reversed && rel == IBeXRelation.GREATER_OR_EQUAL || 
				reversed && rel == IBeXRelation.SMALLER) {
			invalidMatches.addAll(constraintSet.tailSet(findLowerMatchValue(match, 
					constraintSet, comparator), (rel == IBeXRelation.GREATER_OR_EQUAL) ? false : true));			
		}
		else if(!reversed && rel == IBeXRelation.SMALLER || 
				reversed && rel == IBeXRelation.GREATER_OR_EQUAL){
			invalidMatches.addAll(constraintSet.headSet(findLowerMatchValue(match, 
					constraintSet, comparator), (rel == IBeXRelation.SMALLER) ? true : false));
		}
		else {
			invalidMatches.addAll(constraintSet.headSet(findHigherMatchValue(match, 
					constraintSet, comparator), (rel == IBeXRelation.SMALLER_OR_EQUAL) ? true : false));		
		}
		return invalidMatches;
	}
	
	/**
	 * find the highest match with the same value
	 */
	private static final IMatch findHigherMatchValue(final IMatch match, final NavigableSet<IMatch> constraintSet, final SubmatchAttributeComparator comparator) {
		Iterator<IMatch> iter = constraintSet.descendingIterator();
		IMatch currentMatch;

		while(iter.hasNext()) {
			currentMatch = iter.next();
			if(comparator.compareValue(match, currentMatch)==0) {
				return currentMatch;
			}
		}
		return match;
	}
	
	/**
	 * find the lowest match with the same value
	 */
	private static final IMatch findLowerMatchValue(final IMatch match, final NavigableSet<IMatch> constraintSet, final SubmatchAttributeComparator comparator) {
		Iterator<IMatch> iter = constraintSet.iterator();
		IMatch currentMatch;

		while(iter.hasNext()) {
			currentMatch = iter.next();
			if(comparator.compareValue(match, currentMatch)==0) {
				return currentMatch;
			}
		}
		return match;
	}
	
	/**
	 * compare two matches with the attribute constraint
	 */
	public static boolean compare(final IMatch match1, final IMatch match2, final IBeXDisjunctAttribute attribute, final SubmatchAttributeComparator comparator) {
		//find target match
		IMatch targetMatch;
		if(matchFromSubpattern(match1, attribute.getTargetPattern())) targetMatch = match1;
		else if(matchFromSubpattern(match2, attribute.getTargetPattern())) targetMatch = match2;
		else {
			return true;
		}
		IMatch sourceMatch;
		//find the source match
		boolean match1IsSource = true;
		boolean match2IsSource = true;
		for(IBeXContextPattern pattern: attribute.getSourcePattern()) {
			if(!matchFromSubpattern(match1, pattern)) match1IsSource = false;
			if(!matchFromSubpattern(match2, pattern)) match2IsSource = false;
		}
		if(match1IsSource && match2IsSource){
			sourceMatch = merge(match1, match2, match1.getPatternName());
			return comparator.compareWithEquals(targetMatch, sourceMatch);
		}
		if(match1IsSource) {
			return comparator.compareWithEquals(targetMatch, match1);
		}
		if(match2IsSource) {
			return comparator.compareWithEquals(targetMatch, match2);
		}
		return true;
	}
	
	/**
	 * checks if a match is from a specific subpattern
	 */
	public static boolean matchFromSubpattern(final IMatch match, final IBeXContextPattern pattern) {
		for(final IBeXNode node: pattern.getSignatureNodes()) {
			if(!match.isInMatch(node.getName())) return false;
		}
		return true;
	}
	
	/**
	 * merge two submatches to a bigger match
	 */
	public static final IMatch merge(final IMatch match1, final IMatch match2, final String name) {
		IMatch newMatch = new SimpleMatch(match1);
		newMatch.setPatternName(name);
		match2.getParameterNames().forEach(parameterName -> newMatch.put(parameterName, match2.get(parameterName)));
		return newMatch;
	}
}
