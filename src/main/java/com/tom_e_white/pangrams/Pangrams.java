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
  
  public static int[] search(int[] rowStarts, int[] rowEnds, int[] additionalLetters) {
    int count = 0;
    int[] rows = new int[SIZE];
    for (int i0 = rowStarts[0]; i0 <= rowEnds[0]; i0++) {
      rows[0] = i0;
      for (int i1 = rowStarts[1]; i1 <= rowEnds[1]; i1++) {
        rows[1] = i1;
        for (int i2 = rowStarts[2]; i2 <= rowEnds[2]; i2++) {
          rows[2] = i2;
          for (int i3 = rowStarts[3]; i3 <= rowEnds[3]; i3++) {
            rows[3] = i3;
            for (int i4 = rowStarts[4]; i4 <= rowEnds[4]; i4++) {
              rows[4] = i4;
              for (int i5 = rowStarts[5]; i5 <= rowEnds[5]; i5++) {
                rows[5] = i5;
                for (int i6 = rowStarts[6]; i6 <= rowEnds[6]; i6++) {
                  rows[6] = i6;
                  for (int i7 = rowStarts[7]; i7 <= rowEnds[7]; i7++) {
                    rows[7] = i7;
                    for (int i8 = rowStarts[8]; i8 <= rowEnds[8]; i8++) {
                      rows[8] = i8;
                      for (int i9 = rowStarts[9]; i9 <= rowEnds[9]; i9++) {
                        rows[9] = i9;
                        for (int i10 = rowStarts[10]; i10 <= rowEnds[10]; i10++) {
                          rows[10] = i10;
                          for (int i11 = rowStarts[11]; i11 <= rowEnds[11]; i11++) {
                            rows[11] = i11;
                            for (int i12 = rowStarts[12]; i12 <= rowEnds[12]; i12++) {
                              rows[12] = i12;
                              for (int i13 = rowStarts[13]; i13 <= rowEnds[13]; i13++) {
                                rows[13] = i13;
                                for (int i14 = rowStarts[14]; i14 <= rowEnds[14]; i14++) {
                                  rows[14] = i14;
                                  for (int i15 = rowStarts[15]; i15 <= rowEnds[15]; i15++) {
                                    rows[15] = i15;
                                    int[] cols = columnTotals(rows, additionalLetters);
                                    count++;
                                    if (equals(rows, cols)) {
                                      System.out.println(count);
                                      return rows;
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return null;
  }
}
