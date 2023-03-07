package org.emoflon.ibex.tgg.runtime.strategies.gen;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.SimpleTGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.runtime.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.runtime.updatepolicy.RandomMatchUpdatePolicy;
import org.emoflon.ibex.tgg.runtime.updatepolicy.UpdatePolicy;

/**
 * The TGG operation MODELGEN applies TGG rules as they are and thus generates
 * consistent triples. The generation process does not terminate naturally and
 * thus has to be controlled by a {@link MODELGENStopCriterion}.
 * 
 * <br/>
 * <br/>
 * 
 * In addition to a {@link MODELGENStopCriterion}, an {@link UpdatePolicy} can
 * be supplied to control the order in which matches are chosen and applied. For
 * efficiency reasons the default order of rule application is not really random
 * and simply relies on the natural randomness of the underlying hashsets used.
 * If real (or better) randomness (or some other distribution) is required,
 * please subclass the update policy.
 * 
 * <br/>
 * <br/>
 * 
 * Checks ensure that the basic abstract syntax (EMF and metamodel constraints)
 * of all metamodels are respected (NACs are not necessary for upper bound
 * multiplicities). Lower bound multiplicities and other OCL constraints are not
 * supported.
 * 
 * <br/>
 * <br/>
 * 
 * To further increase scalability, subclass and supress, e.g., protocol
 * creation and/or model saving, etc.
 * 
 * @author Anthony Anjorin, Lars Fritsche
 */
public class MODELGEN extends OperationalStrategy {

	/********************** Attributes *********************/

	/**
	 * Used to control the generation process. See {@link MODELGENStopCriterion} for
	 * what is possible.
	 */
	protected MODELGENStopCriterion stopCriterion;

	protected AxiomNACHandler axiomMatchHandler;

	/** Constructors 
	 * @param modelgen **/

	public MODELGEN(IbexOptions options) throws IOException {
		super(options, new RandomMatchUpdatePolicy(50));
	}

	@Override
	protected void initializeAdditionalModules(IbexOptions options) throws IOException {
		this.axiomMatchHandler = new AxiomNACHandler(options, operationalMatchContainer);
	}

	/********************** Public Interface *********************/

	/**
	 * First of all collect all matches for axioms (this does not involve the
	 * pattern matcher), and then update all matches after each rule application.
	 */
	@Override
	public void run() throws IOException {
		collectMatchesForAxioms();
		do
			matchDistributor.updateMatches();
		while (processOneOperationalRuleMatch());
	}

	public void setStopCriterion(MODELGENStopCriterion stop) {
		this.stopCriterion = stop;
	}

	/********************** Internal Interface *********************/

	/**
	 * In contrast to other TGG ops, MODELGEN (i) does not automatically remove
	 * successful matches (but can use them repeatedly), (ii) updates the state of
	 * its stop criterion after every rule application.
	 */
	@Override
	protected boolean processOneOperationalRuleMatch() {
		this.updateBlockedMatches();
		if (operationalMatchContainer.isEmpty())
			return false;

		ITGGMatch match = chooseOneMatch();
		String ruleName = match.getRuleName();

		Optional<ITGGMatch> comatch = processOperationalRuleMatch(ruleName, match);
		comatch.ifPresent(cm -> {
			updateStopCriterion(ruleName);
		});

		// Rule application failed, match must have invalid so remove
		if (!comatch.isPresent()) {
			matchHandler.removeOperationalMatch(match);
			logger.debug("Unable to apply: " + ruleName);
		}

		return true;
	}

	@Override
	protected void updateBlockedMatches() {
		for (ITGGMatch match : operationalMatchContainer.getMatches().toArray(new ITGGMatch[0])) {
			String ruleName = match.getRuleName();
			if (stopCriterion.dont()) {
				if (!blockedMatches.containsKey(match))
					blockedMatches.put(match, "Application blocked by stop criterion");
				matchHandler.removeOperationalMatch(match);
			}
			if (stopCriterion.dont(ruleName)) {
				if (!blockedMatches.containsKey(match))
					blockedMatches.put(match, "Application blocked by ruleName stop criterion");
				matchHandler.removeOperationalMatch(match);
			}
		}
		super.updateBlockedMatches();
	}

	@Override
	protected ITGGMatch chooseOneMatch() {
		ITGGMatch match = this.notifyChooseMatch(new ImmutableMatchContainer(operationalMatchContainer));

		if (match == null)
			throw new IllegalStateException("Update policies should never return null!");

		return match;
	}

	/**
	 * Override in subclass if markers for protocol are not required (this can speed
	 * up the generation process).
	 */
	@Override
	protected void handleSuccessfulRuleApplication(ITGGMatch cm, String ruleName, IGreenPattern greenPattern) {
		createMarkers(greenPattern, cm, ruleName);
	}

	/**
	 * Update stop criterion by passing number of created source and target elements
	 * (nodes and edges).
	 * 
	 * @param ruleName
	 */
	private void updateStopCriterion(String ruleName) {
		stopCriterion.update(ruleName,
				greenFactories.get(ruleName).getGreenSrcNodesInRule().size()
						+ greenFactories.get(ruleName).getGreenSrcEdgesInRule().size(),
						greenFactories.get(ruleName).getGreenTrgNodesInRule().size()
						+ greenFactories.get(ruleName).getGreenTrgEdgesInRule().size());
	}

	/**
	 * Axioms are always applicable but the PatternMatcher does not return empty
	 * matches enabling the application of the axioms. Therefore we have to add the
	 * empty matches. The match of an axiom will be removed once a match of a NAC of
	 * the axiom is found (or the stop criterion blocks the rule).
	 */
	private void collectMatchesForAxioms() {
		options.tgg.getFlattenedConcreteTGGRules().stream().filter(r -> greenFactories.get(r.getName()).isAxiom())
				.forEach(r -> {
					matchHandler.addOperationalMatch(new SimpleTGGMatch(TGGPatternUtil.generateGENBlackPatternName(r.getName())));
				});
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return Collections.singleton(PatternType.GEN);
	}

}
