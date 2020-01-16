package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifierProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;

public class IntegrationPattern {

	private final List<MatchClassifier> mcComponents;
	private final List<IntegrationFragment> fragments;

	public IntegrationPattern(List<MatchClassifier> mcComponents,
			List<IntegrationFragment> fragments) {
		this.mcComponents = mcComponents;
		this.fragments = fragments;
	}

	public IntegrationPattern() {
		mcComponents = MatchClassifierProvider.getDefaultMCCs();
		fragments = IntegrationFragmentProvider.getDefaultIntegrationFragments();
	}

	public List<MatchClassifier> getMCComponents() {
		return mcComponents;
	}

	public List<IntegrationFragment> getIntegrationFragments() {
		return fragments;
	}
	
}
