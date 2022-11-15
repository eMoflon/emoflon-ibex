package org.emoflon.ibex.tgg.runtime.matches;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;

import language.TGG;
import language.TGGRule;
import language.TGGRuleNode;

public class TGGMatchParameterOrderProvider {

	private static Map<String, LinkedList<String>> ruleName2params = new HashMap<>();
	private static Map<String, Map<String, TGGRuleNode>> ruleName2param2node = new HashMap<>();
	private static boolean initialized = false;

	public static void init(TGG tgg) {
		ruleName2params = new HashMap<>();
		ruleName2param2node = new HashMap<>();

		for (TGGRule rule : tgg.getRules()) {
			if (rule.isAbstract())
				continue;

			LinkedList<String> params = new LinkedList<>();
			params.addAll(rule.getNodes().stream() //
					.map(n -> n.getName()) //
					.collect(Collectors.toList()));
			params.addAll(rule.getEdges().stream() //
					.map(e -> e.getName()) //
					.collect(Collectors.toList()));
			params.add(TGGPatternUtil.getProtocolNodeName(rule.getName()));
			ruleName2params.put(rule.getName(), params);

			Map<String, TGGRuleNode> param2node = rule.getNodes().stream() //
					.collect(Collectors.toMap(n -> n.getName(), n -> n));
			ruleName2param2node.put(rule.getName(), param2node);
		}

		initialized = true;
	}

	public static LinkedList<String> getParams(String ruleName) {
		return ruleName2params.get(ruleName);
	}

	public static Map<String, TGGRuleNode> getParam2NodeMap(String ruleName) {
		return ruleName2param2node.get(ruleName);
	}

	public static boolean isInitialized() {
		return initialized;
	}

}
