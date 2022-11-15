package org.emoflon.ibex.tgg.runtime.patterns;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.util.config.IbexOptions;

public class GreenPatternFactoryProvider {

	private final IbexOptions options;

	private final Map<String, IGreenPatternFactory> factories = cfactory.createObjectToObjectHashMap();

	public GreenPatternFactoryProvider(IbexOptions options) {
		this.options = options;
	}

	public synchronized IGreenPatternFactory get(String ruleName) {
		assert (ruleName != null);
		if (!factories.containsKey(ruleName)) {
			factories.put(ruleName, new GreenPatternFactory(options, ruleName));
		}
		return factories.get(ruleName);
	}

	public IGreenPatternFactory get(ITGGMatch match) {
		return get(PatternSuffixes.removeSuffix(match.getPatternName()));
	}

	public Map<String, IGreenPatternFactory> getAll() {
		return factories;
	}

}
