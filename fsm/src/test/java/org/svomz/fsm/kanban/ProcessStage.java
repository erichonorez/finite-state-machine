package org.svomz.fsm.kanban;

import org.svomz.fsm.State;

public enum ProcessStage implements State {
    TODO(false),
    IN_PROGRESS(false),
    WAITING_ACCEPTANCE(false),
    FOR_PRODUCTION(false),
    IN_PRODUCTION(true);

    private final boolean isFinal;

    ProcessStage(boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }
}
