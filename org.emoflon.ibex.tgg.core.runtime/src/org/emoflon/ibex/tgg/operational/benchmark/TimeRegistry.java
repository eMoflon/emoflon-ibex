package org.emoflon.ibex.tgg.operational.benchmark;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.util.ConsoleUtil;

/**
 * This class is a central registry for all classes that are going to measure
 * times.<br>
 * To register a class and measure times proceed as follows:
 * <ol>
 * <li>Let the class implement {@link TimeMeasurable}.</li>
 * <li>Create a {@link Times} object in the class and return it in
 * {@link TimeMeasurable#getTimes()}.</li>
 * <li>Register the class in the constructor with
 * {@link TimeRegistry#register(TimeMeasurable)}.</li>
 * <li>Measure times with the {@link Timer} class and save them with
 * {@link Times#addTo(String, long)} or {@link Times#set(String, long)}.</li>
 * </ol>
 */
public class TimeRegistry {

	private static final Set<TimeMeasurable> registry = cfactory.createObjectSet();

	private static final DecimalFormat df = getFormat();
	private static final double giga = (double) (1000 * 1000 * 1000);

	private static DecimalFormat getFormat() {
		DecimalFormat df = new DecimalFormat("0.#####");
		df.setMaximumFractionDigits(5);
		return df;
	}

	public static void register(TimeMeasurable object) {
		registry.add(object);
	}

	public static void logTimes() {
		LoggerConfig.log(LoggerConfig.log_allTimes(), () -> {
			StringBuffer b = new StringBuffer();

			b.append("\nMeasured Times (sec) {\n");
			for (TimeMeasurable obj : registry) {
				b.append(ConsoleUtil.indent(print(obj), 2, true));
				b.append("\n");
			}
			b.append("}\n");

			return b.toString();
		});
	}

	private static String print(TimeMeasurable obj) {
		StringBuilder b = new StringBuilder();

		b.append(obj.getClass().getSimpleName());
		b.append(" {\n");
		Map<String, Long> times = obj.getTimes().getMap();
		int maxLength = getMaxLength(times.keySet());
		for (String name : times.keySet()) {
			b.append(ConsoleUtil.indent(print(name, times.get(name), maxLength), 2, true));
			b.append("\n");
		}
		b.append("}");

		return b.toString();
	}

	private static String print(String name, long time, int maxLength) {
		StringBuffer b = new StringBuffer();

		b.append(name);
		b.append(":");
		b.append(ConsoleUtil.indent(" ", maxLength - name.length(), true));
		b.append(df.format((double) time / giga));

		return b.toString();
	}

	private static int getMaxLength(Set<String> set) {
		int maxLength = 0;
		for (String s : set) {
			if (s.length() > maxLength)
				maxLength = s.length();
		}
		return maxLength;
	}

}
