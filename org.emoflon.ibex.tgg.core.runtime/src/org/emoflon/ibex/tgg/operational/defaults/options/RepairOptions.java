package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.shortcut.selectionpolicy.DefaultSCRSelectionPolicy;
import org.emoflon.ibex.tgg.operational.repair.shortcut.selectionpolicy.IShortcutRuleSelectionPolicy;

public class RepairOptions extends IbexSubOptions {

	private boolean repairAttributes;
	private boolean useShortcutRules;
	private boolean relaxedSCPatternMatching;
	private boolean disableInjectivity;
	private boolean omitUnnecessaryContext;
	private boolean advancedOverlapStrategies;
	private boolean usePGbasedSCruleCreation;
	private boolean useExperimentalCorrConstraints;
	private boolean createBranchesOfHigherOrderRules;
	private IShortcutRuleSelectionPolicy scrSelectionPolicy;

	public RepairOptions(IbexOptions options) {
		super(options);

		repairAttributes = true;
		useShortcutRules = false;
		relaxedSCPatternMatching = true;
		advancedOverlapStrategies = false;
		disableInjectivity = true;
		omitUnnecessaryContext = true;
		usePGbasedSCruleCreation = false;
		useExperimentalCorrConstraints = false;
		createBranchesOfHigherOrderRules = false;
		scrSelectionPolicy = new DefaultSCRSelectionPolicy();
	}

	public boolean useShortcutRules() {
		return useShortcutRules;
	}

	public IbexOptions useShortcutRules(boolean useShortcutRules) {
		this.useShortcutRules = useShortcutRules;
		return options;
	}

	public boolean relaxedSCPatternMatching() {
		return relaxedSCPatternMatching;
	}

	public IbexOptions relaxedSCPatternMatching(boolean relaxedSCPatternMatching) {
		this.relaxedSCPatternMatching = relaxedSCPatternMatching;
		return options;
	}

	public boolean repairAttributes() {
		return repairAttributes;
	}

	public IbexOptions repairAttributes(boolean repairAttributes) {
		this.repairAttributes = repairAttributes;
		return options;
	}

	public boolean disableInjectivity() {
		return disableInjectivity;
	}

	public IbexOptions disableInjectivity(boolean disableInjectivity) {
		this.disableInjectivity = disableInjectivity;
		return options;
	}

	public boolean omitUnnecessaryContext() {
		return omitUnnecessaryContext;
	}

	public IbexOptions omitUnnecessaryContext(boolean omitUnnecessaryContext) {
		this.omitUnnecessaryContext = omitUnnecessaryContext;
		return options;
	}

	public boolean advancedOverlapStrategies() {
		return advancedOverlapStrategies;
	}

	public IbexOptions advancedOverlapStrategies(boolean advancedOverlapStrategies) {
		this.advancedOverlapStrategies = advancedOverlapStrategies;
		return options;
	}

	public boolean usePGbasedSCruleCreation() {
		return usePGbasedSCruleCreation;
	}

	public IbexOptions usePGbasedSCruleCreation(boolean usePGbasedSCruleCreation) {
		this.usePGbasedSCruleCreation = usePGbasedSCruleCreation;
		return options;
	}

	public boolean useExperimentalCorrConstraints() {
		return useExperimentalCorrConstraints;
	}

	public IbexOptions useExperimentalCorrConstraints(boolean useExperimentalCorrConstraints) {
		this.useExperimentalCorrConstraints = useExperimentalCorrConstraints;
		return options;
	}

	public boolean createBranchesOfHigherOrderRules() {
		return createBranchesOfHigherOrderRules;
	}

	public IbexOptions createBranchesOfHigherOrderRules(boolean createBranchesOfHigherOrderRules) {
		this.createBranchesOfHigherOrderRules = createBranchesOfHigherOrderRules;
		return options;
	}

	public IShortcutRuleSelectionPolicy shortcutRuleSelectionPolicy() {
		return scrSelectionPolicy;
	}

	public IbexOptions shortcutRuleSelectionPolicy(IShortcutRuleSelectionPolicy scrSelectionPolicy) {
		if (scrSelectionPolicy == null)
			this.scrSelectionPolicy = new DefaultSCRSelectionPolicy();
		else
			this.scrSelectionPolicy = scrSelectionPolicy;
		return options;
	}

}
