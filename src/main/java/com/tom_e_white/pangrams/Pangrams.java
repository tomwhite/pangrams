package com.tom_e_white.pangrams;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

  private static final char[] ALL_LETTERS = new char[] {
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
  };

  private static final String[] NUMBERS = new String[]{
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


  public static char profileLetter(int i) {
    return PROFILE_LETTERS[i];
  }

  public static char letter(int i) {
    return ALL_LETTERS[i];
  }

  /**
   * Test if two profiles are equal.
   */
  private static boolean equals(int[] p1, int[] p2) {
    for (int i = 0; i < SIZE; i++) {
      if (p1[i] != p2[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Add profile p2 to p1, mutating p1.
   */
  private static void add(int[] p1, int[] p2) {
    for (int i = 0; i < SIZE; i++) {
      p1[i] = p1[i] + p2[i];
    }
  }

  /**
   * Take profile p2 from p1, mutating p1.
   */
  private static int[] minus(int[] p1, int[] p2) {
    for (int i = 0; i < SIZE; i++) {
      p1[i] = p1[i] - p2[i];
    }
    return p1;
  }

  /**
   * Create a new copy of a profile.
   */
  private static int[] copy(int[] p) {
    return Arrays.copyOf(p, p.length);
  }

  private static int parseNumber(String n) {
    for (int i = 0; i < NUMBERS.length; i++) {
      if (NUMBERS[i].equals(n)) {
        return i;
      }
    }
    throw new IllegalArgumentException("Number not recognized: " + n);
  }

  /**
   * Turn an array of letter counts for all letters into a profile array.
   */
  public static int[] extractProfile(int[] all) {
    int[] p = new int[SIZE];
    int i = 0;
    for (char c : PROFILE_LETTERS) {
      int index = Arrays.binarySearch(ALL_LETTERS, c);
      p[i++] = all[index];
    }
    return p;
  }

  /**
   * @return an array of letter counts in the given string
   */
  public static int[] count(String s) {
    int[] p = new int[ALL_LETTERS.length];
    for (char c : s.toCharArray()) {
      int i = Arrays.binarySearch(ALL_LETTERS, Character.toLowerCase(c));
      if (i >= 0) {
        p[i]++;
      }
    }
    return p;
  }

  /**
   * @return an array of declared letter counts in the given string
   */
  public static int[] countDeclared(String s) {
    return countDeclared(s, false);
  }
  /**
   * @return an array of declared letter counts in the given string
   */
  public static int[] countDeclared(String s, boolean allowMissingCounts) {
    String sl = s.toLowerCase();
    int[] declaredCounts = new int[ALL_LETTERS.length];
    for (int i = 0; i < ALL_LETTERS.length; i++) {
      char l = ALL_LETTERS[i];
      Pattern pattern = Pattern.compile("([a-z\\-]+) " + l + "'s");
      Matcher matcher = pattern.matcher(sl);
      int number;
      if (matcher.find()) {
        String englishNumber = matcher.group(1);
        if ("one".equals(englishNumber)) {
          throw new IllegalArgumentException("Grammatical error: " + matcher.group());
        }
        number = parseNumber(englishNumber);
      } else if (sl.contains("one " + l)) {
        number = 1;
      } else if (allowMissingCounts) {
        number = -1;
      } else {
        throw new IllegalArgumentException("Missing count for " + l);
      }
      declaredCounts[i] = number;
    }
    return declaredCounts;
  }

  /**
   * @return a score measuring how close a candidate sentence is to being a pangram, 0 = perfect, >0 is total absolute sum of errors.
   */
  public static int pangramScore(String pangramCandidate) {
    int[] declaredCounts = countDeclared(pangramCandidate);
    int[] counts = count(pangramCandidate);
    int score = 0;
    for (int i = 0; i < ALL_LETTERS.length; i++) {
      score += Math.abs(declaredCounts[i] - counts[i]);
    }
    return score;
  }

  /**
   * @return true if the candidate sentence is a perfect pangram, false otherwise.
   */
  public static boolean isPerfectPangram(String pangramCandidate) {
    return pangramScore(pangramCandidate) == 0;
  }

  public static String createPseudoPangram(String prologue, String connective) {
    String pseudoPangram = String.format(
        "%s ? a's, ? b's, ? c's, ? d's, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, ? j's, ? k's, ? l's, ? m's, ? n's, ? o's, ? p's, ? q's, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, %s ? z's.",
        prologue, connective);
    int[] counts = count(pseudoPangram);
    for (int i = 0; i < ALL_LETTERS.length; i++) {
      char l = ALL_LETTERS[i];
      if (Arrays.binarySearch(PROFILE_LETTERS, l) >= 0) {
        continue;
      }
      pseudoPangram = pseudoPangram.replace("? " + l + (counts[i] == 1 ? "'s" : ""), NUMBERS[counts[i]] + " " + l);
    }
    return pseudoPangram;
  }

  public static String extractPrologue(String pangram) {
    Pattern pattern = Pattern.compile("(?<prologue>.+) \\S+ a's");
    Matcher matcher = pattern.matcher(pangram);
    if (matcher.find()) {
      return matcher.group("prologue");
    }
    return null;
  }

  public static String extractConnective(String pangram) {
    Pattern pattern = Pattern.compile("y's,? (?<connective>.+) (one z|\\S+ z's)");
    Matcher matcher = pattern.matcher(pangram);
    if (matcher.find()) {
      return matcher.group("connective");
    }
    return null;
  }

  public static SearchParameters getSearch(String pseudoPangram) {
    // from p18 Sallows
    int[] rowStarts = { 25, 4, 2, 3,  8, 1, 17, 12, 3, 24, 18, 2, 3,  7, 1, 3 };
    int[] rowEnds =   { 32, 9, 7, 8, 14, 4, 23, 17, 8, 30, 24, 6, 8, 13, 5, 5 };
    String sallowsPseudoPangram =
        "This pangram lists four a's, one b, one c, two d's, ? e's, ? f's, ? g's, " +
            "? h's, ? i's, one j, one k, ? l's, two m's, ? n's, ? o's, two p's, one q, " +
            "? r's, ? s's, ? t's, ? u's, ? v's, ? w's, ? x's, ? y's, and one z.";

    // TODO: figure out how to adjust row starts and ends
    //minus(rowStarts, profile(sallowsPseudoPangram));
    //add(rowStarts, profile(pseudoPangram));

    //minus(rowEnds, profile(sallowsPseudoPangram));
    //add(rowEnds, profile(pseudoPangram));

    return new SearchParameters(rowStarts, rowEnds, profile(pseudoPangram));
  }

  /**
   * A profile is an array of letter counts for the profile letters - i.e. those that appear in the English language
   * number words ("one", "two", "three", etc). These are
   'e', 'f', 'g', 'h', 'i', 'l', 'n', 'o', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y'.
   */
  public static int[] profile(String s) {
    int[] p = new int[SIZE];
    for (char c : s.toCharArray()) {
      int i = Arrays.binarySearch(PROFILE_LETTERS, Character.toLowerCase(c));
      if (i >= 0) {
        p[i]++;
      }
    }
    if (s.equalsIgnoreCase("one")) {
      p[9]--; // reduce count in the s-position to account for singular
    }
    return p;
  }

  public static final int[][] PROFILES = computeProfiles();

  private static int[][] computeProfiles() {
    return Arrays.stream(NUMBERS)
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

  public static void dump(int[] i) {
    System.out.println(Arrays.toString(i));
  }
  
  public static int[] search(int[] rowStarts, int[] rowEnds, int[] additionalLetters) {
    double searchSpaceSize = 1;
    for (int i = 0; i < SIZE; i++) {
      if (i == 2 || i == 5 || i == 14 || i == 15) {
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
