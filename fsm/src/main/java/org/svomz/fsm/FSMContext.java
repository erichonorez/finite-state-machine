package org.svomz.fsm;

public interface FSMContext<S extends State> {

  S currentState();

  boolean isFinished();

  FSMContext<S> copy(S newState);
}
