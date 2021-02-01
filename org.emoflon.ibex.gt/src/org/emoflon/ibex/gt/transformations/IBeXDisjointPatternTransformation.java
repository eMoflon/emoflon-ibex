package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint;

public class IBeXDisjointPatternTransformation {
	
	private final IBeXContextPattern pattern;
	private final Set<Set<IBeXNode>> subpatterns;
	Map<Set<IBeXNode>, IBeXContextPattern> dividedSubpatterns;
	
	public IBeXDisjointPatternTransformation(final IBeXContextPattern disjointPattern, Set<Set<IBeXNode>> subgraphs) {
		pattern = disjointPattern;
		subpatterns = subgraphs;
		dividedSubpatterns = new HashMap<Set<IBeXNode>, IBeXContextPattern>();
	}
	
	/**
	 * divides the EditorPattern in subpatterns and transforms the subpatterns
	 * 
	 * @param editorPattern the pattern
	 * @param isLocalCheck
	 * @return the IBeXDisjointContextPattern
	 * @throws IllegalArgumentException if something goes wrong when dividing the EditorPattern
	 */
	public IBeXDisjointContextPattern transformToContextPattern(){
		
		if(!checkConstraints(pattern)) throw new IllegalArgumentException("pattern cant be divided");
		
		IBeXDisjointContextPattern ibexPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXDisjointContextPattern();
		ibexPattern.setName(pattern.getName());
		
		//divide the pattern into subpatterns
		dividedSubpatterns = divideContextPatterns(pattern);
		
		ibexPattern.getSubpatterns().addAll(dividedSubpatterns.values());		
		
		//find the injectivity constraints		
		List<IBexDisjointInjectivityConstraint> subconstraint = new ArrayList<IBexDisjointInjectivityConstraint>();
		List<Set<IBeXNode>> subpatternList = new ArrayList<Set<IBeXNode>>(dividedSubpatterns.keySet());
		for(int i = 0; i<subpatternList.size(); i++) {
			Set<IBeXNode> subpattern = subpatternList.get(i);
			for(int j = i+1; j<subpatternList.size(); j++) {
				Set<IBeXNode> otherSubpattern = subpatternList.get(j);
				List<List<IBeXNode>> pairs = pattern.getInjectivityConstraints().stream()
						.filter(pair -> {
						return subpattern.contains(pair.getValues().get(0)) && otherSubpattern.contains(pair.getValues().get(1))||
								subpattern.contains(pair.getValues().get(1)) && otherSubpattern.contains(pair.getValues().get(0));
					}).map(injectivity -> injectivity.getValues()).collect(Collectors.toList());
				if(!pairs.isEmpty()) {
						subconstraint.add(transformConstraints(dividedSubpatterns.get(subpattern), dividedSubpatterns.get(otherSubpattern), pairs));
			
				}
			}	
		}
		List<IBeXInterdependentInjectivityConstraints> injectivityConstraints = transformInjectivityConstraints(ibexPattern.getSubpatterns(), subconstraint);
		ibexPattern.getInjectivityConstraints().addAll(injectivityConstraints);
		
		return ibexPattern;
	}
	
	/**
	 * checks if a ibexpattern does not violate constraints
	 * @return true if if does not violate constraint
	 */
	private boolean checkConstraints(IBeXContextPattern ibexPattern) {
		
		//check if injectivity constraints can be divided
		boolean forbiddenInjectivity = ibexPattern.getInjectivityConstraints().stream()
				.anyMatch(pair -> {
					//injectivity constraint is forbidden if one node is local and if both nodes are in different subpatterns
					boolean isLocal = (ibexPattern.getLocalNodes().contains(pair.getValues().get(0)) || 
							ibexPattern.getLocalNodes().contains(pair.getValues().get(1)));
					for(Set<IBeXNode> subpattern: subpatterns) {
						if(subpattern.contains(pair.getValues().get(0)) && isLocal) {
							if(!subpattern.contains(pair.getValues().get(1))) {
								return true;
							}
						}
					}
					return false;
				});
							
		if(forbiddenInjectivity) throw new IllegalArgumentException("injectivity constraints can't be resolved");	
		
		for(IBeXPatternInvocation invocation: ibexPattern.getInvocations()) {
			forbiddenInjectivity &= checkConstraints(invocation.getInvokedPattern());
		}
		return !forbiddenInjectivity;
	}
	
