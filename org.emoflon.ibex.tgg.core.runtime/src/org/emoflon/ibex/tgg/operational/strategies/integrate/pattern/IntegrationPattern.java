package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassCompProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;

public class IntegrationPattern {

	private final List<MatchClassificationComponent> mcComponents;
	private final List<IntegrationFragment> fragments;

	public IntegrationPattern(List<MatchClassificationComponent> mcComponents,
			List<IntegrationFragment> fragments) {
		this.mcComponents = mcComponents;
		this.fragments = fragments;
	}

	public IntegrationPattern() {
		mcComponents = MatchClassCompProvider.getDefaultMCCs();
		fragments = IntegrationFragmentProvider.getDefaultIntegrationFragments();
	}

	public List<MatchClassificationComponent> getMCComponents() {
		return mcComponents;
	}

	public List<IntegrationFragment> getIntegrationFragments() {
		return fragments;
	}
	
}
