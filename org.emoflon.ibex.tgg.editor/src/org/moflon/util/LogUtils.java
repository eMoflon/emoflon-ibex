package org.moflon.util;

import java.util.Arrays;
import java.util.IllegalFormatException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This utility class simplifies the printing of log messages.
 * 
 * Its methods support format strings (a la {@link String#format(String, Object...)}.
 * 
 * Additionally, a method for conveniently printing {@link Throwable}s is provided (see
 * {@link #error(Logger, Throwable)}.
 */
public final class LogUtils
{
   public static void log(final Logger logger, Level level, final String formatString, final Object... arguments)
   {
      if (logger == null)
         return;

      if (logger.getLevel() == null || logger.getLevel().isGreaterOrEqual(level))
      {
         logger.log(level, formatStringFailsafe(formatString, arguments));
      }
   }

   public static void error(final Logger logger, final String formatString, final Object... arguments)
   {
      log(logger, Level.ERROR, formatString, arguments);
   }

   public static void error(final Logger logger, final Throwable t, final String formatString, final Object... arguments)
   {
      log(logger, Level.ERROR, "%s Reason:\n%s", String.format(formatString, arguments), WorkspaceHelper.printStacktraceToString(t));
   }

   /**
    * This method solely logs the stacktrace of the given {@link Throwable} as an {@link Level#ERROR}.
    * 
    * The purpose of this method is to avoid the infamous invocations of {@link Throwable#printStackTrace()}', which
    * hides critical problems from end-users.
    */
   public static void error(final Logger logger, final Throwable t)
   {
      error(logger, WorkspaceHelper.printStacktraceToString(t));
   }

   public static void warn(final Logger logger, final String formatString, final Object... arguments)
   {
      log(logger, Level.WARN, formatString, arguments);
   }

   public static void info(final Logger logger, final String formatString, final Object... arguments)
   {
      log(logger, Level.INFO, formatString, arguments);
   }

   public static void debug(final Logger logger, final String formatString, final Object... arguments)
   {
      log(logger, Level.DEBUG, formatString, arguments);
   }

   private static String formatStringFailsafe(final String formatString, final Object... arguments)
   {
      String str;
      try
      {
         str = String.format(formatString, arguments);
      } catch (IllegalFormatException e)
      {
         // Fallback in case something is wrong with the formatting string.
         str = String.format("Malformed format string for error message: formatString='%s', parameters='%s'", formatString, Arrays.deepToString(arguments));
      }
      return str;
   }
}
