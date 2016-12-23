package com.tom_e_white.pangrams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class AllPangramsTest {

  @Parameterized.Parameters
  public static Collection<Object[]> data() throws URISyntaxException, IOException {
    // read each line as a pangram
    Path path = Paths.get(ClassLoader.getSystemResource("pangrams.txt").toURI());
    return Arrays.asList(new Object[][]{
        Files.lines(path).collect(Collectors.toList()).toArray()
    });
  }

  private String pangram;

  public AllPangramsTest(String pangram) {
    this.pangram = pangram;
  }

  @Test
  public void testIsPerfectPangram() {
    assertTrue(Pangrams.isPerfectPangram(pangram));
  }

  @Test
  public void testSearchRangeWouldHaveFoundActualPangram() {
    String prologue = Pangrams.extractPrologue(pangram);
    String connective = Pangrams.extractConnective(pangram);
    String pseudoPangram = Pangrams.createPseudoPangram(prologue, connective);
    SearchParameters searchParameters = Pangrams.getSearch(pseudoPangram);
    int[] declaredCounts = Pangrams.countDeclared(pangram);
    int[] pseudoDeclaredCounts = Pangrams.countDeclared(pseudoPangram, true);
    for (int i = 0; i < declaredCounts.length; i++) {
      if (pseudoDeclaredCounts[i] != -1) {
        // check the non-profile letter counts are the same as the pangram
        assertEquals(declaredCounts[i], pseudoDeclaredCounts[i]);
      }
    }
    // check the profile letter ranges for the search parameters contain the declared counts from the pangram
    int[] profile = Pangrams.extractProfile(declaredCounts);
    int[] rowStarts = searchParameters.getRowStarts();
    int[] rowEnds = searchParameters.getRowEnds();
    for (int i = 0; i < profile.length; i++) {
      assertTrue(profile[i] >= rowStarts[i]);
      assertTrue(profile[i] <= rowEnds[i]);
    }
  }

}
