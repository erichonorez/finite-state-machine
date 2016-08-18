package org.svomz.fsm.kanban;

import org.svomz.fsm.Event;

public enum ProcessEvent implements Event {
    WORK_ITEM_ASSIGNED,
    CODE_COMMITED,
    ACCEPTED,
    REFUSED,
    DEPLOYED
}
