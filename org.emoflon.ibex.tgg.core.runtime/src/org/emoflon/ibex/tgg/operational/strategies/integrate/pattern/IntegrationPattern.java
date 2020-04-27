package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.provider.IntegrationFragmentProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.provider.MatchClassifierProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;

public class IntegrationPattern {

	private final List<MatchClassifier> matchClassifier;
	private final List<IntegrationFragment> fragments;

	public IntegrationPattern(List<IntegrationFragment> fragments, List<MatchClassifier> matchClassifier) {
		this.fragments = fragments;
		this.matchClassifier = matchClassifier;
	}
	
	public IntegrationPattern(List<IntegrationFragment> fragments) {
		this.fragments = fragments;
		this.matchClassifier = MatchClassifierProvider.getDefaultMatchClassifier();
	}

	public IntegrationPattern() {
		this.fragments = IntegrationFragmentProvider.getDefaultIntegrationFragments();
		this.matchClassifier = MatchClassifierProvider.getDefaultMatchClassifier();
	}

	public List<MatchClassifier> getMatchClassifier() {
		return matchClassifier;
	}

	public List<IntegrationFragment> getIntegrationFragments() {
		return fragments;
	}

}
