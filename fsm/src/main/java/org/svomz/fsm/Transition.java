package org.svomz.fsm;

import java.util.function.Consumer;

public class Transition<S extends State, C extends FSMContext<S>> {

  private final Event event;

  private final S originalState;

  private final S toState;

  private final Consumer<C> action;

  public Transition(Event event, S originalState, S toState) {
    this(event, originalState, toState, null);
  }

  public Transition(Event event, S originalState, S toState, Consumer<C> action) {
    this.event = event;
    this.originalState = originalState;
    this.toState = toState;
    this.action = action;
  }

  public Event event() {
    return this.event;
  }

  public S originalState() {
    return this.originalState;
  }

  public static Transition on(Event event) {
    return new Transition(event, null, null);
  }

  public S toState() {
    return this.toState;
  }

  public Consumer<C> action() {
    return this.action;
  }

}
