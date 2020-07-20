package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelFactory;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint;
import org.emoflon.ibex.gt.editor.gT.AddExpression;
import org.emoflon.ibex.gt.editor.gT.ArithmeticCalculationExpression;
import org.emoflon.ibex.gt.editor.gT.ArithmeticExpression;
import org.emoflon.ibex.gt.editor.gT.ArithmeticNodeAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorApplicationCondition;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.gT.ExpExpression;
import org.emoflon.ibex.gt.editor.gT.MultExpression;
import org.emoflon.ibex.gt.editor.gT.OneParameterArithmetics;
import org.emoflon.ibex.gt.editor.utils.GTConditionHelper;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.moflon.core.utilities.EcoreUtils;

/**
 * Helper class for the IBeXDisjunctContextPattern class
 *
 */
public class EditorToIBeXDisjunctPatternHelper {
	
	//transformation of the injectivity constraints
	
	/**
	 * find injectivity constraints between all subpatterns
	 * 
	 * @param patternList all patterns
	 * @return a map with the subpattern names and their injectivity constraints
	 */
	public static final Map<Pair<String, String>, List<Pair<EditorNode, EditorNode>>> findInjectivityConstraints(final List<EditorPattern> patternList, final Predicate<EditorNode> isLocalCheck) {
		Map<Pair<String, String>, List<Pair<EditorNode, EditorNode>>> constraintMap = new HashMap<Pair<String,String>, List<Pair<EditorNode,EditorNode>>>();
		//iterate through all subpatterns and find the injectivity constraints between them
		for(int i = 0; i < patternList.size(); i++) {
			for(int j = i+1 ; j < patternList.size(); j++) {
				List<Pair<EditorNode, EditorNode>> injectivityConstraints = findInjectivityConstraintsInSubpattern(patternList.get(i), patternList.get(j), isLocalCheck);
				if(!injectivityConstraints.isEmpty()) {
					constraintMap.put(new Pair<String, String>(patternList.get(i).getName(), patternList.get(j).getName()), injectivityConstraints);
				}
			}
		}
		return constraintMap;
	}
	
	/**
	 * find all injectivity constraints between two subpatterns; the nodes in the injectivity constraint need to be signature nodes;
	 * else it is not possible to check the injectivity contraint later since there is no information about the local nodes in the submatches
	 */
	private static final List<Pair<EditorNode, EditorNode>> findInjectivityConstraintsInSubpattern(final EditorPattern pattern1, final EditorPattern pattern2, final Predicate<EditorNode> isLocalCheck){		
		List<Pair<EditorNode, EditorNode>> injectivityConstraints = new ArrayList<Pair<EditorNode,EditorNode>>();
		
		//iterate through all nodes in both subpatterns and find the injectivity constraint between them
		for(EditorNode node1: EditorModelUtils.getNodesByOperator(pattern1, EditorOperator.CONTEXT, EditorOperator.DELETE)) {
			for(EditorNode node2: EditorModelUtils.getNodesByOperator(pattern2, EditorOperator.CONTEXT, EditorOperator.DELETE)) {
				if(EcoreUtils.areTypesCompatible(node1.getType(), node2.getType())) {
					//check if the nodes are local
					if(isLocalCheck.test(node1)||isLocalCheck.test(node2)) {
						throw new IllegalArgumentException("There is a injectivity contraint between a node and a local node. Pattern could not be divided");
					}
					else {
						injectivityConstraints.add(new Pair<EditorNode, EditorNode>(node1, node2));
					}		
				}
			}
		}
		return injectivityConstraints;
	}
	
