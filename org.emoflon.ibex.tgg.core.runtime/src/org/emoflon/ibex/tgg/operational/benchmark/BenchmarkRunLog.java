package org.emoflon.ibex.tgg.operational.benchmark;

public class BenchmarkRunLog {
	
	private final int runNum;

	// Timing in milliseconds
	protected long executionTime = 0;

	// Elements
	protected long numOfCreatedElements = 0;
	protected long numOfDeletedElements = 0;

	// Matches
	protected long numOfMatchesFound = 0;
	protected long numOfMatchesRepaired = 0;
	protected long numOfMatchesApplied = 0;
	
	protected BenchmarkRunLog(int runNum) {
		this.runNum = runNum;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void addToExecutionTime(long time) {
		executionTime += time;
	}

	public long getNumOfElementsCreated() {
		return numOfCreatedElements;
	}

	public void setNumOfElementsCreated(long numOfElements) {
		this.numOfCreatedElements = numOfElements;
	}

	public long getNumOfElementsDeleted() {
		return numOfDeletedElements;
	}

	public void setNumOfElementsDeleted(long numOfElements) {
		this.numOfDeletedElements = numOfElements;
	}

	public long getNumOfMatchesFound() {
		return numOfMatchesFound;
	}

	public void setNumOfMatchesFound(long numOfMatches) {
		this.numOfMatchesFound = numOfMatches;
	}

	public long getNumOfMatchesRepaired() {
		return numOfMatchesRepaired;
	}

	public void addToNumOfMatchesRepaired(long numOfMatches) {
		this.numOfMatchesRepaired += numOfMatches;
	}

	public long getNumOfMatchesApplied() {
		return numOfMatchesApplied;
	}

	public void addToNumOfMatchesApplied(long numOfMatches) {
		this.numOfMatchesApplied += numOfMatches;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BenchmarkRunLog #" + runNum + "\n[");
		builder.append("\n  Execution time .... ");
		builder.append(getExecutionTime() + "ms");
		builder.append("\n  Created elements .. ");
		builder.append(getNumOfElementsCreated());
		builder.append("\n  Deleted elements .. ");
		builder.append(getNumOfElementsDeleted());
		builder.append("\n  Matches found ..... ");
		builder.append(getNumOfMatchesFound());
		builder.append("\n  Matches repaired .. ");
		builder.append(getNumOfMatchesRepaired());
		builder.append("\n  Matches applied ... ");
		builder.append(getNumOfMatchesApplied());
		builder.append("\n]");
		return builder.toString();
	}

}
