package org.emoflon.ibex.gt.disjunctpatterns;

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
import java.util.stream.Stream;


import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.gt.transformations.Pair;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;


/**
 * The GraphTransformationDisjunctPatternInterpreter calculates matches of all disjunct patterns
 */
public class GraphTransformationDisjunctPatternInterpreter {
	
	/**
	 * the old matches
	 */
	private Map<IBeXContextPattern, Set<IMatch>> oldMatches;
	
	/**
	 * the old injectivity constraints
	 */
	private Map<IBeXDependentInjectivityConstraints, Pair<Map<IMatch, Set<IMatch>>, Map<IMatch, Set<IMatch>>>> oldInjectivityConstraints;
	
	/**
	 * the old calculated cartesian products for the injectivity constraints
	 */
	private Map<IBeXDependentInjectivityConstraints, Pair<Set<IMatch>, Set<IMatch>>> oldCartesianProducts;
	
	/**
	 * the old attribute constraints
	 */
	private Map<IBeXDependentDisjunctAttribute, Map<IMatch, Set<IMatch>>> targetMatchSets;
	
	/**
	 * the comparators for the attribute constraints
	 */
	private List<SubmatchAttributeComparator> attributeComparators;
	
	/**
	 * the sorted source matches for the attribute constraints
	 */
	private Map<IBeXDependentDisjunctAttribute, Map<IMatch, Set<IMatch>>> sourceMatchSets;
	
	/**
	 * the source and target sets for the attribute constraints sorted by the respective attribute; left = target; right = source
	 */
	private Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedSets;
	
	/**
	 * the pattern sequence for the calculation of the cartesian products
	 */
	private Map<IBeXDependentInjectivityConstraints, Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>> injectivityPatternSequence;
	private Map<IBeXDependentDisjunctAttribute, Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>> attributePatternSequence;
	
	/**
	 * the notification adapter
	 */
	IBeXDisjunctContentAdapter adapter; 
	
	public GraphTransformationDisjunctPatternInterpreter(IBeXDisjunctContextPattern contextPattern, ResourceSet rsSet) {
		
		oldMatches = new HashMap<IBeXContextPattern, Set<IMatch>>();
		adapter = new IBeXDisjunctContentAdapter(rsSet);
		
		for(IBeXContextPattern subpattern: contextPattern.getSubpatterns()) {
			oldMatches.put(subpattern, new HashSet<IMatch>());
		}		
	
		oldInjectivityConstraints = new HashMap<IBeXDependentInjectivityConstraints, Pair<Map<IMatch, Set<IMatch>>, Map<IMatch, Set<IMatch>>>>();
		targetMatchSets = new HashMap<IBeXDependentDisjunctAttribute, Map<IMatch,Set<IMatch>>>();
		attributeComparators = new ArrayList<SubmatchAttributeComparator>();
		sourceMatchSets = new HashMap<IBeXDependentDisjunctAttribute, Map<IMatch,Set<IMatch>>>();
		sortedSets = new HashMap<SubmatchAttributeComparator, Pair<TreeSet<IMatch>,TreeSet<IMatch>>>();
		injectivityPatternSequence = new HashMap<IBeXDependentInjectivityConstraints,Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>>();
		attributePatternSequence = new HashMap<IBeXDependentDisjunctAttribute, Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>>();
		oldCartesianProducts = new HashMap<IBeXDependentInjectivityConstraints, Pair<Set<IMatch>, Set<IMatch>>>();
		
		for(IBeXDependentInjectivityConstraints subpatternConstraint: contextPattern.getInjectivityConstraints()) {
			oldInjectivityConstraints.put(subpatternConstraint, 
				new Pair<Map<IMatch, Set<IMatch>>, Map<IMatch, Set<IMatch>>>(new HashMap<IMatch, Set<IMatch>>(), new HashMap<IMatch, Set<IMatch>>()));
			oldCartesianProducts.put(subpatternConstraint, new Pair<Set<IMatch>, Set<IMatch>>(new HashSet<IMatch>(), new HashSet<IMatch>()));
		}
		for(IBeXDependentDisjunctAttribute attribute: contextPattern.getAttributesConstraints()) {
			List<SubmatchAttributeComparator> comparators = new ArrayList<SubmatchAttributeComparator>();			
			for(IBeXDisjunctAttribute subattribute: attribute.getAttributes()) {
				for(IBeXAttributeConstraint constraint: subattribute.getDisjunctAttribute()) {
					SubmatchAttributeComparator comparator = new SubmatchAttributeComparator(constraint,
							subattribute.getSourcePattern(), subattribute.getTargetPattern());
					comparators.add(comparator);
					sortedSets.put(comparator, new Pair<TreeSet<IMatch>, TreeSet<IMatch>>(new TreeSet<IMatch>(comparator),new TreeSet<IMatch>(comparator)));
				}	
				attributeComparators.addAll(comparators);				
			}
			targetMatchSets.put(attribute, new HashMap<IMatch, Set<IMatch>>());
			sourceMatchSets.put(attribute, new HashMap<IMatch,Set<IMatch>>());
		}
	}

