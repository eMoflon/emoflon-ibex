package org.emoflon.moflontohenshin.manipulationrules.noderules;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Rule;

import language.TGGRuleNode;

public class RuleNodeCreationRule extends NodeCreationRule {



	public RuleNodeCreationRule() {
		super(HenshinFactory.eINSTANCE.createRule().eClass());
	}

	@Override
	public EObject forceCreation(TGGRuleNode node) {
		Rule rule = HenshinFactory.eINSTANCE.createRule(node.getName());
		rule.getLhs();
		rule.getRhs();
		
		return rule;
	}

}
