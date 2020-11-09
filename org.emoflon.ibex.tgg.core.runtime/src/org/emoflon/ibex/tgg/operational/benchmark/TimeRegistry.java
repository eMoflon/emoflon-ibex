package org.emoflon.ibex.tgg.operational.benchmark;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.benchmark.Times.TimeElt;
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
		if (!Timer.timerStack.isEmpty())
			throw new RuntimeException("Timer: there is still a timer running!");

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

		reset();
	}
	
	public static void reset() {
		registry.forEach(tm -> tm.getTimes().clear());
	}

	private static String print(TimeMeasurable obj) {
		StringBuilder b = new StringBuilder();

		b.append(obj.getClass().getSimpleName());
		b.append(" {\n");

		Map<TimeElt, Integer> elt2indent = serialize(obj.getTimes().getTree());

		Set<Integer> lengths = elt2indent.keySet().stream() //
				.map(e -> e.name.length() + elt2indent.get(e) * 2) //
				.collect(Collectors.toSet());
		int maxLength = getMaxLength(lengths);
		for (TimeElt elt : elt2indent.keySet()) {
			int hIndent = elt2indent.get(elt);
			b.append(ConsoleUtil.indent(print(elt.name, elt.time, maxLength - hIndent * 2, hIndent), 2, true));
			b.append("\n");
		}
		b.append("}");

		return b.toString();
	}

	private static String print(String name, long time, int maxLength, int hIndent) {
		StringBuffer b = new StringBuffer();

		for (int i = 0; i < hIndent; i++)
			b.append("| ");
		b.append(name);
		if (time != -1) {
			b.append(":");
			b.append(ConsoleUtil.indent(" ", maxLength - name.length(), true));
			b.append(df.format((double) time / giga));
		}

		return b.toString();
	}

	private static int getMaxLength(Set<Integer> set) {
		int maxLength = 0;
		for (Integer i : set) {
			if (i > maxLength)
				maxLength = i;
		}
		return maxLength;
	}

	private static Map<TimeElt, Integer> serialize(Set<TimeElt> elts) {
		Map<TimeElt, Integer> elt2indent = new LinkedHashMap<>();
		serialize(elt2indent, elts, 0);
		return elt2indent;
	}

	private static void serialize(Map<TimeElt, Integer> elt2indent, Set<TimeElt> elts, int indent) {
		for (TimeElt elt : elts) {
			elt2indent.put(elt, indent);
			serialize(elt2indent, elt.childs, indent + 1);
		}
	}

}
