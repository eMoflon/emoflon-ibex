package org.emoflon.ibex.gt.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * The GraphTransformationDisjunctPatternInterpreter calculates matches of all disjunct patterns
 */
public class GraphTransformationDisjunctPatternInterpreter {
	
	/**
	 * calculates all matches of a pattern
	 * 
	 * @param pattern the pattern
	 * @param submatches a list with matches for all subpatterns
	 * @return the number of matches
	 */
	public static long calculateMatchCount(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap) {
		//matchCount is one for multiplication purposes; the submatch list size should be bigger than one anyway
		int matchCount = 1;
		List<Long> submatchCount = new ArrayList<Long>();
		//name all subpatterns that have injecitivity constraints
		List<String> constraintPatterns = new ArrayList<String>();
		for(IBeXDependentInjectivityConstraints constraint: pattern.getInjectivityConstraints()) {
			constraintPatterns.addAll(constraint.getPatterns());
		}
		//find all subpatterns that do not have any injectivity constraints
		submatchCount = submatchesMap.entrySet().stream().filter(entry -> !constraintPatterns.contains(entry.getKey().getName()))
		.map(entry -> (long) entry.getValue().size()).collect(Collectors.toList());
		
		//find the match count of all from each other independent subpatterns
		submatchCount.addAll(pattern.getInjectivityConstraints().stream().map(constraint -> calculateSubmatchCount(pattern, constraint, submatchesMap))
				.collect(Collectors.toList()));
		
		for(Long submatches: submatchCount) {
			matchCount *=submatches;
		}
		return matchCount;
	}
	
	/**
	 * finds any match of the pattern
	 * 
	 * @param pattern the pattern
	 * @param submatches the submatches
	 * @return a random match of the pattern
	 */
	public static Optional<IMatch> findAnyMatch(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap){		

		if(calculateMatchCount(pattern, submatchesMap)>0) {
			
			IMatch match = new SimpleMatch(pattern.getName());
			//sorts from the smallest submatch to the biggest
			List<List<IMatch>> submatches = submatchesMap.values().stream().map(matches -> new ArrayList<IMatch>(matches)).collect(Collectors.toList());
			submatches.sort(new SubmatchComparator());
			
			for(int i = 0; i<submatches.size(); i++) {
				
				IMatch submatch;	
				boolean merged = false;
				Collection<IMatch> matches = submatches.get(i);
				Iterator<IMatch> iterator = matches.iterator();
				
				while(iterator.hasNext()) {
					//search for any submatch of the submatches til it finds one that is disjunct with the current match
					submatch = iterator.next();
					
					if(isDisjunct(match, submatch)) {
						match = merge(match, submatch, pattern.getName());
						merged = true;
						break;
					}
				}
				if(merged) continue;
				else {
					//if there is no disjunct pattern in this subpattern, then try again
					i = 0;
					match = new SimpleMatch(pattern.getName());
				}
				
			}
			return Optional.of(match);
		}
		else {
			return Optional.empty();
		}


	}
	
	/**
	 * calculates all matches of a disjunct pattern
	 * @param submatches the submatches of a pattern
	 * @return the matches
	 */
	public static final Stream<IMatch> joinDisjunctSubpatterns(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap){
		return joinDisjunctSubMatches(new ArrayList<Set<IMatch>>(submatchesMap.values()), pattern.getName()).stream();
	}
	
