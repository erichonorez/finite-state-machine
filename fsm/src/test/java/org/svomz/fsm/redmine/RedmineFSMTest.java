package org.svomz.fsm.redmine;

import org.junit.Test;
import org.svomz.fsm.Status;

import static org.junit.Assert.*;

public class RedmineFSMTest {

  @Test
  public void testDefine() {
    RedmineFSM fsm = new RedmineFSM();

    RedmineFSMContext context = new RedmineFSMContext(Status.TODO, "123");
    RedmineFSMContext wipContext = fsm.trigger(RedmineEvent.STARTED, context);

    assertEquals(org.svomz.fsm.Status.IN_PROGRESS, wipContext.currentState());

    RedmineFSMContext fixedContext = fsm.trigger(RedmineEvent.FIXED, wipContext);

    assertEquals(Status.FIXED, fixedContext.currentState());
  }

}