	/**
	 * finds any match of the pattern
	 * 
	 * @param pattern the pattern
	 * @param submatches the submatches
	 * @return a random match of the pattern
	 */
	public final Optional<IMatch> findAnyMatch(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap){		

		if(calculateMatchCount(pattern, submatchesMap)>0) {
			
			//sorts from the smallest submatch to the biggest
			List<Set<IMatch>> submatches = new ArrayList<Set<IMatch>>(submatchesMap.values());
			submatches.sort(new SubmatchSizeComparator());
			//a map of the attribute constraints
			//recursively generate a new match
			return calculateAnyMatch(pattern, submatches, pattern.getName(), new SimpleMatch(pattern.getName()), attributeComparators);
		}
		else {
			return Optional.empty();
		}
	}
	
	/**
	 * recursive function that finds a random match in the pattern
	 */
	private final Optional<IMatch> calculateAnyMatch(final IBeXDisjunctContextPattern pattern, final List<Set<IMatch>> submatches, 
			final String patternName, final IMatch match, final List<SubmatchAttributeComparator> attributes){		
		if(!submatches.isEmpty()) {
			IMatch submatch;
			//the merged submatch
			IMatch newMatch = new SimpleMatch(patternName);
			
			boolean merged = false;
			
			Collection<IMatch> matches = submatches.get(0);
			Iterator<IMatch> iterator = matches.iterator();
	
				while(iterator.hasNext()) {
					//search for any submatch of the submatches til it finds one that is disjunct with the current match
					submatch = iterator.next();
					
					if(DisjunctPatternHelper.isDisjunct(match, submatch) && DisjunctPatternHelper.checkAttributeConstraint(match, submatch, attributes)) {
						//if there is a subpattern left -> create recursively a match with the other subpatterns and see if they are not disjunct
						if(submatches.size()>1) {
							Optional<IMatch> calculatedMatch = calculateAnyMatch(pattern, submatches.subList(1, submatches.size()), patternName, submatch, attributes);
							if(calculatedMatch.isPresent()) {
								if(DisjunctPatternHelper.isDisjunct(match, calculatedMatch.get()) 
										&& DisjunctPatternHelper.checkAttributeConstraint(match, calculatedMatch.get(), attributes)) {
									newMatch = DisjunctPatternHelper.merge(match, calculatedMatch.get(), patternName);
									merged = true;
									break;
								}				
							}						
						}
						else {
							newMatch = DisjunctPatternHelper.merge(match, submatch, patternName);
							merged = true;
							break;
						}
					}
				}
				if(!merged) {
					return Optional.empty();
				}
				return Optional.of(newMatch);				
			}
			
		return Optional.empty();
	}
	/**
	 * calculates all matches of a pattern
	 * 
	 * @param pattern the pattern
	 * @param submatches a list with matches for all subpatterns
	 * @return the number of matches
	 */
	public long calculateMatchCount(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap) {
		//matchCount is one for multiplication purposes; the submatch list size should be bigger than one anyway
		int matchCount = 1;
		
		//no submatch can be empty
		if(submatchesMap.values().stream().anyMatch(matches -> matches.isEmpty())) return 0;
		
		//name all subpatterns that have injectivity constraints
		List<IBeXContextPattern> constraintPatterns = pattern.getInjectivityConstraints().stream()
				.flatMap(constraint -> constraint.getPatterns().stream()).collect(Collectors.toList());
		
		//name all subpatterns that have attribute constraints
		List<IBeXContextPattern> attributePatterns = pattern.getAttributesConstraints().stream()
				.flatMap(constraint -> constraint.getDependentPatterns().stream()).collect(Collectors.toList());		
		
		//find all subpatterns that do not have any injectivity constraints or attribute constraints with other patterns
		List<Long> submatchCount = submatchesMap.entrySet().stream()
				.filter(subpattern -> !constraintPatterns.contains(subpattern.getKey())&& !attributePatterns.contains(subpattern.getKey()))
				.map(subpattern -> (long) subpattern.getValue().size()).collect(Collectors.toList());

		//find the match count of all subpatterns that have injectivity constraints
		for(IBeXDependentInjectivityConstraints constraint: pattern.getInjectivityConstraints()) { 
			if(!injectivityPatternSequence.containsKey(constraint)) {
				injectivityPatternSequence.put(constraint, DisjunctInjectivityCalculationHelper.findPatternFrequency(constraint, submatchesMap));
			}
			submatchCount.add(DisjunctInjectivityCalculationHelper
					.calculateSubmatchCount(pattern, constraint, submatchesMap, oldMatches, injectivityPatternSequence.get(constraint),
					oldCartesianProducts, attributeComparators, oldInjectivityConstraints, targetMatchSets, sourceMatchSets, sortedSets, adapter.getChangedObjects()));
		}

		
		//find the match count of all subpatterns that have attribute constraints
		List<IBeXDependentDisjunctAttribute> attributes = pattern.getAttributesConstraints().stream().filter(constraint -> {
			for(IBeXContextPattern subpattern: constraint.getDependentPatterns()) {
				if(constraintPatterns.contains(subpattern)) return false;
			}
			return true;
		}).collect(Collectors.toList());
		
		for(IBeXDependentDisjunctAttribute constraint: attributes) {
			if(!attributePatternSequence.containsKey(constraint)) {
				attributePatternSequence.put(constraint, DisjunctAttributeCalculationHelper.findPatternFrequency(constraint, submatchesMap));
			}
			submatchCount.add(DisjunctAttributeCalculationHelper.calculateForbiddenConstraintMatches(pattern, constraint, submatchesMap, 
					attributeComparators, oldMatches, targetMatchSets.get(constraint), sourceMatchSets.get(constraint), 
					adapter.getChangedObjects(), sortedSets, attributePatternSequence.get(constraint)));
		}
		
		//calculate the match count
		for(Long submatches: submatchCount) {
			matchCount *= submatches;
		}

		//update the matches
		for(Entry<IBeXContextPattern, Set<IMatch>> submatches: submatchesMap.entrySet()) {
			oldMatches.put(submatches.getKey(), submatches.getValue());
		}
		
		adapter.cleanUpdatedSets();
		return matchCount;
	}
	/**
	 * calculates all matches of a disjunct pattern
	 * @param submatches the submatches of a pattern
	 * @return the matches
	 */
	public final Stream<IMatch> matchStream(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap){
		return joinDisjunctSubMatches(pattern, new ArrayList<Set<IMatch>>(submatchesMap.values()), pattern.getName()).stream();
	}
	
