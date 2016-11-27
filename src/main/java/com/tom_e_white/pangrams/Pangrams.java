package com.tom_e_white.pangrams;

import java.util.Arrays;

public class Pangrams {

  private static final char[] PROFILE_LETTERS = new char[] {
    'e', 'f', 'g', 'h', 'i', 'l', 'n', 'o', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y'
  };

  public static int[] profile(String s) {
    int[] p = new int[16];
    for (char c : s.toCharArray()) {
      int i = Arrays.binarySearch(PROFILE_LETTERS, Character.toLowerCase(c));
      if (i >= 0) {
        p[i]++;
      }
    }
    return p;
  }
}
