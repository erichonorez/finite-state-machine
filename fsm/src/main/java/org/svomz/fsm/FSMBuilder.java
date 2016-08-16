package org.svomz.fsm;

import java.util.HashSet;
import java.util.Set;

public class FSMBuilder<S extends State, C extends FSMContext<S>> {

  private final S initialState;

  private final Set<Transition<S, C>> transitions = new HashSet<>();

  private S lastState;


  FSMBuilder(S initialState) {
    this.initialState = initialState;
    this.lastState = initialState;
  }

  S initialState() {
    return this.initialState;
  }

  Set<Transition<S, C>> transitions() {
    return this.transitions;
  }

  public FSMBuilder<S, C> transit(TransitionBuilder<S, C>... builders) {
    for (TransitionBuilder transitionBuilder : builders) {
      this.transitions.addAll(transitionBuilder.from(this.lastState).build());
    }
    return this;
  }
}
