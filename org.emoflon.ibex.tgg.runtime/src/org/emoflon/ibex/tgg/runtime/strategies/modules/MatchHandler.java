package org.emoflon.ibex.tgg.runtime.strategies.modules;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.IMatchContainer;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.util.debug.ConsoleUtil;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

import com.google.common.collect.Sets;

import TGGRuntimeModel.TGGRuleApplication;

public class MatchHandler {

	private final IbexOptions options;

	private final int concurrencyThreshold = 5;

	private boolean handleConsistencyMatches;
	private boolean handleBrokenConsistencyMatches;
	private boolean handleOperationalMatches;

	private final Set<IMatchContainer> consistencyContainers;
	private final Map<IMatchContainer, Predicate<PatternType>> operationalContainers;

	private final Map<TGGRuleApplication, ITGGMatch> ra2consMatches = cfactory.createObjectToObjectHashMap();
	private final Map<TGGRuleApplication, ITGGMatch> brokenRA2consMatches = cfactory.createObjectToObjectHashMap();

	private boolean domainsHaveNoSharedTypes;

	private boolean initialized = false;

	public MatchHandler(IbexOptions options) {
		this.options = options;

		this.handleConsistencyMatches = false;
		this.handleBrokenConsistencyMatches = false;
		this.handleOperationalMatches = false;

		this.consistencyContainers = cfactory.createObjectSet();
		this.operationalContainers = cfactory.createObjectToObjectHashMap();
	}

	public void initialize() {
		if (initialized)
			return;

		TGGModel tgg = options.tgg.tgg();
		this.domainsHaveNoSharedTypes = tgg.getSource().stream().noneMatch(tgg.getTarget()::contains);

		initialized = true;
	}

	/**
	 * Enables handling of consistency matches and forwards them to the specified
	 * {@link IMatchContainer}. All currently valid consistency matches can be accessed via
	 * {@link MatchHandler#getConsistencyMatches()}.
	 * 
	 * @param matchContainer the match container consistency matches where forwarded to
	 */
	public void handleConsistencyMatches(IMatchContainer matchContainer) {
		registerConsistencyMatches();
		this.consistencyContainers.add(matchContainer);
	}

	/**
	 * Enables handling of operational matches and forwards them to the specified
	 * {@link IMatchContainer}. The given set of pattern types is used to statically filter the relevant
	 * operational pattern types. By the predicate, matches can be dynamically filtered by their pattern
	 * type.
	 * 
	 * @param matchContainer      the match container operational matches where forwarded to
	 * @param operationalPatterns generally relevant operational patterns
	 * @param isPatternRelevant   determines if the pattern type of the match is relevant at this point
	 *                            of time
	 */
	public void handleOperationalMatches(IMatchContainer matchContainer, //
			Set<PatternType> operationalPatterns, Predicate<PatternType> isPatternRelevant) {
		options.matchDistributor().registerSingle(operationalPatterns, this::addOperationalMatch, this::removeOperationalMatch);
		options.matchDistributor().registerMultiple(operationalPatterns, m -> m.forEach(this::addOperationalMatch),
				m -> m.forEach(this::removeOperationalMatch));

		this.handleOperationalMatches = true;
		this.operationalContainers.put(matchContainer, isPatternRelevant);
	}

	/**
	 * Enables handling of broken consistency matches. All currently broken consistency matches can be
	 * accessed via {@link MatchHandler#getBrokenMatches()}.
	 */
	public void handleBrokenConsistencyMatches() {
		registerConsistencyMatches();
		this.handleBrokenConsistencyMatches = true;
	}

	private void registerConsistencyMatches() {
		if (!this.handleConsistencyMatches) {
			Set<PatternType> consPattern = Collections.singleton(PatternType.CONSISTENCY);
			options.matchDistributor().registerSingle(consPattern, this::addConsistencyMatch, this::removeConsistencyMatch);
			options.matchDistributor().registerMultiple(consPattern, this::addConsistencyMatches, this::removeConsistencyMatches);

			this.handleConsistencyMatches = true;
		}
	}

	//// CONSISTENCY ////

	private void addConsistencyMatch(ITGGMatch match) {
		logConsReceivedAndAdded(match);

		TGGRuleApplication ruleApplication = match.getRuleApplicationNode();
		ra2consMatches.put(ruleApplication, match);

		if (handleBrokenConsistencyMatches && brokenRA2consMatches.containsKey(ruleApplication)) {
			brokenRA2consMatches.remove(ruleApplication);

			logConsRepairConfirmation(match);
			options.debug.benchmarkLogger().addToNumOfMatchesRepaired(1);
		}

		consistencyContainers.forEach(matchContainer -> matchContainer.addMatch(match));
	}

	private void addConsistencyMatches(Collection<ITGGMatch> matches) {
		if (matches.isEmpty())
			return;

		if (matches.size() <= concurrencyThreshold) {
			matches.forEach(this::addConsistencyMatch);
			return;
		}

		matches.forEach(this::logConsReceivedAndAdded);

		Map<TGGRuleApplication, ITGGMatch> ra2matches = matches.parallelStream() //
				.collect(Collectors.toMap(m -> m.getRuleApplicationNode(), m -> m));
		ra2consMatches.putAll(ra2matches);

		if (handleBrokenConsistencyMatches) {
			Set<TGGRuleApplication> repairedRAs = new HashSet<>(Sets.intersection(ra2matches.keySet(), brokenRA2consMatches.keySet()));
			for (TGGRuleApplication ra : repairedRAs) {
				brokenRA2consMatches.remove(ra);
				logConsRepairConfirmation(ra2matches.get(ra));
			}
			options.debug.benchmarkLogger().addToNumOfMatchesRepaired(repairedRAs.size());
		}

		consistencyContainers.forEach(matchContainer -> matches.forEach(m -> matchContainer.addMatch(m)));
	}

