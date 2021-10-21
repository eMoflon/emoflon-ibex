package org.emoflon.ibex.tgg.operational.strategies.gen;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.matchhandling.MatchConsumer;
import org.emoflon.ibex.tgg.operational.strategies.modules.MatchDistributor;

/**
 * If we get notified about a new match that is the NAC of an axiom (i.e. a match for an
 * GENAxiomNacPattern) we need to remove the always available empty axiom match. Otherwise we can
 * add the match as usual.
 */
public class AxiomNACHandler extends MatchConsumer {

	private final IMatchContainer operationalMatchContainer;

	public AxiomNACHandler(IbexOptions options, IMatchContainer operationalMatchContainer) {
		super(options);
		this.operationalMatchContainer = operationalMatchContainer;
	}

	@Override
	protected void registerAtMatchDistributor(MatchDistributor matchDistributor) {
		Set<PatternType> patternSet = Collections.singleton(PatternType.GEN_AXIOM_NAC);
		matchDistributor.registerSingle(patternSet, this::deleteAxiomMatchesForFoundNACs, m -> {});
	}

	/**
	 * We have found a match for a NAC of an axiom. This means this axiom is no longer applicable and
	 * thus needs to be removed from the set of matches
	 * 
	 * @param match the match of a NAC for an Axiom
	 */
	private void deleteAxiomMatchesForFoundNACs(org.emoflon.ibex.common.operational.IMatch match) {
		Set<ITGGMatch> matchesToRemove = new HashSet<>();

		String axiomName = TGGPatternUtil.generateGENBlackPatternName( //
				TGGPatternUtil.extractGENAxiomNacName(match.getPatternName()));

		operationalMatchContainer.getMatches() //
				.stream() //
				.filter(m -> m.getPatternName().equals(axiomName)) //
				.forEach(m -> matchesToRemove.add(m));

		matchesToRemove.forEach(m -> operationalMatchContainer.removeMatch(m));
	}

}