	/**
	 * recursively joins all submatches
	 * 
	 * @param submatches the submatches
	 * @return the all computed matches
	 */
	private static final Set<IMatch> joinDisjunctSubMatches(final List<Set<IMatch>> submatches, final String name){
		if(submatches.size() == 0) {
			throw new IllegalArgumentException("the list of subpatterns should not be empty");
		}
		if(submatches.size() == 1) {
			return submatches.get(0);
		}
		else if(submatches.size() == 2) {
			//calculate the cartesian product of the submatches
			return cartesianProduct(submatches.get(0), submatches.get(1), name);
		}
		else {
			//divide the list into two sublists and then merge them
			return cartesianProduct(joinDisjunctSubMatches(submatches.subList(0, submatches.size()/2),name), 
					joinDisjunctSubMatches(submatches.subList(submatches.size()/2, submatches.size()), name), name);
		}
	}
	/**
	 * calculates the match count for dependent subpatterns
	 */
	private static long calculateSubmatchCount(final IBeXDisjunctContextPattern pattern, 
			final IBeXDependentInjectivityConstraints constraints, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap) {
		
		List<String> parameter1 = new ArrayList<String>();
		List<String> parameter2 = new ArrayList<String>();
		
		if(constraints.getPatterns().size()>2) {
			//sort from smallest to biggest
			List<Set<IMatch>> submatches = new ArrayList<Set<IMatch>>(submatchesMap.values());
			List<String> calculatedMatches = new ArrayList<String>();
			//to know which final sets are calculated
			Set<IMatch> cartesianProduct = new HashSet<IMatch>();
			Set<IMatch> lastMatch = new HashSet<IMatch>();
			
			for(int i = 0; i<constraints.getPatterns().size()-2; i++) {
				//iterate through n-1 patterns and calculate the cartesian product to have two patterns at the end
				cartesianProduct = cartesianProduct(submatches.get(i), submatches.get(i+1), pattern.getName());
				lastMatch = submatches.get(i+2);
				submatches.add(cartesianProduct);
				//find the name of the two subpatterns
				calculatedMatches.addAll(submatchesMap.entrySet().stream()
						.filter(entry -> entry.getValue().equals(submatches.get(0)) || entry.getValue().equals(submatches.get(1)))
				.map(entry -> entry.getKey().getName()).collect(Collectors.toList()));					
			}
			
			//find the constraints between the new pattern and the other subpattern
			for(IBexDisjunctInjectivityConstraint constraint: constraints.getInjectivityConstraints()) {
				if(calculatedMatches.contains(constraint.getPattern1().getName())&& calculatedMatches.contains(constraint.getPattern2().getName())) {
					//this constraint was already solved
					continue;
				}
				if(calculatedMatches.contains(constraint.getPattern1().getName())) {
					parameter1.addAll(constraint.getNode1().stream()
							.map(node -> node.getName()).collect(Collectors.toList()));
					parameter2.addAll(constraint.getNode2().stream()
							.map(node -> node.getName()).collect(Collectors.toList()));
				}
				if(calculatedMatches.contains(constraint.getPattern2().getName())) {
					parameter1.addAll(constraint.getNode2().stream()
							.map(node -> node.getName()).collect(Collectors.toList()));
					parameter2.addAll(constraint.getNode1().stream()
							.map(node -> node.getName()).collect(Collectors.toList()));
				}
			}
			
			return  ((long) cartesianProduct.size())*((long) lastMatch.size()) -
					findForbiddenMatches(parameter1, parameter2, 
					cartesianProduct, lastMatch);	
		}
		else {
			//only two patterns are dependent of each other
			 IBexDisjunctInjectivityConstraint constraint = constraints.getInjectivityConstraints().get(0);
			 
		//name of the nodes that may break the injectivity constraint
			 parameter1 = constraint.getNode1().stream().map(node -> node.getName()).collect(Collectors.toList());
			 parameter2 = constraint.getNode2().stream().map(node -> node.getName()).collect(Collectors.toList());		
			 
		return  ((long) submatchesMap.get(constraint.getPattern1()).size())*((long) submatchesMap.get(constraint.getPattern2()).size()) -
					findForbiddenMatches(parameter1, parameter2, 
					submatchesMap.get(constraint.getPattern1()), submatchesMap.get(constraint.getPattern2()));			 
		}
	}
	
