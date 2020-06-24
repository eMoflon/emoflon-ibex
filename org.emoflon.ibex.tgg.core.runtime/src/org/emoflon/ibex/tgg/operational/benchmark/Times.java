package org.emoflon.ibex.tgg.operational.benchmark;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Map;

public class Times {

	private final Map<String, Long> times = cfactory.createObjectToObjectHashMap();

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
