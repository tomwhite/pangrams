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
  public void testColumnTotals() {
    // numbers are from Figure 72
    int[] rows =   { 27, 6, 3, 5, 11, 2, 20, 14, 6, 28, 29, 3, 6, 10, 4, 5 };
    int[] extra =  { 7, 2, 2, 2, 4, 1, 10, 11, 2, 24, 7, 1, 2, 5, 1, 1};
    int[] totals = { 27, 6, 3, 5, 11, 2, 20, 14, 6, 28, 21, 3, 6, 10, 4, 5 };
    assertArrayEquals(totals, Pangrams.columnTotals(rows, extra));
  }
}