	/**
	 * find all Objects in the match that break the injectivity constraints
	 * 
	 * @param match1 the first match
	 * @param match2 the second match
	 * @param constraints the constraints between the two submatches
	 * @return the sum of forbidden matches
	 */
	private static final long findForbiddenMatches(final List<String> parameter1, final List<String> parameter2,
			final Set<IMatch> match1, final Set<IMatch> match2){		
		//convert the sets
		Set<Object> set1 = new HashSet<Object>();
		Set<Object> set2 = new HashSet<Object>();
		
		//the size of all parameters in the matches
		int patternSize1 = match1.iterator().next().getParameterNames().size();
		int patternSize2 = match2.iterator().next().getParameterNames().size();
		
		//get all nodes that could break the injectivity constraints
		for(IMatch match: match1) {
			for(String parameter: parameter1) {
				set1.add(match.get(parameter));
			}
			
		}
		for(IMatch match: match2) {
			for(String parameter: parameter2) {
				set2.add(match.get(parameter));
			}
			
		}
		
		Set<Object> intersection = new HashSet<Object>();
		//calculate the intersection
		for(Object object: set1) {
			if(set2.contains(object)) {
				intersection.add(object);
			}
		}
		//calculate the match count for forbidden matches		
		long matchCount = 0;
		//when the pattern only has one node then the size of matches with the node that breaks the injectivity constraint is the size
		//of the intersection
		if(patternSize1 == 1) {
			if(patternSize2 == 1) {
				//the number of forbidden matches is the size of the intersection
				matchCount = intersection.size();
			}else {
				Map<Object, Long> map2 = calculateMatchMap(match2, intersection, parameter2);
				for(Object object: intersection) {
					//the size of forbidden matches for each object is map2.get(object)*1
					matchCount += map2.get(object);
				}
			}
		}else {
			Map<Object, Long> map1 = calculateMatchMap(match1, intersection, parameter1);
			if(patternSize2 == 1) {
				for(Object object: intersection) {
					matchCount += map1.get(object);
				}			
			}else {
				Map<Object, Long> map2 = calculateMatchMap(match2, intersection, parameter2);
				//the sum of forbidden matches is sum(sum(objects in submatches1)*sum(objects in submatches2))
				for(Object object: intersection) {
					matchCount += map1.get(object)*map2.get(object);
				}
			}
		}
		return matchCount;		
	}
	
	/**
	 * find all matches that contain objects from the intersection
	 */
	private static final Map<Object, Long> calculateMatchMap(final Set<IMatch> matches, final Set<Object> intersection, final List<String> parameters){
		Map<Object, Long> matchMap = new HashMap<Object, Long>();
		for(IMatch match: matches) {
			for(String parameter: parameters) {
				if(intersection.contains(match.get(parameter))) {
					matchMap.compute(match.get(parameter), (k, v) -> (v == null)? v = (long) 1: (long) v+1);
				}
			}
		}
		return matchMap;
	}
	/**
	 * calculates the cartesian product between two matches
	 * 
	 * @param match1 the first matchSet
	 * @param match2 the second matchSet
	 * @return the cartesian product
	 */
	private static final Set<IMatch> cartesianProduct(final Set<IMatch> match1, final Set<IMatch> match2, final String name){
		Set<IMatch> newSubMatches = new HashSet<IMatch>();
		for(IMatch firstMatch: match1){
			for(IMatch secondMatch: match2) {
				if(isDisjunct(firstMatch, secondMatch)) {
					newSubMatches.add(merge(firstMatch, secondMatch, name));
				}		
			}
		}
		return newSubMatches;
	}
	
	/**
	 * check if two submatches are disjunct
	 */
	private static boolean isDisjunct(IMatch match1, final IMatch match2) {
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
	 * merge two submatches to a bigger match
	 * 
	 * @param match1 the first match
	 * @param match2 the second match
	 * @return the bigger match
	 */
	private static final IMatch merge(final IMatch match1, final IMatch match2, final String name) {
		IMatch newMatch = new SimpleMatch(match1);
		newMatch.setPatternName(name);
		match2.getParameterNames().forEach(parameterName -> newMatch.put(parameterName, match2.get(parameterName)));
		return newMatch;
	}
	
	/**
	 * Helper class for sorting subpatterns
	 *
	 */
	private static class SubmatchComparator implements Comparator<Collection<IMatch>>{
		@Override
		public int compare(Collection<IMatch> o1, Collection<IMatch> o2) {
			return o1.size()-o2.size();
		}
		
	}
}
