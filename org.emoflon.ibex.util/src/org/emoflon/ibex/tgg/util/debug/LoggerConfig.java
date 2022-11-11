package org.emoflon.ibex.tgg.util.debug;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.smartemf.runtime.SmartEMFConfig;

/**
 * This is the configuration class for console logs. It can be configured by using the Environment
 * Variable "log" and a list of options as value.
 * <p>
 * Following options are available:
 * <ul>
 * <li>all</li>
 * <li>matches</li>
 * <li>ruleApplication</li>
 * <li>repair</li>
 * <li>conflicts</li>
 * <li>ilp</li>
 * <li>precedenceGraph</li>
 * <li>times</li>
 * <li>simple</li>
 * </ul>
 * </p>
 * 
 * @author lfritsche
 * @author Adrian Möller
 *
 */
public final class LoggerConfig {

	private static final Collection<String> envVars = getEnvironmentVariables();

	private static final boolean log_all = getValue("all");
	private static final boolean log_matches = getValue("matches");
	private static final boolean log_ruleApplication = getValue("ruleApplication");
	private static final boolean log_repair = getValue("repair");
	private static final boolean log_conflicts = getValue("conflicts");
	private static final boolean log_ilp = getValue("ilp");
	private static final boolean log_pg = getValue("precedenceGraph");
	private static final boolean log_times = getValue("times");
	private static final boolean simplified_logs = getValue("simple");

	static {
		TimeRegistry.setEnabled(log_times);
		SmartEMFConfig.setSimpleStringRepresentations(simplified_logs);
	}

	public static void init() {
		// NO-OP
	}

	public static boolean log_anything() {
		return log_all || log_matches || log_ruleApplication || log_repair || log_conflicts || log_ilp || log_pg;
	}

	public static boolean log_all() {
		return log_all;
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
