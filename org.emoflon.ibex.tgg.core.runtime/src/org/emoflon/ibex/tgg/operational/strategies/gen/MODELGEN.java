package org.emoflon.ibex.tgg.operational.strategies.gen;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.SimpleMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.RandomMatchUpdatePolicy;
import org.emoflon.ibex.tgg.operational.updatepolicy.UpdatePolicy;

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
 * supported. Multi-amalgamation is supported and can be controlled by an update
 * policy.
 * 
 * <br/>
 * <br/>
 * 
 * To further increase scalability, subclass and supress, e.g., protocol
 * creation and/or model saving, etc.
 * 
 * @author Anthony Anjorin
 */
public abstract class MODELGEN extends OperationalStrategy {

	/********************** Attributes *********************/

	/**
	 * Used to control the generation process. See {@link MODELGENStopCriterion} for
	 * what is possible.
	 */
	protected MODELGENStopCriterion stopCriterion;

	/** Constructors **/

	public MODELGEN(IbexOptions options) throws IOException {
		super(options, new RandomMatchUpdatePolicy(50));
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
			blackInterpreter.updateMatches();
		while (processOneOperationalRuleMatch());
	}

	public void setStopCriterion(MODELGENStopCriterion stop) {
		this.stopCriterion = stop;
	}
	
	@Override
	protected void loadTGG() throws IOException {
		super.loadTGG();
	}

	@Override
	public void saveModels() throws IOException {
		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
	}

	@Override
	public void loadModels() throws IOException {
		s = createResource(options.projectPath() + "/instances/src.xmi");
		t = createResource(options.projectPath() + "/instances/trg.xmi");
		c = createResource(options.projectPath() + "/instances/corr.xmi");
		p = createResource(options.projectPath() + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}

	/**
	 * If we get notified about a new match that is the NAC of an axiom (i.e. a
	 * match for an GENAxiomNacPattern) we need to remove the always available empty
	 * axiom match. Otherwise we can add the match as usual.
	 */
	@Override
	public void addMatch(org.emoflon.ibex.common.operational.IMatch match) {
		if (TGGPatternUtil.isGENAxiomNacPattern(match.getPatternName())) {
			this.deleteAxiomMatchesForFoundNACs(match);
		} else {
			super.addMatch(match);
		}
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.GEN) || patternName.endsWith(PatternSuffixes.GEN_AXIOM_NAC);
	}

	/********************** Internal Interface *********************/

	/**
	 * Override in subclasses (concrete modelgen apps in TGG projects), to register
	 * all required metamodels for the concrete example.
	 */
	protected abstract void registerUserMetamodels() throws IOException;

	/**
	 * In contrast to other TGG ops, MODELGEN (i) does not automatically remove
	 * successful matches (but can use them repeatedly), (ii) updates the state of
	 * its stop criterion after every rule application.
	 * 
	 * <br/>
	 * 
	 * For TGGs with multi-amalgamation, all complement matches are handled
	 * immediately after the corresponding kernel match.
	 */
	@Override
	protected boolean processOneOperationalRuleMatch() {
		this.updateBlockedMatches();
		if (operationalMatchContainer.isEmpty())
			return false;

		IMatch match = chooseOneMatch();
		String ruleName = operationalMatchContainer.getRuleName(match);

		Optional<IMatch> comatch = processOperationalRuleMatch(ruleName, match);
		comatch.ifPresent(cm -> {
			updateStopCriterion(ruleName);
		});

		// Rule application failed, match must have invalid so remove
		if (!comatch.isPresent()) {
			removeOperationalRuleMatch(match);
			logger.debug("Unable to apply: " + ruleName);
		}

		return true;
	}
	/**
	 * Matches blocked by Stop criterion will be removed from the 
	 * operational match container containing the applicable matches
	 */
	@Override
	protected void updateBlockedMatches() {
		for(IMatch match : operationalMatchContainer.getMatches().toArray(new IMatch[0])) {
			String ruleName = operationalMatchContainer.getRuleName(match);
			if(stopCriterion.dont()) {
				if(!blockedMatches.containsKey(match))
					blockedMatches.put(match, "Application blocked by stop criterion");
				removeOperationalRuleMatch(match);
			}
			if (stopCriterion.dont(ruleName)) {
				if(!blockedMatches.containsKey(match))
					blockedMatches.put(match, "Application blocked by ruleName stop criterion");
				removeOperationalRuleMatch(match);
			}
		}
		super.updateBlockedMatches();
	}
	
	@Override
	protected IMatch chooseOneMatch() {
		IMatch match = this.notifyChooseMatch(new ImmutableMatchContainer(operationalMatchContainer));
		
		if (match == null)
			throw new IllegalStateException("Update policies should never return null!");

		return match;
	}

	/**
	 * Override in subclass if markers for protocol are not required (this can speed
	 * up the generation process).
	 */
	@Override
	protected void handleSuccessfulRuleApplication(IMatch cm, String ruleName, IGreenPattern greenPattern) {
		createMarkers(greenPattern, cm, ruleName);
	}

	/**
	 * We have found a match for a NAC of an axiom. This means this axiom is no
	 * longer applicable and thus needs to be removed from the set of matches
	 * 
	 * @param match the match of a NAC for an Axiom
	 */
	private void deleteAxiomMatchesForFoundNACs(org.emoflon.ibex.common.operational.IMatch match) {
		Set<IMatch> matchesToRemove = new HashSet<>();

		String axiomName = TGGPatternUtil.getGENBlackPatternName(//
				TGGPatternUtil.extractGENAxiomNacName(match.getPatternName()));

		operationalMatchContainer.getMatches()//
				.stream()//
				.filter(m -> m.getPatternName().equals(axiomName))//
				.forEach(m -> matchesToRemove.add(m));

		matchesToRemove.forEach(m -> removeMatch(m));
	}

		/**
	 * Update stop criterion by passing number of created source and target elements
	 * (nodes and edges).
	 * 
	 * @param ruleName
	 */
	private void updateStopCriterion(String ruleName) {
		stopCriterion.update(ruleName,
				getGreenFactory(ruleName).getGreenSrcNodesInRule().size()
						+ getGreenFactory(ruleName).getGreenSrcEdgesInRule().size(),
				getGreenFactory(ruleName).getGreenTrgNodesInRule().size()
						+ getGreenFactory(ruleName).getGreenTrgEdgesInRule().size());
	}

	/**
	 * Axioms are always applicable but the PatternMatcher does not return empty
	 * matches enabling the application of the axioms. Therefore we have to add the
	 * empty matches. The match of an axiom will be removed once a match of a NAC of
	 * the axiom is found (or the stop criterion blocks the rule).
	 */
	private void collectMatchesForAxioms() {
		options.getFlattenedConcreteTGGRules().stream().filter(r -> getGreenFactory(r.getName()).isAxiom())
				.forEach(r -> {
					addOperationalRuleMatch(new SimpleMatch(TGGPatternUtil.getGENBlackPatternName(r.getName())));
				});
	}
}
