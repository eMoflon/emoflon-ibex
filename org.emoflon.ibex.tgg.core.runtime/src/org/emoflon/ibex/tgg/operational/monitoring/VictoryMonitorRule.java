package org.emoflon.ibex.tgg.operational.monitoring;

import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public class VictoryMonitorRule extends AbstractIbexObserver {

	private final RuleChooser ruleChooser;

	/**
	 * Creates a new victory monitor that returns the object of a ruleName.
	 * @param observable	The operational strategy to observe
	 * @param matchChooser	The rule choosing algorithm
	 */
	public VictoryMonitorRule (OperationalStrategy observable, RuleChooser ruleChooser) {
		super(observable);
		this.ruleChooser = ruleChooser;
		
	}

	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		switch (eventType) {
		case MATCHAPPLIED:
			ruleChooser.ruleHasBeenChosen((IGreenPatternFactory) additionalInformation[0]); 
			break;
		default:
			break;
		}
	}

//	@Override
	public IGreenPatternFactory getGreenFactory(String ruleName) {
		return this.ruleChooser.getGreenFactory(ruleName);
	}
	
	/**
	 * Interface of a class that has function to select a rule from the given data
	 *
	 */
	public interface RuleChooser {
		/**
		 * Chooses one rule from the matches given in the match container
		 * @param ruleName
		 * @return the chosen rule
		 */
		public IGreenPatternFactory getGreenFactory (String ruleName);
		
		/**
		 * Called when a match has been successfully applied
		 * @param match The match that has been applied
		 */
		
		public void ruleHasBeenChosen(IGreenPatternFactory factory);
	}
}
