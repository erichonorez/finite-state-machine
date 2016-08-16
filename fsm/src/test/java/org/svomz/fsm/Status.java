package org.svomz.fsm;

public enum Status implements State {
  TODO(false),
  IN_PROGRESS(false),
  FIXED(true);

  private final boolean isFinal;

  Status(boolean isFinal) {
    this.isFinal = isFinal;
  }

  @Override
  public boolean isFinal() {
    return this.isFinal;
  }
}
