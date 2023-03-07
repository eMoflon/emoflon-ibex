package org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder;

import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

public class HigherOrderSupport {

	@SuppressWarnings("unchecked")
	public static <E extends TGGRuleElement> E getHigherOrderElement(HigherOrderRuleComponent component, E element) {
		ComponentSpecificRuleElement componentSpecRuleElt = component.getComponentSpecificRuleElement(element);
		if (componentSpecRuleElt == null)
			return null;
		return (E) componentSpecRuleElt.getRespectiveHigherOrderElement();
	}

	public static String findHigherOrderNodeName(HigherOrderRuleComponent component, String tggRuleNodeName) {
		TGGNode ruleNode = component.getNodeFromName(tggRuleNodeName);
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
