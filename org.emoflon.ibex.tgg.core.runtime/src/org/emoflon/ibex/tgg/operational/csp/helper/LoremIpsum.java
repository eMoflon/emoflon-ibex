package org.emoflon.ibex.tgg.operational.csp.helper;

import java.util.Random;

/*
 * Copyright 2010 Oliver C Dodd http://01001111.net
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
public class LoremIpsum
{
	
   private static LoremIpsum instance;
   
   private LoremIpsum(){
	   
   }
   
   public static LoremIpsum getInstance(){
	   if(instance == null)
		   instance = new LoremIpsum();
	   return instance;
   }

   /*
    * The Lorem Ipsum Standard Paragraph
    */
   protected final String standard = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

   private final String[] words = { "a", "ac", "accumsan", "ad", "adipiscing", "aenean", "aliquam", "aliquet", "amet", "ante", "aptent", "arcu", "at",
         "auctor", "augue", "bibendum", "blandit", "class", "commodo", "condimentum", "congue", "consectetur", "consequat", "conubia", "convallis", "cras",
         "cubilia", "cum", "curabitur", "curae", "cursus", "dapibus", "diam", "dictum", "dictumst", "dignissim", "dis", "dolor", "donec", "dui", "duis",
         "egestas", "eget", "eleifend", "elementum", "elit", "enim", "erat", "eros", "est", "et", "etiam", "eu", "euismod", "facilisi", "facilisis", "fames",
         "faucibus", "felis", "fermentum", "feugiat", "fringilla", "fusce", "gravida", "habitant", "habitasse", "hac", "hendrerit", "himenaeos", "iaculis",
         "id", "imperdiet", "in", "inceptos", "integer", "interdum", "ipsum", "justo", "lacinia", "lacus", "laoreet", "lectus", "leo", "libero", "ligula",
         "litora", "lobortis", "lorem", "luctus", "maecenas", "magna", "magnis", "malesuada", "massa", "mattis", "mauris", "metus", "mi", "molestie", "mollis",
         "montes", "morbi", "mus", "nam", "nascetur", "natoque", "nec", "neque", "netus", "nibh", "nisi", "nisl", "non", "nostra", "nulla", "nullam", "nunc",
         "odio", "orci", "ornare", "parturient", "pellentesque", "penatibus", "per", "pharetra", "phasellus", "placerat", "platea", "porta", "porttitor",
         "posuere", "potenti", "praesent", "pretium", "primis", "proin", "pulvinar", "purus", "quam", "quis", "quisque", "rhoncus", "ridiculus", "risus",
         "rutrum", "sagittis", "sapien", "scelerisque", "sed", "sem", "semper", "senectus", "sit", "sociis", "sociosqu", "sodales", "sollicitudin", "suscipit",
         "suspendisse", "taciti", "tellus", "tempor", "tempus", "tincidunt", "torquent", "tortor", "tristique", "turpis", "ullamcorper", "ultrices",
         "ultricies", "urna", "ut", "varius", "vehicula", "vel", "velit", "venenatis", "vestibulum", "vitae", "vivamus", "viverra", "volutpat", "vulputate" };

   private final String[] punctuation = { ".", "?" };

   private final String _n = System.getProperty("line.separator");

   private Random random = new Random();

   /**
    * Get a random word
    */
   public String randomWord()
   {
      return words[random.nextInt(words.length - 1)];
   }

   /**
    * Get a random punctuation mark
    */
   public String randomPunctuation()
   {
      return punctuation[random.nextInt(punctuation.length - 1)];
   }

   /**
    * Get a string of words
    * 
    * @param count
    *           - the number of words to fetch
    */
   public String words(int count)
   {
      StringBuilder s = new StringBuilder();
      while (count-- > 0)
         s.append(randomWord()).append(" ");
      return s.toString().trim();
   }

   /**
    * Get a sentence fragment
    */
   public String sentenceFragment()
   {
      return words(random.nextInt(10) + 3);
   }

   /**
    * Get a sentence
    */
   public String sentence()
   {
      // first word
      String w = randomWord();
      StringBuilder s = new StringBuilder(w.substring(0, 1).toUpperCase()).append(w.substring(1)).append(" ");
      // commas?
      if (random.nextBoolean())
      {
         int r = random.nextInt(3) + 1;
         for (int i = 0; i < r; i++)
            s.append(sentenceFragment()).append(", ");
      }
      // last fragment + punctuation
      return s.append(sentenceFragment()).append(randomPunctuation()).toString();
   }

   /**
    * Get multiple sentences
    * 
    * @param count
    *           - the number of sentences
    */
   public String sentences(int count)
   {
      StringBuilder s = new StringBuilder();
      while (count-- > 0)
         s.append(sentence()).append("  ");
      return s.toString().trim();
   }

   /**
    * Get a paragraph
    * 
    * @useStandard - get the standard Lorem Ipsum paragraph?
    */
   public String paragraph(boolean useStandard)
   {
      return useStandard ? standard : sentences(random.nextInt(3) + 2);
   }

   public String paragraph()
   {
      return paragraph(false);
   }

   /**
    * Get multiple paragraphs
    * 
    * @param count
    *           - the number of paragraphs
    * @useStandard - begin with the standard Lorem Ipsum paragraph?
    */
   public String paragraphs(int count, boolean useStandard)
   {
      StringBuilder s = new StringBuilder();
      while (count-- > 0)
      {
         s.append(paragraph(useStandard)).append(_n).append(_n);
         useStandard = false;
      }
      return s.toString().trim();
   }

   public String paragraphs(int count)
   {
      return paragraphs(count, false);
   }
}