	/**
	 * divides a disjoint pattern in different subpatterns; not supported until now: EditorPatternAttributeCondition(?)
	 */
	private Map<Set<IBeXNode>, IBeXContextPattern> divideContextPatterns(IBeXContextPattern pattern) {
		List<IBeXNode> signatureNodes = new ArrayList<IBeXNode>(pattern.getSignatureNodes());
		List<IBeXNode> localNodes = new ArrayList<IBeXNode>(pattern.getLocalNodes());
		Map<Set<IBeXNode>, IBeXContextPattern> dividedSubpatterns = new HashMap<Set<IBeXNode>, IBeXContextPattern>();
		

		
		int i = 0;
		//divide the nodes in the pattern
		if(dividedSubpatterns.isEmpty()) {
			for(Set<IBeXNode> subpattern: subpatterns) {
				if(subpattern.containsAll(signatureNodes)) {
					//if the whole pattern belongs to a specific subpattern, then it does not need to be partitioned into several subpatterns
					pattern.setSubpattern(true);
					dividedSubpatterns.put(subpattern, pattern);
					break;
				}
				List<IBeXNode> subpatternSignatureNodes = subpattern.stream().filter(n -> signatureNodes.contains(n)).collect(Collectors.toList());				
				if(subpatternSignatureNodes.isEmpty()) continue;
				IBeXContextPattern newSubpattern =  IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
				newSubpattern.setName( pattern.getName()+ "_" + i);
				newSubpattern.setSubpattern(true);
				
				newSubpattern.getSignatureNodes().addAll(subpatternSignatureNodes);
				List<IBeXNode> subpatternLocalNodes = subpattern.stream().filter(n -> localNodes.contains(n)).collect(Collectors.toList());
				newSubpattern.getLocalNodes().addAll(subpatternLocalNodes);
				
				newSubpattern.getLocalEdges().addAll(pattern.getLocalEdges().stream().filter(edge -> subpattern.contains(edge.getSourceNode())).collect(Collectors.toList()));
				newSubpattern.getInjectivityConstraints().addAll(pattern.getInjectivityConstraints().stream()
						.filter(pair -> subpattern.contains(pair.getValues().get(0)) && subpattern.contains(pair.getValues().get(1))).collect(Collectors.toList()));		
				
				dividedSubpatterns.put(subpattern, newSubpattern);
				i++;
			}
		}
		
		//now the invocations are divided
		for(IBeXPatternInvocation invocation: pattern.getInvocations()) {
			//divide the invocations
			for(Entry<Set<IBeXNode>, IBeXContextPattern> dividedInvocations : divideContextPatterns(invocation.getInvokedPattern()).entrySet()) {
				if(!dividedSubpatterns.containsKey(dividedInvocations.getKey())) {
					throw new IllegalArgumentException("invocation could not be divided");
				}
				else {				
					IBeXPatternInvocation newInvocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
					newInvocation.setInvokedBy(dividedSubpatterns.get(dividedInvocations.getKey()));
					newInvocation.setInvokedPattern(dividedInvocations.getValue());
					newInvocation.setPositive(invocation.isPositive());
					Map<IBeXNode, IBeXNode> mapping = invocation.getMapping().entrySet().stream()
							.filter(entry -> dividedInvocations.getKey().contains(entry.getKey())).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
					for(Entry<IBeXNode, IBeXNode> map: mapping.entrySet()) {
						newInvocation.getMapping().put(map.getKey(), map.getValue());
					}
					dividedSubpatterns.get(dividedInvocations.getKey()).getInvocations().add(newInvocation);
				}
			}
		}
		
		return dividedSubpatterns;
	}	
	
