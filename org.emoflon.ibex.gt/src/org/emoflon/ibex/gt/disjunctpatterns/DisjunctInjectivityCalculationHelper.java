package org.emoflon.ibex.gt.disjunctpatterns;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.transformations.Pair;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

public class DisjunctInjectivityCalculationHelper {

	/**
	 * calculates the match count for dependent subpatterns
	 */
	public static long calculateSubmatchCount(final IBeXDisjunctContextPattern pattern, 
			final IBeXDependentInjectivityConstraints constraints, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap, final Map<IBeXContextPattern, Set<IMatch>> oldMatches, 
			final Pair<List<IBeXContextPattern>,List<IBeXContextPattern>> cartesianSequence, final Map<IBeXDependentInjectivityConstraints, Pair<Set<IMatch>, Set<IMatch>>> oldCartesianProducts, 
			final List<SubmatchAttributeComparator> comparators, 
			final Map<IBeXDependentInjectivityConstraints, Pair<Map<IMatch, Set<IMatch>>, Map<IMatch, Set<IMatch>>>> oldInjectivityConstraints,
			final Map<IBeXDependentDisjunctAttribute, Map<IMatch, Set<IMatch>>> targetMap, final Map<IBeXDependentDisjunctAttribute, Map<IMatch, Set<IMatch>>> sourceMap, 
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sourceNeededSets, Set<Object> changedNodes) {
		
		//the parameter names
		List<String> parameter1 = new ArrayList<String>(constraints.getInjectivityConstraints().stream()
				.filter(constraint -> cartesianSequence.getRight().contains(constraint.getPattern1()) && cartesianSequence.getLeft().contains(constraint.getPattern2())
						|| cartesianSequence.getRight().contains(constraint.getPattern2()) && cartesianSequence.getLeft().contains(constraint.getPattern1()))
				.flatMap(constraint -> {
					if(!cartesianSequence.getRight().contains(constraint.getPattern1())) return constraint.getNode1().stream().map(node -> node.getName());
					else return constraint.getNode2().stream().map(node -> node.getName());
				}).collect(Collectors.toSet()));
		
		List<String> parameter2 = new ArrayList<String>(constraints.getInjectivityConstraints().stream()
				.filter(constraint -> {
					return cartesianSequence.getLeft().contains(constraint.getPattern1()) && cartesianSequence.getRight().contains(constraint.getPattern2()) 
							|| cartesianSequence.getLeft().contains(constraint.getPattern2()) && cartesianSequence.getRight().contains(constraint.getPattern1());
				}).flatMap(constraint -> {
					if(!cartesianSequence.getLeft().contains(constraint.getPattern1())) return constraint.getNode1().stream().map(node -> node.getName());
					else return constraint.getNode2().stream().map(node -> node.getName());
				}).collect(Collectors.toSet()));
		
		//find the necessary submatch comparators for the cartesian products
		List<SubmatchAttributeComparator> cartesianSourceComparators = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> cartesianSequence.getRight().contains(c.getTargetPattern()) && cartesianSequence.getRight().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));

		List<SubmatchAttributeComparator> cartesianTargetComparators = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
				.filter(c -> cartesianSequence.getLeft().contains(c.getTargetPattern()) && cartesianSequence.getLeft().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));

		Set<IMatch> cartesianTargetProduct = new HashSet<IMatch>();
		Set<IMatch> oldTargetMatches;		
		
		if(cartesianSequence.getLeft().size()>1) {
			oldTargetMatches = oldCartesianProducts.get(constraints).getLeft();
			cartesianTargetProduct = new HashSet<IMatch>(oldCartesianProducts.get(constraints).getLeft());
		}
		else {
			oldTargetMatches = oldMatches.get(cartesianSequence.getLeft().get(0));
			cartesianTargetProduct.addAll(submatchesMap.get(cartesianSequence.getLeft().get(0)));
		}			
		//find the matches which were added and removed
		List<Set<IMatch>> calculatedTargetMatches = DisjunctPatternHelper.createNewAndOldCartesianProducts(pattern, cartesianSequence.getLeft(), submatchesMap, oldMatches, 
				cartesianTargetComparators);
		Set<IMatch> oldCalculatedTargetMatches = calculatedTargetMatches.get(1);
		Set<IMatch> newCalculatedTargetMatches = calculatedTargetMatches.get(0);
		cartesianTargetProduct.addAll(newCalculatedTargetMatches);
		cartesianTargetProduct.removeAll(oldCalculatedTargetMatches);
		
		Set<IMatch> cartesianSourceProduct = new HashSet<IMatch>();
		Set<IMatch> oldSourceMatches;		
		
		if(cartesianSequence.getRight().size()>1) {
			oldSourceMatches = oldCartesianProducts.get(constraints).getRight();
			cartesianSourceProduct = new HashSet<IMatch>(oldCartesianProducts.get(constraints).getRight());
		}
		else {
			oldSourceMatches = oldMatches.get(cartesianSequence.getRight().get(0));
			cartesianSourceProduct.addAll(submatchesMap.get(cartesianSequence.getRight().get(0)));
		}			
		//find the matches which were added and removed
		List<Set<IMatch>> calculatedSourceMatches = DisjunctPatternHelper.createNewAndOldCartesianProducts(pattern, cartesianSequence.getRight(), submatchesMap, oldMatches, 
				cartesianSourceComparators);
		Set<IMatch> oldCalculatedSourceMatches = calculatedSourceMatches.get(1);
		Set<IMatch> newCalculatedSourceMatches = calculatedSourceMatches.get(0);
		cartesianSourceProduct.addAll(newCalculatedSourceMatches);
		cartesianSourceProduct.removeAll(oldCalculatedSourceMatches);
		
		Map<IMatch, Set<IMatch>> injectivityMap = findForbiddenMatches(constraints, oldTargetMatches, oldSourceMatches,parameter1, parameter2, 
				cartesianTargetProduct, cartesianSourceProduct, oldInjectivityConstraints);
		
		//find the updated matches if it is necessary
		if(!constraints.getAttributeConstraints().isEmpty()) {
			
			//find out which comparators are needed for the forbidden constraints
			List<SubmatchAttributeComparator> constraintComparators = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
					.filter(c -> cartesianSequence.getLeft().contains(c.getTargetPattern()) && !cartesianSequence.getLeft().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));
			
			//find the reverse comparators (source -> target) if there are any; necessary for cyclic dependencies
			List<SubmatchAttributeComparator> reverseComparator = new ArrayList<SubmatchAttributeComparator>(comparators.stream()
					.filter(c -> cartesianSequence.getRight().contains(c.getTargetPattern()) && !cartesianSequence.getRight().containsAll(c.getSourcePatterns())).collect(Collectors.toSet()));
			
			//if it is not present than it was already solved when calculating the attribute constraints
			if(!constraintComparators.isEmpty() || !reverseComparator.isEmpty()) {
				
				//find the attribute constraint value
				IBeXDependentDisjunctAttribute dependentAttribute = null;
				for(IBeXDependentDisjunctAttribute attr: constraints.getAttributeConstraints()) {
					for(IBeXContextPattern subpattern: attr.getDependentPatterns()) {
						if(cartesianSequence.getLeft().contains(subpattern)) {
							dependentAttribute = attr;
							break;
						}
					}
					if(dependentAttribute != null) break;
				}

				//changed target matches
				Set<IMatch> changedTargetMatches = DisjunctPatternHelper.createChangedCartesianProducts(pattern, cartesianSequence.getLeft(), changedNodes, oldMatches, submatchesMap, newCalculatedTargetMatches, 
						cartesianTargetComparators);

				//add the changed matches to the new matches and to the changed match set
				Set<IMatch> changedSourceMatches = DisjunctPatternHelper.createChangedCartesianProducts(pattern, cartesianSequence.getRight(), changedNodes, oldMatches, submatchesMap, newCalculatedSourceMatches, 
						cartesianSourceComparators);

				return DisjunctAttributeCalculationHelper.calculateForbiddenConstraintWithInjectivityMatches(constraintComparators, reverseComparator, oldCalculatedTargetMatches, 
						changedTargetMatches, newCalculatedTargetMatches, targetMap.get(dependentAttribute), oldCalculatedSourceMatches, changedSourceMatches, newCalculatedSourceMatches, 
						sourceMap.get(dependentAttribute), injectivityMap, sourceNeededSets);				
				}
		}

		if(cartesianSequence.getRight().size() > 1 || cartesianSequence.getLeft().size() > 1) {
			//update the cartesian product
			oldCartesianProducts.get(constraints).setLeft(cartesianTargetProduct);
			oldCartesianProducts.get(constraints).setRight(cartesianSourceProduct);
		}	
		
		long matchsum = cartesianTargetProduct.size()*cartesianSourceProduct.size();
		
		return matchsum - injectivityMap.values().parallelStream().mapToLong(constraint -> constraint.size()).sum();					
	}
	
	/**
	 * find all Objects in the match that break the injectivity constraints
	 * @param constraint the constraints between the two submatches
	 * @param oldMatch1 the first old match
	 * @param oldMatch2 the second old match
	 * @param match1 the first match
	 * @param match2 the second match
	 * @return the sum of forbidden matches
	 */
	public static final Map<IMatch, Set<IMatch>> findForbiddenMatches(final IBeXDependentInjectivityConstraints constraint, final Set<IMatch> oldMatch1, final Set<IMatch> oldMatch2,
			final List<String> parameter1, final List<String> parameter2, final Set<IMatch> match1, final Set<IMatch> match2, 
			Map<IBeXDependentInjectivityConstraints, Pair<Map<IMatch, Set<IMatch>>, Map<IMatch, Set<IMatch>>>> oldInjectivityConstraints ){
		

		//find out which matches where added		
		Set<IMatch> newMatches1 = match1.parallelStream().filter(match -> !oldMatch1.contains(match)).collect(Collectors.toSet());
		Set<IMatch> newMatches2 = match2.parallelStream().filter(match -> !oldMatch2.contains(match)).collect(Collectors.toSet());
		
		//find out which matches where removed	
		Set<IMatch> oldMatches1 = oldMatch1.parallelStream().filter(match -> !match1.contains(match)).collect(Collectors.toSet());
		Set<IMatch> oldMatches2 = oldMatch2.parallelStream().filter(match -> !match2.contains(match)).collect(Collectors.toSet());
		

		Pair<Map<IMatch, Set<IMatch>>, Map<IMatch, Set<IMatch>>> constraintMap = oldInjectivityConstraints.get(constraint);
		Map<IMatch, Set<IMatch>> constraintMap1 = constraintMap.getLeft();
		Map<IMatch, Set<IMatch>> constraintMap2 = constraintMap.getRight();
	
		//remove the old matches and their injectivity constraint
		for(IMatch oldMatch: oldMatches1) {
			for(IMatch otherMatches: constraintMap1.remove(oldMatch)) {
				constraintMap2.get(otherMatches).remove(oldMatch);
			}
		}
		
		for(IMatch oldMatch: oldMatches2) {
			for(IMatch otherMatches: constraintMap2.remove(oldMatch)) {
				constraintMap1.get(otherMatches).remove(oldMatch);
			}
		}
		
		//calculate the first map
		Set<Object> intersection1 = getIntersection(getObjectSet(newMatches1, parameter1), getObjectSet(constraintMap2.keySet(), parameter2));
		updateInjectivityMap(constraintMap2, constraintMap1, newMatches1, parameter2, parameter1, intersection1);
		
		//calculate the second map
		Set<Object> intersection2 = getIntersection(getObjectSet(newMatches2, parameter2), getObjectSet(constraintMap1.keySet(), parameter1));
		updateInjectivityMap(constraintMap1, constraintMap2, newMatches2, parameter1, parameter2, intersection2);

		return constraintMap1;		
	}
	/**
	 * calculates the set of objects that could break the injectivity constraints
	 */
	private static final Set<Object> getObjectSet(final Set<IMatch> matches, final List<String> parameters){
		return matches.parallelStream()
				.flatMap( match -> parameters.stream().map(parameter -> match.get(parameter)))
				.collect(Collectors.toSet());
	}
	
	/**
	 * calculates the intersection of two sets
	 */
	private static final Set<Object> getIntersection(final Set<Object> set1, final Set<Object> set2){
		if(set1.size()<set2.size()) {
			return set1.parallelStream().filter(object -> set2.contains(object)).collect(Collectors.toSet());
		}else {
			return set2.parallelStream().filter(object -> set1.contains(object)).collect(Collectors.toSet());
		}
	}
	
	/**
	 * update the injectivityConstraintMap
	 */
	private static void updateInjectivityMap(final Map<IMatch, Set<IMatch>> injectivityMap, final Map<IMatch,Set<IMatch>> matchInjectivityMap, final Set<IMatch> matches, 
			final List<String> mapParameters, final List<String> matchParameters, Set<Object> conflictSet) {
		
		for(Object object: conflictSet) {
			//matches of the first set that break injectivity constraints
			Set<IMatch> conflictMatches1 = injectivityMap.keySet().parallelStream()
					.filter(match ->DisjunctPatternHelper.hasObject(match, mapParameters, object)).collect(Collectors.toSet());
			
			Set<IMatch> conflictMatches2 = matches.parallelStream()
					.filter(match ->DisjunctPatternHelper.hasObject(match, matchParameters, object)).collect(Collectors.toSet());
			
			// the injectivity maps will be updated
			for(IMatch match: conflictMatches1) {
				injectivityMap.compute(match, (k, v) -> {
					if(v == null) {
						v = new HashSet<IMatch>();					
					}
					v.addAll(conflictMatches2);
					return v;
				});
			}
			
			for(IMatch match: conflictMatches2) {
				matchInjectivityMap.compute(match, (k, v) -> {
					if(v == null) {
						v = new HashSet<IMatch>();					
					}
					v.addAll(conflictMatches1);
					return v;
				});
			}	
		}
		//all new matches that do not break any injectivity constraint also need to be updated into the constraint map
		matches.stream().filter(match -> !matchInjectivityMap.containsKey(match))
		.forEach(match -> matchInjectivityMap.put(match, new HashSet<IMatch>()));
	}
	
	/**
	 * finds out which patterns should be used for the cartesian product calculation
	 * @param constraint the constraint
	 * @return the list of the subpatterns that should be used for the cartesian product calculation
	 */
	public static final Pair<List<IBeXContextPattern>, List<IBeXContextPattern>> findPatternFrequency(final IBeXDependentInjectivityConstraints constraint, Map<IBeXContextPattern, Set<IMatch>> submatchesMap){
		
		Map<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> patternFrequency = new HashMap<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>();
		//searches for the patterns with attribute constraints
		if(!constraint.getAttributeConstraints().isEmpty()) {
			for(IBeXDependentDisjunctAttribute subattribute: constraint.getAttributeConstraints()) {
				patternFrequency.putAll(DisjunctAttributeCalculationHelper.findAttributeConstraintFrequency(subattribute));
			}
			if(patternFrequency.isEmpty()) throw new IllegalArgumentException("the subpatterns can not be divided into two "
					+ "subpatterns that can be calculated without the cartesian product; please make the rule @notDisjoint");
		} 
		//search for the pattern with the least dependencies
		Optional<Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>> newmin = patternFrequency.entrySet().stream()
        .min(Comparator.comparingInt(Map.Entry::getValue));		
		patternFrequency.entrySet().removeIf(entry -> !entry.getValue().equals(newmin.get().getValue()));
		
		//if there is only one -> return the two pattern sequences
		if(patternFrequency.size() == 1) {
			return patternFrequency.entrySet().stream().findAny().get().getKey();
		}
		else {
			if(patternFrequency.isEmpty()) {
				List<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>> patternCombinations = DisjunctPatternHelper
						.calculatePatternCombinations(constraint.getPatterns(), new HashSet<List<IBeXContextPattern>>());
				patternCombinations.forEach(c -> patternFrequency.put(c, Integer.valueOf(0)));
			}
			//search for the pattern with the least injectivity dependencies
			for(IBexDisjunctInjectivityConstraint subconstraint: constraint.getInjectivityConstraints()) {
				for(Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> entry: patternFrequency.entrySet()) {
					boolean isPartial = entry.getKey().getLeft().contains(subconstraint.getPattern1()) && !entry.getKey().getLeft().contains(subconstraint.getPattern2()) ||
							entry.getKey().getLeft().contains(subconstraint.getPattern2()) && !entry.getKey().getLeft().contains(subconstraint.getPattern1());
					if(isPartial) {
						entry.setValue(Integer.valueOf(entry.getValue().intValue()+1));
					}
				}
			}	
		}
		
		Optional<Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>> newInjectivityMin = patternFrequency.entrySet().stream()
        .min(Comparator.comparingInt(Map.Entry::getValue));
		patternFrequency.entrySet().removeIf(entry -> !entry.getValue().equals(newInjectivityMin.get().getValue()));
		
		if(patternFrequency.size() == 1) {
			return patternFrequency.entrySet().stream().findAny().get().getKey();
		}
		
		//if nothing else -> sort by size of the matches
		Map<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> sizeMap = new HashMap<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>();
		
		for(Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer> entry: patternFrequency.entrySet()) {
			int sizeValue = entry.getKey().getLeft().stream().mapToInt(pattern -> submatchesMap.get(pattern).size()).reduce(1, (a, b) -> a*b);
			sizeValue += entry.getKey().getRight().stream().mapToInt(pattern -> submatchesMap.get(pattern).size()).reduce(1, (a, b) -> a*b);
			sizeMap.put(entry.getKey(), Integer.valueOf(sizeValue));
		}
		
		Optional<Entry<Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>, Integer>> newSizeMin = sizeMap.entrySet().stream()
		        .min(Comparator.comparingInt(Map.Entry::getValue));
		return newSizeMin.get().getKey();	
	}
}
