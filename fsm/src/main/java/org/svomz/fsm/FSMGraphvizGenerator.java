package org.svomz.fsm;

import java.text.MessageFormat;
import java.util.Map;

public class FSMGraphvizGenerator {

  public static String generate(AbstractFSM<? extends State, ?> fsm, FSMContext<?> context) {
    Map<? extends State, ? extends Map<Event, ? extends State>>
      transitionsPerOriginalState = fsm.transitionsPerOriginalState();

    StringBuilder graphViz = new StringBuilder("digraph finite_state_machine {")
      .append("rankdir=LR;")
      .append(MessageFormat
        .format("\"{0}\" [shape=\"doublecircle\" color=\"greenyellow\" style=\"filled\"];",
          context.currentState().toString()))
      .append(MessageFormat.format("size=\"{0}\"", transitionsPerOriginalState.keySet().size() * 3))
      .append("node [shape = circle];");

    for (State fromState : transitionsPerOriginalState.keySet()) {
      Map<Event, ? extends State> map = transitionsPerOriginalState.get(fromState);
      for (Event event : map.keySet()) {
        State toState = map.get(event);
        if (toState.isFinal() && fromState != context.currentState()) {
          graphViz
            .append(MessageFormat.format("\"{0}\" [shape=\"doublecircle\"];", toState.toString()));
        }
      }

    }

    for (State fromState : transitionsPerOriginalState.keySet()) {
      Map<Event, ? extends State> map = transitionsPerOriginalState.get(fromState);
      for (Event event : map.keySet()) {
        graphViz.append(MessageFormat.format("{0} -> {1} [ label = \"on: {2}\"];", fromState.toString(), map.get(event)
          .toString(), event.toString()));
      }

    }

    graphViz.append("}");
    String string = graphViz.toString();
    return string;
  }

  public static String generate(AbstractFSM<? extends State, ?> fsm) {
    Map<? extends State, ? extends Map<Event, ? extends State>>
      transitionsPerOriginalState = fsm.transitionsPerOriginalState();

    StringBuilder graphViz = new StringBuilder("digraph finite_state_machine {")
      .append("rankdir=LR;")
      .append(MessageFormat.format("size=\"{0}\"", transitionsPerOriginalState.keySet().size() * 3))
      .append("node [shape = circle];");

    for (State fromState : transitionsPerOriginalState.keySet()) {
      Map<Event, ? extends State> map = transitionsPerOriginalState.get(fromState);
      for (Event event : map.keySet()) {
        State toState = map.get(event);
        if (toState.isFinal()) {
          graphViz
            .append(MessageFormat.format("\"{0}\" [shape=\"doublecircle\"];", toState.toString()));
        }
      }

    }

    for (State fromState : transitionsPerOriginalState.keySet()) {
      Map<Event, ? extends State> map = transitionsPerOriginalState.get(fromState);
      for (Event event : map.keySet()) {
        graphViz.append(MessageFormat.format("{0} -> {1} [ label = \"on: {2}\"];", fromState.toString(), map.get(event)
          .toString(), event.toString()));
      }

    }

    graphViz.append("}");
    String string = graphViz.toString();
    return string;
  }

}
