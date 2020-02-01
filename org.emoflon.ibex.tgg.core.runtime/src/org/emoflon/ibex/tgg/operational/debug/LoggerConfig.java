package org.emoflon.ibex.tgg.operational.debug;

import java.util.function.Supplier;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LoggerConfig {
	
	boolean log_all = false;
	boolean log_allTimes = false;
	boolean log_incomingMatches = false;
	boolean log_matchApplication = false;
	boolean log_matchApplicationTime = false;
	boolean log_addMatchTime = false;
	boolean log_collectMatchTime = false;
	boolean log_translationTime = false;
	boolean log_removalTime = false;
	boolean log_repairTime = false;
	
	public LoggerConfig() {
		if(log_all()) {
			BasicConfigurator.configure();
			Logger.getRootLogger().setLevel(Level.DEBUG);
		}
		else {
			BasicConfigurator.configure();
			Logger.getRootLogger().setLevel(Level.INFO);
		}
	}
	
	private boolean log_all() {
		return log_all;
	}
	
	public boolean log_incomingMatches() {
		return log_all || log_incomingMatches;
	}
	
	public boolean log_matchApplication() {
		return log_all || log_matchApplication;
	}
	
	public boolean log_matchApplicationTime() {
		return log_all || log_allTimes || log_matchApplicationTime;
	}
	
	public boolean log_addMatchTime() {
		return log_all || log_allTimes || log_addMatchTime;
	}
	
	public boolean log_collectMatchTime() {
		return log_all || log_allTimes || log_collectMatchTime;
	}
	
	public boolean log_translationTime() {
		return log_all || log_allTimes || log_translationTime;
	}
	
	public boolean log_removalTime() {
		return log_all || log_allTimes || log_removalTime;
	}
	
	public boolean log_repairTime() {
		return log_all || log_allTimes || log_repairTime;
	}
	
	public boolean log_allTimes() {
		return log_all || log_allTimes;
	}
	
	public static void log(Logger logger, boolean apply, Supplier<String> output) {
		if(apply)
			logger.debug(output.get());
	}
}
