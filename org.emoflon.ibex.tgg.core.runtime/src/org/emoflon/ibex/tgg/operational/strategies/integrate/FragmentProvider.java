package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol.ChangeKey;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationFragment;

public class FragmentProvider {

	public static final Repair REPAIR = new Repair();
	public static final ResolveConflicts RESOLVE_CONFLICTS = new ResolveConflicts();
	public static final ResolveBrokenMatches RESOLVE_BROKEN_MATCHES = new ResolveBrokenMatches();
	public static final CheckLocalConsistency CHECK_LOCAL_CONSISTENCY = new CheckLocalConsistency();
	public static final Translate TRANSLATE = new Translate();
	public static final RevokeBrokenCorrespondences REVOKE_BROKEN_CORRESPONDENCES = new RevokeBrokenCorrespondences();
	public static final CleanUp CLEAN_UP = new CleanUp();

	public static final List<IntegrationFragment> DEFAULT_FRAGMENTS = Arrays.asList( //
			REPAIR, //
			CHECK_LOCAL_CONSISTENCY, //
			RESOLVE_CONFLICTS, //
			RESOLVE_BROKEN_MATCHES, //
			TRANSLATE, //
			CLEAN_UP //
	);

	//// INTEGRATION FRAGMENTS ////

	public static class Repair implements IntegrationFragment {
		private Repair() {
		}

		@Override
		public void apply(INTEGRATE i) throws IOException {
			TRANSLATE.apply(i);

			FragmentProvider.logFragmentStart(this);
			Timer.start();

			i.repairer().repairBrokenMatches();

			i.getTimes().addTo("fragments:Repair", Timer.stop());
		}
	}

	public static class ResolveConflicts implements IntegrationFragment {
		private ResolveConflicts() {
		}

		@Override
		public void apply(INTEGRATE i) throws IOException {
			FragmentProvider.logFragmentStart(this);
			Timer.start();

			i.classifyBrokenMatches(true);
			i.detectConflicts();
			i.resolveConflicts();

			i.getTimes().addTo("fragments:ResolveConflicts", Timer.stop());
		}
	}

	public static class ResolveBrokenMatches implements IntegrationFragment {
		private ResolveBrokenMatches() {
		}

		@Override
		public void apply(INTEGRATE i) throws IOException {
			FragmentProvider.logFragmentStart(this);
			Timer.start();

			do {
				i.classifyBrokenMatches(false);
				i.resolveBrokenMatches();
			} while (!i.getBrokenRuleApplications().isEmpty());

			i.getTimes().addTo("fragments:ResolveBrokenMatches", Timer.stop());
		}
	}

	public static class CheckLocalConsistency implements IntegrationFragment {
		private CheckLocalConsistency() {
		}

		@Override
		public void apply(INTEGRATE i) throws IOException {
			FragmentProvider.logFragmentStart(this);
			Timer.start();

			ChangeKey key = i.revokeBrokenCorrsAndRuleApplNodes();
			i.consistencyChecker.run();
			i.consistencyChecker.terminate();
			i.restoreBrokenCorrsAndRuleApplNodes(key);

			i.getTimes().addTo("fragments:LocalCC", Timer.stop());
		}
	}

	public static class Translate implements IntegrationFragment {
		private Translate() {
		}

		@Override
		public void apply(INTEGRATE i) throws IOException {
			FragmentProvider.logFragmentStart(this);
			Timer.start();

			i.classifyBrokenMatches(true);
			i.detectAndResolveOpMultiplicityConflicts();
			i.translateConflictFree();

			i.getTimes().addTo("fragments:Translate", Timer.stop());
		}

	}

	public static class RevokeBrokenCorrespondences implements IntegrationFragment {
		private RevokeBrokenCorrespondences() {
		}

		@Override
		public void apply(INTEGRATE i) throws IOException {
			FragmentProvider.logFragmentStart(this);
			Timer.start();

			i.revokeBrokenCorrsAndRuleApplNodes();

			i.getTimes().addTo("fragments:RevokeBrokenCorrs", Timer.stop());
		}
	}

	public static class CleanUp implements IntegrationFragment {
		private CleanUp() {
		}

		@Override
		public void apply(INTEGRATE i) throws IOException {
			FragmentProvider.logFragmentStart(this);
			Timer.start();

			i.revokeBrokenCorrsAndRuleApplNodes();
			i.clearBrokenRuleApplications();
			i.revokeUntranslatedElements();

			i.getTimes().addTo("fragments:CleanUp", Timer.stop());
		}
	}

	private static void logFragmentStart(IntegrationFragment fragment) {
		LoggerConfig.log(LoggerConfig.log_executionStructure(), () -> "Run FRAGMENT " + fragment.getClass().getSimpleName() + ":\n");
	}

}
