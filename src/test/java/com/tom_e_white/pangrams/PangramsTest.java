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
}
