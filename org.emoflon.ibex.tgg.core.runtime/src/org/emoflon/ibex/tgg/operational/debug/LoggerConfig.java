package org.emoflon.ibex.tgg.operational.debug;

import java.util.function.Supplier;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class LoggerConfig {
	
	private static Logger logger = Logger.getRootLogger();
	
	protected boolean log_all = false;
	protected boolean log_allTimes = false;
	protected boolean log_incomingMatches = false;
	protected boolean log_matchApplication = false;
	protected boolean log_matchApplicationTime = false;
	protected boolean log_addMatchTime = false;
	protected boolean log_collectMatchTime = false;
	protected boolean log_translationTime = false;
	protected boolean log_removalTime = false;
	protected boolean log_repairTime = false;
	
	public LoggerConfig() {
//		if(log_anything()) {
//			BasicConfigurator.configure();
//			Logger.getRootLogger().setLevel(Level.DEBUG);
//		}
//		else {
//			BasicConfigurator.configure();
//			Logger.getRootLogger().setLevel(Level.INFO);
//		}
	}
	
	private boolean log_anything() {
		return log_all || log_allTimes || log_incomingMatches || log_matchApplication || log_matchApplication || log_matchApplication
				|| log_addMatchTime || log_collectMatchTime || log_translationTime || log_removalTime || log_repairTime;
	}
	
	public boolean log_all() {
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
	
	public static void log(boolean apply, Supplier<String> output) {
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		if(apply)
			logger.debug(output.get());
	}
}
