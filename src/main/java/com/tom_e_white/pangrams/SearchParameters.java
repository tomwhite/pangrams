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

}