	/**
	 * recursively joins all submatches
	 * 
	 * @param submatches the submatches
	 * @return the all computed matches
	 */
	private final Set<IMatch> joinDisjunctSubMatches(final IBeXDisjunctContextPattern pattern, final List<Set<IMatch>> submatches, final String name){
		if(submatches.size() == 0) {
			throw new IllegalArgumentException("the list of subpatterns should not be empty");
		}
		if(submatches.size() == 1) {
			return submatches.get(0);
		}
		else if(submatches.size() == 2) {
			//calculate the cartesian product of the submatches
			return DisjunctPatternHelper.cartesianProduct(submatches.get(0), submatches.get(1), name, attributeComparators);
		}
		else {
			//divide the list into two sublists and then merge them
			return DisjunctPatternHelper.cartesianProduct(joinDisjunctSubMatches(pattern, submatches.subList(0, submatches.size()/2),name), 
					joinDisjunctSubMatches(pattern, submatches.subList(submatches.size()/2, submatches.size()), name), name, attributeComparators);
		}
	}
	
	/**
	 * calculates all the matches that contain the specific submatch

	 */
	public final Set<IMatch> createMatchesWithThisSubmatch(final IBeXDisjunctContextPattern pattern, final IMatch match, final Map<IBeXContextPattern, Set<IMatch>> submatchesMap, 
			final String name){
		Set<IMatch> cartesianProduct = new HashSet<IMatch>();
		cartesianProduct.add(match);
		for(Set<IMatch> matches: submatchesMap.values()) {
			if(!matches.isEmpty()) {
				if(!matches.iterator().next().getPatternName().equals(match.getPatternName())) {
					cartesianProduct = DisjunctPatternHelper.cartesianProduct(cartesianProduct, matches, name, attributeComparators);
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
	 * Helper class for sorting subpatterns by size
	 *
	 */
	private static class SubmatchSizeComparator implements Comparator<Collection<IMatch>>{
		@Override
		public int compare(Collection<IMatch> o1, Collection<IMatch> o2) {
			return o1.size()-o2.size();
		}		
	}
}
