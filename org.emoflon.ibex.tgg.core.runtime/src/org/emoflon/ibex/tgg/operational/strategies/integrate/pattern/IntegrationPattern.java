package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.provider.IntegrationFragmentProvider;

public class IntegrationPattern {

	private final List<DeletionType> deletionTypes;
	private final List<IntegrationFragment> fragments;

	public IntegrationPattern(List<IntegrationFragment> fragments, List<DeletionType> matchClassifier) {
		this.fragments = fragments;
		this.deletionTypes = matchClassifier;
	}
	
	public IntegrationPattern(List<IntegrationFragment> fragments) {
		this.fragments = fragments;
		this.deletionTypes = DeletionType.getDefaultDeletionTypes();
	}

	public IntegrationPattern() {
		this.fragments = IntegrationFragmentProvider.getDefaultIntegrationFragments();
		this.deletionTypes = DeletionType.getDefaultDeletionTypes();
	}

	public List<DeletionType> getDeletionTypes() {
		return deletionTypes;
	}

	public List<IntegrationFragment> getIntegrationFragments() {
		return fragments;
	}

}
