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
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.gt.transformations.Pair;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern;


/**
 * The GraphTransformationDisjunctPatternInterpreter calculates matches of all disjunct patterns
 */
public class GraphTransformationDisjunctPatternInterpreter {
	
	/**
	 * the old matches
	 */
	private final Map<IBeXContextPattern, Collection<IMatch>> oldMatches;
	
	/**
	 * the old injectivity constraints
	 */
	private final Map<IBeXDependentInjectivityConstraints, Pair<Map<IMatch, Collection<IMatch>>, Map<IMatch, Collection<IMatch>>>> oldInjectivityConstraints;
	
	/**
	 * the old calculated cartesian products for the injectivity constraints
	 */
	private final Map<IBeXDependentInjectivityConstraints, Pair<Collection<IMatch>, Collection<IMatch>>> oldCartesianProducts;
	
	/**
	 * the old attribute constraints
	 */
	private final Map<IBeXDependentDisjunctAttribute, Map<IMatch, Collection<IMatch>>> targetMatchSets;
	
	/**
	 * the comparators for the attribute constraints
	 */
	private final List<SubmatchAttributeComparator> attributeComparators;
	
	/**
	 * the sorted source matches for the attribute constraints
	 */
	private final Map<IBeXDependentDisjunctAttribute, Map<IMatch, Collection<IMatch>>> sourceMatchSets;
	
	/**
	 * the source and target sets for the attribute constraints sorted by the respective attribute; left = target; right = source
	 */
	private final Map<SubmatchAttributeComparator, Pair<TreeSet<IMatch>, TreeSet<IMatch>>> sortedSets;
	
	/**
	 * the pattern sequence for the calculation of the cartesian products
	 */
	private final Map<IBeXDependentInjectivityConstraints, Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>> injectivityPatternSequence;
	private final Map<IBeXDependentDisjunctAttribute, Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>> attributePatternSequence;
	
	/**
	 * the notification adapter
	 */
	IBeXDisjunctContentAdapter adapter; 
	
	public GraphTransformationDisjunctPatternInterpreter(final GraphTransformationInterpreter interpreter, final IBeXDisjunctContextPattern contextPattern, final ResourceSet rsSet) {
		
		oldMatches = new HashMap<IBeXContextPattern, Collection<IMatch>>();
		adapter = new IBeXDisjunctContentAdapter(rsSet);
		
		for(IBeXContextPattern subpattern: contextPattern.getSubpatterns()) {
			oldMatches.put(subpattern, new HashSet<IMatch>());
		}		
	
		oldInjectivityConstraints = new HashMap<IBeXDependentInjectivityConstraints, Pair<Map<IMatch, Collection<IMatch>>, Map<IMatch, Collection<IMatch>>>>();
		targetMatchSets = new HashMap<IBeXDependentDisjunctAttribute, Map<IMatch,Collection<IMatch>>>();
		attributeComparators = new ArrayList<SubmatchAttributeComparator>();
		sourceMatchSets = new HashMap<IBeXDependentDisjunctAttribute, Map<IMatch,Collection<IMatch>>>();
		sortedSets = new HashMap<SubmatchAttributeComparator, Pair<TreeSet<IMatch>,TreeSet<IMatch>>>();
		injectivityPatternSequence = new HashMap<IBeXDependentInjectivityConstraints,Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>>();
		attributePatternSequence = new HashMap<IBeXDependentDisjunctAttribute, Pair<List<IBeXContextPattern>, List<IBeXContextPattern>>>();
		oldCartesianProducts = new HashMap<IBeXDependentInjectivityConstraints, Pair<Collection<IMatch>, Collection<IMatch>>>();
		
		for(IBeXDependentInjectivityConstraints subpatternConstraint: contextPattern.getInjectivityConstraints()) {
			oldInjectivityConstraints.put(subpatternConstraint, 
				new Pair<Map<IMatch, Collection<IMatch>>, Map<IMatch, Collection<IMatch>>>(new HashMap<IMatch, Collection<IMatch>>(), new HashMap<IMatch, Collection<IMatch>>()));
			oldCartesianProducts.put(subpatternConstraint, new Pair<Collection<IMatch>, Collection<IMatch>>(new HashSet<IMatch>(), new HashSet<IMatch>()));
		}
		for(IBeXDependentDisjunctAttribute attribute: contextPattern.getAttributesConstraints()) {		
			for(IBeXDisjunctAttribute subattribute: attribute.getAttributes()) {
				for(IBeXConstraint constraint: subattribute.getDisjunctAttribute()) {
					SubmatchAttributeComparator comparator = new SubmatchAttributeComparator(constraint,
							subattribute.getSourcePattern(), subattribute.getTargetPattern(), interpreter);
					sortedSets.put(comparator, new Pair<TreeSet<IMatch>, TreeSet<IMatch>>(new TreeSet<IMatch>(comparator),new TreeSet<IMatch>(comparator)));
					attributeComparators.add(comparator);						
				}	
			
			}
			targetMatchSets.put(attribute, new HashMap<IMatch, Collection<IMatch>>());
			sourceMatchSets.put(attribute, new HashMap<IMatch, Collection<IMatch>>());
		}
	}

	/**
	 * finds any match of the pattern
	 * 
	 * @param pattern the pattern
	 * @param submatches the submatches
	 * @return a random match of the pattern
	 */
	public final Optional<IMatch> findAnyMatch(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Collection<IMatch>> submatchesMap){		

		if(calculateMatchCount(pattern, submatchesMap)>0) {
			
			//sorts from the smallest submatch to the biggest
			List<Collection<IMatch>> submatches = new ArrayList<Collection<IMatch>>(submatchesMap.values());
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
	private final Optional<IMatch> calculateAnyMatch(final IBeXDisjunctContextPattern pattern, final List<Collection<IMatch>> submatches, 
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
	public long calculateMatchCount(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Collection<IMatch>> submatchesMap) {
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
		for(Entry<IBeXContextPattern, Collection<IMatch>> submatches: submatchesMap.entrySet()) {
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
	public final Stream<IMatch> matchStream(final IBeXDisjunctContextPattern pattern, final Map<IBeXContextPattern, Collection<IMatch>> submatchesMap){
		return joinDisjunctSubMatches(pattern, new ArrayList<Collection<IMatch>>(submatchesMap.values()), pattern.getName()).stream();
	}
	
	/**
	 * recursively joins all submatches
	 * 
	 * @param submatches the submatches
	 * @return the all computed matches
	 */
	private final Collection<IMatch> joinDisjunctSubMatches(final IBeXDisjunctContextPattern pattern, final List<Collection<IMatch>> submatches, final String name){
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
	public final Collection<IMatch> createMatchesWithThisSubmatch(final IBeXDisjunctContextPattern pattern, final IMatch match, final Map<String, Collection<IMatch>> submatchesMap, 
			final String name){
		Collection<IMatch> cartesianProduct = new HashSet<IMatch>();
		cartesianProduct.add(match);
		for(IBeXContextPattern subpattern: pattern.getSubpatterns()) {
			Collection<IMatch> matches = submatchesMap.get(subpattern.getName());
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
