package org.emoflon.ibex.tgg.util.benchmark;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Timer {

	private static volatile boolean ENABLED = false;

	protected static Stack<Long> timerStack = new Stack<>();

	protected static final boolean debug = false;
	protected static Map<Long, String> callers = new HashMap<>();

	public static void start() {
		if (!ENABLED)
			return;

		long t = System.nanoTime();
		timerStack.push(t);

		if (debug)
			callers.put(t, getCaller());
	}

	public static long stop() {
		if (!ENABLED)
			return -1;

		if (timerStack.isEmpty())
			throw new RuntimeException("Timer: can not stop a not running timer!");
		long t = timerStack.pop();

		if (debug) {
			if (callers.get(t).equals(getCaller()))
				callers.remove(t);
			else
				throw new RuntimeException("Timer: timer has to be stopped in the same method it was started: " + callers.get(t));
		}

		return System.nanoTime() - t;
	}

	private static String getCaller() {
		return Thread.currentThread().getStackTrace()[3].getClassName() + "#" //
				+ Thread.currentThread().getStackTrace()[3].getMethodName();
	}

	/**
	 * Enables or disables the functionalities of this class, so they do not affect performance.
	 * 
	 * @param enabled
	 */
	public static void setEnabled(boolean enabled) {
		if (enabled || !TimeRegistry.isEnabled())
			Timer.ENABLED = enabled;
	}

	/**
	 * 
	 * @return {@code true} if the {@link TimeRegistry} is enabled.
	 */
	public static boolean isEnabled() {
		return Timer.ENABLED;
	}

}
