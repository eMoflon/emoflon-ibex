package org.emoflon.ibex.tgg.operational.debug;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This is the configuration class for console logs. It can be configured by using the
 * Environment Variable "log" and a list of options as value.
 * <p>
 * Following options are available:
 * <ul>
 * <li>all</li>
 * <li>allTimes</li>
 * <li>matches</li>
 * <li>ruleApplication</li>
 * <li>repair</li>
 * <li>conflicts</li>
 * <li>ilp</li>
 * <li>precedenceGraph</li>
 * <li>matchApplicationTime</li>
 * <li>addMatchTime</li>
 * <li>collectMatchTime</li>
 * <li>translationTime</li>
 * <li>removalTime</li>
 * <li>repairTime</li>
 * </ul>
 * </p>
 * 
 * @author lfritsche
 * @author Adrian Möller
 *
 */
public final class LoggerConfig {

	private static Collection<String> envVars = getEnvironmentVariables();

	private static boolean log_all = getValue("all");
	private static boolean log_allTimes = getValue("allTimes");
	private static boolean log_matches = getValue("matches");
	private static boolean log_ruleApplication = getValue("ruleApplication");
	private static boolean log_repair = getValue("repair");
	private static boolean log_conflicts = getValue("conflicts");
	private static boolean log_ilp = getValue("ilp");
	private static boolean log_pg = getValue("precedenceGraph");
	private static boolean log_matchApplicationTime = getValue("matchApplicationTime");
	private static boolean log_addMatchTime = getValue("addMatchTime");
	private static boolean log_collectMatchTime = getValue("collectMatchTime");
	private static boolean log_translationTime = getValue("translationTime");
	private static boolean log_removalTime = getValue("removalTime");
	private static boolean log_repairTime = getValue("repairTime");

	public static boolean log_anything() {
		return log_all || log_allTimes || log_matches || log_ruleApplication || log_repair || log_conflicts || log_ilp || log_pg //
				|| log_matchApplicationTime || log_addMatchTime || log_collectMatchTime || log_translationTime || log_removalTime || log_repairTime;
	}

	public static boolean log_all() {
		return log_all;
	}

	public static boolean log_allTimes() {
		return log_all || log_allTimes;
	}

	public static boolean log_executionStructure() {
		return log_anything();
	}

	public static boolean log_matches() {
		return log_all || log_matches;
	}

	public static boolean log_ruleApplication() {
		return log_all || log_ruleApplication;
	}

	public static boolean log_repair() {
		return log_all || log_repair;
	}

	public static boolean log_conflicts() {
		return log_all || log_conflicts;
	}

	public static boolean log_ilp() {
		return log_all || log_ilp;
	}

	public static boolean log_pg() {
		return log_pg;
	}

	public static boolean log_matchApplicationTime() {
		return log_all || log_allTimes || log_matchApplicationTime;
	}

	public static boolean log_addMatchTime() {
		return log_all || log_allTimes || log_addMatchTime;
	}

	public static boolean log_collectMatchTime() {
		return log_all || log_allTimes || log_collectMatchTime;
	}

	public static boolean log_translationTime() {
		return log_all || log_allTimes || log_translationTime;
	}

	public static boolean log_removalTime() {
		return log_all || log_allTimes || log_removalTime;
	}

	public static boolean log_repairTime() {
		return log_all || log_allTimes || log_repairTime;
	}

	public static void log(boolean apply, Supplier<String> output) {
		if (apply)
			System.out.println(output.get());
	}

	private static Collection<String> getEnvironmentVariables() {
		try {
			String value = System.getenv("log");
			if (value == null)
				return Collections.emptyList();

			return Arrays.asList(value.split("[,; ]")).stream() //
					.map(s -> s.replaceAll(" ", "")) //
					.filter(s -> !s.isEmpty()) //
					.collect(Collectors.toList());
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private static boolean getValue(String param) {
		return envVars.contains(param);
	}

}
