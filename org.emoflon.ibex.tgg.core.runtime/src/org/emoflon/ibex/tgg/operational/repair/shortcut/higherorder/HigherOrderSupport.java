package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;

import language.TGGRuleElement;
import language.TGGRuleNode;

public class HigherOrderSupport {

	public static TGGRuleElement getHigherOrderElement(HigherOrderRuleComponent component, TGGRuleElement element) {
		ComponentSpecificRuleElement componentSpecRuleElt = component.getComponentSpecificRuleElement(element);
		return componentSpecRuleElt.getRespectiveHigherOrderElement();
	}

	public static String findEntryNodeName(HigherOrderTGGRule higherOrderRule, String tggRuleNodeName) {
		HigherOrderRuleComponent closureComponent = higherOrderRule.getClosureComponent();
		TGGRuleNode ruleNode = closureComponent.getNodeFromName(tggRuleNodeName);
		ComponentSpecificRuleElement componentSpecRuleElt = closureComponent.getComponentSpecificRuleElement(ruleNode);
		return componentSpecRuleElt.getRespectiveHigherOrderElement().getName();
	}

	public static String getKeyRuleName(HigherOrderTGGRule higherOrderRule) {
		return higherOrderRule.getClosureComponent().rule.getName();
	}

}
