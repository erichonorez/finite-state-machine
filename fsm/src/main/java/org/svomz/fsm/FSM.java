package org.svomz.fsm;

public interface FSM<C extends FSMContext> {

  /**
   * Triggers an {@link Event} in the final state machine. This event may cause the current state of the
   * FSM to change.
   *
   * It does not modify the initial {@link FSMContext} given in parameter. Instead it return an updated copy
   * of the {@link FSMContext}.
   *
   * @param event
   * @param context the context of the FSM on which the {@link Event} is triggered.
   * @return the new context
   */
  C trigger(Event event, C context);

}