	/**
	 * transforms the dependent injectivity constraints
	 */
	private List<IBeXInterdependentInjectivityConstraints> transformInjectivityConstraints(List<IBeXContextPattern> subpatterns, List<IBexDisjointInjectivityConstraint> constraints){
		
		Map<List<IBeXContextPattern>, List<IBexDisjointInjectivityConstraint>> constraintMap = new HashMap<List<IBeXContextPattern>, List<IBexDisjointInjectivityConstraint>>();
		
		for(IBexDisjointInjectivityConstraint constraint: constraints) {
			
			List<List<IBeXContextPattern>> dependentPatterns = constraintMap.keySet().stream()
					.filter(list -> list.contains(constraint.getPattern1()) || list.contains(constraint.getPattern2())).collect(Collectors.toList());
			
			if(dependentPatterns.isEmpty()) {
				List<IBeXContextPattern> key = new ArrayList<IBeXContextPattern>();
				key.add(constraint.getPattern1());
				key.add(constraint.getPattern2());
				List<IBexDisjointInjectivityConstraint> value = new ArrayList<IBexDisjointInjectivityConstraint>();
				value.add(constraint);
				constraintMap.put(key, value);
			}
			else {
				List<IBeXContextPattern> key = new ArrayList<IBeXContextPattern>();
				key.addAll(dependentPatterns.stream().flatMap(pattern -> pattern.stream()).collect(Collectors.toList()));
				key.add(constraint.getPattern1());
				key.add(constraint.getPattern2());
				List<IBexDisjointInjectivityConstraint> value = new ArrayList<IBexDisjointInjectivityConstraint>();
				value.addAll(dependentPatterns.stream().flatMap(pattern -> constraintMap.get(pattern).stream()).collect(Collectors.toList()));
				value.add(constraint);
				constraintMap.put(key, value);
				for(List<IBeXContextPattern> dependentSubpattern: dependentPatterns) {
					constraintMap.remove(dependentSubpattern);
				}
			}
		}
		
		List<IBeXInterdependentInjectivityConstraints> dependentConstraints = new ArrayList<IBeXInterdependentInjectivityConstraints>();
		for(Entry<List<IBeXContextPattern>, List<IBexDisjointInjectivityConstraint>> entry: constraintMap.entrySet()) {
			IBeXInterdependentInjectivityConstraints depententConstraint = IBeXPatternModelFactory.eINSTANCE.createIBeXInterdependentInjectivityConstraints();
			depententConstraint.getInjectivityConstraints().addAll(entry.getValue());
			depententConstraint.getPatterns().addAll(entry.getKey());
			dependentConstraints.add(depententConstraint);
		}
		return dependentConstraints;
	}
	
	/**
	 * transforms a injectivity constraint between two patterns
	 */
	private IBexDisjointInjectivityConstraint transformConstraints(IBeXContextPattern pattern1, IBeXContextPattern pattern2, List<List<IBeXNode>> nodes) {
		IBexDisjointInjectivityConstraint constraint = IBeXPatternModelFactory.eINSTANCE.createIBexDisjointInjectivityConstraint();
		constraint.setPattern1(pattern1);
		constraint.setPattern2(pattern2);
		List<IBeXNode> nodes1 = nodes.stream()
				.filter(pair -> pattern1.getSignatureNodes().contains(pair.get(0)) || pattern1.getSignatureNodes().contains(pair.get(1)))
				.map(pair -> (pattern1.getSignatureNodes().contains(pair.get(0)))? pair.get(0) : pair.get(1)).collect(Collectors.toList());
		List<IBeXNode> nodes2 = nodes.stream()
				.filter(pair -> pattern2.getSignatureNodes().contains(pair.get(0)) || pattern2.getSignatureNodes().contains(pair.get(1)))
				.map(pair -> (pattern2.getSignatureNodes().contains(pair.get(0)))? pair.get(0) : pair.get(1)).collect(Collectors.toList());
		constraint.getNode1().addAll(nodes1);
		constraint.getNode2().addAll(nodes2);
		return constraint;
	}
	
	/**
	 * divides all attribute contraints of an ibexPattern
	 */
	public void transformAttributeConstraint(IBeXDisjointContextPattern disjointPattern) {
		
		for(Set<IBeXNode> subpattern: subpatterns) {
			
			List<IBeXAttributeConstraint> attributeConstraints = new ArrayList<IBeXAttributeConstraint>();	
		
			IBeXContextPattern pattern = disjointPattern.getSubpatterns().stream()
				.filter(p -> p.getSignatureNodes().stream().anyMatch(n -> subpattern.contains(n)) || p.getLocalNodes().stream().anyMatch(n -> subpattern.contains(n)))
				.findFirst().get();
				
			//divide the attribute constraints; for now only the attribute constraints that are not dependent on other subpatterns
			for(IBeXAttributeConstraint constraint: disjointPattern.getNonOptimizedPattern().getAttributeConstraint()) {
				boolean isInSubpattern = true;
				//check if the left side is dependent on the subpattern; only attribute constraints are checked since the other expression types are independent on the subpattern
				if(constraint.getLhs() instanceof IBeXAttributeExpression) {
					if(!subpattern.contains(((IBeXAttributeExpression) constraint.getLhs()).getNode())) {
						isInSubpattern = false;
					}
				}
				//check if the right side is dependent on the subpattern
				if(constraint.getRhs() instanceof IBeXAttributeExpression) {
					if(!subpattern.contains(((IBeXAttributeExpression) constraint.getRhs()).getNode())) {
						isInSubpattern = false;
					}
				}
				//if both sides are not in other subpatterns then it is added to the current subpattern
				if(isInSubpattern) {
					attributeConstraints.add(constraint);
				} 
			}
			pattern.getAttributeConstraint().addAll(attributeConstraints);
			
			List<IBeXArithmeticConstraint> arithmeticConstraints = new ArrayList<IBeXArithmeticConstraint>();			
			for(IBeXArithmeticConstraint constraint: pattern.getArithmeticConstraints()) {
				boolean isInSubpattern = true;
				
				//find all dependent nodes of the expression
				List<IBeXArithmeticAttribute> dependencies = findNodesInExpression(constraint.getLhs());
				dependencies.addAll(findNodesInExpression(constraint.getRhs()));
	
				for(IBeXArithmeticAttribute node: dependencies) {
					if(!subpattern.stream().anyMatch(n -> n.getName().equals(node.getName()) && n.getType().equals(node.getType()))) {
						//if there is no node that is the same then it is not (fully) in the subpattern
						isInSubpattern = false;
					}
				}
				//if both sides are not in other subpatterns then it is added to the current subpattern
				if(isInSubpattern) {
					arithmeticConstraints.add(constraint);
				} 
			}
			pattern.getArithmeticConstraints().addAll(arithmeticConstraints);
			
		}
		
		List<IBeXConstraint> dependentConstraints = new ArrayList<IBeXConstraint>();	
		dependentConstraints.addAll(disjointPattern.getNonOptimizedPattern().getAttributeConstraint());
		dependentConstraints.addAll(disjointPattern.getNonOptimizedPattern().getArithmeticConstraints());
		//create the disjoint attribute constraint
		disjointPattern.getAttributeConstraints().addAll(transformAttributeConstraints(dividedSubpatterns, dependentConstraints, disjointPattern.getInjectivityConstraints()));
			}
	
