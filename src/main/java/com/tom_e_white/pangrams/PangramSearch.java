package com.tom_e_white.pangrams;

public class PangramSearch {
  public static void main(String[] args) {
    String[] names = {
//        "Lottie White",
//        "Emilia White",
//        "Eliane Wigzell",
        "Tom White",
//        "Lottie",
//        "Emilia",
//        "Millie",
//        "Eliane",
//        "Tom",
    };
    for (String name : names) {
      String[] prologues = {
          String.format("This pangram for %s contains", name),
          String.format("This pangram for %s lists", name),
          String.format("This pangram for %s includes", name),
          String.format("This pangram for %s has", name),
          String.format("This pangram for %s uses", name),
          String.format("This pangram for %s features", name),
          String.format("This pangram for %s shows", name),
          String.format("This pangram for %s totals", name),
          String.format("This pangram for %s enumerates", name),
      };
      String[] connectives = {
          "and", "&"
      };
      for (String prologue : prologues) {
        for (String connective: connectives) {
          String pseudoPangram = Pangrams.createPseudoPangram(prologue, connective);
          SearchParameters search = Pangrams.getSearch(pseudoPangram);
          System.out.println("Searching for '" + prologue + "' ... '" + connective + "' ...");
          int[] profileCounts = Pangrams.search(search);
          if (profileCounts == null) {
            System.out.println("No pangram found");
          } else {
            String pangram = Pangrams.substituteProfile(pseudoPangram, profileCounts);
            if (Pangrams.isPerfectPangram(pangram)) {
              System.out.println("Eureka!");
              System.out.println(pangram);
            } else {
              System.out.println("Something went wrong, found");
              System.out.println(pangram);
              System.out.println("But it isn't a pangram...");
            }
          }
        }
      }
    }
  }
}
