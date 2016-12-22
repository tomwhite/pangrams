package com.tom_e_white.pangrams;

public class SearchParameters {
  private final int[] rowStarts;
  private final int[] rowEnds;
  private final int[] additionalLetters;

  public SearchParameters(int[] rowStarts, int[] rowEnds, int[] additionalLetters) {
    this.rowStarts = rowStarts;
    this.rowEnds = rowEnds;
    this.additionalLetters = additionalLetters;
  }

  public int[] getRowStarts() {
    return rowStarts;
  }

  public int[] getRowEnds() {
    return rowEnds;
  }

  public int[] getAdditionalLetters() {
    return additionalLetters;
  }

  public boolean isInRange(int[] p) {
    for (int i = 0; i < p.length; i++) {
      if (p[i] < rowStarts[i] || p[i] > rowEnds[i]) {
        return false;
      }
    }
    return true;
  }
}