	/**
	 * transforms the injectivity constraint and saves the dependency between subpatterns
	 */
	public static final List<IBeXDependentInjectivityConstraints> transformInjectivityConstraints(final List<IBeXContextPattern> ibexSubpatterns, 
			final Map<Pair<String, String>, List<Pair<EditorNode, EditorNode>>> injectivityConstraints){
		//find out which injectivity constrains are dependent of each other	
		Map<List<String>, List<IBexDisjunctInjectivityConstraint>> constraintMap = new HashMap<List<String>, List<IBexDisjunctInjectivityConstraint>>();
		
		for(Entry<Pair<String, String>, List<Pair<EditorNode, EditorNode>>> constraint: injectivityConstraints.entrySet()) {
			//find the subpatterns
			IBeXContextPattern pattern1 = ibexSubpatterns.stream().filter(pattern -> pattern.getName().equals(constraint.getKey().getLeft())).findAny().get();
			IBeXContextPattern pattern2 = ibexSubpatterns.stream().filter(pattern -> pattern.getName().equals(constraint.getKey().getRight())).findAny().get();
			
			IBexDisjunctInjectivityConstraint transformedConstraint = transformInjectivityConstraints(pattern1, pattern2, constraint.getValue());
			
			//filter for injectivity constraints that share the same pattern
			List<List<String>> keys = constraintMap.keySet().stream().filter(key -> {
				for(String pattern: key) {
					if(transformedConstraint.getPattern1().getName().equals(pattern)||transformedConstraint.getPattern2().getName().equals(pattern)) return true;
				}
				return false;
			}).collect(Collectors.toList());
			
			if(keys.isEmpty()) {
				List<IBexDisjunctInjectivityConstraint> allConstraints = new ArrayList<IBexDisjunctInjectivityConstraint>();
				List<String> patternList = new ArrayList<String>();
				patternList.add(transformedConstraint.getPattern1().getName());
				patternList.add(transformedConstraint.getPattern2().getName());
				allConstraints.add(transformedConstraint);
				constraintMap.put(patternList, allConstraints);
			}else {
				//update the constraint map
				List<String> newKey = new ArrayList<String>();
				List<IBexDisjunctInjectivityConstraint> newConstraints = new ArrayList<IBexDisjunctInjectivityConstraint>();
				for(List<String> key: keys) {
					newKey.addAll(key);
					newConstraints.addAll(constraintMap.remove(key));
				}
				newKey.add(transformedConstraint.getPattern1().getName());
				newKey.add(transformedConstraint.getPattern2().getName());
				newConstraints.add(transformedConstraint);
				constraintMap.put(newKey, newConstraints);
			}
		}
		
		List<IBeXDependentInjectivityConstraints> allDependentConstraints = new ArrayList<IBeXDependentInjectivityConstraints>();
		
		for(Entry<List<String>, List<IBexDisjunctInjectivityConstraint>> entrySet: constraintMap.entrySet()) {
			IBeXDependentInjectivityConstraints dependentContraints = IBeXDisjunctPatternModelFactory.eINSTANCE.createIBeXDependentInjectivityConstraints();
			dependentContraints.getPatterns().addAll(ibexSubpatterns.stream().filter(subpattern -> entrySet.getKey().contains(subpattern.getName())).collect(Collectors.toList()));
			dependentContraints.getInjectivityConstraints().addAll(entrySet.getValue());
			allDependentConstraints.add(dependentContraints);
		}
		return allDependentConstraints;
	}
	
	/**
	 * transform all insjectivity constraints between two subpatterns
	 */
	private static IBexDisjunctInjectivityConstraint transformInjectivityConstraints(IBeXContextPattern pattern1, IBeXContextPattern pattern2,
		List<Pair<EditorNode, EditorNode>> injectivityConstraints) {
		IBexDisjunctInjectivityConstraint constraint = IBeXDisjunctPatternModelFactory.eINSTANCE.createIBexDisjunctInjectivityConstraint();
		List<IBeXNode> nodeList1 = new ArrayList<IBeXNode>();
		List<IBeXNode> nodeList2 = new ArrayList<IBeXNode>();
		for(Pair<EditorNode, EditorNode> costraint: injectivityConstraints) {
			//find the IBeXNodes in both subpatterns 	
			IBeXNode node1 = pattern1.getSignatureNodes().stream()
					.filter(node -> node.getName().equals(costraint.getLeft().getName()) || node.getName().equals(costraint.getRight().getName())).findAny().get();
			IBeXNode node2 = pattern2.getSignatureNodes().stream()
					.filter(node -> node.getName().equals(costraint.getLeft().getName()) || node.getName().equals(costraint.getRight().getName())).findAny().get();
			nodeList1.add(node1);
			nodeList2.add(node2);

		}			
		constraint.setPattern1(pattern1);
		constraint.setPattern2(pattern2);
		constraint.getNode1().addAll(nodeList1);
		constraint.getNode2().addAll(nodeList2);
		return constraint;
	}
	
	//transformation of attribute constraints
	
