package org.svomz.fsm.kanban;

import org.junit.Assert;
import org.junit.Test;

public class ProcessUnitTest {

    @Test
    public void WhenProcessReceiveAKnownEvent_ItShouldTransitToAnotherState() {
        Process process = new Process();
        ProcessContext initialContext = new ProcessContext(ProcessStage.TODO);

        ProcessContext contextInTodoState = process.trigger(ProcessEvent.WORK_ITEM_ASSIGNED, initialContext);
        Assert.assertEquals(ProcessStage.IN_PROGRESS, contextInTodoState.currentState());

        ProcessContext contextInWaitingAcceptanceState = process.trigger(ProcessEvent.CODE_COMMITED, contextInTodoState);
        Assert.assertEquals(ProcessStage.WAITING_ACCEPTANCE, contextInWaitingAcceptanceState.currentState());

        ProcessContext contextInForProductionState = process.trigger(ProcessEvent.ACCEPTED, contextInWaitingAcceptanceState);
        Assert.assertEquals(ProcessStage.FOR_PRODUCTION, contextInForProductionState.currentState());

        ProcessContext contextInProductiontate = process.trigger(ProcessEvent.DEPLOYED, contextInForProductionState);
        Assert.assertEquals(ProcessStage.IN_PRODUCTION, contextInProductiontate.currentState());
    }

}
