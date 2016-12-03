package com.tom_e_white.pangrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

// TODO: try something like MTJ to get better perf:
// https://github.com/fommil/matrix-toolkits-java/blob/master/src/main/java/no/uib/cipr/matrix/Vector.java
// although doesn't seem to have anything native...
// similarly for jblas: http://mikiobraun.github.io/jblas/javadoc/index.html "jblas therefore uses Java implementation for things like vector addition"
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
    return Arrays.copyOf(p, p.length);
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

  public static final int[][][] PROFILE_DELTAS = computeProfileDeltas();

  private static int[][][] computeProfileDeltas() {
    int[][][] deltas = new int[PROFILES.length][PROFILES.length][SIZE];
    for (int i = 0; i < PROFILES.length; i++) {
      for (int j = 0; j < PROFILES.length; j++) {
        deltas[i][j] = minus(copy(PROFILES[j]), PROFILES[i]);
      }
    }
    return deltas;
  }

  public static int[] columnTotals(int[] rows, int[] additionalLetters) {
    int[] p = copy(additionalLetters);
    for (int r : rows) {
      add(p, PROFILES[r]);
    }
    return p;
  }

  public static int[] dependents(int rowStart, int rowEnd, char letter) {
    int letterIndex = Arrays.binarySearch(PROFILE_LETTERS, letter);
    Set<Integer> deps = new TreeSet<>();
    for (int i = rowStart; i <= rowEnd; i++) {
      if (PROFILES[i][letterIndex] == 0) {
        deps.add(-1);
      } else {
        deps.add(i);
      }
    }
    int[] depsArray = new int[deps.size()];
    int i = 0;
    for (int dep : deps) {
      depsArray[i++] = dep;
    }
    return depsArray;
  }

  public static void dump(int[] i) {
    System.out.println(Arrays.toString(i));
  }
  
  public static int[] search(int[] rowStarts, int[] rowEnds, int[] additionalLetters) {
    double searchSpaceSize = 1;
    for (int i = 0; i < SIZE; i++) {
      if (i == 2 || i == 5 || i == 15) {
        continue;
      }
      searchSpaceSize *= rowEnds[i] - rowStarts[i] + 1;
    }
    System.out.println("Search space size: " + searchSpaceSize);

    long startTime = System.nanoTime();
    long count = 0;
    int[] rows = copy(rowStarts);
    int[] cols = columnTotals(rows, additionalLetters);
    for (int i0 = rowStarts[0]; i0 <= rowEnds[0]; i0++) {
      if (rows[0] != i0) {
        add(cols, PROFILE_DELTAS[rows[0]][i0]);
        rows[0] = i0;
      }
      for (int i1 = rowStarts[1]; i1 <= rowEnds[1]; i1++) {
        if (rows[1] != i1) {
          add(cols, PROFILE_DELTAS[rows[1]][i1]);
          rows[1] = i1;
        }
//        for (int i2 = rowStarts[2]; i2 <= rowEnds[2]; i2++) {
//          if (rows[2] != i2) {
//            add(cols, PROFILE_DELTAS[rows[2]][i2]);
//            rows[2] = i2;
//          }
          for (int i3 = rowStarts[3]; i3 <= rowEnds[3]; i3++) {
            if (rows[3] != i3) {
              add(cols, PROFILE_DELTAS[rows[3]][i3]);
              rows[3] = i3;
            }
            for (int i4 = rowStarts[4]; i4 <= rowEnds[4]; i4++) {
              if (rows[4] != i4) {
                add(cols, PROFILE_DELTAS[rows[4]][i4]);
                rows[4] = i4;
              }
//              for (int i5 = rowStarts[5]; i5 <= rowEnds[5]; i5++) {
//                if (rows[5] != i5) {
//                  add(cols, PROFILE_DELTAS[rows[5]][i5]);
//                  rows[5] = i5;
//                }
                for (int i6 = rowStarts[6]; i6 <= rowEnds[6]; i6++) {
                  if (rows[6] != i6) {
                    add(cols, PROFILE_DELTAS[rows[6]][i6]);
                    rows[6] = i6;
                  }
                  for (int i7 = rowStarts[7]; i7 <= rowEnds[7]; i7++) {
                    if (rows[7] != i7) {
                      add(cols, PROFILE_DELTAS[rows[7]][i7]);
                      rows[7] = i7;
                    }
                    for (int i8 = rowStarts[8]; i8 <= rowEnds[8]; i8++) {
                      if (rows[8] != i8) {
                        add(cols, PROFILE_DELTAS[rows[8]][i8]);
                        rows[8] = i8;
                      }
                      for (int i9 = rowStarts[9]; i9 <= rowEnds[9]; i9++) {
                        if (rows[9] != i9) {
                          add(cols, PROFILE_DELTAS[rows[9]][i9]);
                          rows[9] = i9;
                        }
                        for (int i10 = rowStarts[10]; i10 <= rowEnds[10]; i10++) {
                          if (rows[10] != i10) {
                            add(cols, PROFILE_DELTAS[rows[10]][i10]);
                            rows[10] = i10;
                          }
                          for (int i11 = rowStarts[11]; i11 <= rowEnds[11]; i11++) {
                            if (rows[11] != i11) {
                              add(cols, PROFILE_DELTAS[rows[11]][i11]);
                              rows[11] = i11;
                            }
                            for (int i12 = rowStarts[12]; i12 <= rowEnds[12]; i12++) {
                              if (rows[12] != i12) {
                                add(cols, PROFILE_DELTAS[rows[12]][i12]);
                                rows[12] = i12;
                              }
                              for (int i13 = rowStarts[13]; i13 <= rowEnds[13]; i13++) {
                                if (rows[13] != i13) {
                                  add(cols, PROFILE_DELTAS[rows[13]][i13]);
                                  rows[13] = i13;
                                }
//                                for (int i14 = rowStarts[14]; i14 <= rowEnds[14]; i14++) {
//                                  if (rows[14] != i14) {
//                                    add(cols, PROFILE_DELTAS[rows[14]][i14]);
//                                    rows[14] = i14;
//                                  }

                                  // inner loop

                                  // count number of g's
                                  int numGs = cols[2];
                                  add(cols, PROFILE_DELTAS[rows[2]][numGs]);
                                  rows[2] = numGs;

                                  // count number of l's
                                  int numLs = cols[5];
                                  add(cols, PROFILE_DELTAS[rows[5]][numLs]);
                                  rows[5] = numLs;

                                  // count number of x's
                                  int numXs = cols[14];
                                  add(cols, PROFILE_DELTAS[rows[14]][numXs]);
                                  rows[14] = numXs;

                                  // count number of y's
                                  int numYs = cols[15];
                                  add(cols, PROFILE_DELTAS[rows[15]][numYs]);
                                  rows[15] = numYs;

                                  count++;
                                  if (equals(rows, cols)) {
                                    System.out.println(count);
                                    long endTime = System.nanoTime();
                                    System.out.println("Time (pangrams/s): " + 1.0 *
                                        1_000_000_000 *
                                        count /
                                        (endTime - startTime));
                                    return rows;
                                  }
                                //}
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                //}
              }
            }
          //}
        }
      }
    }
    long endTime = System.nanoTime();
    System.out.println("Time (pangrams/s): " + 1.0 *
        1_000_000_000 *
        count /
        (endTime - startTime));
    return null;
  }
}
