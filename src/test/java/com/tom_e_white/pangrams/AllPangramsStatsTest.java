package com.tom_e_white.pangrams;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AllPangramsStatsTest {

  @Test
  public void testStats() throws URISyntaxException, IOException {
    int[] rangeStart = new int[16];
    int[] rangeEnd = new int[16];
    Arrays.fill(rangeStart, Integer.MAX_VALUE);
    Arrays.fill(rangeEnd, Integer.MIN_VALUE);
    // read each line as a pangram
    Path path = Paths.get(ClassLoader.getSystemResource("pangrams.txt").toURI());
    for (String pangram : Files.lines(path).collect(Collectors.toList())) {
      int[] profile = Pangrams.extractProfile(Pangrams.countDeclared(pangram));
      for (int i = 0; i < 16; i++) {
        rangeStart[i] = Math.min(rangeStart[i], profile[i]);
        rangeEnd[i] = Math.max(rangeEnd[i], profile[i]);
      }
    }
    for (int i = 0; i < 16; i++) {
      char c = Pangrams.profileLetter(i);
      System.out.println(c + "," + rangeStart[i] + "-" + rangeEnd[i] + "," + (rangeEnd[i] - rangeStart[i] + 1));
    }
  }

}
