package org.svomz.fsm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractFSM<S extends State, C extends FSMContext<S>> implements FSM<C> {

  Map<S, Map<Event, S>> transitionPerOriginalState = new HashMap<>();

  Map<S, Map<Event, Consumer<C>>> actionsToExecutePerState = new HashMap<>();

  public AbstractFSM() {
    FSMBuilder<S, C> builder = this.define();

    Set<Transition<S, C>> transitions = builder.transitions();

    for (Transition<S, C> transition : transitions) {
      if (!this.transitionPerOriginalState.containsKey(transition.originalState())) {
        this.transitionPerOriginalState
          .put(transition.originalState(), new HashMap<Event, S>());
      }
      Map<Event, S> map = this.transitionPerOriginalState.get(transition.originalState());
      map.put(transition.event(), transition.toState());

      if (!this.actionsToExecutePerState.containsKey(transition.originalState())) {
        this.actionsToExecutePerState
          .put(transition.originalState(), new HashMap<Event, Consumer<C>>());
      }
      Map<Event, Consumer<C>>
        map2 =
        this.actionsToExecutePerState.get(transition.originalState());
      map2.put(transition.event(), transition.action());

    }

  }

  @Override
  public C trigger(Event event, C context) {
    if (context.isFinished()) {
      return context;
    }

    Map<Event, S>
      possibleTransitionsForCurrentState =
      this.transitionsPerOriginalState().get(context.currentState());

    if (possibleTransitionsForCurrentState == null) {
      return context;
    }

    S newState = possibleTransitionsForCurrentState.get(event);
    if (newState == null) {
      return context;
    }

    C newContext = (C) context.copy(newState);

    Map<Event, Consumer<C>>
      possibleActionToExecute =
      this.actionsToExecutePerState.get(context.currentState());
    if (possibleActionToExecute != null) {
      Consumer<C> action = possibleActionToExecute.get(event);
      if (action != null) {
        action.accept(newContext);
      }
    }

    return newContext;
  }

  /**
   * Creates a {@link FSMBuilder} by specifing the initial {@link State} of the Final State Machine.
   *
   * @param initialStatus the inital {@link State} of the final state machine
   * @return a {@link FSMBuilder}
   */
  protected FSMBuilder<S, C> startWith(S initialStatus) {
    return new FSMBuilder<S, C>(initialStatus);
  }

  /**
   * Creates a {@link TransitionBuilder} by specifing the {@link Event} that will cause the Finite State
   * Machine to change.
   *
   * @param event The {@link Event} causing the FSM to change
   * @return a {@link TransitionBuilder}
   */
  protected TransitionBuilder<S, C> on(Event event) {
    return new TransitionBuilder<S, C>().on(event);
  }

  /**
   * Allows child classes to define the Finite State Machine behavior. This method is called by the constructor
   * to retrive the list of transitions.
   *
   * @return the {@link FSMBuilder} use to create instances of the finite state machine.
   */
  protected abstract FSMBuilder<S, C> define();

  /**
   * Get all possible transitions from origin {@link State}s to destination {@link State} according
   * to the {@link Event} triggered.
   *
   * @return a map with the keys corresponding the {@link State}s and the values are a map with
   * all possible events in the {@link State} specified by the key and the {@link State} to with the
   * {@link FSM} must change..
   */
  Map<S, Map<Event, S>> transitionsPerOriginalState() {
    return this.transitionPerOriginalState;
  }
}
