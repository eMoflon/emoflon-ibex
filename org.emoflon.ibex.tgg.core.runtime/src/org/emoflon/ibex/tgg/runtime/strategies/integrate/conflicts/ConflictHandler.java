package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.runtime.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.runtime.benchmark.Timer;
import org.emoflon.ibex.tgg.runtime.benchmark.Times;
import org.emoflon.ibex.tgg.runtime.debug.LoggerConfig;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.detection.ConflictDetector;

public class ConflictHandler implements TimeMeasurable {

	private final INTEGRATE opStrat;

	private final ConflictDetector detector;

	private Set<ConflictContainer> conflicts;
	private Map<ITGGMatch, ConflictContainer> match2conflicts;

	private final Times times;

	public ConflictHandler(INTEGRATE opStrat) {
		this.opStrat = opStrat;
		this.detector = new ConflictDetector(opStrat);
		this.clear();

		this.times = new Times();
	}

	public void detectConflicts() {
		opStrat.getOptions().matchDistributor().updateMatches();

		Timer.start();

		match2conflicts = this.detector.detectConflicts();
		conflicts = buildContainerHierarchy(match2conflicts);

		times.addTo("detectConflicts", Timer.stop());

		if (!match2conflicts.isEmpty())
			LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "");
	}

	public void resolveConflicts() {
		Timer.start();

		for (ConflictContainer c : conflicts)
			opStrat.getOptions().integration.conflictSolver().resolveConflict(c);

		times.addTo("resolveConflicts", Timer.stop());

		if (!conflicts.isEmpty())
			LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "");
	}

	public void detectAndResolveOpMultiplicityConflicts() {
		opStrat.getOptions().matchDistributor().updateMatches();

		Timer.start();

		Set<ConflictContainer> opMultiConflicts = buildContainerHierarchy(detector.detectOpMultiplicityConflicts());

		times.addTo("detectOpMultiConflicts", Timer.stop());
		Timer.start();

		for (ConflictContainer c : opMultiConflicts)
			opStrat.getOptions().integration.conflictSolver().resolveConflict(c);

		times.addTo("resolveOpMultiConflicts", Timer.stop());

		if (!opMultiConflicts.isEmpty())
			LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "");
	}

	private Set<ConflictContainer> buildContainerHierarchy(Map<ITGGMatch, ConflictContainer> match2conflicts) {
		Set<ConflictContainer> conflicts = new HashSet<>(match2conflicts.values());
		for (ITGGMatch match : match2conflicts.keySet()) {
			opStrat.precedenceGraph().getNode(match).forAllRequiredBy((n, pre) -> {
				ITGGMatch m = n.getMatch();
				if (match2conflicts.containsKey(m)) {
					ConflictContainer cc = match2conflicts.get(m);
					match2conflicts.get(match).getSubContainers().add(cc);
					conflicts.remove(cc);
					return false;
				}
				return true;
			});
		}
		return conflicts;
	}

	public Map<ITGGMatch, ConflictContainer> getConflicts() {
		return match2conflicts;
	}

	public void clear() {
		conflicts = new HashSet<>();
		match2conflicts = new HashMap<>();
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
