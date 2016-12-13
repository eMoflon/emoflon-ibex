package org.emoflon.ibex.tgg.operational.csp.generator;


public class Generator
{

   private static int count = 0;

   private static LoremIpsum loremIpsum = new LoremIpsum();

   public static String getNewRandomString(final String type)
   {

      if (type == null)
      {
         return loremIpsum.randomWord();
      }

      String tLower = type.toLowerCase();

      if (tLower.equals("double") || tLower.equals("int") || tLower.equals("float") || tLower.equals("number"))
      {
         return "" + count++;
      }
      return loremIpsum.randomWord();
   }

   public static int getNewUniqueNumber()
   {
      return count++;
   }

}
