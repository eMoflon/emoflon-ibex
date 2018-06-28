package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

public class InterfaceShortcutRule {

	private SyncDirection direction;
	private ShortcutRule scRule;
	
	public InterfaceShortcutRule(SyncDirection direction, ShortcutRule scRule) {
		this.scRule = scRule;
		this.direction = direction;
		createInterfaceShortcutRule();
	}
	
	public void createInterfaceShortcutRule() {
		
	}
}
