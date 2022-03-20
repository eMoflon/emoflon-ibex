package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;

import language.TGGRuleElement;
import language.TGGRuleNode;

public class HigherOrderSupport {

	public static TGGRuleElement getHigherOrderElement(HigherOrderRuleComponent component, TGGRuleElement element) {
		ComponentSpecificRuleElement componentSpecRuleElt = component.getComponentSpecificRuleElement(element);
		if (componentSpecRuleElt == null)
			return null;
		return componentSpecRuleElt.getRespectiveHigherOrderElement();
	}

	public static String findEntryNodeName(HigherOrderTGGRule higherOrderRule, String tggRuleNodeName) {
		HigherOrderRuleComponent closureComponent = higherOrderRule.getClosureComponent();
		TGGRuleNode ruleNode = closureComponent.getNodeFromName(tggRuleNodeName);
		if (ruleNode == null)
			return null;
		ComponentSpecificRuleElement componentSpecRuleElt = closureComponent.getComponentSpecificRuleElement(ruleNode);
		if (componentSpecRuleElt == null)
			return null;
		return componentSpecRuleElt.getRespectiveHigherOrderElement().getName();
	}

	public static String getKeyRuleName(HigherOrderTGGRule higherOrderRule) {
		return higherOrderRule.getClosureComponent().rule.getName();
	}

}
