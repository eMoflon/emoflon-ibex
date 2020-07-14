package org.emoflon.ibex.tgg.operational.matches;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;

import language.TGG;
import language.TGGRule;

public class TGGMatchParameterOrderProvider {

	private static Map<String, LinkedList<String>> ruleName2params = null;

	public static void init(TGG tgg) {
		ruleName2params = new HashMap<>();
		for (TGGRule rule : tgg.getRules()) {
			if(rule.isAbstract())
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
		}
	}

	public static LinkedList<String> getParams(String ruleName) {
		return ruleName2params.get(ruleName);
	}

}
