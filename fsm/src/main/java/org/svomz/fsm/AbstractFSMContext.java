package org.svomz.fsm;


public abstract class AbstractFSMContext<S extends State> implements FSMContext<S> {

  private final S currentState;

  public AbstractFSMContext(S state) {
    this.currentState = state;
  }

  @Override
  public S currentState() {
    return this.currentState;
  }

  @Override
  public boolean isFinished() {
    return this.currentState.isFinal();
  }

  @Override
  public abstract FSMContext<S> copy(S newState);
}
