package org.emoflon.ibex.gt.disjunctpatterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.stream.Collectors;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.gt.transformations.Pair;
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
			final List<SubmatchAttributeComparator> attributes){
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
			final List<SubmatchAttributeComparator> attributes){
		Set<IMatch> newSubMatches = new HashSet<IMatch>();
		if(match1.isEmpty() || match2.isEmpty()) {
			return newSubMatches;		
		}		
		else{
			IMatch firstMatch = match1.iterator().next();
			IMatch secondMatch = match2.iterator().next();
			List<SubmatchAttributeComparator> firstIsTargetComp = new ArrayList<SubmatchAttributeComparator>();
			List<SubmatchAttributeComparator> secondIsTargetComp = new ArrayList<SubmatchAttributeComparator>();
			List<SubmatchAttributeComparator> afterMergeComp = new ArrayList<SubmatchAttributeComparator>();
			//find all necessary attributes for the calculation of the cartesian product
			for(SubmatchAttributeComparator comp: attributes) {
				boolean firstIsTarget = false;
				boolean secondIsTarget = false;
				if(matchFromSubpattern(firstMatch, comp.getTargetPattern())) firstIsTarget = true;
				else if(matchFromSubpattern(secondMatch, comp.getTargetPattern())) secondIsTarget = true;
				if(!firstIsTarget && !secondIsTarget) continue;
				//find the source match
				boolean firstIsSource = true;
				boolean secondIsSource = true;
				boolean bothAreSource = true;
				IMatch merge = merge(firstMatch,secondMatch, name);
				for(IBeXContextPattern pattern: comp.getSourcePatterns()) {
					if(!matchFromSubpattern(firstMatch, pattern)) firstIsSource = false;
					if(!matchFromSubpattern(secondMatch, pattern)) secondIsSource = false;
					if(!matchFromSubpattern(merge, pattern)) bothAreSource = false;
				}
				if(!firstIsSource && !secondIsSource && !bothAreSource) continue;
				else if(bothAreSource && !firstIsSource && !secondIsSource) afterMergeComp.add(comp);
				else {
					if(firstIsSource) secondIsTargetComp.add(comp);
					else if(secondIsSource) firstIsTargetComp.add(comp);
				}
			}

			for(IMatch match: match1) {
				newSubMatches.addAll(match2.parallelStream().filter(otherMatch -> {
					boolean valid = isDisjunct(match, otherMatch);
					if(!valid) return false;
					for(SubmatchAttributeComparator comp: firstIsTargetComp) {
						valid &= comp.compareWithEquals(match, otherMatch);
						if(!valid) return false;
					}
					for(SubmatchAttributeComparator comp: secondIsTargetComp) {
						valid &= comp.compareWithEquals(otherMatch, match);
						if(!valid) return false;
					}
					return true;
				}).map(otherMatch -> merge(match, otherMatch, name)).filter(m -> {
					boolean valid = true;
					for(SubmatchAttributeComparator comp: afterMergeComp) {
						valid &= comp.compareWithEquals(m, m);
					}
					return valid;
				}).collect(Collectors.toSet()));
			}
		}
		return newSubMatches;			
	}
	/**
	 * creates an updated cartesian product with the given sets
	 */
	public static final Set<IMatch> createUpdatedCartesianProduct(final IBeXDisjunctContextPattern pattern, final List<IBeXContextPattern> patternSequence, 
			final IBeXContextPattern updatedPattern, final Set<IMatch> updatedMatches, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap,
			final List<SubmatchAttributeComparator> attributes){

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
	 * creates the old and new matches of the cartesian product; at index 0 are the new matches and at 1 are the old matches
	 */
	public static final List<Set<IMatch>> createNewAndOldCartesianProducts(final IBeXDisjunctContextPattern pattern, final List<IBeXContextPattern> cartesianSequence, 
			final Map<IBeXContextPattern, Set<IMatch>> submatchesMap, final Map<IBeXContextPattern, Set<IMatch>> oldMatches,
			final List<SubmatchAttributeComparator> comparators){
		Map<IBeXContextPattern, Set<IMatch>> newSubmatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		Map<IBeXContextPattern, Set<IMatch>> oldSubmatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		
		for(IBeXContextPattern subpattern: cartesianSequence) {
			Set<IMatch> newMatch = submatchesMap.get(subpattern);
			Set<IMatch> oldMatch = oldMatches.get(subpattern);
			newSubmatches.put(subpattern, newMatch.parallelStream().filter(match -> !oldMatch.contains(match)).collect(Collectors.toSet()));
			oldSubmatches.put(subpattern, oldMatch.parallelStream().filter(match -> !newMatch.contains(match)).collect(Collectors.toSet()));
		}
		Set<IMatch> oldCalculatedMatches = new HashSet<IMatch>();
		Set<IMatch> newCalculatedMatches = new HashSet<IMatch>();
		if(cartesianSequence.size() == 1) {
			oldCalculatedMatches = oldSubmatches.get(cartesianSequence.get(0));
			newCalculatedMatches = newSubmatches.get(cartesianSequence.get(0));
		}
		else {
			for(IBeXContextPattern subpattern: cartesianSequence) {
				newCalculatedMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, cartesianSequence, subpattern, newSubmatches.get(subpattern), submatchesMap, comparators));
				oldCalculatedMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, cartesianSequence, subpattern, oldSubmatches.get(subpattern), oldMatches, comparators));
			}		
		}
		
		List<Set<IMatch>> matches = new ArrayList<Set<IMatch>>();
		matches.add(0, newCalculatedMatches);
		matches.add(1, oldCalculatedMatches);
		return matches;
	}
	
	/**
	 * calculate the changed matches for the cartesian product and add it to the new cartesian matches
	 */
	public static Set<IMatch> createChangedCartesianProducts(final IBeXDisjunctContextPattern pattern, final List<IBeXContextPattern> cartesianSequence, final Set<Object> changedNodes,
			final Map<IBeXContextPattern, Set<IMatch>> oldMatches, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap, final Set<IMatch> newMatches, List<SubmatchAttributeComparator> comparators){
		
		Map<IBeXContextPattern, Set<IMatch>> changedMatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		for(IBeXContextPattern subpattern: cartesianSequence) {
			Set<IMatch> oldSubpatternMatches = oldMatches.get(subpattern);
			Set<IMatch> newSubpatternMatches = submatchesMap.get(subpattern);
			changedMatches.put(subpattern, newSubpatternMatches.parallelStream().filter(match -> {
				//if it is a new match then it cant be updated
				if(!oldSubpatternMatches.contains(match)) return false;
				for(String parameter: match.getParameterNames()) {
					if(changedNodes.contains(match.get(parameter))) {
						return true;
					}
				}
				return false;
			}).collect(Collectors.toSet()));
		}
		
		//add the changed matches to the new matches and to the changed match set
		Set<IMatch> changedCartesianMatches = new HashSet<IMatch>();
		for(IBeXContextPattern subpattern: cartesianSequence) {
			newMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, cartesianSequence, subpattern, changedMatches.get(subpattern), submatchesMap, comparators));
			changedCartesianMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, cartesianSequence, subpattern, changedMatches.get(subpattern), oldMatches, comparators));
		}
		return changedCartesianMatches;
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
	 * compare two matches with the attribute constraint
	 */
	public static boolean compare(final IMatch match1, final IMatch match2, final SubmatchAttributeComparator comparator) {
		//find target match
		IMatch targetMatch;
		if(matchFromSubpattern(match1, comparator.getTargetPattern())) targetMatch = match1;
		else if(matchFromSubpattern(match2, comparator.getTargetPattern())) targetMatch = match2;
		else {
			return true;
		}
		IMatch sourceMatch;
		//find the source match
		boolean match1IsSource = true;
		boolean match2IsSource = true;
		for(IBeXContextPattern pattern: comparator.getSourcePatterns()) {
			if(!matchFromSubpattern(match1, pattern)) match1IsSource = false;
			if(!matchFromSubpattern(match2, pattern)) match2IsSource = false;
		}
		if(match1IsSource && match2IsSource){
			sourceMatch = merge(match1, match2, match1.getPatternName());
			if(comparator.isLegal(sourceMatch)){
				return comparator.compareWithEquals(targetMatch, sourceMatch);
			}
			else {
				return false;
			}
		}
		if(match1IsSource) {
			if(comparator.isLegal(match1)){
				return comparator.compareWithEquals(targetMatch, match1);				
			}
			else {
				return false;
			}
		}
		if(match2IsSource) {
			if(comparator.isLegal(match2)) {
				return comparator.compareWithEquals(targetMatch, match2);				
			}
			else {
				return false;
			}

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
	public static boolean checkAttributeConstraint(final IMatch match1, final IMatch match2, final List<SubmatchAttributeComparator> attributes) {
		for(SubmatchAttributeComparator constraint: attributes) {
			if(!compare(match1, match2, constraint)) return false;			
		}	
		return true;
	}
	
	/**
	 * finds all the attributes in the constraint map that do not fulfill the attribute constraints;reversed when looking from source to target
	 */
	public static final Set<IMatch> findSubsetWithAttributeConstraint(final SubmatchAttributeComparator comparator, 
			final TreeSet<IMatch> constraintSet, final IMatch match, boolean notReversed){
		Set<IMatch> invalidMatches = new HashSet<IMatch>();
		IBeXRelation rel = comparator.getRelation();
		if(rel == IBeXRelation.EQUAL ) {
			//search for all unequal matches
			invalidMatches.addAll(constraintSet.tailSet(findHigherMatchValue(match, 
					constraintSet, comparator), false));
			invalidMatches.addAll(constraintSet.headSet(findLowerMatchValue(match, 
					constraintSet, comparator), false));	
		}
		else if(rel == IBeXRelation.UNEQUAL) {
			//search for all equal matches
			invalidMatches.addAll(constraintSet.subSet(findLowerMatchValue(match, 
					constraintSet, comparator), true, findHigherMatchValue(match, 
							constraintSet, comparator), true));				
		} 
		else if(!notReversed && rel == IBeXRelation.GREATER
				|| notReversed && rel == IBeXRelation.SMALLER) {
			invalidMatches.addAll(constraintSet.headSet(findLowerMatchValue(match, 
					constraintSet, comparator),true));
		}
		else if(!notReversed && rel == IBeXRelation.GREATER_OR_EQUAL
				|| notReversed && rel == IBeXRelation.SMALLER_OR_EQUAL) {
			invalidMatches.addAll(constraintSet.headSet(findHigherMatchValue(match, 
					constraintSet, comparator),false));			
		}
		else if(!notReversed && rel == IBeXRelation.SMALLER 
				|| notReversed && rel == IBeXRelation.GREATER){
			invalidMatches.addAll(constraintSet.tailSet(findLowerMatchValue(match, 
					constraintSet, comparator),true));
		}
		else {//rel == IBeXRelation.SMALLER_OR_EQUAL
			invalidMatches.addAll(constraintSet.tailSet(findHigherMatchValue(match, 
					constraintSet, comparator),false));		
		}
		return invalidMatches;
	}
	
	/**
	 * find the highest match with the same value
	 */
	private static final IMatch findHigherMatchValue(final IMatch match, final NavigableSet<IMatch> constraintSet, final SubmatchAttributeComparator comparator) {
		Iterator<IMatch> iter = constraintSet.tailSet(match).iterator();
		IMatch currentMatch;
		IMatch prevMatch = constraintSet.floor(match);

		while(iter.hasNext()) {
			currentMatch = iter.next();
			if(comparator.compareValue(match, currentMatch)<0) {
				break;
			}
			prevMatch = currentMatch;
		}
		if(prevMatch != null && comparator.compareValue(match, prevMatch)==0) {
			return prevMatch;
		}
		return match;
	}
	
	/**
	 * find the lowest match with the same value
	 */
	private static final IMatch findLowerMatchValue(final IMatch match, final NavigableSet<IMatch> constraintSet, final SubmatchAttributeComparator comparator) {
		Iterator<IMatch> iter = ((NavigableSet<IMatch>) constraintSet.headSet(match)).descendingIterator();
		IMatch currentMatch;
		IMatch prevMatch = constraintSet.higher(match);

		while(iter.hasNext()) {
			currentMatch = iter.next();
			if(comparator.compareValue(match, currentMatch)>0) {
				break;
			}
			prevMatch = currentMatch;
		}
		if(prevMatch != null && comparator.compareValue(match, prevMatch)==0) {
			return prevMatch;
		}
		return match;
	}
	
	/**
	 * calculates all possibles combinations that a pattern can be calculated with two cartesian products that do not have patterns that need to be in the same sequence partitioned
	 */
	public static final List<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>> calculatePatternCombinations(final List<IBeXContextPattern> subpatterns, final Set<List<IBeXContextPattern>> forbiddenPatterns){
		List<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>> combinations = new ArrayList<Pair<List<IBeXContextPattern>,List<IBeXContextPattern>>>();
		
		for(Set<IBeXContextPattern> comb: calculatePatternCombination(subpatterns, subpatterns.size()/2)) {
			List<IBeXContextPattern> firstSequence = new ArrayList<IBeXContextPattern>(comb);
			boolean validPattern = true;
			for(List<IBeXContextPattern> forbPattern: forbiddenPatterns) {
				boolean hasPattern = forbPattern.stream().anyMatch(p -> firstSequence.contains(p));
				if(hasPattern && !firstSequence.containsAll(forbPattern)) {
					//this pattern combination is not valid
					validPattern = false;
					break;
				}
			}
			if(!validPattern) continue;
			
			boolean repeatedPattern = combinations.stream().anyMatch(c -> c.getRight().containsAll(firstSequence) && firstSequence.containsAll(c.getRight()));
			if(repeatedPattern) {
				//this pattern combination already exists
				continue;
			}			
			List<IBeXContextPattern> secSequence = subpatterns.stream().filter(pattern -> !firstSequence.contains(pattern)).collect(Collectors.toList());
			
			Pair<List<IBeXContextPattern>, List<IBeXContextPattern>> pair = 
					new Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>(firstSequence, secSequence);
			combinations.add(pair);			
		}
		return combinations;
	}
	
	/**
	 * returns a combination of all possible combinations between subpatterns
	 */
	private static final Set<Set<IBeXContextPattern>> calculatePatternCombination(final List<IBeXContextPattern> subpatterns, int combNr){
		Set<Set<IBeXContextPattern>> combinations = new HashSet<Set<IBeXContextPattern>>();
		
		for(IBeXContextPattern subpattern: subpatterns) {
			Set<IBeXContextPattern> newComb = new HashSet<IBeXContextPattern>();
			newComb.add(subpattern);
			combinations.add(newComb);
			if(combNr > 1) {
				for(Set<IBeXContextPattern> otherComb: calculatePatternCombination(subpatterns, combNr-1)) {
					otherComb.add(subpattern);
					combinations.add(otherComb);
				}
			}
		}
		return combinations;
	}

}
