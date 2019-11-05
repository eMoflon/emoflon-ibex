package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.util.ArrayList;
import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MCCProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.IntegrationFragment;

public class IntegrationPattern {

	private final List<MatchClassificationComponent> mcComponents;
	private final List<IntegrationFragment> fragments;

	public IntegrationPattern(List<MatchClassificationComponent> mcComponents,
			List<IntegrationFragment> fragments) {
		this.mcComponents = mcComponents;
		this.fragments = fragments;
	}

	public IntegrationPattern() {
		mcComponents = MCCProvider.getDefaultMCCs();
		fragments = new ArrayList<>();
	}

	public List<MatchClassificationComponent> getMCComponents() {
		return mcComponents;
	}

	public List<IntegrationFragment> getIntegrationFragments() {
		return fragments;
	}
	
}
