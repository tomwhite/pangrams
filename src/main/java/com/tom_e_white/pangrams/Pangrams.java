package com.tom_e_white.pangrams;

import java.util.Arrays;
import java.util.stream.Collectors;

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
    for (int i = 0; i < SIZE; i++) {
      p1[i] = p1[i] + p2[i];
    }
    return p1;
  }

  public static int[] minus(int[] p1, int[] p2) {
    for (int i = 0; i < SIZE; i++) {
      p1[i] = p1[i] - p2[i];
    }
    return p1;
  }

  public static int[] copy(int[] p) {
    int[] copy = new int[SIZE];
    for (int i = 0; i < SIZE; i++) {
      copy[i] = p[i];
    }
    return copy;
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

  public static final int[][] PROFILES = computeProfiles();

  private static int[][] computeProfiles() {
    String[] numbers = {
      "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
        "seventeen", "eighteen", "nineteen",
        "twenty", "twenty-one", "twenty-two", "twenty-three", "twenty-four",
        "twenty-five", "twenty-six", "twenty-seven", "twenty-eight", "twenty-nine",
        "thirty", "thirty-one", "thirty-two", "thirty-three", "thirty-four",
        "thirty-five", "thirty-six", "thirty-seven", "thirty-eight", "thirty-nine",
        "forty", "forty-one", "forty-two", "forty-three", "forty-four",
        "forty-five", "forty-six", "forty-seven", "forty-eight", "forty-nine",
    };
    return Arrays.stream(numbers)
        .map(Pangrams::profile)
        .collect(Collectors.toList())
        .toArray(new int[0][0]);
  }

  public static int[] columnTotals(int[] rows, int[] additionalLetters) {
    int[] p = copy(additionalLetters);
    for (int r : rows) {
      add(p, PROFILES[r]);
    }
    return p;
  }
}
