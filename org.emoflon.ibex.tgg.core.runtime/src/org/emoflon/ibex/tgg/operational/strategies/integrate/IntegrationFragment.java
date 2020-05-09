package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.GeneralConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol.ChangeKey;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

public interface IntegrationFragment {

	void apply(INTEGRATE integrate) throws IOException;

	//// INTEGRATION FRAGMENTS ////

	public static class ApplyUserDelta implements IntegrationFragment {
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
		@Override
		public void apply(INTEGRATE i) throws IOException {
			do {
				i.classifyBrokenMatches();
				i.detectConflicts();
				i.translateConflictFreeElements();
			} while (i.repairBrokenMatches());
		}
	}

	public static class ResolveConflicts implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.classifyBrokenMatches();
			i.detectConflicts();
			for (GeneralConflict c : i.conflicts) {
				i.getOptions().integration.conflictSolver().resolveConflict(c).forEach(crs -> crs.apply(i));
			}
		}
	}

	public static class ResolveBrokenMatches implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			do {
				i.classifyBrokenMatches();
				i.resolveBrokenMatches();
			} while (!i.getBrokenMatches().isEmpty());
		}
	}

	public static class CheckLocalConsistency implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			ChangeKey key = i.revokeBrokenCorrsAndRuleApplNodes();
			i.consistencyChecker.run();
			i.restoreBrokenCorrsAndRuleApplNodes(key);
		}
	}

	public static class Translate implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.classifyBrokenMatches();
			i.detectConflicts();
			i.translateConflictFreeElements();
		}

	}

	public static class RevokeBrokenCorrespondences implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.revokeBrokenCorrsAndRuleApplNodes();
		}
	}

	public static class CleanUp implements IntegrationFragment {

		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.revokeBrokenCorrsAndRuleApplNodes();
			i.clearBrokenRuleApplications();
			i.revokeUntranslatedElements();
		}

	}

}
