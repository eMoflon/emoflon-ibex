package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

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

	private static final ApplyUserDelta APPLY_USER_DELTA = new ApplyUserDelta();
	private static final Repair REPAIR = new Repair();
	private static final ResolveConflicts RESOLVE_CONFLICTS = new ResolveConflicts();
	private static final ResolveBrokenMatches RESOLVE_BROKEN_MATCHES = new ResolveBrokenMatches();
	private static final CheckLocalConsistency CHECK_LOCAL_CONSISTENCY = new CheckLocalConsistency();
	private static final Translate TRANSLATE = new Translate();
	private static final RevokeBrokenCorrespondences REVOKE_BROKEN_CORRESPONDENCES = new RevokeBrokenCorrespondences();
	private static final CleanUp CLEAN_UP = new CleanUp();

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

	public static ApplyUserDelta getApplyUserDelta() {
		return APPLY_USER_DELTA;
	}

	public static Repair getRepair() {
		return REPAIR;
	}

	public static ResolveConflicts getResolveConflicts() {
		return RESOLVE_CONFLICTS;
	}

	public static ResolveBrokenMatches getResolveBrokenMatches() {
		return RESOLVE_BROKEN_MATCHES;
	}

	public static CheckLocalConsistency getCheckLocalConsistency() {
		return CHECK_LOCAL_CONSISTENCY;
	}

	public static Translate getTranslate() {
		return TRANSLATE;
	}

	public static RevokeBrokenCorrespondences getRevokeBrokenCorrespondences() {
		return REVOKE_BROKEN_CORRESPONDENCES;
	}

	public static CleanUp getCleanUp() {
		return CLEAN_UP;
	}

}
