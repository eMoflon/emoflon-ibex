package org.emoflon.ibex.gt.disjunctpatterns;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.transformations.Pair;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * Helper class for attribute conditions for disjunct patterns
 *
 */
public class DisjunctAttributeCalculationHelper {
	
	/**
	 * updates the old attribute matches and calculates the constraints between two submatches
	 */
	public static final long calculateForbiddenConstraintMatches(final IBeXDisjunctContextPattern pattern, final IBeXDependentDisjunctAttribute attribute, 
			final Map<IBeXContextPattern, Set<IMatch>> submatchesMap, final Map<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> comparators, final Map<IBeXContextPattern, Set<IMatch>> oldMatches,
			final Map<IMatch, Set<IMatch>> targetMap, final Map<IMatch, Set<IMatch>> sourceMap, final Set<Object> changedNodes, 
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets, List<IBeXContextPattern> patternSequence) {
		
		//map used for the cartesian product
		Map<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> comparatorMap = new HashMap<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>>();
		List<SubmatchAttributeComparator> constraintComparators  = new ArrayList<SubmatchAttributeComparator>();
		
		//find the target pattern and source list
		IBeXContextPattern targetPattern;

		//find the ibexdisjunctattribute
		Optional<IBeXDisjunctAttribute> subattribute = attribute.getAttributes().stream().filter(subsubattribute -> !patternSequence.contains(subsubattribute.getTargetPattern())).findAny();
		if(subattribute.isEmpty()) throw new IllegalArgumentException("target pattern constraint was not found");
		
		
		
		if(attribute.getAttributes().size() == 1) {
			targetPattern = attribute.getAttributes().get(0).getTargetPattern();
		}
		else{
			targetPattern = subattribute.get().getTargetPattern();
		}
		
		Set<SubmatchAttributeComparator> tempComparator = new HashSet<SubmatchAttributeComparator>();
		//find out which comparators are needed for the cartesian product and which for the forbidden constraints
		for(Entry<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> comparator: comparators.entrySet()) {
			for(SubmatchAttributeComparator subcomparator: comparator.getValue()) {
				if(subcomparator.getTargetPatternName().equals(targetPattern.getName())) {
					tempComparator.add(subcomparator);
				}
				else {
					comparatorMap.compute(comparator.getKey(), (k, v) ->{
						if(v==null) {
							v = new ArrayList<SubmatchAttributeComparator>();
							v.add(subcomparator);
						}
						else {
							v.add(subcomparator);
						}
						return v;
					});
				}					
			}
			
		}
		constraintComparators.addAll(tempComparator);
		
		Set<IMatch> oldTargetMatch = oldMatches.get(targetPattern);	
		Set<IMatch> currentTargetMatch = submatchesMap.get(targetPattern);	
		
		Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedSets = sortedNeededSets.entrySet().stream()
				.filter(entry -> constraintComparators.contains(entry.getKey())).collect(Collectors.toMap(c->c.getKey(), c->c.getValue()));
		
		//calculate the new and old target matches
		Set<IMatch> newTargetMatches = currentTargetMatch.parallelStream().filter(match -> !oldTargetMatch.contains(match)).collect(Collectors.toSet());
		Set<IMatch> oldTargetMatches = oldTargetMatch.parallelStream().filter(match -> !currentTargetMatch.contains(match)).collect(Collectors.toSet());
		Set<IMatch> changedTargetMatches = currentTargetMatch.parallelStream().filter(match -> {
			if(!oldTargetMatch.contains(match)) return false;
			for(String parameter: match.getParameterNames()) {
				if(changedNodes.contains(match.get(parameter))) {
					return true;
				}
			}
			return false;
		}).collect(Collectors.toSet());
		//add the changed Matches to the new Matches
		newTargetMatches.addAll(changedTargetMatches);
		
		//calculate the new and old source Matches		
		Map<IBeXContextPattern, Set<IMatch>> oldSourceMatch = patternSequence.stream().collect(Collectors.toMap(subpattern -> subpattern, subpattern -> oldMatches.get(subpattern)));
		Map<IBeXContextPattern, Set<IMatch>> currentSourceMatch = patternSequence.stream().collect(Collectors.toMap(subpattern -> subpattern, subpattern -> submatchesMap.get(subpattern)));
		
		//find out which matches where added	
		Map<IBeXContextPattern, Set<IMatch>> newSourceMatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		for(IBeXContextPattern subpattern: currentSourceMatch.keySet()) {
			Set<IMatch> matches = oldSourceMatch.get(subpattern);
			newSourceMatches.put(subpattern, currentSourceMatch.get(subpattern).parallelStream().filter(match -> !matches.contains(match)).collect(Collectors.toSet()));			

		}			
		//find out which matches where removed	
		Map<IBeXContextPattern, Set<IMatch>> oldSourceMatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		for(IBeXContextPattern subpattern: oldSourceMatch.keySet()) {
			Set<IMatch> matches = currentSourceMatch.get(subpattern);			
			oldSourceMatches.put(subpattern, oldSourceMatch.get(subpattern).parallelStream().filter(match -> !matches.contains(match)).collect(Collectors.toSet()));
		}
		//find out which matches were changed
		Map<IBeXContextPattern, Set<IMatch>> changedSourceMatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		for(IBeXContextPattern subpattern: currentSourceMatch.keySet()) {
			Set<IMatch> matches = oldSourceMatches.get(subpattern);
			Set<IMatch> newMatches = currentSourceMatch.get(subpattern);
			changedSourceMatches.put(subpattern, newMatches.parallelStream().filter(match -> {
				if(matches.contains(match)) return false;
				for(String parameter: match.getParameterNames()) {
					if(changedNodes.contains(match.get(parameter))) {
						return true;
					}
				}
				return false;
			}).collect(Collectors.toSet()));
		}
		//add the changed source matches to the new matches
		for(IBeXContextPattern subpattern: changedSourceMatches.keySet()) {
			newSourceMatches.compute(subpattern, (k, v) -> {
				if(v==null) {
					v = new HashSet<IMatch>();
					v.addAll(changedSourceMatches.get(subpattern));
				}
				else {
					v.addAll(changedSourceMatches.get(subpattern));
				}
				return v;
			});
		}
		
		Set<IMatch> changedSourceMatchSet = changedSourceMatches.values().stream().flatMap(c -> c.stream()).collect(Collectors.toSet());
	
		if(patternSequence.size() == 1) {
			
			long forbiddenMatches = calculateForbiddenConstraintMatches(pattern, subattribute.get(), oldTargetMatches, changedTargetMatches, newTargetMatches,  
					oldSourceMatches.get(patternSequence.get(0)), changedSourceMatchSet, 
					newSourceMatches.get(patternSequence.get(0)), targetMap, sourceMap, sortedSets, constraintComparators)
					.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();
			
			long matchCount = sourceMap.keySet().size()*targetMap.keySet().size();		
			return matchCount - forbiddenMatches;
		}
		else {
			//calculate the cartesian product of the old matches
			Set<IMatch> oldCartesianMatches = new HashSet<IMatch>();
			for(IBeXContextPattern subpattern: patternSequence) {				
				oldCartesianMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, patternSequence, subpattern, oldSourceMatches.get(subpattern),
						currentSourceMatch, comparatorMap));	
			}
			
