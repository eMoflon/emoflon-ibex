package org.emoflon.ibex.tgg.operational.strategies.integrate.provider;

import java.util.Arrays;
import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment.ApplyUserDelta;
import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment.CheckLocalConsistency;
import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment.CleanUp;
import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment.Repair;
import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment.ResolveBrokenMatches;
import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment.ResolveConflicts;
import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment.Translate;
import org.emoflon.ibex.tgg.operational.strategies.integrate.IntegrationFragment.Translate.RevokeBrokenCorrespondences;

public class IntegrationFragmentProvider {

	public static final ApplyUserDelta APPLY_USER_DELTA = new ApplyUserDelta();
	public static final Repair REPAIR = new Repair();
	public static final ResolveConflicts RESOLVE_CONFLICTS = new ResolveConflicts();
	public static final ResolveBrokenMatches RESOLVE_BROKEN_MATCHES = new ResolveBrokenMatches();
	public static final CheckLocalConsistency CHECK_LOCAL_CONSISTENCY = new CheckLocalConsistency();
	public static final Translate TRANSLATE = new Translate();
	public static final RevokeBrokenCorrespondences REVOKE_BROKEN_CORRESPONDENCES = new RevokeBrokenCorrespondences();
	public static final CleanUp CLEAN_UP = new CleanUp();

	private static final List<IntegrationFragment> DEFAULT_FRAGMENTS = Arrays.asList( //
			APPLY_USER_DELTA, //
			REPAIR, //
			CHECK_LOCAL_CONSISTENCY, //
			RESOLVE_CONFLICTS, //
			RESOLVE_BROKEN_MATCHES, //
			TRANSLATE, //
			CLEAN_UP //
	);

	public static List<IntegrationFragment> getDefaultIntegrationFragments() {
		return DEFAULT_FRAGMENTS;
	}

}
