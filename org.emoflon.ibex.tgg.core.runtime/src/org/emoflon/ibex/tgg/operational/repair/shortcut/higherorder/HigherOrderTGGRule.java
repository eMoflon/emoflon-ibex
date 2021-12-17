package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.util.ConsoleUtil;

import language.TGGRule;
import language.TGGRuleElement;

public class HigherOrderTGGRule {

	private final LinkedList<HigherOrderRuleComponent> components;
	private final Map<ITGGMatch, HigherOrderRuleComponent> match2component;

	public HigherOrderTGGRule() {
		this.components = new LinkedList<>();
		this.match2component = new HashMap<>();
	}

	public void addComponentFirst(TGGRule rule, ITGGMatch match, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
		HigherOrderRuleComponent component = new HigherOrderRuleComponent(rule, contextMapping);
		components.addFirst(component);
		match2component.put(match, component);
	}

	public void addComponentLast(TGGRule rule, ITGGMatch match, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
		HigherOrderRuleComponent component = new HigherOrderRuleComponent(rule, contextMapping);
		components.addLast(component);
		match2component.put(match, component);
	}

	public List<HigherOrderRuleComponent> getComponents() {
		return components;
	}

	public HigherOrderRuleComponent getComponent(ITGGMatch match) {
		return match2component.get(match);
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
		public final Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping;

		private HigherOrderRuleComponent(TGGRule rule, Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping) {
			this.rule = rule;
			this.contextMapping = contextMapping;
		}
	}

	public static class ComponentSpecificRuleElement {
		public final TGGRuleElement ruleElement;
		public final HigherOrderRuleComponent component;

		public ComponentSpecificRuleElement(TGGRuleElement ruleElement, HigherOrderRuleComponent component) {
			this.ruleElement = ruleElement;
			this.component = component;
		}
	}

}
