package org.emoflon.ibex.tgg.operational.strategies.matchhandling;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactoryProvider;
import org.emoflon.ibex.tgg.operational.strategies.modules.MatchDistributor;

import language.TGG;
import language.TGGRuleNode;

public class OperationalMatchHandler extends MatchConsumer {

	private final GreenPatternFactoryProvider greenPatternFactoryProvider;
	private final IMatchContainer operationalMatchContainer;
	private final boolean domainsHaveNoSharedTypes;
	private final Predicate<PatternType> isPatternRelevant;

	public OperationalMatchHandler(IbexOptions options, //
			GreenPatternFactoryProvider greenPatternFactoryProvider, //
			IMatchContainer operationalMatchContainer, //
			Set<PatternType> operationalPatterns, //
			Predicate<PatternType> isPatternRelevant) {
		super(options, operationalPatterns);

		this.greenPatternFactoryProvider = greenPatternFactoryProvider;
		this.operationalMatchContainer = operationalMatchContainer;
		this.isPatternRelevant = isPatternRelevant;

		TGG tgg = options.tgg.tgg();

		this.domainsHaveNoSharedTypes = tgg.getSrc().stream().noneMatch(tgg.getTrg()::contains);
	}

	@Override
	protected void registerAtMatchDistributor(MatchDistributor matchDistributor) {
		matchDistributor.registerSingle(patternSet, this::addOperationalMatch, this::removeOperationalMatch);
		matchDistributor.registerMultiple(patternSet, m -> m.forEach(this::addOperationalMatch), m -> m.forEach(this::removeOperationalMatch));
	}

	public void addOperationalMatch(ITGGMatch match) {
		if (isPatternRelevant.test(match.getType()) && matchIsDomainConform(match)) {
			operationalMatchContainer.addMatch(match);

			LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received & added " //
					+ match.getPatternName() + "(" + match.hashCode() + ")");
		} else {
			LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: received but rejected " //
					+ match.getPatternName() + "(" + match.hashCode() + ")");
		}
	}

	public void removeOperationalMatch(ITGGMatch match) {
		operationalMatchContainer.removeMatch(match);
	}

	private boolean matchIsDomainConform(ITGGMatch match) {
		if (domainsHaveNoSharedTypes || options.patterns.ignoreDomainConformity())
			return true;

		if (options.patterns.relaxDomainConformity())
			return (matchedNodesAreInCorrectResource(options.resourceHandler().getSourceResource(), //
					greenPatternFactoryProvider.get(match.getRuleName()).getBlackSrcNodesInRule(), match)
					|| matchedNodesAreInCorrectResource(options.resourceHandler().getSourceResource(), //
							greenPatternFactoryProvider.get(match.getRuleName()).getGreenSrcNodesInRule(), match))
					&& (matchedNodesAreInCorrectResource(options.resourceHandler().getTargetResource(), //
							greenPatternFactoryProvider.get(match.getRuleName()).getBlackTrgNodesInRule(), match)
							|| matchedNodesAreInCorrectResource(options.resourceHandler().getTargetResource(), //
									greenPatternFactoryProvider.get(match.getRuleName()).getGreenTrgNodesInRule(), match));
		else
			return matchedNodesAreInCorrectResource(options.resourceHandler().getSourceResource(), //
					greenPatternFactoryProvider.get(match.getRuleName()).getBlackSrcNodesInRule(), match)
					&& matchedNodesAreInCorrectResource(options.resourceHandler().getSourceResource(), //
							greenPatternFactoryProvider.get(match.getRuleName()).getGreenSrcNodesInRule(), match)
					&& matchedNodesAreInCorrectResource(options.resourceHandler().getTargetResource(), //
							greenPatternFactoryProvider.get(match.getRuleName()).getBlackTrgNodesInRule(), match)
					&& matchedNodesAreInCorrectResource(options.resourceHandler().getTargetResource(), //
							greenPatternFactoryProvider.get(match.getRuleName()).getGreenTrgNodesInRule(), match);
	}

	private boolean matchedNodesAreInCorrectResource(Resource r, Collection<TGGRuleNode> nodes, ITGGMatch match) {
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

}
