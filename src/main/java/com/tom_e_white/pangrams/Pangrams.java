package com.tom_e_white.pangrams;

import java.util.Arrays;

public class Pangrams {

  private static final char[] PROFILE_LETTERS = new char[] {
    'e', 'f', 'g', 'h', 'i', 'l', 'n', 'o', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y'
  };

  private static final int SIZE = PROFILE_LETTERS.length;

  public static boolean equals(int[] p1, int[] p2) {
    for (int i = 0; i < SIZE; i++) {
      if (p1[i] != p2[i]) {
        return false;
      }
    }
    return true;
  }

  public static int[] add(int[] p1, int[] p2) {
    int[] p = new int[SIZE];
    for (int i = 0; i < SIZE; i++) {
      p[i] = p1[i] + p2[i];
    }
    return p;
  }

  public static int[] minus(int[] p1, int[] p2) {
    int[] p = new int[SIZE];
    for (int i = 0; i < SIZE; i++) {
      p[i] = p1[i] - p2[i];
    }
    return p;
  }

  public static int[] profile(String s) {
    int[] p = new int[SIZE];
    for (char c : s.toCharArray()) {
      int i = Arrays.binarySearch(PROFILE_LETTERS, Character.toLowerCase(c));
      if (i >= 0) {
        p[i]++;
      }
    }
    return p;
  }
}
