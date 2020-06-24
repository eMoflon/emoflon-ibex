package org.emoflon.ibex.tgg.operational.benchmark;

import java.util.Stack;

public class Timer {

	private static Stack<Long> timerStack = new Stack<>();
	
	public static void start() {
		timerStack.push(System.nanoTime());
	}
	
	public static long stop() {
		if (timerStack.isEmpty())
			return 0;
		return System.nanoTime() - timerStack.pop();
	}

}
