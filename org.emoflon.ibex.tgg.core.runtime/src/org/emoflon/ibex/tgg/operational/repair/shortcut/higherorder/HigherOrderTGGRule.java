package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import language.TGGRule;
import language.TGGRuleNode;

public class HigherOrderTGGRule {

	private List<TGGRule> rules;
	private List<Map<TGGRuleNode, TGGRuleNode>> contextMappings;

	public HigherOrderTGGRule() {
		this.rules = new LinkedList<>();
		this.contextMappings = new LinkedList<>();
	}

	public void addRule(TGGRule rule, Map<TGGRuleNode, TGGRuleNode> contextMapping) {
		rules.add(rule);
		contextMappings.add(contextMapping);
	}

	public List<TGGRule> getRules() {
		return rules;
	}

	public List<Map<TGGRuleNode, TGGRuleNode>> getContextMappings() {
		return contextMappings;
	}

}
