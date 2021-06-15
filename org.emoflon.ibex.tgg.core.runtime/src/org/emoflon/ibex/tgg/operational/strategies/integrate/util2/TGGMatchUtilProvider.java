package org.emoflon.ibex.tgg.operational.strategies.integrate.util2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;

import language.TGGRule;

public class TGGMatchUtilProvider {

	private final INTEGRATE integrate;

	private Map<String, TGGRule> name2rule;
	private Map<ITGGMatch, TGGMatchUtil> match2util;

	public TGGMatchUtilProvider(INTEGRATE integrate) {
		this.integrate = integrate;
		init();
	}

	private void init() {
		name2rule = integrate.getOptions().tgg.getFlattenedConcreteTGGRules().stream() //
				.collect(Collectors.toMap(rule -> rule.getName(), rule -> rule));
		match2util = Collections.synchronizedMap(new HashMap<>());
	}

	public TGGMatchUtil get(ITGGMatch match) {
		TGGMatchUtil util = match2util.get(match);
		if (util == null) {
			util = new TGGMatchUtil(integrate, match, name2rule.get(match.getRuleName()));
			match2util.put(match, util);
		}
		return util;
	}

}
