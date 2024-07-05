package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;

import language.TGGRuleElement;
import language.TGGRuleNode;

public class HigherOrderSupport {

	@SuppressWarnings("unchecked")
	public static <E extends TGGRuleElement> E getHigherOrderElement(HigherOrderRuleComponent component, E element) {
		ComponentSpecificRuleElement componentSpecRuleElt = component.getComponentSpecificRuleElement(element);
		if (componentSpecRuleElt == null)
			return null;
		return (E) componentSpecRuleElt.getRespectiveHigherOrderElement();
	}

	public static String findHigherOrderNodeName(HigherOrderRuleComponent component, String tggRuleNodeName) {
		TGGRuleNode ruleNode = component.getNodeFromName(tggRuleNodeName);
		if (ruleNode == null)
			return null;
		ComponentSpecificRuleElement componentSpecRuleElt = component.getComponentSpecificRuleElement(ruleNode);
		if (componentSpecRuleElt == null)
			return null;
		return componentSpecRuleElt.getRespectiveHigherOrderElement().getName();
	}

	public static String getKeyRuleName(HigherOrderTGGRule higherOrderRule) {
		return higherOrderRule.getClosureComponent().rule.getName();
	}

}
