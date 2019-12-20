package org.emoflon.ibex.tgg.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author AdrianM
 */
public class ConsoleUtil {

	private static final PrintStream dummyPrintStream = new PrintStream(new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			// NO-OP
		}
	});
	
	public interface ThrowingSupplier<T, E extends Exception> {
		T get() throws E;
	}

	/**
	 * Suppresses unwanted prints to <code>System.out</code> by replacing the
	 * current <code>PrintStream</code> by a dummy for the duration of the given
	 * operation.
	 * 
	 * @param <T> type of the operation's return value
	 * @param operation operation that produces unwanted prints
	 * @return return value of the given operation if present
	 */
	public static <T, E extends Exception> T suppressUnwantedPrints(ThrowingSupplier<T, E> operation) throws E {
		PrintStream currentPrintStream = System.out;
		System.setOut(dummyPrintStream);
		T result = operation.get();
		System.setOut(currentPrintStream);
		return result;
	}

}
