package org.emoflon.ibex.tgg.runtime.strategies.integrate.pattern;

import java.util.List;

import org.emoflon.ibex.tgg.runtime.strategies.integrate.FragmentProvider;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.DeletionType;

public class IntegrationPattern {

	private final List<DeletionType> deletionTypes;
	private final List<IntegrationFragment> fragments;

	public IntegrationPattern(List<IntegrationFragment> fragments, List<DeletionType> matchClassifier) {
		this.fragments = fragments;
		this.deletionTypes = matchClassifier;
	}
	
	public IntegrationPattern(List<IntegrationFragment> fragments) {
		this.fragments = fragments;
		this.deletionTypes = DeletionType.defaultTypes;
	}

	public IntegrationPattern() {
		this.fragments = FragmentProvider.DEFAULT_FRAGMENTS;
		this.deletionTypes = DeletionType.defaultTypes;
	}

	public List<DeletionType> getDeletionTypes() {
		return deletionTypes;
	}

	public List<IntegrationFragment> getIntegrationFragments() {
		return fragments;
	}

}
