package org.emoflon.ibex.tgg.operational.util;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * This UpdatePolicy can be used to extend other UpdatePolicies
 * by a timeout feature.
 * When the deadline is exceeded by the operationalization, the
 * TimedUpdatePolicy will not provide any more matches, resulting
 * in an OutOfTimeException.
 */
public class TimedUpdatePolicy implements IUpdatePolicy {
	private IUpdatePolicy basePolicy;
	private long startTime;
	private long timeout;
	
	public class OutOfTimeException extends RuntimeException {
		private static final long serialVersionUID = -1643055022267531668L;
		
		public OutOfTimeException() {
			super("A request for a match was issued to a TimedUpdatePolicy after its timeout.");
		}
	}
	
	public TimedUpdatePolicy(IUpdatePolicy basePolicy, long timeout, TimeUnit timeUnit) {
		this.basePolicy = basePolicy;
		this.startTime = System.nanoTime();
		this.timeout = timeUnit.toNanos(timeout);
	}
	
	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		long currentTime = System.nanoTime();
		long deadline = startTime + timeout;
		if (currentTime >= deadline)
			throw new OutOfTimeException();
		return basePolicy.chooseOneMatch(matchContainer);
	}
	
	public void reset() {
		this.startTime = System.nanoTime();
	}

	@Override
	public HashMap<String, Integer> getNumberOfApplications(Set<String> complementRules) {
		// TODO Auto-generated method stub
		return null;
	}
}
