package org.emoflon.ibex.tgg.operational.benchmark;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;

public class Times {

	private static volatile boolean ENABLED = false;

	private final Map<String, Long> times = new TreeMap<>();

	static {
		LoggerConfig.init();
	}

	/**
	 * Adds the given time to the entry with the given name.<br>
	 * <br>
	 * To optionally specify a parent entry, enter it's name before the entry name separated with a
	 * colon. The name parameter should then be in the form of
	 * <code>&lt;parentName&gt;:&lt;childName&gt;</code>.
	 * 
	 * @param name name of the entry
	 * @param time time to add
	 */
	public void addTo(String name, long time) {
		if (!ENABLED)
			return;

		if (times.containsKey(name))
			times.put(name, times.get(name) + time);
		else
			times.put(name, time);
	}

	/**
	 * Subtracts the given time from the entry with the given name.
	 * 
	 * @param name
	 * @param time
	 */
	public void subtractFrom(String name, long time) {
		if (!ENABLED)
			return;

		if (times.containsKey(name))
			times.put(name, times.get(name) - time);
		else
			times.put(name, 0 - time);
	}

	/**
	 * Sets the time of the entry with the given name to the given time.<br>
	 * <br>
	 * To optionally specify a parent entry, enter it's name before the entry name separated with a
	 * colon. The name parameter should then be in the form of
	 * <code>&lt;parentName&gt;:&lt;childName&gt;</code>.
	 * 
	 * @param name name of the entry
	 * @param time time to set
	 */
	public void set(String name, long time) {
		times.put(name, time);
	}

	public Map<String, Long> getMap() {
		return times;
	}

	public Set<TimeElt> getTree() {
		validateMap();

		Map<String, TimeElt> name2elt = new HashMap<>();
		Set<TimeElt> tree = new TreeSet<>();

		for (String name : times.keySet()) {
			String[] splittedName = name.split(":");

			TimeElt parent;
			if (name2elt.containsKey(splittedName[0])) {
				parent = name2elt.get(splittedName[0]);
			} else {
				parent = new TimeElt(splittedName[0]);
				name2elt.put(splittedName[0], parent);
			}

			if (splittedName.length == 2) {
				TimeElt child;
				if (name2elt.containsKey(splittedName[1])) {
					child = name2elt.get(splittedName[1]);
				} else {
					child = new TimeElt(splittedName[1]);
					name2elt.put(splittedName[1], child);
				}
				parent.childs.add(child);
				child.parent = parent;
			} else {
				tree.add(parent);
			}
		}

		name2elt.values().forEach(elt -> {
			if (elt.parent == null) {
				Long time = times.get(elt.name);
				if (time != null)
					elt.time = time;
				else
					tree.add(elt);
			} else {
				elt.time = times.get(elt.parent.name + ":" + elt.name);
			}
		});

		return tree;
	}

	private void validateMap() {
		List<String> childs = times.keySet().stream() //
				.map(name -> {
					String[] splittedName = name.split(":");
					if (splittedName.length > 2 || splittedName.length == 0)
						throw new RuntimeException("Times: syntax error in '" + name + "'!");
					return splittedName.length == 1 ? splittedName[0] : splittedName[1];
				}) //
				.collect(Collectors.toList());
		Set<String> temp = new HashSet<>();
		for (String s : childs) {
			if (!temp.add(s))
				throw new RuntimeException("Times: parent definitions for '" + s + "' are contradictory!");
		}
	}

	public void clear() {
		times.clear();
	}

	class TimeElt implements Comparable<TimeElt> {
		final String name;
		long time;
		final Set<TimeElt> childs;
		TimeElt parent;

		TimeElt(String name) {
			this.name = name;
			this.time = -1;
			this.childs = new TreeSet<>();
			this.parent = null;
		}

		@Override
		public int compareTo(TimeElt other) {
			return this.name.compareTo(other.name);
		}
	}

	/**
	 * Enables or disables the functionalities of this class, so they do not affect performance.
	 * 
	 * @param enabled
	 */
	public static void setEnabled(boolean enabled) {
		if (enabled || !TimeRegistry.isEnabled())
			Times.ENABLED = enabled;
	}

	/**
	 * 
	 * @return {@code true} if this class is enabled.
	 */
	public static boolean isEnabled() {
		return Times.ENABLED;
	}

}