	/**
	 * transforms the disjunct attributeConstraints into IBexDisjunctAttribute
	 */
	public static final List<IBeXDependentDisjunctAttribute> transformDisjunctAttributes(final List<IBeXContextPattern> transformedSubpatterns, final Set<Set<EditorNode>> subpatterns,
			final List<Pair<EditorNode, List<EditorAttribute>>>  attributeConstraints, List<IBeXDependentInjectivityConstraints> injectivityConstraints){
		
		Map<Set<IBeXContextPattern>, List<IBeXDisjunctAttribute>> constraintMap = new HashMap<Set<IBeXContextPattern>, List<IBeXDisjunctAttribute>>();
		
		for(Pair<EditorNode, List<EditorAttribute>> pair: attributeConstraints) {
			IBeXDisjunctAttribute attribute = transformDisjunctAttribute(pair, transformedSubpatterns);
			
			//filter for injectivity constraints that share the same pattern
			List<Set<IBeXContextPattern>> keys = constraintMap.keySet().stream().filter(key -> {
				for(IBeXContextPattern pattern: key) {
					if(attribute.getTargetPattern().equals(pattern)|| attribute.getSourcePattern().contains(pattern)) return true;
				}
				return false;
			}).collect(Collectors.toList());
			
			if(keys.isEmpty()) {
				List<IBeXDisjunctAttribute> allConstraints = new ArrayList<IBeXDisjunctAttribute>();
				Set<IBeXContextPattern> patternList = new HashSet<IBeXContextPattern>();
				patternList.add(attribute.getTargetPattern());
				patternList.addAll(attribute.getSourcePattern());
				allConstraints.add(attribute);
				constraintMap.put(patternList, allConstraints);
			}else {
				//update the constraint map
				Set<IBeXContextPattern> newKey = new HashSet<IBeXContextPattern>();
				List<IBeXDisjunctAttribute> newConstraints = new ArrayList<IBeXDisjunctAttribute>();
				for(Set<IBeXContextPattern> key: keys) {
					newKey.addAll(key);
					newConstraints.addAll(constraintMap.remove(key));
				}
				newKey.add(attribute.getTargetPattern());
				newKey.addAll(attribute.getSourcePattern());
				newConstraints.add(attribute);
				constraintMap.put(newKey, newConstraints);
			}
		}
		List<IBeXDependentDisjunctAttribute> disjunctAttributes = new ArrayList<IBeXDependentDisjunctAttribute>();
		
		for(Entry<Set<IBeXContextPattern>, List<IBeXDisjunctAttribute>> constraints: constraintMap.entrySet()) {
			IBeXDependentDisjunctAttribute attribute = IBeXDisjunctPatternModelFactory.eINSTANCE.createIBeXDependentDisjunctAttribute();
			attribute.getDependentPatterns().addAll(constraints.getKey());
			attribute.getAttributes().addAll(constraints.getValue());
			//search for the injectivity constraints
			List<IBeXDependentInjectivityConstraints> injectivityConstraint = injectivityConstraints.stream().filter(i -> {
				for(IBeXContextPattern pattern: i.getPatterns()) {
					if(attribute.getDependentPatterns().contains(pattern)) return true;
				}
				return false;
			}).collect(Collectors.toList());
			if(injectivityConstraint.size()>1) throw new IllegalArgumentException("attribute constraints should not go over more than one dependent injectivity");
			if(injectivityConstraint.size()==1) attribute.setInjectivityConstraints(injectivityConstraint.get(0));
			disjunctAttributes.add(attribute);
			
		}
		
		
		return disjunctAttributes;
	}
	