	/**
	 * transforms the attributes into dependent disjoint attributes; pool dependent attributes together to dependent attributes
	 */
	private List<IBeXInterdependentAttributes> transformAttributeConstraints(Map<Set<IBeXNode>, IBeXContextPattern> dividedSubpatterns, List<IBeXConstraint> constraints,
			List<IBeXInterdependentInjectivityConstraints> injectivityConstraints) {
		
		Map<Set<IBeXContextPattern>, List<IBeXDisjointAttribute>> constraintMap = new HashMap<Set<IBeXContextPattern>, List<IBeXDisjointAttribute>>();
		
		List<IBeXDisjointAttribute> disjointAttributes = constraints.stream().map(c -> transformAttributeConstraint(dividedSubpatterns, c)).collect(Collectors.toList());
		for(IBeXDisjointAttribute attr: disjointAttributes) {
			//search for dependent patterns
			List<Set<IBeXContextPattern>> keys = constraintMap.keySet().stream().filter(key -> {
				for(IBeXContextPattern pattern: key) {
					if(attr.getTargetPattern().contains(pattern)|| attr.getSourcePattern().contains(pattern)) return true;
				}
				return false;
			}).collect(Collectors.toList());
			
			if(keys.isEmpty()) {
				//if there is no dependency -> create a new entry
				Set<IBeXContextPattern> newKey = new HashSet<IBeXContextPattern>(attr.getSourcePattern());
				newKey.addAll(attr.getTargetPattern());
				List<IBeXDisjointAttribute> allConstraints = new ArrayList<IBeXDisjointAttribute>();
				allConstraints.add(attr);
				constraintMap.put(newKey, allConstraints);
			}else {
				//update the constraint map -> dependent attributes are pooled together
				Set<IBeXContextPattern> newKey = new HashSet<IBeXContextPattern>();
				List<IBeXDisjointAttribute> newConstraints = new ArrayList<IBeXDisjointAttribute>();
				for(Set<IBeXContextPattern> key: keys) {
					newKey.addAll(key);
					newConstraints.addAll(constraintMap.remove(key));
				}
				newKey.addAll(attr.getTargetPattern());
				newKey.addAll(attr.getSourcePattern());
				newConstraints.add(attr);
				constraintMap.put(newKey, newConstraints);
			}
		}
		
		List<IBeXInterdependentAttributes> attrList = new ArrayList<IBeXInterdependentAttributes>();
		//create the pool of dependent constraints
		for(Entry<Set<IBeXContextPattern>, List<IBeXDisjointAttribute>> dependentAttribute: constraintMap.entrySet()) {
			IBeXInterdependentAttributes attr = IBeXPatternModelFactory.eINSTANCE.createIBeXInterdependentAttributes();
			attr.getInterdependentPatterns().addAll(dependentAttribute.getKey());
			attr.getAttributes().addAll(dependentAttribute.getValue());
			
			//search for the injectivity constraints
			List<IBeXInterdependentInjectivityConstraints> injecConstr = injectivityConstraints.stream().filter(i -> {
				for(IBeXContextPattern pattern: i.getPatterns()) {
					if(attr.getInterdependentPatterns().contains(pattern)) return true;
				}
				return false;
			}).collect(Collectors.toList());
			
			if(injecConstr.size()>1) throw new IllegalArgumentException("attribute constraints should not go over more than one dependent injectivity");
			if(injecConstr.size()==1) attr.setInjectivityConstraints(injecConstr.get(0));
			
			attrList.add(attr);
		}
		
		return attrList;
	}
	
