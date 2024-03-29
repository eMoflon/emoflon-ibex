package org.emoflon.ibex.tgg.operational.benchmark;

import java.util.ArrayList;
import java.util.List;

public abstract class BenchmarkLogger {
	
	// Run logs
	protected final List<BenchmarkRunLog> runLogs = new ArrayList<>();
	protected int runCount = -1;

	public abstract boolean isLoggingEnabled();

	public abstract void startNewRun();

	public abstract long getTotalElementsCreated();

	public abstract void setNumOfElementsCreated(long numOfElements);

	public abstract long getTotalElementsDeleted();

	public abstract void setNumOfElementsDeleted(long numOfElements);

	public abstract long getTotalMatchesFound();

	public abstract void setNumOfMatchesFound(long numOfMatches);

	public abstract long getTotalMatchesRepaired();

	public abstract void addToNumOfMatchesRepaired(long numOfMatches);
	
	public abstract long getTotalMatchesRevoked();
	
	public abstract void addToNumOfMatchesRevoked(long numOfMatches);

	public abstract long getTotalMatchesApplied();

	public abstract void addToNumOfMatchesApplied(long numOfMatches);

	public abstract List<BenchmarkRunLog> getRunLogs();

	@Override
	public abstract String toString();

}
