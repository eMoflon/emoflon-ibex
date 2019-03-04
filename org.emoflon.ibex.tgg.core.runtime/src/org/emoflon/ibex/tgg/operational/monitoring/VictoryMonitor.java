package org.emoflon.ibex.tgg.operational.monitoring;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public class VictoryMonitor extends AbstractIbexObserver implements IUpdatePolicy {

	private final IMatchChooser matchChooser;

	/**
	 * Creates a new victory monitor that allows the class using this observer to choose matches
	 * @param observable	The operational strategy to observe
	 * @param matchChooser	The match choosing algorithm
	 */
	public VictoryMonitor(OperationalStrategy observable, IMatchChooser matchChooser) {
		super(observable);
		this.matchChooser = matchChooser;
		observable.setUpdatePolicy(this);
	}

	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		switch (eventType) {
		case MATCHAPPLIED:
			matchChooser.matchHasBeenApplied((IMatch) additionalInformation[0]); 
			break;
		default:
			break;
		}
	}

	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return this.matchChooser.chooseOneMatch(matchContainer);
	}
	
	/**
	 * Interface of a class that has function to select a match from the given matches
	 *
	 */
	public interface IMatchChooser {
		/**
		 * Chooses one match from the matches given in the match container
		 * @param matchContainer
		 * @return the chosen match
		 */
		public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer);
		
		/**
		 * Called when a match has been successfully applied
		 * @param match The match that has been applied
		 */
		public void matchHasBeenApplied(IMatch match);
	}
}
