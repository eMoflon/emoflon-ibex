package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.LinkedList;
import java.util.Map;

import org.emoflon.ibex.tgg.util.ConsoleUtil;

import language.TGGRule;
import language.TGGRuleNode;

public class HigherOrderTGGRule {

	private final LinkedList<HigherOrderRuleComponent> components;

	public HigherOrderTGGRule() {
		this.components = new LinkedList<>();
	}

	public void addComponentFirst(TGGRule rule, Map<TGGRuleNode, ComponentSpecificRuleNode> contextMapping) {
		components.addFirst(new HigherOrderRuleComponent(rule, contextMapping));
	}

	public void addComponentLast(TGGRule rule, Map<TGGRuleNode, ComponentSpecificRuleNode> contextMapping) {
		components.addLast(new HigherOrderRuleComponent(rule, contextMapping));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HigherOrderTGGRule [\n");
		for (HigherOrderRuleComponent component : components) {
			builder.append(ConsoleUtil.indent(component.rule.getName(), 2, true));
			builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}

	public class HigherOrderRuleComponent {
		public final TGGRule rule;
		public final Map<TGGRuleNode, ComponentSpecificRuleNode> contextMapping;

		private HigherOrderRuleComponent(TGGRule rule, Map<TGGRuleNode, ComponentSpecificRuleNode> contextMapping) {
			this.rule = rule;
			this.contextMapping = contextMapping;
		}
	}

	public class ComponentSpecificRuleNode {
		public final TGGRuleNode ruleNode;
		public final HigherOrderRuleComponent component;

		public ComponentSpecificRuleNode(TGGRuleNode ruleNode, HigherOrderRuleComponent component) {
			this.ruleNode = ruleNode;
			this.component = component;
		}
	}

}
