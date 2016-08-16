package org.svomz.fsm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class TransitionBuilder<S extends State, C extends FSMContext<S>> {

  private S fromState;

  private S toState;

  private Event event;

  private List<TransitionBuilder> childBuilders = new ArrayList<>();

  private Consumer<C> action;

  public TransitionBuilder<S, C> from(S fromState) {
    this.fromState = fromState;
    return this;
  }

  public TransitionBuilder<S, C> execute(Consumer<C> action) {
    this.action = action;
    return this;
  }

  public TransitionBuilder<S, C> transit(TransitionBuilder<S, C>... transitionBuilders) {
    for (TransitionBuilder<S, C> builder : transitionBuilders) {
      this.childBuilders.add(builder);
    }
    return this;
  }

  public TransitionBuilder<S, C> to(S toState) {
    this.toState = toState;
    return this;
  }

  public TransitionBuilder<S, C> on(Event event) {
    this.event = event;
    return this;
  }

  public Set<Transition<S, C>> build() {
    Set<Transition<S, C>> transitions = new HashSet<>();
    for (TransitionBuilder builder : this.childBuilders) {
      builder.from(this.toState);
      transitions.addAll(builder.build());
    }
    transitions.add(new Transition<S, C>(this.event, this.fromState, this.toState, this.action));
    return transitions;
  }

}
