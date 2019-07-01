package org.emoflon.ibex.tgg.operational.benchmark;

import java.util.List;

public class FullBenchmarkLogger extends BenchmarkLogger {
	
	// Latest absolute values
	private long absNumOfCreatedElements = 0;
	private long absNumOfDeletedElements = 0;
	private long absNumOfMatchesFound = 0;

	@Override
	public boolean isLoggingEnabled() {
		return true;
	}

	@Override
	public void startNewRun() {
		runCount++;
		runLogs.add(new BenchmarkRunLog(runCount + 1));
	}

	@Override
	public long getInitTime() {
		return initTime;
	}

	@Override
	public void addToInitTime(long time) {
		initTime += time;
	}

	@Override
	public long getTotalExecutionTime() {
		long totalTime = 0;
		for (BenchmarkRunLog runLog : runLogs) {
			totalTime += runLog.getExecutionTime();
		}
		return totalTime;
	}

	@Override
	public void addToExecutionTime(long time) {
		runLogs.get(runCount).addToExecutionTime(time);
	}

	@Override
	public long getTotalElementsCreated() {
		long totalElem = 0;
		for (BenchmarkRunLog runLog : runLogs) {
			totalElem += runLog.getNumOfElementsCreated();
		}
		return totalElem;
	}

	@Override
	public void setNumOfElementsCreated(long numOfCreatedElements) {
		runLogs.get(runCount).setNumOfElementsCreated(numOfCreatedElements - absNumOfCreatedElements);
		absNumOfCreatedElements = numOfCreatedElements;
	}

	@Override
	public long getTotalElementsDeleted() {
		long totalElem = 0;
		for (BenchmarkRunLog runLog : runLogs) {
			totalElem += runLog.getNumOfElementsDeleted();
		}
		return totalElem;
	}

	@Override
	public void setNumOfElementsDeleted(long numOfDeletedElements) {
		runLogs.get(runCount).setNumOfElementsDeleted(numOfDeletedElements - absNumOfDeletedElements);
		absNumOfDeletedElements = numOfDeletedElements;
	}

	@Override
	public long getTotalMatchesFound() {
		long totalElem = 0;
		for (BenchmarkRunLog runLog : runLogs) {
			totalElem += runLog.getNumOfMatchesFound();
		}
		return totalElem;
	}

	@Override
	public void setNumOfMatchesFound(long numOfMatchesFound) {
		runLogs.get(runCount).setNumOfMatchesFound(numOfMatchesFound - absNumOfMatchesFound);
		absNumOfMatchesFound = numOfMatchesFound;
	}

	@Override
	public long getTotalMatchesRepaired() {
		long totalElem = 0;
		for (BenchmarkRunLog runLog : runLogs) {
			totalElem += runLog.getNumOfMatchesRepaired();
		}
		return totalElem;
	}

	@Override
	public void addToNumOfMatchesRepaired(long numOfMatchesRepaired) {
		runLogs.get(runCount).addToNumOfMatchesRepaired(numOfMatchesRepaired);
	}

	@Override
	public long getTotalMatchesApplied() {
		long totalElem = 0;
		for (BenchmarkRunLog runLog : runLogs) {
			totalElem += runLog.getNumOfMatchesApplied();
		}
		return totalElem;
	}

	@Override
	public void addToNumOfMatchesApplied(long numOfMatchesApplied) {
		runLogs.get(runCount).addToNumOfMatchesApplied(numOfMatchesApplied);
	}

	@Override
	public List<BenchmarkRunLog> getRunLogs() {
		return runLogs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BenchmarkLogger\n[");
		builder.append("\n  Initialisation time ..... ");
		builder.append(initTime + "ms");
		builder.append("\n  Total execution time .... ");
		builder.append(getTotalExecutionTime() + "ms");
		builder.append("\n  Total elements created .. ");
		builder.append(getTotalElementsCreated());
		builder.append("\n  Total elements deleted .. ");
		builder.append(getTotalElementsDeleted());
		builder.append("\n  Total matches found ..... ");
		builder.append(getTotalMatchesFound());
		builder.append("\n  Total matches repaired .. ");
		builder.append(getTotalMatchesRepaired());
		builder.append("\n  Total matches applied ... ");
		builder.append(getTotalMatchesApplied());
		builder.append("\n]");
		return builder.toString();
	}

}
