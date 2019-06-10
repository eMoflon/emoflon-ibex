package org.emoflon.ibex.tgg.operational.benchmark;

import java.util.ArrayList;
import java.util.List;

public class EmptyBenchmarkLogger extends BenchmarkLogger {

	@Override
	public boolean isLoggingEnabled() {
		return false;
	}

	@Override
	public void startNewRun() {
		// Intentionally left blank
	}

	@Override
	public long getInitTime() {
		return -1;
	}

	@Override
	public void addToInitTime(long time) {
		// Intentionally left blank
	}

	@Override
	public long getTotalTranslationTime() {
		return -1;
	}

	@Override
	public void addToTranslationTime(long time) {
		// Intentionally left blank
	}

	@Override
	public long getTotalElementsCreated() {
		return -1;
	}

	@Override
	public void setNumOfElementsCreated(long numOfCreatedElements) {
		// Intentionally left blank
	}

	@Override
	public long getTotalElementsDeleted() {
		return -1;
	}

	@Override
	public void setNumOfElementsDeleted(long numOfDeletedElements) {
		// Intentionally left blank
	}

	@Override
	public long getTotalMatchesFound() {
		return -1;
	}

	@Override
	public void setNumOfMatchesFound(long matchCounter) {
		// Intentionally left blank
	}

	@Override
	public long getTotalMatchesRepaired() {
		return -1;
	}

	@Override
	public void addToNumOfMatchesRepaired(long numOfMatchesRepaired) {
		// Intentionally left blank
	}

	@Override
	public long getTotalMatchesApplied() {
		return -1;
	}

	@Override
	public void addToNumOfMatchesApplied(long numOfMatchesApplied) {
		// Intentionally left blank
	}

	@Override
	public List<BenchmarkRunLog> getRunLogs() {
		return new ArrayList<>();
	}

	@Override
	public String toString() {
		return "BenchmarkLogger\n[ Logging disabled ]";
	}

}
