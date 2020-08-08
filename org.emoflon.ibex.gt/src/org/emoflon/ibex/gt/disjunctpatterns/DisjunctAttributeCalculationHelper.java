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
			final Map<IBeXContextPattern, Set<IMatch>> submatchesMap, final List<SubmatchAttributeComparator> comparators, final Map<IBeXContextPattern, Set<IMatch>> oldMatches,
			final Map<IMatch, Set<IMatch>> targetMap, final Map<IMatch, Set<IMatch>> sourceMap, final Set<Object> changedNodes, 
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets, Pair<List<IBeXContextPattern>, List<IBeXContextPattern>> patternSequence) {
		
		//find the target pattern and source list
		List<SubmatchAttributeComparator> constraintComparators  = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> patternSequence.getLeft().contains(c.getTargetPattern()) && !patternSequence.getLeft().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));		

		//find the reverse comparators (source -> target) if there are any; necessary for cyclic dependencies
		List<SubmatchAttributeComparator> reverseComparator = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> patternSequence.getRight().contains(c.getTargetPattern()) && !patternSequence.getRight().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));
		
		//comparators for the cartesian products
		List<SubmatchAttributeComparator> cartesianTargetComparators = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> patternSequence.getLeft().contains(c.getTargetPattern()) && patternSequence.getLeft().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));
		
		List<SubmatchAttributeComparator> cartesianSourceComparators = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> patternSequence.getRight().contains(c.getTargetPattern()) && patternSequence.getRight().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));
		
		//calculate the new and old target matches		
		List<Set<IMatch>> calculatedTargetMatches = DisjunctPatternHelper.createNewAndOldCartesianProducts(pattern, patternSequence.getLeft(), submatchesMap, oldMatches, 
				cartesianTargetComparators);
		Set<IMatch> oldCalculatedTargetMatches = calculatedTargetMatches.get(1);
		Set<IMatch> newCalculatedTargetMatches = calculatedTargetMatches.get(0);
		
		//find out which matches were changed
		Set<IMatch> changedTargetMatches = DisjunctPatternHelper.createChangedCartesianProducts(pattern, patternSequence.getLeft(), changedNodes, submatchesMap, oldMatches, 
				newCalculatedTargetMatches, cartesianTargetComparators);

		//calculate the new and old source matches		
		List<Set<IMatch>> calculatedSourceMatches = DisjunctPatternHelper.createNewAndOldCartesianProducts(pattern, patternSequence.getRight(), submatchesMap, oldMatches, 
				cartesianSourceComparators);
		Set<IMatch> oldCalculatedSourceMatches = calculatedSourceMatches.get(1);
		Set<IMatch> newCalculatedSourceMatches = calculatedSourceMatches.get(0);
		
		//find out which matches were changed
		Set<IMatch> changedSourceMatches = DisjunctPatternHelper.createChangedCartesianProducts(pattern, patternSequence.getRight(), changedNodes, submatchesMap, oldMatches, 
				newCalculatedSourceMatches, cartesianSourceComparators);

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
			final List<SubmatchAttributeComparator> reverseComparators, final Set<IMatch> oldTargetMatches, final Set<IMatch> changedTargetMatches, 
			final Set<IMatch> newTargetMatches, final Map<IMatch, Set<IMatch>> targetMap, final Set<IMatch> oldCartesianMatches, 
			final Set<IMatch> changedSourceMatchSet, final Set<IMatch> newCartesianMatches,
			final Map<IMatch, Set<IMatch>> sourceMap, final Map<IMatch, Set<IMatch>> injectivityConstraintMap,
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets) {
		
		 calculateForbiddenConstraintMatches(oldTargetMatches, changedTargetMatches, newTargetMatches,  
				oldCartesianMatches, changedSourceMatchSet, newCartesianMatches, targetMap, sourceMap, sortedNeededSets, comparators, reverseComparators);	
		 
		 //remove all matches forbidden through injectivity constraints
		 for(IMatch match: injectivityConstraintMap.keySet()) {
			 targetMap.get(match).removeAll(injectivityConstraintMap.get(match));
			 for(IMatch sourceMatches: injectivityConstraintMap.get(match)) {
				 sourceMap.get(sourceMatches).remove(match);
			 }
		 }
		 	
		 long constraintValue =  targetMap.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();
		 constraintValue += injectivityConstraintMap.values().parallelStream().mapToLong(matches -> (long) matches.size()).sum();
		 return constraintValue;
	}
	
	/**
	 * calculate all the attribute constraints between two submatches
	 */
	public static final Map<IMatch, Set<IMatch>> calculateForbiddenConstraintMatches(final Set<IMatch> oldTargetMatch, final Set<IMatch> changedTargetMatch, 
			final Set<IMatch> newTargetMatch, final Set<IMatch>oldSourceMatch, final Set<IMatch> changedSourceMatch, final Set<IMatch> newSourceMatch, 
			final Map<IMatch, Set<IMatch>> targetMap, final Map<IMatch, Set<IMatch>> sourceMap,
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedNeededSets, List<SubmatchAttributeComparator> comparators, 
			List<SubmatchAttributeComparator> reverseComparator) {
		
		
		//remove the old target matches
		for(IMatch oldMatch: oldTargetMatch) {
			for(IMatch dependentSourceMatch: targetMap.remove(oldMatch)) {
				sourceMap.get(dependentSourceMatch).remove(oldMatch);
			}
			for(SubmatchAttributeComparator comparator: comparators) {
				sortedNeededSets.get(comparator).getLeft().remove(oldMatch);
			}
			for(SubmatchAttributeComparator comparator: reverseComparator) {
				sortedNeededSets.get(comparator).getRight().remove(oldMatch);
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
			for(SubmatchAttributeComparator comparator: reverseComparator) {
				sortedNeededSets.get(comparator).getLeft().remove(oldMatch);
			}
		}
		
		//remove the changed matches -> needs to be found iteratively because it cant be found with the comparator
		for(IMatch updatedMatch: changedTargetMatch) {
			for(IMatch dependentMatches: targetMap.remove(updatedMatch)){
				sourceMap.get(dependentMatches).remove(updatedMatch);
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
				allForbiddenMatches.addAll(DisjunctPatternHelper.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getRight(), newMatch, true));
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
					allForbiddenMatches.addAll(DisjunctPatternHelper.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getLeft(), newMatch, false));
					sortedNeededSets.get(comparator).getRight().add(newMatch);					
				}
				else {
					//match is illegal for all target matches
					allForbiddenMatches.addAll(new HashSet<IMatch>(targetMap.keySet()));	
				}

			}
			for(SubmatchAttributeComparator comparator: reverseComparator) {
				allForbiddenMatches.addAll(DisjunctPatternHelper.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getRight(), newMatch, true));
				sortedNeededSets.get(comparator).getLeft().add(newMatch);					
			}
			for(IMatch dependentTargetMatch: allForbiddenMatches) {
				targetMap.get(dependentTargetMatch).add(newMatch);
			}				
			sourceMap.put(newMatch, allForbiddenMatches);
		}	
		//now use the reverse comparator
		for(IMatch newMatch: newTargetMatch) {
			Set<IMatch> allForbiddenMatches = new HashSet<IMatch>();
			for(SubmatchAttributeComparator comparator: reverseComparator) {
				if(comparator.isLegal(newMatch)) {
					allForbiddenMatches.addAll(DisjunctPatternHelper.findSubsetWithAttributeConstraint(comparator, sortedNeededSets.get(comparator).getLeft(), newMatch, false));
					sortedNeededSets.get(comparator).getRight().add(newMatch);
				}
				else {
					//match is illegal for all target matches
					allForbiddenMatches.addAll(new HashSet<IMatch>(sourceMap.keySet()));	
				}

			}	
			for(IMatch match: allForbiddenMatches) {
				sourceMap.get(match).add(newMatch);
			}
			targetMap.get(newMatch).addAll(allForbiddenMatches);			
		}

		return targetMap;
	}
	
	/**
	 * finds the best cartesian sequence patterns for calculation
	 */
	public static final Pair<List<IBeXContextPattern>, List<IBeXContextPattern>> findPatternFrequency(final IBeXDependentDisjunctAttribute attribute, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap){
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
	public static Map<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> findAttributeConstraintFrequency(final IBeXDependentDisjunctAttribute attribute){
		Map<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> patternFrequency = new HashMap<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>();
		
		//find all patterns that are not dependent of other patterns in multiple source matches
		Set<List<IBeXContextPattern>> forbiddenPatterns = new HashSet<List<IBeXContextPattern>>();
		for(IBeXDisjunctAttribute subattribute: attribute.getAttributes()) {
			if(subattribute.getSourcePattern().size()>1) {
				forbiddenPatterns.add(subattribute.getSourcePattern());
			}
		}
		
		for(Pair<List<IBeXContextPattern>, List<IBeXContextPattern>> pattern: DisjunctPatternHelper.calculatePatternCombinations(attribute.getDependentPatterns(), forbiddenPatterns)) {
			patternFrequency.put(pattern, Integer.valueOf(0));
		}
		//find all constraints that need to be calculated between the two pattern sequences
		for(IBeXDisjunctAttribute constraint: attribute.getAttributes()) {
			for(Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> entry: patternFrequency.entrySet()) {
				boolean isPartial = entry.getKey().getLeft().contains(constraint.getTargetPattern()) && !entry.getKey().getLeft().containsAll(constraint.getSourcePattern()) ||
						!entry.getKey().getLeft().contains(constraint.getTargetPattern()) && entry.getKey().getLeft().containsAll(constraint.getSourcePattern());
				if(isPartial) {
					entry.setValue(Integer.valueOf(entry.getValue().intValue()+1));
				}
			}
		}
		
		return patternFrequency;
	}
}
