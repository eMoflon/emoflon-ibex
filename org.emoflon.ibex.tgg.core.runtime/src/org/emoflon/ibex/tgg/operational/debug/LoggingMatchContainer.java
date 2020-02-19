package org.emoflon.ibex.tgg.operational.debug;

import java.text.DecimalFormat;

public abstract class LoggingMatchContainer {

	protected long addMatchTime = 0;
	protected long getMatchTime = 0;
	protected long matchAppliedTime = 0;
	protected long removeMatchTime = 0;
	
	public void log() {
		DecimalFormat df = new DecimalFormat("0.#####");
		df.setMaximumFractionDigits(5);

		LoggerConfig.log(LoggerConfig.log_allTimes(), () -> "MatchContainer -> {");
		LoggerConfig.log(LoggerConfig.log_allTimes(), () -> "     addMatchTime:     " + df.format((double) addMatchTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_allTimes(), () -> "     getMatchTime:     " + df.format((double) getMatchTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_allTimes(), () -> "     matchAppliedTime: " + df.format((double) matchAppliedTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_allTimes(), () -> "     removeMatchTime:  " + df.format((double) removeMatchTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_allTimes(), () -> "}");
	}

}
