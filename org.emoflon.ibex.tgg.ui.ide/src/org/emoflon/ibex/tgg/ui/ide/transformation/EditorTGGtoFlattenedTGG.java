package org.emoflon.ibex.tgg.ui.ide.transformation;

import java.util.*;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.moflon.tgg.mosl.tgg.*;

/**
 *	Provides flattening of rule refinements for {@linkplain TripleGraphGrammarFile}s.
 */
public class EditorTGGtoFlattenedTGG {

	private Map<Set<Rule>, Rule> superRuleMap;
	private Map<String, ObjectVariablePattern> sourcePatterns;
	private Map<String, ObjectVariablePattern> targetPatterns;
	private Map<String, CorrVariablePattern> corrPatterns;
	
	// TODO improve the use of maps, currently the arbitrary access to them makes merge confusing
	// TODO think about correctness of the flatten operation for singular rules
	// TODO extend cleanup to Rules, not only ObjectVariablePatterns [necessary?]
	
	/**
	 * Produces a flattened {@linkplain TripleGraphGrammarFile} from a given non-flattened TripleGraphGrammarFile.
	 * 
	 * @param tgg The TripleGraphGrammarFile that shall be flattened.
	 * @return The flattened TripleGraphGrammarFile.
	 */
	public TripleGraphGrammarFile flatten(TripleGraphGrammarFile tgg) {
		TripleGraphGrammarFile flattened = EcoreUtil.copy(tgg);
		EList<Rule> rules = flattened.getRules();
		
		superRuleMap = new HashMap<Set<Rule>, Rule>();
		rules.stream()
//		  	 .filter(r -> !r.isAbstractRule()) // abstract rules are removed when flattening
		  	 .forEach(r -> superRuleMap.put(this.findSuperRules(r), r));
		
		Set<Rule> newRules = superRuleMap.keySet().stream()
								  				  .map(this::merge)
								  				  .collect(Collectors.toSet());
		rules.clear();
		rules.addAll(newRules);
		
		return flattened;
	}
	
	/**
	 * Produces a flattened version of a {@link Rule}.
	 * 
	 * @param r The Rule that shall be flattened.
	 * @param tgg The {@link TripleGraphGrammarFile} that contains this Rule.
	 * @return The flattened Rule.
	 */
	public Rule flattenRule(Rule r, TripleGraphGrammarFile tgg) {
		superRuleMap = new HashMap<Set<Rule>, Rule>();
		Set<Rule> superRules = this.findSuperRules(r);
		superRuleMap.put(superRules, r);
		return this.merge(superRules);
	}
	
	private Set<Rule> findSuperRules(Rule rule) {
		List<Rule> superRules = new ArrayList<Rule>();
		superRules.add(rule);
		
		for (int i = 0; i < superRules.size(); i++) {
			EList<Rule> newSuperTypes = superRules.get(i).getSupertypes();
			for (Rule r : newSuperTypes) {
				if (!superRules.contains(r)) {
					superRules.add(r);
				}
			}
		}
		
		return new HashSet<Rule>(superRules);
	}
	
	private Rule merge(Set<Rule> rules) {
		Rule mergedRule = null;
		
		// create "co-product" of the rules in mergedRule
		for (Rule r : rules) {
			if (mergedRule == null) {
				mergedRule = EcoreUtil.copy(r);
				// mergedRule.setAbstractRule(false);
				mergedRule.setName(superRuleMap.get(rules).getName());
				mergedRule.getSupertypes().clear();
			} else {
				mergedRule.getSourcePatterns().addAll(EcoreUtil.copyAll(r.getSourcePatterns()));
				mergedRule.getTargetPatterns().addAll(EcoreUtil.copyAll(r.getTargetPatterns()));
				mergedRule.getCorrespondencePatterns().addAll(EcoreUtil.copyAll(r.getCorrespondencePatterns()));
				mergedRule.getAttrConditions().addAll(EcoreUtil.copyAll(r.getAttrConditions()));
			}
		}
		
		// "glue" by finding equivalence classes of patterns and merging each class into one pattern
		mergeSourcePatterns(mergedRule.getSourcePatterns());
		mergedRule.getSourcePatterns().clear();
		mergedRule.getSourcePatterns().addAll(sourcePatterns.values());
		
		mergeTargetPatterns(mergedRule.getTargetPatterns());
		mergedRule.getTargetPatterns().clear();
		mergedRule.getTargetPatterns().addAll(targetPatterns.values());
		
		mergeCorrPatterns(mergedRule.getCorrespondencePatterns());
		mergedRule.getCorrespondencePatterns().clear();
		mergedRule.getCorrespondencePatterns().addAll(corrPatterns.values());
		
		// TODO AttributeConditions merge should be nicer, e.g. combine conditions on the same attribute
		
		// update all references to point to the correct copy in the flattened TGG
		cleanupReferences(mergedRule);
		
		return mergedRule;
	}

	private void mergeSourcePatterns(Collection<ObjectVariablePattern> patterns) {
		sourcePatterns = new HashMap<String, ObjectVariablePattern>();

		for (ObjectVariablePattern pattern : patterns) {
			if (!sourcePatterns.containsKey(pattern.getName())) {
				sourcePatterns.put(pattern.getName(), pattern);
			} else {
				mergeTwoObjectPatterns(pattern, sourcePatterns.get(pattern.getName()));
			}
		}
	}
	
	private void mergeTargetPatterns(Collection<ObjectVariablePattern> patterns) {
		targetPatterns = new HashMap<String, ObjectVariablePattern>();

		for (ObjectVariablePattern pattern : patterns) {
			if (!targetPatterns.containsKey(pattern.getName())) {
				targetPatterns.put(pattern.getName(), pattern);
			} else {
				mergeTwoObjectPatterns(pattern, targetPatterns.get(pattern.getName()));
			}
		}
	}
	
