package org.emoflon.ibex.tgg.ui.ide.admin.plugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A class that makes manifest files more pretty, e.g., by placing dependencies and exported packages in a single line each.
 */
public class ManifestPrettyPrinter
{

   // Newline character used by Manifest.write
   public static final String MF_NL = "\r\n";

   private static final int MAX_CHARACTERS_PER_LINE = 70;

   public String print(final String content)
   {
      String[] lines = content.split(Pattern.quote(MF_NL));
      StringBuilder sb = new StringBuilder();
      for (int lineNumber = 0; lineNumber < lines.length; ++lineNumber)
      {
         final String line = lines[lineNumber];
         String[] keyAndValue = line.split(":", 2);
         String key = keyAndValue[0];

         if (key.equals(PluginManifestConstants.EXPORT_PACKAGE_KEY) || key.equals(PluginManifestConstants.REQUIRE_BUNDLE_KEY))
         {
            String value = keyAndValue[1];
            for (int entryLines = lineNumber + 1; entryLines < lines.length && lines[entryLines].startsWith(" "); ++entryLines)
            {
               value += lines[entryLines].trim();
               lineNumber++;
            }
            String[] dependencies = value.split(",");
            sb.append(shortenToMaximumWidth(key + ":" + dependencies[0]));
            if (dependencies.length > 1)
            {
               sb.append(",");
               sb.append(MF_NL);
               sb.append(Arrays.asList(dependencies).subList(1, dependencies.length).stream().map(ManifestPrettyPrinter::normalizeIndentedLine)
                     .map(ManifestPrettyPrinter::shortenToMaximumWidth).collect(Collectors.joining("," + MF_NL)));
            }
         } else
         {
            sb.append(line);
         }
         sb.append(MF_NL);
      }
      return sb.toString();
   }

   private static String normalizeIndentedLine(final String input)
   {
      return " " + input.trim();
   }

   private static String shortenToMaximumWidth(final String input)
   {
      String temp = input;
      List<String> output = new ArrayList<>();
      while (temp.length() > MAX_CHARACTERS_PER_LINE)
      {
         output.add(temp.substring(0, MAX_CHARACTERS_PER_LINE));
         temp = " " + temp.substring(MAX_CHARACTERS_PER_LINE);
      }
      if (!temp.isEmpty())
      {
         output.add(temp);
      }
      String formattedOutput = output.stream().collect(Collectors.joining(MF_NL));
      return formattedOutput;
   }
}