	/**
	 * transform a single disjoint attribute constraint 
	 */
	private IBeXDisjointAttribute transformAttributeConstraint(Map<Set<IBeXNode>, IBeXContextPattern> dividedSubpatterns, IBeXConstraint constraint) {
		
		IBeXDisjointAttribute disjointAttribute = IBeXPatternModelFactory.eINSTANCE.createIBeXDisjointAttribute();
		//search for the patterns for the lhs and rhs
		List<IBeXContextPattern> sourcePatterns = new ArrayList<IBeXContextPattern>();
		List<IBeXContextPattern> targetPatterns = new ArrayList<IBeXContextPattern>();
		
		if(constraint instanceof IBeXAttributeConstraint) {
			//find the source patterns for the lhs
			if(!(((IBeXAttributeConstraint) constraint).getLhs() instanceof IBeXAttributeExpression)) {
				throw new IllegalArgumentException("expression is not an attribute expression");
			}
			IBeXNode lhsNode = ((IBeXAttributeExpression) ((IBeXAttributeConstraint) constraint).getLhs()).getNode();
			sourcePatterns.addAll(dividedSubpatterns.values().stream()
					.filter(p -> p.getSignatureNodes().contains(lhsNode)).collect(Collectors.toList()));
			
			//find the source patterns for the rhs
			if(!(((IBeXAttributeConstraint) constraint).getRhs() instanceof IBeXAttributeExpression)) {
				throw new IllegalArgumentException("expression is not an attribute expression");
			}
			IBeXNode rhsNode = ((IBeXAttributeExpression) ((IBeXAttributeConstraint) constraint).getRhs()).getNode();
			targetPatterns.addAll(dividedSubpatterns.values().stream()
					.filter(p -> p.getSignatureNodes().contains(rhsNode)).collect(Collectors.toList()));
		}
		else {
			//else it is an arithmetic constraint
			List<IBeXArithmeticAttribute> dependentLhs = findNodesInExpression(((IBeXArithmeticConstraint) constraint).getLhs());
			List<IBeXArithmeticAttribute> dependentRhs = findNodesInExpression(((IBeXArithmeticConstraint) constraint).getRhs());
			for(Entry<Set<IBeXNode>, IBeXContextPattern> entry: dividedSubpatterns.entrySet()) {
				//if any node of the subpattern is contained in the expression, then it is added to the dependent patterns
				if(dependentLhs.stream()
						.anyMatch(n -> entry.getKey().stream().anyMatch(node -> node.getName().equals(n.getName()) && node.getType().equals(n.getType())))) {
					sourcePatterns.add(entry.getValue());
				}
				//same for rhs
				if(dependentRhs.stream(
						).anyMatch(n -> entry.getKey().stream().anyMatch(node -> node.getName().equals(n.getName()) && node.getType().equals(n.getType())))) {
					targetPatterns.add(entry.getValue());
				}
			}
		}
		disjointAttribute.getSourcePattern().addAll(sourcePatterns);
		disjointAttribute.getTargetPattern().addAll(targetPatterns);
		disjointAttribute.getDisjointAttribute().add(constraint);
		
		return disjointAttribute;
	}
	
	/**
	 * find all dependent nodes of an arithmetic expression
	 */
	private List<IBeXArithmeticAttribute> findNodesInExpression(IBeXArithmeticExpression expression) {
		List<IBeXArithmeticAttribute> allNodes = new ArrayList<IBeXArithmeticAttribute>();
		if(expression instanceof IBeXUnaryExpression) {
			allNodes.addAll(findNodesInExpression(((IBeXUnaryExpression) expression).getOperand()));
		}
		if(expression instanceof IBeXBinaryExpression) {
			allNodes.addAll(findNodesInExpression(((IBeXBinaryExpression) expression).getLeft()));
			allNodes.addAll(findNodesInExpression(((IBeXBinaryExpression) expression).getRight()));
		}
		if(expression instanceof IBeXArithmeticAttribute) {
			allNodes.add((IBeXArithmeticAttribute) expression);
		}
		return allNodes;
	}
}
