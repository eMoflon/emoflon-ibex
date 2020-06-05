package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.AddExpression;
import org.emoflon.ibex.gt.editor.gT.ArithmeticCalculationExpression;
import org.emoflon.ibex.gt.editor.gT.ArithmeticExpression;
import org.emoflon.ibex.gt.editor.gT.ArithmeticNodeAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorApplicationCondition;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorParameter;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.gT.ExpExpression;
import org.emoflon.ibex.gt.editor.gT.GTFactory;
import org.emoflon.ibex.gt.editor.gT.MultExpression;
import org.emoflon.ibex.gt.editor.gT.OneParameterArithmetics;
import org.emoflon.ibex.gt.editor.utils.GTConditionHelper;
import org.emoflon.ibex.gt.editor.utils.GTDisjunctPatternFinder;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodePair;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.moflon.core.utilities.EcoreUtils;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelFactory;

/**
 * class which divides the disjunct subpatterns 
 */
public class EditorToIBeXDisjunctContextPatternTransformation extends EditorToIBeXPatternTransformation{
	
	EditorToIBeXPatternTransformation transformation;
	Map<Set<EditorNode>, EditorPattern> dividedPatterns;
	GTDisjunctPatternFinder patternfinder;
	List<Set<EditorNode>> subpatterns;
	List<IBeXAttributeConstraint> disjunctAttributeConditions;
	EditorPattern editorPattern;
	Map<Pair<String, String>, List<Pair<EditorNode, EditorNode>>> injectivityConstraints;
	
	
	public EditorToIBeXDisjunctContextPatternTransformation(final EditorToIBeXPatternTransformation transformation, 
			final GTDisjunctPatternFinder patternfinder, final EditorPattern editorPattern) {
		this.transformation = transformation;
		this.patternfinder = patternfinder;
		dividedPatterns = new HashMap<Set<EditorNode>, EditorPattern>();
		disjunctAttributeConditions = new ArrayList<IBeXAttributeConstraint>();
		subpatterns = patternfinder.getSubpatterns();
		this.editorPattern = editorPattern;
		injectivityConstraints = new HashMap<Pair<String,String>, List<Pair<EditorNode,EditorNode>>>();
	}
	
	/**
	 * divides the EditorPattern in subpatterns and transforms the subpatterns
	 * 
	 * @param editorPattern the pattern
	 * @param isLocalCheck
	 * @return the IBeXDisjunctContextPattern
	 * @throws IllegalArgumentException if something goes wrong when dividing the EditorPattern
	 */
	public IBeXContext transformToContextPattern(final Predicate<EditorNode> isLocalCheck) throws IllegalArgumentException {
		IBeXDisjunctContextPattern ibexPattern = IBeXDisjunctPatternModelFactory.eINSTANCE.createIBeXDisjunctContextPattern();
		ibexPattern.setName("DISJUNCT_" + editorPattern.getName());
		
		//divide the EditorPattern in sub-EditorPatterns; The transformation of the subpatterns is done by the main IBeXTransformation class so that the 
		//subpatterns themselves do not have errors
		divideContextPatterns(isLocalCheck);
		
		//find attributeConstraints between transformed disjunct subpatterns
		dividedPatterns.forEach((k, v) -> EditorToIBeXDisjunctPatternHelper.findDisjunctAttributeConstraints(k, v));
		
		//transform all subpatterns and their conditions
		ibexPattern.getSubpatterns().addAll(dividedPatterns.values().stream().map(pattern -> {
			IBeXContextPattern ibexSubpattern = transformation
					.transformToContextPattern(pattern, pattern.getName(), editorNode -> EditorModelUtils.isLocal(editorNode));
			if(pattern.getConditions().size() == 1) {
				new EditorToIBeXConditionHelper(transformation, ibexSubpattern).transformCondition(pattern.getConditions().get(0));
			}
			return ibexSubpattern;
		}).collect(Collectors.toList())); 
		
		ibexPattern.getInjectivityConstraints().addAll(EditorToIBeXDisjunctPatternHelper.transformInjectivityConstraints(ibexPattern.getSubpatterns(), injectivityConstraints));
		
//		ibexPattern.getDisjunctAttributes().addAll(disjunctAttributeConditions);
		return ibexPattern;
	}
	
	/**
	 * divides a disjunct editorPattern in different subpatterns; not supported until now: EditorPatternAttributeCondition(?)
	 */
	private void divideContextPatterns(final Predicate<EditorNode> isLocalCheck) {
		List<EditorNode> editorNodes = EditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE);	
			
		//divide the nodes in the pattern
		for(int i = 0; i< subpatterns.size(); i++) {
			//create new subpatterns that are later transformed into IBeXPattern; consistency of the pattern can be generated this way
			Set<EditorNode> currentSubpattern = subpatterns.get(i);
			EditorPattern newSubpattern = GTFactory.eINSTANCE.createEditorPattern();
			newSubpattern.setName(editorPattern.getName() + "_"+ i);
			List<EditorNode> subpatternNodes = currentSubpattern.stream().filter(n -> editorNodes.contains(n)).collect(Collectors.toList());
			newSubpattern.getNodes().addAll(subpatternNodes);
			newSubpattern.setType(editorPattern.getType());
			dividedPatterns.put(subpatterns.get(i), newSubpattern);
		}
		
		//find injectivity constraints in the subpatterns
		injectivityConstraints = EditorToIBeXDisjunctPatternHelper.findInjectivityConstraints(new ArrayList<EditorPattern>(dividedPatterns.values()), isLocalCheck);
		
