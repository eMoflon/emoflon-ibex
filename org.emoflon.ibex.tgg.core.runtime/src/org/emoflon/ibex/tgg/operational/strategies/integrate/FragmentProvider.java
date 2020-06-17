package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol.ChangeKey;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.pattern.IntegrationFragment;

public class FragmentProvider {

	public static final ApplyUserDelta APPLY_USER_DELTA = new ApplyUserDelta();
	public static final Repair REPAIR = new Repair();
	public static final ResolveConflicts RESOLVE_CONFLICTS = new ResolveConflicts();
	public static final ResolveBrokenMatches RESOLVE_BROKEN_MATCHES = new ResolveBrokenMatches();
	public static final CheckLocalConsistency CHECK_LOCAL_CONSISTENCY = new CheckLocalConsistency();
	public static final Translate TRANSLATE = new Translate();
	public static final RevokeBrokenCorrespondences REVOKE_BROKEN_CORRESPONDENCES = new RevokeBrokenCorrespondences();
	public static final CleanUp CLEAN_UP = new CleanUp();

	public static final List<IntegrationFragment> DEFAULT_FRAGMENTS = Arrays.asList( //
			APPLY_USER_DELTA, //
			REPAIR, //
//			CHECK_LOCAL_CONSISTENCY, //
			RESOLVE_CONFLICTS, //
			RESOLVE_BROKEN_MATCHES, //
			TRANSLATE, //
			CLEAN_UP //
	);
	
	//// INTEGRATION FRAGMENTS ////

	public static class ApplyUserDelta implements IntegrationFragment {
		private ApplyUserDelta() {
		}
		
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.modelChangeProtocol.registerKey(i.userDeltaKey);

			if (i.userDeltaContainer != null) {
				ModelChangeUtil.applyUserDelta(i.userDeltaContainer);
				i.userDeltaContainer = null;
			} else if (i.userDeltaBiConsumer != null) {
				i.userDeltaBiConsumer.accept(i.getResourceHandler().getSourceResource().getContents().get(0),
						i.getResourceHandler().getTargetResource().getContents().get(0));
				i.userDeltaBiConsumer = null;
			}

			i.modelChangeProtocol.deregisterKey(i.userDeltaKey);
		}
	}

	public static class Repair implements IntegrationFragment {
		private Repair() {
		}
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.classifyBrokenMatches();
			i.detectConflicts();
			i.translateConflictFreeElements();
			i.repairBrokenMatches();
		}
	}

	public static class ResolveConflicts implements IntegrationFragment {
		private ResolveConflicts() {
		}
		
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.classifyBrokenMatches();
			i.detectConflicts();
			for (ConflictContainer c : i.conflicts) {
				i.getOptions().integration.conflictSolver().resolveConflict(c);
			}
		}
	}

	public static class ResolveBrokenMatches implements IntegrationFragment {
		private ResolveBrokenMatches() {
		}
		
		@Override
		public void apply(INTEGRATE i) throws IOException {
			do {
				i.classifyBrokenMatches();
				i.resolveBrokenMatches();
			} while (!i.getClassifiedBrokenMatches().isEmpty());
		}
	}

	public static class CheckLocalConsistency implements IntegrationFragment {
		private CheckLocalConsistency() {
		}
		
		@Override
		public void apply(INTEGRATE i) throws IOException {
			ChangeKey key = i.revokeBrokenCorrsAndRuleApplNodes();
			i.consistencyChecker.run();
			i.restoreBrokenCorrsAndRuleApplNodes(key);
		}
	}

	public static class Translate implements IntegrationFragment {
		private Translate() {
		}
		
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.classifyBrokenMatches();
			i.detectConflicts();
			i.translateConflictFreeElements();
		}

	}

	public static class RevokeBrokenCorrespondences implements IntegrationFragment {
		private RevokeBrokenCorrespondences() {
		}
		
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.revokeBrokenCorrsAndRuleApplNodes();
		}
	}

	public static class CleanUp implements IntegrationFragment {
		private CleanUp() {
		}
		
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.revokeBrokenCorrsAndRuleApplNodes();
			i.clearBrokenRuleApplications();
			i.revokeUntranslatedElements();
		}
	}

}
