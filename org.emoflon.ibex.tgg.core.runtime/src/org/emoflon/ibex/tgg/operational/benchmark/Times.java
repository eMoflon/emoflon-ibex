package org.emoflon.ibex.tgg.operational.benchmark;

import java.util.Map;
import java.util.TreeMap;

public class Times {

	private final Map<String, Long> times = new TreeMap<>();

	public void addTo(String name, long time) {
		if (times.containsKey(name))
			times.put(name, times.get(name) + time);
		else
			times.put(name, time);
	}

	public void set(String name, long time) {
		times.put(name, time);
	}
	
	public Map<String, Long> getMap() {
		return times;
	}

}
