package org.svomz.fsm;

import static org.svomz.fsm.FSM2UnitTest.FlowBuilder.from;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.ACCEPTED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.CLIENT_SIGNED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.CONTRACT_OPTIONS_CHOSEN;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.CONTRACT_PRINTED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.CONTRACT_VALIDATED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.DIGIAL_SIGNING_CHOSEN;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.DIGITAL_SIGNED_ABANDONNED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.DOSSIER_SCORE_INTEROGATIVE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.DOSSIER_SCORE_NEGATIVE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.DOSSIER_SCORE_POSITIVE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.EMPLOYER_INFORMATION_SAVED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.EVIDENCES_UPLOADED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.ID_SCORE_NEGATIVE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.ID_SCORE_POSITIVE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.MANUAL_SIGNING_CHOSEN;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.TEMPORARY_CARD_NUMBER_ASSIGNED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.TEMPORARY_CARD_RECEIPT_SIGNED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowEvent.VENDOR_SIGNED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.REFUSED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.VALIDATED;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_ACCEPTATION;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_CLIENT_SIGNATURE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_CONTRACT_OPTIONS_CHOICE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_CONTRACT_PRINTING;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_CONTRACT_VALIDATION;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_EMPLOYER_INFORMATION;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_EVIDENCES;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_HOUSEHOLD_INFORMATION;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_PERSONAL_INFORMATION;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_SIGNATURE_TYPE_CHOICE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_TEMPORARY_CARD_ATTRIBUTION;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_TEMPORARY_CARD_RECEIPT_SIGNATURE;
import static org.svomz.fsm.FSM2UnitTest.MakroESignFlowState.WAITING_VENDOR_SIGNATURE;
import static org.svomz.fsm.FSM2UnitTest.MyState.TO_START;
import static org.svomz.fsm.FSM2UnitTest.TransitionDSL.on;
import static org.svomz.fsm.FSM2UnitTest.TransitionDSL.to;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class FSM2UnitTest {

  @Test
  public void test() {
    Set<Transition> transitions = new HashSet<>();
    transitions.add(new Transition(MyFlowEvent.STARTED, MyState.TO_START, MyState.IN_PROGRESS));
    transitions.add(new Transition(MyFlowEvent.REFUSED, MyState.TO_START, MyState.WONT_FIX));
    transitions.add(new Transition(MyFlowEvent.REFUSED, MyState.IN_PROGRESS, MyState.WONT_FIX));
    transitions.add(new Transition(MyFlowEvent.FIXED, MyState.IN_PROGRESS, MyState.TO_VALIDATE));
    transitions
      .add(new Transition(MyFlowEvent.NOT_VALIDATED, MyState.TO_VALIDATE, MyState.IN_PROGRESS));

    FSM flow = new FSM(transitions, MyState.TO_START);
    flow.trigger(MyFlowEvent.FIXED);
    Assert.assertFalse(flow.isFinished());
    Assert.assertEquals(flow.currentState(), MyState.TO_START);

    flow.trigger(MyFlowEvent.STARTED);
    Assert.assertFalse(flow.isFinished());
    Assert.assertEquals(flow.currentState(), MyState.IN_PROGRESS);

    flow.trigger(MyFlowEvent.REFUSED);
    flow.trigger(MyFlowEvent.FIXED);
    Assert.assertEquals(flow.currentState(), MyState.WONT_FIX);
    Assert.assertTrue(flow.isFinished());
  }

  @Test
  public void mescouilles() {
    FSM flow = from(MyState.TO_START).transit(
      to(MyState.IN_PROGRESS)
        .on(MyFlowEvent.STARTED).transit(
        to(MyState.TO_VALIDATE)
          .on(MyFlowEvent.FIXED).transit(
          to(MyState.TO_VALIDATE)
            .on(MyFlowEvent.NOT_VALIDATED))),
      to(MyState.WONT_FIX)
        .on(MyFlowEvent.REFUSED)).build();

    Assert.assertEquals(MyState.TO_START, flow.currentState());

    flow.trigger(MyFlowEvent.STARTED);
    Assert.assertEquals(MyState.IN_PROGRESS, flow.currentState());

    flow.trigger(MyFlowEvent.FIXED);
    Assert.assertEquals(MyState.TO_VALIDATE, flow.currentState());

    flow.trigger(MyFlowEvent.NOT_VALIDATED);
    Assert.assertEquals(MyState.TO_VALIDATE, flow.currentState());
  }

  @Test
  public void mescouilles2() {
    FSM flow = from(MyState.TO_START).transit(
      on(MyFlowEvent.STARTED)
        .to(MyState.IN_PROGRESS)
        .transit(
          on(MyFlowEvent.FIXED)
            .to(MyState.TO_VALIDATE)
            .transit(
              on(MyFlowEvent.NOT_VALIDATED)
                .to(MyState.IN_PROGRESS))),
      on(MyFlowEvent.REFUSED)
        .to(MyState.WONT_FIX)

    ).build();

    Assert.assertEquals(MyState.TO_START, flow.currentState());

    flow.trigger(MyFlowEvent.STARTED);
    Assert.assertEquals(MyState.IN_PROGRESS, flow.currentState());

    flow.trigger(MyFlowEvent.FIXED);
    Assert.assertEquals(MyState.TO_VALIDATE, flow.currentState());

    flow.trigger(MyFlowEvent.NOT_VALIDATED);
    Assert.assertEquals(MyState.TO_VALIDATE, flow.currentState());
  }

  @Test
  public void itShoudGenerateAGraphViz() {
    FSM flow = from(TO_START).transit(
      on(MyFlowEvent.STARTED)
        .to(MyState.IN_PROGRESS)
        .transit(
          on(MyFlowEvent.FIXED)
            .to(MyState.TO_VALIDATE)
            .transit(
              on(MyFlowEvent.NOT_VALIDATED)
                .to(MyState.IN_PROGRESS))),
      on(MyFlowEvent.REFUSED)
        .to(MyState.WONT_FIX)

    ).build();

    String string = generateGraph(flow);

    System.out.println(string);
  }

  @Test
  public void mescouillesInAction() {
    FSM flow = from(MyState.TO_START)
      .transit(
        to(MyState.IN_PROGRESS)
          .on(MyFlowEvent.STARTED)
          .execute(new PrintAction())
          .transit(
            to(MyState.TO_VALIDATE)
              .on(MyFlowEvent.FIXED)
              .transit(
                to(MyState.TO_VALIDATE)
                  .on(MyFlowEvent.NOT_VALIDATED))),
        to(MyState.WONT_FIX)
          .on(MyFlowEvent.REFUSED)).build();

    Assert.assertEquals(MyState.TO_START, flow.currentState());

    flow.trigger(MyFlowEvent.STARTED);
    Assert.assertEquals(MyState.IN_PROGRESS, flow.currentState());

    flow.trigger(MyFlowEvent.FIXED);
    Assert.assertEquals(MyState.TO_VALIDATE, flow.currentState());

    flow.trigger(MyFlowEvent.NOT_VALIDATED);
    Assert.assertEquals(MyState.TO_VALIDATE, flow.currentState());

    System.out.println(generateGraph(flow));
  }

  private static final class PrintAction implements Action<State> {

    @Override
    public void execute(State t) {
      System.out.println("Changed to state: " + t);
    }
  }

  public static interface Action<T> {

    void execute(T t);
  }

  @Test
  public void itShoudGenerateAGraphViz2() {
    FSM flow = from(MyState.TO_START).transit(
      on(MyFlowEvent.STARTED)
        .to(MyState.IN_PROGRESS)
        .transit(
          on(MyFlowEvent.FIXED)
            .to(MyState.TO_VALIDATE)
            .transit(
              on(MyFlowEvent.NOT_VALIDATED)
                .to(MyState.IN_PROGRESS))),
      on(MyFlowEvent.REFUSED)
        .to(MyState.WONT_FIX)

    ).build();

    flow.trigger(MyFlowEvent.STARTED);

    System.out.println(generateGraph(flow));
  }

  @Test
  public void MakroESignFlow() {
    //@formatter:off
    FSM makroESigningFlow = from(WAITING_PERSONAL_INFORMATION)
      .transit(

        on(ID_SCORE_POSITIVE)
          .to(WAITING_EMPLOYER_INFORMATION)
          .transit(

            on(EMPLOYER_INFORMATION_SAVED)
              .to(WAITING_HOUSEHOLD_INFORMATION)
              .transit(

                on(DOSSIER_SCORE_POSITIVE)
                  .to(WAITING_SIGNATURE_TYPE_CHOICE)
                  .transit(

                    on(MANUAL_SIGNING_CHOSEN)
                      .to(WAITING_CONTRACT_PRINTING)
                      .transit(

                        on(CONTRACT_PRINTED)
                          .to(WAITING_CONTRACT_VALIDATION)
                          .transit(
                            on(CONTRACT_VALIDATED)
                              .to(VALIDATED))),
                    on(DIGIAL_SIGNING_CHOSEN)
                      .to(WAITING_CONTRACT_OPTIONS_CHOICE)
                      .transit(

                        on(CONTRACT_OPTIONS_CHOSEN)
                          .to(WAITING_CLIENT_SIGNATURE)
                          .transit(

                            on(CLIENT_SIGNED)
                              .to(WAITING_VENDOR_SIGNATURE)
                              .transit(

                                on(VENDOR_SIGNED)
                                  .to(WAITING_EVIDENCES)
                                  .transit(

                                    on(EVIDENCES_UPLOADED)
                                      .to(WAITING_TEMPORARY_CARD_ATTRIBUTION)
                                      .transit(

                                        on(TEMPORARY_CARD_NUMBER_ASSIGNED)
                                          .to(WAITING_TEMPORARY_CARD_RECEIPT_SIGNATURE)
                                          .transit(

                                            on(TEMPORARY_CARD_RECEIPT_SIGNED)
                                              .to(VALIDATED)))),
                                on(DIGITAL_SIGNED_ABANDONNED)
                                  .to(WAITING_CONTRACT_PRINTING)),
                            on(DIGITAL_SIGNED_ABANDONNED)
                              .to(WAITING_CONTRACT_PRINTING)))),
                on(DOSSIER_SCORE_NEGATIVE)
                  .to(REFUSED),
                on(DOSSIER_SCORE_INTEROGATIVE)
                  .to(WAITING_ACCEPTATION)
                  .transit(
                    on(ACCEPTED)
                      .to(WAITING_SIGNATURE_TYPE_CHOICE)))),

        on(ID_SCORE_NEGATIVE)
          .to(REFUSED)).build();

    System.out.println(generateGraph(makroESigningFlow));
    //@formatter:on
  }

  public static interface FSMContext<S extends State> {

    S currentState();

  }

  public enum MakroESignFlowState implements State {
    WAITING_PERSONAL_INFORMATION(false),
    WAITING_EMPLOYER_INFORMATION(false),
    WAITING_HOUSEHOLD_INFORMATION(false),
    REFUSED(true),
    WAITING_ACCEPTATION(false),
    ABANDONNED(true),
    WAITING_SIGNATURE_TYPE_CHOICE(false),
    WAITING_CONTRACT_OPTIONS_CHOICE(false),
    WAITING_CLIENT_SIGNATURE(false),
    WAITING_VENDOR_SIGNATURE(false),
    WAITING_EVIDENCES(false),
    WAITING_TEMPORARY_CARD_ATTRIBUTION(false),
    WAITING_TEMPORARY_CARD_RECEIPT_SIGNATURE(false),
    VALIDATED(true),
    WAITING_CONTRACT_PRINTING(false),
    WAITING_CONTRACT_VALIDATION(false);

    private final boolean isEnding;

    MakroESignFlowState(boolean isEnding) {
      this.isEnding = isEnding;
    }

    @Override
    public boolean isEnding() {
      return this.isEnding;
    }
  }

  public enum MakroESignFlowEvent implements Event {
    ID_SCORE_POSITIVE,
    ID_SCORE_NEGATIVE,
    DOSSIER_SCORE_POSITIVE,
    DOSSIER_SCORE_NEGATIVE,
    DOSSIER_SCORE_INTEROGATIVE,
    EMPLOYER_INFORMATION_SAVED,
    ACCEPTED,
    MANUAL_SIGNING_CHOSEN,
    DIGIAL_SIGNING_CHOSEN,
    CONTRACT_OPTIONS_CHOSEN,
    CONTRACT_PRINTED,
    CONTRACT_VALIDATED,
    CLIENT_SIGNED,
    VENDOR_SIGNED,
    TEMPORARY_CARD_NUMBER_ASSIGNED,
    TEMPORARY_CARD_RECEIPT_SIGNED,
    DIGITAL_SIGNED_ABANDONNED,
    EVIDENCES_UPLOADED;
  }

  private String generateGraph(FSM flow) {
    Map<State, Map<Event, State>> transitionsPerOriginalState = flow.transitionsPerOriginalState();
    StringBuilder graphViz = new StringBuilder("digraph finite_state_machine {")
      .append("rankdir=LR;")
      .append(MessageFormat
        .format("\"{0}\" [shape=\"doublecircle\" color=\"greenyellow\" style=\"filled\"];",
          flow.currentState().toString()))
      .append(MessageFormat.format("size=\"{0}\"", transitionsPerOriginalState.keySet().size() * 3))
      .append("node [shape = circle];");

    for (State fromState : transitionsPerOriginalState.keySet()) {
      Map<Event, State> map = transitionsPerOriginalState.get(fromState);
      for (Event event : map.keySet()) {
        State toState = map.get(event);
        if (toState.isEnding() && fromState != flow.currentState()) {
          graphViz
            .append(MessageFormat.format("\"{0}\" [shape=\"doublecircle\"];", toState.toString()));
        }
      }

    }

    for (State fromState : transitionsPerOriginalState.keySet()) {
      Map<Event, State> map = transitionsPerOriginalState.get(fromState);
      for (Event event : map.keySet()) {

        String actionName = null;
        Map<Event, Action<State>> possibleActions = flow.actionsToExecutePerState.get(fromState);
        if (possibleActions != null) {
          Action<State> action = possibleActions.get(event);
          if (action != null) {
            actionName = action.getClass().getSimpleName();
          }
        }

        String
          node =
          actionName != null ? "{0} -> {1} [ label = \"on: {2} \\n execute: {3} \"];"
                             : "{0} -> {1} [ label = \"on: {2}\"];";
        graphViz.append(MessageFormat.format(node, fromState.toString(), map.get(event)
          .toString(), event.toString(), actionName));
      }

    }

    graphViz.append("}");
    String string = graphViz.toString();
    return string;
  }

  public static class FlowBuilder {

    private Set<Transition> transitions = new HashSet<>();

    private State lastState;

    private State initialState;

    public static FlowBuilder from(State toStart) {
      return new FlowBuilder()
        .initialState(toStart)
        .fromState(toStart);
    }

    public FSM build() {
      return new FSM(this.transitions, this.initialState);
    }

    private FlowBuilder initialState(State toStart) {
      this.initialState = toStart;
      return this;
    }

    private FlowBuilder fromState(State toStart) {
      this.lastState = toStart;
      return this;
    }

    public FlowBuilder transit(TransitionBuilder... builders) {
      for (TransitionBuilder transitionBuilder : builders) {
        this.transitions.addAll(transitionBuilder.from(this.lastState).build());
      }
      return this;
    }

  }

  public static class TransitionBuilder {

    private State fromState;

    private State toState;

    private Event event;

    private List<TransitionBuilder> childBuilders = new ArrayList<>();

    private Action<State> action;

    public TransitionBuilder from(State fromState) {
      this.fromState = fromState;
      return this;
    }

    public TransitionBuilder execute(Action<State> action) {
      this.action = action;
      return this;
    }

    public TransitionBuilder transit(TransitionBuilder... transitionBuilders) {
      for (TransitionBuilder builder : transitionBuilders) {
        this.childBuilders.add(builder);
      }
      return this;
    }

    public TransitionBuilder to(State toState) {
      this.toState = toState;
      return this;
    }

    public TransitionBuilder on(Event event) {
      this.event = event;
      return this;
    }

    public Set<Transition> build() {
      Set<Transition> transitions = new HashSet<>();
      for (TransitionBuilder builder : this.childBuilders) {
        builder.from(this.toState);
        transitions.addAll(builder.build());
      }
      transitions.add(new Transition(this.event, this.fromState, this.toState, this.action));
      return transitions;
    }

  }

  public static class TransitionDSL {

    public static TransitionBuilder to(State toState) {
      return new TransitionBuilder().to(toState);
    }

    public static TransitionBuilder on(Event event) {
      return new TransitionBuilder().on(event);
    }
  }

  public static class FSM {

    Map<State, Map<Event, State>> transitionPerOriginalState = new HashMap<>();

    Map<State, Map<Event, Action<State>>> actionsToExecutePerState = new HashMap<>();

    private State initialState;

    public FSM(FlowBuilder builder) {
      this(builder.transitions, builder.initialState);
    }

    public FSM(Set<Transition> transitions, State initialState) {
      this.initialState = initialState;

      for (Transition transition : transitions) {
        if (!this.transitionPerOriginalState.containsKey(transition.originalState())) {
          this.transitionPerOriginalState
            .put(transition.originalState(), new HashMap<Event, State>());
        }
        Map<Event, State> map = this.transitionPerOriginalState.get(transition.originalState());
        map.put(transition.event(), transition.toState());

        if (!this.actionsToExecutePerState.containsKey(transition.originalState())) {
          this.actionsToExecutePerState
            .put(transition.originalState(), new HashMap<Event, Action<State>>());
        }
        Map<Event, Action<State>>
          map2 =
          this.actionsToExecutePerState.get(transition.originalState());
        map2.put(transition.event(), transition.action());

      }
    }

    public boolean isFinished() {
      return this.currentState().isEnding();
    }

    public State currentState() {
      return this.initialState;
    }

    public void trigger(MyFlowEvent event) {
      if (this.isFinished()) {
        return;
      }

      Map<Event, State>
        possibleTransitionsForCurrentState =
        this.transitionsPerOriginalState().get(this.currentState());
      if (possibleTransitionsForCurrentState == null) {
        return;
      }

      State newState = possibleTransitionsForCurrentState.get(event);
      if (newState == null) {
        return;
      }

      Map<Event, Action<State>>
        possibleActionToExecute =
        this.actionsToExecutePerState.get(this.currentState());
      if (possibleActionToExecute != null) {
        Action<State> action = possibleActionToExecute.get(event);
        if (action != null) {
          action.execute(newState);
        }
      }

      this.initialState = newState;
    }

    private Map<State, Map<Event, State>> transitionsPerOriginalState() {
      return this.transitionPerOriginalState;
    }

  }

  public static class Transition {

    private final Event event;

    private final State originalState;

    private final State toState;

    private final Action<State> action;

    public Transition(Event event, State originalState, State toState) {
      this(event, originalState, toState, null);
    }

    public Transition(Event event, State originalState, State toState, Action<State> action) {
      this.event = event;
      this.originalState = originalState;
      this.toState = toState;
      this.action = action;
    }

    public Event event() {
      return this.event;
    }

    public State originalState() {
      return this.originalState;
    }

    public static Transition on(Event event) {
      return new Transition(event, null, null);
    }

    public State toState() {
      return this.toState;
    }

    public Action<State> action() {
      return this.action;
    }

  }

  private interface Event {

  }

  private interface State {

    boolean isEnding();

  }

  public enum MyFlowEvent implements Event {
    STARTED,
    REFUSED,
    FIXED,
    NOT_VALIDATED
  }

  public enum MyState implements State {
    TO_START(false),
    IN_PROGRESS(false),
    TO_VALIDATE(false),
    WONT_FIX(true);

    private boolean isEnding;

    MyState(boolean isEnding) {
      this.isEnding = isEnding;
    }

    @Override
    public boolean isEnding() {
      return this.isEnding;
    }
  }

  // itShouldReturnAllPossibleTransitionFromAOriginalStat
}
