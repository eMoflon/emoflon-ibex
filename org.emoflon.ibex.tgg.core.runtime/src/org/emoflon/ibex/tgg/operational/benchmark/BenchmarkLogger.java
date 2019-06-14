package org.emoflon.ibex.tgg.operational.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class BenchmarkLogger {

	private static Stack<Long> timerStack = new Stack<>();
	
	// Run logs
	protected final List<BenchmarkRunLog> runLogs = new ArrayList<>();
	protected int runCount = -1;
	
	// Timing in milliseconds
	protected long initTime = 0;	

	public static void startTimer() {
		timerStack.push(System.currentTimeMillis());
	}

	public static long stopTimer() {
		if (timerStack.isEmpty())
			return 0;
		return System.currentTimeMillis() - timerStack.pop();
	}

	public abstract boolean isLoggingEnabled();

	public abstract void startNewRun();

	public abstract long getInitTime();

	public abstract void addToInitTime(long time);

	public abstract long getTotalTranslationTime();

	public abstract void addToTranslationTime(long time);

	public abstract long getTotalElementsCreated();

	public abstract void setNumOfElementsCreated(long numOfElements);

	public abstract long getTotalElementsDeleted();

	public abstract void setNumOfElementsDeleted(long numOfElements);

	public abstract long getTotalMatchesFound();

	public abstract void setNumOfMatchesFound(long numOfMatches);

	public abstract long getTotalMatchesRepaired();

	public abstract void addToNumOfMatchesRepaired(long numOfMatches);

	public abstract long getTotalMatchesApplied();

	public abstract void addToNumOfMatchesApplied(long numOfMatches);

	public abstract List<BenchmarkRunLog> getRunLogs();

	@Override
	public abstract String toString();

}
