package com.tom_e_white.pangrams;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PangramsTest {
  @Test
  public void testIsPerfectPangram() {
    String pangram =
        "This pangram lists four a's, one b, one c, two d's, twenty-nine e's, eight f's," +
        " three g's, five h's, eleven i's, one j, one k, three l's, two m's, twenty-two" +
        " n's, fifteen o's, two p's, one q, seven r's, twenty-six s's, nineteen t's, " +
        "four u's, five v's, nine w's, two x's, four y's, and one z.";
    assertTrue(Pangrams.isPerfectPangram(pangram));
  }

  @Test
  public void testProfile() {
    //                            e  f  g  h  i  l  n  o  r  s  t  u  v  w  x  y
    assertArrayEquals(new int[] { 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, Pangrams.profile("one"));
    assertArrayEquals(new int[] { 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, Pangrams.profile("ONE"));
    assertArrayEquals(new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, Pangrams.profile("two"));
    assertArrayEquals(new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, Pangrams.profile("abcz"));
  }

  @Test
  public void testProfiles() {
    assertArrayEquals(Pangrams.profile("zero"), Pangrams.PROFILES[0]);
    assertArrayEquals(Pangrams.profile("one"), Pangrams.PROFILES[1]);
    assertArrayEquals(Pangrams.profile("two"), Pangrams.PROFILES[2]);
    assertArrayEquals(Pangrams.profile("three"), Pangrams.PROFILES[3]);
  }

  @Test
  public void testAdditionalLetters() {
    // from p208
    String pseudoPangram =
        "This pangram contains five a's, one b, two c's, two d's, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, and one z.";
    // numbers are from Figure 72
    int[] extra =  { 7, 2, 2, 2, 4, 1, 10, 11, 2, 24, 7, 1, 2, 5, 1, 1};
    assertArrayEquals(extra, Pangrams.profile(pseudoPangram));
  }

  @Test
  public void testColumnTotals() {
    // numbers are from Figure 72
    int[] rows =   { 27, 6, 3, 5, 11, 2, 20, 14, 6, 28, 29, 3, 6, 10, 4, 5 };
    int[] extra =  { 7, 2, 2, 2, 4, 1, 10, 11, 2, 24, 7, 1, 2, 5, 1, 1};
    int[] totals = { 27, 6, 3, 5, 11, 2, 20, 14, 6, 28, 21, 3, 6, 10, 4, 5 };
    assertArrayEquals(totals, Pangrams.columnTotals(rows, extra));
  }

  @Test
  public void testColumnTotalsMatch() {
    // from p209, subtly different to the one on p208, since 'and' has become '&'
    String pseudoPangram =
        "This pangram contains four a's, one b, two c's, one d, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, & one z.";

    int[] rows =   { 30, 6, 5, 7, 11, 2, 18, 15, 5, 27, 18, 2, 7, 8, 2, 3 };
    int[] extra =  Pangrams.profile(pseudoPangram);
    int[] totals = rows; // since it is a pangram
    assertArrayEquals(totals, Pangrams.columnTotals(rows, extra));
  }

  @Test
  public void testSearch() {
    // from p209
    String pseudoPangram =
        "This pangram contains four a's, one b, two c's, one d, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, & one z.";
    int[] rowStarts = { 23,  1,  1,  1,  6, 2, 18, 15, 5, 27, 18, 2, 7, 8, 2, 2 };
    int[] rowEnds =   { 32, 10, 10, 10, 15, 2, 18, 15, 5, 27, 18, 2, 7, 8, 2, 5 };
    int[] extra =  Pangrams.profile(pseudoPangram);
    int[] rows =   { 30, 6, 5, 7, 11, 2, 18, 15, 5, 27, 18, 2, 7, 8, 2, 3 };
    assertArrayEquals(rows, Pangrams.search(rowStarts, rowEnds, extra));
  }

  //@Test
  public void testSearchWithSallowsRanges() {
    // from p18 Sallows
    // running at 1.4632726613672342E7 pangrams/s
    // changed to start from 2 to avoid 's' problems (not sure this is an actual
    // problem though)
    String pseudoPangram =
        "This pangram lists four a's, one b, one c, two d's, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, and one z.";
    int[] rowStarts = { 25, 4, 2, 3,  8, 2, 17, 12, 3, 24, 18, 2, 3,  7, 2, 3 };
    int[] rowEnds =   { 32, 9, 7, 8, 14, 4, 23, 17, 8, 30, 24, 6, 8, 13, 5, 5 };
    int[] extra =  Pangrams.profile(pseudoPangram);
    int[] rows =   { 29, 8, 3, 5, 11, 3, 22, 15, 7, 26, 19, 4, 5, 9, 2, 4 };
    assertArrayEquals(rows, Pangrams.search(rowStarts, rowEnds, extra));
  }

  @Test
  public void testCreatePseudoPangram() {
    // from p208
    String pseudoPangram =
        "This pangram contains five a's, one b, two c's, two d's, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, and one z.";
    assertEquals(pseudoPangram, Pangrams.createPseudoPangram("This pangram contains", "and"));
  }

  @Test
  public void testSearchParameters() {
    // from p18 Sallows
    String pseudoPangram =
        "This pangram lists four a's, one b, one c, two d's, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, and one z.";
    assertEquals(pseudoPangram, Pangrams.createPseudoPangram("This pangram lists", "and"));

    int[] rowStarts = { 25, 4, 2, 3,  8, 2, 17, 12, 3, 24, 18, 2, 3,  7, 2, 3 };
    int[] rowEnds =   { 32, 9, 7, 8, 14, 4, 23, 17, 8, 30, 24, 6, 8, 13, 5, 5 };

    SearchParameters searchParameters = Pangrams.getSearch(pseudoPangram);

    assertArrayEquals(rowStarts, searchParameters.getRowStarts());
    assertArrayEquals(rowEnds, searchParameters.getRowEnds());

  }

  @Test
  public void testExtractPrologueAndConnective() {
    String pangram =
        "This pangram lists four a's, one b, one c, two d's, twenty-nine e's, eight f's," +
            " three g's, five h's, eleven i's, one j, one k, three l's, two m's, twenty-two" +
            " n's, fifteen o's, two p's, one q, seven r's, twenty-six s's, nineteen t's, " +
            "four u's, five v's, nine w's, two x's, four y's, and one z.";
    assertEquals("This pangram lists", Pangrams.extractPrologue(pangram));
    assertEquals("and", Pangrams.extractConnective(pangram));

    // TODO: create a pseudo-pangram, and check the non-profile letter counts are the same as the pangram
    // TODO: check the profile letter ranges for the search parameters contain the declared counts from the pangram
    // TODO: run this test for all the Nth pangrams
  }
}
