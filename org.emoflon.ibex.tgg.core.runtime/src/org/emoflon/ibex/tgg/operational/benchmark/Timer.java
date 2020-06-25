package org.emoflon.ibex.tgg.operational.benchmark;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Timer {

	protected static Stack<Long> timerStack = new Stack<>();

	protected static final boolean debug = false;
	protected static Map<Long, String> callers = new HashMap<>();

	public static void start() {
		long t = System.nanoTime();
		timerStack.push(t);
		
		if (debug)
			callers.put(t, getCaller());
	}

	public static long stop() {
		if (timerStack.isEmpty())
			throw new RuntimeException("Timer: can not stop a not running timer!");
		long t = timerStack.pop();
		
		if (debug) {
			if (callers.get(t).equals(getCaller()))
				callers.remove(t);
			else
				throw new RuntimeException(
						"Timer: timer has to be stopped in the same method it was started: " + callers.get(t));
		}
		
		return System.nanoTime() - t;
	}

	private static String getCaller() {
		return Thread.currentThread().getStackTrace()[3].getClassName() + "#"
				+ Thread.currentThread().getStackTrace()[3].getMethodName();
	}

}
