package org.emoflon.ibex.tgg.operational.strategies.matchhandling;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.modules.MatchDistributor;
import org.emoflon.ibex.tgg.util.ConsoleUtil;

import com.google.common.collect.Sets;

import runtime.TGGRuleApplication;

public class ConsistencyMatchHandler extends MatchConsumer {

	private final IMatchContainer operationalMatchContainer;

	private final Map<TGGRuleApplication, ITGGMatch> ra2consMatches = cfactory.createObjectToObjectHashMap();
	private final Map<TGGRuleApplication, ITGGMatch> brokenRA2consMatches = cfactory.createObjectToObjectHashMap();
	private final boolean monitorBrokenRuleApplications;

	private final int concurrencyThreshold = 5;

	public ConsistencyMatchHandler(IbexOptions options, IMatchContainer operationalMatchContainer, //
			boolean monitorBrokenRuleApplications) {
		super(options);
		this.operationalMatchContainer = operationalMatchContainer;
		this.monitorBrokenRuleApplications = monitorBrokenRuleApplications;
	}

	@Override
	protected void registerAtMatchDistributor(MatchDistributor matchDistributor) {
		Set<PatternType> patternSet = Collections.singleton(PatternType.CONSISTENCY);
		matchDistributor.registerSingle(patternSet, this::addConsistencyMatch, this::removeConsistencyMatch);
		matchDistributor.registerMultiple(patternSet, this::addConsistencyMatches, this::removeConsistencyMatches);
	}

	private void addConsistencyMatch(ITGGMatch match) {
		TGGRuleApplication ruleApplication = match.getRuleApplicationNode();
		ra2consMatches.put(ruleApplication, match);
		logReceivedAndAdded(match);

		if (monitorBrokenRuleApplications && brokenRA2consMatches.containsKey(ruleApplication)) {
			brokenRA2consMatches.remove(ruleApplication);

			logRepairConfirmation(match);
			options.debug.benchmarkLogger().addToNumOfMatchesRepaired(1);
		}

		operationalMatchContainer.addMatch(match);
	}

	private void addConsistencyMatches(Collection<ITGGMatch> matches) {
		if (matches.isEmpty())
			return;

		if (matches.size() <= concurrencyThreshold) {
			matches.forEach(this::addConsistencyMatch);
			return;
		}

		Map<TGGRuleApplication, ITGGMatch> ra2matches = matches.parallelStream() //
				.collect(Collectors.toMap(m -> m.getRuleApplicationNode(), m -> m));
		ra2consMatches.putAll(ra2matches);
		matches.forEach(this::logReceivedAndAdded);

		if (monitorBrokenRuleApplications) {
			Set<TGGRuleApplication> repairedRAs = new HashSet<>(Sets.intersection(ra2matches.keySet(), brokenRA2consMatches.keySet()));
			for (TGGRuleApplication ra : repairedRAs) {
				brokenRA2consMatches.remove(ra);
				logRepairConfirmation(ra2matches.get(ra));
			}
			options.debug.benchmarkLogger().addToNumOfMatchesRepaired(repairedRAs.size());
		}

		matches.forEach(m -> operationalMatchContainer.addMatch(m));
	}

	private void removeConsistencyMatch(ITGGMatch match) {
		TGGRuleApplication ruleApplication = match.getRuleApplicationNode();
		if (monitorBrokenRuleApplications) {
			ra2consMatches.remove(ruleApplication);
			brokenRA2consMatches.put(ruleApplication, match);
		}

		operationalMatchContainer.removeMatch(match);
	}

	private void removeConsistencyMatches(Collection<ITGGMatch> matches) {
		if (matches.isEmpty())
			return;

		if (matches.size() <= concurrencyThreshold) {
			matches.forEach(this::removeConsistencyMatch);
			return;
		}

		if (monitorBrokenRuleApplications) {
			Map<TGGRuleApplication, ITGGMatch> ra2matches = matches.parallelStream() //
					.collect(Collectors.toMap(m -> m.getRuleApplicationNode(), m -> m));
			for (TGGRuleApplication ra : ra2matches.keySet())
				ra2consMatches.remove(ra);
			brokenRA2consMatches.putAll(ra2matches);
		}

		matches.forEach(m -> operationalMatchContainer.removeMatch(m));
	}

	public Map<TGGRuleApplication, ITGGMatch> getRA2ConsMatches() {
		return ra2consMatches;
	}

	public Collection<ITGGMatch> getConsistencyMatches() {
		return ra2consMatches.values();
	}

	public Map<TGGRuleApplication, ITGGMatch> getBrokenRA2ConsMatches() {
		return brokenRA2consMatches;
	}

	public Set<TGGRuleApplication> getBrokenRuleApplications() {
		return brokenRA2consMatches.keySet();
	}

	public Collection<ITGGMatch> getBrokenMatches() {
		return brokenRA2consMatches.values();
	}

	public ITGGMatch getBrokenMatch(TGGRuleApplication ruleApplication) {
		return brokenRA2consMatches.get(ruleApplication);
	}

	public void addBrokenRuleApplication(TGGRuleApplication ruleApplication, ITGGMatch match) {
		brokenRA2consMatches.put(ruleApplication, match);
	}

	public ITGGMatch removeBrokenRuleApplication(TGGRuleApplication ruleApplication) {
		return brokenRA2consMatches.remove(ruleApplication);
	}

	public void clearBrokenRuleApplications() {
		brokenRA2consMatches.clear();
	}

	public boolean noBrokenRuleApplications() {
		return brokenRA2consMatches.isEmpty();
	}

	private void logReceivedAndAdded(ITGGMatch match) {
		LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received & added " //
				+ match.getPatternName() + "(" + match.hashCode() + ")\n" //
				+ ConsoleUtil.indent(ConsoleUtil.printMatchParameter(match), 9, true));
	}

	private void logRepairConfirmation(ITGGMatch match) {
		LoggerConfig.log(LoggerConfig.log_ruleApplication(), () -> "Repair confirmation: " //
				+ match.getPatternName() + "(" + match.hashCode() + ") appears to be fixed.");
	}

}