	private void mergeTwoObjectPatterns(ObjectVariablePattern from, ObjectVariablePattern to) {
		// TODO check for invalid configurations
		
		// Types
		if (to.getType().isSuperTypeOf(from.getType())) {
			to.setType(from.getType());
		}
		
		// Operator   
		mergeOperators(from, to);

		// Link Variable Pattern	
		for (LinkVariablePattern fromEdge : from.getLinkVariablePatterns()) {
			boolean matchingEdgeFound = false;
			for (LinkVariablePattern toEdge : to.getLinkVariablePatterns()) {
				if (fromEdge.getTarget().getName().equals(toEdge.getTarget().getName()) && fromEdge.getType() == toEdge.getType()) {
					matchingEdgeFound = true;
					// Operator 
					mergeOperators(fromEdge, toEdge);
					break;
				}
			}
			if (!matchingEdgeFound) {
				to.getLinkVariablePatterns().add(EcoreUtil.copy(fromEdge));
			}
		}
		
		// Attributes     TODO AttributeAssignment/Constraint merge should be nicer, e.g. combine constraints on the same attribute
		to.getAttributeAssignments().addAll(EcoreUtil.copyAll(from.getAttributeAssignments()));
		to.getAttributeConstraints().addAll(EcoreUtil.copyAll(from.getAttributeConstraints()));
		
	}
	
	private void mergeCorrPatterns(Collection<CorrVariablePattern> patterns) {
		corrPatterns = new HashMap<String, CorrVariablePattern>();

		for (CorrVariablePattern pattern : patterns) {
			if (!corrPatterns.containsKey(pattern.getName())) {
				corrPatterns.put(pattern.getName(), pattern);
			} else {
				mergeTwoCorrPatterns(pattern, corrPatterns.get(pattern.getName()));
			}
		}
	}
	

	private void mergeTwoCorrPatterns(CorrVariablePattern from, CorrVariablePattern to) {
		// TODO check for invalid configurations
		
		// Types
		CorrType superType = from.getType().getSuper();
		while (superType != null) {
			if (superType == to.getType()) {
				to.setType(from.getType());
				break;
			}
			superType = superType.getSuper();
		}
				
		// Operator   
		mergeOperators(from, to);
		
		// Source
		if (to.getSource() == null) {
			to.setSource(from.getSource());
		}
		
		// Target
		if (to.getTarget() == null) {
			to.setTarget(from.getTarget());
		}
	}
	
	private void mergeOperators(OperatorPattern from, OperatorPattern to) {
		if (to.getOp() != null && from.getOp() == null) {
			to.setOp(null);
		}
	}
	
	private void cleanupReferences(Rule rule) {
		Map<String, ObjectVariablePattern> objectPatterns = new HashMap<String, ObjectVariablePattern>();
		objectPatterns.putAll(this.sourcePatterns);
		objectPatterns.putAll(this.targetPatterns);
		
			// CorrespondenceVariablePatterns
		rule.getCorrespondencePatterns().stream()
							            .forEach(c -> {
							            	c.setSource(this.sourcePatterns.get(c.getSource().getName()));
							            	c.setTarget(this.targetPatterns.get(c.getTarget().getName()));
							            });

			// LinkVariablePatterns
		rule.getSourcePatterns().stream()
								.flatMap(s -> s.getLinkVariablePatterns().stream())
								.forEach(l -> l.setTarget(this.sourcePatterns.get(l.getTarget().getName())));

		rule.getTargetPatterns().stream()
								.flatMap(s -> s.getLinkVariablePatterns().stream())
								.forEach(l -> l.setTarget(this.targetPatterns.get(l.getTarget().getName())));
		
			// AttributeExpressions
		rule.getAttrConditions().stream()
								.flatMap(a -> a.getValues().stream())
								.filter(v -> v instanceof AttributeExpression)
								.forEach(a -> ((AttributeExpression)a).setObjectVar(objectPatterns.get(((AttributeExpression)a)
																								  .getObjectVar().getName())));
		
		rule.getSourcePatterns().stream()
								.flatMap(s -> s.getAttributeAssignments().stream())
								.map(a -> a.getValueExp())
								.filter(v -> v instanceof AttributeExpression)
								.forEach(a -> ((AttributeExpression)a).setObjectVar(objectPatterns.get(((AttributeExpression)a)
																								  .getObjectVar().getName())));
		
		rule.getTargetPatterns().stream()
								.flatMap(s -> s.getAttributeAssignments().stream())
								.map(a -> a.getValueExp())
								.filter(v -> v instanceof AttributeExpression)
								.forEach(a -> ((AttributeExpression)a).setObjectVar(objectPatterns.get(((AttributeExpression)a)
																								  .getObjectVar().getName())));
		
		rule.getSourcePatterns().stream()
								.flatMap(s -> s.getAttributeConstraints().stream())
								.map(a -> a.getValueExp())
								.filter(v -> v instanceof AttributeExpression)
								.forEach(a -> ((AttributeExpression)a).setObjectVar(objectPatterns.get(((AttributeExpression)a)
																								  .getObjectVar().getName())));
		
		rule.getTargetPatterns().stream()
								.flatMap(s -> s.getAttributeConstraints().stream())
								.map(a -> a.getValueExp())
								.filter(v -> v instanceof AttributeExpression)
								.forEach(a -> ((AttributeExpression)a).setObjectVar(objectPatterns.get(((AttributeExpression)a)
																								  .getObjectVar().getName())));

	}

}
