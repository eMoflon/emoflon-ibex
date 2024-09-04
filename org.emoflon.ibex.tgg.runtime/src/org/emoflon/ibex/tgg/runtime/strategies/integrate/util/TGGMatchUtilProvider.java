package org.emoflon.ibex.tgg.runtime.strategies.integrate.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

public class TGGMatchUtilProvider {

	private final INTEGRATE integrate;

	private Map<String, TGGRule> name2rule;
	private Map<ITGGMatch, TGGMatchUtil> match2util;

	public TGGMatchUtilProvider(INTEGRATE integrate) {
		this.integrate = integrate;
		init();
	}

	private void init() {
		name2rule = integrate.getOptions().tgg.getConcreteTGGRules().stream() //
				.collect(Collectors.toMap(rule -> rule.getName(), rule -> rule));
		match2util = Collections.synchronizedMap(new HashMap<>());
	}

	public TGGMatchUtil get(ITGGMatch match) {
		return match2util.computeIfAbsent(match, k -> new TGGMatchUtil(integrate, match, name2rule.get(match.getRuleName())));
	}

}