			//calculate the cartesian product of the new matches
			Set<IMatch> newCartesianMatches = new HashSet<IMatch>();
			for(IBeXContextPattern subpattern: patternSequence) {
				//calculate the cartesian product
				newCartesianMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, patternSequence, subpattern, newSourceMatches.get(subpattern),
						currentSourceMatch, comparatorMap));	
			}

			long forbiddenMatches = calculateForbiddenConstraintMatches(pattern, subattribute.get(), oldTargetMatches, changedTargetMatches, newTargetMatches,  
					oldCartesianMatches, changedSourceMatchSet, newCartesianMatches, targetMap, sourceMap, sortedSets, constraintComparators)
					.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();	
			
			long matchCount = sourceMap.keySet().size()*targetMap.keySet().size();		
			return matchCount - forbiddenMatches;
		}		
	}

	public static final long calculateForbiddenConstraintWithInjectivityMatches(final IBeXDisjunctContextPattern pattern, final IBeXDisjunctAttribute attribute, 
			final List<SubmatchAttributeComparator> comparators, final Set<IMatch> oldTargetMatches, final Set<IMatch> changedTargetMatches, final Set<IMatch> newTargetMatches, 
			final Map<IMatch, Set<IMatch>> targetMap, final Set<IMatch> oldCartesianMatches, final Set<IMatch> changedSourceMatchSet, final Set<IMatch> newCartesianMatches,
			final Map<IMatch, Set<IMatch>> sourceMap, final Map<IMatch, Set<IMatch>> injectivityConstraintMap,
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets) {
		
		 calculateForbiddenConstraintMatches(pattern, attribute, oldTargetMatches, changedTargetMatches, newTargetMatches,  
				oldCartesianMatches, changedSourceMatchSet, newCartesianMatches, targetMap, sourceMap, sortedNeededSets, comparators);	
		 
		 //remove all matches forbidden through injectivity constraints
		 for(Entry<IMatch, Set<IMatch>> injectivityConstraint: injectivityConstraintMap.entrySet()) {
			 targetMap.get(injectivityConstraint.getKey()).removeAll(injectivityConstraint.getValue());
			 for(IMatch sourceMatches: injectivityConstraint.getValue()) {
				 sourceMap.get(sourceMatches).remove(injectivityConstraint.getKey());
			 }
		 }
		 
		 long constraintValue =  targetMap.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();
		 constraintValue += injectivityConstraintMap.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();
		 return constraintValue;
	}
	
	/**
	 * calculate all the attribute constraints between two submatches
	 */
	public static final Map<IMatch, Set<IMatch>> calculateForbiddenConstraintMatches(final IBeXDisjunctContextPattern pattern, final IBeXDisjunctAttribute attribute,
			final Set<IMatch> oldTargetMatch, final Set<IMatch> changedTargetMatch, final Set<IMatch> newTargetMatch, final Set<IMatch>oldSourceMatch, 
			final Set<IMatch> changedSourceMatch, final Set<IMatch> newSourceMatch, final Map<IMatch, Set<IMatch>> targetMap, final Map<IMatch, Set<IMatch>> sourceMap,
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets, List<SubmatchAttributeComparator> comparators) {
		
		//remove the old target matches
		for(IMatch oldMatch: oldTargetMatch) {
			for(IMatch dependentSourceMatch: targetMap.remove(oldMatch)) {
				sourceMap.get(dependentSourceMatch).remove(oldMatch);
			}
			for(SubmatchAttributeComparator comparator: sortedNeededSets.keySet()) {
				sortedNeededSets.get(comparator).getLeft().remove(oldMatch);
			}
		}	
		
		//remove the changed matches -> needs to be found iteratively because it cant be found with the comparator
		for(IMatch updatedMatch: changedTargetMatch) {
			for(IMatch dependentMatches: targetMap.remove(updatedMatch)){
				sourceMap.get(dependentMatches).remove(updatedMatch);
			}

			for(SubmatchAttributeComparator comparator: sortedNeededSets.keySet()) {
				Iterator<IMatch> iter =  sortedNeededSets.get(comparator).getLeft().iterator();
				while(iter.hasNext()) {
					IMatch match = iter.next();
					if(updatedMatch.equals(match)) {
						//remove the old value
						iter.remove();
						break;
					}
				}
			}
		}	
		
		//remove the old source matches
		for(IMatch oldMatch: oldSourceMatch) {
			for(IMatch dependentTargetMatch: sourceMap.remove(oldMatch)) {
				targetMap.get(dependentTargetMatch).remove(oldMatch);
			}
			for(SubmatchAttributeComparator comparator: comparators) {
				sortedNeededSets.get(comparator).getRight().remove(oldMatch);
			}
		}
		
		//remove the changed source matches -> needs to be found iteratively because it cant be found with the comparator
		for(IMatch updatedMatch: changedSourceMatch) {
			for(IMatch dependentMatches: sourceMap.remove(updatedMatch)){
				targetMap.get(dependentMatches).remove(updatedMatch);
			}
			for(SubmatchAttributeComparator comparator: comparators) {
				Iterator<IMatch> iter =  sortedNeededSets.get(comparator).getRight().iterator();
				while(iter.hasNext()) {
					IMatch match = iter.next();
					if(updatedMatch.equals(match)) {
						//remove the old value
						iter.remove();
						break;
					}
				}
			}
		}
		
		//update the target matches
		for(IMatch newMatch: newTargetMatch) {
			Set<IMatch> allForbiddenMatches = new HashSet<IMatch>();
			for(SubmatchAttributeComparator comparator: comparators) {
				allForbiddenMatches.addAll(DisjunctPatternHelper.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getRight(), newMatch, false));
				sortedNeededSets.get(comparator).getLeft().add(newMatch);
			}
			for(IMatch match: allForbiddenMatches) {
				sourceMap.get(match).add(newMatch);
			}
			targetMap.put(newMatch, allForbiddenMatches);
		}	
		
		
		//add the new source matches
		for(IMatch newMatch: newSourceMatch) {
			Set<IMatch> allForbiddenMatches = new HashSet<IMatch>();
			for(SubmatchAttributeComparator comparator: comparators) {
				if(comparator.isLegal(newMatch)) {
					allForbiddenMatches.addAll(DisjunctPatternHelper.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getLeft(), newMatch, true));
					sortedNeededSets.get(comparator).getRight().add(newMatch);					
				}
				else {
					//match is illegal for all target matches
					allForbiddenMatches.addAll(new HashSet<IMatch>(targetMap.keySet()));	
				}

			}
			for(IMatch dependentTargetMatch: allForbiddenMatches) {
				targetMap.get(dependentTargetMatch).add(newMatch);
			}				
			sourceMap.put(newMatch, allForbiddenMatches);
		}	


		return targetMap;
	}
	
	public static final List<IBeXContextPattern> findPatternFrequency(final IBeXDependentDisjunctAttribute attribute, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap){
		Map<IBeXContextPattern, Integer> frequencyMap = findAttributeConstraintFrequency(attribute);
		List<IBeXContextPattern> cartesianPatterns = new ArrayList<IBeXContextPattern>();
		IBeXContextPattern pattern;
		//search for the pattern with the least dependencies
		Optional<Entry<IBeXContextPattern, Integer>> newmin = frequencyMap.entrySet().stream()
        .min(Comparator.comparingInt(Map.Entry::getValue));
		
		Map<IBeXContextPattern, Integer> newFrequencyMap = new HashMap<IBeXContextPattern, Integer>();
		for(Entry<IBeXContextPattern, Integer> entry: frequencyMap.entrySet()) {
			if(entry.getValue() == newmin.get().getValue()) {
				newFrequencyMap.put(entry.getKey(), entry.getValue());
			}
		}
		if(newFrequencyMap.size()>1) {
			//if nothing else -> sort by size of the matches
			Optional<Entry<IBeXContextPattern, Set<IMatch>>> endValues = submatchesMap.entrySet().stream().filter(subpattern -> newFrequencyMap.containsKey(subpattern.getKey()))
					.min(((e1, e2) -> e1.getValue().size()- e2.getValue().size()));
			pattern = endValues.get().getKey();
		}
		else {
			pattern = newmin.get().getKey();
		}
		
		cartesianPatterns.addAll(attribute.getDependentPatterns().stream().filter(otherpattern -> !otherpattern.equals(pattern)).collect(Collectors.toList()));
		return cartesianPatterns;
	}
	/**
	 * finds out how often a pattern is referenced in an attribute constraint
	 */
	public static Map<IBeXContextPattern, Integer> findAttributeConstraintFrequency(final IBeXDependentDisjunctAttribute attribute){
		Map<IBeXContextPattern, Integer> patternFrequency = new HashMap<IBeXContextPattern, Integer>();
		for(IBeXContextPattern pattern: attribute.getDependentPatterns()) {
			for(IBeXDisjunctAttribute constraint: attribute.getAttributes()) {
				if(constraint.getTargetPattern().equals(pattern)) {
					patternFrequency.compute(pattern, (k, v) -> (v==null)? 1: Integer.valueOf(v+1));
				}
			}
		}
		return patternFrequency;
	}
}
