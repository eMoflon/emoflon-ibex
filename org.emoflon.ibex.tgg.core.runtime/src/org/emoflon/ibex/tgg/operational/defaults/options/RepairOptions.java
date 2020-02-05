package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.shortcut.updatepolicy.DefaultSCRUpdatePolicy;
import org.emoflon.ibex.tgg.operational.repair.shortcut.updatepolicy.IShortcutRuleUpdatePolicy;

public class RepairOptions extends IbexSubOptions {
	
	private boolean repairAttributes;
	private boolean useShortcutRules;
	private boolean relaxedSCPatternMatching;
	private IShortcutRuleUpdatePolicy scrUpdatePolicy;

	public RepairOptions(IbexOptions options) {
		super(options);
		
		repairAttributes = true;
		useShortcutRules = false;
		relaxedSCPatternMatching = true;
		scrUpdatePolicy = new DefaultSCRUpdatePolicy();
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

	public IShortcutRuleUpdatePolicy shortcutRuleUpdatePolicy() {
		return scrUpdatePolicy;
	}

	public IbexOptions shortcutRuleUpdatePolicy(IShortcutRuleUpdatePolicy scrUpdatePolicy) {
		if (scrUpdatePolicy == null)
			this.scrUpdatePolicy = new DefaultSCRUpdatePolicy();
		else
			this.scrUpdatePolicy = scrUpdatePolicy;
		return options;
	}

}
