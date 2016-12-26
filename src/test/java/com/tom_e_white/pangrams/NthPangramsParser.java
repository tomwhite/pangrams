package com.tom_e_white.pangrams;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class NthPangramsParser {
  public static void main(String[] args) throws Exception {
    Path path = Paths.get(ClassLoader.getSystemResource("nth-pangrams.csv").toURI());
    Files.lines(path).map(line -> {
      String[] split = line.split(",");
      String allCounts = split[0];
      String prologue = split[1];
      String connective = split[2];
      int[] counts = Arrays.asList(allCounts.split(" +")).stream().mapToInt(s -> Integer.parseInt(s)).toArray();
      String pseudoPangram = String.format(
          "%s ? a's, ? b's, ? c's, ? d's, ? e's, ? f's, ? g's, " +
              "? h's, ? i's, ? j's, ? k's, ? l's, ? m's, ? n's, ? o's, ? p's, ? q's, " +
              "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, %s ? z's.",
          prologue, connective);
      return Pangrams.substituteLetters(pseudoPangram, counts);
    }).forEach(System.out::println);
  }
}
