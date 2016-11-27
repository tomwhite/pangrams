package com.tom_e_white.pangrams;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class PangramsTest {
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
    String pangramTemplate =
        "This pangram contains five a's, one b, two c's, two d's, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, and one z.";
    // numbers are from Figure 72
    int[] extra =  { 7, 2, 2, 2, 4, 1, 10, 11, 2, 24, 7, 1, 2, 5, 1, 1};
    assertArrayEquals(extra, Pangrams.profile(pangramTemplate));
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
    String pangramTemplate =
        "This pangram contains four a's, one b, two c's, one d, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, & one z.";

    int[] rows =   { 30, 6, 5, 7, 11, 2, 18, 15, 5, 27, 18, 2, 7, 8, 2, 3 };
    int[] extra =  Pangrams.profile(pangramTemplate);
    int[] totals = rows; // since it is a pangram
    assertArrayEquals(totals, Pangrams.columnTotals(rows, extra));
  }

}