	private static final IBeXDisjunctAttribute transformDisjunctAttribute(Pair<EditorNode, List<EditorAttribute>> pair, List<IBeXContextPattern> transformedSubpatterns) {
		//find targetNode
		Optional<IBeXNode> targetNode = null;
		IBeXContextPattern targetPattern = null;
		for(IBeXContextPattern pattern: transformedSubpatterns) {
			targetNode = pattern.getSignatureNodes().stream()
					.filter(node -> node.getName().equals(pair.getLeft().getName()) && node.getType().equals(pair.getLeft().getType())).findAny();
			if(targetNode.isPresent()) {
				targetPattern = pattern;
				break;
			}
		}
		if(targetNode.isEmpty()) throw new IllegalArgumentException("target node was not found");
		
		Map<IBeXAttributeConstraint, List<IBeXContextPattern>> sourceAttributeMapping = new HashMap<IBeXAttributeConstraint, List<IBeXContextPattern>>();
		//transform all attributes					
		for(EditorAttribute attribute: pair.getRight()) {

			//find out which subpatterns are needed for the attribute calculation
			List<IBeXContextPattern> nodesOfSubpattern = new ArrayList<IBeXContextPattern>();				
			
			if(attribute.getValue() instanceof ArithmeticCalculationExpression) {
				List<ArithmeticNodeAttribute> dependentNodes = findAllNodesInExpression(((ArithmeticCalculationExpression) attribute.getValue()).getExpression());
				for(IBeXContextPattern subpattern: transformedSubpatterns) {
					for(IBeXNode subpatternNode: subpattern.getSignatureNodes()) {
						if(dependentNodes.stream()
							.anyMatch(node -> node.getNode().getName().equals(subpatternNode.getName()) && node.getNode().getType().equals(subpatternNode.getType()))) {
							nodesOfSubpattern.add(subpattern);
						}
					}
				}					
			}
			
			if(attribute.getValue() instanceof EditorAttributeExpression) {
				//find the other subpattern
				for(IBeXContextPattern subpattern: transformedSubpatterns) {
					Optional<IBeXNode> node = subpattern.getSignatureNodes().stream().filter(signaturenode -> {
						return signaturenode.getName().equals(((EditorAttributeExpression) attribute.getValue()).getNode().getName()) 
								&& signaturenode.getType().equals(((EditorAttributeExpression) attribute.getValue()).getNode().getType());
					}).findFirst();
					if(node.isPresent()) {
						nodesOfSubpattern.add(subpattern);
						break;
					}
				}
			}	
			sourceAttributeMapping.put(transformAttributeValue(attribute, targetNode.get(), nodesOfSubpattern), nodesOfSubpattern);	
			
		}
		IBeXDisjunctAttribute disjunctAttribute = IBeXDisjunctPatternModelFactory.eINSTANCE.createIBeXDisjunctAttribute();
		disjunctAttribute.setTargetPattern(targetPattern);		
		disjunctAttribute.getSourcePattern().addAll(sourceAttributeMapping.values().stream().flatMap(c -> c.stream()).collect(Collectors.toSet()));
		disjunctAttribute.getDisjunctAttribute().addAll(sourceAttributeMapping.keySet());		
		return disjunctAttribute;
	}
	/**
	 * find all attribute constraints that a disjunct pattern has with another subpattern 
	 * 
	 * @param subpattern all nodes in the subpattern
	 * @param pattern the subpattern
	 */
	public static final List<Pair<EditorNode, List<EditorAttribute>>> findDisjunctAttributeConstraints(final Set<EditorNode> subpattern, final EditorPattern pattern){
		//get all nodes from the pattern
		List<Pair<EditorNode, List<EditorAttribute>>> dependencyList = new ArrayList<Pair<EditorNode,List<EditorAttribute>>>();
		for(EditorNode node: pattern.getNodes()) {
			List<EditorAttribute> dependentAttributes = checkAttributeConditions(subpattern, node);
			if(!dependentAttributes.isEmpty()) {
				dependencyList.add(new Pair<EditorNode, List<EditorAttribute>>(node, dependentAttributes));
			}
		}
		//attribute constraints should only be in the pattern or from signature patterns
		if(!pattern.getConditions().isEmpty()) {
			for(EditorApplicationCondition simpleCondition: new GTConditionHelper(pattern.getConditions().get(0)).getApplicationConditions()) {
				List<Pair<EditorNode, List<EditorAttribute>>> attributeConditionMap = findDisjunctAttributeConstraints(subpattern, simpleCondition.getPattern());
				if(!attributeConditionMap.isEmpty()) {
					throw new IllegalArgumentException("The condition pattern should not have attribute constraints with other subpatterns");
				}				
			}
		}
		return dependencyList;
	}
	
