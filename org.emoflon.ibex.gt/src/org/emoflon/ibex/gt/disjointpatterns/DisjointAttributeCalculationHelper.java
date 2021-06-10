package org.emoflon.ibex.gt.disjointpatterns;

import java.util.ArrayList;
import java.util.Collection;
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

import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.transformations.Pair;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes;

/**
 * Helper class for attribute conditions for disjunct patterns
 *
 */
public class DisjointAttributeCalculationHelper {
	
	/**
	 * updates the old attribute matches and calculates the constraints between two submatches
	 */
	public static final long calculateForbiddenConstraintMatches(final IBeXDisjointContextPattern pattern, final IBeXInterdependentAttributes attribute, 
			final Map<IBeXContextPattern, Collection<IMatch>> submatchesMap, final List<SubmatchAttributeComparator> comparators, final Map<IBeXContextPattern, Collection<IMatch>> oldMatches,
			final Map<IMatch, Collection<IMatch>> targetMap, final Map<IMatch, Collection<IMatch>> sourceMap, final Collection<Object> changedNodes, 
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets, Pair<List<IBeXContextPattern>, List<IBeXContextPattern>> patternSequence) {
		
		//find the target pattern and source list
		List<SubmatchAttributeComparator> constraintComparators  = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> patternSequence.getLeft().containsAll(c.getTargetPatterns()) && !patternSequence.getLeft().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));		

		//find the reverse comparators (source -> target) if there are any; necessary for cyclic dependencies
		List<SubmatchAttributeComparator> reverseComparator = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> patternSequence.getRight().containsAll(c.getTargetPatterns()) && !patternSequence.getRight().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));
		
		//comparators for the cartesian products
		List<SubmatchAttributeComparator> cartesianTargetComparators = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> patternSequence.getLeft().containsAll(c.getTargetPatterns()) && patternSequence.getLeft().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));
		
		List<SubmatchAttributeComparator> cartesianSourceComparators = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> patternSequence.getRight().containsAll(c.getTargetPatterns()) && patternSequence.getRight().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));
		
		//calculate the new and old target matches		
		List<Collection<IMatch>> calculatedTargetMatches = DisjointPatternHelper.createNewAndOldCartesianProducts(pattern, patternSequence.getLeft(), submatchesMap, oldMatches, 
				cartesianTargetComparators);
		Collection<IMatch> oldCalculatedTargetMatches = calculatedTargetMatches.get(1);
		Collection<IMatch> newCalculatedTargetMatches = calculatedTargetMatches.get(0);
		
		//find out which matches were changed
		Collection<IMatch> changedTargetMatches = DisjointPatternHelper.createChangedCartesianProducts(pattern, patternSequence.getLeft(), changedNodes, submatchesMap, oldMatches, 
				newCalculatedTargetMatches, targetMap.keySet(), cartesianTargetComparators);

		//calculate the new and old source matches		
		List<Collection<IMatch>> calculatedSourceMatches = DisjointPatternHelper.createNewAndOldCartesianProducts(pattern, patternSequence.getRight(), submatchesMap, oldMatches, 
				cartesianSourceComparators);
		Collection<IMatch> oldCalculatedSourceMatches = calculatedSourceMatches.get(1);
		Collection<IMatch> newCalculatedSourceMatches = calculatedSourceMatches.get(0);
		
		//find out which matches were changed
		Collection<IMatch> changedSourceMatches = DisjointPatternHelper.createChangedCartesianProducts(pattern, patternSequence.getRight(), changedNodes, submatchesMap, oldMatches, 
				newCalculatedSourceMatches, sourceMap.keySet(), cartesianSourceComparators);

		long forbiddenMatches = calculateForbiddenConstraintMatches(oldCalculatedTargetMatches, changedTargetMatches, 
				newCalculatedTargetMatches, oldCalculatedSourceMatches, changedSourceMatches, 
				newCalculatedSourceMatches, targetMap, sourceMap, sortedNeededSets, constraintComparators, reverseComparator)
				.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();
			
		long matchCount = sourceMap.keySet().size()*targetMap.keySet().size();		
		return matchCount - forbiddenMatches;
			
	}
	
	/**
	 * updates the old attribute matches and calculates the constraints between two submatches dependent of the injectivity constraints between them 
	 */
	public static final long calculateForbiddenConstraintWithInjectivityMatches(final List<SubmatchAttributeComparator> comparators, 
			final List<SubmatchAttributeComparator> reverseComparators, final Collection<IMatch> oldTargetMatches, final Collection<IMatch> changedTargetMatches, 
			final Collection<IMatch> newTargetMatches, final Map<IMatch, Collection<IMatch>> map, final Collection<IMatch> oldCartesianMatches, 
			final Collection<IMatch> changedSourceMatchSet, final Collection<IMatch> newCartesianMatches,
			final Map<IMatch, Collection<IMatch>> map2, final Map<IMatch, Collection<IMatch>> injectivityConstraintMap,
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets) {
		
		 calculateForbiddenConstraintMatches(oldTargetMatches, changedTargetMatches, newTargetMatches,  
				oldCartesianMatches, changedSourceMatchSet, newCartesianMatches, map, map2, sortedNeededSets, comparators, reverseComparators);	
		 
		 //remove all matches forbidden through injectivity constraints
		 for(IMatch match: injectivityConstraintMap.keySet()) {
			 map.get(match).removeAll(injectivityConstraintMap.get(match));
			 for(IMatch sourceMatches: injectivityConstraintMap.get(match)) {
				 map2.get(sourceMatches).remove(match);
			 }
		 }
		 	
		 long constraintValue =  map.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();
		 constraintValue += injectivityConstraintMap.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();
		 return constraintValue;
	}
	
	/**
	 * calculate all the attribute constraints between two submatches
	 */
	public static final Map<IMatch, Collection<IMatch>> calculateForbiddenConstraintMatches(final Collection<IMatch> oldTargetMatch, final Collection<IMatch> changedTargetMatch, 
			final Collection<IMatch> newTargetMatch, final Collection<IMatch>oldSourceMatch, final Collection<IMatch> changedSourceMatch, final Collection<IMatch> newSourceMatch, 
			final Map<IMatch, Collection<IMatch>> targetMap, final Map<IMatch, Collection<IMatch>> map2,
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets, List<SubmatchAttributeComparator> comparators, 
			List<SubmatchAttributeComparator> reverseComparator) {
		
		
		//remove the old target matches
		for(IMatch oldMatch: oldTargetMatch) {
			boolean removed = true;
			for(IMatch dependentSourceMatch: targetMap.remove(oldMatch)) {
				map2.get(dependentSourceMatch).remove(oldMatch);
			}
			for(SubmatchAttributeComparator comparator: comparators) {
				removed &= sortedNeededSets.get(comparator).getLeft().remove(oldMatch);
			}
			for(SubmatchAttributeComparator comparator: reverseComparator) {
				removed &= sortedNeededSets.get(comparator).getRight().remove(oldMatch);
				
			}
			if(!removed) {
				//remove the changed matches -> needs to be found iteratively because it cant be found with the comparator
				for(SubmatchAttributeComparator comparator: comparators) {
					Iterator<IMatch> iter =  sortedNeededSets.get(comparator).getLeft().iterator();
					while(iter.hasNext()) {
						IMatch match = iter.next();
						if(oldMatch.equals(match)) {
							//remove the old value
							iter.remove();
							break;
						}
					}
				}
				for(SubmatchAttributeComparator comparator: reverseComparator) {
					Iterator<IMatch> iter =  sortedNeededSets.get(comparator).getRight().iterator();
					while(iter.hasNext()) {
						IMatch match = iter.next();
						if(oldMatch.equals(match)) {
							//remove the old value
							iter.remove();
							break;
						}
					}
				}
			}	
		}	
		
		//remove the old source matches
		for(IMatch oldMatch: oldSourceMatch) {
			boolean removed = true;
			for(IMatch dependentTargetMatch: map2.remove(oldMatch)) {
				targetMap.get(dependentTargetMatch).remove(oldMatch);
			}
			for(SubmatchAttributeComparator comparator: comparators) {
				removed &= sortedNeededSets.get(comparator).getRight().remove(oldMatch);
			}
			for(SubmatchAttributeComparator comparator: reverseComparator) {
				removed &= sortedNeededSets.get(comparator).getLeft().remove(oldMatch);
			}
			if(!removed) {
				for(SubmatchAttributeComparator comparator: comparators) {
					Iterator<IMatch> iter =  sortedNeededSets.get(comparator).getRight().iterator();
					while(iter.hasNext()) {
						IMatch match = iter.next();
						if(oldMatch.equals(match)) {
							//remove the old value
							iter.remove();
							break;
						}
					}
				}
				for(SubmatchAttributeComparator comparator: reverseComparator) {
					Iterator<IMatch> iter =  sortedNeededSets.get(comparator).getLeft().iterator();
					while(iter.hasNext()) {
						IMatch match = iter.next();
						if(oldMatch.equals(match)) {
							//remove the old value
							iter.remove();
							break;
						}
					}
				}
			}
		}
		
		//remove the changed matches -> needs to be found iteratively because it cant be found with the comparator
		for(IMatch updatedMatch: changedTargetMatch) {
			for(IMatch dependentMatches: targetMap.remove(updatedMatch)){
				map2.get(dependentMatches).remove(updatedMatch);
			}
			for(SubmatchAttributeComparator comparator: comparators) {
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
			for(SubmatchAttributeComparator comparator: reverseComparator) {
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

		//remove the changed source matches -> needs to be found iteratively because it cant be found with the comparator
		for(IMatch updatedMatch: changedSourceMatch) {
			for(IMatch dependentMatches: map2.remove(updatedMatch)){
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
			for(SubmatchAttributeComparator comparator: reverseComparator) {
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
		
		//update the target matches
		for(IMatch newMatch: newTargetMatch) {
			Set<IMatch> allForbiddenMatches = new HashSet<IMatch>();
			for(SubmatchAttributeComparator comparator: comparators) {
				//notReversed = true when newMatch = lhs 
				allForbiddenMatches.addAll(DisjointPatternHelper
						.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getRight(), newMatch, comparator.isLhs(newMatch)));
				sortedNeededSets.get(comparator).getLeft().add(newMatch);
			}			
			for(IMatch match: allForbiddenMatches) {
				map2.get(match).add(newMatch);
			}
			targetMap.put(newMatch, allForbiddenMatches);
		}	
		
		
		//add the new source matches
		for(IMatch newMatch: newSourceMatch) {
			Set<IMatch> allForbiddenMatches = new HashSet<IMatch>();
			for(SubmatchAttributeComparator comparator: comparators) {
				if(comparator.isLegal(newMatch)) {
					//notReversed = false when newMatch = rhs 
					allForbiddenMatches.addAll(DisjointPatternHelper
							.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getLeft(), newMatch, comparator.isLhs(newMatch)));
					sortedNeededSets.get(comparator).getRight().add(newMatch);					
				}
				else {
					//match is illegal for all target matches
					allForbiddenMatches.addAll(new HashSet<IMatch>(targetMap.keySet()));
				}

			}
			for(SubmatchAttributeComparator comparator: reverseComparator) {
				//notReversed = true when newMatch = rhs 
				allForbiddenMatches.addAll(DisjointPatternHelper
						.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getRight(), newMatch, comparator.isLhs(newMatch)));
				sortedNeededSets.get(comparator).getLeft().add(newMatch);					
			}
			for(IMatch dependentTargetMatch: allForbiddenMatches) {
				targetMap.get(dependentTargetMatch).add(newMatch);
			}				
			map2.put(newMatch, allForbiddenMatches);
		}	
		//now use the reverse comparator
		for(IMatch newMatch: newTargetMatch) {
			Set<IMatch> allForbiddenMatches = new HashSet<IMatch>();
			for(SubmatchAttributeComparator comparator: reverseComparator) {
				if(comparator.isLegal(newMatch)) {
					//notReversed = false when newMatch = lhs 					
					allForbiddenMatches.addAll(DisjointPatternHelper
							.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getLeft(), newMatch, comparator.isLhs(newMatch)));
					sortedNeededSets.get(comparator).getRight().add(newMatch);
				}
				else {
					//match is illegal for all target matches
					allForbiddenMatches.addAll(new HashSet<IMatch>(map2.keySet()));	
				}

			}	
			for(IMatch match: allForbiddenMatches) {
				map2.get(match).add(newMatch);
			}
			targetMap.get(newMatch).addAll(allForbiddenMatches);			
		}

		return targetMap;
	}

	/**
	 * finds the best cartesian sequence patterns for calculation
	 */
	public static final Pair<List<IBeXContextPattern>, List<IBeXContextPattern>> findPatternFrequency(final IBeXInterdependentAttributes attribute, final Map<IBeXContextPattern, Collection<IMatch>> submatchesMap){
		Map<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> frequencyMap = findAttributeConstraintFrequency(attribute);

		//search for the pattern with the least dependencies
		Optional<Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>> newmin = frequencyMap.entrySet().stream()
        .min(Comparator.comparingInt(Map.Entry::getValue));
		
		frequencyMap.entrySet().removeIf(entry -> !entry.getValue().equals(newmin.get().getValue()));

		if(frequencyMap.size()>1) {
			//if nothing else -> sort by size of the matches
			Map<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> sizeMap = new HashMap<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>();
			for(Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> entry: frequencyMap.entrySet()) {
				int sizeValue = entry.getKey().getLeft().stream().mapToInt(pattern -> submatchesMap.get(pattern).size()).reduce(1, (a, b) -> a*b);
				sizeValue += entry.getKey().getRight().stream().mapToInt(pattern -> submatchesMap.get(pattern).size()).reduce(1, (a, b) -> a*b);
				sizeMap.put(entry.getKey(), Integer.valueOf(sizeValue));
			}
			Optional<Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>> newSizeMin = sizeMap.entrySet().stream()
			        .min(Comparator.comparingInt(Map.Entry::getValue));
			return newSizeMin.get().getKey();
		}
		else {
			return frequencyMap.entrySet().stream().findAny().get().getKey();
		}
	}
	
	/**
	 * finds out how often a pattern is referenced in an attribute constraint
	 */
	public static Map<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> findAttributeConstraintFrequency(final IBeXInterdependentAttributes attribute){
		Map<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> patternFrequency = new HashMap<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>();
		
		//find all patterns that are not dependent of other patterns in multiple source matches
		Set<List<IBeXContextPattern>> forbiddenPatterns = new HashSet<List<IBeXContextPattern>>();
		for(IBeXDisjointAttribute subattribute: attribute.getAttributes()) {
			if(subattribute.getSourcePattern().size()>1) {
				forbiddenPatterns.add(subattribute.getSourcePattern());
			}
			if(subattribute.getTargetPattern().size()>1) {
				forbiddenPatterns.add(subattribute.getTargetPattern());				
			}
		}
		
		for(Pair<List<IBeXContextPattern>, List<IBeXContextPattern>> pattern: DisjointPatternHelper.calculatePatternCombinations(attribute.getInterdependentPatterns(), forbiddenPatterns)) {
			patternFrequency.put(pattern, Integer.valueOf(0));
		}
		//remove the combinations that are not divided properly
		Optional<Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>> newmin = patternFrequency.entrySet().stream()
		        .min((x1, x2) -> Math.abs(x1.getKey().getLeft().size()-attribute.getInterdependentPatterns().size()/2)-Math.abs(x2.getKey().getLeft().size()-attribute.getInterdependentPatterns().size()/2));
				
		patternFrequency.entrySet().removeIf(entry -> !(entry.getKey().getLeft().size() == newmin.get().getKey().getLeft().size()) && !(entry.getKey().getRight().size() == newmin.get().getKey().getLeft().size()));
		
		//find all constraints that need to be calculated between the two pattern sequences
		for(IBeXDisjointAttribute constraint: attribute.getAttributes()) {
			for(Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> entry: patternFrequency.entrySet()) {
				boolean isPartial = entry.getKey().getLeft().containsAll(constraint.getTargetPattern()) && !entry.getKey().getLeft().containsAll(constraint.getSourcePattern()) ||
						!entry.getKey().getLeft().containsAll(constraint.getTargetPattern()) && entry.getKey().getLeft().containsAll(constraint.getSourcePattern());
				if(isPartial) {
					entry.setValue(Integer.valueOf(entry.getValue().intValue()+1));
				}
			}
		}
		
		return patternFrequency;
	}
}