		if(!editorPattern.getConditions().isEmpty()) {
			//divide the conditions and add them to the divided editorPatterns
			divideConditions(editorPattern.getConditions().get(0), editorNodes.stream().filter(isLocalCheck.negate()).collect(Collectors.toList()), editorPattern.getName())
			.forEach((k, v) -> {
				dividedPatterns.compute(k, (otherK, otherV) -> {
					if(otherV == null) {
						//there should be a disjunct editorPattern under this subpattern; if not then something went wrong and the pattern is dealt with in the original way
						throw new IllegalArgumentException("something went wrong when diving the subconditions");
					}
					else {
						otherV.getConditions().add(v);
						return otherV;						
					}
				});
			});	
		}		
	}



	/**
	 * divides the conditions in the corresponding subpatterns
	 * 
	 * @param condition the current condition that needs to be separated
	 * @param signatureNodes the 
	 * @return a map with the subpatterns and the corresponding EditorConditions
	 */
	private Map<Set<EditorNode>, EditorCondition> divideConditions(final EditorCondition condition, final List<EditorNode> signatureNodes, String patternName){
		Map<Set<EditorNode>, EditorCondition> conditionMap = new LinkedHashMap<Set<EditorNode>, EditorCondition>();
		int conditionNr = 0;
		
		for(EditorApplicationCondition simpleCondition: new GTConditionHelper(condition).getApplicationConditions()) {
			
			Map<Set<EditorNode>, EditorApplicationCondition> simpleConditionSubpatterns = computeConditionSubmap(simpleCondition, signatureNodes);
			List<EditorPattern> conditionPatterns = simpleConditionSubpatterns.values().stream().map(c -> c.getPattern()).collect(Collectors.toList());
			//find out if the different subpatterns of the conditions have injectivity constraint; they will only have valid constraints if they are mapped with signature nodes
			EditorToIBeXDisjunctPatternHelper.findInjectivityConstraints(conditionPatterns, n -> !signatureNodes.contains(n));
			
			for(Entry<Set<EditorNode>, EditorApplicationCondition> conditionSubmap: simpleConditionSubpatterns.entrySet()) {
				// add the new subconditions to the corresponding Editorconditions
				if(conditionMap.containsKey(conditionSubmap.getKey())) {
					conditionMap.computeIfPresent(conditionSubmap.getKey(),(k, v) -> {
						v.getConditions().add(conditionSubmap.getValue());
						return v;
					});
				}
				else {
					EditorCondition newCondition =  GTFactory.eINSTANCE.createEditorCondition();
					newCondition.getConditions().add(conditionSubmap.getValue());
					//all conditions need a different name so that they are not recognized as the same condition by the IBeXToPatternTransformation class
					newCondition.setName(patternName + "_" + condition.getName() + "_" + conditionNr++);	
					conditionMap.put(conditionSubmap.getKey(), newCondition);
				}
			}				
		}
		return  conditionMap;
	}
	
	/**
	 * computes the subpatterns for the specific condition
	 * 
	 * @param condition the specific condition
	 * @param signatureNodes the signatureNodes of the pattern
	 * @return the computed subpattern
	 */
	private Map<Set<EditorNode>, EditorApplicationCondition> computeConditionSubmap(final EditorApplicationCondition condition, final List<EditorNode> signatureNodes){
		Map<Set<EditorNode>, EditorApplicationCondition> applicationConditionMap = new HashMap<Set<EditorNode>, EditorApplicationCondition>();
		//indexNr for the condition naming
		int conditionNr = 0;
		if(getFlattenedPattern(condition.getPattern()).isPresent()) {
			//see if the whole condition fits in one subpattern; then it does not need to be partitioned
			for(Set<EditorNode> subpattern: subpatterns) {
				if(subpattern.containsAll(EditorModelUtils.getNodesByOperator(getFlattenedPattern(condition.getPattern()).get(), EditorOperator.CONTEXT))) {
					applicationConditionMap.put(subpattern, condition);
					return applicationConditionMap;
				}
			}
			
			//else the pattern needs to be partitioned
			for(EditorNode node: EditorModelUtils.getNodesByOperator(getFlattenedPattern(condition.getPattern()).get(), EditorOperator.CONTEXT)) {
				boolean inASubbpattern = false;
				
				//find out to which subpattern do the nodes in the condition belong to; node can be saved directly in the subpattern or indirectly as a signature node
				for(Set<EditorNode> subpattern: subpatterns) {
					if(subpattern.contains(node)) {					
						if(applicationConditionMap.containsKey(subpattern)) {
							applicationConditionMap.computeIfPresent(subpattern, (k, v) -> {
								v.getPattern().getNodes().add(node);
								return v;
							});
						}
						else {
							/* creates a new partitioned simpleCondition for the specific subpattern; all subconditions need different names so
							 * that they can be seen as different patterns by the EditorToIBeXPatternTransformator
							 */
							EditorApplicationCondition newCondition = GTFactory.eINSTANCE.createEditorApplicationCondition();
							EditorPattern pattern  = GTFactory.eINSTANCE.createEditorPattern();
							pattern.getNodes().add(node);
							pattern.setName(condition.getPattern().getName() + "_" + conditionNr++);
							pattern.setAbstract(condition.getPattern().isAbstract());
							newCondition.setPattern(pattern);
							newCondition.setType(condition.getType());
							applicationConditionMap.put(subpattern, newCondition);
						}
						inASubbpattern = true;
					}
				}
				if(!inASubbpattern) {
					// if it is not in a subpattern, then it is disjunct => all disjunct subconditions will be saved and dealt with somewhere else
					throw new IllegalArgumentException("condition could not be partioned");
				}
			}
		}
		return applicationConditionMap;
	}
}
