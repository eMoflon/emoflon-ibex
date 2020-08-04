package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.EditorApplicationCondition;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorCondition;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.GTFactory;
import org.emoflon.ibex.gt.editor.utils.GTConditionHelper;
import org.emoflon.ibex.gt.editor.utils.GTDisjunctPatternFinder;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelFactory;

/**
 * class which divides the disjunct subpatterns 
 */
public class EditorToIBeXDisjunctContextPatternTransformation extends EditorToIBeXPatternTransformation{
	
	EditorToIBeXPatternTransformation transformation;
	Map<Set<EditorNode>, EditorPattern> dividedPatterns;
	List<Set<EditorNode>> subpatterns;
	List<Pair<EditorNode, List<EditorAttribute>>> disjunctAttributeConditions;
	EditorPattern editorPattern;
	Map<Pair<String, String>, List<Pair<EditorNode, EditorNode>>> injectivityConstraints;
	
	
	public EditorToIBeXDisjunctContextPatternTransformation(final EditorToIBeXPatternTransformation transformation, 
			final GTDisjunctPatternFinder patternfinder, final EditorPattern editorPattern) {
		this.transformation = transformation;
		dividedPatterns = new HashMap<Set<EditorNode>, EditorPattern>();
		disjunctAttributeConditions = new ArrayList<Pair<EditorNode,List<EditorAttribute>>>();
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
		dividedPatterns = divideContextPatterns(editorPattern, isLocalCheck);
		

		//find injectivity constraints in the subpatterns
		injectivityConstraints = EditorToIBeXDisjunctPatternHelper.findInjectivityConstraints(new ArrayList<EditorPattern>(dividedPatterns.values()), isLocalCheck);
		
		//find attributeConstraints between transformed disjunct subpatterns
		disjunctAttributeConditions = dividedPatterns.entrySet().stream()
				.flatMap(entrySet -> EditorToIBeXDisjunctPatternHelper.findDisjunctAttributeConstraints(entrySet.getKey(), entrySet.getValue()).stream())
				.collect(Collectors.toList());

		
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
		
		ibexPattern.getAttributesConstraints().addAll(EditorToIBeXDisjunctPatternHelper 
				.transformDisjunctAttributes(ibexPattern.getSubpatterns(), dividedPatterns.keySet(), disjunctAttributeConditions, ibexPattern.getInjectivityConstraints()));
		return ibexPattern;
	}
	
	/**
	 * divides a disjunct editorPattern in different subpatterns; not supported until now: EditorPatternAttributeCondition(?)
	 */
	private Map<Set<EditorNode>, EditorPattern> divideContextPatterns(final EditorPattern pattern, final Predicate<EditorNode> isLocalCheck) {
		
		List<EditorNode> editorNodes = new ArrayList<EditorNode>(EditorModelUtils.getNodesByOperator(pattern, EditorOperator.CONTEXT,
				EditorOperator.DELETE));	
		Map<Set<EditorNode>, EditorPattern> dividedPatterns = new HashMap<Set<EditorNode>, EditorPattern>();
		
		//see if there are any nodes that do not belong to any subpattern
		List<EditorNode> independentNodes = new ArrayList<EditorNode>(editorNodes);
		for(Set<EditorNode> subpattern: subpatterns) {
			independentNodes.removeIf(n -> subpattern.contains(n));
		}
		if(!independentNodes.isEmpty()) {
			throw new IllegalArgumentException("there is a node that does not belong to any subpattern");
		}
		
		int i = 0;
		//divide the nodes in the pattern
		for(Set<EditorNode> subpattern: subpatterns) {
			if(subpattern.containsAll(editorNodes)) {
				dividedPatterns.put(subpattern, pattern);
				break;
			}
			List<EditorNode> subpatternNodes = editorNodes.stream().filter(n -> subpattern.contains(n)).collect(Collectors.toList());
			if(subpatternNodes.isEmpty()) {
				continue;
			}
			EditorPattern newSubpattern = GTFactory.eINSTANCE.createEditorPattern();
			newSubpattern.setName(pattern.getName() + "_"+ i++);
			newSubpattern.getNodes().addAll(subpatternNodes);
			newSubpattern.setType(pattern.getType());
			dividedPatterns.put(subpattern, newSubpattern);
		}

		//find illegal injectivity constraints in the subpatterns
		EditorToIBeXDisjunctPatternHelper.findInjectivityConstraints(new ArrayList<EditorPattern>(dividedPatterns.values()), isLocalCheck);
		
		if(!pattern.getConditions().isEmpty()) {
			//divide the conditions and add them to the divided editorPatterns
			if(pattern.getConditions().size()>1) throw new IllegalArgumentException("conditions can not be partitioned");
			
			Map<Set<EditorNode>, EditorCondition> conditionSubmap = new HashMap<Set<EditorNode>, EditorCondition>();
			
			for(EditorApplicationCondition simpleCondition: new GTConditionHelper(pattern.getConditions().get(0)).getApplicationConditions()) {
				List<EditorNode> signatureNodes = editorNodes.stream().filter(isLocalCheck.negate()).collect(Collectors.toList());
				Map<Set<EditorNode>, EditorPattern> dividedConditions = divideContextPatterns(simpleCondition.getPattern(), 
						n -> !signatureNodes.contains(n));
				int j = 0;
				
				for(Entry<Set<EditorNode>, EditorPattern> entry: dividedConditions.entrySet()) {
					if(!conditionSubmap.containsKey(entry.getKey())) {
						EditorCondition condition = GTFactory.eINSTANCE.createEditorCondition();
						condition.setName(pattern.getConditions().get(0).getName() + "_" + j++);
						conditionSubmap.put(entry.getKey(), condition);

					}
					EditorApplicationCondition subcondition = GTFactory.eINSTANCE.createEditorApplicationCondition();
					subcondition.setType(simpleCondition.getType());
					subcondition.setPattern(entry.getValue());
					conditionSubmap.get(entry.getKey()).getConditions().add(subcondition);
				}
				
			}
			
			for(Entry<Set<EditorNode>, EditorCondition> entry: conditionSubmap.entrySet()) {
				if(dividedPatterns.get(entry.getKey()) == null) {
					//there should be a disjunct editorPattern under this subpattern; if not then something went wrong and the pattern is dealt with in the original way
					throw new IllegalArgumentException("something went wrong when diving the subconditions");
				}
				else {
					if(conditionSubmap.size()==1) {
						entry.getValue().setName(pattern.getConditions().get(0).getName());
					}
					dividedPatterns.get(entry.getKey()).getConditions().add(entry.getValue());
				}
			}
		}
		return dividedPatterns;
	}

}