	/**
	 * finds out if an attribute is dependent of an attribute from a node in another subpattern and deletes it from the node and puts it in the IBeXDisjunctPattern
	 * 
	 * @param subpattern
	 * @param node
	 * @return
	 */
	private static final List<EditorAttribute> checkAttributeConditions(final Set<EditorNode> subpattern, final EditorNode node) {
		List<EditorAttribute> dependentAttributes = new ArrayList<EditorAttribute>();

		//iterate through all subpatterns and find the injectivity constraints between them

		for(EditorAttribute attribute: node.getAttributes()) {
			//assignments belong to create-pattern and are ignored
			if(attribute.getValue() instanceof EditorAttributeExpression && attribute.getRelation() != EditorRelation.ASSIGNMENT) {
				if(!subpattern.contains(((EditorAttributeExpression) attribute.getValue()).getNode())) {
					dependentAttributes.add(attribute);
				}
			}
			else if(attribute.getValue() instanceof ArithmeticCalculationExpression && attribute.getRelation() != EditorRelation.ASSIGNMENT) {
				for(ArithmeticNodeAttribute dependentNodes: findAllNodesInExpression(((ArithmeticCalculationExpression) attribute.getValue()).getExpression())) {
					//find out if any node in the arithmetic expression is in another subpattern
					if(!subpattern.contains(dependentNodes.getNode())) {
						dependentAttributes.add(attribute);
					}
				}

			}
		}
		//remove all attributes that are dependent on nodes of other attributes
		node.getAttributes().removeAll(dependentAttributes);
		return dependentAttributes;
	}
	
	/**
	 * find all nodes in an arithmetic expression
	 */
	private static List<ArithmeticNodeAttribute> findAllNodesInExpression(final ArithmeticExpression expression) {
		List<ArithmeticNodeAttribute> foundNodes = new ArrayList<ArithmeticNodeAttribute>();
		if(expression instanceof AddExpression) {
			foundNodes.addAll(findAllNodesInExpression(((AddExpression) expression).getLeft()));
			foundNodes.addAll(findAllNodesInExpression(((AddExpression) expression).getRight()));
		}
		if(expression instanceof MultExpression) {
			foundNodes.addAll(findAllNodesInExpression(((MultExpression) expression).getLeft()));
			foundNodes.addAll(findAllNodesInExpression(((MultExpression) expression).getRight()));
		}
		if(expression instanceof ExpExpression) {
			foundNodes.addAll(findAllNodesInExpression(((ExpExpression) expression).getLeft()));
			foundNodes.addAll(findAllNodesInExpression(((ExpExpression) expression).getRight()));
		}
		if(expression instanceof OneParameterArithmetics) {
			foundNodes.addAll(findAllNodesInExpression(((OneParameterArithmetics) expression).getExpression()));
		}
		if(expression instanceof ArithmeticNodeAttribute) {
			foundNodes.add((ArithmeticNodeAttribute) expression);
		}
		return foundNodes;
	}
	
	
	private static IBeXAttributeConstraint transformAttributeValue(EditorAttribute attribute, IBeXNode node, List<IBeXContextPattern> pattern) {
		IBeXAttributeConstraint constraint = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeConstraint();
		constraint.setRelation(EditorToIBeXAttributeHelper.convertRelation(attribute.getRelation()));
		constraint.setType(attribute.getAttribute());		
		constraint.setNode(node);
		
		if(attribute.getValue() instanceof ArithmeticCalculationExpression) {
			IBeXArithmeticValue value= IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValue();
			value.setExpression(EditorToArithmeticExtensionHelper.transformToGTArithmetics(((ArithmeticCalculationExpression) attribute.getValue()).getExpression()));
			constraint.setValue(value);
		}
		if(attribute.getValue() instanceof EditorAttributeExpression) {
			IBeXAttributeExpression ibexAttributeExpression = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeExpression();
			ibexAttributeExpression.setAttribute(((EditorAttributeExpression) attribute.getValue()).getAttribute());
			//find the node
			Optional<IBeXNode> sourceNode = pattern.get(0).getSignatureNodes().stream().filter(signaturenode -> {
				return signaturenode.getName().equals(((EditorAttributeExpression) attribute.getValue()).getNode().getName()) 
						&& signaturenode.getType().equals(((EditorAttributeExpression) attribute.getValue()).getNode().getType());
			}).findFirst();
			if(sourceNode.isEmpty()) throw new IllegalArgumentException("node was not found in pattern");
			ibexAttributeExpression.setNode(sourceNode.get());
			constraint.setValue(ibexAttributeExpression);
		}
		return constraint;
	}

}
