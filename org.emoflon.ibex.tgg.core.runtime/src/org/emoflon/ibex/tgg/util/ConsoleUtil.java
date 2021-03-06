package org.emoflon.ibex.tgg.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.emoflon.ibex.common.operational.IMatch;

/**
 * @author AdrianM
 */
public class ConsoleUtil {

	// TODO adrianm: save printed stuff for retrieving later
	private static final PrintStream dummyPrintStream = new PrintStream(new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			// NO-OP
		}
	});

	public interface ThrowingSupplier<T, E extends Exception> {
		T get() throws E;
	}

	public interface ThrowingVoidSupplier<E extends Exception> {
		void get() throws E;
	}

	/**
	 * Suppresses unwanted prints to <code>System.out</code> by replacing the current
	 * <code>PrintStream</code> by a dummy for the duration of the given operation.
	 * 
	 * @param <T>       type of the operation's return value
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

	public static <E extends Exception> void suppressUnwantedPrints(ThrowingVoidSupplier<E> operation) throws E {
		PrintStream currentPrintStream = System.out;
		System.setOut(dummyPrintStream);
		operation.get();
		System.setOut(currentPrintStream);
	}

	public static String indent(String s, int indent, boolean indentFirstLine) {
		String i = "";
		for (int j = 0; j < indent; j++) {
			i += " ";
		}
		return (indentFirstLine ? i : "") + s.replace("\n", "\n" + i);
	}

	public static String printMatchParameter(IMatch match) {
		StringBuffer b = new StringBuffer();

		List<String> paramNames = new ArrayList<>(match.getParameterNames());
		for (int i = 0; i < paramNames.size(); i++) {
			String param = paramNames.get(i);
			if (i == paramNames.size() - 1)
				b.append("'- ");
			else
				b.append("|  ");
			b.append(param);
			b.append(" -> ");
			b.append(match.get(param));
			if (i != paramNames.size() - 1)
				b.append("\n");
		}
		return b.toString();
	}

}
