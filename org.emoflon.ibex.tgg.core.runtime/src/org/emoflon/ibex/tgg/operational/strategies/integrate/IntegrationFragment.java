package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

public interface IntegrationFragment {

	void apply(INTEGRATE integrate) throws IOException;

	//// INTEGRATION FRAGMENTS ////

	public static class ApplyUserDelta implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			if (i.userDeltaContainer != null) {
				ModelChangeUtil.applyUserDelta(i.userDeltaContainer);
				i.userDeltaContainer = null;
			} else if (i.userDeltaBiConsumer != null) {
				i.userDeltaBiConsumer.accept(i.getResourceHandler().getSourceResource().getContents().get(0),
						i.getResourceHandler().getTargetResource().getContents().get(0));
				i.userDeltaBiConsumer = null;
			}
		}
	}

	public static class Repair implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			do {
				i.classifyMatches();
				i.detectConflicts();
				i.translateConflictFreeElements();
			} while (i.repairBrokenMatches());
		}
	}

	public static class ResolveConflicts implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.classifyMatches();
			i.detectConflicts();
			i.conflicts.values().forEach(conflict -> {
				i.getOptions().getConflictSolver().resolveDeleteConflict(conflict).apply(i);
				i.conflicts.remove(conflict.getMatch());
			});
		}
	}

	public static class ResolveBrokenMatches implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			do {
				i.classifyMatches();
				i.resolveMismatches();
			} while (!i.getMismatches().isEmpty());
		}
	}

	public static class CheckLocalConsistency implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.consistencyChecker.run();
		}
	}

	public static class Translate implements IntegrationFragment {
		@Override
		public void apply(INTEGRATE i) throws IOException {
			i.classifyMatches();
			i.detectConflicts();
			i.translateConflictFreeElements();
		}

		public static class RevokeBrokenCorrespondences implements IntegrationFragment {
			@Override
			public void apply(INTEGRATE i) throws IOException {
				i.revokeBrokenCorrs();
			}
		}
	}

	public static class CleanUp implements IntegrationFragment {

		@Override
		public void apply(INTEGRATE integrate) throws IOException {
			// TODO adrianm: implement
			// delete all elements that haven't been translated
		}

	}

}
