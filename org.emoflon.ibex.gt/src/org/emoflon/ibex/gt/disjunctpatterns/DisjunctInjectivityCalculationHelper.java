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
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute;
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
			final List<IBeXContextPattern> cartesianSequence, final Map<IBeXDependentInjectivityConstraints, Set<IMatch>> oldCartesianProducts, 
			final Map<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> attributes, 
			final Map<IBeXDependentInjectivityConstraints, Pair<Map<IMatch, Set<IMatch>>, Map<IMatch, Set<IMatch>>>> oldInjectivityConstraints,
			final Map<IBeXDependentDisjunctAttribute, Map<IMatch, Set<IMatch>>> targetMap, final Map<IBeXDependentDisjunctAttribute, Map<IMatch, Set<IMatch>>> sourceMap, 
			final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sourceNeededSets, Set<Object> changedNodes) {
		
		IBeXContextPattern targetPattern;
		
		Optional<IBeXContextPattern> possibleTargetPattern = constraints.getPatterns().stream().filter(subpattern -> !cartesianSequence.contains(subpattern)).findFirst();
		if(possibleTargetPattern.isEmpty() && constraints.getAttributeConstraints() != null) {
			for(IBeXDependentDisjunctAttribute attribute: constraints.getAttributeConstraints()) {
				possibleTargetPattern = attribute.getDependentPatterns().stream().filter(subpattern -> !cartesianSequence.contains(subpattern)).findFirst();
				if(possibleTargetPattern.isPresent()) break;			
			}
		}
		if(possibleTargetPattern.isEmpty()) throw new IllegalArgumentException("target pattern was not found");
		targetPattern = possibleTargetPattern.get();
		
		//find the new target matches 
		//the parameter names
		List<String> parameter1 = new ArrayList<String>(constraints.getInjectivityConstraints().stream()
				.filter(constraint -> !cartesianSequence.contains(constraint.getPattern1())||!cartesianSequence.contains(constraint.getPattern2()))
				.flatMap(constraint -> {
					if(!cartesianSequence.contains(constraint.getPattern1())) return constraint.getNode1().stream().map(node -> node.getName());
					else return constraint.getNode2().stream().map(node -> node.getName());
				}).collect(Collectors.toSet()));
		
		List<String> parameter2 = new ArrayList<String>(constraints.getInjectivityConstraints().stream()
				.filter(constraint -> {
					return cartesianSequence.contains(constraint.getPattern1()) && !cartesianSequence.contains(constraint.getPattern2()) 
							|| cartesianSequence.contains(constraint.getPattern2()) && !cartesianSequence.contains(constraint.getPattern1());
				}).flatMap(constraint -> {
					if(cartesianSequence.contains(constraint.getPattern1())) return constraint.getNode1().stream().map(node -> node.getName());
					else return constraint.getNode2().stream().map(node -> node.getName());
				}).collect(Collectors.toSet()));
		
		Set<IMatch> cartesianProduct = new HashSet<IMatch>();
		Set<IMatch> oldTargetMatch = oldMatches.get(targetPattern);
		Set<IMatch> newTargetMatches = submatchesMap.get(targetPattern).parallelStream().filter(match -> !oldTargetMatch.contains(match)).collect(Collectors.toSet());
		
		
		Map<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>> comparators = new HashMap<IBeXDisjunctAttribute, List<SubmatchAttributeComparator>>();
		//find the necessary submatch comparators
		if(constraints.getAttributeConstraints() != null) {
			for(IBeXDependentDisjunctAttribute attribute: constraints.getAttributeConstraints()) {			
			comparators.putAll(attributes.entrySet().stream().filter(entry -> attribute.getAttributes().contains(entry.getKey()))
					.collect(Collectors.toMap(c->c.getKey(), c->c.getValue().stream().filter(comparator -> !comparator.getTargetPatternName().equals(targetPattern.getName())).collect(Collectors.toList()))));
			}
		}
		Map<IBeXContextPattern, Set<IMatch>> newSubmatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		Map<IBeXContextPattern, Set<IMatch>>  oldSubmatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		//find the matches which were added and removed
		for(IBeXContextPattern subpattern: cartesianSequence) {
			Set<IMatch> newMatch = submatchesMap.get(subpattern);
			Set<IMatch> oldMatch = oldMatches.get(subpattern);
			newSubmatches.put(subpattern, newMatch.parallelStream().filter(match -> !oldMatch.contains(match)).collect(Collectors.toSet()));
			oldSubmatches.put(subpattern, oldMatch.parallelStream().filter(match -> !newMatch.contains(match)).collect(Collectors.toSet()));
		}
		Set<IMatch> oldCalculatedSourceMatches = new HashSet<IMatch>();
		Set<IMatch> newCalculatedSourceMatches = new HashSet<IMatch>();
		Set<IMatch> oldSourceMatches;
		if(cartesianSequence.size()>1) {
			//update the cartesian product
			if(!oldCartesianProducts.containsKey(constraints)) {
				oldCartesianProducts.put(constraints, new HashSet<IMatch>());
			}
			cartesianProduct = new HashSet<IMatch>(oldCartesianProducts.get(constraints));
			for(IBeXContextPattern subpattern: cartesianSequence) {
				newCalculatedSourceMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, cartesianSequence, subpattern, newSubmatches.get(subpattern), submatchesMap, comparators));
				oldCalculatedSourceMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, cartesianSequence, subpattern, oldSubmatches.get(subpattern), oldMatches, comparators));
			}
			oldSourceMatches = oldCartesianProducts.get(constraints);
			cartesianProduct.addAll(newCalculatedSourceMatches);
			cartesianProduct.removeAll(oldCalculatedSourceMatches);
		}
		else {
			cartesianProduct.addAll(submatchesMap.get(cartesianSequence.get(0)));
			newCalculatedSourceMatches.addAll(newSubmatches.get(cartesianSequence.get(0)));
			oldCalculatedSourceMatches.addAll(oldSubmatches.get(cartesianSequence.get(0)));
			oldSourceMatches = oldMatches.get(cartesianSequence.get(0));
		}
		
		Map<IMatch, Set<IMatch>> injectivityMap = findForbiddenMatches(constraints, oldMatches.get(targetPattern), oldSourceMatches,parameter1, parameter2, 
				submatchesMap.get(targetPattern), cartesianProduct, oldInjectivityConstraints);
		
		//find the updated matches if it is necessary
		if(!constraints.getAttributeConstraints().isEmpty()) {
			
			Optional<IBeXDisjunctAttribute> attribute = constraints.getAttributeConstraints().stream().flatMap(subattribute -> subattribute.getAttributes().stream())
					.filter(subattribute -> subattribute.getTargetPattern().equals(targetPattern)).findAny();	
			//if it is not present than it was already solved when calculating the attribute constraints
			if(attribute.isPresent()) {
				Optional<IBeXDependentDisjunctAttribute> dependentAttribute = constraints.getAttributeConstraints().stream()
						.filter(subattribute -> subattribute.getAttributes().contains(attribute.get())).findFirst();
				Set<IMatch> currentTargetMatch = submatchesMap.get(targetPattern);
				Set<IMatch> exclusiveOldTargetMatch = oldTargetMatch.parallelStream().filter(match -> !currentTargetMatch.contains(match)).collect(Collectors.toSet());
				//changed target matches
				Set<IMatch> changedTargetMatches = currentTargetMatch.parallelStream().filter(match -> {
					if(!oldTargetMatch.contains(match)) return false;
					for(String parameter: match.getParameterNames()) {
						if(changedNodes.contains(match.get(parameter))) {
							return true;
						}
					}
					return false;
				}).collect(Collectors.toSet());
				//find the changed matches
				Map<IBeXContextPattern, Set<IMatch>> changedSourceMatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
				for(IBeXContextPattern subpattern: cartesianSequence) {
					Set<IMatch> matches = oldMatches.get(subpattern);
					Set<IMatch> newMatches = submatchesMap.get(subpattern);
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
				//add the changed matches to the new matches and to the changed match set
				Set<IMatch> changedMatches = new HashSet<IMatch>();
				for(IBeXContextPattern subpattern: cartesianSequence) {
					newCalculatedSourceMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, cartesianSequence, subpattern, changedSourceMatches.get(subpattern), submatchesMap, comparators));
					changedMatches.addAll(DisjunctPatternHelper.createUpdatedCartesianProduct(pattern, cartesianSequence, subpattern, changedSourceMatches.get(subpattern), oldMatches, comparators));
				}
				//find out which comparators are needed for the forbidden constraints
				Set<SubmatchAttributeComparator> tempComparator = new HashSet<SubmatchAttributeComparator>();
				List<SubmatchAttributeComparator> constraintComparators = new ArrayList<SubmatchAttributeComparator>();
				for(SubmatchAttributeComparator comparator: attributes.get(attribute.get())) {
					if(comparator.getTargetPatternName().equals(targetPattern.getName())) {
						tempComparator.add(comparator);
					}					
				}		
				constraintComparators.addAll(tempComparator);
	
				//find the ibexdisjunctattribute with the target pattern as target
				long matchsum = submatchesMap.get(targetPattern).size()*cartesianProduct.size();
				
				long forbiddenMatches = DisjunctAttributeCalculationHelper.calculateForbiddenConstraintWithInjectivityMatches(pattern, attribute.get(), constraintComparators, exclusiveOldTargetMatch, 
						changedTargetMatches, newTargetMatches, targetMap.get(dependentAttribute.get()), oldCalculatedSourceMatches, changedMatches, newCalculatedSourceMatches, 
						sourceMap.get(dependentAttribute.get()), injectivityMap, sourceNeededSets);				
				return matchsum - forbiddenMatches;
				}
		}

		if(cartesianSequence.size() > 1) {
			//update the cartesian product
			oldCartesianProducts.put(constraints, cartesianProduct);			
		}	
		
		long matchsum = submatchesMap.get(targetPattern).size()*cartesianProduct.size();
		
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
	public static final List<IBeXContextPattern> findPatternFrequency(final IBeXDependentInjectivityConstraints constraint, Map<IBeXContextPattern, Set<IMatch>> submatchesMap){
		
		Map<IBeXContextPattern, Integer> patternFrequency = new HashMap<IBeXContextPattern, Integer>();
		Set<IBeXContextPattern> cartesianPatterns = new HashSet<IBeXContextPattern>();
		
		//searches for the patterns with attribute constraints
		if(constraint.getAttributeConstraints() != null) {
			for(IBeXDependentDisjunctAttribute subattribute: constraint.getAttributeConstraints()) {
				Map<IBeXContextPattern, Integer> map = DisjunctAttributeCalculationHelper.findAttributeConstraintFrequency(subattribute);
				for(Entry<IBeXContextPattern, Integer> entry: map.entrySet()) {
					patternFrequency.compute(entry.getKey(), (k, v)-> (v==null)? entry.getValue() : entry.getValue() + v);
				}
			}
		} 
		
		Map<IBeXContextPattern, Integer> otherPatternFrequency = new HashMap<IBeXContextPattern, Integer>();
		//search for the pattern with the least dependencies
		Optional<Entry<IBeXContextPattern, Integer>> min = patternFrequency.entrySet().stream()
        .min(Comparator.comparingInt(Map.Entry::getValue));
		if(min.isPresent()) {
			for(Entry<IBeXContextPattern, Integer> entry: patternFrequency.entrySet()) {
				//remove all patterns that are not of interest
				if(entry.getValue()==min.get().getValue()) {
					otherPatternFrequency.put(entry.getKey(), entry.getValue());
				}
			}
		}
		//if there is only one -> return all others as cartesian products
		if(otherPatternFrequency.size() == 1) {
			IBeXContextPattern pattern = otherPatternFrequency.entrySet().iterator().next().getKey();
			cartesianPatterns.addAll(constraint.getPatterns().stream().filter(otherpattern -> !otherpattern.equals(pattern)).collect(Collectors.toList()));
			if(constraint.getAttributeConstraints() != null) {
				for(IBeXDependentDisjunctAttribute attributes: constraint.getAttributeConstraints()) {
					cartesianPatterns.addAll(attributes.getDependentPatterns().stream().filter(otherpattern -> !otherpattern.equals(pattern)).collect(Collectors.toList()));
				}
			}
			return new ArrayList<IBeXContextPattern>(cartesianPatterns);
		}
		//search for the pattern with the least injectivity dependencies
		for(IBeXContextPattern pattern: constraint.getPatterns()) {
			if(!otherPatternFrequency.isEmpty()) {
				for(IBexDisjunctInjectivityConstraint subconstraint: constraint.getInjectivityConstraints()) {
					if(otherPatternFrequency.containsKey(pattern) && (subconstraint.getPattern1().equals(pattern) || subconstraint.getPattern2().equals(pattern))) {
						otherPatternFrequency.compute(pattern, (k, v) -> (v==null)? 1: Integer.valueOf(v+1));
					}
				}				
			}
			else {
				for(IBexDisjunctInjectivityConstraint subconstraint: constraint.getInjectivityConstraints()) {
					if((subconstraint.getPattern1().equals(pattern) || subconstraint.getPattern2().equals(pattern))) {
						otherPatternFrequency.compute(pattern, (k, v) -> (v==null)? 1: Integer.valueOf(v+1));
					}
				}	
			}
		}

		//search for the pattern with the least dependencies
		Optional<Entry<IBeXContextPattern, Integer>> newmin = otherPatternFrequency.entrySet().stream()
        .min(Comparator.comparingInt(Map.Entry::getValue));
	
		Map<IBeXContextPattern, Integer> lastPatternFrequency = new HashMap<IBeXContextPattern, Integer>();
		if(newmin.isPresent()) {
			for(Entry<IBeXContextPattern, Integer> entry: otherPatternFrequency.entrySet()) {
				//remove all patterns that are not of interest
				if(entry.getValue()==newmin.get().getValue()) {
					lastPatternFrequency.put(entry.getKey(), entry.getValue());
				}
			}
		}
		if(lastPatternFrequency.size() == 1) {
			IBeXContextPattern pattern = otherPatternFrequency.entrySet().iterator().next().getKey();
			cartesianPatterns.addAll(constraint.getPatterns().stream().filter(otherpattern -> !otherpattern.equals(pattern)).collect(Collectors.toList()));
			if(constraint.getAttributeConstraints() != null) {
				for(IBeXDependentDisjunctAttribute attributes: constraint.getAttributeConstraints()) {
					cartesianPatterns.addAll(attributes.getDependentPatterns().stream().filter(otherpattern -> !otherpattern.equals(pattern)).collect(Collectors.toList()));
				}
			}
			return new ArrayList<IBeXContextPattern>(cartesianPatterns);
		}
		//if nothing else -> sort by size of the matches
		Optional<Entry<IBeXContextPattern, Set<IMatch>>> endValues = submatchesMap.entrySet().stream().filter(pattern -> lastPatternFrequency.containsKey(pattern.getKey()))
				.min(((e1, e2) -> e1.getValue().size()- e2.getValue().size()));
		
		if(endValues.isPresent()) {
			IBeXContextPattern pattern = endValues.get().getKey();
			cartesianPatterns.addAll(constraint.getPatterns().stream().filter(otherpattern -> !otherpattern.equals(pattern)).collect(Collectors.toList()));
			if(constraint.getAttributeConstraints() != null) {
				for(IBeXDependentDisjunctAttribute attributes: constraint.getAttributeConstraints()) {
					cartesianPatterns.addAll(attributes.getDependentPatterns().stream().filter(otherpattern -> !otherpattern.equals(pattern)).collect(Collectors.toList()));
				}
			}
			return new ArrayList<IBeXContextPattern>(cartesianPatterns);
		}
		else {
			throw new IllegalArgumentException("pattern for cartesian product could not be found");
		}
			
	}
}