	private void removeConsistencyMatch(ITGGMatch match) {
		TGGRuleApplication ruleApplication = match.getRuleApplicationNode();
		if (handleBrokenConsistencyMatches) {
			ra2consMatches.remove(ruleApplication);
			brokenRA2consMatches.put(ruleApplication, match);
		}

		consistencyContainers.forEach(matchContainer -> matchContainer.removeMatch(match));
	}

	private void removeConsistencyMatches(Collection<ITGGMatch> matches) {
		if (matches.isEmpty())
			return;

		if (matches.size() <= concurrencyThreshold) {
			matches.forEach(this::removeConsistencyMatch);
			return;
		}

		if (handleBrokenConsistencyMatches) {
			Map<TGGRuleApplication, ITGGMatch> ra2matches = matches.parallelStream() //
					.collect(Collectors.toMap(m -> m.getRuleApplicationNode(), m -> m));
			for (TGGRuleApplication ra : ra2matches.keySet())
				ra2consMatches.remove(ra);
			brokenRA2consMatches.putAll(ra2matches);
		}

		consistencyContainers.forEach(matchContainer -> matches.forEach(m -> matchContainer.removeMatch(m)));
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

	//// OPERATIONAL ////

	public void addOperationalMatch(ITGGMatch match) {
		boolean checkedMatchIsDomainConform = false;
		for (Entry<IMatchContainer, Predicate<PatternType>> entry : operationalContainers.entrySet()) {
			if (entry.getValue().test(match.getType())) {
				if (!checkedMatchIsDomainConform) {
					if (matchIsDomainConform(match)) {
						checkedMatchIsDomainConform = true;
						logOpReceivedAndAdded(match);
					} else {
						logOpReceivedButNotDomainConform(match);
						break;
					}
				}

				entry.getKey().addMatch(match);
			} else {
				// TODO fix redundant log prints
				logOpReceivedButRejected(match);
			}
		}
	}

	public void removeOperationalMatch(ITGGMatch match) {
		if (!handleOperationalMatches)
			return;

		operationalContainers.forEach((matchContainer, __) -> matchContainer.removeMatch(match));
	}

	private boolean matchIsDomainConform(ITGGMatch match) {
		TGGOperationalRule operationalRule = options.tgg.ruleHandler().getOperationalRule(match.getOperationalRuleName());
		if (domainsHaveNoSharedTypes || options.patterns.ignoreDomainConformity())
			return true;

		if (options.patterns.relaxDomainConformity())
			return (matchedNodesAreInCorrectResource(options.resourceHandler().getSourceResource(), //
					operationalRule.getContextSource().getNodes(), match)
					|| matchedNodesAreInCorrectResource(options.resourceHandler().getSourceResource(), //
							operationalRule.getCreateSource().getNodes(), match))
					&& (matchedNodesAreInCorrectResource(options.resourceHandler().getTargetResource(), //
							operationalRule.getContextTarget().getNodes(), match)
							|| matchedNodesAreInCorrectResource(options.resourceHandler().getTargetResource(), //
									operationalRule.getCreateTarget().getNodes(), match));
		else
			return matchedNodesAreInCorrectResource(options.resourceHandler().getSourceResource(), //
					operationalRule.getContextSource().getNodes(), match)
					&& matchedNodesAreInCorrectResource(options.resourceHandler().getSourceResource(), //
							operationalRule.getCreateSource().getNodes(), match)
					&& matchedNodesAreInCorrectResource(options.resourceHandler().getTargetResource(), //
							operationalRule.getContextTarget().getNodes(), match)
					&& matchedNodesAreInCorrectResource(options.resourceHandler().getTargetResource(), //
							operationalRule.getCreateTarget().getNodes(), match);
	}

	private boolean matchedNodesAreInCorrectResource(Resource r, Collection<IBeXNode> nodes, ITGGMatch match) {
		return nodes.stream().noneMatch(n -> match.isInMatch(n.getName()) && !nodeIsInResource(match, n.getName(), r));
	}

	private Map<EObject, Resource> cacheObjectToResource = cfactory.createObjectToObjectHashMap();

	private boolean nodeIsInResource(ITGGMatch match, String name, Resource r) {
		EObject root = (EObject) match.get(name);
		if (!cacheObjectToResource.containsKey(root))
			cacheObjectToResource.put(root, root.eResource());

		return cacheObjectToResource.get(root).equals(r);
	}

	public void clearCaches() {
		cacheObjectToResource.clear();
	}

	private void logConsReceivedAndAdded(ITGGMatch match) {
		LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received & added " //
				+ match.getPatternName() + "(" + match.hashCode() + ")\n" //
				+ ConsoleUtil.indent(ConsoleUtil.printMatchParameter(match), 9, true));
	}

	private void logConsRepairConfirmation(ITGGMatch match) {
		LoggerConfig.log(LoggerConfig.log_ruleApplication(), () -> "Repair confirmation: " //
				+ match.getPatternName() + "(" + match.hashCode() + ") appears to be fixed.");
	}

	private void logOpReceivedButRejected(ITGGMatch match) {
		LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received but rejected " //
				+ match.getPatternName() + "(" + match.hashCode() + ")");
	}

	private void logOpReceivedButNotDomainConform(ITGGMatch match) {
		LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received but not domain conform " //
				+ match.getPatternName() + "(" + match.hashCode() + ")");
	}

	private void logOpReceivedAndAdded(ITGGMatch match) {
		LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received & added " //
				+ match.getPatternName() + "(" + match.hashCode() + ")");
	}

}
