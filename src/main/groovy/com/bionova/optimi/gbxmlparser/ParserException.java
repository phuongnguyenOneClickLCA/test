package com.bionova.optimi.gbxmlparser;

@SuppressWarnings("serial")
public class ParserException extends Exception {
  Integer line;

  public ParserException(String message) {
    super(message);
    this.line = null;
  }

  public ParserException(String message, int line) {
    super(message);
    this.line = line;
  }

  public int getLine() {
    if (line == null) return 0;
    return line;
  }
}
